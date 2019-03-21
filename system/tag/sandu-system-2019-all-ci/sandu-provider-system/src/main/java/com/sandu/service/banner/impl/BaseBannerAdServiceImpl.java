package com.sandu.service.banner.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.sandu.api.banner.input.BaseBannerAdAdd;
import com.sandu.api.banner.input.BaseBannerAdUpdate;
import com.sandu.api.banner.input.BaseBannerListQuery;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.BaseBannerPosition;
import com.sandu.api.banner.model.po.BannerPO;
import com.sandu.api.banner.output.BaseBannerVO;
import com.sandu.api.banner.service.BaseBannerAdService;
import com.sandu.api.banner.service.BaseBannerPositionService;
import com.sandu.api.common.exception.SystemException;
import com.sandu.api.platform.model.BasePlatform;
import com.sandu.api.platform.service.BasePlatformService;
import com.sandu.common.exception.BizException;
import com.sandu.common.exception.bizexceptions.SystemBizException;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.service.banner.dao.BaseBannerAdMapper;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 基础-广告 Service实现类
 * @author WangHaiLin
 * @date 2018/5/16  20:57
 */
@Service("baseBannerAdService")
public class BaseBannerAdServiceImpl implements BaseBannerAdService {
    private final static Logger logger = LoggerFactory.getLogger(BaseBannerAdServiceImpl.class);

    @Autowired
    private BaseBannerAdMapper baseBannerAdMapper;

    @Autowired
    private BasePlatformService basePlatformService;

    @Autowired
    private BaseBannerPositionService baseBannerPositionService;
    
    @Override
    public int addBaseBanner(BaseBannerAdAdd bannerAdd, LoginUser loginUser) {
        BaseBannerAd banner=bannerAdd.getBannerFromBannerAdd(bannerAdd);
        //存储系统字段
        this.saveSystemInfo(banner,loginUser);
        int result = baseBannerAdMapper.insertBanner(banner);
        if (result>0){
            return banner.getId();
        }
        return 0;
    }

    @Override
    public int deleteBanner(Integer bannerId,LoginUser loginUser) {
        BaseBannerAd banner =baseBannerAdMapper.getBannerById(bannerId);
        this.saveSystemInfo(banner,loginUser);
        /*
         * add by zhangchengda
         * 删除banner图后更改其他banner图排序
         */
        if (banner.getSequence() != null) {
            BaseBannerListQuery query = new BaseBannerListQuery();
            query.setPositionId(banner.getPositionId());
            query.setRefModelId(banner.getRefModelId());
            List<BaseBannerAd> bannerList = baseBannerAdMapper.getAllBannerByModelPosition(query);
            bannerList.forEach(baseBannerAd -> {
                if (baseBannerAd.getSequence() != null) {
                    if (baseBannerAd.getSequence() > banner.getSequence()) {
                        baseBannerAd.setSequence(baseBannerAd.getSequence() - 1);
                        baseBannerAdMapper.updateBanner(baseBannerAd);
                    }
                }
            });
        }
        return baseBannerAdMapper.deleteBanner(banner);
    }

    @Override
    public int updateBanner(BaseBannerAdUpdate update,LoginUser loginUser) {
        //获取旧值
        BaseBannerAd bannerAd = baseBannerAdMapper.getBannerById(update.getBannerId());
        //修改属性值
        BaseBannerAd banner = this.getBannerFromBannerUpdate(bannerAd,update);
        //存储修改者和修改时间
        this.saveSystemInfo(banner,loginUser);
        logger.error("修改时入参BaseBannerAd：${}",banner);
        return baseBannerAdMapper.updateBanner(banner);
    }

    //数据转换 BaserBannerUpdate 转换成 BaseBannerAd
    public BaseBannerAd getBannerFromBannerUpdate(BaseBannerAd banner,BaseBannerAdUpdate update){
        if (update.getPositionId()!=null){
            banner.setPositionId(update.getPositionId());
        }if (update.getBannerName()!=null){
            banner.setName(update.getBannerName());
        }if (update.getResBannerPicId()!=null){
            banner.setResBannerPicId(update.getResBannerPicId());
        }if (update.getRefModelId()!=null){
            banner.setRefModelId(update.getRefModelId());
        }if (update.getStatus()!=null){
            banner.setStatus(update.getStatus());
        }if (update.getSequence()!=null){
            banner.setSequence(update.getSequence());
        }
        banner.setUrl(Strings.nullToEmpty(update.getSkipPath()));
        return banner;
    }

