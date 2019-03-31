// 定义控制器:
app.controller("AddressController",function($scope,$http,AddressService){

    $scope.findAll = function(){
        // 向后台发送请求:
        AddressService.findAll().success(function(response){
            $scope.list = response;
        });
    }
    $scope.insert = function(){
        // 向后台发送请求:
        AddressService.insert($scope.entity).success(function(response){
            // 判断保存是否成功:
            if(response.success==true){
                // 保存成功
                $scope.findAll();
                 alert(response.message);

            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }

    $scope.delete = function(id){
        // 向后台发送请求:
        if (confirm("确认要删除吗","取消")){
        AddressService.delete(id).success(function(response){
            // 判断删除成功:
            if(response.success==true){
                // alert(response.message);
                $scope.findAll();
            }else{
                // 保存失败
                alert(response.message);
            }
        });

    }
    }


});
