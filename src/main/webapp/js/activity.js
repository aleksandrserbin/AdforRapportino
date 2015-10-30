var module = angular.module('Rapportino', ['ngResource']);




module.factory('Activity', function ($resource) {
    return $resource(":userid/a", {userid: '@userid'});
})
        .controller('ActivityController', function ($scope, Activity) {
            var url = function () {
                return {userid: $scope.userid || 7};
            }
            var update = function () {
                $scope.activities = Activity.query(url());
            }
            $scope.update = update;
            $scope.add = function addActivity() {

                var a = new Activity();
                //ошибка т.к. empl :: Staff, а отправляем id
                a.empl = {"id": $scope.userid};
                console.log($scope.projid);
                a.proj = {"id": $scope.projid};
                a.hours = $scope.hours;
                a.date = $scope.date;
                a.$save(url(), update());

                update();

            }

            update();

            $scope.ac_win_show = false;
        })

module.controller('UserController', function ($scope, $http) {


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
            $scope.rights = response.data.scope;
            $scope.userid = response.data.staff_id;
            
            
        }, function errorCallback(response) {
            alert("err");
        });
    }
})

