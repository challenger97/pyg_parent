package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {

    public void add(Order order);

    public PayLog getPayLogByUserName(String userName);

    public void updatePayStatus(String userName);

    //后台订单管理
    public List<Order> findAll();


    public PageResult findPage(Order order, Integer page, Integer rows);
    public Order findById(Long orderId);

    //销售折线图,统计某一天支付总金额
    public Map<String ,Object> findTotalMoney();
     //计算支付超时
    long toDate();

    Map<String,String> timeAndOrderId();
}
