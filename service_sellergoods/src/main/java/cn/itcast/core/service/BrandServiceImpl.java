package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> findAll() {

        List<Brand> brands = brandDao.selectByExample(null);
        return brands;
    }

    @Override
    public PageResult findPage(Brand brand, Integer page, Integer rows) {
        //利用分页助手实现分页, 第一个参数:当前页, 第二个参数: 每页展示数据条数
        PageHelper.startPage(page, rows);

        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        if (brand != null) {
            if (brand.getName() != null && !"".equals(brand.getName())) {
                criteria.andNameLike("%"+brand.getName()+"%");
            }
            if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar())) {
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");

            }
        }

        Page<Brand> brandList = (Page<Brand>) brandDao.selectByExample(brandQuery);
        return new PageResult(brandList.getTotal(), brandList.getResult());
    }

    @Override
    public void add(Brand brand) {
        //插入数据, 插入的时候不会判断传入对象中的属性是否为空, 所有字段都参与拼接sql语句执行效率低
        //brandDao.insert();
        //插入数据, 插入的时候会判断传入对象中的属性是否为空, 如果为空, 不参与拼接sql语句, sql语句会变短执行效率会提高.
        brandDao.insertSelective(brand);
    }

    @Override
    public Brand findOne(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Brand brand) {
        //根据主键作为条件修改, 这个方法带selective说明传入的对象会进行判断, 如果传入的对象属性为null则不会拼接到sql语句中
        brandDao.updateByPrimaryKeySelective(brand);
        //根据组件作为条件修改, 这个方法不带selective, 所以传入对象中的属性如果为null也会参与拼接sql语句, 修改完如果有属性为null则数据库中的值也会被修改为null
        //brandDao.updateByPrimaryKey(brand);
        //根据非主键条件修改, 第一个参数传入需要修改的对象, 第二个参数传入修改的条件对象. 不带selective说明传入的对象属性不管是否为null都会参与修改
        //brandDao.updateByExample(, );
        //根据非主键条件修改, 第一个参数传入需要修改的对象, 第二个参数传入修改的条件对象. 带selective说明如果传入的修改对象中的属性为null不参与修改.
        //brandDao.updateByExampleSelective(, )
    }

    @Override
    public void delete(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                brandDao.deleteByPrimaryKey(id);
            }
        }

    }

    @Override
    public List<Map> findBrandList() {
        return brandDao.findBrandList();
    }

    @Override
    public void updateStatus(Long id, String status) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setAuditStatus(status);
        brandDao.updateByPrimaryKeySelective(brand);
    }

    @Override
    public List<Map> selectOptionList() {
        return null;
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



        Brand addBrand = null;
        //建立集合存取品牌pojo
        List<Brand> list = new ArrayList<Brand>();
        // 循环工作表Sheet
        for (int numSheet =0 ; numSheet <hssfWorkbook.getNumberOfSheets(); numSheet++) {

            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                //HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    addBrand = new Brand();

                    Cell name = hssfRow.getCell(0);
                    Cell firstchar = hssfRow.getCell(1);

                    //将数据放到pojo
                    addBrand.setName(name.toString());
                    addBrand.setFirstChar(firstchar.toString());

                    list.add(addBrand);
                }
            }
        }
        //数据存入数据库
        for (Brand brand : list) {
            brand.setId(System.currentTimeMillis());
            brandDao.insertSelective(brand);
        }


    }
}
