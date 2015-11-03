module.factory('Activity', function ($resource) {
    return $resource(":userid/a", {userid: '@userid'});
})
        .controller('ActivityController', function ($scope, Activity, $localStorage, $state, $http) {
            var url = function () {
                return {userid: $localStorage.userid};
            }

            var update = function () {
                console.log(url());
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
                a.proj = {"id": $scope.projid};
                a.hours = $scope.hours;
                a.date = $scope.date;
                a.note = $scope.note;
                a.place = $scope.place;
                a.description = $scope.desc;
                alert($scope.desc);
                a.$save(url(), update());
                update();
            }
            
            $scope.applyDates = function(){
                console.log($scope.start.getDate());
                var s = $scope.start.getFullYear()+"-"+$scope.start.getMonth()+"-"+$scope.start.getDate();
                var e = $scope.end.getFullYear()+"-"+$scope.end.getMonth()+"-"+$scope.end.getDate();
                
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


            update();

            $scope.ac_win_show = false;
        })
