var module = angular.module('Rapportino', ['ngResource', 'ngRoute', 'ngStorage']);

module.config(function ($routeProvider) {
    $routeProvider.when("/", {
        templateUrl: "login.html",
        controller: "UserController"

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
            
            $scope.$storage = $localStorage.$default({
                userid: response.data.staff_id,
                rights: response.data.scope
            });
            if ($scope.userid == null && $scope.rights < 32)
                $location.path("/err?code=0");
            if ($scope.userid != null || $scope.rights >= 32)
                $location.path("/cp");
            $location.path("/act");

        }, function errorCallback(response) {
            alert("err");
        });
    }
    
    $scope.logout = function logout(){
        $localStorage.rights=null;
        $localStorage.userid=null;
        $location.path("/");
    }
})

// error codes  - 0 - incorrect login or password
// 1 - no access


