package com.sandu.service.product.impl.biz;

import com.sandu.api.product.model.ProductControl;
import com.sandu.api.product.service.ProductControlService;
import com.sandu.api.product.service.ProductService;
import com.sandu.api.product.service.biz.ProductControlBizService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sandu
 */
@Service
public class ProductControlBizServiceImpl implements ProductControlBizService {
    @Autowired
     private ProductControlService productControlService;
    @Autowired
    private ProductService productService;
    @Override
    public int updateBiz(ProductControl productControl) {
        int ret = 0;
        List<Integer> list = new ArrayList<>();
        list.add(productControl.getProductId().intValue());
        List<Integer> ids = productControlService.getExistProductByProductId(list);
        if(ids.isEmpty()){
            //插入数据
            ProductControl pcon = new ProductControl();
            BeanUtils.copyProperties(productControl,pcon);
            pcon.setModelNumber(productControl.getModelNumber());
            pcon.setProductId(productControl.getProductId());
            pcon.setGmtCreate(new Date());
            pcon.setGmtModified(new Date());
            pcon.setSecrecy((byte) 0);
            ret = productControlService.saveProductControl(pcon);
        }else{
            //更新数据
            ret = productControlService.update(productControl);
        }
        return ret;
    }
}
