package com.sandu.web.comment.controller;

import com.nork.common.model.LoginUser;
import com.sandu.comment.input.WxCommentRecordAdd;
import com.sandu.comment.input.WxCommentRecordQuery;
import com.sandu.comment.model.WxCommentRecord;
import com.sandu.comment.output.WxCommentRecordVO;
import com.sandu.comment.service.WxCommentRecordBizService;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.BadWordUtil;
import com.sandu.common.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Yuxc
 * @Description:
 * @Date: 2019/1/2
 */

@Log4j2
@RestController
@RequestMapping("/v1/miniprogram/comment")
public class WxCommentController {

    @Resource
    private WxCommentRecordBizService wxCommentRecordBizService;

    @ApiOperation(value = "获取评论列表", response = ResponseEnvelope.class)
    @GetMapping("/list")
    public ResponseEnvelope getCommentsList(WxCommentRecordQuery query) {
        if (query.getPlanId() == null || query.getPlanId() <= 0) {
            log.error("参数错误：planId不能为空！ query => {}", query);
            return new ResponseEnvelope(false, "参数错误：planId不能为空！");
        }
        query.setType(1);

        // 商品记录总数
        Integer total = wxCommentRecordBizService.getCommentListCount(query);
        log.info("评论总数量,total:{}", total);
        //分页
        if(query.getPageNo() == null) {
            query.setPageNo(1);
            query.setPageSize(10);
        }
        if (total > 0) {
            List<WxCommentRecordVO> commentsList = wxCommentRecordBizService.queryAll(query);
            log.info("查询商品列表结果:{}", commentsList);
            if (commentsList == null || commentsList.size() == 0) {
                return new ResponseEnvelope(false, "查询评论列表出错");
            }

            // 敏感字过滤
            commentsList.forEach(item -> item.setComment(BadWordUtil.replaceBadWord(item.getComment(), 1, "*")));

            return new ResponseEnvelope(true, "success", commentsList, total);
        }
        return new ResponseEnvelope(true, "暂无评论");
    }

    @ApiOperation(value = "获取评论列表", response = ResponseEnvelope.class)
    @GetMapping("/order/list")
    public ResponseEnvelope listOrderComment(WxCommentRecordQuery query) {
        if (query.getSpuId() == null || query.getSpuId() <= 0) {
            log.error("参数错误：spuId不能为空！ query => {}", query);
            return new ResponseEnvelope(false, "参数错误：spuId不能为空！");
        }
        // 订单
        query.setType(0);

        // 商品记录总数
        Integer allTotal = wxCommentRecordBizService.getCommentListCount(query);

        //分页
        if (query.getPageNo() == null) {
            query.setPageNo(1);
            query.setPageSize(10);
        }

        if (allTotal > 0) {
            List<WxCommentRecordVO> commentsList = wxCommentRecordBizService.queryAll(query);
            if (commentsList == null || commentsList.size() == 0) {
                return new ResponseEnvelope(false, "暂无数据");
            }

            // 敏感字过滤
            commentsList.forEach(item -> item.setComment(BadWordUtil.replaceBadWord(item.getComment(), 1, "*")));

            Map<String, Object> map = new HashMap<>();
            map.put("allTotal", allTotal);

            // 有图的评论总数
            query.setIsPicComment(query.getIsPicComment() == null ? 1 : query.getIsPicComment());
            Integer picTotal = wxCommentRecordBizService.getCommentListCount(query);

            map.put("picTotal", picTotal);
            map.put("data", commentsList);

            return new ResponseEnvelope(true, "success", map);
        }

        return new ResponseEnvelope(true, "暂无评论");
    }

