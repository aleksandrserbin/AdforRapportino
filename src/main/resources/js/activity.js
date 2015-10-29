var module = angular.module('Rapportino', ['ngResource']);

module.factory('Activity', function ($resource) {
    return $resource(":userid/a", {userid: '@userid'});
})
        .controller('ActivityController', function ($scope, Activity) {
            var url = function () {
                return {userid: $scope.userid};
            }
            var update = function(){
                $scope.activities= Activity.query(url());
            }
            $scope.update = update;
            $scope.add = function(){
                var activity =  new Activity();
                activity.empl = $scope.userid;
                activity.hours = $scope.hours;
                activity.proj = $scope.projectid;
                activity.date= $scope.date;
                activity.$save(url(),function(){
                    update();
                })
            }
            
            update();
        })


