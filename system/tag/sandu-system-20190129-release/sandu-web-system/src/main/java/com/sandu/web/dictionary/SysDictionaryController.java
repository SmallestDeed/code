package com.sandu.web.dictionary;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.dictionary.model.SysDictionary;
import com.sandu.api.servicepurchase.constant.ServicesPurchaseConstant;
import com.sandu.commons.constant.BusinessTypeToUserTypeConstant;
import com.sandu.commons.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.sandu.api.dictionary.input.DictionaryTypeListQuery;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author chenqiang
 * @Description 数据字典 控制层
 * @Date 2018/6/1 0001 16:35
 * @Modified By
 */
@Api(tags = "dictionary", description = "数据字典")
@RestController
@RequestMapping("/v1/sys/dictionary")
@Slf4j
public class SysDictionaryController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SysDictionaryController.class);

    @Autowired
    private DictionaryService sysDictionaryService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @ApiOperation(value = "类型条件查询数据字典列表数据", response = DictionaryTypeListVO.class)
    @GetMapping("/type/list")
    public ResponseEnvelope getDictionaryListByType(@Valid @ModelAttribute DictionaryTypeListQuery query, BindingResult validResult) {
        try {

            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }

            //2.获取list数据
            List<DictionaryTypeListVO> dictionaryList = sysDictionaryService.getDictionaryListByType(query);

            return new ResponseEnvelope<>(true, dictionaryList.size(), dictionaryList);

        } catch (Exception e) {

            logger.error("getDictionaryListByType 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "查询企业类型所属用户类型", response = DictionaryTypeListVO.class)
    @GetMapping("/user/list")
    public ResponseEnvelope getDictionaryUserList(@RequestParam("companyId") Long companyId) {
        try {

            //1.数据校验
            if (null == companyId) {
                return new ResponseEnvelope<>(false, "企业id不能为空!");
            }

            //2.获取企业类型对应用户类型
            BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(companyId);
            String value = BusinessTypeToUserTypeConstant.getValueByKey(baseCompany.getBusinessType().toString());
            if (StringUtils.isEmpty(value)) {
                return new ResponseEnvelope<>(false, "该企业没有对应的用户类型!");
            }
            List<Integer> valueList = StringUtil.getListByString(value);

            //2.获取list数据
            List<DictionaryTypeListVO> dictionaryList = sysDictionaryService.getListByTypeOrValues("userType", valueList);

            return new ResponseEnvelope<>(true, dictionaryList.size(), dictionaryList);

        } catch (Exception e) {

            logger.error("getDictionaryListByType 方法 系统异常:", e);
            return new ResponseEnvelope<>(false, "系统异常!");

        }
    }

    @ApiOperation(value = "查询用户类型", response = DictionaryTypeListVO.class)
    @GetMapping("/user/scope/list/{companyId}")
    public ResponseEnvelope getDictionaryUserScopeList(@PathVariable("companyId") Long companyId) {
        List<SysDictionary> userScopeByCompanyId = this.getUserScopeByCompanyId(companyId);
        if (userScopeByCompanyId != null) {
            List<DictionaryTypeListVO> collect = userScopeByCompanyId.stream().map(item -> {
                DictionaryTypeListVO vo = new DictionaryTypeListVO();
                vo.setName(item.getAtt2());
                vo.setValue(Integer.valueOf(item.getAtt1()));
                return vo;
            }).collect(Collectors.toList());
            return new ResponseEnvelope<>(true, collect.size(), collect);
        }
        return new ResponseEnvelope<>(false, "系统异常!");
    }


    /**
     * 企业类型包含的用户类型集合
     *
     * @param companyId
     * @return
     */
    private List<SysDictionary> getUserScopeByCompanyId(Long companyId) {
        BaseCompany baseCompany = baseCompanyService.getCompanyById(companyId);
        if (baseCompany == null) {
            log.error(" 没有找到公司，数据异常，请联系管理员，公司id:{}", companyId);
            return Collections.emptyList();
        }
        //企业类型的Value
        Integer value = baseCompany.getBusinessType();
        //根据企业类型查询用户类型
        SysDictionary sysDictionary = sysDictionaryService.getByTypeAndValue(ServicesPurchaseConstant.BRAND_BUSSINESS_TYPE, value);
        if (sysDictionary == null) {
            log.error("数据字典没有找到公司类型，数据异常，请联系管理员，公司类型Value:{},type:{}", value, ServicesPurchaseConstant.BRAND_BUSSINESS_TYPE);
            return Collections.emptyList();
        }
        //用户类型的type
        String type = sysDictionary.getValuekey();
        return sysDictionaryService.queryByType(type);
    }
}
