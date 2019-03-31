package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.user.User;

import java.util.List;

public interface UserService {

    public void sendCode(String phone);

    public Boolean checkSmsCode(String phone, String smsCode);

    public void add(User user);

    public void updateUser(String username,User user);

    public User findImg(String username);

    public List<User> findAll();

    public PageResult findByPage(User user, Integer page, Integer rows);

    public User findOne(Long id);

    public void update(Long id, String status);

    public PageResult search(User user, Integer page, Integer rows);

}
