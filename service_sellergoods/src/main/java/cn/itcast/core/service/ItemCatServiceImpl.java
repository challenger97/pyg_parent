package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsQuery;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao catDao;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        //获取所有分类数据
        List<ItemCat> itemCatAll = catDao.selectByExample(null);
        //分类名称作为key, typeId也就是模板id作为value, 缓存到redis当中
        for (ItemCat itemCat : itemCatAll) {
            redisTemplate.boundHashOps(Constants.CATEGORY_LIST_REDIS).put(itemCat.getName(), itemCat.getTypeId());
        }

        //根据父级id查询它的子集, 展示到页面
        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = catDao.selectByExample(query);
        return itemCats;
    }

    @Override
    public ItemCat findOne(Long id) {
        return catDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return catDao.selectByExample(null);
    }

    @Override
    public void add(ItemCat itemCat) {
        catDao.insertSelective(itemCat);
    }

    /**
     * 查询商品分类信息
     * @return
     */
    @Override
    public List<ItemCat> findItemCatList() {
        //从缓存中查询首页商品分类
        List<ItemCat> itemCatList = (List<ItemCat>) redisTemplate.boundHashOps("itemCat").get("indexItemCat");

        //如果缓存中没有数据，则从数据库查询再存入缓存
        if(itemCatList==null){
            //查询出1级商品分类的集合
        List<ItemCat> itemCatList1 = catDao.findItemCatListByParentId(0L);
            //遍历1级商品分类的集合
            for(ItemCat itemCat1:itemCatList1){
                //查询2级商品分类的集合(将1级商品分类的id作为条件)
                List<ItemCat> itemCatList2 = catDao.findItemCatListByParentId(itemCat1.getId());
                //遍历2级商品分类的集合
                for(ItemCat itemCat2:itemCatList2){
                    //查询3级商品分类的集合(将2级商品分类的父id作为条件)
                    List<ItemCat> itemCatList3 = catDao.findItemCatListByParentId(itemCat2.getId());
                    //将2级商品分类的集合封装到2级商品分类实体中
                    itemCat2.setItemCatList(itemCatList3);
                }
                /*到这一步的时候，3级商品分类已经封装到2级分类中*/
                //将2级商品分类的集合封装到1级商品分类实体中
                itemCat1.setItemCatList(itemCatList2);
            }
            //存入缓存
            redisTemplate.boundHashOps("itemCat").put("indexItemCat",itemCatList1);
            return itemCatList1;
        }
        //到这一步，说明缓存中有数据，直接返回
        return itemCatList;
    }


    /**
     * 上传Excel表数据读取到数据库
     * @param fileName  excel路径
     * @throws Exception
     */
    @Override
    public void uploadExcel(String fileName) throws Exception {

        //判断后缀名
        InputStream is = new FileInputStream(new File(fileName));
        Workbook hssfWorkbook = null;
        if (fileName.endsWith("xlsx")){
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        }else if (fileName.endsWith("xls")){
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003

        }



        ItemCat addItem = null;
        //建立集合存取品牌pojo
        List<ItemCat> list = new ArrayList<ItemCat>();
        // 循环工作表Sheet
        for (int numSheet =0 ; numSheet <hssfWorkbook.getNumberOfSheets(); numSheet++) {

            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {

                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    addItem = new ItemCat();


                    Cell name = hssfRow.getCell(1);
                    Cell typeId = hssfRow.getCell(2);
                    Cell parentId = hssfRow.getCell(0);

                    String typeIdStr = typeId.toString();
                    String s = toString(typeIdStr);
                    String s1 = toString(parentId.toString());

                    //将数据放到pojo

                    addItem.setName(name.toString());
                    addItem.setParentId(Long.parseLong(s1));
                    addItem.setTypeId(Long.parseLong(s));
                    list.add(addItem);
                }
            }
        }
        //数据存入数据库
        for (ItemCat itemCat : list) {
            catDao.insertSelective(itemCat);
        }


    }

    public String toString(String typeIdStr){
        char[] chars = typeIdStr.toCharArray();
        String s = String.valueOf(chars[0]);
        String s1 = String.valueOf(chars[1]);
        return s + s1;

    }

    @Override
    public PageResult findPage(ItemCat itemCat, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Page<ItemCat> itemCatList = (Page<ItemCat>)catDao.selectByExample(null);
        return new PageResult(itemCatList.getTotal(), itemCatList.getResult());
    }

    @Override
    public void updateStatus(Long id, String status) {
        ItemCat itemCat = new ItemCat();
        itemCat.setId(id);
        itemCat.setAuditStatus(status);
        catDao.updateByPrimaryKeySelective(itemCat);
    }
}
