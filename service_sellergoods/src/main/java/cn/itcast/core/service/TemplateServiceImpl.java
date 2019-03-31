package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateQuery;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TypeTemplateDao templateDao;

    @Autowired
    private SpecificationOptionDao optionDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageResult findPage(TypeTemplate template, Integer page, Integer rows) {
        /**
         * redis中缓存模板所有数据
         */
        List<TypeTemplate> templateAll = templateDao.selectByExample(null);
        for (TypeTemplate typeTemplate : templateAll) {
            //模板id作为key, 品牌集合作为value缓存入redis中
            String brandIdsJsonStr = typeTemplate.getBrandIds();
            //将json转换成集合
            List<Map> brandList = JSON.parseArray(brandIdsJsonStr, Map.class);
            redisTemplate.boundHashOps(Constants.BRAND_LIST_REDIS).put(typeTemplate.getId(), brandList);

            //模板id作为key, 规格集合作为value缓存入redis中
            List<Map> specList = findBySpecList(typeTemplate.getId());
            redisTemplate.boundHashOps(Constants.SPEC_LIST_REDIS).put(typeTemplate.getId(), specList);

        }

        /**
         * 模板分页查询
         */
        PageHelper.startPage(page, rows);
        TypeTemplateQuery query = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = query.createCriteria();
        if (template != null) {
            if (template.getName() != null && !"".equals(template.getName())) {
                criteria.andNameLike("%"+template.getName()+"%");
            }
        }
        Page<TypeTemplate> templateList = (Page<TypeTemplate>)templateDao.selectByExample(query);
        return new PageResult(templateList.getTotal(), templateList.getResult());
    }

    @Override
    public void add(TypeTemplate template) {
        //template.setStatus("0");
        templateDao.insertSelective(template);
    }

    @Override
    public TypeTemplate findOne(Long id) {
        return templateDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(TypeTemplate template) {
        templateDao.updateByPrimaryKeySelective(template);
    }

    @Override
    public void delete(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                templateDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public List<Map> findBySpecList(Long id) {
        //1. 根据模板id查询模板对象
        TypeTemplate typeTemplate = templateDao.selectByPrimaryKey(id);
        //2. 从模板对象中获取规格集合数据, 获取到的是json格式字符串
        //数据格式例如: [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        String specIds = typeTemplate.getSpecIds();
        //3. 将json格式字符串解析成Java中的List集合对象
        List<Map> maps = JSON.parseArray(specIds, Map.class);

        //4. 遍历集合对象
        if (maps != null) {
            for (Map map : maps) {
                //5. 遍历过程中根据规格id, 查询对应的规格选项集合数据
                Long specId = Long.parseLong(String.valueOf(map.get("id")));
                //6. 将规格选项再封装到规格数据中一起返回
                SpecificationOptionQuery query = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();
                criteria.andSpecIdEqualTo(specId);
                //根据规格id获取规格选项集合数据
                List<SpecificationOption> optionList =  optionDao.selectByExample(query);
                //将规格选项集合数据封装到原来的map中
                map.put("options", optionList);

            }
        }


        return maps;
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



        TypeTemplate addType = null;
        //建立集合存取品牌pojo
        List<TypeTemplate> list = new ArrayList<TypeTemplate>();
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
                    addType = new TypeTemplate();


                    Cell name = hssfRow.getCell(1);
                    Cell specId = hssfRow.getCell(2);
                    Cell brandId = hssfRow.getCell(3);
                    Cell custom = hssfRow.getCell(4);



                    //将数据放到pojo

                    addType.setName(name.toString());
                    addType.setSpecIds(specId.toString());
                    addType.setBrandIds(brandId.toString());
                    addType.setCustomAttributeItems(custom.toString());
                    list.add(addType);
                }
            }
        }
        //数据存入数据库
        for (TypeTemplate typeTemplate : list) {
            templateDao.insertSelective(typeTemplate);
        }


    }



    @Override
    public void updateStatus(Long id, String status) {
        TypeTemplate typeTemplate = new TypeTemplate();
        typeTemplate.setId(id);
        typeTemplate.setAuditStatus(status);
        templateDao.updateByPrimaryKeySelective(typeTemplate);
    }
}
