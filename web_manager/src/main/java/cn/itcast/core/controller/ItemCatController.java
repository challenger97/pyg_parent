package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService catService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody ItemCat itemCat, Integer page , Integer rows) {

        PageResult result = catService.findPage(itemCat, page, rows);
        return result;
    }


    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        List<ItemCat> list = catService.findByParentId(parentId);
        return list;
    }

    @RequestMapping("/findAll")
    public List<ItemCat> findAll() {
        return catService.findAll();
    }

    /**
     * excel导入
     * @param file  excel文件
     * @param request
     * @return
     */
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
                catService.uploadExcel(savePath);
                return new Result(true, "导入成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "导入失败");
            }
        }
        return null;
    }


    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status){
        try {
            if(ids != null){
                for (Long id : ids) {
                    catService.updateStatus(id,status);
                }
            }
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }

}
