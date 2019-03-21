package com.sandu.service.customer.imp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.mchange.v2.codegen.bean.BeangenUtils;
import com.sandu.api.customer.input.query.CustomerBaseInfoQuery;
import com.sandu.api.customer.model.CustomerBaseInfo;
import com.sandu.api.customer.output.CustomerVO;
import com.sandu.api.customer.service.CustomerBaseInfoService;
import com.sandu.service.customer.dao.CustomerBaseInfoMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sandu-lipeiyuan
 */
@Service("customerBaseInfoService")
@Slf4j
public class CustomerBaseInfoServiceImpl implements CustomerBaseInfoService {


	private static final Integer QUERY_CUSTOMER_PER_PAGE = 500;

	private ThreadFactory proPool = new ThreadFactoryBuilder().setNameFormat("product biz server pool").build();
	private final ThreadPoolExecutor executorService =
		new ThreadPoolExecutor(
				30,
				60,
				30,
				TimeUnit.SECONDS,
				new LinkedBlockingDeque<>(512),
				proPool,
				new ThreadPoolExecutor.AbortPolicy()
	);
	
    @Resource
    private CustomerBaseInfoMapper customerBaseInfoMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerBaseInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(CustomerBaseInfo record) {
        return customerBaseInfoMapper.insertSelective(record);
    }

    @Override
    public CustomerBaseInfo selectByPrimaryKey(Long id) {
        return customerBaseInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CustomerBaseInfo record) {
        return customerBaseInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<CustomerBaseInfo> selectByOption(CustomerBaseInfoQuery query) {

        return customerBaseInfoMapper.selectByOption(query);
    }

    @Override
    public int countByOption(CustomerBaseInfoQuery query) {
        Integer count = customerBaseInfoMapper.countByOption(query);
        if(count == null){
            return 0;
        }
        return count;
    }

    @Override
    public PageInfo<CustomerBaseInfo> pageSelectByOption(CustomerBaseInfoQuery query, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<CustomerBaseInfo> customerBaseInfoList =customerBaseInfoMapper.queryByOption(query);
        return new PageInfo<>(customerBaseInfoList);
    }

    @Override
    public int updateByUserId(CustomerBaseInfo record) {
        return customerBaseInfoMapper.updateByUserId(record);
    }

    @Override
    public List<CustomerBaseInfo> queryNotAllotCus() {
        return customerBaseInfoMapper.queryNotAllotCus();
    }

	@Override
	public List<CustomerVO> queryExportData(CustomerBaseInfoQuery customerBaseInfoQuery) {
        Integer count = customerBaseInfoMapper.countByOption(customerBaseInfoQuery);
        if(count == null){
            return Collections.emptyList();
        }
        //默认导出2000条
        if(count <= QUERY_CUSTOMER_PER_PAGE) {
        	List<CustomerVO>  datas = customerBaseInfoMapper.queryExportData(customerBaseInfoQuery);
        	return datas;
        }
        List<CustomerVO>  allDatas = new ArrayList<CustomerVO>();
    	int threadNum = (int) Math.ceil(count * 1.0 / QUERY_CUSTOMER_PER_PAGE);
		CountDownLatch doneSignal = new CountDownLatch(threadNum);
		for (int i = 0; i < threadNum; i++) {
			int pageNo = i;
			executorService.submit(
				() -> {
					try {
						log.info("start queryExportData in Thread :{},pageNo{}", Thread.currentThread().getName(),pageNo);
						CustomerBaseInfoQuery query = new CustomerBaseInfoQuery();
						BeanUtils.copyProperties(customerBaseInfoQuery, query);
						query.setStart(pageNo);
						query.setLimit(QUERY_CUSTOMER_PER_PAGE);
						List<CustomerVO> tmpData = customerBaseInfoMapper.queryExportData(query);
						allDatas.addAll(tmpData);
						log.info("end queryExportData info in Thread :{}", Thread.currentThread().getName());
					} catch (Exception e) {
						log.error("查询导出数据异常:{}",e);
					} finally {
						doneSignal.countDown();
					}
				});
			}
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return allDatas;
	}
}