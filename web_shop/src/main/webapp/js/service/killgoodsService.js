//服务层
app.service('killgoodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../killgoods/findAll.do');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../killgoods/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../killgoods/findOne.do?id='+id);
	}

	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../killgoods/search.do?page='+page+"&rows="+rows, searchEntity);
	}    
	
	this.updateStatus = function(ids,status){
		return $http.get('../killgoods/updateStatus.do?ids='+ids+"&status="+status);
	}

    this.add= function(entity){
        return $http.post("../killgoods/add.do",entity);
    }
});
