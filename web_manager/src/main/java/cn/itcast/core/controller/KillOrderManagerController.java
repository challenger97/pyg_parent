package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.KillOrderService;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/killorderManager")
public class KillOrderManagerController {

    @Reference
    private KillOrderService killOrderService;

    /**
     * 条件查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody SeckillOrder seckillOrder, Integer page, Integer rows) {
        PageResult result = killOrderService.findPage(seckillOrder, page, rows);
        return result;
    }
}
