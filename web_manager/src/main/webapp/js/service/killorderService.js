//服务层
app.service('killorderService',function($http){

    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../killorderManager/findAll.do');
    }
    //分页
    this.findPage=function(page,rows){
        return $http.get('../killorderManager/findPage.do?page='+page+'&rows='+rows);
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
        return $http.post('../killorderManager/search.do?page='+page+"&rows="+rows, searchEntity);
    }

    // this.selectOptionList=function(){
    //     return $http.get("../specification/selectOptionList.do");
    // }
});
