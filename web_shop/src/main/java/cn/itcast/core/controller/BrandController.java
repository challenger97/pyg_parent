package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<Brand> findAll(){
        List<Brand> brandList = brandService.findAll();
        return brandList;
    }

    //加
    @RequestMapping("/save")
    public Result save(@RequestBody Brand brand){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
           // brand.setSeller_id(userName);
            brandService.add(brand);
            return new Result(true,"保存成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"保存失败");
        }
    }

    //根据id查询
    @RequestMapping("/findById")
    public Brand findOne(Long id){
        Brand one = brandService.findOne(id);
        return one;
    }

    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand){
        try {
            brandService.update(brand);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    //高级分页
    @RequestMapping("/search")
    public PageResult search(@RequestBody Brand brand, Integer page, Integer rows){
        PageResult result = brandService.findPage(brand, page, rows);
        return result;
    }

    @RequestMapping("/selectOptionList")
    public List<Map> findBrandList(){
        return brandService.findBrandList();
    }
}
