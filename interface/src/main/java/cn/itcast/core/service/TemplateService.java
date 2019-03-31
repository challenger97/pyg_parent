package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

public interface TemplateService {

    public PageResult findPage(TypeTemplate template, Integer page, Integer rows);

    public void add(TypeTemplate template);

    public TypeTemplate findOne(Long id);

    public void update(TypeTemplate template);

    public void delete(Long[] ids);

    public List<Map> findBySpecList(Long id);

    void uploadExcel(String fileName) throws Exception;

    public void updateStatus(Long id, String status);
}
