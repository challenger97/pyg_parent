package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.good.GoodsDescDao;
import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.good.GoodsQuery;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemQuery;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.util.IdWorker;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class KillGoodsServiceImpl implements KillGoodsService {

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Autowired
    private GoodsDescDao descDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemCatDao catDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private SellerDao sellerDao;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public PageResult findPage(SeckillGoods seckillGoods, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Page<SeckillGoods> killgoodsList = (Page<SeckillGoods>)seckillGoodsDao.selectByExample(null);
        return new PageResult(killgoodsList.getTotal(), killgoodsList.getResult());
    }

    @Override
    public void updateStatus(Long id, String status) {
        /**
         * 根据商品id到数据库中将商品的上架状态改变
         */

        //1. 根据商品id修改商品对象状态码
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setId(id);
        seckillGoods.setStatus(status);
        seckillGoodsDao.updateByPrimaryKeySelective(seckillGoods);
    }

    @Override
    public void add(SeckillGoods seckillGoods) {
        seckillGoods.setStatus("0");
        IdWorker idWorker = new IdWorker();
        long id = idWorker.nextId();
        seckillGoods.setGoodsId(id);

        seckillGoodsDao.insertSelective(seckillGoods);
    }

}
