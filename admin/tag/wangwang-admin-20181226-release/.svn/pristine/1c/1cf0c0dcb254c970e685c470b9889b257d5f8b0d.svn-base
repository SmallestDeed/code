package com.sandu.service.goods.impl;

import com.sandu.api.goods.model.ResPic;
import com.sandu.api.goods.output.PicVO;
import com.sandu.api.goods.service.PicService;
import com.sandu.service.goods.dao.ResPicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("picService")
public class PicServiceImpl implements PicService
{
    @Autowired
    private ResPicMapper resPicMapper;

    @Override
    public Integer saveImage(ResPic resPic)
    {
        int num = resPicMapper.insertSelective(resPic);
        if (num <= 0)
        {
            return 0;
        }
        return resPicMapper.getIdBySysCode(resPic.getSysCode());
    }

    @Override
    public List<PicVO> getPicsByIds(List<Integer> ids)
    {
        if (ids != null && ids.size() > 0)
        {
            return resPicMapper.getPicsByIds(ids);
        }else
        {
            return null;
        }

    }

    @Override
    public ResPic get(Integer id) {
        return resPicMapper.selectByPrimaryKey(id.longValue());
    }
}
