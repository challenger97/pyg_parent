// 定义服务层:
app.service("brandService",function($http){
	this.findAll = function(){
		return $http.get("../brand/findAll.do");
	}
	
	this.findByPage = function(page,rows){
		return $http.get("../brand/findByPage.do?page="+page+"&rows="+rows);
	}
	
	this.save = function(entity){
		return $http.post("../brand/add.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../brand/update.do",entity);
	}
	
	this.findById=function(id){
		return $http.get("../brand/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../brand/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../brand/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
	this.selectOptionList = function(){
		return $http.get("../brand/selectOptionList.do");
	}

    //导入Excel
    this.uploadExcel = function() {
        // 向后台传递数据:
        var formData = new FormData();
        // 向formData中添加数据:
        var file = document.querySelector('input[type=file]').files[0];
        formData.append('file', file);

        return $http({
            method: 'post',
            url: '../brand/uploadExcel.do',
            data: formData,
            headers: {'Content-Type': undefined},// Content-Type : text/html  text/plain
            transformRequest: angular.identity
        });
    }




    this.updateStatus = function(ids,status){
        return $http.get('../brand/updateStatus.do?ids='+ids+"&status="+status);
    }
});