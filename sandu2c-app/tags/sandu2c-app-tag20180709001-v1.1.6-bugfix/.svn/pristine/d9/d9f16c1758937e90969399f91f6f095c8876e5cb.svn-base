package com.sandu.product.service.impl;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.product.dao.CollectCatalogMapper;
import com.sandu.product.model.CollectCatalog;
import com.sandu.product.model.result.SearchCollectCatalogResult;
import com.sandu.product.model.search.CollectCatalogSearch;
import com.sandu.product.model.search.UserProductCollectSearch;
import com.sandu.product.service.CollectCatalogService;
import com.sandu.product.service.UserProductCollectService;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("collectCatalogService")
public class CollectCatalogServiceImpl implements CollectCatalogService {


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserProductCollectService userProductCollectService;
    @Autowired
    private CollectCatalogMapper collectCatalogMapper;


    /**
     * 新增数据
     *
     * @param collectCatalog
     * @return int
     */
    @Override
    public int add(CollectCatalog collectCatalog) {
        collectCatalogMapper.insertSelective(collectCatalog);
        return collectCatalog.getId();
    }

    /**
     * 更新数据
     *
     * @param collectCatalog
     * @return int
     */
    @Override
    public int update(CollectCatalog collectCatalog) {
        return collectCatalogMapper
                .updateByPrimaryKeySelective(collectCatalog);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return collectCatalogMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return CollectCatalog
     */
    @Override
    public CollectCatalog get(Integer id) {
        return collectCatalogMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param collectCatalog
     * @return List<CollectCatalog>
     */
    @Override
    public List<CollectCatalog> getList(CollectCatalog collectCatalog) {
        return collectCatalogMapper.selectList(collectCatalog);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(CollectCatalogSearch collectCatalogSearch) {
        return collectCatalogMapper.selectCount(collectCatalogSearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<CollectCatalog>
     */
    @Override
    public List<CollectCatalog> getPaginatedList(
            CollectCatalogSearch collectCatalogSearch) {
        return collectCatalogMapper.selectPaginatedList(collectCatalogSearch);
    }


    @Override
    public ResponseEnvelope queryCollectCatalogList(CollectCatalogSearch collectCatalogSearch,
                                                    LoginUser loginUser) {
        List<SearchCollectCatalogResult> ResultList = new ArrayList<SearchCollectCatalogResult>();
        List<CollectCatalog> list = new ArrayList<CollectCatalog>();
        int count = 0;
        try {
            count = this.getCount(collectCatalogSearch);
            if (count <= 0) {
                SysUser user = sysUserService.get(loginUser.getId());
                this.newCollectCatalogController(user);
                count = this.getCount(collectCatalogSearch);
            }
            if (count > 0) {
                collectCatalogSearch.setOrder("gmt_create");
                collectCatalogSearch.setOrderNum("desc");
                list = this.getPaginatedList(collectCatalogSearch);
            }
            if (list != null && list.size() > 0) {
                for (CollectCatalog collectCatalog : list) {
                    SearchCollectCatalogResult searchCollectCatalogResult = new SearchCollectCatalogResult();
                    searchCollectCatalogResult.setId(collectCatalog.getId());
                    searchCollectCatalogResult.setCatalogName(collectCatalog.getCatalogName());
                    searchCollectCatalogResult.setSysCode(collectCatalog.getSysCode());
                    searchCollectCatalogResult.setIsLocked(collectCatalog.getIsLocked());
                    ResultList.add(searchCollectCatalogResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEnvelope(false, "数据异常!");
        }
        return new ResponseEnvelope(true, ResultList);
    }

    /**
     * 新建一个默认文件夹
     *
     * @param user
     */
    public void newCollectCatalogController(SysUser user) {
        CollectCatalog catalog = new CollectCatalog();
        catalog.setCatalogName("默认");
        catalog.setIsLocked(1);
        catalog.setUserId(user.getId());
        catalog.setIsDeleted(0);
        catalog.setSysCode(System.currentTimeMillis() + "_" + randomNumber());

        catalog.setModifier(user.getMobile());
        catalog.setCreator(user.getNickName());
        this.add(catalog);
    }


    public int randomNumber() {
        Set<Integer> m = new HashSet<Integer>();
        int a;
        do {
            a = (int) (Math.random() * 1000000);
        } while (m.contains(a));
        m.add(a);
        return a;
    }

    @Override
    public ResponseEnvelope deleteCollectCatalog(CollectCatalog collectCatalog) {
        if (collectCatalog == null) {
            return new ResponseEnvelope(false, "删除失败,缺少参数id!");
        }
        if (collectCatalog.getId() == null || collectCatalog.getId() <= 0) {
            return new ResponseEnvelope(false, "删除失败,缺少参数id!");
        }
        if (collectCatalog.getUserId() == null || collectCatalog.getUserId() <= 0) {
            return new ResponseEnvelope(false, "删除失败,缺少参数userId!");
        }

        List<CollectCatalog> list = this.getList(collectCatalog);
        if (list == null || list.size() <= 0) {
            return new ResponseEnvelope(false, "删除失败,该目录不存在");
        }

        if ("默认".equals(list.get(0).getCatalogName())) {
            return new ResponseEnvelope(false, "默认目录 无法删除");
        }
        this.delete(collectCatalog.getId());
        /*查询该用户的  默认收藏夹  Id*/
        CollectCatalogSearch collectCatalogSearch = new CollectCatalogSearch();
        collectCatalogSearch.setUserId(collectCatalog.getUserId());
        collectCatalogSearch.setCatalogName("默认");
        List<CollectCatalog> CollectCatalogList = this.getPaginatedList(collectCatalogSearch);
        int defaultId = CollectCatalogList.get(0).getId();
		
		/*通过用户id 和 收藏夹Id，将将收藏夹ID  改为 默认Id*/
        UserProductCollectSearch userProductCollectSearch = new UserProductCollectSearch();
        userProductCollectSearch.setUserId(collectCatalog.getUserId());
        userProductCollectSearch.setCollectCatalogId(collectCatalog.getId());
        userProductCollectSearch.setDefaultId(defaultId);
        userProductCollectService.transferCollection(userProductCollectSearch);   /*转移方法*/
        return new ResponseEnvelope(true);
    }


}
