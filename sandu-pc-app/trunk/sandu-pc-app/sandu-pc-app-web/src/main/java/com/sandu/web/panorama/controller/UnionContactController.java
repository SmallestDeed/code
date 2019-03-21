package com.sandu.web.panorama.controller;

import com.sandu.cityunion.model.UnionContact;
import com.sandu.common.LoginContext;
import com.sandu.common.model.LoginUser;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.panorama.model.input.UnionContactAdd;
import com.sandu.panorama.model.input.UnionContactSearch;
import com.sandu.panorama.model.input.UnionContactUpdate;
import com.sandu.panorama.model.output.UnionContactVo;
import com.sandu.panorama.service.UnionContactService;
import com.sandu.panorama.service.exception.PanorameException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "联系人", tags = "Panorama-contact", description = "720分享制作-联系人")
@RestController
@RequestMapping("/v1/panorama/unionContact")
public class UnionContactController {

    @Autowired
    private UnionContactService unionContactService;

    private Logger logger = LoggerFactory.getLogger(UnionContact.class);

    /**
     * 联系人列表
     * @return
     */
    @ApiOperation(value = "联系人列表", response = UnionContactVo.class)
    @PostMapping("/list")
    public Object list(@RequestBody UnionContactSearch unionContactSearch){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
        if( unionContactSearch == null ){
            unionContactSearch = new UnionContactSearch();
            unionContactSearch.setStart(0);
            unionContactSearch.setLimit(20);
        }else {
            if( unionContactSearch.getStart() == null || unionContactSearch.getLimit() == null ){
                unionContactSearch.setStart(0);
                unionContactSearch.setLimit(20);
            }
        }
        unionContactSearch.setUserId(loginUser.getId());
        unionContactSearch.setIsDeleted(0);
        int total = unionContactService.getCount(unionContactSearch);
        List<UnionContactVo> list = null;
        if( total > 0 ) {
            list = unionContactService.list(unionContactSearch);
        }
        return new ResponseEnvelope<UnionContactVo>(total, list);
    }

    /**
     * 添加联系人
     * @return
     */
    @ApiOperation(value = "添加联系人" , response = ResponseEnvelope.class)
    @PostMapping("/add")
    public Object add(@RequestBody UnionContactAdd unionContactAdd , BindingResult result){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
      if(result.hasErrors()){
            return new ResponseEnvelope<UnionContactAdd>(result.getFieldError().getDefaultMessage());
        }
        int i = 0;
        try {
            i = unionContactService.addContact(unionContactAdd, loginUser);
        } catch (PanorameException e) {
            return new ResponseEnvelope<>(false,e.getMessage());
        }catch (Exception e){
            logger.error("添加联系人时出现异常:",e);
            return new ResponseEnvelope<>(false,"出现异常，新增失败！");
        }
        if( i > 0 ){
            return new ResponseEnvelope<>(true,"保存成功");
        }
        return new ResponseEnvelope<>(false,"保存失败");
    }

    /**
     * 删除联系人
     * @param id
     * @return
     */
    @ApiOperation(value = "删除联系人" , response = ResponseEnvelope.class)
    @GetMapping("/del")
    public Object del(@RequestParam(value = "id",required = true) Integer id){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
        int i = unionContactService.delete(id);
        if( i > 0 ){
            return new ResponseEnvelope<>(true,"删除成功");
        }
        return new ResponseEnvelope<>(false,"删除失败");
    }

    /**
     * 通过ID获取联系人信息
     * @param id
     * @return
     */
    @ApiOperation(value = "通过ID获取联系人信息" , response = UnionContactVo.class)
    @GetMapping("/get")
    public Object get(@RequestParam(value = "id",required = true) Integer id){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
        UnionContactVo unionContact = unionContactService.getContact(id);
        return new ResponseEnvelope<UnionContactVo>(true, unionContact);
    }

    /**
     * 修改联系人信息
     * @param unionContactUpdate
     * @return
     */
    @ApiOperation(value = "修改联系人信息" , response = ResponseEnvelope.class)
    @PostMapping("/update")
    public Object update(@RequestBody @Valid UnionContactUpdate unionContactUpdate,BindingResult result){
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if( loginUser == null ){
            return new ResponseEnvelope<>(false,"当前登录失效，请重新登录");
        }
        if(result.hasErrors()){
            return new ResponseEnvelope<UnionContactUpdate>(result.getFieldError().getDefaultMessage());
        }

        int i = 0;
        try {
            i = unionContactService.updateContact(unionContactUpdate, loginUser);
        } catch (PanorameException e) {
            return new ResponseEnvelope<>(false,e.getMessage());
        }catch (Exception e){
            logger.error("",e);
            return new ResponseEnvelope<>(false,"出现异常,修改失败！");
        }
        if( i > 0 ){
            return new ResponseEnvelope<>(true,"修改成功");
        }
        return new ResponseEnvelope<>(false,"修改失败");
    }

}
