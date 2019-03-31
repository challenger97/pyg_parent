package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService {

    public PageResult findPage(Specification spec, Integer page, Integer rows);

    public void add(SpecEntity specEntity);

    public SpecEntity findOne(Long id);

    public void update(SpecEntity specEntity);

    public void delete(Long[] ids);

    List<Map> findSpecList();

    void uploadExcel(String fileName) throws Exception;

    public void updateStatus(Long id, String status);

    public List<Map> selectOptionList();
}
