package com.sandu.rendermachine.dao.system;

import com.sandu.rendermachine.model.system.SysDicitonaryOptimize;
import com.sandu.rendermachine.model.system.SysDictionarySearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 11:48 2018/5/14 0014
 * @Modified By:
 */
@Repository
public interface SysDictionaryMapper {

    List<SysDicitonaryOptimize> getListOptimize(SysDictionarySearch sysDictionarySearch);

    List<SysDicitonaryOptimize> findAll(SysDictionarySearch sysDictionarySearch);

}
