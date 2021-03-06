module.factory('Activity', function ($resource) {
    return $resource("api/activities/:userid", {userid: '@userid'});
})
        .controller('ActivityController', function ($scope, Activity, $localStorage, $state, $http, $rootScope, $filter) {

            init();
            function init() {
                if (!$rootScope.authorized || $rootScope.authorized == null || $rootScope.authorized == undefined)
                {
                    $state.go("/err");
                    $localStorage.err = 1;
                }
                $scope.curmonth = months[new Date().getMonth()];
                $scope.show = new Object();
                $scope.show.show = false;
                $scope.show.submit = false;
                $scope.show.moduleWin = false;
                $scope.show.showFilters = false;
                $scope.show.style = {};
                $scope.show.errorStyle = {'background-color': '#FF8080', 'padding': '5px', "border": "2px solid red", "text-align": "center"};
                $scope.show.successStyle = {'background-color': '#81E383', 'padding': '5px', "border": "2px solid green", "text-align": "center"};
                $scope.show.warningStyle = {'background-color': 'yellow', 'padding': '5px', "border": "2px solid #D6D300", "text-align": "center"};
                $scope.show.loading = false;
                $scope.msg = msg;
            }


            var url = function () {
                return {userid: $rootScope.user.staffId};
            }

            var update = function () {
                
                $scope.activities = Activity.query(url());
                $scope.projectActivities = $localStorage.projectActivities;
                $scope.searchpname = $state.params.name;
                if ($state.params.bdate!=null) $scope.start = $state.params.bdate;
                if ($state.params.edate!=null) $scope.end = $state.params.edate;
                $scope.applyDates();
            }

            $scope.update = update;

            $scope.add = function addActivity() {
                if (!verifyInput()) return;
                var a = new Activity();
                a.empl = {"id": $rootScope.user.staffId};
                a.proj = {"id": $scope.projid};
                a.hours = $scope.hours;
                a.note = $scope.note;
                a.place = $scope.place;
                a.description = $scope.desc;
                a.submitted = false;
                if ($scope.type)
                    a.typeId = 5;
                else
                    a.typeId = 6;
                if ($scope.fillseveraldates) {
                    var bdate = new Date($scope.fromDate);
                    var edate = new Date($scope.toDate);
                    for (; bdate <= edate; bdate.setDate(bdate.getDate() + 1)) {
                        if (bdate.getDay() == 6 || bdate.getDay() == 0)
                            continue;
                        //this piece sucks
                        var d = new Date(bdate.getTime());
                        a.date = d;
                        var a1 = new Activity();
                        a1.empl = a.empl;
                        a1.proj = a.proj;
                        a1.hours = a.hours;
                        a1.note = a.note;
                        a1.place = a.place;
                        a1.description = a.description;
                        a1.typeId = a.typeId;
                        a1.date = d;
                        a1.$save();
                    }


                } else {
                    var date = new Date($scope.date);
                    a.date = $scope.date;
                    a.$save();


                }
                $scope.show.message = "Data has been added successfully";
                $scope.show.style = $scope.show.successStyle;
                $scope.show.show = true;
            }

            $scope.applyDates = function () {
                if ($scope.start==null || $scope.end==null) return;
                var m = $scope.start.getMonth() + 1;
                var s = $scope.start.getFullYear() + "-" + m + "-" + $scope.start.getDate();
                m = $scope.end.getMonth() + 1;
                var e = $scope.end.getFullYear() + "-" + m + "-" + $scope.end.getDate();

                $http.get("api/activities/" + $rootScope.user.staffId + "/" + s + "_" + e)
                        .then(function successCallback(response) {
                            $scope.activities = response.data;
                            checkIfFilled();  
                        }, function errorCallback(response) {
                        });
                
            }
            function checkIfFilled(){
                var m = new Map();
                var d  = new Date();
                var b = new Date(d.getTime());
                b.setDate(1);
                var e = new Date(b.getTime());
                e.setMonth(e.getMonth()+1);
                for (;b<e;b.setDate(b.getDate()+1)){
                    var temp = new Date(d.getTime());
                    temp.setDate(b.getDate());
                    if (temp.getDay()==0 || temp.getDay()==6) continue;
                    m.set(b.getDate(),null);
                }
                if ( $scope.start.getMonth()==d.getMonth() &&
                        $scope.end.getMonth() == d.getMonth()+1 &&
                        d.getDate()>25) {
                    for (var i=0;i<$scope.activities.length;i++){
                        var el = $scope.activities[i];
                        var temp = new Date(el.date);
                        m.set(temp.getDate(), m.get(temp.getDate())+el.hours);
                    }
                    m.forEach(function(value,key){
                        if (value<8) {
                            $scope.show.message = "You have less than 8 hours on some dates!";
                            $scope.show.style = $scope.show.warningStyle;
                            $scope.show.show=true;
                            return false;
                        }
                    });
                   
                }
            }
            
            $scope.resetDates = function () {
                delete $state.params.bdate;
                delete $state.params.edate;
                $scope.start=null;
                $scope.end=null;
                update();
            }

            $scope.applyDatesAndSummary = function () {
                var m = $scope.start.getMonth() + 1;
                var s = $scope.start.getFullYear() + "-" + m + "-" + $scope.start.getDate();
                m = $scope.end.getMonth() + 1;
                var e = $scope.end.getFullYear() + "-" + m + "-" + $scope.end.getDate();

                $http.get("api/activities/" + $rootScope.user.staffId + "/" + s + "_" + e
                        ).then(function successCallback(response) {


                    $scope.activities = response.data;
                    var m = new Map();
                    for (var i = 0; i < $scope.activities.length; i++) {
                        var elem = $scope.activities[i];
                        if (m.has(elem.proj.name)) {
                            var value = m.get(elem.proj.name);
                            if (elem.typeId == 5) {
                                value.nhours += elem.hours;
                            } else {
                                value.hours += elem.hours;
                            }
                            m.set(elem.proj.name, value);
                        } else {
                            var value = new Object();
                            if (elem.typeId == 5) {
                                value.nhours = elem.hours;
                                value.hours = 0;
                            } else {
                                value.hours = elem.hours;
                                value.nhours = 0;
                            }
                            m.set(elem.proj.name, value);
                        }
                    }

                    var obj = [];
                    m.forEach(function (value, key) {
                        var temp = new Object();
                        temp.key = key;
                        temp.value = value;
                        obj.push(temp);
                    });

                    $scope.activities = obj;
                }, function errorCallback(response) {
                });

            }

            $scope.predicate = 'date';
            $scope.reverse = true;

            $scope.reorder = function (predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            };

            $scope.delete = function (a) {
                if (a.submitted) {
                    $scope.show.message = "This data is already subitted!";
                    $scope.show.style = $scope.show.errorStyle;
                    $scope.show.show = true;
                    return;
                }
                $http.delete("api/activities/" + a.id).success(function () {

                    update();
                });
            }

            $scope.edit = function (a) {
                if (a.submitted){
                    $scope.show.message = "This data is already subitted!";
                    $scope.show.style = $scope.show.errorStyle;
                    $scope.show.show = true;
                    return;
                }
                $scope.show.moduleWin =true;
                $scope.projid = a.proj.id;
                $scope.hours = a.hours;
                if (a.typeId == 5)
                    $scope.type = true;
                else
                    $scope.type = false;
                $scope.desc = a.description;
                $scope.note = a.note;
                $scope.place = a.place;
                $scope.act_id = a.id;
                $scope.date = new Date(a.date);
            }

            $scope.save = function () {
                var a = new Activity();
                a.id = $scope.act_id;
                a.empl = {"id": $rootScope.user.staffId};
                a.proj = {"id": $scope.projid};
                a.hours = $scope.hours;
                a.date = $scope.date;
                a.note = $scope.note;
                a.place = $scope.place;
                a.submitted = false;
                if ($scope.type)
                    a.typeId = 5;
                else
                    a.typeId = 6;
                a.description = $scope.desc;
                $http.put("api/activities/", a).then(
                        function successCallback() {
                            hide();
                            update();

                        }, function errorCallback() {
                    alert("error occured");
                });

            }



            function hide() {
                $scope.show.moduleWin=false;
            }

            $scope.hide = hide;



            loadProjects = function () {
                $scope.show.loading = true;
                $http.get("/api/projects").success(function (response) {
                    $localStorage.projects = response;
                });
                $http.get("/api/companies/clients").success(
                    function(response){
                        $scope.clients = response;
                    }
                );
                $scope.show.loading = false;
                $scope.projects = $localStorage.projects;
            }
            if ($localStorage.projects == undefined) {
                loadProjects();
            }
            $scope.loadProjects = loadProjects;


            $scope.loadManaged = function loadManaged(id) {
                $http.get("/api/projects/pm/" + $rootScope.user.staffId).success(
                        function (response) {
                            $scope.mngproj = response;
                        }
                );
            }

            $scope.info = info;
            function info() {
                $http({
                    method: "GET",
                    url: "api/activities/",
                    params: {
                        projid: $rootScope.pid
                    }
                }).success(function (response) {
                    if (response.length == 0)
                        return;
                    var map = new Map();
                    response[0].proj.status=="Attivo"?
                        $scope.show.closeButton = true :
                            $scope.show.closeButton = false;
                    
                    for (var i = 0; i < response.length; i++) {
                        var name = response[i].empl.name + " " 
                                + response[i].empl.sname;
                        if (map.has(response[i].empl.id)) {
                            var value = map.get(response[i].empl.id);
                            if (response[i].typeId == 5) {
                                value.nhours += response[i].hours;
                            } else {
                                value.hours += response[i].hours;
                            }
                            map.set(response[i].empl.id, value);
                        } else {
                            var value = new Object();
                            if (response[i].typeId == 5) {
                                value.nhours = response[i].hours;
                                value.hours = 0;
                                value.name = name;
                            } else {
                                value.hours = response[i].hours;
                                value.nhours = 0;
                                value.name = name;
                            }
                            map.set(response[i].empl.id, value)
                        }
                    }
                    var obj = [];
                    map.forEach(function (value, key) {
                        var temp = new Object();
                        temp.key = key;
                        temp.value = value;
                        obj.push(temp);
                    });
                    $scope.activities = obj;
                    console.log(obj);
                });
            }

            var summary = function () {

                $http.get("api/activities/" + $rootScope.user.staffId).success(function (response) {
                    $scope.activities = response;
                    var m = new Map();
                    for (var i = 0; i < $scope.activities.length; i++) {
                        var elem = $scope.activities[i];
                        if (m.has(elem.proj.name)) {
                            var value = m.get(elem.proj.name);
                            if (elem.typeId == 5) {
                                value.nhours += elem.hours;
                            } else {
                                value.hours += elem.hours;
                            }
                            m.set(elem.proj.name, value);
                        } else {
                            var value = new Object();
                            if (elem.typeId == 5) {
                                value.nhours = elem.hours;
                                value.hours = 0;
                            } else {
                                value.hours = elem.hours;
                                value.nhours = 0;
                            }
                            m.set(elem.proj.name, value);
                        }
                    }

                    var obj = [];
                    m.forEach(function (value, key) {
                        var temp = new Object();
                        temp.key = key;
                        temp.value = value;
                        obj.push(temp);
                    });

                    $scope.activities = obj;
                })

            }
            
            $scope.closeProject = function(){
                $http.get("api/projects/details/"+$rootScope.pid).success(
                        function(response){
                           $scope.project = response;
                           $scope.project.status="Chiuso";
                           $http.post("api/projects",$scope.project).success(
                                   function(){
                                        
                                   }
                            );
                        }
                );
            }
            
            $scope.summary = summary;
            $scope.setpid = function (pid) {
                $rootScope.pid = pid;
                $state.go("/act.projectinfo");

            }

            
            $scope.validate = function () {
                var date = new Date($scope.date);
                if ((date.getDay() == 6 || date.getDay() == 0) && !$scope.fillseveraldates) {
                    $scope.show.message = "You have selected a day off";
                    $scope.show.style = $scope.show.warningStyle;
                    $scope.show.show = true;
                    return;
                } else {
                    var p = $filter('filter')($localStorage.projects, {id: $scope.projid},
                    function (actual, expected) {
                        if (actual == expected)
                            return true;
                        return false;
                    }
                    );
                    if (p[0].status != "Attivo") {
                        $scope.show.message = "You have selected project that is closed";
                        $scope.show.style = $scope.show.warningStyle;
                        $scope.show.show = true;
                        return;
                    }
                }
                $scope.show.show = false;

            }

            $scope.detailedSummary = function (name) {
                $state.go("/act.watch",
                        {name: name, bdate: $scope.start, edate: $scope.end});
            }

            $scope.filterProjects = function(){
                if ($scope.client.length>3)
                $scope.projects = $filter('filter')($localStorage.projects, {client:{name:$scope.client}});
                //$scope.$apply();
            }
            
            function verifyInput(){
                if ($scope.projid==null || $scope.hours==null || $scope.hours=="" ||
                        (!$scope.fillseveraldates && $scope.date==null) ||
                        (($scope.fillseveraldates!=undefined || $scope.fillseveraldates==true)
                        && ($scope.toDate ==null || 
                        $scope.fromDate==null))) {
                            $scope.show.message = "Some mandatory fields are not filled";
                            $scope.show.style = $scope.show.errorStyle;
                            $scope.show.show= true;
                            return false;
                        } else if ($scope.hours<0 || $scope.hours>8) {
                            $scope.show.message = "Illegal input on hours";
                            $scope.show.style = $scope.show.errorStyle;
                            $scope.show.show= true;
                            return false;
                        }
                        return true;
            }
            
            $scope.report = function() {
                var m = $scope.start.getMonth() + 1;
                var s = $scope.start.getFullYear() + "-" + m + "-" + $scope.start.getDate();
                m = $scope.end.getMonth() + 1;
                var e = $scope.end.getFullYear() + "-" + m + "-" + $scope.end.getDate();
                $http({
                    method:'GET',
                    url:"api/reports/"+$rootScope.user.staffId,
                    params: {
                        start:s,
                        end: e
                    }
                })
            }
            
            $scope.assureSubmit = function(){
                $scope.show.message="Are you sure you want to submit data for"
                            +$scope.curmonth+"?\n Data will be no longer editable";
               
                $scope.show.style=$scope.show.warningStyle;
                $scope.show.show = true;
                $scope.show.submit = true;
            }
            
            $scope.submit = function(){
                var d =  new Date();
                $http.put("api/activities/"
                        +$rootScope.user.staffId+"/"+d.getMonth())
                        .success(function(response){
                            $scope.show.message = "Data submitted!";
                            $scope.show.style=$scope.show.successStyle;
                            $scope.show.show = true;
                            $scope.show.submit = false;
                            update();
                        });
                
            }
            
            $scope.submitSingle = function(a){
                $http.patch("api/activities/"+a.id)
                        .success(function(response){
                            $scope.show.message = "Data submitted!";
                            $scope.show.style=$scope.show.successStyle;
                            $scope.show.show = true;
                            $scope.show.submit = false;
                        });
                update();
            }
            
            $scope.initMonths  =function(){
                var d  = new Date();
                $scope.months = months;
                $scope.month = $scope.months[d.getMonth()];
                $scope.year = d.getFullYear();
            }
            
            $scope.applyMonth = function(){
                var m = document.getElementById("month").selectedIndex;
                m++;
                var b = $scope.year+"-"+m+"-1";
                m++;
                if (m>12) {
                    m =1;
                    $scope.year++;
                }
                var e = $scope.year+"-"+m+"-1";
                $http.get("api/activities/"+$rootScope.user.staffId+"/"+b+"_"+e)
                        .success(function(response){
                            $scope.activities = response;
                        })
            }
            $scope.projects = $localStorage.projects;


        })
