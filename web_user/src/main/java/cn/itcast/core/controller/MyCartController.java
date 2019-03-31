package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.MyCartService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class MyCartController {

    @Reference
    private MyCartService myCartService;



    @RequestMapping("/findAll")
    public List<Order> findAll(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> orders = myCartService.findAll(username);
        return orders;
    }

    /**
     * 分页查询订单详情
     * @param page
     * @param rows
     * @return
     */

    @RequestMapping("/findByPage")
    public PageResult findByPage(Integer page, Integer rows){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        PageResult pageResult = myCartService.findByPage(username, page, rows);
        return pageResult;
    }

/*    @RequestMapping("/findStatus")
    public PageResult findStatus(Integer page, Integer rows){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        PageResult pageResult = myCartService.findStatus(username, page,rows);
        return pageResult;
    }*/
}
