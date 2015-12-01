module.controller('AdminController', function($scope, $rootScope, $http, $state, $localStorage){
    
    /*
     *         init
     */
    
    init();
    function init(){
        if (!$rootScope.user.scope=="ROLE_ADM") $state.go("/");
        if (!$localStorage.companies) initCompanies();
        $scope.msg = msg;
        $scope.show = new Object();
        $scope.show.moduleWin = false;
    }
    
    $scope.initStaff = initStaff;
    function initStaff() {
        $http.get("staff").success(function(response){
            $scope.staff = response;
        })
    }
    $scope.initClients = initClients;
    function initClients(){
        $http.get("api/companies/clients").success(
                function(response){
                    $scope.clients = response;
                });
    }
    $scope.initProjects = function() {
        $http.get("api/projects").success(
                function(response) {
                    $scope.projects = response;
                    $scope.client=$state.params.query;
                }
        );
    }
    
    $scope.initCompanies = initCompanies; 
    function initCompanies(){
        $http.get("api/companies").success(function(response){
            $localStorage.companies = response;
            $scope.companies = $localStorage.companies;
        });
    }
    $scope.initDivisions = initDivisions;
    function initDivisions() {
        $http.get("api/companies/divisions").success(
                function(response){
                    $scope.divisions = response;
                    alert(1);
                });
    }
    
    $scope.setid = function(id,uri,allowId){
        $rootScope.allowId=allowId;
        $rootScope.resId = id;
        $state.go("/cp.info"+uri);
    }
    
    $scope.staffInfo = function() {
        if ($rootScope.resId!=null){
            $http.get("staff/"+$rootScope.resId).success(function(response){
            $scope.staff = response;
            $scope.company = response.company.id;
        }).success(function(){
            $http.get("users/"+$rootScope.resId).success(function(response){
            $scope.staff.username = response.username;
            $scope.staff.scope = response.scope;
            });
        });
        }
        
    }
    
    $scope.saveStaff=function(){
        var u = new Object();
        u.username = $scope.staff.username;
        u.scope = $scope.staff.scope;
        $scope.staff.company = {id:$scope.company};
        delete $scope.staff.username;
        delete $scope.staff.scope;
        $http.put("staff",$scope.staff).success(function(response){
            u.staffId = response;
            $http.put("users/add",u);
        });
    }
    
    $scope.resetPassword = function() {
        $http.put("users/"+$rootScope.resId);
    }
    
    $scope.projectInfo = function() {
        initStaff();
        initClients();
        initDivisions();
        $scope.allowId = $rootScope.allowId;
        delete $rootScope.allowId;
        if ($rootScope.resId!=null){
            $scope.data = new Object();
            $http.get("api/projects/details/"+$rootScope.resId)
                    .success(function(response) {
                    $scope.project = response;
                    $scope.begin =  new Date($scope.project.begin);
                    $scope.end =  new Date($scope.project.end);
                    document.getElementById("status").value=$scope.project.status;
                    document.getElementById("client").value=$scope.project.client.id;
                    document.getElementById("cm").value=$scope.project.cm.id;
                    document.getElementById("pm").value=$scope.project.pm.id;
                    document.getElementById("divis").value=$scope.project.division.id;
                    document.getElementById("type").value=$scope.project.type.id;
            }); 
        }
        
    }
    
    $scope.saveProject = function(){
        var p = new Object();
        //check id
        $http.get("api/projects/"+$scope.project.id).success(
            function(response) {
                if (response.name && $scope.allowId) {
                    console.log("error");
                    return;
                } else {
                    p.id = $scope.project.id;
                    p.name = $scope.project.name;
                    p.status = document.getElementById("status").value;
                    p.cm={id:document.getElementById("cm").value};
                    p.pm={id:document.getElementById("pm").value};
                    p.client={id:document.getElementById("client").value};
                    p.division={id:document.getElementById("divis").value};
                    p.type={id:document.getElementById("type").value};
                    p.begin=new Date($scope.begin);
                    p.end=new Date($scope.end);
                    $http.post("api/projects",p);
                }
            }
        );
    }
    
    $scope.saveClient =function(){
        $http.post("api/companies/clients",$scope.client);
    }
    
    $scope.showEditClients = function(res){
        if (res!=null) $scope.client = res; 
        else $scope.client=new Object();
        $scope.show.moduleWin=true;
    }
    
    $scope.toClientProjects = function(q){
        $state.go("/cp.projects",{query:q});
    }
    
    $scope.showEditCompanies = function(res){
        if (res!=null) $scope.company = res; 
        else $scope.company =new Object();
        $scope.show.moduleWin=true;
    }
    
    $scope.saveCompany =function(){
        $http.post("api/companies",$scope.company);
    }
    $scope.deleteCompany = function(){
        $http({
           method:"DELETE",
           url:"api/companies",
           params:{
               id:$scope.company.id
           }
        });
    }
    
    $scope.showEditDivisions = function(res){
        if (res!=null) $scope.company = res; 
        else $scope.company =new Object();
        $scope.show.moduleWin=true;
    }
    
    $scope.saveCompany =function(){
        $http.post("api/companies/divisions",$scope.division);
    }
    $scope.deleteCompany = function(){
        $http({
           method:"DELETE",
           url:"api/companies/divisions",
           params:{
               id:$scope.division.id
           }
        });
    }
    
})

