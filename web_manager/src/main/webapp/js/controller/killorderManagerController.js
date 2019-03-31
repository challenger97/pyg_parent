//控制层
app.controller('killorderManagerController' ,function($scope,$controller   ,killorderService){

    $controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        killorderService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //分页
    $scope.findPage=function(page,rows){
        killorderService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }



    // //保存
    // $scope.save=function(){
    //     var serviceObject;//服务层对象
    //     if($scope.entity.specification.id!=null){//如果有ID
    //         serviceObject=orderService.update( $scope.entity ); //修改
    //     }else{
    //         serviceObject=orderService.add( $scope.entity  );//增加
    //     }
    //     serviceObject.success(
    //         function(response){
    //             if(response.success){
    //                 //重新查询
    //                 $scope.reloadList();//重新加载
    //             }else{
    //                 alert(response.message);
    //             }
    //         }
    //     );
    // }


    // //批量删除
    // $scope.dele=function(){
    //     //获取选中的复选框
    //     orderService.dele( $scope.selectIds ).success(
    //         function(response){
    //             if(response.success){
    //                 $scope.reloadList();//刷新列表
    //                 $scope.selectIds = [];
    //             }
    //         }
    //     );
    // }

    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        killorderService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }



    // $scope.addTableRow = function(){
    //     $scope.entity.specificationOptionList.push({});
    // }
    //
    // $scope.deleteTableRow = function(index){
    //     $scope.entity.specificationOptionList.splice(index,1);
    // }

});
