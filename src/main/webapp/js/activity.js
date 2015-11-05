module.factory('Activity', function ($resource) {
    return $resource(":userid/a", {userid: '@userid'});
})
        .controller('ActivityController', function ($scope, Activity, $localStorage, $state, $http) {
            
           
            var url = function () {
                return {userid: $localStorage.userid};
            }

            var update = function () {
                if ($localStorage.userid == null || $localStorage.userid == undefined)
                {
                    $state.go("/err");
                    $localStorage.err = 1;
                }
                $scope.activities = Activity.query(url());
            }

            $scope.update = update;

            $scope.add = function addActivity() {
                if ($localStorage.rights < 1) {
                    alert("you have no access to writing information\n Contact your administator");
                    return;
                }
                var a = new Activity();
                a.empl = {"id": $localStorage.userid};
                a.proj = {"id": $scope.projid.split(" - ")[0]};
                a.hours = $scope.hours;
                a.date = $scope.date;
                a.note = $scope.note;
                a.place = $scope.place;
                a.description = $scope.desc;
                if ($scope.type) a.typeId = 5; else a. typeId = 6;
                a.$save(url(), update());
                update();
            }
            
            $scope.applyDates = function(){
                console.log($scope.start.getDate());
                var  m = $scope.start.getMonth()+1;
                var s = $scope.start.getFullYear()+"-"+m+"-"+$scope.start.getDate();
                m = $scope.end.getMonth()+1;
                var e = $scope.end.getFullYear()+"-"+m+"-"+$scope.end.getDate();
                
                $http.get($localStorage.userid+"/a/"+s+"_"+e
                        ).then(function successCallback(response){
                            console.log(response);
                            alert($localStorage.userid+"/a/"+
                                s+"_"+e);
                            $scope.activities = response.data;
                        }, function errorCallback(response){
                            alert($localStorage.userid+"/a/"+
                                s+"_"+e);
                        });
                update();
            }
            
            
            $scope.predicate = 'date';
            $scope.reverse = true;
            $scope.reorder = function (predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            };

            $scope.delete = function(id){
                $http.delete($localStorage.userid+"/a/"+id).success(function(){
                    
                    update();
                });
            }
            
            $scope.edit = function(a){
                var mwin = document.getElementsByClassName("module-win")[0];
                mwin.style.display="inline-block";
                $scope.projid = a.proj.id;
                $scope.hours = a.hours;
                if (a.typeId==5) $scope.type = true; else $scope.type=false;
                $scope.desc = a.description;
                $scope.note = a.note;
                $scope.place = a.place;
                $scope.act_id = a.id;
                $scope.date = new Date(a.date);
            }
            
            $scope.save = function(){
                var a = new Activity();
                a.id = $scope.act_id;
                a.empl = {"id": $localStorage.userid};
                a.proj = {"id": $scope.projid};
                a.hours = $scope.hours;
                a.date = $scope.date;
                a.note = $scope.note;
                a.place = $scope.place;
                if ($scope.type) a.typeId = 5; else a. typeId = 6;
                a.description = $scope.desc;
                $http.put($localStorage.userid+'/a',a).then(
                        function successCallback(){
                            hide();
                            update();
                            
                        }, function errorCallback(){
                            alert("error occured");
                        });
                
            }
            
            update();
            
            function hide(){
                document.getElementsByClassName("module-win")[0].style.display="none";
            }
            
            $scope.hide = hide;
            
            $http.get("/p/all").success(function(response){
                if ($localStorage.projects==undefined){
                    $localStorage.projects = response;
                    
                }
                $scope.projects = $localStorage.projects;
            });
            
            $scope.loadManaged = function loadManaged(id){
                $http.get("/p/"+$localStorage.userid).success(
                        function (response) {
                            $scope.mngproj = response;
                        }
                        );
            }
            
            $scope.info = info;
            function info(id){
                $http.get("p/info/"+id).success(function(response){
                    var map =  new Map();
                    for (var i =0;i<response.length;i++){
                        if (map.has(response[i].empl)){
                            var h = map.get(response[i].empl);
                            alert("h = "+h);
                            map.set(response[i].empl,h+response[i].hours);
                        } else {
                            map.set(response[i].empl,response[i].hours)
                        }
                    }
                    var obj = [];
                    map.forEach(function(value,key){
                        var temp =  new Object();
                        temp.key = key;
                        temp.value=value;
                        obj.push(temp);
                    });
                    $scope.projectActivities = obj; 
                    console.log($scope.projectActivities);
                    $state.go("/act.projectinfo");
                });
            }
            
        })
