package com.sandu.rendermachine.dao.product;

import com.sandu.rendermachine.model.product.ProCategory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 3:55 2018/5/8 0008
 * @Modified By:
 */
@Repository
@Transactional
public interface ProCategoryMapper {


    List<ProCategory> selectList(ProCategory proCategory);

    ProCategory selectByPrimaryKey(Integer id);

    List<ProCategory> selectListV2(ProCategory proCategory);
}
