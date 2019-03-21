package com.sandu.home.service.impl;

import com.sandu.home.dao.HouseSpaceNewMapper;
import com.sandu.home.model.HouseSpaceNew;
import com.sandu.home.model.search.HouseSpaceNewSearch;
import com.sandu.home.service.HouseSpaceNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @version V1.0
 * @Title: HouseSpaceNewServiceImpl.java
 * @Package com.sandu.business.service.impl
 * @Description:户型空间-户型空间U3DServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2017-05-18 15:19:17
 */
@Service("houseSpaceNewService")
public class HouseSpaceNewServiceImpl implements HouseSpaceNewService {

    @Autowired
    private HouseSpaceNewMapper houseSpaceNewMapper;


    /**
     * 新增数据
     *
     * @param houseSpaceNew
     * @return int
     */
    @Override
    public int add(HouseSpaceNew houseSpaceNew) {
        houseSpaceNewMapper.insertSelective(houseSpaceNew);
        return houseSpaceNew.getId();
    }

    /**
     * 更新数据
     *
     * @param houseSpaceNew
     * @return int
     */
    @Override
    public int update(HouseSpaceNew houseSpaceNew) {
        return houseSpaceNewMapper
                .updateByPrimaryKeySelective(houseSpaceNew);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return houseSpaceNewMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return HouseSpaceNew
     */
    @Override
    public HouseSpaceNew get(Integer id) {
        return houseSpaceNewMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param houseSpaceNew
     * @return List<HouseSpaceNew>
     */
    @Override
    public List<HouseSpaceNew> getList(HouseSpaceNew houseSpaceNew) {
        return houseSpaceNewMapper.selectList(houseSpaceNew);
    }

    /**
     * 获取数据数量
     *
     * @return int
     */
    @Override
    public int getCount(HouseSpaceNewSearch houseSpaceNewSearch) {
        return houseSpaceNewMapper.selectCount(houseSpaceNewSearch);
    }


    /**
     * 分页获取数据
     *
     * @return List<HouseSpaceNew>
     */
    @Override
    public List<HouseSpaceNew> getPaginatedList(HouseSpaceNewSearch houseSpaceNewSearch) {
        return houseSpaceNewMapper.selectPaginatedList(houseSpaceNewSearch);
    }

    /**
     * 通过户型ID获取该户型下所有的空间类型
     *
     * @param houseId 户型ID
     * @return
     */
    @Override
    public List<String> getSpaceTypeListByHouseId(Integer houseId) {
        return houseSpaceNewMapper.getSpaceTypeListByHouseId(houseId);
    }

    /**
     * 通过户型ID获取该户型下有哪些空间类型 
     * add by yangzhun
     * 2018年1月17日15:09:55
     */
	@Override
	public List<String> getSpaceTypeByHouseId(Integer houseId) {
		// TODO Auto-generated method stub
		return houseSpaceNewMapper.getSpaceTypeByHouseId(houseId);
	}

}
