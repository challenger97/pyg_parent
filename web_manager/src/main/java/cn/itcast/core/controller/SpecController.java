package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 规格管理
 */
@RestController
@RequestMapping("/specification")
public class SpecController {

    @Reference
    private SpecificationService specService;

    /**
     * 规格高级分页查询
     * @param spec
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Specification spec, Integer page, Integer rows) {
        PageResult result = specService.findPage(spec, page, rows);
        return result;
    }

    /**
     * 规格添加
     * @param specEntity
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody  SpecEntity specEntity) {
        try {
            specService.add(specEntity);
            return new Result(true, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败!");
        }
    }

    /**
     * 规格数据回显
     * @param id 规格id
     * @return
     */
    @RequestMapping("/findOne")
    public SpecEntity findOne(Long id) {
        SpecEntity one = specService.findOne(id);
        return one;
    }

    /**
     * 更新保存
     * @param specEntity
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SpecEntity specEntity) {
        try {
            specService.update(specEntity);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public  Result delete(Long[] ids) {
        try {
            specService.delete(ids);
            return new Result(true,"删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败!");
        }
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        return specService.selectOptionList();
    }


    @RequestMapping("/uploadExcel")
    public Result uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request){

        String filePath = file.getOriginalFilename();
        //获取文件路径
        String savePath = request.getSession().getServletContext().getRealPath(filePath);
        if(!file.isEmpty()) {

            //新建文件
            File targetFile = new File(savePath);

            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            try {
                file.transferTo(targetFile);
                specService.uploadExcel(savePath);
                return new Result(true, "导入成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "导入失败");
            }
        }
        return null;
    }

    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids,String status){
        try {
            if(ids != null){
                for (Long id : ids) {
                    specService.updateStatus(id,status);
                }
            }
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }


}
