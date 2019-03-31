package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.opensaml.xml.signature.G;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService catService;

    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        List<ItemCat> list = catService.findByParentId(parentId);
        return list;
    }

    @RequestMapping("/findOne")
    public ItemCat findOne(Long id) {
        return  catService.findOne(id);
    }

    @RequestMapping("/findAll")
    public List<ItemCat> findAll() {
        return catService.findAll();
    }

    @RequestMapping("/add")
    public Result add(@RequestBody GoodsEntity goodsEntity){
        try {
            ItemCat itemCat=new ItemCat();
            Long category1Id = goodsEntity.getGoods().getCategory1Id();
            Long category2Id = goodsEntity.getGoods().getCategory2Id();
            if (category2Id!=null){
                itemCat.setParentId(category2Id);
            }else {
                itemCat.setParentId(category1Id);
            }
            String name = goodsEntity.getGoods().getGoodsName();
            Long typeTemplateId = goodsEntity.getGoods().getTypeTemplateId();

            itemCat.setName(name);
            itemCat.setAuditStatus("0");
            itemCat.setTypeId(typeTemplateId);

            catService.add(itemCat);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"保存失败");
        }
    }
}
