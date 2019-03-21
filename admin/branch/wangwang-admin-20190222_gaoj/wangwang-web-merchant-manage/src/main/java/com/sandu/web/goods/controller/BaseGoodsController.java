package com.sandu.web.goods.controller;


import static com.sandu.common.ReturnData.builder;
import static com.sandu.constant.Punctuation.DOT;
import static com.sandu.constant.Punctuation.UNDERLINE;
import static com.sandu.constant.ResponseEnum.ERROR;
import static com.sandu.constant.ResponseEnum.NOT_CONTENT;
import static com.sandu.constant.ResponseEnum.SUCCESS;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.nork.common.model.LoginUser;
import com.sandu.api.company.model.CompanyMiniProgramConfig;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.product.service.biz.ProductBizService;
import com.sandu.common.LoginContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.sandu.api.goods.input.GoodsDetailQuery;
import com.sandu.api.goods.input.GoodsListQuery;
import com.sandu.api.goods.input.GoodsSKUListAdd;
import com.sandu.api.goods.input.GoodsSPUAdd;
import com.sandu.api.goods.model.BaseGoodsSPU;
import com.sandu.api.goods.model.ResPic;
import com.sandu.api.goods.model.bo.PutAwayBO;
import com.sandu.api.goods.output.GoodsDetailVO;
import com.sandu.api.goods.output.GoodsSKUVO;
import com.sandu.api.goods.output.GoodsTypeVO;
import com.sandu.api.goods.output.GoodsVO;
import com.sandu.api.goods.output.PicVO;
import com.sandu.api.goods.service.BaseGoodsSKUService;
import com.sandu.api.goods.service.BaseGoodsSPUService;
import com.sandu.api.goods.service.PicService;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.resmodel.output.FileVO;
import com.sandu.common.ReturnData;
import com.sandu.constant.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Api(tags = "BaseGoodsController", description = "商品管理")
@RestController
@RequestMapping("/v1/base/goods")
@Slf4j
public class BaseGoodsController {
    @Autowired
    private BaseGoodsSPUService baseGoodsSPUService;
    @Autowired
    private BaseGoodsSKUService baseGoodsSKUService;
    @Autowired
    private QueueService queueService;
    @Autowired
    private ProductBizService productBizService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PicService picService;
    @Value("${file.storage.path}")
    private String basePath;

    private static final String PICTURE_DIRECTORY_PATH = "/windowsPc/goods/picture";
    private static final String SMALL_PICTURE_DIRECTORY_PATH = "/windowsPc/goods/smallPicture";
    private static final int RANDOM_PATH_LENGTH = 6;

    @ApiOperation(value = "获取该企业有的商品分类", response = ReturnData.class)
    @GetMapping("/type")
    public Object getGoodsType(@RequestParam("companyId") Integer companyId) {
        ReturnData<GoodsTypeVO> data = ReturnData.builder();
        if (companyId <= 0) {
            return data.code(ERROR).message("公司ID必须大于0");
        }
        List<GoodsTypeVO> goodsType = baseGoodsSPUService.getGoodsType(companyId);
        if (goodsType == null || goodsType.size() <= 0) {
            return data.code(NOT_CONTENT).message("查询商品分类为空");
        }
        return data.code(SUCCESS).message("查询商品分类成功").list(goodsType);
    }

    @ApiOperation(value = "获取商品列表", response = ReturnData.class)
    @GetMapping("/list")
    public Object getGoodsList(@Validated GoodsListQuery goodsListQuery, BindingResult bindingResult) {
        ReturnData<GoodsVO> data = ReturnData.builder();

        if (hasError(bindingResult, data)) {
            return data;
        } else {
            List<GoodsVO> goodsList = baseGoodsSPUService.getGoodsList(goodsListQuery);
            Integer total = baseGoodsSPUService.totalCount(goodsListQuery);
            if (goodsList == null || goodsList.size() <= 0) {
                return data.code(NOT_CONTENT).message("查询商品列表为空");
            }
            return data.code(SUCCESS).message("查询商品列表成功").list(goodsList).total(total);
        }
    }

