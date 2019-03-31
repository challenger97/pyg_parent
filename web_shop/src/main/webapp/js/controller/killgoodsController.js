 //控制层 
app.controller('killgoodsController' ,function($scope,$controller,killgoodsService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		killgoodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){
        killgoodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){
        killgoodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存
	$scope.save=function(){
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=killgoodsService.update( $scope.entity ); //修改
		}else{
			serviceObject=killgoodsService.add( $scope.entity);//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}

	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		killgoodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	// 显示状态
	$scope.status = ["未审核","审核通过","审核未通过","关闭"];

	
	// 审核的方法:
	$scope.updateStatus = function(status){
		killgoodsService.updateStatus($scope.selectIds,status).success(function(response){
			if(response.success){
				$scope.reloadList();//刷新列表
				$scope.selectIds = [];
			}else{
				alert(response.message);
			}
		});
	}
});	
