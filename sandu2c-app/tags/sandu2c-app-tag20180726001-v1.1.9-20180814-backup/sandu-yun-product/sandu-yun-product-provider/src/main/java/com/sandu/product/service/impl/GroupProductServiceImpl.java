package com.sandu.product.service.impl;

import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandu.designconfig.service.DesignRulesService;
import com.sandu.product.dao.GroupProductMapper;
import com.sandu.product.dao.ProductGroupDao;
import com.sandu.product.model.GroupProduct;
import com.sandu.product.service.BaseProductService;
import com.sandu.product.service.GroupProductDetailsService;
import com.sandu.product.service.GroupProductService;
import com.sandu.product.service.ProductAttributeService;
import com.sandu.system.service.ResFileService;
import com.sandu.system.service.ResModelService;
import com.sandu.system.service.ResTextureService;
import com.sandu.system.service.SysDictionaryService;

/**   
 * @Title: GroupProductServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-产品组合主表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:52:57
 * @version V1.0   
 */
@Service("groupProductService")
public class GroupProductServiceImpl implements GroupProductService {

    @Autowired
    private GroupProductMapper groupProductMapper;


    /**
     * 新增数据
     *
     * @param groupProduct
     * @return  int 
     */
    @Override
    public int add(GroupProduct groupProduct) {
        groupProductMapper.insertSelective(groupProduct);
        return groupProduct.getId();
    }
    /**
     * 组合收藏状态
     * @author louxinhua
     *
     */
    private enum GROUP_COLLECTED_STATUS {
        not_collect,  	// 没有收藏
        collected		// 已经收藏
    }

    /**
     *    更新数据
     *
     * @param groupProduct
     * @return  int 
     */
    @Override
    public int update(GroupProduct groupProduct) {
        return groupProductMapper
                .updateByPrimaryKeySelective(groupProduct);
    }
    
    /**
     *    删除数据
     *
     * @param id
     * @return  int 
     */
    @Override
    public int delete(Integer id) {
        return groupProductMapper.deleteByPrimaryKey(id);
    }

    /**
     *    获取数据详情
     *
     * @param id
     * @return  GroupProduct 
     */
    @Override
    public GroupProduct get(Integer id) {
        return groupProductMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     * 
     * @param  groupProduct
     * @return   List<GroupProduct>
     */
    @Override
    public List<GroupProduct> getList(GroupProduct groupProduct) {
        return groupProductMapper.selectList(groupProduct);
    }
    
    /**
     * 通过id查找组合(关联查找更多信息,比如:品牌名,风格名)
     * @author huangsongbo
     * @param groupId
     * @return
     */
    public GroupProduct getMoreInfoById(Integer groupId) {
        return groupProductMapper.getMoreInfoById(groupId);
    }

}