    @ApiOperation(value = "批量上架，参数ids是要上架的商品id集合", response = ReturnData.class)
    @PostMapping("/putaway")
    public Object goodsPutawayUp(@RequestBody PutAwayBO putAwayBO) {
        ReturnData<Integer> data = ReturnData.builder();
        if (putAwayBO == null) {
            return data.code(ERROR).message("请至少选择一件商品").success(false);
        }
        Gson gson = new Gson();
        Integer num = baseGoodsSPUService.goodsPutawayUp(putAwayBO);
        if (num == null || num <= 0) {

            return data.code(ERROR).message("已上架的商品不能重复上架").success(false);
        }
        //商品上架成功后像ES搜索服务器消息队列发送消息
        sycMessageDoSend(SyncMessage.ACTION_UPDATE,putAwayBO.getIds());
        log.info("商品上架成功发送消息完成,商品ID列表:"+ gson.toJson(putAwayBO));
        return data.code(SUCCESS).message(num + "条商品上架成功").data(num).success(true);
    }

    @ApiOperation(value = "批量下架，参数ids是要下架的商品id集合", response = ReturnData.class)
    @PostMapping("/offshelves")
    public Object goodsPutawayDown(@RequestBody PutAwayBO putAwayBO) {
        ReturnData<Integer> data = ReturnData.builder();
        if (putAwayBO.getIds() == null) {
            return data.code(ERROR).message("请至少选择一件商品").success(false);
        }
        Gson gson = new Gson();
        //校验商品是否已加入拼团活动
        Integer result = productBizService.validGroupActivityBySpuId(putAwayBO.getIds());
        if(result > 0) {
            return data.code(ERROR).message("进行中的拼团活动商品不能上下架").success(false);
        }
        String appId = null;
        CompanyMiniProgramConfig miniProgramConfig = companyService.getCompanyMiniConfigByCompanyId(putAwayBO.getCompanyId());
        if (miniProgramConfig != null) {
            appId = miniProgramConfig.getAppId();
        }
        Integer num = baseGoodsSPUService.goodsPutawayDown(putAwayBO.getIds(), appId);
        if (num == null || num <= 0) {
            return data.code(ERROR).message("已下架的商品不能重复下架").success(false);
        }

        //更新拼团活动状态
        productBizService.updateGroupActivityBySpuId(putAwayBO.getIds());

        //商品下架成功后像ES搜索服务器消息队列发送消息
        sycMessageDoSend(SyncMessage.ACTION_UPDATE,putAwayBO.getIds());
        log.info("商品下架成功成功发送消息完成,商品ID列表:"+gson.toJson(putAwayBO));
        return data.code(SUCCESS).message(num + "条商品下架成功").data(num).success(true);
    }

    @ApiOperation(value = "编辑商品", response = ReturnData.class)
    @GetMapping("/edit")
    public Object editGoods(GoodsDetailQuery goodsDetailQuery) {
        ReturnData<GoodsDetailVO> data = ReturnData.builder();

        GoodsDetailVO goodsDetailVO = baseGoodsSPUService.getGoodsInfo(goodsDetailQuery.getSpuId());
        List<GoodsSKUVO> skuList = baseGoodsSKUService.getGoodsSKUs(goodsDetailQuery);

        if (goodsDetailVO == null) {
            return data.code(ERROR).message("查询商品详情失败");
        } else {
            if (skuList == null || skuList.size() <= 0) {
                return data.code(ERROR).message("查询商品SKU失败或该商品无SKU信息");
            } else {
                goodsDetailVO.setTableHeads(skuList.get(0).getTableHeads());
                for (GoodsSKUVO sku : skuList) {
                    sku.setTableHeads(null);
                }
                goodsDetailVO.setSkuList(skuList);
                return data.code(SUCCESS).message("查询商品详情成功").data(goodsDetailVO);
            }
        }
    }

