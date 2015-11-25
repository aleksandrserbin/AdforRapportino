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
            bdate: new Date(new Date().getFullYear(),new Date().getMonth(),1),
            edate: new Date(new Date().getFullYear(),new Date().getMonth()+1,1)
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
    });
});




module.controller('UserController', function ($scope, $http, $state, $localStorage, $rootScope) {


    $scope.login = login;
    function login() {
        $http({
            method: 'POST',
            url: '/login',
            params: {
                username: $scope.username,
                password: $scope.pass
            }
        }).success(function () {
            $http.get("users/cur").then(
                    function successCallback(response) {
                        if (response.data.user) {
                            $rootScope.authorized = true;
                            $rootScope.user = new Object();
                            $rootScope.user.staffId = response.data.user.staffId;
                            $rootScope.user.scope = response.data.user.scope;
                            $state.go("/act");
                        } else{
                            $rootScope.authorized = false;
                        }
                            
                    }, function failCallback() {
                $rootScope.authorized = false;
            });




        });


    }

    $scope.logout = function logout() {
        $localStorage.rights = null;
        $localStorage.userid = null;
        $localStorage.$reset();
        $state.go("/");
    }
})


module.controller('ErrorController', function ($scope, $localStorage, $state, $http) {
    var errCode = $localStorage.err;
    console.log(errCode);

    $http({
        method: 'GET',
        url: "err/" + errCode
    }).then(function successCallback(response) {

        $scope.errmsg = response.data.msg;
        $localStorage.err = null;
    }, function errCallback(response) {
        console.log(response);
    });
    //$localStorage.err=null;
})
// error codes  - 0 - incorrect login or password
// 1 - no access


