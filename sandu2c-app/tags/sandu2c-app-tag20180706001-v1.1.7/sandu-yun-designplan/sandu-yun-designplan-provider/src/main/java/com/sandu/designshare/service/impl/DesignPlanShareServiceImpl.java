package com.sandu.designshare.service.impl;

import com.sandu.common.properties.ResProperties;
import com.sandu.designplan.model.Group;
import com.sandu.designplan.model.GroupDetail;
import com.sandu.designplan.model.ResRenderPic;
import com.sandu.designshare.dao.DesignPlanShareMapper;
import com.sandu.designshare.model.SysUserGroup;
import com.sandu.designshare.service.DesignPlanShareService;
import com.sandu.render.model.RenderTypeCode;
import com.sandu.system.service.ResRenderPicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-13.
 * Rename to DesignPlanShareService From SysUserGroupService by pengxuangang on 20171113.
 * 设计方案分享服务
 *
 * @date 20171113
 * @auth pengxuangang
 */
@Service("designPlanShareService")
public class DesignPlanShareServiceImpl implements DesignPlanShareService {

    @Autowired
    private DesignPlanShareMapper designPlanShareMapper;
    @Autowired
    private ResRenderPicService resRenderPicService;

    /**
     * doShare 分享组里面的设计方案渲染图
     *
     * @param groupBusinessId
     * @return List    返回类型
     * @Exception 异常对象
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    @Override
    public Group doShare(String groupBusinessId) {
        if (StringUtils.isEmpty(groupBusinessId)) {
            return null;
        }
        List<ResRenderPic> list = designPlanShareMapper.get720ResRenderPicByGid(groupBusinessId);
        List retList = new ArrayList<GroupDetail>();
        String coverPath = null;//分享缩略图片
        for (ResRenderPic pic : list) {
            if (!ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY.equals(pic.getFileKey())) {// 不是缩略图，缩略图一定是有pid值的
                continue;
            }

            if (pic.getPid() == null) {
                continue;
            }

            if (RenderTypeCode.COMMON_720_LEVEL == pic.getRenderingType()) {
                // 普通渲染，又称单点渲染，此时pid指向的原图id

                if (pic.getId() == null) {
                    continue;
                }

                if (StringUtils.isEmpty(coverPath)) {
                    coverPath = pic.getPicPath();
                }

                com.sandu.designplan.model.ResRenderPic org_pic = resRenderPicService.get(pic.getPid());
                GroupDetail groupDetail = new GroupDetail();
                groupDetail.setGroupName(pic.getPicName());
                groupDetail.setPicId(pic.getPid());

                if (org_pic != null) {//原图路径
                    groupDetail.setPicPath(org_pic.getPicPath());
                }

                groupDetail.setPlanId(pic.getDesignSceneId());
                groupDetail.setThumbDesc(pic.getRemark());
                groupDetail.setThumbId(pic.getId());
                groupDetail.setThumbPath(pic.getPicPath());
                groupDetail.setType(pic.getRenderingType());
                retList.add(groupDetail);
            }
        }

        int count = designPlanShareMapper.countListShare(groupBusinessId);
        SysUserGroup userGroup = new SysUserGroup();
        userGroup.setBid(groupBusinessId);
        userGroup.setShareTimes(count);
        designPlanShareMapper.addShareTimes(userGroup);//分享次数+1

        Group group = new Group();
        group.setList(retList);
        group.setThumbPath(coverPath);
        return group;
    }

}
