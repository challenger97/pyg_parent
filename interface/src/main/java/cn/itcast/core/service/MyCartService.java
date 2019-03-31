package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;

import java.util.List;

public interface MyCartService {


    public List<Order> findAll(String username);
    //分页查询订单详情
    public PageResult findByPage(String username, Integer page, Integer rows);

    public PageResult findStatus(String username, Integer page, Integer rows);
}
