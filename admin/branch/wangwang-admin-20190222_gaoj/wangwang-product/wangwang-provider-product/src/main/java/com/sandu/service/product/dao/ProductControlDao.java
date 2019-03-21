package com.sandu.service.product.dao;

import com.sandu.api.product.model.ProductControl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface ProductControlDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProductControl record);

    int insertList(List<ProductControl> productContrels);

    ProductControl selectByPrimaryKey(Long id);

    int batchChangeProductSecrecy(@Param("ids") List<Integer> productIds,@Param("secrecy") Integer secrecy);

    int updateSelectiveByProductId(ProductControl record);

    List<Integer> getExistProductByProductId(List<Integer> productIds);

    ProductControl getByProductId(Long id);

}
