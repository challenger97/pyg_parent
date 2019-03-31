//首页控制器
app.controller('indexController',function($scope,$controller,$http,loginService,orderService){
    $controller('baseController',{$scope:$scope});
    $scope.showName=function(){
        loginService.showName().success(
            function(response){
                $scope.img=response.img;
                $scope.loginName=response.loginName;
            }
        );
    }
    $scope.findByPage = function(page,rows){
        // 向后台发送请求获取数据:
        orderService.findByPage(page,rows).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.orderList = response.rows;
        });
    }

    $scope.orderlist=function(){
        orderService.findAll().success(
            function(response){
                $scope.orderList=response;
            }
        );
    }
    $scope.findStatus = function(page,rows){
        // 向后台发送请求获取数据:
        orderService.findStatus(page,rows).success(function(response){
            $scope.pagination.totalItems = response.total;
            $scope.orderList = response.rows;
        });
    }






});