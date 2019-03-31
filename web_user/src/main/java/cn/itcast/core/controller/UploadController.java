package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService;
import cn.itcast.core.util.FastDFSClient;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/upload")
public class UploadController {


    @Reference
    private UserService userService;

    /**
     * 读取application.properties配置文件内容, 通过里面的key, FILE_SERVER_URL读取值并且赋值给FILE_SERVER这个变量
     */
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER;

    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) throws Exception {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = new User();
        try {
            FastDFSClient fastDFS = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            //上传文件返回文件保存的路径和文件名, 例如: group1/M00/00/01/wKjIgFwWA3OANPupAAvqH_kipG8513.jpg
            String path = fastDFS.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());

            String realpath = FILE_SERVER + path;
            user.setHeadPic(realpath);
            userService.updateUser(name,user);

            return new Result(true, FILE_SERVER + path);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败!");
        }
    }
}
