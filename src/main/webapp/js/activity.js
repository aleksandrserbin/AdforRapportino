module.factory('Activity', function ($resource) {
    return $resource(":userid/a", {userid: '@userid'});
})
        .controller('ActivityController', function ($scope, Activity, $localStorage, $state) {
            var url = function () {
                return {userid: $scope.userid || 7};
            }
            
            var update = function () {
                console.log($localStorage);
                if ($localStorage.userid==null || $localStorage.userid==undefined)
                 {   
                     $state.go("/err");
                     $localStorage.err=1;
                 } else console.log($localStorage.userid);
                $scope.activities = Activity.query(url());
            }
            
            $scope.update = update;
            
            $scope.add = function addActivity() {
                if ($localStorage.rights<1) {
                    alert("you have no access to writing information\n Contact your administator");
                    return;
                }
                var a = new Activity();
                a.empl = {"id": $scope.userid};
                a.proj = {"id": $scope.projid};
                a.hours = $scope.hours;
                a.date = $scope.date;
                a.$save(url(), update());
                update();
            }
            
            $scope.navigate = function(path){
                switch (path) {
                    case "fill" : 
                }
            }
            
            
            update();

            $scope.ac_win_show = false;
        })
