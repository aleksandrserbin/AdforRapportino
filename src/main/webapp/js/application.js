var module = angular.module('Rapportino', ['ngResource',  'ngStorage','ui.router']);

module.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/act');
    $stateProvider.state("/", {
        url:"/",
        templateUrl: "login.html",
        controller: "UserController"

    }).state("/err", {
        url:"/err",
        templateUrl: "err.html",
        controller: "ErrorController"

    }).state("/act", {
        url:"/act",
        templateUrl: "act.html",
        controller: "ActivityController"
    }).state("/act.fill",{
        url:"/act/fill",
        templateUrl: "act-fill.html",
        controller: "ActivityController"
    });
});




module.controller('UserController', function ($scope,  $http, $state, $localStorage) {


    $scope.login = login;
    function login() {
        $http({
            method: 'GET',
            url: '/users',
            params: {
                username: $scope.username,
                password: $scope.pass
            }
        }).then(function successCallback(response) {
            $scope.userid = response.data.staff_id;
            $scope.rights = response.data.scope;
            $scope.$storage = $localStorage;
            $localStorage.userid=$scope.userid;
            $localStorage.rights = $scope.rights; 
            if ($scope.userid == undefined && $scope.rights == undefined)
            {
                $localStorage.err=0;
                $state.go("/err");
            } else
            if ($scope.userid != null && $scope.rights >= 32)
                $state.go("/cp"); else
            $state.go("/act");

        }, function errorCallback(response) {
            console.log(response);
        });
    }
    
    $scope.logout = function logout(){
        $localStorage.rights=null;
        $localStorage.userid=null;
        $state.go("/");
    }
})


module.controller('ErrorController', function($scope, $localStorage, $state, $http){
    var errCode = $localStorage.err;
    console.log(errCode);
    
    $http({
            method: 'GET',
            url: "err/"+errCode
        }).then(function successCallback(response){
            
            $scope.errmsg = response.data.msg;
            
        }, function errCallback(response){
            console.log(response);
        });
        //$localStorage.err=null;
})
// error codes  - 0 - incorrect login or password
// 1 - no access


