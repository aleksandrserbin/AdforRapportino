module.controller('AdminController', function ($scope, $rootScope, $http, $state, $localStorage, $window) {

    /*
     *         init
     */

    init();
    function init() {
        if (!$rootScope.user.scope == "ROLE_ADM")
            $state.go("/");
        if (!$localStorage.companies)
            initCompanies();
        $scope.msg = msg;
        $scope.show = new Object();
        $scope.show.moduleWin = false;
        $scope.show.show = false;
        $scope.show.showFilters = false;
        $scope.show.message = "";
        $scope.show.style = {};
        $scope.show.errorStyle = {'background-color': '#FF8080', 'padding': '5px', "border": "2px solid red", "text-align": "center"};
        $scope.show.successStyle = {'background-color': '#81E383', 'padding': '5px', "border": "2px solid green", "text-align": "center"};
        $scope.show.warningStyle = {'background-color': 'yellow', 'padding': '5px', "border": "2px solid #D6D300", "text-align": "center"};
    }

    $scope.initStaff = initStaff;
    function initStaff() {
        $http.get("staff").success(function (response) {
            $scope.staff = response;
        })
    }
    $scope.initClients = initClients;
    function initClients() {
        $http.get("api/companies/clients").success(
                function (response) {
                    $scope.clients = response;
                });
    }
    $scope.initProjects = initProjects;
    function initProjects() {
        $http.get("api/projects").success(
                function (response) {
                    $scope.projects = response;
                    $scope.client = $state.params.query;
                }
        );
    }

    $scope.initCompanies = initCompanies;
    function initCompanies() {
        $http.get("api/companies").success(function (response) {
            $localStorage.companies = response;
            $scope.companies = $localStorage.companies;
        });
    }
    $scope.initDivisions = initDivisions;
    function initDivisions() {
        $http.get("api/companies/divisions").success(
                function (response) {
                    $scope.divisions = response;
                });
    }

    $scope.initTypes = initTypes;
    function initTypes() {
        $http.get("api/projects/types").success(
                function (response) {
                    $scope.types = response;
                });
    }

    $scope.initActivities = initActivities;
    function initActivities() {
        initStaff();
    }
    $scope.initReports = function () {
        initStaff();
        initProjects();
        $scope.show.dateSelector = false;
        $scope.months = months;
        var d = new Date();
        $scope.month = $scope.months[d.getMonth()];
        $scope.year = d.getFullYear();
    }

    $scope.setid = function (id, uri) {
        $rootScope.resId = id;
        $state.go("/cp.info" + uri);
    }

    $scope.staffInfo = function () {
        if ($rootScope.resId != null) {
            $http.get("staff/" + $rootScope.resId).success(function (response) {
                $scope.staff = response;
                $scope.company = response.company.id;
            }).success(function () {
                $http.get("users/" + $rootScope.resId).success(function (response) {
                    $scope.staff.username = response.username;
                    $scope.staff.scope = response.scope;
                });
            });
        }

    }

    $scope.saveStaff = function () {
        var u = new Object();
        u.username = $scope.staff.username;
        u.scope = $scope.staff.scope;
        $scope.staff.company = {id: $scope.company};
        delete $scope.staff.username;
        delete $scope.staff.scope;
        $http.put("staff", $scope.staff).success(function (response) {
            u.staffId = response;
            $http.put("users/add", u).success(
                    function (response) {
                        $scope.show.message = "Data saved";
                        $scope.show.style = $scope.show.successStyle;
                        $scope.show.show = true;
                    });
        });
    }
    $scope.deleteStaff = function (s) {
        confirm("Are you sure you want to delete " + s.sname + "?");
        $http.delete("staff/" + s.id).success(
                function () {
                    $http.delete("users/" + s.id).success(
                            function () {
                                $scope.show.message = s.sname + " was deleted";
                                $scope.show.style = $scope.show.warningStyle;
                                $scope.show.show = true;
                                initStaff();
                            }
                    );

                }
        );
    }

    $scope.resetPassword = function () {
        $http.put("users/" + $rootScope.resId);
    }

    $scope.projectInfo = function () {
        initStaff();
        initClients();
        initDivisions();
        if ($rootScope.resId != null) {
            $scope.data = new Object();
            $http.get("api/projects/details/" + $rootScope.resId)
                    .success(function (response) {
                        $scope.project = response;
                        if ($scope.project.begin != null)
                            $scope.begin = new Date($scope.project.begin);
                        if ($scope.project.end != null)
                            $scope.end = new Date($scope.project.end);
                        setTimeout(function(){
                            document.getElementById("status").value = $scope.project.status;
                            document.getElementById("client").value = $scope.project.client.id;
                            document.getElementById("cm").value = $scope.project.cm.id;
                            document.getElementById("pm").value = $scope.project.pm.id;
                            document.getElementById("divis").value = $scope.project.division.id;
                            document.getElementById("type").value = $scope.project.type.id;
                        },750);
                        
                    });
        }

    }

    $scope.saveProject = function () {
        var p = new Object();
        //check id
        $http.get("api/projects/" + $scope.project.id).success(
                function (response) {
                    p.id = $scope.project.id;
                    p.name = $scope.project.name;
                    p.status = document.getElementById("status").value;
                    p.cm = {id: document.getElementById("cm").value};
                    p.pm = {id: document.getElementById("pm").value};
                    p.client = {id: document.getElementById("client").value};
                    p.division = {id: document.getElementById("divis").value};
                    p.type = {id: document.getElementById("type").value};
                    p.begin = new Date($scope.begin);
                    p.end = new Date($scope.end);
                    $http.post("api/projects", p).success(
                            function (response) {
                                $scope.show.message = "Data saved";
                                $scope.show.style = $scope.show.successStyle;
                                $scope.show.show = true;
                            });

                }
        );
    }
    $scope.deleteProject = function (s) {
        confirm("Are you sure you want to delete " + s.sname + "?");
        $http.delete("api/projects/" + s.id).success(
                function () {
                    $scope.show.message = s.sname + " was deleted";
                    $scope.show.style = $scope.show.warningStyle;
                    $scope.show.show = true;
                    initProjects();
                });
    }
    $scope.saveClient = function () {
        $http.post("api/companies/clients", $scope.client).then(
                function (response) {
                    $scope.show.moduleWin = false;
                    initClients();
                }, function (response) {
            $scope.show.message = "Data was not saved due to error\n Check your input and try again";
            $scope.show.style = $scope.show.errorStyle;
            $scope.show.show = true;

        });
    }

    $scope.showEditClients = function (res) {
        if (res != null)
            $scope.client = res;
        else
            $scope.client = new Object();
        $scope.show.moduleWin = true;
    }

    $scope.toClientProjects = function (q) {
        $state.go("/cp.projects", {query: q});
    }

    $scope.deleteClient = function () {
        $http.delete("api/companies/clients/" + $scope.client.id).success(function (response) {
            $scope.show.moduleWin = false;
            initClients();
        });
    }

    $scope.showEditCompanies = function (res) {
        if (res != null)
            $scope.company = res;
        else
            $scope.company = new Object();
        $scope.show.moduleWin = true;
    }

    $scope.saveCompany = function () {
        $http.post("api/companies", $scope.company).then(
                function (response) {
                    $scope.show.moduleWin = false;
                    initCompanies();
                }, function (response) {
            $scope.show.message = "Data was not saved due to error\n Check your input and try again";
            $scope.show.style = $scope.show.errorStyle;
            $scope.show.show = true;
        });
    }
    $scope.deleteCompany = function () {
        $http({
            method: "DELETE",
            url: "api/companies",
            params: {
                id: $scope.company.id
            }
        }).success(function () {
            initCompanies();
            $scope.show.moduleWin = false;
        });

    }

    $scope.showEditDivisions = function (res) {
        if (res != null)
            $scope.division = res;
        else
            $scope.division = new Object();
        $scope.show.moduleWin = true;
    }

    $scope.saveDivision = function () {
        $http.post("api/companies/divisions", $scope.division).then(
                function (response) {
                    $scope.show.moduleWin = false;
                    initDivisions();
                }, function (response) {
            $scope.show.message = "Data was not saved due to error\n Check your input and try again";
            $scope.show.style = $scope.show.errorStyle;
            $scope.show.show = true;
        });
    }
    $scope.deleteDivision = function () {
        $http({
            method: "DELETE",
            url: "api/companies/divisions",
            params: {
                id: $scope.division.id
            }
        }).success(function () {
            initDivisions();
            $scope.show.moduleWin = false;
        });
    }

    $scope.showEditTypes = function (res) {
        if (res != null)
            $scope.type = res;
        else
            $scope.type = new Object();
        $scope.show.moduleWin = true;
    }

    $scope.saveType = function () {
        $http.post("api/projects/types", $scope.type).then(
                function (response) {
                    $scope.show.moduleWin = false;
                    initTypes();
                }, function (response) {
            $scope.show.message = "Data was not saved due to error\n Check your input and try again";
            $scope.show.style = $scope.show.errorStyle;
            $scope.show.show = true;
            ;
        });
    }
    $scope.deleteType = function () {
        $http({
            method: "DELETE",
            url: "api/projects/types",
            params: {
                id: $scope.type.id
            }
        }).success(function () {
            initTypes();
            $scope.show.moduleWin = false;
        });
    }

    $scope.reportStaff = reportStaff;
    function reportStaff(id, type) {
        var d = new Date();
        $window.open("api/reports/staff/" + id + "/" + d.getMonth() + "/" + type,
                "_blank");
    }

    $scope.projectReport = function (applyDates) {
        if (!applyDates) {
            $http.get("api/reports/projects/" + $scope.project.id)
                    .success(function (response) {
                        if (response.startsWith("%PDF")) {
                            $scope.show.show = false;
                            $window.open("api/reports/projects/"
                                    + $scope.project.id, "_blank");
                        } else {
                            $scope.show.message = "No data";
                            $scope.show.style = $scope.show.errorStyle;
                            $scope.show.show = true;
                        }
                    })
        }
        else {
            var d = new Date();
            b = new Date(d.setDate(1)).toISOString().slice(0, 10);
            e = new Date(new Date(d.setMonth(d.getMonth() + 1)).setDate(1))
                    .toISOString().slice(0, 10);
            var params = "?begin=" + b + "&end=" + e;
            $http.get("api/reports/projects/" + $scope.project.id + params)
                    .success(function (response) {
                        if (response.startsWith("%PDF")) {
                            $scope.show.show = false;
                            $window.open(
                                    "api/reports/projects/" + $scope.project.id + params, "_blank");
                        } else {
                            $scope.show.message = "No data";
                            $scope.show.style = $scope.show.errorStyle;
                            $scope.show.show = true;
                        }

                    });
        }
    }

    $scope.loadActivities = loadActivities;
    function loadActivities() {
        $http.get("api/activities/submitted/" + $scope.emplselect)
                .success(function (response) {
                    $scope.activities = response;
                });
    }

    $scope.deleteActivity = function (id) {
        confirm("Are you sure you want to delete this activity?");
        $http.delete("api/activities/" + id).success(function () {
            $scope.show.message = "Activity was deleted";
            $scope.show.style = $scope.show.warningStyle;
            $scope.show.show = true;
            loadActivities();
        })
    }

    $scope.addActivity = function (shown) {
        if (shown == undefined) {
            $scope.show.moduleWin = true;
            return;
        } else {
            if ($scope.emplselect == null) {
                $scope.show.message = "Select Employee!";
                $scope.show.style = $scope.show.errorStyle;
                $scope.show.show = true;
                $scope.show.moduleWin = false;
                return;
            }
            $scope.activity.empl = {id: $scope.emplselect};
            $scope.activity.typeId = ($scope.activity.type) ? 5 : 6;
            $scope.activity.date = new Date($scope.activity.date);
            $scope.activity.submitted = true;
            $http.post("api/activities/", $scope.activity).then(function () {
                $scope.show.message = "Activity added";
                $scope.show.style = $scope.show.successStyle;
                $scope.show.show = true;
                loadActivities();
                $scope.show.moduleWin = false;
            }, function (response) {
                console.log(response);
            });
        }
    }


    $scope.applyDates = function () {
        //конец не включается
        var b = new Date($scope.start);
        b = new Date(b.setDate(b.getDate() + 1)).toISOString().slice(0, 10);
        var e = new Date($scope.end).toISOString().slice(0, 10);
        $http.get("api/activities/" + $scope.emplselect + "/" + b + "_" + e)
                .then(function (response) {
                    $scope.activities = response.data;
                }, function (response) {
                    console.log(response);
                })
    }


    $scope.staffReportHandler = function (type) {
        if (!$scope.show.dateSelector) {
            var m = document.getElementById("month").selectedIndex;
            m++;
            var b = $scope.year + "-" + m + "-1";
            m++;
            if (m > 12) {
                m = 1;
                $scope.year++;
            }
            var e = $scope.year + "-" + m + "-1";
            $window.open("api/reports/staff/" + $scope.staffs + "/"
                        + type + "?start=" + b +
                        "&end=" + e,
                        "_blank");
        } else {
            $http({
                method: "GET",
                url: "api/reports/staff/" + $scope.staffs + "/" + type,
                params: {
                    start: $scope.start.toISOString().slice(0, 10),
                    end: $scope.end.toISOString().slice(0, 10)
                }
            }).success(function () {
                $window.open("api/reports/staff/" + $scope.staffs + "/"
                        + type + "?start=" + $scope.start.toISOString().slice(0, 10) +
                        "&end=" + $scope.end.toISOString().slice(0, 10),
                        "_blank");
            });
        }
    }

    $scope.projectReportHandler = function (type) {
        if (!$scope.show.dateSelector) {
            var m = document.getElementById("month").selectedIndex;
            m++;
            var b = $scope.year + "-" + m + "-1";
            m++;
            if (m > 12) {
                m = 1;
                $scope.year++;
            }
            var e = $scope.year + "-" + m + "-1";
            $http({
                method: "GET",
                url: "api/reports/projects/" + $scope.project + "/" + type,
                params: {
                    start: b,
                    end: e
                }
            }).success(function () {
                $window.open("api/reports/projects/" + $scope.project + "/"
                        + type + "?start=" + b +
                        "&end=" + e,
                        "_blank");
            });
        } else {
            $http({
                method: "GET",
                url: "api/reports/projects/" + $scope.project + "/" + type,
                params: {
                    start: $scope.start.toISOString().slice(0, 10),
                    end: $scope.end.toISOString().slice(0, 10)
                }
            }).success(function () {
                $window.open("api/reports/projects/" + $scope.project + "/"
                        + type + "?start=" + $scope.start.toISOString().slice(0, 10) +
                        "&end=" + $scope.end.toISOString().slice(0, 10),
                        "_blank");
            });
        }
    }
    
    $scope.uncommit = function(id){
        $http.patch("api/activities/"+id+"/u").success(function(){
            loadActivities();
        })
    }
    
    $scope.resetDates = function(){
        $scope.start = null;
        $scope.end = null;
        loadActivities();
    }

})

