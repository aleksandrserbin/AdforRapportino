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
                a.empl={"id":$scope.userid};
                console.log($scope.projid);
                a.proj={"id":$scope.projid};
                a.hours = $scope.hours;
                a.date = $scope.date;
                a.$save(url(),update());
                
                update();
                
            }

            update();
        })


