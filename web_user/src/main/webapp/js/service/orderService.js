//服务层
app.service('orderService',function($http){
    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../order/findAll.do');
    }
    this.findByPage = function(page,rows){
        return $http.get('../order/findByPage.do?page='+page+'&rows='+rows);
    }
    this.findStatus = function(){
        return $http.get('../order/findStatus.do?page='+page+'&rows='+rows);
    }
    //增加
    this.add=function(entity){
        return  $http.post('../user/add.do',entity );
    }
    //修改
    this.update=function(entity){
        return  $http.post('../user/update.do',entity );
    }

});