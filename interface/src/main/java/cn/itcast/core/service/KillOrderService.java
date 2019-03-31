package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.seckill.SeckillOrder;

import java.util.Date;
import java.util.List;

public interface KillOrderService {

    public PageResult findPage(SeckillOrder seckillOrder, Integer page, Integer rows);

}
