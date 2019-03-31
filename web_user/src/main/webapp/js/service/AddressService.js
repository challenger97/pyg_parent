// 定义服务层:
app.service("AddressService",function($http){
    this.findAll = function(){
        return $http.get("../address/findAll.do");
    }
    this.insert = function(entity){
        return $http.post("../address/insert.do",entity);
    }
    this.delete = function(id){
        return $http.get("../address/delete.do?id="+id);
    }


});