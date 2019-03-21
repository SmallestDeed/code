package com.sandu.web.user;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:28 2018/8/21 0021
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.cache.service.RedisLoginService;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.tool.PinYin4jUtils;
import com.sandu.common.util.BankWordUtil;
import com.sandu.user.model.*;
import com.sandu.user.model.input.MediationBankInfoAdd;
import com.sandu.user.model.view.MediationBankInfoListVo;
import com.sandu.user.service.MediationBankInfoService;
import com.sandu.validate.AnnotationValidator;
import com.sandu.validate.vo.ValidateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author weisheng
 * @Title: 中介用户管理
 * @Package
 * @Description:
 * @date 2018/8/21 0021AM 10:28
 */
@RestController
@Slf4j
@RequestMapping("/v1/union/bank")
public class MediationBankInfoController {


    private static Gson gson = new Gson();

    @Autowired
    private MediationBankInfoService mediationBankInfoService;

    @Autowired
    private RedisLoginService redisLoginService;

    //初始化时间类型
    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }




/*

绑定银行卡,新增中介银行卡信息
 */

    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEnvelope bind(@RequestBody MediationBankInfoAdd mediationBankInfoAdd, HttpServletRequest request) {

        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:" + gson.toJson(loginUser));

        //校验登录用户角色权限(只允许中介访问)
        if (null == loginUser.getUserType() || loginUser.getUserType().intValue() != UserRoleContants.MEDIATION) {
            log.warn("该用户角色类型:" + loginUser.getUserType());
            return new ResponseEnvelope(false, "该用户不是中介");
        }

        //校验传入信息
        ValidateResult result = AnnotationValidator.validate(mediationBankInfoAdd);
        if (!result.isValid()) {
            log.warn("参数校验未通过:" + gson.toJson(result));
            return new ResponseEnvelope(false, result.getFieldName() + "------------" + result.getMessage());
        }
        log.info("传参信息校验通过:" + gson.toJson(mediationBankInfoAdd));

        //校验同一张银行卡只允许被绑定一次
        MediationBankInfo mediationBankInfoByBankNumber;
        try {
            mediationBankInfoByBankNumber = mediationBankInfoService.getMediationBankInfoByBankNumber(mediationBankInfoAdd.getBankNumber());
            if (null != mediationBankInfoByBankNumber) {
                log.warn("该银行卡已经被绑定:" + mediationBankInfoAdd.getBankNumber());
                return new ResponseEnvelope(false, "该银行卡已经被绑定了");
            }
        } catch (Exception e) {
            log.error("通过银行卡号查询数据异常" + e);
            return new ResponseEnvelope(false, "通过银行卡号查询数据异常");
        }

        //校验同一个用户只能绑定一张银行卡
        MediationBankInfo mediationBankInfoByUserId;
        try {
            mediationBankInfoByUserId = mediationBankInfoService.getMediationBankInfoByUserId(loginUser.getId());
            if (null != mediationBankInfoByUserId) {
                log.warn("该用户已经绑定银行卡:" +loginUser.getId());
                return new ResponseEnvelope(false, "该用户已经绑定银行卡");
            }
        } catch (Exception e) {
            log.error("查询该用户已经绑定银行卡数据异常" + e);
            return new ResponseEnvelope(false, "查询该用户已经绑定银行卡数据异常");
        }



        //获取该用户绑定的历史记录信息
        MediationBankInfo mediationBankHistory;
        try {
            mediationBankHistory = mediationBankInfoService.getMediationBankHistoryByUserId(loginUser.getId());
        } catch (Exception e) {
            log.error("通过用户ID查询银行历史数据异常" + e);
            return new ResponseEnvelope(false, "通过用户ID查询银行历史数据异常");
        }

        int mediationBankInfoId;
        //首次绑定
        if (null == mediationBankInfoByUserId) {
            try {
                mediationBankInfoId = mediationBankInfoService.addMediationBankInfo(mediationBankInfoAdd, loginUser);
                if (mediationBankInfoId == 0) {
                    log.warn("首次绑定银行卡失败", gson.toJson(mediationBankInfoAdd) + "---------" + loginUser.getId());
                    return new ResponseEnvelope(false, "首次绑定银行卡失败");
                }
            } catch (Exception e) {
                log.error("首次绑定银行卡数据异常" + e);
                return new ResponseEnvelope(false, "通过银行卡号查询数据异常");
            }
        } else {
            //非首次绑定(获取原来的身份证信息)
            try {
                mediationBankInfoId = mediationBankInfoService.reAddMediationBankInfo(mediationBankInfoAdd, loginUser, mediationBankInfoByUserId);
                if (mediationBankInfoId == 0) {
                    log.warn("非首次绑定银行卡失败", gson.toJson(mediationBankInfoAdd) + "---------" + loginUser.getId() + "---------" + gson.toJson(mediationBankInfoByUserId));
                    return new ResponseEnvelope(false, "非首次绑定银行卡失败");
                }
            } catch (Exception e) {
                log.error("非首次绑定银行卡数据异常" + e);
                return new ResponseEnvelope(false, "非首次绑定银行卡数据异常");
            }


        }


        return new ResponseEnvelope(true, "绑定银行卡成功", mediationBankInfoId);
    }




    /*

解绑银行卡
 */

    @RequestMapping(value = "/unBind", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope bind(@RequestParam Integer id, HttpServletRequest request) {

        if (null == id || id == 0) {
            return new ResponseEnvelope(false, "必传参数ID缺失");
        }
        log.info("银行卡绑定信息ID:" + id);
        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:" + gson.toJson(loginUser));

        //校验登录用户角色权限(只允许中介访问)
        if (null == loginUser.getUserType() || loginUser.getUserType().intValue() != UserRoleContants.MEDIATION) {
            log.warn("该用户角色类型:" + loginUser.getUserType());
            return new ResponseEnvelope(false, "该用户不是中介");
        }

        //校验绑定银行卡信息是否有效
        MediationBankInfo mediationBankInfo;
        try {
            mediationBankInfo = mediationBankInfoService.getMediationBankInfoById(id);
            if (null == mediationBankInfo) {
                log.warn("无效的银行卡信息,解绑失败" + id);
                return new ResponseEnvelope(false, "无效的银行卡信息,解绑失败");
            }
        } catch (Exception e) {
            log.error("查询银行卡信息数据异常" + e);
            return new ResponseEnvelope(false, "查询银行卡信息数据异常");
        }

        //解除绑定的银行卡
        int mediationBankInfoId;
        try {
            mediationBankInfoId = mediationBankInfoService.updateMediationBankInfoById(id, loginUser);
            if (mediationBankInfoId == 0) {
                log.warn("解绑失败" + id);
                return new ResponseEnvelope(false, "解绑失败");
            }
        } catch (Exception e) {
            log.error("解除绑定数据异常" + e);
            return new ResponseEnvelope(false, "解除绑定数据异常");
        }


        return new ResponseEnvelope(true, "解绑成功", mediationBankInfoId);
    }



    /*

 校验用户是否绑定过银行卡
 */

    @RequestMapping(value = "/checkisbind", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope checkIsBind(HttpServletRequest request) {
        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:" + gson.toJson(loginUser));

        //校验登录用户角色权限(只允许中介访问)
        if (null == loginUser.getUserType() || loginUser.getUserType().intValue() != UserRoleContants.MEDIATION) {
            log.warn("该用户角色类型:" + loginUser.getUserType());
            return new ResponseEnvelope(false, "该用户不是中介");
        }

        //获取该用户绑定的历史记录信息
        MediationBankInfo mediationBankInfoByUserId;
        try {
            mediationBankInfoByUserId = mediationBankInfoService.getMediationBankInfoByUserId(loginUser.getId());
            if(null == mediationBankInfoByUserId){
                log.warn("该用户未绑定过银行卡:"+loginUser.getId());
                return new ResponseEnvelope(true,"该用户未绑定过银行卡");
            }
        } catch (Exception e) {
            log.error("通过用户ID查询数据异常" + e);
            return new ResponseEnvelope(false, "通过用户ID查询数据异常");
        }
        return new ResponseEnvelope(true,"",mediationBankInfoByUserId);
    }




    /*

校验银行预留手机号是否合法
*/
/*

*/
    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope checkCode(@RequestParam("authCode") String authCode, @RequestParam("mobile") String mobile, HttpServletRequest request) {
        //校验必传参数
        if (StringUtils.isBlank(authCode)) {
            log.warn("缺失必填参数authCode:" + authCode);
            return new ResponseEnvelope(false, "缺失必填参数authCode");
        }
        if (StringUtils.isBlank(mobile)) {
            log.warn("缺失必填参数mobile:" + mobile);
            return new ResponseEnvelope(false, "缺失必填参数mobile");
        }


        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:" + gson.toJson(loginUser));


        //检验验证码
        String map = redisLoginService.getMap(UserConstant.SESSION_SMS_CODE, mobile);

        SmsVo smsVo = gson.fromJson(map, SmsVo.class);
        if (smsVo == null) {

            log.warn("请输入验证码:" + gson.toJson(smsVo));
            return new ResponseEnvelope(false, "请输入验证码");

        }
        String code = smsVo.getCode();

        Long sendCodeTime = 0L;
        sendCodeTime = smsVo.getSendTime();

        Long currentTime = System.currentTimeMillis();

        if (StringUtils.isEmpty(code) || !(authCode.equals(code))) {

            log.warn("请输入验证码:" + authCode + "----------" + code);
            return new ResponseEnvelope(false, "请输入正确的验证码");
        }

        if ((currentTime - sendCodeTime) > (60000 * 3)) {//是否超时 默认3分钟

            log.warn("验证码超时:" + (currentTime - sendCodeTime));
            return new ResponseEnvelope(false, "验证码超时");
        }
        return new ResponseEnvelope(true, "success");
    }


    /*

获取绑定银行卡列表信息
 */

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getList(HttpServletRequest request) {
        //获取当前登录用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            log.warn("用户未登录,token未获取到或已失效");
            return new ResponseEnvelope(false, "请先登录");
        }
        log.info("当前登录用户:" + gson.toJson(loginUser));

        //校验登录用户角色权限(只允许中介访问)
        if (null == loginUser.getUserType() || loginUser.getUserType().intValue() != UserRoleContants.MEDIATION) {
            log.warn("该用户角色类型:" + loginUser.getUserType());
            return new ResponseEnvelope(false, "该用户不是中介");
        }

        //查询当前登录用户绑定的银行卡列表
        List<MediationBankInfoListVo> mediationBankInfoListVoList;
        try {
            mediationBankInfoListVoList = mediationBankInfoService.getMediationBankInfoListByUserId(loginUser.getId());
            if (null == mediationBankInfoListVoList || mediationBankInfoListVoList.size() == 0) {
                log.warn("改用户未绑定银行卡信息:" + loginUser.getId());
                return new ResponseEnvelope(false, "改用户未绑定银行卡");
            }
        } catch (Exception e) {
            log.error("查询数据异常:" + e);
            return new ResponseEnvelope(false, "查询数据异常");
        }

        //处理银行卡号(只显示后4位)
        mediationBankInfoListVoList.stream().forEach(mediationBankInfoListVo -> {
            String bankNumber = mediationBankInfoListVo.getBankNumber();
            if (StringUtils.isNotBlank(bankNumber)) {
                mediationBankInfoListVo.setBankNumber(bankNumber.substring(bankNumber.length() - 4, bankNumber.length()));
            }
        });


        return new ResponseEnvelope(true, "success", mediationBankInfoListVoList, mediationBankInfoListVoList.size());
    }


    /*获取所有银行的名称*/
    @RequestMapping(value = "/banklist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getAllBankName(HttpServletRequest request) {
        //获取银行数据集合
        Set<String> bankWordMap = BankWordUtil.bankWordMap;
        if (bankWordMap == null || bankWordMap.size() == 0) {
            log.error("初始化银行数据失败");
            return new ResponseEnvelope(false, "初始化银行数据失败");
        }
        log.info("初始化银行数据条数:" + bankWordMap.size());

        //处理银行数据
        List<BankWord> bankWordList = new ArrayList<>(bankWordMap.size());
        if (null != bankWordMap && bankWordMap.size() > 0) {
            for (String s : bankWordMap) {
                BankWord bankWord = new BankWord();
                bankWord.setBankName(s);
                bankWord.setBrankNameFirstLetter(PinYin4jUtils.getPinYinHeadChar(s, true));
                bankWordList.add(bankWord);
            }
        }


        //将集合转换为map并排序
        Map<String, List<BankWord>> map;
        try {
            map = builderMap(bankWordList);
            if (null == map || map.size() == 0) {
                log.warn("获取银行数据失败" + bankWordList.size());
                return new ResponseEnvelope(false, "获取银行数据失败");
            }
        } catch (Exception e) {
            log.error("获取银行数据异常" + e);
            return new ResponseEnvelope(false, "获取银行数据异常");
        }

        return new ResponseEnvelope(true, "success", map);
    }

    private Map<String, List<BankWord>> builderMap(List<BankWord> bankWordList) {
        //将集合转为map
        Map<String, List<BankWord>> map = new HashMap<>(26);
        for (int i = 65; i < 91; i++) {//利用ascall循环得到A-Z的码
            List<BankWord> bankWords = new ArrayList<>();
            String letter = Character.toString((char) i);
            for (BankWord bankWord : bankWordList) {
                if (StringUtils.isNotBlank(bankWord.getBrankNameFirstLetter()) && letter.equals(bankWord.getBrankNameFirstLetter().toUpperCase())) {
                    bankWords.add(bankWord);
                }
            }
            if (bankWords != null && bankWords.size() > 0) {
                map.put(letter, bankWords);
            }


        }


        //对map进行排序
        Map<String, List<BankWord>> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 升序排序
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(map);


        return sortMap;
    }


}
