package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 模板管理
 */
@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {

    @Reference
    private TemplateService templateService;

    /**
     * 模板高级分页查询
     * @param template
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody  TypeTemplate template, Integer page, Integer rows) {
        PageResult result = templateService.findPage(template, page, rows);
        return result;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplate template) {
        try {
            templateService.add(template);
            return new Result(true, "添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败!");
        }
    }

    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id) {
        return templateService.findOne(id);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody TypeTemplate template) {
        try {
            templateService.update(template);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            templateService.delete(ids);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
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
                templateService.uploadExcel(savePath);
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
                    templateService.updateStatus(id,status);
                }
            }
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }

}