    @Override
    public BaseBannerAd getBannerById(Integer bannerId) {
        return baseBannerAdMapper.getBannerById(bannerId);
    }

    @Override
    public List<BaseBannerVO> getBannerList(BannerPO queryList) {
        return baseBannerAdMapper.getBannerList(queryList);
    }

    @Override
    public int getCount(BannerPO bannerPO) {
        return baseBannerAdMapper.getBannerCount(bannerPO);
    }

    @Override
    public List<BaseBannerAd> getBannerByModelPosition(BaseBannerListQuery query) {
        return baseBannerAdMapper.getBannerByModelPosition(query);
    }

    @Override
    public List<BaseBannerAd> getBaseBannerByRefModelId(Integer refModelId) {
        return baseBannerAdMapper.getBannerByRefModelId(refModelId);
    }

    @Override
    public Integer setBannerState(Integer bannerAdId, Integer state, LoginUser loginUser) {
        BaseBannerAd update = new BaseBannerAd();
        update.setId(bannerAdId);
        update.setStatus(state);
        saveSystemInfo(update, loginUser);
        return baseBannerAdMapper.updateBanner(update);
    }

    @Override
    public List<BaseBannerAd> setBannerSequence(Integer bannerAdId, Integer sequence, LoginUser loginUser) {
        BaseBannerAd banner = baseBannerAdMapper.getBannerById(bannerAdId);
        BaseBannerListQuery query = new BaseBannerListQuery();
        query.setPositionId(banner.getPositionId());
        query.setRefModelId(banner.getRefModelId());
        List<BaseBannerAd> bannerList = baseBannerAdMapper.getAllBannerByModelPosition(query);
        Integer oldSequence = banner.getSequence();
        Integer num = sequence > oldSequence ? -1 : 1;
        bannerList.forEach(baseBannerAd -> {
            if (num == -1 ? (baseBannerAd.getSequence() > oldSequence && baseBannerAd.getSequence() <= sequence) :
                    (baseBannerAd.getSequence() < oldSequence && baseBannerAd.getSequence() >= sequence)) {
                baseBannerAd.setSequence(baseBannerAd.getSequence() + num);
                this.saveSystemInfo(baseBannerAd, loginUser);
                baseBannerAdMapper.updateBanner(baseBannerAd);
            }
        });
        banner.setSequence(sequence);
        this.saveSystemInfo(banner, loginUser);
        baseBannerAdMapper.updateBanner(banner);
        return bannerList;
    }

    @Override
    public Integer getBannerCountByModelPosition(Integer positionId, Integer shopId) {
        BaseBannerListQuery query = new BaseBannerListQuery();
        query.setPositionId(positionId);
        query.setRefModelId(shopId);
        return baseBannerAdMapper.getBannerCountByModelPosition(query);
    }

    @Override
    public Object saveBannerAd(BaseBannerAdAdd baseBannerAdAdd, LoginUser loginUser, String platfromCode) {
        
        //获取平台信息
        BasePlatform platform = basePlatformService.getByPlatformCode(platfromCode);

        baseBannerAdAdd.setRefModelId(platform.getId());

        //获取banner位置信息
        BaseBannerPosition baseBannerPosition = baseBannerPositionService.getPositionByCode(baseBannerAdAdd.getPositionCode());

        if (Objects.isNull(baseBannerPosition)){
            throw  new SystemException("bannner位置信息为空");
        }

        baseBannerAdAdd.setPositionId(baseBannerPosition.getId());

        //获取已保存的banner图
        int total = baseBannerAdMapper.countAlreadySaveBanner(baseBannerPosition.getId());

        baseBannerAdAdd.setSequence(total + 1);
        //设置banner开启状态
        baseBannerAdAdd.setStatus(1);

        //查看url中是否带有跳转其他路径参数
        String encodeUrl = this.encodeUrl(baseBannerAdAdd.getUrl());
        baseBannerAdAdd.setUrl(encodeUrl);

        return addBaseBanner(baseBannerAdAdd,loginUser);
    }

