package com.sandu.service.user.impl;

import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.api.user.model.User;
import com.sandu.api.user.model.bo.UserBo;
import com.sandu.api.user.service.UserService;
import com.sandu.service.dictionary.dao.DictionaryDao;
import com.sandu.service.user.dao.UserDao;
import com.sandu.util.Commoner;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sandu
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao       userDao;
    @Resource
    private ResPicService resPicService;
    @Autowired
    private DictionaryDao dictionaryMapper;

    @Override
    public UserBo getUserById(Integer id) {
        User user = userDao.selectByPrimaryKey(id.longValue());
        UserBo bo = new UserBo();
        if (Commoner.isEmpty(user)) {
            throw new RuntimeException("没有这个用户");
        }
        
        BeanUtils.copyProperties(user, bo);
        bo.setCompanyId(user.getBusinessAdministrationId());
        if (user.getPicId() != null) {
            bo.setPicPath(resPicService.getPathById(user.getPicId()));
        }
        return bo;
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean checkPhone(String phone) {
        int count = userDao.checkPhone(phone);
        return count > 0;
    }

    @Override
    public String getUserByNickname(String nickname) {
        return userDao.getUserByNickname(nickname);
    }

    @Override
    public List<User> selectByIds(Set<Integer> ids) {
        if (ids == null || ids.size() <= 0) {
            return null;
        }
        return userDao.selectByIds(ids);
    }

    @Override
    public Map<Integer, User> selectByIdsToMap(Set<Integer> ids) {
        List<User> userList = selectByIds(ids);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        Map<Integer, User> userInfoMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user, (key1, key2) -> key2));
        return userInfoMap;
    }

    @Override
    public Map<Integer, String> selectByIdsToNameMap(Set<Integer> ids) {
        List<User> userList = selectByIds(ids);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        Map<Integer, String> userInfoMap = new HashMap<>();
        //如果用户名为空则存手机号
        for(User user :userList){
            userInfoMap.put(user.getId(),user.getUserName() == null ? user.getMobile():user.getUserName());
        }
        return userInfoMap;
    }

    @Override
    public List<User> queryNotAllotUsers() {
        return userDao.queryNotAllotUsers();
    }

    @Override
    public List<User> listUserByCompanyIds(Set<Integer> collect) {
        if (collect != null && collect.isEmpty()) {
            return Collections.emptyList();
        }
        return userDao.listUserByCompanyIds(collect);
    }


    @Override
    public User selectById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public List<User> selectByBusinessAdministrationId(Integer businessAdministrationId){
        return userDao.selectByBusinessAdministrationId(businessAdministrationId);
    }

    @Override
    public List<User> queryIntermediary() {
        List<Dictionary> dictionary = dictionaryMapper.getByTypeAndName("userType","中介");
        List<User> users = userDao.selectByType(dictionary.get(0).getValue());
        return users;
    }
}
