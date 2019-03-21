package com.sandu.api.user.service;

import com.sandu.api.user.model.User;
import com.sandu.api.user.model.bo.UserBo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sandu
 */
public interface UserService  {
    UserBo getUserById(Integer id);

    int updateUser(User user);

    /**
     * 校验手机号(已注册true/未注册false)
     * @param phone
     * @return
     */
    boolean checkPhone(String phone);

    String getUserByNickname(String nickname);

    List<User> selectByIds(Set<Integer> ids);

    Map<Integer,User> selectByIdsToMap(Set<Integer> ids);

    Map<Integer,String> selectByIdsToNameMap(Set<Integer> ids);

    User selectById(Long id);

    List<User> selectByBusinessAdministrationId(Integer businessAdministrationId);

    List<User> queryNotAllotUsers();

    List<User> listUserByCompanyIds(Set<Integer> collect);

    List<User> queryIntermediary();
}