    @Override
    public PageInfo<BaseBannerAd> getSXWIndexBannerList(String positionCode,Integer page,Integer limit) {
        BaseBannerPosition positionByCode = baseBannerPositionService.getPositionByCode(positionCode);

        if (Objects.isNull(positionByCode)){
            throw new SystemException("参数错误");
        }

        PageHelper.startPage(page,limit);
        List<BaseBannerAd> baseBannerAds  = baseBannerAdMapper.selectBannersByPositionId(positionByCode.getId());
        DeCoderUrl(baseBannerAds);
        return new PageInfo<>(baseBannerAds);
    }

    @Override
    public int updateSXWBanner(BaseBannerAdUpdate baseBannerAdUpdate) {

        String encodeUrl = encodeUrl(baseBannerAdUpdate.getUrl());

        BaseBannerAd ad = new BaseBannerAd();
        //BeanUtils.copyProperties(baseBannerAdUpdate,ad)
        ad.setName(baseBannerAdUpdate.getBannerName());
        ad.setResBannerPicId(baseBannerAdUpdate.getPicId());
        ad.setId(baseBannerAdUpdate.getBannerId());
        ad.setUrl(encodeUrl);
        return baseBannerAdMapper.updateBanner(ad);
    }

    @Override
    public int changeBannerIsEnable(Integer bannerId, LoginUser loginUser) {

        BaseBannerAd banner = baseBannerAdMapper.getBannerById(bannerId);
        if (banner != null){
            BaseBannerAd update = new BaseBannerAd();
            saveSystemInfo(update,loginUser);
            update.setStatus(banner.getStatus() == 0 ? 1 : 0);
            update.setId(bannerId);
            return baseBannerAdMapper.updateBanner(update);
        }
        return 0;
    }

    private void DeCoderUrl(List<BaseBannerAd> baseBannerAds) {
        //解码带有url参数的路径
        baseBannerAds.stream().forEach(banner ->{
            String skipPath = banner.getUrl();
            if (StringUtils.isEmpty(skipPath))
                return ;

            int index = skipPath.indexOf("?url=");
            if (index >= 0) {
                StringBuilder sb = new StringBuilder();
                String suffix = skipPath.substring(index + "?url=".length(), skipPath.length());
                String prefix = skipPath.substring(0, index + "?url=".length());
                sb.append(prefix);
                try {
                    String encodeUrl = URLDecoder.decode(suffix, "UTF-8");
                    sb.append(encodeUrl);
                    banner.setUrl(sb.toString());
                } catch (Exception e) {
                    logger.error("encode url 失败");
                }

            }
        });
    }

    public  String encodeUrl(String skipPath) {

        if (StringUtils.isEmpty(skipPath))
            return skipPath;

        int index = skipPath.indexOf("?url=");
        if (index >= 0) {
            StringBuilder sb = new StringBuilder();
            String suffix = skipPath.substring(index + "?url=".length(), skipPath.length());
            String prefix = skipPath.substring(0, index + "?url=".length());
            sb.append(prefix);
            try {
                String encodeUrl = URLEncoder.encode(suffix, "UTF-8");
                sb.append(encodeUrl);
            } catch (Exception e) {
                logger.error("encode url 失败");
            }
            return sb.toString();
        }
        return skipPath;
    }

    /**
     * 自动存储系统字段
     * @param banner BannerAd实体
     * @param loginUser 当前登录用户
     */
    public void saveSystemInfo(BaseBannerAd banner, LoginUser loginUser) {
        if(banner != null){
            //新增
            if(banner.getId() == null){
                banner.setGmtCreate(new Date());
                if (null!=loginUser.getLoginName()){
                    banner.setCreator(loginUser.getLoginName());
                }
                banner.setIsDeleted(0);
                if(banner.getSysCode()==null || "".equals(banner.getSysCode())){
                    banner.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }
            //修改
            banner.setGmtModified(new Date());
            if (null!=loginUser.getLoginName()){
                banner.setModifier(loginUser.getLoginName());
            }
        }
    }
}
