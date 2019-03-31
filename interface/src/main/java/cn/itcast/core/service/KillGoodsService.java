package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.seckill.SeckillGoods;

public interface KillGoodsService {

    public PageResult findPage(SeckillGoods seckillGoods, Integer page, Integer rows);

    public void updateStatus(Long id, String status);

    public void add(SeckillGoods seckillGoods);
}
