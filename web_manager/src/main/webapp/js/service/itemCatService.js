//服务层
app.service('itemCatService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../itemCat/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../itemCat/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../itemCat/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../itemCat/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../itemCat/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../itemCat/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../itemCat/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	this.findByParentId = function(parentId){
		return $http.get("../itemCat/findByParentId.do?parentId="+parentId);
	}


    //导入为excel表
    this.uploadExcel = function(){
        // 向后台传递数据:
        var formData = new FormData();
        // 向formData中添加数据:
        var file =document.querySelector('input[type=file]').files[0];
        formData.append('file',file);

        return $http({
            method:'post',
            url:'../itemCat/uploadExcel.do',
            data:formData,
            headers:{'Content-Type':undefined} ,// Content-Type : text/html  text/plain
            transformRequest: angular.identity
        });
    }


    this.updateStatus = function(ids,status){
        return $http.get('../itemCat/updateStatus.do?ids='+ids+"&status="+status);
    }

});
