// 定义控制器:
app.controller("userController",function($scope,$controller,$http,userService){
    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});

    // 查询所有的用户列表的方法:
    $scope.findAll = function(){
        // 向后台发送请求:
        userService.findAll().success(function(response){
            $scope.list = response;
        });
    }


    // 分页查询
    $scope.findByPage = function(page,rows){
        // 向后台发送请求获取数据:
        userService.findByPage(page,rows).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;

        });
    }


    // 查询一个:
    $scope.findOne = function(id){
        userService.findOne(id).success(function(response){
            // {id:xx,name:yy,firstChar:zz}
            $scope.User = response;
        });
    }

    $scope.searchEntity={};

    // 更改冻结状态
    $scope.update = function(id,status){
        // 向后台发送请求:
        userService.update(id,status).success(function(response){
            if(response.success){
                //重新查询
                $scope.reloadList();
            }else{
                alert(response.message);
            }

        });
    }

    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        userService.search(page,rows,$scope.searchEntity).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }



});
