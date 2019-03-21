package com.sandu.web.designplan.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.exception.BizException;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.designplan.model.DesignPlanLike;
import com.sandu.designplan.service.DesignPlanLikeService;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.system.service.NodeInfoBizService;
import com.sandu.user.model.UserSO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 6:10 2018/1/8 0008
 * @Modified By:
 */
@Controller
@RequestMapping("/v1/miniprogram/designPlanLike")
public class DesignPlanLikeController {

    private static Logger logger = Logger.getLogger(DesignPlanLikeController.class);
    private final static String CLASS_LOG_PREFIX = "[方案点赞服务]";

    @Autowired
    private DesignPlanLikeService designPlanLikeService;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private NodeInfoBizService nodeInfoBizService;
    @Value("${sanducloudhome.company.code}")
    private String companyCode;
    @Value("${miniprogram.platform.code}")
    private String miniProgram;

  /*  @Autowired
    private RabbitService rabbitService;*/

    /**
     * 方案点赞或取消点赞
     *
     * @param designPlanLike
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/setLikeOrDislike")
    @ResponseBody
    public ResponseEnvelope setLikeOrDislike(@RequestBody DesignPlanLike designPlanLike, HttpServletRequest request) {
        if (designPlanLike == null || designPlanLike.getDesignId() == null || designPlanLike.getStatus() == null) {
            return new ResponseEnvelope(false, "参数传递有误！");
        }
        // 平台编码
        String platformCode = request.getHeader("Platform-Code");
        if (platformCode == null || "".equals(platformCode)) {
            return new ResponseEnvelope(false, "平台编码为空");
        }
        // 公司
        BaseCompany baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
        if (null == baseCompany) {
            baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
            if (null == baseCompany) {
                return new ResponseEnvelope(false, "未获取到公司信息");
            }
        }
        UserSO userSo = null;
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser != null) {
            userSo = new UserSO();
            userSo.setUserId(loginUser.getId());
            userSo.setUserName(loginUser.getLoginName());
            userSo.setUserMobile(loginUser.getLoginPhone());
            userSo.setUserType(loginUser.getUserType());
        }else {
            return new ResponseEnvelope(false, "请登录!!!");
        }

        long likeCount = 0;
        if (designPlanLike.getDesignPlanType() == 1){
            if (miniProgram.equals(platformCode) && companyCode.equals(baseCompany.getCompanyCode())){
                // 获取节点ID
                Integer nodeId = nodeInfoBizService.registerNodeInfo(designPlanLike.getDesignId(), NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED);
                // 处理操作
                likeCount = nodeInfoBizService.updateUserNodeRelStatus(loginUser.getId(),
                        nodeId,
                        designPlanLike.getDesignId(),
                        NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED,
                        NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_LIKE,
                        designPlanLike.getStatus().byteValue());
            }else {
                try {
                    likeCount = designPlanLikeService.setLikeOrDislike(UserObject.parseUserSoToLoginUser(userSo), designPlanLike);
                } catch (BizException e) {
                    return new ResponseEnvelope(false, e.getMessage());
                }
            }
        }else  if (designPlanLike.getDesignPlanType() == 2){
            if (miniProgram.equals(platformCode) && companyCode.equals(baseCompany.getCompanyCode())){
                // 获取节点ID
                Integer nodeId = nodeInfoBizService.registerNodeInfo(designPlanLike.getDesignId(),  NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_FULL_HOUSE);
                // 处理操作
                likeCount = nodeInfoBizService.updateUserNodeRelStatus(loginUser.getId(),
                        nodeId,
                        designPlanLike.getDesignId(),
                        NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_FULL_HOUSE,
                        NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_LIKE,
                        designPlanLike.getStatus().byteValue());
            }else {
                try {
                    designPlanLike.setFullHouseDesignPlanId(designPlanLike.getDesignId());
                    likeCount = designPlanLikeService.setFullHouseLikeOrDislike(UserObject.parseUserSoToLoginUser(userSo), designPlanLike);
                } catch (BizException e) {
                    return new ResponseEnvelope(false, e.getMessage());
                }
            }
        }
        return new ResponseEnvelope(true, likeCount);
    }

}
