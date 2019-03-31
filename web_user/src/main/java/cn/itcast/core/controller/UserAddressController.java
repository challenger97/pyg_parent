package cn.itcast.core.controller;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.service.UserAddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class UserAddressController {

    @Reference
    private UserAddressService userAddressService;

    /**
     * 添加地址
     * @param address
     * @return
     */
    @RequestMapping("/insert")
    public Result insert(@RequestBody Address address){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            userAddressService.insert(username,address);
            return new Result(true,"添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }

    }


    /**
     * 查询全部地址
     * @return
     */
    @RequestMapping("/findAll")
    public List<Address> findAll(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Address> addressList = userAddressService.findAll(username);
        return addressList;
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long id){
        try {
            userAddressService.delete(id);
            return new Result(true,"删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败!");
        }
    }
}
