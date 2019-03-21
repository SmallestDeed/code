package com.sandu.service.user.dao;

import com.sandu.api.user.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Sandu
 */
@Repository
public interface UserDao {
    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User user);

    int checkPhone(String phone);

    @Select("select user_name from sys_user where nick_name = #{nickname} and is_deleted =0 limit 1")
    String getUserByNickname(@Param("nickname") String nickname);

    List<User> selectByIds(@Param("set")Set<Integer> ids);

    User selectById(Long id);

    List<User> selectByBusinessAdministrationId(Integer businessAdministrationId);

    List<User> queryNotAllotUsers();

    List<User> listUserByCompanyIds(@Param("companyIds") Set<Integer> companyIds);

    List<User> selectByType(@Param("type") Integer value);

    User selectByNameOrId(@Param("creator") String creator);
}
