package com.sandu.service.grouppurchase.service.impl.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.grouppurchase.bo.GoodsSkuBO;
import com.sandu.api.grouppurchase.input.GroupPurchaseActivityAdd;
import com.sandu.api.grouppurchase.input.GroupPurchaseActivityQuery;
import com.sandu.api.grouppurchase.input.GroupPurchaseActivityUpdate;
import com.sandu.api.grouppurchase.model.GroupPurchaseActivity;
import com.sandu.api.grouppurchase.service.GroupPurchaseActivityService;
import com.sandu.api.grouppurchase.service.biz.GroupPurchaseActivityBizService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.pay.goods.model.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 16:16 2018/12/6
 */

@Slf4j
@Service("groupPurchaseActivityBizService")
public class GroupPurchaseActivityBizServiceImpl implements GroupPurchaseActivityBizService {

    @Autowired
    private GroupPurchaseActivityService groupPurchaseActivityService;

    @Resource
    private SysUserService sysUserService;

    @Value("${group.purchase.activity.path}")
    private String activityPath;

    @Override
    public int create(GroupPurchaseActivityAdd input) {
        GroupPurchaseActivity activity = new GroupPurchaseActivity();
        BeanUtils.copyProperties(input, activity);
        activity.setActivityStatus(Byte.valueOf(input.getActivityStatus()));
        SysUser creator = sysUserService.getUserById(Long.valueOf(input.getCreator()));
        activity.setCreator(creator.getUserName());
        activity.setCompanyId(creator.getCompanyId().intValue());
        groupPurchaseActivityService.create(activity);
        return activity.getId().intValue();
    }

    @Override
    public PageInfo<GroupPurchaseActivity> query(GroupPurchaseActivityQuery query) {
        List<GroupPurchaseActivity> lists = groupPurchaseActivityService.findAll(query).getList();
        lists.forEach(result -> {
            result.setActivityPath(activityPath+result.getSpuId()+"&groupId="+result.getId());
        });
        return new PageInfo<>(lists);
    }

    @Override
    public int updateStatus(String id, String activityStatus) {
        return groupPurchaseActivityService.updateStatus(id,activityStatus);
    }

    @Override
    public int deleteActivity(String id) {
        return groupPurchaseActivityService.deleteActivity(id);
    }

    @Override
    public GroupPurchaseActivity getByActivityId(int id) {
        return groupPurchaseActivityService.getByActivityId(id);
    }

    @Override
    public int updateActivity(GroupPurchaseActivityUpdate update) {
        GroupPurchaseActivity activity = new GroupPurchaseActivity();
        BeanUtils.copyProperties(update, activity);
        activity.setActivityStatus(update.getActivityStatus()!=null?Byte.valueOf(update.getActivityStatus()):null);
        SysUser creator = sysUserService.getUserById(Long.valueOf(update.getModifier()));
        activity.setModifier(creator.getUserName());
        activity.setGmtModified(new Date());
        activity.setId(update.getActivityId());
        return groupPurchaseActivityService.update(activity);
    }
}
