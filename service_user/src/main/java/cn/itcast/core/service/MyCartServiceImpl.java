package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.BinaryClient;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MyCartServiceImpl implements MyCartService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    /**
     * 查询全部订单
     * @param username
     * @return
     */
    @Override
    public List<Order> findAll(String username) {
        //创建查询条件对象
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria1 = orderQuery.createCriteria();
        criteria1.andUserIdEqualTo(username);
        //根据条件查询
        List<Order> orderList = orderDao.selectByExample(orderQuery);

        for (Order order : orderList) {

            OrderItemQuery orderItemQuery = new OrderItemQuery();
            OrderItemQuery.Criteria criteria = orderItemQuery.createCriteria();

            criteria.andOrderIdEqualTo(order.getOrderId());

            List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);
            for (OrderItem orderItem : orderItems) {
                order.setOrderItems(orderItems);
            }
        }


        return orderList;
    }

    /**
     * 订单分页查询
     * @param username
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageResult findByPage(String username, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        //创建查询条件对象
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria1 = orderQuery.createCriteria();
        criteria1.andUserIdEqualTo(username);
        //根据条件查询
        List<Order> orderList = orderDao.selectByExample(orderQuery);

        for (Order order : orderList) {

            OrderItemQuery orderItemQuery = new OrderItemQuery();
            OrderItemQuery.Criteria criteria = orderItemQuery.createCriteria();

            criteria.andOrderIdEqualTo(order.getOrderId());

            List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);
            for (OrderItem orderItem : orderItems) {
                order.setOrderItems(orderItems);
            }
        }
        Page<Order> page1= (Page<Order>)orderList;
        return new PageResult(page1.getTotal(),page1.getResult());
    }

    /**
     * 查询订单状态
     * @param username
     * @return
     */
    @Override
    public PageResult findStatus(String username, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        //创建查询条件对象
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        criteria.andUserIdEqualTo(username);
        criteria.andStatusEqualTo("1");
        //根据条件查询
        List<Order> orderList = orderDao.selectByExample(orderQuery);

        for (Order order : orderList) {

            OrderItemQuery orderItemQuery = new OrderItemQuery();
            OrderItemQuery.Criteria criteria1 = orderItemQuery.createCriteria();

            criteria1.andOrderIdEqualTo(order.getOrderId());

            List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);
            for (OrderItem orderItem : orderItems) {
                order.setOrderItems(orderItems);
            }
        }
        Page<Order> page1= (Page<Order>)orderList;
        return new PageResult(page1.getTotal(),page1.getResult());

    }
}
