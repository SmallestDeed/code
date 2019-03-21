package com.sandu.web.banner;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.banner.input.BaseBannerPositionAdd;
import com.sandu.api.banner.input.BaseBannerPositionIsExist;
import com.sandu.api.banner.input.BaseBannerPositionUpdate;
import com.sandu.api.banner.model.BaseBannerPosition;
import com.sandu.api.banner.output.BaseBannerPositionVO;
import com.sandu.api.banner.service.BaseBannerPositionService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



/**
 * 广告-位置 控制层
 * @author WangHaiLin
 * @date 2018/5/16  15:43
 */
@Api(tags = "position", description = "广告位置")
@RestController
@RequestMapping("/v1/base/banner/position")
public class BaseBannerPositionController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseBannerPositionController.class);

    @Autowired
    private BaseBannerPositionService baseBannerPositionService;

    @ApiOperation(value = "新增广告位置信息", response = ResponseEnvelope.class)
    @PostMapping
    public ResponseEnvelope addBannerPosition(@Valid @RequestBody BaseBannerPositionAdd positionAdd, BindingResult validResult){
        try{
            //数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //判断新增位置是否已经存在
            BaseBannerPositionIsExist isExist=new BaseBannerPositionIsExist();
            if(!" ".equals(positionAdd.getPositionCode())){
                isExist.setCode(positionAdd.getPositionCode());
                isExist.setType(positionAdd.getPositionType());
                BaseBannerPosition position = baseBannerPositionService.getPositionIsExist(isExist);
                //新位置不存在，则添加
                if (null==position){
                    int positionId = baseBannerPositionService.addBannerPosition(positionAdd,loginUser);
                    if (positionId>0){
                        return new ResponseEnvelope<>(true, "创建成功!");
                    }
                    return new ResponseEnvelope<>(false, "创建失败!");
                }
                return new ResponseEnvelope<>(false, "此次添加的位置已经存在,通过'位置编码'和'位置类型'确保广告位置唯一!");
            }
            return new ResponseEnvelope<>(false, "位置编码要传有意义的值，eg：xz:plan:index:top!");
        }catch (Exception ex){
            logger.error("系统异常:",ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }

    @ApiOperation(value = "删除广告位置信息", response = ResponseEnvelope.class)
    @DeleteMapping
    public ResponseEnvelope deletePosition(@RequestParam("positionId")Integer positionId){
        try{
            //校验参数
            if (positionId==null){
                return new ResponseEnvelope<>(false, "positionId不能为空!");
            }
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //查询要删除数据是否存在
            BaseBannerPosition position = baseBannerPositionService.getPositionById(positionId);
            if (position==null){
                return new ResponseEnvelope<>(false, "无此位置信息!");
            }
            //位置信息存在，进行删除
            int result = baseBannerPositionService.deletePosition(positionId,loginUser);
            if(result>0){
                return new ResponseEnvelope<>(true, "删除成功!");
            }
            return new ResponseEnvelope<>(false, "删除失败!");
        }catch (Exception ex){
            logger.error("系统异常",ex);
            return new ResponseEnvelope<>(false, "系统异常!");
        }
    }


    @ApiOperation(value = "修改广告位置信息", response = ResponseEnvelope.class)
    @PutMapping
    public ResponseEnvelope updatePosition(@Valid @RequestBody BaseBannerPositionUpdate update,BindingResult validResult){
        try{
            //1.数据校验
            if (validResult.hasErrors()) {
                return processValidError(validResult, new ResponseEnvelope());
            }
            //获取登录用户
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            //2.查询要修改数据是否存在(用于接口校验)
            BaseBannerPosition position = baseBannerPositionService.getPositionById(update.getPositionId());
            if (position==null){
                return new ResponseEnvelope<>(false, "无此位置信息!");
            }
            //3.将要修改的位置信息，是否唯一
            BaseBannerPositionIsExist isExist=new BaseBannerPositionIsExist();
            isExist.setCode(update.getPositionCode());
            isExist.setType(update.getPositionType());
            BaseBannerPosition isExist1 = baseBannerPositionService.getPositionIsExist(isExist);
            if (isExist1!=null&&isExist1.getId()!=update.getPositionId()){
                return new ResponseEnvelope<>(false, "修改后位置将不唯一,通过'位置编码'和'位置类型'确保广告位置唯一!");
            }
            //4.信息存在，调取服务，进行修改
            int result = baseBannerPositionService.updateBannerPosition(update,loginUser);
            if(result>0){
                return new ResponseEnvelope<>(true, "修改成功!");
            }
            return new ResponseEnvelope<>(false, "修改操作失败!");
        }catch (Exception ex){
            logger.error("系统异常",ex);
            return new ResponseEnvelope<>(false,"系统异常");
        }
    }



    @ApiOperation(value = "通过Id查询广告位置信息", response = BaseBannerPositionVO.class)
    @GetMapping
    public ResponseEnvelope getPositionById(@RequestParam("positionId")Integer positionId){
        try{
            //1.校验参数
            if (positionId==null){
                return new ResponseEnvelope(false,"positionId不能为空");
            }
            //2.进行查询
            BaseBannerPosition position = baseBannerPositionService.getPositionById(positionId);
            //输出转换
            BaseBannerPositionVO positionVO=new BaseBannerPositionVO().getPositionVOFromPosition(position);
            if (null!=position){
                return new ResponseEnvelope(true,"查询成功");
            }
            return new ResponseEnvelope(true,"无此对应位置信息");
        }catch (Exception ex){
            logger.error("系统异常",ex);
            return new ResponseEnvelope(false,"系统异常");
        }

    }


    @ApiOperation(value = "通过类型查询位置信息列表", response = BaseBannerPosition.class)
    @GetMapping("/list")
    public ResponseEnvelope getPositionListByType(@RequestParam("type")Integer type){
        try{
            //1.校验数据
            if (type==null||type<1||type>3){
                return new ResponseEnvelope(false,"type参数错误");
            }
            //2.进行查询
            List<BaseBannerPosition> positionList = baseBannerPositionService.getPositionListByType(type);
            if (positionList!=null&&positionList.size()>0){
                return new ResponseEnvelope(true,positionList.size(),positionList);
            }
            return new ResponseEnvelope(true,"查无此内容");
        }catch (Exception ex){
            logger.error("系统异常",ex);
            return new ResponseEnvelope(false,"系统异常");
        }
    }
}
