package cn.itcast.core.service;


import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class KillOrderServiceImpl implements KillOrderService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    @Override
    public PageResult findPage(SeckillOrder seckillOrder, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Page<SeckillOrder> seckillOrderList = (Page<SeckillOrder>)seckillOrderDao.selectByExample(null);
        return new PageResult(seckillOrderList.getTotal(),seckillOrderList.getResult());
    }

}
