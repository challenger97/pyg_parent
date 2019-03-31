package cn.itcast.core.dao.user;

import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface UserDao {
    int countByExample(UserQuery example);

    int deleteByExample(UserQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserQuery example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserQuery example);

    int updateByExample(@Param("record") User record, @Param("example") UserQuery example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select * from tb_user where username=#{username}")
//    @Results 映射多列数据
//    @Result:映射单列数据
//    select :全限类名+ 方法名 == mapperId
    @Results({
            @Result(property = "headPic",column = "head_pic"),
            @Result(property = "username",column = "name"),
            @Result(property ="nickName",column = "nick_name")
    })
    User findAll(String username);
}