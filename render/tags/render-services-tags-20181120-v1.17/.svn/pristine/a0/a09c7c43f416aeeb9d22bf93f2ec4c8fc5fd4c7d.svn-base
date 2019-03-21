package com.nork.system.service.impl;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.system.dao.ResVersionMapper;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResVersion;
import com.nork.system.model.search.ResVersionSearch;
import com.nork.system.service.ResVersionService;

/**   
 * @Title: ResVersionServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统版本-系统版本资源表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-08-17 11:41:05
 * @version V1.0   
 */
@Service("resVersionService")
public class ResVersionServiceImpl implements ResVersionService {

	private ResVersionMapper resVersionMapper;
	
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	@Autowired
	public void setResVersionMapper(
			ResVersionMapper resVersionMapper) {
		this.resVersionMapper = resVersionMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param resVersion
	 * @return  int 
	 */
	@Override
	public int add(ResVersion resVersion) {
		resVersionMapper.insertSelective(resVersion);
		return resVersion.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resVersion
	 * @return  int 
	 */
	@Override
	public int update(ResVersion resVersion) {
		return resVersionMapper
				.updateByPrimaryKeySelective(resVersion);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		/*删除物理文件*/
		ResVersion resFile=get(id);
		if(resFile!=null){
			String filePath = app.getString("")+resFile.getFilePath();
			File file = new File(filePath);
			if(file.exists())
				file.delete();
		}
		return resVersionMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResVersion 
	 */
	@Override
	public ResVersion get(Integer id) {
		return resVersionMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  resVersion
	 * @return   List<ResVersion>
	 */
	@Override
	public List<ResVersion> getList(ResVersion resVersion) {
	    return resVersionMapper.selectList(resVersion);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resVersion
	 * @return   int
	 */
	@Override
	public int getCount(ResVersionSearch resVersionSearch){
		return  resVersionMapper.selectCount(resVersionSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resVersion
	 * @return   List<ResVersion>
	 */
	@Override
	public List<ResVersion> getPaginatedList(
			ResVersionSearch resVersionSearch) {
		return resVersionMapper.selectPaginatedList(resVersionSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
