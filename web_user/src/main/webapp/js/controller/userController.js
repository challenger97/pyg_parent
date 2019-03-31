//控制层
app.controller('userController' ,function($scope,$controller   ,userService,uploadService){

    //注册用户
    $scope.reg=function(){

        //比较两次输入的密码是否一致
        if($scope.password!=$scope.entity.password){
            alert("两次输入密码不一致，请重新输入");
            $scope.entity.password="";
            $scope.password="";
            return ;
        }
        //新增
        userService.add($scope.entity,$scope.smscode).success(
            function(response){
                alert(response.message);
            }
        );
    }

    //发送验证码
    $scope.sendCode=function(){
        if($scope.entity.phone==null || $scope.entity.phone==""){
            alert("请填写手机号码");
            return ;
        }

        userService.sendCode($scope.entity.phone  ).success(
            function(response){
                alert(response.message);
            }
        );
    }

    $scope.uploadFile = function(){
        // 调用uploadService的方法完成文件的上传
        uploadService.uploadFile().success(function(response){
            if(response.success){
                // 获得url
                alert(response.message);
            }else{
                alert(response.message);
            }
        });
    }

    //查询实体
    $scope.findOne=function(id){
        itemCatService.findOne(id).success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    $scope.entity={};
    $scope.updateUser =function(){
        console.log($scope.entity);
        userService.updateUser($scope.entity).success(function(response){
            console.log(response.message);
            if(response.success){
                alert(response.message);
            }else{
                alert(response.message);
            }
        });
    }




});	
