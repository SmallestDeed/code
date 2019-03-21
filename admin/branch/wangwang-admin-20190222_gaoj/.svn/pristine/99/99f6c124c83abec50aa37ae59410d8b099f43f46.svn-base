package com.sandu.web.resfile.controller;

import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.service.ResPicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sandu-lipeiyuan
 */
@RestController
@RequestMapping("/{style}/system/resPic")
public class ResFileController {

    @Autowired
    private ResPicService resPicService;

//    /**
//     * 批量文件上传
//     *
//     */
//
//    @PostMapping(value = "/batchSave")
//    public Object batchSave(@RequestBody MultipartFile filePath, ResPic resPic) throws UnsupportedEncodingException {
//        // 获取该文件上传后的文件名(根据旧数据,更新flag标志)(应对于更新缓存功能)
//        /*String fileName = resPicService.getFileNameByResPicInfo(resPic.getBusinessId(), resPic.getFileKey());*/
//        Map<getFilePathRemoveSuffixByResPicInfoMapKey, Object> fileInfoMap = resPicService.getFilePathRemoveSuffixByResPicInfo(resPic.getBusinessId(), resPic.getFileKey());
//
//        /**品牌和产品组合 水印开启关闭状态*/
//        String watermark_brand_state=null;
//		/*String watermark_groupProduct_state=null;
//		watermark_groupProduct_state=res.getString("watermark.groupProduct.pic");*/
//        watermark_brand_state=res.getString("watermark.brand.pic");
//        String resIds=resPic.getResIds();
//        // list不为空，则批量插入数据库中
//        String fileidlist = request.getParameter("fileidlist");
//        String filenamelist = request.getParameter("filenamelist");
//        /*String watermarkState = request.getParameter("watermarkState");*/
//        String opType = request.getParameter("opType");
//
//        // 多文件同批次上传,解析多个路径
//        if (!StringUtils.isEmpty(fileidlist.replaceFirst(";", ""))) {
//            fileidlist = fileidlist.replaceFirst(";", "") + ";";
//            filenamelist = filenamelist.replaceFirst(";","");
//            String[] fileids = fileidlist.split(";");
//            String[] filenames = filenamelist.split(";");
//            for( int i=0;i<fileids.length;i++ ){
//                String filedid = fileids[i];
//                if(StringUtils.isNotBlank(resPic.getSysCode())){
//                    filedid=filedid.replace("[code]", resPic.getSysCode());
//                }
//                ResPic copyRecPic = resPic.copy();
//                copyRecPic.setPicPath(filedid);
//                String realPath = FileUploadUtils.UPLOAD_ROOT .replace("\\", "/")+ filedid;
//                try {
//                    logger.debug("realPath=============" +realPath);
//                    if(SYSTEM_FORMAT.equals("linux")){
//                        realPath = realPath.replace("\\", "/");
//                    }
//                    File f = new File(realPath);
//                    String fname = f.getName();
//                    long fileSize = f.length();
//                    String suffix = fname.substring(fname.lastIndexOf("."));
//                    copyRecPic.setFileKey(copyRecPic.getFileKey());
//                    copyRecPic.setBusinessId(copyRecPic.getBusinessId());
//                    copyRecPic.setPicName(filenames[i].substring(0,filenames[i].lastIndexOf(".")));
//                    copyRecPic.setPicFileName(copyRecPic.getPicName());
//                    copyRecPic.setPicSuffix(suffix);// 文件后缀
//                    copyRecPic.setPicSize(new Long(fileSize).intValue());// 文件大小
//
//                    String pictype = "无";
//
//
//                    String UPLOAD_PATH = resPic.getFileKey() + ".upload.path";
//                    Map<String,Object> map = new HashMap<String,Object>();
//                    map.put(FileModel.FILE_KEY,UPLOAD_PATH.replace(".upload.path", ""));
//                    map.put(FileUploadUtils.UPLOADPATHTKEY, UPLOAD_PATH);
//                    map.put(FileUploadUtils.FILE, filePath);
//                    map.put("fileKey", "fileKey");
//
//
//                    if(map!=null&&map.size()>0){
//                        if(map.get("fileKey")!=null&&!"".equals(map.get("fileKey"))){
////								if(watermark_brand_state!=null&&"1".equals(watermark_brand_state)){
////									if("product.baseBrand.logo".equals(map.get("fileKey").toString())){
////										ImageUtils.watermarkingNew(realPath,true,"baseBrand");
////										resPic.setNumAtt2(1);
////										resPicService.update(resPic);
////									}
////								}
////								if(watermark_groupProduct_state!=null&&"1".equals(watermark_groupProduct_state)){
////									if("product.groupProduct.pic".equals(map.get("fileKey").toString())){
////										ImageUtils.watermarkingNew(realPath,true,"groupProduct");
////										resPic.setNumAtt2(1);
////										resPicService.update(resPic);
////									}
////								}
//                        }
//                    }
//
//                    if("design.designTemplet.cadPic".equals(map.get("fileKey").toString())){
//                        ImageUtils.watermarkingNew(realPath,true,"designTemplet");
//                        resPic.setNumAtt2(1);
//                        resPicService.update(resPic);
//                    }
//                    copyRecPic.setPicType(pictype);
//                    BufferedImage bufferedImage = ImageIO.read(f);
//                    int width = bufferedImage.getWidth(); // 图片宽度
//                    int height = bufferedImage.getHeight();// 图片高度
//                    String format = suffix.replace(".", "");
//                    copyRecPic.setPicWeight(new Integer(width).toString());
//                    copyRecPic.setPicHigh(new Integer(height).toString());
//                    copyRecPic.setPicFormat(format);// 图片格式
//                    if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==2){
//                        //删除本地
//                        FileUploadUtils.deleteFile(filedid);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (StringUtils.isEmpty(copyRecPic.getPicLevel())) {
//                    copyRecPic.setPicLevel("0");// 图片级别
//                }
//                if (StringUtils.isEmpty(copyRecPic.getPicOrdering())) {
//                    copyRecPic.setPicOrdering("0");// 图片排序
//                }
//                try {
//                    sysSave(copyRecPic, request);
//                    copyRecPic.setPicCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
//                    if (copyRecPic.getId() == null) {
//                        /*查找最大的序号,得到该图片对应的序号*/
//                        ResPicSearch resPicSearch=new ResPicSearch();
//                        resPicSearch.setLimit(1);
//                        resPicSearch.setStart(0);
//                        resPicSearch.setFileKey(copyRecPic.getFileKey());
//                        resPicSearch.setBusinessId(copyRecPic.getBusinessId());
//                        resPicSearch.setOrder(" sequence ");
//                        resPicSearch.setOrderNum(" desc ");
//                        resPicSearch.setSysCode(copyRecPic.getSysCode());
//                        List<ResPic> resPics=resPicService.getPaginatedList(resPicSearch);
//                        if(resPics!=null&&resPics.size()>0){
//                            copyRecPic.setSequence(resPics.get(0).getSequence()+1);
//                        }
//                        int id = resPicService.add(copyRecPic);
//                        logger.info("add:id=" + id);
//                        copyRecPic.setId(id);
//                    } else {
//                        int id = resPicService.update(copyRecPic);
//                        logger.info("update:id=" + id);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return new ResponseEnvelope<ResPic>(false, "数据异常!");
//                }
//
//                resPic = copyRecPic.copy();
//                resPic.setId(copyRecPic.getId());
//                resIds = (resIds==null?"":resIds) + (StringUtils.isEmpty(resIds)?"":"," ) + resPic.getId();
//            }
//        }
//
//
//
//        if (StringUtils.isEmpty(fileidlist.replaceFirst(";", ""))) {
//            String UPLOAD_PATH = resPic.getFileKey() + ".upload.path";
//            // 单文件上传使用MultipartFile
//            if (filePath != null) {
//                response.setContentType("text/html;charset=utf-8");
//                // 获取文件列表并将物理文件保存到服务器中
//                // filePath设置到model对象中，由model存入数据库中
//                Map<String,Object> map = new HashMap<String,Object>();
//                map.put(FileUploadUtils.UPLOADPATHTKEY, UPLOAD_PATH);
//                map.put(FileUploadUtils.FILE, filePath);
//
//                // 获取文件大小
//                Long fileSize = filePath.getSize();
//                String originalFilename = filePath.getOriginalFilename();
//                // 获取文件后缀(.jpg)
//                String suffix = originalFilename.substring(originalFilename
//                        .lastIndexOf("."));
//                // 获取物理文件名称
//                String filename = originalFilename.substring(0,
//                        originalFilename.lastIndexOf("."));
//                map.put("opType",opType);
//                /*添加code信息(有的文件路径中间有code(样板房))*/
//                map.put("code", resPic.getSysCode());
//
//                Integer fileId = (Integer) fileInfoMap.get(getFilePathRemoveSuffixByResPicInfoMapKey.fileId);
//                if(fileId != null){
//                    resPic.setId(fileId);
//                    resPic.setIsDeleted(0);
//
//                    String filePathRemoveSuffix = (String) fileInfoMap.get(getFilePathRemoveSuffixByResPicInfoMapKey.filePath);
//                    ResPic resPicOld = (ResPic) fileInfoMap.get(getFilePathRemoveSuffixByResPicInfoMapKey.resFile);
//                    if(StringUtils.isNotBlank(resPicOld.getSmallPicInfo())){
//                        resPic.setSmallPicInfo(resPicOld.getSmallPicInfo() + "rebuild:true;");
//                    }
//                    map.put("filePathRemoveSuffix", filePathRemoveSuffix);
//                }
//
//                boolean flag = FileUploadUtils.saveFile(map);
//                if (flag) {
//                    String realPath = (String) map.get(FileUploadUtils.SERVER_FILE_PATH);
//                    String dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
//                    String ftpFilePath =(String) map.get("filePath");
//                    String finalFlieName =(String) map.get("finalFlieName");
//                    File sFile=new File(realPath);
//                    try {
//                        //BufferedImage bufferedImage = ImageIO.read(new File(realPath));
//                        /*应对CMYK模式图片上传报错的情况*/
//                        BufferedImage bufferedImage=null;
//                        try{
//                            bufferedImage = ImageIO.read(sFile);
//                        }catch(Exception e){
//                            try {
//                                ThumbnailConvert tc = new ThumbnailConvert();
//                                tc.setCMYK_COMMAND(sFile.getPath());
//                                bufferedImage = null;
//                                Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
//                                MediaTracker mediaTracker = new MediaTracker(new Container());
//                                mediaTracker.addImage(bufferedImage, 0);
//                                mediaTracker.waitForID(0);
//                                bufferedImage=ThumbnailConvert.toBufferedImage(image);
//                            } catch (Exception e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//
//                        if(map!=null&&map.size()>0){
//                            if(map.get("fileKey")!=null&&!"".equals(map.get("fileKey"))){
////								if(watermark_brand_state!=null&&"1".equals(watermark_brand_state)){
////									if("product.baseBrand.logo".equals(map.get("fileKey").toString())){
////										ImageUtils.watermarkingNew(realPath,false,"product.baseBrand.logo");
////										resPic.setNumAtt2(1);
////										resPicService.update(resPic);
////									}
////								}
////								if(watermark_groupProduct_state!=null&&"1".equals(watermark_groupProduct_state)){
////									if("product.groupProduct.pic".equals(map.get("fileKey").toString())){
////										ImageUtils.watermarkingNew(realPath,false,"product.groupProduct.pic");
////										resPic.setNumAtt2(1);
////										resPicService.update(resPic);
////									}
////								}
//                                if(watermark_brand_state!=null&&"1".equals(watermark_brand_state)){
//                                    if("product.baseProduct.pic.ipad".equals(map.get("fileKey").toString())){
//                                        ImageUtils.watermarkingNew(realPath,false,"product.baseProduct.pic.ipad");
//                                        //
//                                        resPic.setNumAtt2(1);
//                                        resPicService.update(resPic);
//                                    }
//                                }
//                            }
//                        }
//
//                        /*应对CMYK模式图片上传报错的情况END*/
//                        int width = bufferedImage.getWidth(); // 图片宽度
//                        int height = bufferedImage.getHeight();// 图片高度
//                        String format = suffix.replace(".", "");
//                        resPic.setPicWeight(new Integer(width).toString());
//                        resPic.setPicHigh(new Integer(height).toString());
//                        resPic.setPicFormat(format);// 图片格式
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    //仅支持ftp服务器上传
//                    if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==2){
//                        flag = FtpUploadUtils.uploadFile(finalFlieName,realPath,ftpFilePath);
//                        if(flag){
//                            //删除本地
//                            FileUploadUtils.deleteFile(dbFilePath);
//                        }else{
//                            return new ResponseEnvelope<ResPic>(false, "Ftp上传异常!");
//                        }
//                    }
//                    //3 本地和ftp同时上传(默认是本地上传)
//                    if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==3){
//                        flag = FtpUploadUtils.uploadFile(finalFlieName,realPath,ftpFilePath);
//                        if(!flag){
//                            return new ResponseEnvelope<ResPic>(false, "Ftp上传异常!");
//                        }
//                    }
//                    resPic.setPicPath(dbFilePath);
//                    resPic.setFileKey(resPic.getFileKey());
//                    resPic.setBusinessId(resPic.getBusinessId());
//                    logger.debug("------picName:"+resPic.getPicName());
//                    if (StringUtils.isEmpty(resPic.getPicName())) {
//                        if(filename.lastIndexOf(".")!=-1){
//                            resPic.setPicName(filename.substring(0,filename.lastIndexOf(".")));// 业务名称
//                        }else{
//                            resPic.setPicName(filename);
//                        }
//                    }
//                    resPic.setPicFileName(dbFilePath.substring(dbFilePath.lastIndexOf("/") + 1, dbFilePath.lastIndexOf(".")));
//                    resPic.setPicSuffix(suffix);// 文件后缀
//                    resPic.setPicSize(new Long(fileSize).intValue());// 文件大小
//                    String pictype = "无";
//                    //自动识别文件类型
//                    String fileKey=resPic.getFileKey();
//                    String[] fileKeyEle=fileKey.split("\\.");
//                    String a="dictionary.";
//                    boolean j=false;
//                    String fileKeyInfo="";
//                    if(fileKeyEle.length>2){
//                        fileKeyInfo+=Utils.getValueByFileKey(AppProperties.APP, a+fileKeyEle[0], "").split(",")[0];
//                        fileKeyInfo+="-"+Utils.getValueByFileKey(AppProperties.APP, a+fileKeyEle[1], "").split(",")[0];
//                        /*判断fileKeyEle[2]是不是u3dmodel,是的话要带上后缀去识别类型名称*/
//                        if(StringUtils.equals("u3dmodel", fileKeyEle[2])){
//                            if(fileKeyEle.length>3){
//                                fileKeyInfo+="-"+Utils.getValueByFileKey(AppProperties.APP, a+fileKeyEle[2]+"."+fileKeyEle[3], "").split(",")[0];
//                                j=true;
//                            }
//                        }else{
//                            fileKeyInfo+="-"+Utils.getValueByFileKey(AppProperties.APP, a+fileKeyEle[2], "").split(",")[0];
//                            j=true;
//                        }
//
//                    }
//                    if(j){
//                        pictype=new String(fileKeyInfo.getBytes("ISO-8859-1"),"UTF-8");
//                    }
//                    resPic.setPicType(pictype);
//
//                    if (StringUtils.isEmpty(resPic.getPicLevel())) {
//                        resPic.setPicLevel("0");// 图片级别
//                    }
//                    if (StringUtils.isEmpty(resPic.getPicOrdering())) {
//                        resPic.setPicOrdering("0");// 图片排序
//                    }
//                }
//            }
//
//            try {
//                sysSave(resPic, request);
//                resPic.setPicCode(resPic.getSysCode());
//                if (resPic.getId() == null) {
//                    /*查找最大的序号,得到该图片对应的序号*/
//                    ResPicSearch resPicSearch=new ResPicSearch();
//                    resPicSearch.setLimit(1);
//                    resPicSearch.setStart(0);
//                    resPicSearch.setFileKey(resPic.getFileKey());
//                    resPicSearch.setBusinessId(resPic.getBusinessId());
//                    resPicSearch.setOrder(" sequence ");
//                    resPicSearch.setOrderNum(" desc ");
//                    resPicSearch.setSysCode(resPic.getSysCode());
//                    List<ResPic> resPics=resPicService.getPaginatedList(resPicSearch);
//                    if(resPics!=null&&resPics.size()>0){
//                        resPic.setSequence(resPics.get(0).getSequence()+1);
//                    }
//                    int id = resPicService.add(resPic);
//                    logger.info("add:id=" + id);
//                    resPic.setId(id);
//                } else {
//                    int id = resPicService.update(resPic);
//                    logger.info("update:id=" + id);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new ResponseEnvelope<ResPic>(false, "数据异常!");
//            }
//            resIds = (resIds==null?"":resIds) + (StringUtils.isEmpty(resIds)?"":"," ) + resPic.getId();
//        }
//
//
//
//
//
//
//        // 显示列表
//        ResPicSearch resPicSearch = new ResPicSearch();
//        resPicSearch.setIsDeleted(new Integer(0));
//        resPicSearch.setFileKey(resPic.getFileKey());
//        if(resPic.getBusinessId()!=null){
//            resPicSearch.setBusinessId(resPic.getBusinessId());
//        }else{
//            resPicSearch.setBusinessId(-1);
//        }
//        resPicSearch.setResIds(resIds);
//        resPicSearch.setSysCode(resPic.getSysCode());
//        resPicSearch.setStart(resPic.getStart());
//        resPicSearch.setLimit(new Integer(5));
//        /*按照sequence排序*/
//        resPicSearch.setOrder(" sequence ");
//        resPicSearch.setOrderNum(" asc ");
//        if(!StringUtils.isEmpty(resIds)){
//            List<Integer> resIdList = new ArrayList<Integer>();
//            String[] ss = resIds.split(",");
//            for(String s:ss){
//                if(s!=null&&!"".equals(s)&&!"undefined".equals(s)){
//                    resIdList.add(new Integer(s));
//                }
//            }
//            resPicSearch.setResIdList(resIdList);
//        }
//
//        resPic.setResIds(resIds);
//        resPicSearch.setResIdList(null);
//        List<ResPic> list = new ArrayList<ResPic>();
//        int total = 0;
//        try {
//            total = resPicService.getCount(resPicSearch);
//            logger.info("total:" + total);
//
//            if (total > 0) {
//                list = resPicService.getPaginatedList(resPicSearch);
//
//                for(int i=0;i<list.size();i++){
//                    ResPic rp = list.get(i);
//                    //如果资源共享businessId为0，且businessIds不为空，这块主要是判断新增传过来的businessId是0需处理。
//                    if(resPicSearch.getBusinessId()==0 && StringUtils.isNotBlank(rp.getBusinessIds())){
//                        list.remove(i);
//                        i--;
//                        continue;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEnvelope<ResPic>(false, "数据异常!");
//        }
//        ResponseEnvelope<ResPic> res = new ResponseEnvelope<ResPic>(total, list);
//        request.setAttribute("list", list);
//        request.setAttribute("res", res);
//        request.setAttribute("search", resPicSearch);
//        request.setAttribute("opType",opType);
//        request.setAttribute("resPic", resPic);
//        return JSPMAIN + "/batchAdd";
//    }
//
//    /**
//     * 批量文件上传
//     */
//    @RequestMapping(value = "/upload")
//    @ResponseBody
//    public String upload(HttpServletRequest request
//            , HttpServletResponse response, @ModelAttribute("resFile") ResFile resFile) throws Exception {
//        String UPLOAD_PATH = "";
//        String file_key = resFile.getFileKey();
//        if(file_key !=null&&!file_key.equals("")){
//            UPLOAD_PATH = file_key + ".upload.path";
//        }
//
//        if (resFile == null || UPLOAD_PATH==null || "".equals(UPLOAD_PATH)) {
//            return "";
//        }
//
//        //多文件上传使用request
//        if(request instanceof MultipartHttpServletRequest){
//            //获取文件列表并将物理文件保存到服务器中
//            //将HttpServletRequest对象转换为MultipartHttpServletRequest对象
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            String fileName = null;
//
//            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//                MultipartFile mf = entity.getValue();
//                fileName = mf.getOriginalFilename();
//                logger.debug("文件上传名称：" + fileName);
//                response.setContentType("text/html;charset=utf-8");
//                //获取文件列表并将物理文件保存到服务器中
//
//                //filePath设置到model对象中，由model存入数据库中
//                Map map = new HashMap();
//                map.put(FileUploadUtils.UPLOADPATHTKEY, UPLOAD_PATH);
//                boolean flag = false;
//                List<Map> list = new ArrayList<Map>();
//                String dbFilePath = null;
//
//                map.put(FileUploadUtils.FILE, mf);
//                flag = FileUploadUtils.saveFile(map);
//                if (flag) {
//                    dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
//
//                    String realPath = (String) map.get(FileUploadUtils.SERVER_FILE_PATH);
//                    String ftpFilePath =(String) map.get("filePath");
//                    String finalFlieName =(String) map.get("finalFlieName");
//
//                    //仅支持ftp服务器上传
//                    if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==2){
//                        flag = FtpUploadUtils.uploadFile(finalFlieName,realPath,ftpFilePath);
//                        if(!flag){
//                            //删除本地
////								FileUploadUtils.deleteFile(dbFilePath);
//                            //这里不能直接删除文件，因为回填资源需要图片信息，回填完之后在做删除。
//                            return "";
//                        }
//                    }
//                    //3 本地和ftp同时上传
//                    if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==3){
//                        flag = FtpUploadUtils.uploadFile(finalFlieName,realPath,ftpFilePath);
//                        if(!flag){
//                            return "";
//                        }
//                    }
//                    return dbFilePath;
//                }
//            }
//        }
//        return "";
//    }
}
