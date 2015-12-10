var module = angular.module('Rapportino', ['ngResource',
    'ngStorage', 'ui.router', 'angularUtils.directives.dirPagination']);

module.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');
    $stateProvider.state("/", {
        url: "/",
        templateUrl: "login.html",
        controller: "UserController"

    }).state("/err", {
        url: "/err",
        templateUrl: "err.html",
        controller: "ErrorController"

    }).state("/act", {
        url: "/act",
        templateUrl: "act.html",
        controller: "ActivityController"
    }).state("/act.fill", {
        url: "/fill",
        templateUrl: "act-fill.html",
        controller: "ActivityController"
    }).state("/act.watch", {
        url: "/watch",
        templateUrl: "act-watch.html",
        controller: "ActivityController",
        params : {
            name:"",
            bdate: null,
            edate: null
        }
    }).state("/act.watchs", {
        url: "/mysummary",
        templateUrl: "act-watch-summary.html",
        controller: "ActivityController"
    }).state("/act.manp", {
        url: "/managed",
        templateUrl: "act-managed.html",
        controller: "ActivityController"
    }).state("/act.projectinfo", {
        url: "/info",
        templateUrl: "proj-info.html",
        controller: "ActivityController"
    }).state("/act.personal", {
        url: "/personal",
        templateUrl: "act-personal.html",
        controller: "StaffController"
    }).state("/cp", {
        url: "/controlPanel",
        templateUrl: "cp.html",
        controller: "AdminController"
    }).state("/cp.staff", {
        url: "staff",
        templateUrl: "cp-staff.html",
        controller: "AdminController"
    }).state("/cp.infostaff", {
        url: "staff/info",
        templateUrl: "cp-staff-info.html",
        controller: "AdminController"
    }).state("/cp.projects", {
        url: "projects",
        templateUrl: "cp-projects.html",
        controller: "AdminController",
        params : {
            query:""
        }
    }).state("/cp.infoprojects", {
        url: "projects/info",
        templateUrl: "cp-projects-info.html",
        controller: "AdminController"
    }).state("/cp.clients", {
        url: "clients",
        templateUrl: "cp-clients.html",
        controller: "AdminController"
    }).state("/cp.companies", {
        url: "companies",
        templateUrl: "cp-companies.html",
        controller: "AdminController"
    }).state("/cp.divisions", {
        url: "divisions",
        templateUrl: "cp-divisions.html",
        controller: "AdminController"
    }).state("/cp.types", {
        url: "types",
        templateUrl: "cp-types.html",
        controller: "AdminController"
    }).state("/cp.act", {
        url: "activities",
        templateUrl: "cp-activities.html",
        controller: "AdminController"
    }).state("/cp.reports", {
        url: "reports",
        templateUrl: "cp-reports.html",
        controller: "AdminController"
    }).state("/cp.reports.staff", {
        url: "staff",
        templateUrl: "cp-reports-staff.html",
        controller: "AdminController"
    }).state("/cp.reports.proj", {
        url: "projects",
        templateUrl: "cp-reports-proj.html",
        controller: "AdminController"
    });
});




module.controller('UserController', function ($scope, $http, $state, $localStorage, $rootScope) {
    $scope.gui =  new Object();
    $scope.gui.show = false;
    $scope.gui.style= {};
    $scope.message="";
    $scope.login = login;
    function login() {
        $http({
            method: 'POST',
            url: '/login',
            params: {
                username: $scope.username,
                password: $scope.pass
            }
        }).then(function () {
            $http.get("users/cur").then(
                    function successCallback(response) {
                        if (response.data.user) {
                            $rootScope.authorized = true;
                            $rootScope.user = new Object();
                            $rootScope.user.staffId = response.data.user.staffId;
                            $rootScope.user.scope = response.data.user.scope;
                            if ($rootScope.user.scope=="ROLE_ADM") $state.go("/cp");
                            else $state.go("/act");
                        } else{
                            $rootScope.authorized = false;
                        }
                            
                    }, function failCallback() {
                        $rootScope.authorized = false;
            });
        }, function errorCallback(){
            $scope.gui.message = "Wrong username or password!";
            $scope.gui.style={'background-color': '#FF8080', 'padding': '5px', "border": "2px solid red", "text-align": "center"};
            $scope.gui.show = true;
        });


    }

    $scope.logout = function logout() {
        $rootScope.authorized = false;
        delete $rootScope.user;
        $localStorage.$reset();
        $state.go("/");
    }
})


module.controller('ErrorController', function ($scope, $localStorage, $state, $http, $routeParams) {
    var errCode = $routeParams.code;
    console.log($routeParams);
    console.log(errCode);
    $http({
        method: 'GET',
        url: "err/" + errCode
    }).then(function successCallback(response) {
        console.log("suc");
        $scope.errmsg = response.data.msg;
        $localStorage.err = null;
    }, function errCallback(response) {
        console.log(response);
    });
    //$localStorage.err=null;
})
// error codes  - 0 - incorrect login or password
// 1 - no access


