app.service("contentService",function($http){
    this.findByCategoryId = function(categoryId){
        return $http.get("content/findByCategoryId.do?categoryId="+categoryId);
    }
});
app.service("contentService2",function($http){
    this.findByCategoryId2 = function(categoryId){
        return $http.get("content/findByCategoryId2.do?categoryId="+categoryId);
    }
});
app.service("contentService3",function($http){
    this.findByCategoryId3 = function(categoryId){
        return $http.get("content/findByCategoryId3.do?categoryId="+categoryId);
    }
});


//查询商品分类信息
app.service('indexService', function ($http) {
    this.findItemCatList = function () {
        return $http.get("content/findItemCatList.do");
    }
});