    @ApiOperation(value = "新建评论", response = ResponseEnvelope.class)
    @PostMapping("/plan/add")
    public ResponseEnvelope createComment(@RequestBody WxCommentRecordAdd input) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("方案评论：请先登录");
            return new ResponseEnvelope(false, "请先登录");
        }

        if (input.getPlanId() == null) {
            log.error("planId => {} 不能为空", input.getPlanId());
            return new ResponseEnvelope<>(false, "参数错误！");
        }

        if (StringUtils.isEmpty(input.getComment())) {
            log.error("评论内容不能为空, comment => {}", input.getComment());
            return new ResponseEnvelope<>(false, "评论内容不能为空！");
        }
        if (input.getComment().length() > 500) {
            log.error("评论字数不能超过500, comment => {}", input.getComment());
            return new ResponseEnvelope<>(false, "评论字数不能超过500！");
        }

        if ((input.getUserId() == null || input.getUserId() <= 0) && loginUser.getId() != null) {
            input.setUserId(loginUser.getId().longValue());
        }

        input.setCreator(input.getUserId().intValue());

        Integer commentId = wxCommentRecordBizService.createComment(input);

        if (commentId > 0) {
            return new ResponseEnvelope<>(true, "评论成功!", commentId);
        }

        return new ResponseEnvelope<>(false, "输入数据有误,评论失败");
    }

    @ApiOperation(value = "新建评论", response = ResponseEnvelope.class)
    @PostMapping("/order/add")
    public ResponseEnvelope createComment(@RequestBody List<WxCommentRecordAdd> input) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.error("订单评论：请先登录");
            return new ResponseEnvelope(false, "请先登录");
        }

        for (WxCommentRecordAdd record : input) {
            if (record.getProductId() == null) {
                log.error("spuId => {} 不能为空", record.getSpuId());
                return new ResponseEnvelope<>(false, "参数错误！");
            }

            if (record.getDescLevel() == null || record.getDescLevel() < 1 || record.getDescLevel() > 5) {
                log.error("评论星级错误，descLevel => {}", record.getDescLevel());
                return new ResponseEnvelope<>(false, "评论星级错误！");
            }

            if (StringUtils.isEmpty(record.getComment())) {
                log.error("评论内容不能为空, comment => {}", record.getComment());
                return new ResponseEnvelope<>(false, "评论内容不能为空！");
            }
            if (record.getComment().length() > 500) {
                log.error("评论字数不能超过500, comment => {}", record.getComment());
                return new ResponseEnvelope<>(false, "评论字数不能超过500！");
            }
            if ((record.getUserId() == null || record.getUserId() <= 0) && loginUser.getId() != null) {
                record.setUserId(loginUser.getId().longValue());
            }

            record.setCreator(record.getUserId().intValue());
        }

//        WxCommentRecord record = wxCommentRecordBizService.checkCommented(input.get(0).getOrderId(), null);
//        if (record != null) {
//            log.error("订单已评论，不能重复评论！input => {}", input);
//            return new ResponseEnvelope(false, "订单已评论，不能重复评论！");
//        }

        Integer updateCount = wxCommentRecordBizService.createComment(input);
        if (updateCount > 0) {
            return new ResponseEnvelope<>(true, "评论成功!");
        }

        return new ResponseEnvelope<>(false, "评论失败");
    }

    @ApiOperation(value = "删除评论", response = ResponseEnvelope.class)
    @DeleteMapping("/delete")
    public ResponseEnvelope deleteComment(String id) {

        int result = wxCommentRecordBizService.deleteComment(Long.valueOf(id));
        if (result > 0) {
            return new ResponseEnvelope<>(true, "成功!");
        }

        return new ResponseEnvelope<>(false, "失败!");
    }

    @ApiOperation(value = "评论点赞/取消", response = ResponseEnvelope.class)
    @PutMapping("/changePraiseState")
    public ResponseEnvelope changePraiseState(Long userId, Long commentId, Long type) {
        //修改点赞状态
        int result = wxCommentRecordBizService.changePraiseState(userId, commentId, type);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "成功!");
        }
        return new ResponseEnvelope<>(false, "失败!");
    }

    /**
     *
     * @param validResult
     * @return
     */
    private ResponseEnvelope processValidError(BindingResult validResult) {
        StringBuilder error = new StringBuilder();
        validResult.getAllErrors().forEach((e) -> {
            error.append(e.getDefaultMessage());
            error.append("; ");
        });

        return new ResponseEnvelope<>(false, error.toString());
    }
}
