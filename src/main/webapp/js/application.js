var module = angular.module('Rapportino', ['ngResource', 'ngRoute', 'ngStorage']);

module.config(function ($routeProvider) {
    $routeProvider.when("/", {
        templateUrl: "login.html",
        controller: "UserController"

    }).when("/err", {
        templateUrl: "err.html",
        controller: "ErrorController"

    }).when("/act", {
        templateUrl: "act.html",
        controller: "ActivityController"
    });
});




module.controller('UserController', function ($scope,  $http, $location, $localStorage) {


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
                $location.path("/err");
            } else
            if ($scope.userid != null && $scope.rights >= 32)
                $location.path("/cp"); else
            $location.path("/act");

        }, function errorCallback(response) {
            console.log(response);
        });
    }
    
    $scope.logout = function logout(){
        $localStorage.rights=null;
        $localStorage.userid=null;
        $location.path("/");
    }
})


module.controller('ErrorController', function($scope, $localStorage, $location, $http){
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