    @ApiOperation(value = "上传图片", response = ReturnData.class)
    @PostMapping("/uploadPic")
    public Object uploadPic(@RequestPart("file") MultipartFile file, String type) throws IOException {
        ReturnData<FileVO> data = ReturnData.builder();
        if (file == null){
            return data.code(ERROR).message("上传图片为空");
        }

        String name = file.getOriginalFilename();
        //------------------- 检查图片格式 ---------------------//
        // 后缀
        String suffix = StringUtils.substringAfterLast(name, ".");
        if (suffix != null && !"".equals(suffix)){
            if (!"jpg".equalsIgnoreCase(suffix) && !"png".equalsIgnoreCase(suffix) && !"jpeg".equalsIgnoreCase(suffix)){
                return data.code(ERROR).message("不支持的图片格式");
            }
        }else {
            return data.code(ERROR).message("请上传格式为jpg、jpeg、png的图片");
        }
        // 大小
        Long size = file.getSize();
        if (size > (4 * 1024 * 1024)) {
            return data.code(ERROR).message("图片大小最大为4M");
        }

        //------------------- 生成图片保存路径 ---------------------//
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("/yyyy/MM/dd/HH/");
        DateTimeFormatter ddf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String nowPath = now.format(df);

        String path = basePath + PICTURE_DIRECTORY_PATH + nowPath;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Random random = new Random(size);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < RANDOM_PATH_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }

        String filename = sb.toString() + UNDERLINE + now.format(ddf) + DOT + FilenameUtils.getExtension(file.getOriginalFilename());
        path += filename;

        String urlPath = PICTURE_DIRECTORY_PATH + nowPath + filename;

        file.transferTo(new File(path));

        //------------------- 保存到数据库 ---------------------//
        Date date = new Date();
        ResPic pic = new ResPic();
        pic.setPicPath(urlPath);
        pic.setPicName(filename);
        pic.setPicFileName(filename + suffix);
        pic.setGmtCreate(date);
        pic.setGmtModified(date);
        pic.setPicSize(size.intValue());
        pic.setPicSuffix(suffix);
        pic.setPicType(type);
        pic.setSysCode(urlPath + System.currentTimeMillis());
        pic.setIsDeleted(0);
        LoginUser user = LoginContext.getLoginUser(LoginUser.class);
        if (user != null){
            pic.setCreator(user.getLoginName());
            pic.setModifier(user.getLoginName());
        }

