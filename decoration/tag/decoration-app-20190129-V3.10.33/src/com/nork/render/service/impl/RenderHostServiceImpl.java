package com.nork.render.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.render.dao.RenderHostMapper;
import com.nork.render.model.RenderHost;
import com.nork.render.model.RenderHostCodes;
import com.nork.render.model.search.RenderHostSearch;
import com.nork.render.model.search.RenderTaskSearch;
import com.nork.render.service.RenderHostService;
import com.nork.render.service.RenderTaskService;

/**   
 * @Title: RenderHostServiceImpl.java 
 * @Package com.nork.render.service.impl
 * @Description:渲染-渲染主机ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-01-15 17:45:34
 * @version V1.0   
 */
@Service("renderHostService")
public class RenderHostServiceImpl implements RenderHostService {
	private static Logger logger = Logger
			.getLogger(RenderHostServiceImpl.class);
	@Autowired
	private RenderHostMapper renderHostMapper;
	@Autowired
	private RenderTaskService renderTaskService;

	/**
	 * 新增数据
	 *
	 * @param renderHost
	 * @return  int 
	 */
	@Override
	public int add(RenderHost renderHost) {
		renderHostMapper.insertSelective(renderHost);
		return renderHost.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param renderHost
	 * @return  int 
	 */
	@Override
	public int update(RenderHost renderHost) {
		return renderHostMapper
				.updateByPrimaryKeySelective(renderHost);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return renderHostMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  RenderHost 
	 */
	@Override
	public RenderHost get(Integer id) {
		return renderHostMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  renderHost
	 * @return   List<RenderHost>
	 */
	@Override
	public List<RenderHost> getList(RenderHost renderHost) {
	    return renderHostMapper.selectList(renderHost);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  renderHost
	 * @return   int
	 */
	@Override
	public int getCount(RenderHostSearch renderHostSearch){
		return  renderHostMapper.selectCount(renderHostSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  renderHost
	 * @return   List<RenderHost>
	 */
	@Override
	public List<RenderHost> getPaginatedList(
			RenderHostSearch renderHostSearch) {
		return renderHostMapper.selectPaginatedList(renderHostSearch);
	}
	
	/**
	 * 主机分配（租用主机+云渲染主机）
	 */
	@Override
	public RenderHost allocationRenderHost() {
		//获取可以使用的租用主机列表
		int hostCount = 0;
		RenderHostSearch renderHostSearch = new RenderHostSearch();
		renderHostSearch.setStatus(RenderHostCodes.CAN_USE);
		renderHostSearch.setType(RenderHostCodes.HIRE_HOST_TYPE);
		
		hostCount = this.getCount(renderHostSearch);
		
		RenderHost availableHost = new RenderHost();
		if(hostCount > 0){
			//获取任务量最小的一台可用主机信息
			availableHost = this.renderHostMapper.selectMinTaskAvailableHost();
		}else{
			//可用渲染主机为零
			//TODO  发消息
			return null;
		}
		
		//判断租用主机是否饱和
		int usingTaskCount = 0;
		RenderTaskSearch renderTaskSearch = new RenderTaskSearch();
		if(availableHost != null){
				renderTaskSearch.setHostId(availableHost.getId());//主机id
				//获取该主机正在渲染的任务数量
				usingTaskCount = renderTaskService.getUsingTaskCount(renderTaskSearch);
				//该主机的任务量未饱和则返回一台主机信息
				if(availableHost.getAbilityMaxNumTask() == null){
					logger.error("该主机："+availableHost.getIp()+":参数配置错误：未设置最大任务量");
				}
				else{
					if(usingTaskCount < availableHost.getAbilityMaxNumTask()){
						return availableHost;
					}
				}
			return null;
		}
		else{
			logger.error("渲染主机饱和");
			// TODO:走云渲染
		}
		return null;
	}

}
