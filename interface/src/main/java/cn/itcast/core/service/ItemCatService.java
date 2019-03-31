package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.ItemCat;

import java.util.List;

public interface ItemCatService {

    public List<ItemCat> findByParentId(Long parentId);

    public ItemCat findOne(Long id);

    public List<ItemCat> findAll();

    public void add(ItemCat itemCat);

    public List<ItemCat> findItemCatList();

    void uploadExcel(String fileName) throws Exception;

    public PageResult findPage(ItemCat itemCat, Integer page, Integer rows);

    public void updateStatus(Long id, String status);
}
