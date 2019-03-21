package com.nork.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.base.service.BaseService;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.BaseProduct;

@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService {

	@Resource
	private BaseProductMapper baseProductMapper;
	@Override
	public void updateData(List<BaseProduct> productList){
		if( productList != null && productList.size() > 0 ) {
			for ( BaseProduct product : productList ) {
				baseProductMapper.updateByPrimaryKeySelective(product);
			}
		}
	}

}
