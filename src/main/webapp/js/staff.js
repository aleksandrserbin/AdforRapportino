module.controller('StaffController', function($http, $scope, $localStorage){
    $scope.confirmationPassStyle = {}
    
    $scope.pull = function(){
       $http.get("/staff/"+$localStorage.userid).success(
            function(response) {
                console.log(response);
                var staff = response;
                $scope.name = staff.name;
                $scope.sname = staff.sname;
                $scope.fiscal = staff.fiscal;
            }
        );

        $http.get("/users/"+$localStorage.userid).success(function(response){
            var user = response;
            console.log(response);
            $scope.login = user.username;
        });
   } 
   
   $scope.pull();
   
   $scope.save = function(){
       if ($scope.pass == $scope.cpass) {
           var s = new Object();
           s.name = $scope.name;
           s.sname = $scope.sname;
           s.fiscal=$scope.fiscal;
           s.company = {"id":document.getElementById("com").selectedIndex+1};
           $http.put("/staff",s);
           if ($scope.pass!=null && $scope.pass!=undefined && scope.pass!=""){
               alert("heh");
           }
       } 
       
   }
   
   $scope.validatePass = function(){
       if ($scope.pass !== $scope.cpass){
           confirmationPassStyle.border = "2px solid red";
       } else {
           confirmationPassStyle={};
       }
   }
   
   $scope.loadCompanies = function(){
       $http.get("/companies").success(function(response){
           $scope.companies = response;
       });
   }
    
});

