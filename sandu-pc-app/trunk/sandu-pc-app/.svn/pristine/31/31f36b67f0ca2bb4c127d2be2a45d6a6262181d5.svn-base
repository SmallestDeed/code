package com.sandu.web.panorama.controller;

import com.sandu.cityunion.model.UnionContact;
import com.sandu.cityunion.model.UnionGroup;
import com.sandu.common.LoginContext;
import com.sandu.common.model.LoginUser;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.panorama.model.input.UnionGroupAdd;
import com.sandu.panorama.model.input.UnionGroupSearch;
import com.sandu.panorama.model.input.UnionGroupUpdate;
import com.sandu.panorama.model.output.UnionGroupVo;
import com.sandu.panorama.service.UnionGroupService;
import com.sandu.panorama.service.exception.PanorameException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by chenm on 2018/7/30.
 */
@Api(value="联盟门店",tags = "Panorama-unionGroup",description = "720分享制作-联盟门店")
@RestController
@RequestMapping("/v1/panorama/unionGroup")
public class UnionGroupController {

    @Autowired
    private UnionGroupService unionGroupService;
    private Logger logger = LoggerFactory.getLogger(UnionGroupController.class);

    @ApiOperation(value = "联盟门店列表", response = UnionGroupVo.class)
    @PostMapping("/list")
    public Object list(@RequestBody UnionGroupSearch unionGroupSearch){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null){
            return  new ResponseEnvelope<UnionGroup>(false,"当前登录失效，请重新登录");
        }
        if(unionGroupSearch == null){
            unionGroupSearch = new UnionGroupSearch();
            unionGroupSearch.setStart(0);
            unionGroupSearch.setLimit(20);
        }else{
            if(unionGroupSearch.getLimit() == null || unionGroupSearch.getStart() == null){
                unionGroupSearch.setStart(0);
                unionGroupSearch.setLimit(20);
            }
        }
        unionGroupSearch.setUserId(loginUser.getId());
        int count = unionGroupService.getCount(unionGroupSearch);
        List<UnionGroupVo> list = null;
        if(count > 0){
            list = unionGroupService.getUnionGroupVoList(unionGroupSearch);
        }
        return new ResponseEnvelope<UnionGroupVo>(count,list);
    }

    @ApiOperation(value="通过Id获取联盟门店信息",response = UnionGroupVo.class)
    @GetMapping("/get")
    public Object get(@RequestParam(value="id",required = true) Integer id){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null){
            return  new ResponseEnvelope<UnionGroup>(false,"当前登录失效，请重新登录");
        }
        UnionGroupVo unionGroupVo = unionGroupService.getUnionGroupVo(id);
        return new ResponseEnvelope<UnionGroupVo>(true,unionGroupVo);
    }

    @ApiOperation(value="新增联盟分组",response = ResponseEnvelope.class)
    @PostMapping(value="/add")
    public Object add(@RequestBody  @Valid UnionGroupAdd unionGroupAdd, BindingResult result) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return  new ResponseEnvelope<UnionGroupAdd>(false,"当前登录失效，请重新登录");
        }
        if (result.hasErrors()) {
            return new ResponseEnvelope(false, result.getFieldError().getDefaultMessage());
        }
        int ret = 0;
        try {
            /*ret = unionGroupService.addUnionGroup(unionGroupAdd, loginUser);*/
            ret = unionGroupService.addUnionGroupWithShop(unionGroupAdd,loginUser);
        }catch(PanorameException e){
            return new ResponseEnvelope<UnionGroupAdd>(e.getMessage());
        }catch (Exception e) {
            logger.error("新增联盟分组时出现异常:",e);
            return new ResponseEnvelope<UnionGroupAdd>(false,"出现异常,保存失败!");
        }
        if(ret > 0){
            return new ResponseEnvelope<UnionGroupAdd>(true,"保存成功！");
        }else{
            return new ResponseEnvelope<UnionGroupAdd>(false,"保存失败!");
        }
    }

    @ApiOperation(value="修改联盟分组",response = ResponseEnvelope.class)
    @PostMapping(value="/update")
    public Object update(@RequestBody  @Valid UnionGroupUpdate unionGroupUpdate, BindingResult result){

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null){
            return  new ResponseEnvelope<UnionGroupUpdate>(false,"当前登录失效，请重新登录");
        }
        if (result.hasErrors()) {
            return new ResponseEnvelope(false, result.getFieldError().getDefaultMessage());
        }
        int ret = 0;
        try {
          /*  ret = unionGroupService.updateUnionGroup(unionGroupUpdate,loginUser);*/
            ret = unionGroupService.updateUnionGroupWithShop(unionGroupUpdate,loginUser);
        } catch (PanorameException e) {
            return new ResponseEnvelope<UnionGroupUpdate>(false,e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            logger.error("修改联盟门店信息时出现异常:",e);
            return new ResponseEnvelope<UnionGroupUpdate>(false,"出现异常，修改失败!");
        }
        if(ret > 0){
            return new ResponseEnvelope<UnionGroupUpdate>(true,"修改成功！");
        }else{
            return new ResponseEnvelope<UnionGroupUpdate>(false,"修改失败!");
        }
    }

    /**
     * 删除联盟门店
     * @param id
     * @return
     */
    @ApiOperation(value = "删除联盟门店" , response = ResponseEnvelope.class)
    @GetMapping("/del")
    public Object del(@RequestParam(value="id",required = true) Integer id){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
        int i = 0;
        try {
            i = unionGroupService.deleteUnionGroupAndDetails(id);
        } catch (Exception e) {
            logger.error("删除联盟门店时出现异常:" + e);
            return new ResponseEnvelope<>(false,"出现异常，删除失败！");
        }
        if( i > 0 ){
            return new ResponseEnvelope<>(true,"删除成功");
        }
        return new ResponseEnvelope<>(false,"删除失败");
    }

}
