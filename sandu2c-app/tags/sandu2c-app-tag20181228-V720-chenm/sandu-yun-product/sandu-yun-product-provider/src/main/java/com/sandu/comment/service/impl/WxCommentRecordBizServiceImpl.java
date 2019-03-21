package com.sandu.comment.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sandu.comment.input.WxCommentRecordAdd;
import com.sandu.comment.input.WxCommentRecordQuery;
import com.sandu.comment.model.WxCommentPraiseRecord;
import com.sandu.comment.model.WxCommentRecord;
import com.sandu.comment.output.WxCommentRecordVO;
import com.sandu.comment.service.WxCommentPraiseRecordService;
import com.sandu.comment.service.WxCommentRecordBizService;
import com.sandu.comment.service.WxCommentRecordService;
import com.sandu.common.util.BadWordUtil;
import com.sandu.common.util.StringUtils;
import com.sandu.goods.model.BaseGoodsSKU;
import com.sandu.goods.service.BaseGoodsSKUService;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


@Slf4j
@Service("wxCommentRecordBizService")
public class WxCommentRecordBizServiceImpl implements WxCommentRecordBizService {

    @Resource
    private WxCommentRecordService wxCommentRecordService;

    @Resource
    private WxCommentPraiseRecordService wxCommentPraiseRecordService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private BaseGoodsSKUService baseGoodsSKUService;

    @Resource
    private ResPicService resPicService;

    @Override
    public int createComment(WxCommentRecordAdd input) {
        //数据转换
        WxCommentRecord record = new WxCommentRecord();
        BeanUtils.copyProperties(input, record);
        return wxCommentRecordService.create(record);
    }

    @Override
    @Transactional
    public int createComment(List<WxCommentRecordAdd> input) {
        if (input == null || input.isEmpty()) {
            return -1;
        }

        List<Integer> listProductId = input.stream().map(WxCommentRecordAdd::getProductId).collect(Collectors.toList());
        Map<Integer, BaseGoodsSKU> goodsSKUMap = this.goodsSKUMap(listProductId);

        int updateCount = 0;
        for (WxCommentRecordAdd record : input) {
            // SKU信息
            BaseGoodsSKU baseGoodsSKU = goodsSKUMap.get(record.getProductId());
            if (baseGoodsSKU == null) {
                log.error("评论的商品不存在，productId => {}", record.getProductId());
                throw new RuntimeException("评论的商品不存在");
            }
            record.setSkuId(baseGoodsSKU.getId().longValue());
            record.setSpuId(baseGoodsSKU.getSpuId().longValue());

            // 评论记录
            updateCount += this.createComment(record);

            // 订单的评论状态
            wxCommentRecordService.updateOrderCommentStatus(record.getOrderId(), 1);
        }

        return updateCount;
    }

    /**
     *
     * @param listProductId
     * @return
     */
    private Map<Integer, BaseGoodsSKU> goodsSKUMap(List<Integer> listProductId) {
        if (listProductId == null || listProductId.isEmpty()) {
            return new HashMap<>(0);
        }

        List<BaseGoodsSKU> listGoodsSKU = wxCommentRecordService.goodsSKUMap(listProductId);
        return listGoodsSKU.stream().collect(Collectors.toMap(BaseGoodsSKU::getProductId, sku -> sku, (p, n) -> n));
    }

    @Override
    public int deleteComment(Long id) {
        //删除评论
        Integer result = wxCommentRecordService.delete(id);
        if (result > 0) {
            //删除点赞表
            wxCommentPraiseRecordService.deleteByUserIdAndCommentId(null, id);
        }
        return result;
    }

