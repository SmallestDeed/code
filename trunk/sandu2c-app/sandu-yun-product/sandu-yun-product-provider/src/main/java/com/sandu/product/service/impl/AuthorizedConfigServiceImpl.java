package com.sandu.product.service.impl;

import com.sandu.common.util.Utils;
import com.sandu.product.dao.AuthorizedConfigMapper;
import com.sandu.product.model.AuthorizedConfig;
import com.sandu.product.model.search.AuthorizedConfigSearch;
import com.sandu.product.service.AuthorizedConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Title: AuthorizedConfigServiceImpl.java
 * @Package com.sandu.product.service.impl
 * @Description:产品-授权配置ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2016-04-27 14:07:34
 */
@Service("authorizedConfigService")
public class AuthorizedConfigServiceImpl implements AuthorizedConfigService {

    private static Logger logger = LoggerFactory.getLogger(AuthorizedConfigServiceImpl.class);

    @Autowired
    private AuthorizedConfigMapper authorizedConfigMapper;

    /**
     * 新增数据
     *
     * @param authorizedConfig
     * @return int
     */
    @Override
    public int add(AuthorizedConfig authorizedConfig) {
        authorizedConfigMapper.insertSelective(authorizedConfig);
        return authorizedConfig.getId();
    }

    /**
     * 更新数据
     *
     * @param authorizedConfig
     * @return int
     */
    @Override
    public int update(AuthorizedConfig authorizedConfig) {
        return authorizedConfigMapper.updateByPrimaryKeySelective(authorizedConfig);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return authorizedConfigMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return AuthorizedConfig
     */
    @Override
    public AuthorizedConfig get(Integer id) {
        return authorizedConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param authorizedConfig
     * @return List<AuthorizedConfig>
     */
    @Override
    public List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig) {
        if (authorizedConfig.getValidState() == null) {
            authorizedConfig.setValidState(1);
        }
        if (authorizedConfig.getValidState() != null && authorizedConfig.getValidState() == 3) {/**等于3则查询所有*/
            authorizedConfig.setValidState(null);
        }
        return authorizedConfigMapper.selectList(authorizedConfig);
    }

    /**
     * 获取数据数量
     *
     * @param authorizedConfigSearch
     * @return int
     */
    @Override
    public int getCount(AuthorizedConfigSearch authorizedConfigSearch) {
        if (authorizedConfigSearch.getValidState() == null) {
            authorizedConfigSearch.setValidState(1);
        }
        if (authorizedConfigSearch.getValidState() != null && authorizedConfigSearch.getValidState() == 3) {/**等于3则查询所有*/
            authorizedConfigSearch.setValidState(null);
        }
        return authorizedConfigMapper.selectCount(authorizedConfigSearch);
    }

    /**
     * 分页获取数据
     *
     * @param authorizedConfigSearch
     * @return List<AuthorizedConfig>
     */
    @Override
    public List<AuthorizedConfig> getPaginatedList(AuthorizedConfigSearch authorizedConfigSearch) {
        if (authorizedConfigSearch.getValidState() == null) {
            authorizedConfigSearch.setValidState(1);
        }
        if (authorizedConfigSearch.getValidState() != null && authorizedConfigSearch.getValidState() == 3) {/**等于3则查询所有*/
            authorizedConfigSearch.setValidState(null);
        }
        if (authorizedConfigSearch.getValidState() != null && authorizedConfigSearch.getValidState() == 4) {/**等于4则查询  试用的和未过期的*/
            authorizedConfigSearch.setValidState(4);
        }
        return authorizedConfigMapper.selectPaginatedList(authorizedConfigSearch);
    }


    /**
     * 查找该用户绑定的序列号
     *
     * @param userId
     * @return
     */
    @Override
    public List<AuthorizedConfig> findAllByUserId(Integer userId) {
        AuthorizedConfigSearch authorizedConfigSearch = new AuthorizedConfigSearch();
        authorizedConfigSearch.setStart(-1);
        authorizedConfigSearch.setLimit(-1);
        authorizedConfigSearch.setUserId(userId);
        authorizedConfigSearch.setState(new Integer(1));
        return getPaginatedList(authorizedConfigSearch);
    }

    @Override
    public List<AuthorizedConfig> selectList(AuthorizedConfig authorizedConfig) {
        return authorizedConfigMapper.selectList(authorizedConfig);
    }

    /**
     * 去重复(品牌,大类,小类)
     *
     * @param authorizedConfig
     * @author huangsongbo
     */
    @Override
    public void dealWithRepeat(AuthorizedConfig authorizedConfig) {
        String brandIds = authorizedConfig.getBrandIds();
        List<String> brandIdsList = Utils.getListFromStr(brandIds);
        if (brandIdsList != null && brandIdsList.size() > 0) {
            authorizedConfig.setBrandIds(brandIdsList.get(0));
        }
        String bigType = authorizedConfig.getBigType();
        List<String> bigTypeList = Utils.getListFromStr(bigType);
        if (bigTypeList != null && bigTypeList.size() > 0) {
            authorizedConfig.setBigType(bigTypeList.get(0));
        }
        String smallTypeIds = authorizedConfig.getSmallTypeIds();
        List<String> smallTypeIdList = Utils.getListFromStr(smallTypeIds);
        if (smallTypeIdList != null && smallTypeIdList.size() > 0) {
            authorizedConfig.setSmallTypeIds(smallTypeIdList.get(0));
        }
    }
}
