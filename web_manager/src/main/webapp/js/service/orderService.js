//服务层
app.service('orderService',function($http){

    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../orderManager/findAll.do');
    }
    //分页
    this.findPage=function(page,rows){
        return $http.get('../orderManager/findPage.do?page='+page+'&rows='+rows);
    }
    this.findById=function(orderId){
        return $http.get("../orderManager/findOne.do?orderId="+orderId);
    }

    //查询某天总金额
    this.findTotalMoney=function(){
        return $http.get('../orderManager/findTotalMoney.do');
    }
    // //增加
    // this.add=function(entity){
    //     return  $http.post('../specification/add.do',entity );
    // }
    // //修改
    // this.update=function(entity){
    //     return  $http.post('../specification/update.do',entity );
    // }
    // //删除
    // this.dele=function(ids){
    //     return $http.get('../specification/delete.do?ids='+ids);
    // }
    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../orderManager/search.do?page='+page+"&rows="+rows, searchEntity);
    }

    // this.selectOptionList=function(){
    //     return $http.get("../specification/selectOptionList.do");
    // }
});
