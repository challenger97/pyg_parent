package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 模板管理
 */
@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {

    @Reference
    private TemplateService templateService;

    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id) {
        return templateService.findOne(id);
    }

    /**
     * 根据模板id, 查询规格集合和对应的规格选项集合数据
     * 数据例如: [{"id":27,"text":"网络","options":
     *                              [{"id":1, option_name:3G},{"id":2, option_name:4G}]},
     *          {"id":32,"text":"机身内存","options":
     *                              [{"id":3, option_name:128G},{"id":4, option_name:256G}]}
     *          ]
     * @param id
     */
    @RequestMapping("/findBySpecList")
    public List<Map> findBySpecList(Long id) {
        List<Map> list = templateService.findBySpecList(id);
        return list;
    }


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
}
