package com.nork.design.controller.web;

import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.design.model.DesignPlanRecommendFavoriteRef;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.Favorite;
import com.nork.design.model.FavoriteRecommendedModel;
import com.nork.design.model.input.FavoriteQuery;
import com.nork.design.service.DesignPlanRecommendFavoriteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/{style}/web/favorite")
public class VueFavoriteController {

    private static Logger logger = Logger.getLogger(VueFavoriteController.class);
    @Autowired
    private DesignPlanRecommendFavoriteService designPlanRecommendFavoriteService;

    /**
     * add 方法描述： 创建个人收藏夹
     * 
     * @param request
     * @param query
     * @return ResponseEnvelope 返回类型
     * @Exception 异常对象
     *            http://localhost:8080/timeSpace/online/favorite/add.htm?nm=test&msgId=123
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEnvelope add(@RequestBody FavoriteQuery query, HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        String msgId = query.getMsgId();
        String name = query.getNm();// 收藏夹名称

        envelope.setMsgId(msgId);
        
        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        if (StringUtils.isEmpty(name)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if("默认".equals(name.trim())){
        	envelope.setSuccess(false);
            envelope.setMessage("无法新增 默认 收藏夹");
            return envelope;
        }
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();

        // int userId = 123;
        Favorite favorite = new Favorite();
        favorite.setName(name);
        favorite.setUserId(userId);

        String uuid = designPlanRecommendFavoriteService.addFavorite(favorite);
        if (StringUtils.isEmpty(uuid)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        envelope.setObj(uuid);// 创建成功后可能立即使用到
        return envelope;
    }

    /**
     * 
     * 
     * update 方法描述：修改个人收藏夹名称
     * 
     * @param request
     * @param query
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     *            http://localhost:8080/timeSpace/online/favorite/update.htm?nm=ljx&msgId=123&bid=3ddfa1816b6149e4ad3f83712acc8b40
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEnvelope update(@RequestBody FavoriteQuery query, HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();

        String name = query.getNm();// 收藏夹名称
        String businessId = query.getBid();// 收藏夹业务Id
        String msgId = query.getMsgId();
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(businessId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if("默认".equals(name.trim())){
        	envelope.setSuccess(false);
            envelope.setMessage("无法命名为默认收藏夹，请重命名！");
            return envelope;
        }
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();

        // int userId = 123;
        Favorite favorite = new Favorite();
        favorite.setName(name);
        favorite.setUserId(userId);
        favorite.setBid(businessId);

        boolean res = designPlanRecommendFavoriteService.updateFavorite(favorite);
        envelope.setSuccess(res);
        if (!res)
            envelope.setMessage("params error");

        return envelope;
    }

    /**
     * 
     * 
     * list 方法描述：列表展示个人创建的收藏夹
     * 
     * @param request
     * @param query
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     *            http://localhost:8080/timeSpace/online/favorite/list.htm?msgId=123
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/list")
    public ResponseEnvelope list(@RequestBody FavoriteQuery query, HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        String msgId = query.getMsgId();
        String select = query.getSelect();//如果等于1说明 是下拉
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();

        // int userId = 123;

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        List listSearch = designPlanRecommendFavoriteService.listFavorites(favorite);
        if(listSearch == null || listSearch.size()<=0){
        	Favorite favoriteAdd = new Favorite();
        	favoriteAdd.setName("默认");
        	favoriteAdd.setUserId(userId);
            designPlanRecommendFavoriteService.addFavorite(favoriteAdd);
        }
        List<Favorite> list = designPlanRecommendFavoriteService.listFavorites(favorite);
        if(list == null || list.size()<=0){
        	 envelope.setDatalist(new ArrayList<>());
        	 envelope.setTotalCount(0);
        	 return envelope;
        }else{
        	Favorite defaultFavorite = null;
        	 
        	for (int i = 0; i < list.size(); i++) {
				if("默认".equals(list.get(i).getName().trim())){
					defaultFavorite = list.get(i);
					list.remove(i);
				}
			}
        	if(defaultFavorite!=null){
        		list.add(0, defaultFavorite);
        	}
        	if(StringUtils.isNotEmpty(select) && "1".equals(select)){
        		Favorite favoriteSelect = new Favorite();
        		favoriteSelect.setName("全部");
        		favoriteSelect.setBid("-1");
        		list.add(0, favoriteSelect);
        	}
        	envelope.setDatalist(list);
        	envelope.setTotalCount(list.size());
        	return envelope;
        }
    }

    /**
     * 
     * 
     * delete 方法描述：删除个人创建的收藏夹
     * 
     * @param request
     * @param query
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     *            http://localhost:8080/timeSpace/online/favorite/del.htm?msgId=123&bid=3ddfa1816b6149e4ad3f83712acc8b40
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/del")
    public ResponseEnvelope delete(@RequestBody FavoriteQuery query, HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();

        String businessId = query.getBid();// 收藏夹业务Id
        String msgId = query.getMsgId();
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        if (StringUtils.isEmpty(businessId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();
        // int userId =123;
        
        Favorite favoriteSearch = new Favorite();
        favoriteSearch.setUserId(userId);
        favoriteSearch.setBid(businessId);

        envelope = designPlanRecommendFavoriteService.deleteFavorite(favoriteSearch);
        envelope.setMsgId(msgId);
        return envelope;
    }

    /**
     * 
     * 
     * moveIn 方法描述： 将推荐方案添加到个人创建好的收藏夹
     * 
     * @param request
     * @param query
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     *            http://localhost:8080/timeSpace/online/favorite/moveIn.htm?msgId=123&fid=ca927580613548af929e048a5207ecfd&redId=85921
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/moveIn")
    public ResponseEnvelope moveIn(@RequestBody FavoriteQuery query, HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        String msgId = query.getMsgId();
        String recommendId = query.getRedId()+"";// 推荐方案的id
        String favoriate_businessId = query.getFid();// 收藏夹的业务id
        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId) || StringUtils.isEmpty(favoriate_businessId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        if (StringUtils.isEmpty(recommendId) || !StringUtils.isNumeric(recommendId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        
        
        DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
        favoriteRef.setFid(favoriate_businessId);
        favoriteRef.setUserId(loginUser.getId());
        favoriteRef.setRecommendId(Integer.valueOf(recommendId));
        boolean flag = designPlanRecommendFavoriteService.existInFavoriteNew(favoriteRef);
        if (!flag) { 
        	 envelope.setSuccess(false);
             envelope.setMessage("请勿重复收藏");
             return envelope;
        }
        boolean res = designPlanRecommendFavoriteService.moveInFavorite(favoriteRef);
        envelope.setSuccess(res);
        if (!res)
            envelope.setMessage("params error");
        return envelope;
    }

    /**
     * 
     * 
     * moveOut 方法描述：移除已经收藏的推荐方案
     * 
     * @param request
     * @param query
     * @return
     * 
     * @return ResponseEnvelope 返回类型
     * 
     * @Exception 异常对象
     *            http://localhost:8080/timeSpace/online/favorite/moveOut.htm?msgId=123&bid=399653cc9fe04fd8ad7fb402be3203f9&redId=85921
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/moveOut")
    public ResponseEnvelope moveOut(@RequestBody FavoriteQuery query, HttpServletRequest request) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        String msgId = query.getMsgId();
        String recommendRef_busyneesId = query.getBid();// 推荐方案的业务id

        envelope.setMsgId(msgId);

        if (StringUtils.isEmpty(msgId) || StringUtils.isEmpty(recommendRef_busyneesId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }

        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }

        DesignPlanRecommendFavoriteRef favoriteRef = new DesignPlanRecommendFavoriteRef();
        favoriteRef.setBid(recommendRef_busyneesId);
        boolean res = designPlanRecommendFavoriteService.moveOutFavorite(favoriteRef);
        envelope.setSuccess(res);
        if (!res)
            envelope.setMessage("params error");

        return envelope;
    }


}
