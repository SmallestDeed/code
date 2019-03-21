package com.sandu.web.imall;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sandu.api.gift.input.GiftAdd;
import com.sandu.api.gift.input.GiftQuery;
import com.sandu.api.gift.input.GiftUpdate;
import com.sandu.api.gift.model.Gift;
import com.sandu.api.gift.model.GiftImage;
import com.sandu.api.gift.output.GiftVO;
import com.sandu.api.gift.service.GiftBizService;
import com.sandu.authz.annotation.RequiresPermissions;
import com.sandu.common.util.Utils;
import com.sandu.commons.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.web.imall.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.sandu.constant.ResponseEnum.*;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/26 15:35
 */
@Api(value = "礼品管理", tags = "礼品", description = "礼品管理")
@RestController
@RequestMapping(value = "/v1/gift")
public class GiftController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(GiftController.class);

    @Value("${upload.base.path}")
    private String rootPath;

    @Value("${imall.gift.upload.path}")
    private String giftPath;

    @Autowired
    private GiftBizService giftBizService;

    @ApiOperation(value = "保存图片", response = ReturnData.class)
    @RequestMapping(value = {"/imageUpload"},consumes = "multipart/form-data",method=RequestMethod.POST)
    @RequiresPermissions({"points:mall:gift:edit"})
    public ReturnData imageUpload(int giftId,int order,int cover,int small,HttpServletRequest request) {
        //int id=StringUtil.isNullOrEmptyInt(request.getParameter("id"),0);//
        ReturnData data = ReturnData.builder();
        int iCoverIndex=StringUtil.isNullOrEmptyInt(request.getParameter("picCover"),-1) ;
        int iSmallIndex=StringUtil.isNullOrEmptyInt(request.getParameter("picSmall"),-1) ;
        LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
        String userName=loginUser.getNickName();
        if (giftId > 0) {//保存图片
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            if (files.size()>0){
                MultipartFile mfile=files.get(0);
                try {
                    // 文件保存路径
                    giftPath = StringUtil.replaceDate(giftPath, null);
                    String filePath = rootPath + giftPath;
                    File file=new File(filePath);
                    if(!file.exists()) file.mkdirs();
                    String suffix=mfile.getOriginalFilename().substring(mfile.getOriginalFilename().lastIndexOf("."));
                    String fileNameNew=UUID.randomUUID().toString();//String.valueOf(new Date().getTime())+String.valueOf(i);//新文件名
                    mfile.transferTo(new File(filePath+fileNameNew+suffix));// 转存文件
                    GiftImage giftImage=new GiftImage();
                    giftImage.setGiftId(giftId);
                    giftImage.setFileName(giftPath+fileNameNew+suffix);
                    giftImage.setOrder(order);
                    giftImage.setCover(cover);//封面
                    giftImage.setSmall(small);//缩略图
                    giftImage.setCreator(userName);//操作人
                    giftImage.setModifier(giftImage.getCreator());
                    int giftImageId = giftBizService.createGiftImage(giftImage);
                    JSONObject jsonObject = new JSONObject();
                    //jsonObject.put("id",giftImage.getId());
                    jsonObject.put("id",giftImageId);
                    jsonObject.put("name",giftPath+fileNameNew+suffix);
                    return data.code(SUCCESS).data(jsonObject);
                } catch (Exception e) {
                    logger.error("未知错误:",e);
                }

            }
        }

        return data.code(PARAM_ERROR).message("输入数据有误");
    }

    @ApiOperation(value = "新增", response = ReturnData.class)
    @RequestMapping(value = {"/create"},consumes = "multipart/form-data",method=RequestMethod.POST)
    @RequiresPermissions({"points:mall:gift:edit"})
    public ReturnData create(@Valid GiftAdd input, BindingResult validResult,HttpServletRequest request) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        int iCoverIndex=StringUtil.isNullOrEmptyInt(request.getParameter("picCover"),-1);
        int iSmallIndex=StringUtil.isNullOrEmptyInt(request.getParameter("picSmall"),-1);
        input.setIsPutaway(0);

        LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
        String userName=loginUser.getNickName();
        input.setCreator(userName);
        input.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
        int giftId = giftBizService.create(input);

        if (giftId > 0) {//保存图片
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            /**上传图片数量限制 add by WangHaiLin start**/
            if (files.size()>6){
                return data.code(PARAM_ERROR).message("图片最多上传6张");
            }
            if (files.size()==0){
                return data.code(PARAM_ERROR).message("请上传礼品图片！");
            }
            /**上传图片数量限制 add by WangHaiLin end**/
            for (int i=0;i<files.size();i++){
                MultipartFile mfile=files.get(i);
                try {
                    // 文件保存路径
                    giftPath = StringUtil.replaceDate(giftPath, null);
                    String filePath = rootPath + giftPath;
                    File file=new File(filePath);
                    if(!file.exists()) file.mkdirs();
                    String suffix=mfile.getOriginalFilename().substring(mfile.getOriginalFilename().lastIndexOf("."));
                    String fileNameNew=UUID.randomUUID().toString();//String.valueOf(new Date().getTime())+String.valueOf(i);//新文件名
                    mfile.transferTo(new File(filePath+fileNameNew+suffix));// 转存文件
                    GiftImage giftImage=new GiftImage();
                    giftImage.setGiftId(giftId);
                    giftImage.setFileName(giftPath+fileNameNew+suffix);
                    giftImage.setOrder(i);
                    if (iCoverIndex==i){//封面
                        giftImage.setCover(1);
                    }else{
                        giftImage.setCover(0);
                    }
                    if (iSmallIndex==i){//缩略图
                        giftImage.setSmall(1);
                    }else{
                        giftImage.setSmall(0);
                    }
                    giftImage.setCreator(input.getCreator());
                    giftImage.setModifier(input.getCreator());
                    giftBizService.createGiftImage(giftImage);
                } catch (Exception e) {
                    logger.error("未知错误:",e);
                }
            }
            return data.code(SUCCESS).data(giftId);
        }

        return data.code(PARAM_ERROR).message("输入数据有误");
    }

    @ApiOperation(value = "编辑", response = ReturnData.class)
    @PutMapping
    @RequiresPermissions({"points:mall:gift:edit"})
    public ReturnData update(@Valid @RequestBody GiftUpdate input, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }

        LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
        String userName=loginUser.getNickName();
        input.setModifier(userName);

        int result = giftBizService.update(input);
        if (result > 0) {
            return data.code(SUCCESS).data(result).message("修改礼品成功");
        }

        return data.code(PARAM_ERROR).message("输入数据有误");
    }

    @ApiOperation(value = "设置封面", response = ReturnData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "giftId", value = "giftId", paramType = "query", dataType = "Integer")
    })
    @RequestMapping(value = {"/updateCover"},method=RequestMethod.GET)
    public ReturnData updateCover(int id,int giftId) {
        logger.info("设置封面图入参----id:{}",id);
        logger.info("设置封面图入参----giftId:{}",giftId);
        ReturnData data = ReturnData.builder();
        LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
        String userName=loginUser.getNickName();
        logger.info("设置封面图入参----userName:{}",userName);
        int result = giftBizService.updateCover(id,giftId,userName);

        if (result > 0) {
            return data.code(SUCCESS).data(result);
        }

        return data.code(PARAM_ERROR).message("输入数据有误");
    }

    @ApiOperation(value = "设置缩略图", response = ReturnData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "giftId", value = "giftId", paramType = "query", dataType = "Integer")
    })
    @RequestMapping(value = {"/updateSmall"},method=RequestMethod.GET)
    public ReturnData updateSmall(int id,int giftId) {
        logger.info("设置缩略图入参----id:{}",id);
        logger.info("设置缩略图入参----giftId:{}",giftId);
        ReturnData data = ReturnData.builder();
        LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
        String userName=loginUser.getNickName();
        logger.info("设置缩略图入参----userName:{}",userName);
        int result = giftBizService.updateSmall(id,giftId,userName);
        if (result > 0) {
            return data.code(SUCCESS).data(result);
        }

        return data.code(PARAM_ERROR).message("输入数据有误");
    }

    @ApiOperation(value = "修改上下架状态", response = ReturnData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "giftId", value = "giftId", paramType = "query", dataType = "Integer")
    })
    @RequestMapping(value = {"/updatePutaway"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:gift:edit"})
    public ReturnData updatePutaway(String ids,int putaway) {
        ReturnData data = ReturnData.builder();
        LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
        String userName=loginUser.getNickName();
        int result = giftBizService.updatePutaway(ids,putaway,userName);
        if (result > 0) {
            return data.code(SUCCESS).data(result);
        }

        return data.code(PARAM_ERROR).message("异常");
    }

    @ApiOperation(value = "删除图片", response = ReturnData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "fileName", value = "图片名称", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = {"/deleteGiftImageById"},method=RequestMethod.GET)
    public ReturnData deleteGiftImageById(int id,String fileName) {
        ReturnData data = ReturnData.builder();
        LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
        String userName=loginUser.getNickName();
        int result = giftBizService.deleteGiftImageById(id,userName);
        if (result > 0) {
            /*try {
                String filePath = ResourceUtils.getURL("classpath:").getPath() + "upload/";
                new File(filePath+fileName).delete();
            }catch (Exception e){

            }*/

            return data.code(SUCCESS).data(result);
        }

        return data.code(PARAM_ERROR).message("异常");
    }

    @ApiOperation(value = "删除", response = ReturnData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query", dataType = "Integer")
    })
    @DeleteMapping
    @RequiresPermissions({"points:mall:gift:edit"})
    public ReturnData delete(String id) {
        ReturnData data = ReturnData.builder();
        LoginUser loginUser=LoginContext.getLoginUser(LoginUser.class);
        String userName=loginUser.getNickName();
        int result = giftBizService.delete(id,userName);
        if (result > 0) {
            return data.code(SUCCESS).data(result);
        }

        return data.code(PARAM_ERROR).message("不存在");
    }

    @ApiOperation(value = "获取详情", response = GiftVO.class)
    @RequestMapping(value = {"/getGift"},method=RequestMethod.GET)
    @RequiresPermissions({"points:mall:gift:edit"})
    public ReturnData getDetailById(int id) {
        ReturnData data = ReturnData.builder();
        if (id <= 0) {
            return data.code(PARAM_ERROR).message("ID无效");
        }

        Gift gift = giftBizService.getDetailById(id);
        if (gift == null) {
            return data.code(PARAM_ERROR).message("内容不存在");
        }

        List<GiftImage> giftImages=giftBizService.getGiftImages(id);

        GiftVO output = new GiftVO();
        output.setId(gift.getId());
        output.setName(gift.getName());
        output.setExplain(gift.getExplain());
        output.setIntroduce(gift.getIntroduce());
        output.setIsPutaway(gift.getIsPutaway());
        output.setInventory(gift.getInventory());
        output.setPrice(gift.getPrice());
        output.setPoint(gift.getPoint());
        output.setExpressFee(gift.getExpressFee());
        output.setImages(giftImages);

        return data.code(SUCCESS).data(output);
    }


    @ApiOperation(value = "查询节点内容列表", response = GiftVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "礼品编号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "礼品名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isPutaway", value = "状态：0未上架，1上架", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "imallOrder", value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序方式", paramType = "query", dataType = "String")
    })
    @GetMapping(value = "/list")
    @RequiresPermissions({"points:mall:gift:view"})
    public ReturnData queryList(@Valid GiftQuery query, BindingResult validResult) {
        ReturnData data = ReturnData.builder();
        if (validResult.hasErrors()) {
            return processValidError(validResult, data);
        }
        //去参数前后空格
        if (null!=query.getCode()){
            query.setCode(query.getCode().trim());
        }
        if (null!=query.getName()){
            query.setName(query.getName().trim());
        }
        PageInfo<Gift> results = giftBizService.queryList(query);
        if (results.getTotal() > 0) {
            /*final List<GiftVO> gifts = Lists.newArrayList();
            results.getList().stream().forEach(gift -> {
                GiftVO out = new GiftVO();
                out.setId(gift.getId());
                out.setCode(gift.getCode());
                out.setExplain(gift.getExplain());
                out.setIntroduce(gift.getIntroduce());
                out.setIsPutaway(gift.getIsPutaway());
                out.setAmount(gift.getAmount());
                out.setPrice(gift.getPrice());
                out.setIntegral(gift.getIntegral());
                out.setExpressFee(gift.getExpressFee());
                out.setCreated(gift.getCreated());
                gifts.add(out);
            });*/


            return data.code(SUCCESS).list(results.getList()).total(results.getTotal());
        }

        return data.code(NOT_CONTENT).message("暂无数据");
    }


}
