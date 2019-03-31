package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;
    //查询所有
    @RequestMapping("/findAll")
    public List<User> findAll(){
        List<User> userList = userService.findAll();
        return userList;
    }
    /**
     * 分页查询
     * @param page 当前页
     * @param rows 每页展示数据条数
     * @return
     */
    @RequestMapping("/findByPage")
    public PageResult findByPage(Integer page, Integer rows) {
        PageResult result = userService.findByPage(null, page, rows);
        return result;
    }


    /**
     * 根据主键查询对象
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public User findOne(Long id) {
        User one = userService.findOne(id);
        return one;
    }

    @RequestMapping("/update")
    public Result update(Long id, String status) {
        try {
            userService.update(id,status);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody User user, Integer page, Integer rows) {
        PageResult result = userService.findByPage(user, page, rows);
        return result;
    }


}
