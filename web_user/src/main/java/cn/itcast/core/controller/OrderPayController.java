package cn.itcast.core.controller;


import cn.itcast.core.pojo.entity.BuyerCart;

import cn.itcast.core.service.CartService;

import cn.itcast.core.service.OrderService;
import cn.itcast.core.service.PayService;
import com.alibaba.dubbo.config.annotation.Reference;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderPay")
public class OrderPayController {

    @Reference
    private CartService cartService;

    @Reference
    private OrderService orderService;

    @Reference
    private PayService payService;


    @RequestMapping("/findCartList")
    public List<BuyerCart> findCartList() {
        //1. 获取当前登录用户名称
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        List<BuyerCart> cartList = cartService.gCartListFromRedis(userName);

        return cartList;

    }

    @RequestMapping("/time")
    public long time(){
         //计算超时时间
        long payLo = payService.toDate();
        long orderLo = orderService.toDate();
        long o = payLo - orderLo;
        //System.out.println(payLo+"==="+orderLo+"==="+o);
        return o;

    }
    @RequestMapping("/timeAndOrderId")
    public Map<String,String> timeAndOrderId(){
        return orderService.timeAndOrderId();
    }
}
