package com.sandu.web.designplan.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.designplan.DesignPlanSpaceArea;
import com.sandu.common.objectconvert.designplan.SpaceShapeObject;
import com.sandu.designplan.vo.DesignPlanSpaceAreaVo;
import com.sandu.designplan.vo.SpaceShapeVo;
import com.sandu.product.service.BaseProductStyleService;
import com.sandu.product.vo.DesignProductStyleDicVo;
import com.sandu.system.model.ResPic;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.service.ResPicService;
import com.sandu.system.service.SysDictionaryService;

@Api(tags = "designPlan",description = "设计方案")
@Controller
@RequestMapping("/v1/xzminiprogram/designplan")
public class DesignPlanController {

    //Json转换类
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[设计方案服务]:";
    private final static Logger logger = LoggerFactory.getLogger(DesignPlanController.class);
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private BaseProductStyleService baseProductStyleService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 获取设计方案筛选条件元数据
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取设计方案筛选条件元数据",response = ResponseEnvelope.class)
    @ResponseBody
    @RequestMapping(value = "/designplanconditionmetadata",method = RequestMethod.GET)
    public ResponseEnvelope designplanStyleList(HttpServletRequest request) {

        /*----------------Setp 1:获取设计方案空间类型和风格数据-----------------*/
        List<DesignProductStyleDicVo> designPlanStyleAllDic = baseProductStyleService.getDesignPlanStyleAllDic();
        //logger.info(CLASS_LOG_PREFIX + "获取设计方案风格详情:获取风格字典值完成:JSON[List<DesignProductStyleDicVo>]:{}.", null == designPlanStyleAllDic ? null : gson.toJson(designPlanStyleAllDic));

        /*----------------Setp 2:获取设计方案空间面积数据-----------------*/
        //构造字典值查询对象
        SysDictionary sysDictionaryCondition = new SysDictionary();
        sysDictionaryCondition.setType(SysDictionaryConstant.DESIGNPLAN_SPACE_TYPE);

        //获取组空间字典值
        logger.info(CLASS_LOG_PREFIX + "获取设计方案空间面积数据:获取设计方案空间面积字典值:SysDictionary:{}.", sysDictionaryCondition.toString());
        List<SysDictionary> sysDictionaryParentList = sysDictionaryService.getList(sysDictionaryCondition);
        logger.info(CLASS_LOG_PREFIX + "获取设计方案空间面积数据:获取设计方案空间面积字典值完成:List<SysDictionary>:{}.", gson.toJson(sysDictionaryParentList));
        final List<DesignPlanSpaceAreaVo> designPlanSpaceAreaList = new ArrayList<>();
        //详细查询子数据
        if (null != sysDictionaryParentList) {
            //遍历父节点字典数据--倒序排序
            for (int listLength = sysDictionaryParentList.size(); listLength > 0; listLength--) {
                SysDictionary sysDictionary = sysDictionaryParentList.get(listLength - 1);
                //构造单个对象
                DesignPlanSpaceAreaVo designPlanSpaceAreaVo = new DesignPlanSpaceAreaVo();
                //修改字典值查询参数
                sysDictionaryCondition.setType(sysDictionary.getValuekey());
                List<SysDictionary> sysDictionaryChildrenList = sysDictionaryService.getList(sysDictionaryCondition);
                //装入参数
                designPlanSpaceAreaVo.setDesignPlanSpaceId(sysDictionary.getValue());
                designPlanSpaceAreaVo.setDesignPlanSpaceName(sysDictionary.getName());
                designPlanSpaceAreaVo.setDesignPlanAreaList(DesignPlanSpaceArea.parseToDesignPlanAreaListFromSySDirection(sysDictionaryChildrenList));
                //装入List
                designPlanSpaceAreaList.add(designPlanSpaceAreaVo);
            }
        }

        //遍历List---合并数据
        if(null!=designPlanStyleAllDic&&designPlanStyleAllDic.size()>0) {
        designPlanStyleAllDic.forEach(designProductStyleDicVo -> {
            designPlanSpaceAreaList.forEach(designPlanSpaceAreaVo -> {
                if (designProductStyleDicVo.getHouseType().equals(designPlanSpaceAreaVo.getDesignPlanSpaceId())) {
                    designProductStyleDicVo.setDesignPlanAreaList(designPlanSpaceAreaVo.getDesignPlanAreaList());
                }
            });
        });
        } 
        return new ResponseEnvelope(true, "", designPlanStyleAllDic, null == designPlanStyleAllDic ? 0 : designPlanStyleAllDic.size());
    }

    /**
     * 获取空间形状配置数据
     *
     * @return
     * @date 20171016
     * @author pengxuangang
     */
    @ApiOperation(value = "获取空间形状配置数据" ,response =ResponseEnvelope.class )
    @ResponseBody
    @RequestMapping(value = "/getspaceshape",method = RequestMethod.GET)
    public ResponseEnvelope getSpaceShape() {

        //构造字典值查询对象
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setType(SysDictionaryConstant.DESIGNPLAN_SPACE_TYPE);

        //获取数据字典中空间形状的所有数据
        logger.info(CLASS_LOG_PREFIX + "获取空间形状配置数据->获取数据字典中空间形状的所有数据SysDictionary:{}", sysDictionary.toString());
        List<SysDictionary> sysDictionaryList = sysDictionaryService.findAllByType(SysDictionaryConstant.SPACE_SHAPE);
        logger.info(CLASS_LOG_PREFIX + "获取空间形状配置数据->获取数据字典中空间形状的所有数据完成sysDictionaryList:{}", gson.toJson(sysDictionaryList));

        //初始化返回数据
        List<SpaceShapeVo> spaceShapeVoList = new ArrayList<>(null == sysDictionaryList ? 0 : sysDictionaryList.size());
        for (SysDictionary dictionary : sysDictionaryList) {
            logger.info(CLASS_LOG_PREFIX + "获取空间形状配置数据->通过picId得到图片信息SysDictionary:", dictionary.toString());
            //图片ID
            Integer picId = dictionary.getPicId();
            if (null != picId && 0 != picId) {
                // 遍历得到所有空间形状的picId,并通过picId得到图片信息
                logger.info(CLASS_LOG_PREFIX + "获取空间形状配置数据->通过picId得到图片信息PicId:{}", picId);
                ResPic resPic = resPicService.get(picId);
                if(resPic==null) {
                	logger.error(CLASS_LOG_PREFIX + "获取空间形状配置数据为空->picId:{}", picId);
                	continue;
                }
                logger.info(CLASS_LOG_PREFIX + "获取空间形状配置数据->通过picId得到图片信息ResPic:{}", resPic.toString());

                //装入返回集
                spaceShapeVoList.add(SpaceShapeObject.parseToSpaceShapeVoBySysDictionaryAndPicDate(dictionary, resPic));
            }
         }
        logger.info(CLASS_LOG_PREFIX + "获取空间形状配置数据完成->List<SpaceShapeVo>", gson.toJson(spaceShapeVoList));

        return new ResponseEnvelope(true, "", spaceShapeVoList);
    }

}
