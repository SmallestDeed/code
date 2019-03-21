package com.sandu.web.cart;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.cart.MallBaseCart;
import com.sandu.cart.MallCartProductRef;
import com.sandu.cart.service.MallBaseCartService;
import com.sandu.cart.service.MallCartProductRefService;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description  小程序购物车模块
 * @Date:Created Administrator in 上午 11:55 2018/3/28 0028
 * @Modified By:
 */
@Controller
@RequestMapping("/v1/miniprogram/cart")
public class MallBaseCartController {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[购物车服务]:";
    private final static Logger logger = LoggerFactory.getLogger(MallBaseCartController.class);

    @Autowired
    private MallBaseCartService mallBaseCartService;
    @Autowired
    private MallCartProductRefService mallCartProductRefService;

    /**
     * 加入购物车
     * @param request
     * @param mallCartProductRef
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public ResponseEnvelope addInCart(@RequestBody MallCartProductRef mallCartProductRef, HttpServletRequest request){
        if (null == mallCartProductRef) {
            return new ResponseEnvelope(false,"参数为空！");
        }
        if (null == mallCartProductRef.getProductId() || mallCartProductRef.getProductId() <= 0) {
            return new ResponseEnvelope(false,"产品id为空!");
        }
        if (null == mallCartProductRef.getProductNumber() || mallCartProductRef.getProductNumber() <= 0) {
            return new ResponseEnvelope(false,"产品数量为空！");
        }
        if (null == mallCartProductRef.getProductPrice() || mallCartProductRef.getProductPrice() <= 0) {
            return new ResponseEnvelope(false,"产品价格为空！");
        }

       LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser || null == loginUser.getId()){
            return new ResponseEnvelope(false,"请登录！");
        }
        Integer userId =loginUser.getId();
        //根据用户id获取购物车
        MallBaseCart baseCart = mallBaseCartService.getByUserId(userId);
        if (null == baseCart) {
            baseCart = new MallBaseCart();
            baseCart.setUserId(userId);
             baseCart.setCreator(loginUser.getName());
             baseCart.setModifier(loginUser.getName());
            int cartId = mallBaseCartService.add(baseCart);
            if (cartId <= 0) {
                return new ResponseEnvelope(false,"参数不完整，创建购物车失败！");
            }
            baseCart.setId(cartId);
        }
        mallCartProductRef.setCartId(baseCart.getId());
        mallCartProductRef.setCreator(loginUser.getName());
        mallCartProductRef.setModifier(loginUser.getName());
        mallCartProductRef.setIsDeleted(0);
        //查找购物车中是否存在该商品
        List<MallCartProductRef> oldMallCartProductList = mallCartProductRefService.getMallCartProductRef(mallCartProductRef);
        int cartProductId = 0;
        if(oldMallCartProductList!=null&&oldMallCartProductList.size()>0){
            MallCartProductRef oldCartProductRef = oldMallCartProductList.get(0);
            oldCartProductRef.setProductNumber(oldCartProductRef.getProductNumber()+mallCartProductRef.getProductNumber());
            cartProductId = mallCartProductRefService.update(oldCartProductRef);
        }else{
            cartProductId = mallCartProductRefService.add(mallCartProductRef);
        }
        if (cartProductId <= 0) {
            return new ResponseEnvelope(false,"加入购物车失败！");
        }
        return new ResponseEnvelope(true,"加入购物车成功！");
    }

    /**
     * 从购物车删除
     * @param mallCartProductRef
     * @param request
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public ResponseEnvelope delete(@RequestBody MallCartProductRef mallCartProductRef, HttpServletRequest request) {
        if (null == mallCartProductRef) {
            return new ResponseEnvelope(false,"参数为空！");
        }
        List<Integer> cartProductIdList = mallCartProductRef.getCartProductIdList();
        if (null == cartProductIdList || cartProductIdList.isEmpty()) {
            return new ResponseEnvelope(false,"参数为空！");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser || null == loginUser.getId()){
            return new ResponseEnvelope(false,"请登录！");
        }
        Integer userId = loginUser.getId();
        //根据用户id获取购物车
        MallBaseCart baseCart = mallBaseCartService.getByUserId(userId);
        if (null == baseCart) {
            return new ResponseEnvelope(false,"请先创建购物车！");
        }

        int res = mallCartProductRefService.delete(cartProductIdList);
        if (res <= 0) {
            return new ResponseEnvelope(false,"删除失败！");
        }
        return new ResponseEnvelope(true,"删除成功！");
    }

    /**
     * 修改购物车中产品的数量（暂时感觉只能修改数量）
     * @param cartProductRef
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResponseEnvelope updateNumber(@RequestBody MallCartProductRef cartProductRef,HttpServletRequest request) {
        if (null == cartProductRef) {
            return new ResponseEnvelope(false,"参数为空！");
        }
        if (null == cartProductRef.getId() || null == cartProductRef.getProductNumber()) {
            return new ResponseEnvelope(false,"必须的参数为空！");
        }
        int res = mallCartProductRefService.update(cartProductRef);
        if (res <= 0) {
            return new ResponseEnvelope(false,"修改失败！");
        }
        return new ResponseEnvelope(true,"修改成功！");
    }

    /**
     * 获取购物车所有产品
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDetail")
    public ResponseEnvelope getDetail(HttpServletRequest request, HttpServletResponse response){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (null == loginUser || null == loginUser.getId()){
            return new ResponseEnvelope(false,"请登录！");
        }
        Integer userId = loginUser.getId();

        MallBaseCart baseCart = mallBaseCartService.getByUserId(userId);
        if (null == baseCart) {
            return new ResponseEnvelope(true,"购物车为空",null,0);
        }

        MallCartProductRef mallCartProductRef = new MallCartProductRef();
        mallCartProductRef.setCartId(baseCart.getId());
        mallCartProductRef.setIsDeleted(0);
        List<MallCartProductRef> cartProductList = mallCartProductRefService.getList(mallCartProductRef);
        for (MallCartProductRef cartProductRef : cartProductList)
        {
            cartProductRef.setTransportMoney(cartProductRef.getFixTransportExpense()==null?"0":""+cartProductRef.getFixTransportExpense().intValue());
            if (cartProductRef.getAttribute() == null || "".equals(cartProductRef.getAttribute()))
            {
                cartProductRef.setAttribute("默认");
            }
        }
        if (null == cartProductList || cartProductList.isEmpty()) {
            return new ResponseEnvelope(true,"购物车为空",null,0);
        }
        baseCart.setCartProductList(cartProductList);
        return new ResponseEnvelope(true,"ok",baseCart,cartProductList.size());
    }

}
