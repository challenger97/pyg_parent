package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.GoodsService;
import cn.itcast.core.service.KillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀商品审核
 */
@RestController
@RequestMapping("/killgoods")
public class KillGoodsController {

    @Reference
    private KillGoodsService killGoodsService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody SeckillGoods seckillGoods, Integer page , Integer rows) {
        PageResult result = killGoodsService.findPage(seckillGoods, page, rows);
        return result;
    }

    /**
     * 修改商品状态
     * @param ids       需要修改的商品id数组
     * @param status    状态码, 由页面传入
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            if (ids != null) {
                for (Long id : ids) {
                    //到数据库中根据商品id改变商品的上架状态
                    killGoodsService.updateStatus(id, status);
                }
            }
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody SeckillGoods seckillGoods){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            seckillGoods.setSellerId(userName);
            killGoodsService.add(seckillGoods);
            return new Result(true, "添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败!");
        }
    }
}
