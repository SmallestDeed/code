package com.sandu.search.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.ResponseEnvelope;
import com.nork.mobile.model.search.MobileSearchProductModel;
import com.nork.mobile.model.search.SearchProCategory;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.MobileWebSearchService;

/**
 * Created by yangz on 2017/12/12.
 */
@Controller
@RequestMapping("/{style}/web/mobile/search")
public class SearchForMobileAndWeb {
    private static final String TYPE_NOT_NULL = "参数type不能为空";
    private static final String MSGID_NOT_NULL = "参数msgId不能为空";
    public static final String CUSTOM_REFERER = "Custom-Referer";

    @Autowired
    private MobileWebSearchService mobileWebSearchService;

    private static Logger logger =
            Logger.getLogger(SearchForMobileAndWeb.class);


    /**
     * 根据分类查询产品接口 主逻辑放在service
     *
     * @param request
     * @param model
     * @return Object
     */
    @RequestMapping(value = "/searchProduct")
    @ResponseBody
    public Object searchProductV6(@PathVariable String style,
                                  @RequestBody MobileSearchProductModel model, HttpServletRequest request) {

        Integer userId = SystemCommonUtil.getUserIdByAuthorization(request);
        model.setUserId(userId);
        
        
        String platformCode = request.getHeader("Platform-Code");
        model.setPlatformCode(platformCode);

        String mediaType = SystemCommonUtil.getMediaType(request);

        return mobileWebSearchService.searchProduct(model, mediaType,style);
    }

    /**
     * 分类查询接口供移动端调用
     *
     * @return
     */
    @RequestMapping(value = "/searchProCategory")
    @ResponseBody
    public Object searchProCategory(@RequestBody SearchProCategory small) {
        String type = small.getType();
        Integer cid = small.getCid();
        String msgId = small.getMsgId();
        String msg = "";
        if (StringUtils.isBlank(msgId)) {
            msg = MSGID_NOT_NULL;
            return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
        }
        if (StringUtils.isBlank(type)) {
            msg = TYPE_NOT_NULL;
            return new ResponseEnvelope<SearchProCategorySmall>(false, msg, msgId);
        }
        return mobileWebSearchService.searchProCategory(type, cid, msgId);
    }

    /**
     * 查询产品组合列表
     *
     * @author yangzhun
     */
	@RequestMapping(value = "/searchProductGroup")
    @ResponseBody
    public Object searchProductGroup(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {
        System.out.println("searchProductGroup=>referreferreferreferrefer=");
        //组合没有平台关联表，这里平台编码做异业联盟过滤的条件
        String platformCode = request.getHeader("Platform-Code");
        model.setPlatformCode(platformCode);

        String mediaType = SystemCommonUtil.getMediaType(request);
        
        Integer userId = SystemCommonUtil.getUserIdByAuthorization(request);
        model.setUserId(userId);
        String referer = request.getHeader(CUSTOM_REFERER);

        if (null != referer && !"".equals(referer) && (
                referer.startsWith("http://servicewechat.com/")
                        || referer.startsWith("https://servicewechat.com/")
        )) {
            //去掉前缀
            referer = referer.substring(referer.indexOf("//servicewechat.com/") + 20, referer.length());
            //去掉后缀
            referer = referer.substring(0, referer.indexOf("/"));
            model.setAppId(referer);
        }
        logger.error("searchProductGroup=>getAppId=" + model.getAppId());
        return mobileWebSearchService.searchProductGroup(model,mediaType);
    }

    /**
     * 同类型产品列表接口
     */
    @RequestMapping(value = "/findSameTypeProductList")
    @ResponseBody
    public Object findSameTypeProductList(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {
        Integer productId = model.getProductId();
        String msgId = model.getMsgId();
        Integer planProductId = model.getPlanProductId();
        Integer userId = SystemCommonUtil.getUserIdByAuthorization(request);

        if (StringUtils.isBlank(msgId)) {
            return new ResponseEnvelope<>(false, "msgId为空!", msgId);
        }
        if (productId == null) {
            return new ResponseEnvelope<>(false, "参数缺少产品productId!", msgId);
        }

        return mobileWebSearchService.findTextureOfSameTypeProduct(productId, msgId, planProductId, userId);
    }

    /**
     * 根据产品id查询材质
     */
    @RequestMapping(value = "/selectProductById")
    @ResponseBody
    public Object selectProductById(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {
        Integer id = model.getProductId();
        boolean onlyDefault = model.getOnlyDefault();

        return mobileWebSearchService.selectProductById(id, onlyDefault);
    }

    /**
     * 根据材质id查询材质详情
     */
    @RequestMapping(value = "/selectTextureById")
    @ResponseBody
    public Object selectTextureById(@RequestBody MobileSearchProductModel model, HttpServletRequest request) {
        Integer id = model.getTextureId();


        return mobileWebSearchService.selectTextureById(id);
    }
}
