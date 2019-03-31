package cn.itcast.core.controller;

import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginContoller {

    @Reference
    private UserService userService;

    @RequestMapping("/showName")
    public Map showName(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap hashMap = new HashMap();
        User img = userService.findImg(username);
        String headPic = img.getHeadPic();
        hashMap.put("loginName",username);
        hashMap.put("img",headPic);
        return hashMap;
    }

}
