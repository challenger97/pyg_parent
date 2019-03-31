package cn.itcast.core.controller;

import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.service.ContentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId) {
        List<Content> list = contentService.findByCategoryIdFromRedis(categoryId);
        return list;
    }
    @RequestMapping("/findByCategoryId2")
    public List<Content> findByCategoryId2(Long categoryId) {
        List<Content> list = contentService.findByCategoryIdFromRedis(categoryId);
        return list;
    }
    @RequestMapping("/findByCategoryId3")
    public List<Content> findByCategoryId3(Long categoryId) {
        List<Content> list = contentService.findByCategoryIdFromRedis(categoryId);
        return list;
    }
}
