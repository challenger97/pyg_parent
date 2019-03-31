app.controller("contentController", function ($scope, contentService, contentService2, contentService3, indexService) {
    $scope.contentList = [];
    //根据分类ID查询广告的方法:
    $scope.findByCategoryId = function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (response) {
            $scope.contentList[categoryId] = response;
        });
    }

    $scope.contentList2 = [];
    // 根据分类ID查询广告的方法:
    $scope.findByCategoryId2 = function (categoryId) {
        contentService2.findByCategoryId2(categoryId).success(function (response) {
            $scope.contentList2[categoryId] = response;
        });
    }

    $scope.contentList3 = [];
    // 根据分类ID查询广告的方法:
    $scope.findByCategoryId3 = function (categoryId) {
        contentService3.findByCategoryId3(categoryId).success(function (response) {
            $scope.contentList3[categoryId] = response;
        });
    }

    //搜索,跳转到portal系统查询列表页面(传递参数）
    $scope.search = function () {
        location.href = "http://localhost:8080/search.html#?keywords=" + $scope.keywords;
    }


    //查询库存分类显示前端页面
    //查询商品分类信息
    $scope.findItemCatList = function () {
        indexService.findItemCatList().success(function (response) {
            $scope.itemCatList = response;
        })
    }

});