        Integer id = picService.saveImage(pic);
        if (id == 0) {
            return data.code(ERROR).message("上传图片失败");
        } else {
            PicVO vo = new PicVO();
            vo.setPicId(id);
            vo.setPicPath(urlPath);
            return data.code(SUCCESS).message("上传图片成功").data(vo);
        }
    }

    @ApiOperation(value = "更新商品sku信息", response = ReturnData.class)
    @PutMapping("/updateSku")
    public Object updateSKU(@Validated @RequestBody GoodsSKUListAdd list, BindingResult bindingResult) {
        Gson gson = new Gson();
        ReturnData<Object> data = ReturnData.builder();
        if (hasError(bindingResult, data)) {
            return data;
        } else {
            if (list.getCompanyId() != null) {
                CompanyMiniProgramConfig miniProgramConfig = companyService.getCompanyMiniConfigByCompanyId(list.getCompanyId());
                if (miniProgramConfig != null) {
                    list.setAppId(miniProgramConfig.getAppId());
                }
            }
            int i = baseGoodsSKUService.updateGoodsSKU(list);
            if (i == 0) {
                return data.code(ERROR).message("保存SKU信息失败");
            } else {
                //更新商品sku信息成功后像ES搜索服务器消息队列发送消息
                ArrayList<Integer> ids = new ArrayList<>();
                ids.add(list.getSpuId());
                sycMessageDoSend(SyncMessage.ACTION_UPDATE,ids);
                log.info("更新商品sku信息成功发送消息完成,商品ID列表:"+gson.toJson(ids));
                return data.code(SUCCESS).message("成功保存" + i + "条SKU信息");
            }
        }
    }

    @ApiOperation(value = "更新商品spu信息", response = ReturnData.class)
    @PutMapping("/updateSpu")
    public Object updateSPU(Integer userId, @Validated @RequestBody GoodsSPUAdd goodsSPUAdd, BindingResult bindingResult) {
        ReturnData<GoodsSPUAdd> data = ReturnData.builder();
        Gson gson = new Gson();
        if (hasError(bindingResult, data)) {
            return data;
        } else {
            if (goodsSPUAdd.getCompanyId() != null) {
                CompanyMiniProgramConfig miniProgramConfig = companyService.getCompanyMiniConfigByCompanyId(goodsSPUAdd.getCompanyId());
                if (miniProgramConfig != null) {
                    goodsSPUAdd.setAppId(miniProgramConfig.getAppId());
                }
            }
            Date now = new Date();
            goodsSPUAdd.setGmtModified(now);
            if (userId != null) {
                goodsSPUAdd.setModifier(userId.toString());
            }
            // 获取旧的缩略图原图id
            BaseGoodsSPU oldSpu = baseGoodsSPUService.get(goodsSPUAdd.getSpuId());
            String picIds = oldSpu.getPicIds();
            Integer oldPicId = null;
            if (picIds != null && !"".equals(picIds)){
                // 缩略图原图id默认放在IDS的第一个
                oldPicId = Integer.parseInt(picIds.split(",")[0]);
            }
            // 新的缩略图id与旧缩略图id不同时，生成一张新的小图
            if (goodsSPUAdd.getMainPicId() != null && !goodsSPUAdd.getMainPicId().equals(oldPicId)){
                ResPic sourcePic = picService.get(goodsSPUAdd.getMainPicId());
                String sourcePath = basePath + sourcePic.getPicPath();
                String targetPath = "";
                // 生成小图的存放路径
                Date date = new Date();
                SimpleDateFormat pathFormatter = new SimpleDateFormat("/yyyy/MM/dd/HH/");
                String path = basePath + SMALL_PICTURE_DIRECTORY_PATH + pathFormatter.format(date);
                File dir = new File(path);
                if (!dir.exists()){
                    dir.mkdirs();
                }
                // 生成小图的文件名
                Random random = new Random(sourcePic.getId());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < RANDOM_PATH_LENGTH; i++) {
                    sb.append(random.nextInt(10));
                }
                String newFileName = date.getTime() + sb.toString() + DOT + FilenameUtils.getExtension(sourcePic.getPicPath());
                targetPath = path + newFileName;
                // 生成小图
                try {
                    Thumbnails.of(sourcePath)
                            .scale(0.6f)
                            .outputQuality(0.5f)
                            .toFile(targetPath);
                } catch (IOException e) {
                    log.error("图片压缩失败,Exception:{}",e);
                    return data.message("图片压缩失败");
                }
                // 保存小图到数据库
                ResPic pic = new ResPic();
                pic.setPicPath(SMALL_PICTURE_DIRECTORY_PATH + pathFormatter.format(date) + newFileName);
                pic.setPicName(newFileName);
                pic.setPicFileName(newFileName);
                pic.setGmtCreate(date);
                pic.setGmtModified(date);
                pic.setIsDeleted(0);
                pic.setSysCode(System.currentTimeMillis() + newFileName);
                int newPicId = picService.saveImage(pic);
                goodsSPUAdd.setMainPicId(newPicId);
            }else if (goodsSPUAdd.getMainPicId() != null && goodsSPUAdd.getMainPicId().equals(oldPicId)) {
                goodsSPUAdd.setMainPicId(oldSpu.getPicId());
            }
            data = baseGoodsSPUService.updateGoods(goodsSPUAdd);
            ArrayList<Integer> ids = new ArrayList<>();
            ids.add(goodsSPUAdd.getSpuId());
            sycMessageDoSend(SyncMessage.ACTION_UPDATE,ids);
            log.info("更新商品spu信息成功发送消息完成,商品ID列表:"+gson.toJson(ids));
            return data;
        }
    }

    private <T> boolean hasError(BindingResult bindingResult, ReturnData<T> data) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError err : errors) {
                sb.append(err.getDefaultMessage() + System.lineSeparator());
            }
            data.code(ERROR).message(sb.toString());
            return true;
        } else {
            return false;
        }
    }

    private List<Integer> getIdsFromString(String str) {
        List<Integer> ids = new ArrayList<>();
        str = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
        if (str.contains(",")) {
            String[] idStrList = str.split(",");
            for (String idStr : idStrList) {
                ids.add(Integer.parseInt(idStr));
            }
        } else {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }



    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        List<Map> content = ids.stream().map(item -> {
            HashMap<String, Integer> tmp = new HashMap<>(1);
            tmp.put("id", item);
            return tmp;
        }).collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("G-" + System.currentTimeMillis());
        message.setModule(SyncMessage.GOODS_MODULE);
        message.setPlatformType(Constants.PLATFORM_CODE_MINI_APP);
        message.setObject(content);
        queueService.send(message);
    }






















}