    @Override
    public List<WxCommentRecordVO> queryAll(WxCommentRecordQuery query) {
        List<WxCommentRecord> wxCommentRecords = wxCommentRecordService.queryAll(query);
        if (wxCommentRecords == null || wxCommentRecords.isEmpty()) {
            log.info("通过 query => {} 未找到评论数据", query);
            return new ArrayList<>(0);
        }

        List<Long> userIds = wxCommentRecords.stream().filter(vo -> vo.getUserId() != null)
                .map(WxCommentRecord::getUserId).collect(toList());
        //根据userIds查所有user信息
        List<SysUser> userList = sysUserService.queryUserByIds(userIds);

        // SKU信息
        List<Long> listSku = wxCommentRecords.stream().filter(vo -> vo.getSkuId() != null)
                .map(WxCommentRecord::getSkuId).collect(Collectors.toList());
        Map<Integer, String> mapGoodsSKU = baseGoodsSKUService.mapGoodsSKU(listSku);

        // 评论图片
        Map<Integer, String> mapCommentPic = this.mapCommentPic(wxCommentRecords);

        List<WxCommentRecordVO> results = Lists.newArrayList();

        for (WxCommentRecord record : wxCommentRecords) {
            for (SysUser user : userList) {
                if (record.getUserId() == user.getId().longValue()) {
                    record.setHeadPath(user.getAtt3());
                    record.setNickName(user.getNickName() != null ? user.getNickName()
                            : user.getUserName() != null ? user.getUserName()
                            : user.getMobile());
                }
                if(record.getInitUserId() != null && record.getInitUserId() == user.getId().longValue()) {
                    record.setInitNickName(user.getNickName() != null ? user.getNickName()
                            : user.getUserName() != null ? user.getUserName()
                            : user.getMobile());
                }
            }

            if(record.getSkuId() != null) {
                record.setSkuInfo(StringUtils.isEmpty(mapGoodsSKU.get(record.getSkuId().intValue()))
                        ? mapGoodsSKU.get(record.getSkuId().intValue()) : "默认");
            }
            // 设置评论图片
            record.setCommentPics(getCommentPic(mapCommentPic, record.getPicIds()));

            // 放到最后面
            WxCommentRecordVO recordVO = new WxCommentRecordVO();
            BeanUtils.copyProperties(record, recordVO);
            results.add(recordVO);
        }

        return results;
    }

    /**
     * 评论图片
     *
     * @param wxCommentRecords
     * @return
     */
    private Map<Integer, String> mapCommentPic(List<WxCommentRecord> wxCommentRecords) {
        // 评论的图片
        List<List<String>> listPic = wxCommentRecords.stream().filter(vo -> !StringUtils.isEmpty(vo.getPicIds()))
                .map(vo -> Lists.newArrayList(vo.getPicIds().split(","))).collect(Collectors.toList());

        List<Integer> qListPic = new ArrayList<>();
        for (List<String> items : listPic) {
            items.forEach(item -> qListPic.add(Integer.valueOf(item)));
        }

        List<ResPic> listResPic = resPicService.getResPicByIds(qListPic);
        if (listResPic == null || listResPic.isEmpty()) {
            return new HashMap<>(0);
        }

        return listResPic.stream().collect(toMap(ResPic::getId, ResPic::getPicPath, (p, n) -> n));
    }

    /**
     * 设置评论图片
     *
     * @param map
     * @param picIds
     * @return
     */
    private List<String> getCommentPic(Map<Integer, String> map, String picIds) {
        if (!StringUtils.isEmpty(picIds)) {
            List<Integer> list = Lists.newArrayList(picIds.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
            List<String> picPaths = new ArrayList<>();
            list.forEach(item -> picPaths.add(map.get(item)));

            return picPaths;
        }

        return null;
    }

    @Override
    public Integer changePraiseState(Long userId, Long commentId, Long type) {
        Integer result;
        if (type == 0) {
            //取消点赞
            result = wxCommentPraiseRecordService.deleteByUserIdAndCommentId(userId, commentId);
            if (result > 0) {
                //评论点赞-1
                result = wxCommentRecordService.updatePraiseCount(commentId, -1);
            }
        } else {
            //点赞
            WxCommentPraiseRecord wxCommentPraiseRecord = new WxCommentPraiseRecord();
            wxCommentPraiseRecord.setCommentId(commentId);
            wxCommentPraiseRecord.setUserId(userId);
            result = wxCommentPraiseRecordService.addPraiseRecord(wxCommentPraiseRecord);
            if (result > 0) {
                //评论点赞+1
                result = wxCommentRecordService.updatePraiseCount(commentId, 1);
            }
        }
        return result;
    }

    @Override
    public Integer getCommentListCount(WxCommentRecordQuery query) {
        return wxCommentRecordService.getCommentListCount(query);
    }

    @Override
    public WxCommentRecord checkCommented(Long orderId, Long planId) {
        if (orderId != null || planId != null) {
            return wxCommentRecordService.checkCommented(orderId, planId);
        }

        throw new RuntimeException("参数异常：orderId或planId 为空");
    }
}
