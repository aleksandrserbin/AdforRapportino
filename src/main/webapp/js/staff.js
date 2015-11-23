module.controller('StaffController', function ($http, $scope, $localStorage, $rootScope) {
    init();
    
    function init() {
        $scope.show = new Object();
        $scope.show.show = false;
        $scope.show.style={};
        $scope.show.errorStyle = {'background-color':'red', 'padding':'5px'}
        $scope.show.successStyle = {'background-color':'green', 'padding':'5px'}
    }
    
    $scope.pull = function () {
        $http.get("/staff/" + $rootScope.user.staffId).success(
                function (response) {
                    var staff = response;
                    $scope.name = staff.name;
                    $scope.sname = staff.sname;
                    $scope.fiscal = staff.fiscal;
                }
        );

        $http.get("/users/" + $rootScope.user.staffId).success(function (response) {
            var user = response;
            $scope.login = user.username;
        });
    }

    $scope.pull();

    $scope.save = function () {
        if ($scope.pass == $scope.cpass) {
            var s = new Object();
            s.id = $rootScope.user.staffId;
            s.name = $scope.name;
            s.sname = $scope.sname;
            s.fiscal = $scope.fiscal;
            s.company = {"id": document.getElementById("com").selectedIndex + 1};
            $http.put("/staff", s);
            if ($scope.pass != null && $scope.pass != undefined && $scope.pass != "") {
                var u = new Object();
                u.username = $scope.login;
                $http({
                    method: 'PUT',
                    url: '/users',
                    data: u,
                    params: {
                        password: $scope.cpass
                    }
                }).success(function () {
                    $scope.show.message = "Changes have been saved";
                    $scope.show.show = true;
                    $scope.show.style = $scope.show.successStyle;
                });
            }
        } 

    }

    $scope.validatePass = function () {
        if ($scope.pass !== $scope.cpass) {

        } else {

        }
    }

    $scope.loadCompanies = function () {
        $http.get("api/companies").success(function (response) {
            $scope.companies = response;
        });
    }

});

