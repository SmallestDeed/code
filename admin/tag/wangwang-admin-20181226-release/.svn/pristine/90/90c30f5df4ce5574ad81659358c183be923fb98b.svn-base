package com.sandu.service.solution.impl.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.platform.model.Platform;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.model.bo.SolutionProductListBO;
import com.sandu.api.product.model.po.ProductQueryPO;
import com.sandu.api.product.service.BaseProductStyleService;
import com.sandu.api.product.service.biz.ProductBizService;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.solution.constant.CopyType;
import com.sandu.api.solution.constant.PlanOrigin;
import com.sandu.api.solution.constant.PlatformType;
import com.sandu.api.solution.constant.ShelfStatus;
import com.sandu.api.solution.input.*;
import com.sandu.api.solution.model.*;
import com.sandu.api.solution.model.bo.*;
import com.sandu.api.solution.model.po.*;
import com.sandu.api.solution.service.*;
import com.sandu.api.solution.service.biz.DesignPlanBizService;
import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.service.ResFileService;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.api.user.model.SysRole;
import com.sandu.api.user.model.SysUserRole;
import com.sandu.api.user.service.SysRoleService;
import com.sandu.common.BaseQuery;
import com.sandu.constant.Constants;
import com.sandu.constant.Punctuation;
import com.sandu.service.solution.dao.*;
import com.sandu.service.solution.utils.FileUtils;
import com.sandu.service.solution.utils.RenderTypeCode;
import com.sandu.service.solution.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.sandu.config.ResPropertiesConstance.PRODUCT_BASE_PRODUCT_MODEL;
import static com.sandu.config.ResPropertiesConstance.PRODUCT_BASE_PRODUCT_U3DMODEL_WINDOWS_PC;
import static com.sandu.constant.Punctuation.COMMA;
import static com.sandu.util.Commoner.isNotEmpty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * @author bvvy
 */
@Service("designPlanBizService")
@Slf4j
@SuppressWarnings("unchecked")
public class DesignPlanBizServiceImpl implements DesignPlanBizService {

	/**
	 * corePoolSize： 线程池维护线程的最少数量 maximumPoolSize：线程池维护线程的最大数量 keepAliveTime： 线程池维护线程所允许的空闲时间 unit：
	 * 线程池维护线程所允许的空闲时间的单位 workQueue： 线程池所使用的缓冲队列 threadFactory：创建执行线程的工厂 handler： 线程池对拒绝任务的处理策略
	 */
	ThreadPoolExecutor executor =
			new ThreadPoolExecutor(
					50,
					300,
					300,
					TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(1000),
					new ThreadPoolExecutor.CallerRunsPolicy());

	@Value("${file.storage.path}")
	private String basePath;

	@Value("${server.url}")
	private String downloadUrl;

	@Resource(name = "resPropertiesMap")
	private Map<String, String> fileKey2Path;

	@Resource
	private DesignPlanRecommendedMapper designPlanRecommendedMapper;

	@Resource
	private DesignPlanRecommendedService designPlanRecommendedService;

	@Resource
	private ProductBizService productBizService;

	@Resource
	private ResRenderPicMapper resRenderPicMapper;

	@Resource
	private ResRenderVideoMapper resRenderVideoMapper;

	@Resource
	private DesignPlan2bPlatformMapper designPlan2bPlatformMapper;

	@Resource
	private DesignPlan2cPlatformMapper designPlan2cPlatformMapper;

	@Resource
	private DesignPlanRecommendedProductService designPlanRecommendedProductService;

	@Resource
	private DesignPlanBrandMapper designPlanBrandMapper;

	@Resource
	private DesignPlanBrandService designPlanBrandService;

	@Resource
	private ResRenderPicService resRenderPicService;

	@Resource
	private DesignPlanCopyLogMapper designPlanCopyLogMapper;

	@Resource
	private PlanDecoratePriceMapper planDecoratePriceMapper;

	@Resource
	private DesignPlanCopyLogService designPlanCopyLogService;

	@Resource
	private DesignPlan2cPlatformService designPlan2cPlatformService;

	@Resource
	private DesignPlan2bPlatformService designPlan2bPlatformService;

	@Resource
	private CompanyService companyService;

	@Resource
	private BaseProductStyleService baseProductStyleService;

	@Resource
	private PlatformService platformService;

	@Resource
	private BrandService brandService;

	@Resource
	private ResFileService resFileService;

	@Resource
	private DictionaryService dictionaryService;

	@Resource
	private DesignRenderRoamService designRenderRoamService;

	@Resource
	private ResRenderVideoService resRenderVideoService;

	@Resource
	private SpaceCommonService spaceCommonService;

	@Resource
	private CompanyDesignPlanIncomeMapper companyDesignPlanIncomeMapper;

	@Resource
	private QueueService queueService;

	@Resource
	private ResPicService resPicService;

	@Autowired
	private ResModelService resModelService;

	@Autowired
	private SysRoleService sysRoleService;

	private static final String PLAN_ROLE_CODE = "Schemecharge";

	private static final String SYS_HOUSE_TYPE = "houseType";

	private static final Integer DEFAULT_COMMON = 1;

	private static final Integer DEFAULT_ONEKEY = 2;

	@Value(value = "${render.url}")
	private String RENDER_URL;

	//    private DesignPlanBizServiceImpl getService() {
	//        return AopContext.currentProxy() != null ? (DesignPlanBizServiceImpl)
	// AopContext.currentProxy() : this;
	//    }

	private List<Integer> getIds(List<DesignPlanEffectDiagramBO> items) {
		return items
				.stream()
				.map(designPlanEffectDiagramBO -> designPlanEffectDiagramBO.getPicId().intValue())
				.collect(toList());
	}

	@Override
	public PageInfo<SolutionProductListBO> listDesignPlanProducts(DesignPlanProductQuery query) {
		List<DesignPlanProductBO> designPlanProductBOS =
				designPlanRecommendedMapper.listDesignPlanProducts(query);
		if (isNotEmpty(designPlanProductBOS)) {
			List<Integer> productIds =
					designPlanProductBOS.stream().map(DesignPlanProductBO::getProductId).collect(toList());
			ProductQueryPO productQueryPO = new ProductQueryPO();
			productQueryPO.setProductIds(productIds);
			productQueryPO.setQueryType("library");
			productQueryPO.setPage(query.getPage());
			productQueryPO.setLimit(query.getLimit());
			if (isNotEmpty(query.getCategoryCode())) {
				productQueryPO.setCategoryCode(query.getCategoryCode());
			}
			productQueryPO.setSecrecy(query.getSecrecy());
			PageInfo<SolutionProductListBO> designPlanProducts =
					productBizService.querySolutionProduct(productQueryPO);
			if (isNotEmpty(designPlanProducts)) {
				for (SolutionProductListBO designPlanProduct : designPlanProducts.getList()) {
					for (DesignPlanProductBO designPlanProductBO : designPlanProductBOS) {
						if (designPlanProduct.getId().equals(designPlanProductBO.getProductId())) {
							designPlanProduct.setUsedCount(designPlanProductBO.getUsedCount());
							designPlanProduct.setDisplayStatus(designPlanProductBO.getDisplayStatus());
							designPlanProduct.setPlanId(designPlanProductBO.getPlanId());
						}
					}
				}
			}
			return designPlanProducts;
		}
		return new PageInfo<>();
	}

	@Override
	public PageInfo<DesignPlanBO> listOnekeyDesignPlan(DesignPlanRecommendedQuery query) {
		query.setRecommendedType(DEFAULT_ONEKEY);
		return this.listDesignPlan(query);
	}

	@Override
	public PageInfo<DesignPlanBO> listCommonDesignPlan(DesignPlanRecommendedQuery query) {
		query.setRecommendedType(DEFAULT_COMMON);
		return this.listDesignPlan(query);
	}

	@Override
	public PageInfo<DesignPlanBO> listOnekeyChannelDesignPlan(DesignPlanRecommendedQuery query) {
		query.setRecommendedType(DEFAULT_ONEKEY);
		return this.listChannelDesignPlan(query);
	}

	@Override
	public PageInfo<DesignPlanBO> onkeyPlanStoreList(DesignPlanRecommendedQuery query) {
		query.setRecommendedType(DEFAULT_ONEKEY);
		return this.storePlanList(query);
	}

	public PageInfo<DesignPlanBO> storePlanList(DesignPlanRecommendedQuery query) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<DesignPlanBO> designPlanBOS = designPlanRecommendedMapper.planStoreList(query);

		mergeDesignPlan(designPlanBOS, query.getCompanyId());
		return new PageInfo<>(designPlanBOS);
	}

	@Override
	public PageInfo<DesignPlanBO> commonPlanStoreList(DesignPlanRecommendedQuery query) {
		query.setRecommendedType(DEFAULT_COMMON);
		return this.storePlanList(query);
	}

	@Override
	public PageInfo<DesignPlanBO> listCommonChannelDesignPlan(DesignPlanRecommendedQuery query) {
		query.setRecommendedType(DEFAULT_COMMON);
		return this.listChannelDesignPlan(query);
	}

	@Override
	public PageInfo<DesignPlanBO> listOnekeyOnlineDesignPlan(DesignPlanRecommendedQuery query) {
		query.setRecommendedType(DEFAULT_ONEKEY);
		return this.listOnlineDesignPlan(query);
	}

	@Override
	public void setOneKeyPlanDefaultDiagram(DesignPicUpdate designPicUpdate) {
		DesignPlanRecommended designPlanRecommended =
				designPlanRecommendedMapper.selectByPrimaryKey(designPicUpdate.getPlanId().longValue());
		designPlanRecommended.setCoverPicId(designPicUpdate.getPicId());
		designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);
	}

	@Override
	public void deleteResRenderPic(Long id) {
		resRenderPicMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void banResRenderPic(Long id) {
		resRenderPicMapper.deleteLogicByPrimaryKey(id);
	}

	@Override
	public void onekeyChannelShelf(DesignPlanChannelShelfPO shelfPO) {

		// 根据方案id判断方案是否组合方案
		if (designPlanRecommendedMapper.getPrimaryPlan(shelfPO.getPlanId()) != null) {
			// 查询该组合方案下的所有子方案
			List<DesignPlanRecommended> plans =
					designPlanRecommendedMapper.queryPrimaryPlan(shelfPO.getPlanId());
			plans.forEach(
					plan -> {
						log.info("##############组合方案子方案{}上架开始###########################", plan.getId());
						DesignPlanRecommended designPlanRecommended =
								designPlanRecommendedMapper.selectByPrimaryKey(plan.getId().longValue());
						designPlanRecommended.setShelfStatus(join(shelfPO.getShelfStatus(), COMMA));
						designPlanRecommended.setReleaseTime(new Date());
						// 上架
						if (shelfPO.getShelfStatus().size() > 0) {
							boolean existed =
									(("share".equals(designPlanRecommended.getPlanSource())
											&& shelfPO.getShelfStatus().contains("OPEN"))
											|| (!"share".equals(designPlanRecommended.getPlanSource()))
											|| (shelfPO.getShelfStatus().contains(("TEMPLATE"))));
							if (existed) {
								handleShelf(plan.getId().intValue());
							}
						} else {
							this.handleCancelShelf(plan.getId().intValue(), shelfPO.getShelfStatus());
						}
						// 来源类型为分享方案智能商家到公开方案里面
						if (!shelfPO.isFlag()) {
							if ("share".equals(designPlanRecommended.getPlanSource())
									&& shelfPO.getShelfStatus().contains("OPEN")) {
								designPlanRecommended.setShelfStatus("OPEN");
							}
						}
						designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);
					});
		}

		// 单方案或组合方案主方案上下架
		DesignPlanRecommended designPlanRecommended =
				designPlanRecommendedMapper.selectByPrimaryKey(shelfPO.getPlanId().longValue());
		designPlanRecommended.setShelfStatus(join(shelfPO.getShelfStatus(), COMMA));
		designPlanRecommended.setReleaseTime(new Date());
		// 上架
		if (shelfPO.getShelfStatus().size() > 0) {
			log.info("##############方案{}上架开始###########################", shelfPO.getPlanId());
			boolean exists =
					(("share".equals(designPlanRecommended.getPlanSource())
							&& shelfPO.getShelfStatus().contains("OPEN"))
							|| (!"share".equals(designPlanRecommended.getPlanSource()))
							|| (shelfPO.getShelfStatus().contains(("TEMPLATE"))));
			if (exists) {
				handleShelf(shelfPO.getPlanId());
			}
		} else {
			this.handleCancelShelf(shelfPO.getPlanId(), shelfPO.getShelfStatus());
		}
		// 来源类型为分享方案智能商家到公开方案里面
		if (!shelfPO.isFlag()) {
			if ("share".equals(designPlanRecommended.getPlanSource())
					&& shelfPO.getShelfStatus().contains("OPEN")) {
				designPlanRecommended.setShelfStatus("OPEN");
			}
		}
		designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);
	}

	@Override
	public void handleCancelShelf(Integer planId, List<String> shelfStatus) {
		designPlan2bPlatformMapper
				.listByPlanIdAndType(planId.longValue(), null)
				.forEach(
						it -> {
							it.setPutawayState(0);
							designPlan2bPlatformMapper.updateByPrimaryKeySelective(it);
						});
		boolean flag = designPlanRecommendedService.getIsValidSharePlan(planId);
		log.debug("the valid share plan flag is {}", flag);
		if (flag) {
			// 获取分享后变成的方案
			List<Integer> planIds =
					designPlanCopyLogMapper.listDeliveredPlanIds(planId, CopyType.SHARE.getCode(), 1);
			// 删除这些方案
			planIds.forEach(designPlanRecommendedService::deleteDesignPlanRecommended);
			// 清除记录
			designPlanCopyLogMapper.deleteBySourceId(planId, CopyType.SHARE.getCode(), 1);
		}
	}

	private void handleShelf(Integer planId) {
		designPlan2bPlatformMapper
				.listByPlanIdAndType(planId.longValue(), null)
				.forEach(
						it -> {
							it.setPutawayState(1);
							designPlan2bPlatformMapper.updateByPrimaryKeySelective(it);
						});
	}

	@Override
	public void commonChannelShelf(DesignPlanChannelShelfPO shelfPO) {
		DesignPlanRecommended designPlanRecommended =
				designPlanRecommendedMapper.selectByPrimaryKey(shelfPO.getPlanId().longValue());
		designPlanRecommended.setShelfStatus(join(shelfPO.getShelfStatus(), COMMA));
		designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);
	}

	@Override
	public List<Integer> deliveredCompanys(Integer deliverCompanyId, Integer planId) {
		return designPlanCopyLogMapper.listCopiedCompanys(
				deliverCompanyId, planId, CopyType.DELIVER.getCode(), 1);
	}

	@Override
	public PageInfo<DesignPlanDeliverBO> deliveredLog(Integer planId, BaseQuery baseQuery) {
		return PageHelper.startPage(baseQuery.getPage(), baseQuery.getLimit())
				.doSelectPageInfo(
						() ->
								designPlanCopyLogMapper.listDesignPlanCopiedLog(
										planId, CopyType.DELIVER.getCode()));
	}

	/**
	 * @param suffix   文件后缀
	 * @param fileKey  文件key标识 design.designPlanRecommended.render.video
	 * @param filePath 文件路径
	 * @param type     文件类型 0-文件;1-视频
	 * @return
	 */
	private Map<String, Object> downloadImageVideo(
			String suffix, String fileKey, String filePath, String type) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer filePathBuffer = new StringBuffer();
		if (StringUtils.isNotBlank(fileKey)) {
			String[] filePathArr = fileKey.split("\\.");
			for (String path : filePathArr) {
				filePathBuffer.append(File.separator + path);
			}
		}
		LocalDateTime now = LocalDateTime.now();

		// 生成文件名称139731_1522404047658
		String dbfileName =
				Utils.generateRandomDigitString(6)
						+ Punctuation.UNDERLINE
						+ Utils.getCurrentDateTime(Utils.DATETIMESSS);

		// 获取远程服务器的图片下载地址:https://show.ci.sanduspace.com/AA/c_basedesign_recommended/2018/03/30/18/design/designPlanRecommended/render/139731_1522404047658.jpg
		String remoteUrl = downloadUrl + filePath;

		// 根据key获取路径
		String dirPath = this.getPathByFileKey(fileKey, now);

		String dbfilePath = dirPath + dbfileName + suffix;
		//        String dbfilePath = secondPath + nowPath + filePathBuffer.toString() + File.separator
		// + dbfileName + suffix;

		// 本地服务器文件存储路径:
		// data01/resource/windowsPc/resRenderPic/2018/03/30/18/design/designPlanRecommended/render/139731_1522404047658.jpg
		String localFilePath = basePath + dbfilePath;
		// 原方案文件夹路径：data001/resource/${pic_path};
		String path = basePath + filePath;
		File file = new File(path);
		// 判断是否文件夹
		if (file.isDirectory()) {
			// 本地服务器存储路径 去除后缀.zip
			localFilePath = localFilePath.substring(0, localFilePath.lastIndexOf("."));
			// 数据库存储pic_path 去除后缀.zip
			dbfilePath = dbfilePath.substring(0, dbfilePath.lastIndexOf("."));
			// 获取原方案文件夹下所有文件组
			String[] filePaths = file.list();
			// 创建本地新的文件目录
			if (!(new File(localFilePath)).exists()) {
				(new File(localFilePath)).mkdirs();
			}
			// 复制文件夹下的所有文件
			for (int i = 0; i < filePaths.length; i++) {
				log.info("######################开始遍历文件夹下文件:{}####################", filePaths[i]);
				File oldFile = new File(path + File.separator + filePaths[i]);
				File newFile = new File(localFilePath + File.separator + filePaths[i]);
				Files.copy(oldFile, newFile);
				log.info("######################结束遍历文件夹下文件:{}####################", filePaths[i]);
			}
		} else {
			log.info("######################开始下载文件{}####################", remoteUrl);
			// 下载文件到本地服务器
			FileUtils.downloadFile(remoteUrl, localFilePath);
		}
		resultMap.put("dbfileName", dbfileName);
		resultMap.put("dbfilePath", dbfilePath);
		return resultMap;
	}

	/**
	 * 按品牌或者企业二次交付
	 *
	 * @param designPlanDelivery
	 */
	private void deliverAgain(DesignPlanDeliveryPO designPlanDelivery) {
		// 遍历被交付的企业
		for (Integer receive : designPlanDelivery.getReceiveCompanyIds()) {
			DesignPlanCopyLog query = new DesignPlanCopyLog();
			query.setSourceId(designPlanDelivery.getDesignPlanId());
			query.setSourceCompanyId(designPlanDelivery.getDeliveryCompanyId());
			query.setTargetCompanyId(receive);
			query.setPlanType(1);
			// 查询该企业是否有被交付的记录
			List<DesignPlanCopyLog> logs = designPlanCopyLogMapper.queryLog(query);
			if (logs.size() > 0) {
				// 公司对应的品牌id集
				List<Integer> brandIds = new ArrayList<>();
				// 按公司交付
				if (designPlanDelivery.getReceiveBrandIds().size() == 0
						|| designPlanDelivery.getReceiveBrandIds() == null) {
					// 根据公司id查询该公司对应的品牌id
					List<Brand> brands = brandService.getBrandByCompanyId(receive);
					for (Brand brand : brands) {
						brandIds.add(brand.getId().intValue());
					}
				} else {
					// 按品牌交付
					for (Integer id : designPlanDelivery.getReceiveBrandIds()) {
						// 匹配品牌对应的企业
						Brand brand = brandService.getBrandById(id);
						if (receive.equals(brand.getCompanyId())) {
							brandIds.add(brand.getId().intValue());
						}
					}
				}
				// 遍历品牌
				brandIds.forEach(
						id -> {
							// 查询该品牌是否已被交付
							DesignPlanBrand brand =
									designPlanBrandMapper.getBrandInfo(logs.get(0).getTargetId(), id, receive);
							if (brand == null) {
								// 组合方案子方案交付
								if (designPlanRecommendedMapper.getPrimaryPlan(logs.get(0).getTargetId()) != null) {
									List<DesignPlanRecommended> plans =
											designPlanRecommendedMapper.queryPrimaryPlan(logs.get(0).getTargetId());
									plans.forEach(
											plan -> {
												DesignPlanBrand designPlanBrand = new DesignPlanBrand();
												designPlanBrand.setPlanId(plan.getId().intValue());
												designPlanBrand.setBrandId(id);
												designPlanBrand.setCompanyId(receive);
												designPlanBrand.setIsDeleted(0);
												designPlanBrand.setGmtCreate(new Date());
												designPlanBrand.setGmtModified(new Date());
												designPlanBrandMapper.insertSelective(designPlanBrand);
											});
								}
								DesignPlanBrand designPlanBrand = new DesignPlanBrand();
								designPlanBrand.setPlanId(logs.get(0).getTargetId());
								designPlanBrand.setBrandId(id);
								designPlanBrand.setCompanyId(receive);
								designPlanBrand.setIsDeleted(0);
								designPlanBrand.setGmtCreate(new Date());
								designPlanBrand.setGmtModified(new Date());
								designPlanBrandMapper.insertSelective(designPlanBrand);
							}
						});
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Integer> deliver(DesignPlanDeliveryPO designPlanDelivery, boolean isBatch) {
		// 根据交付公司id和方案id查询 该方案已经交付过的公司id集合
		List<Integer> deliveredCompanyIds =
				this.deliveredCompanys(
						designPlanDelivery.getDeliveryCompanyId(), designPlanDelivery.getDesignPlanId());
		// 判断是否批量交付（批量交付不能取消）
		if (!isBatch) {
			// 取消交付公司
			if (designPlanDelivery.getReceiveBrandIds().size() == 0) {
				deliveredCompanyIds.forEach(
						deliveredCId -> {
							if (!designPlanDelivery.getReceiveCompanyIds().contains(deliveredCId)) {
								StringBuilder sb = new StringBuilder();
								// 取消组合方案子方案
								if (designPlanRecommendedMapper.getPrimaryPlan(designPlanDelivery.getDesignPlanId())
										!= null) {
									List<DesignPlanRecommended> plans =
											designPlanRecommendedMapper.queryPrimaryPlan(
													designPlanDelivery.getDesignPlanId());
									plans.forEach(
											plan -> {
												this.cancelDeliver2Company(deliveredCId, plan.getId().intValue());
												sb.append(plan.getId()).append(",");
											});
								}
								// 取消组合方案主方案或单方案
								this.cancelDeliver2Company(deliveredCId, designPlanDelivery.getDesignPlanId());
								sb.append(designPlanDelivery.getDesignPlanId());
								// 同步到es
								List<Integer> planidList =
										Arrays.stream(sb.toString().split(COMMA))
												.map(s -> Integer.parseInt(s))
												.collect(Collectors.toList());
								List<Integer> planId =
										designPlanRecommendedService.selectMainSubPlanList(planidList);
								sycMessageDoSend(SyncMessage.ACTION_DELETE, planId);
							}
						});
			}
		}
		// 根据品牌进行二次交付 (该方案已交付给该公司，第二次按该公司的品牌再次交付)
		if (designPlanDelivery.getReceiveBrandIds() != null
				&& designPlanDelivery.getReceiveBrandIds().size() > 0) {

			if (designPlanDelivery.getReceiveBrandIds().size() > 0) {
				log.info("##############按品牌进行二次交付{}开始###########################",designPlanDelivery);
				deliverAgain(designPlanDelivery);
			}
		} else {
			// 根据企业进行二次交付 (该方案已交付给该公司，第二次按该公司再次交付)
			log.info("##############按企业进行二次交付{}开始###########################",designPlanDelivery);
			deliverAgain(designPlanDelivery);
		}

		return designPlanDelivery
				.getReceiveCompanyIds()
				.stream()
				.filter(
						id -> {
							log.debug("-------------------------{}", deliveredCompanyIds);
							return !deliveredCompanyIds.contains(id);
						})
				.map(
						companyId -> {
							// 同步id到es
							StringBuilder sb = new StringBuilder();
							// 复制方案
							DesignPlanRecommended tempDesignPlan =
									designPlanRecommendedMapper.selectByPrimaryKey(
											designPlanDelivery.getDesignPlanId().longValue());
							tempDesignPlan.setGmtCreate(new Date());
							tempDesignPlan.setId(null);
							tempDesignPlan.setGmtModified(new Date());
							tempDesignPlan.setPlanSource(PlanOrigin.DELIVER.getCode());
							tempDesignPlan.setCompanyId(companyId);
							tempDesignPlan.setShelfStatus("");
							designPlanRecommendedMapper.insertSelective(tempDesignPlan);
							sb.append(tempDesignPlan.getId()).append(",");
							final Integer pKey = tempDesignPlan.getId().intValue();
							// 处理单个方案
							if (tempDesignPlan.getGroupPrimaryId() == 0) {
								log.info(
										"##############交付方案{}开始###########################",
										designPlanDelivery.getDesignPlanId());
								executor.execute(
										() -> {
											log.info(
													"##############线程处理开始,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
													designPlanDelivery.getDesignPlanId(),
													executor.getTaskCount(),
													executor.getActiveCount(),
													executor.getCompletedTaskCount());
											// 复制单个方案品牌，产品，图像，视频，报价信息
											processBrandPoductImage(
													designPlanDelivery.getReceiveBrandIds(),
													companyId,
													designPlanDelivery.getDesignPlanId(),
													tempDesignPlan.getId().intValue(),
													designPlanDelivery.getDelivererId() + "");
											log.info(
													"##############线程处理结束,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
													designPlanDelivery.getDesignPlanId(),
													executor.getTaskCount(),
													executor.getActiveCount(),
													executor.getCompletedTaskCount());
										});
								// 处理组合方案
							} else {
								DesignPlanRecommended modifyGroup = new DesignPlanRecommended();
								modifyGroup.setId(tempDesignPlan.getId());
								modifyGroup.setGroupPrimaryId(tempDesignPlan.getId().intValue());
								designPlanRecommendedMapper.updateByPrimaryKeySelective(modifyGroup);
								executor.execute(
										() -> {
											// 复制主方案品牌，产品，图像，视频
											processBrandPoductImage(
													designPlanDelivery.getReceiveBrandIds(),
													companyId,
													designPlanDelivery.getDesignPlanId(),
													tempDesignPlan.getId().intValue(),
													designPlanDelivery.getDelivererId() + "");
											// 获取子方案列表
											List<DesignPlanRecommended> plans =
													designPlanRecommendedMapper.queryPrimaryPlan(
															designPlanDelivery.getDesignPlanId());
											plans.forEach(
													plan -> {
														DesignPlanRecommended tempDesignPlanSon =
																designPlanRecommendedMapper.selectByPrimaryKey(plan.getId());
														tempDesignPlanSon.setGmtCreate(new Date());
														tempDesignPlanSon.setGmtModified(new Date());
														tempDesignPlanSon.setId(null);
														tempDesignPlanSon.setPlanSource(PlanOrigin.DELIVER.getCode());
														tempDesignPlanSon.setCompanyId(companyId);
														tempDesignPlanSon.setShelfStatus("");
														tempDesignPlanSon.setGroupPrimaryId(pKey);
														designPlanRecommendedMapper.insertSelective(tempDesignPlanSon);
														sb.append(tempDesignPlanSon.getId()).append(",");
														log.info(
																"##############交付方案{}开始###########################", plan.getId());
														log.info(
																"##############线程处理开始,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
																plan.getId(),
																executor.getTaskCount(),
																executor.getActiveCount(),
																executor.getCompletedTaskCount());
														// 复制子方案品牌，产品，图像，视频
														processBrandPoductImage(
																designPlanDelivery.getReceiveBrandIds(),
																companyId,
																plan.getId().intValue(),
																tempDesignPlanSon.getId().intValue(),
																designPlanDelivery.getDelivererId() + "");
														log.info(
																"##############线程处理结束,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
																plan.getId(),
																executor.getTaskCount(),
																executor.getActiveCount(),
																executor.getCompletedTaskCount());
														DesignPlanCopyLog designPlanCopyLogSon = new DesignPlanCopyLog();
														designPlanCopyLogSon.setGmtCreate(new Date());
														designPlanCopyLogSon.setKind(CopyType.DELIVER.getCode());
														designPlanCopyLogSon.setSourceId(plan.getId().intValue());
														designPlanCopyLogSon.setTargetId(tempDesignPlanSon.getId().intValue());
														designPlanCopyLogSon.setTargetCompanyId(companyId);
														designPlanCopyLogSon.setSourceCompanyId(
																designPlanDelivery.getDeliveryCompanyId());
														designPlanCopyLogSon.setTargetKind(
																tempDesignPlanSon.getRecommendedType().byteValue());
														designPlanCopyLogMapper.insertSelective(designPlanCopyLogSon);
														log.info(
																"##############交付方案{}结束###########################", plan.getId());
													});
										});
							}
							// 同步到es
							List<Integer> planidList =
									Arrays.stream(sb.toString().split(COMMA))
											.map(s -> Integer.parseInt(s))
											.collect(Collectors.toList());
							List<Integer> planId = designPlanRecommendedService.selectMainSubPlanList(planidList);
							sycMessageDoSend(SyncMessage.ACTION_ADD, planId);

							DesignPlanCopyLog designPlanCopyLog = new DesignPlanCopyLog();
							designPlanCopyLog.setGmtCreate(new Date());
							designPlanCopyLog.setKind(CopyType.DELIVER.getCode());
							designPlanCopyLog.setSourceId(designPlanDelivery.getDesignPlanId());
							designPlanCopyLog.setTargetId(tempDesignPlan.getId().intValue());
							designPlanCopyLog.setTargetCompanyId(companyId);
							designPlanCopyLog.setSourceCompanyId(designPlanDelivery.getDeliveryCompanyId());
							designPlanCopyLog.setTargetKind(tempDesignPlan.getRecommendedType().byteValue());
							designPlanCopyLogMapper.insertSelective(designPlanCopyLog);
							log.info(
									"##############交付方案{}结束###########################",
									designPlanDelivery.getDesignPlanId());
							return tempDesignPlan.getId().intValue();
						})
				.collect(toList());
	}

	/**
	 * 品牌复制，方案关联产品复制,图片视频复制
	 *
	 * @param receiveBrandIds
	 * @param companyId
	 */
	public void processBrandPoductImage(
			List<Integer> receiveBrandIds,
			Integer companyId,
			Integer oldDesignPlanId,
			Integer newDesignPlanId,
			String userId) {
		// 获取原报价信息
		List<PlanDecoratePrice> planDecoratePrices =
				planDecoratePriceMapper.selectByDesignPlanId(oldDesignPlanId);
		// 复制报价信息
		for (PlanDecoratePrice planDecoratePrice : planDecoratePrices) {
			planDecoratePrice.setId(null);
			planDecoratePrice.setPlanRecommendId(newDesignPlanId);
			planDecoratePriceMapper.insertSelective(planDecoratePrice);
		}
		// 根据品牌货公司交付 方案分发/交付公司
		List<Integer> brandIds = new ArrayList<>();
		if (receiveBrandIds.size() > 0) {
			for (Integer ids : receiveBrandIds) {
				Brand brand = brandService.getBrandById(ids);
				if (companyId.equals(brand.getCompanyId())) {
					brandIds.add(brand.getId().intValue());
				}
			}
		} else {
			// 根据公司id查询该公司对应的品牌id
			List<Brand> brands = brandService.getBrandByCompanyId(companyId);
			for (Brand brand : brands) {
				brandIds.add(brand.getId().intValue());
			}
			if (brandIds.size() == 0) {
				brandIds.add(-1);
			}
		}
		for (Integer id : brandIds) {
			DesignPlanBrand designPlanBrand = new DesignPlanBrand();
			designPlanBrand.setPlanId(newDesignPlanId);
			designPlanBrand.setBrandId(id);
			designPlanBrand.setCompanyId(companyId);
			designPlanBrand.setIsDeleted(0);
			designPlanBrand.setGmtCreate(new Date());
			designPlanBrand.setGmtModified(new Date());
			designPlanBrandMapper.insertSelective(designPlanBrand);
		}
		// copy plan  product
		List<DesignPlanRecommendedProduct> products =
				designPlanRecommendedProductService.listByPlanId(oldDesignPlanId);
		designPlanRecommendedProductService.batchAddDesignPlanProducts(products, newDesignPlanId + "");
		// copy plan render pic
		List<ResRenderPic> renderPics = resRenderPicMapper.listByPlan(oldDesignPlanId + "");
		// 处理视频图片多点
		processImageRoamVideo(renderPics, oldDesignPlanId + "", newDesignPlanId + "");
	}

	/**
	 * 取消交付
	 *
	 * @param deliveredCId 被取消交付的公司id
	 * @param designPlanId 被取消交付方案id
	 */
	private void cancelDeliver2Company(Integer deliveredCId, Integer designPlanId) {
		// 1. 获取交付后变成的方案
		List<Integer> planIds =
				designPlanCopyLogMapper.listDeliveredPlanId(
						deliveredCId, designPlanId, CopyType.DELIVER.getCode(), 1);
		// 2. 删除该方案
		planIds.forEach(designPlanRecommendedService::deleteDesignPlanRecommended);
		// 3. 删除记录中间表
		designPlanCopyLogMapper.deleteByTargetCompanyAndSource(
				deliveredCId, designPlanId, CopyType.DELIVER.getCode(), 1);
		// 4. 删除方案产品表
		planIds.forEach(
				id -> {
					designPlanBrandMapper.deleteByPlanIdAndCompanyId(id, deliveredCId);
					planDecoratePriceMapper.deleteByRecommonedId(id);
				});
	}

	private void dealWithDesignPlan(List<DesignPlanBO> designPlanBOS, Integer companyId) {
		log.info("##############关联方案相关信息表{}开始###########################");
		// 方案ID
		List<Integer> planIds =
				designPlanBOS.stream().map(it -> it.getPlanId().intValue()).collect(toList());
		// 空间类型ID
		List<String> spaceCommonIds =
				designPlanBOS
						.stream()
						.filter(s -> s.getSpaceTypeId() != null)
						.map(it -> String.valueOf(it.getSpaceTypeId()))
						.distinct()
						.collect(toList());
		// 方案图片id
		List<Integer> picIds = designPlanBOS.stream().map(DesignPlanBO::getPicId).collect(toList());
		// 方案风格id
		List<Long> styleIds =
				designPlanBOS
						.stream()
						.filter(s -> s.getDesignStyleId() != null)
						.map(it -> it.getDesignStyleId().longValue())
						.collect(toList());
		long queryBeginStart = System.currentTimeMillis();
		Map<Long, Integer> spaceFunctionMap = spaceCommonService.idAndSpaceTypeMap(spaceCommonIds);
		long queryBeginEnd = System.currentTimeMillis();
		log.info("spaceCommonService.idAndSpaceTypeMap方法查询耗时{}", (queryBeginEnd - queryBeginStart));
		// 根据图片id查询图片途径
		long queryPicBegin = System.currentTimeMillis();
		Map<Integer, String> picPathMap = resRenderPicService.idAndPathMap(picIds);
		long queryPicEnd = System.currentTimeMillis();
		log.info("resRenderPicService.idAndPathMap方法查询耗时{}", (queryPicEnd - queryPicBegin));
		List<BaseProductStyle> baseProductStyles =
				baseProductStyleService.getBaseProductStyleByIds(styleIds);
		// 查询字典所有空间类型名称
		long queryDicBegin = System.currentTimeMillis();
		Map<Integer, String> houseType = dictionaryService.valueAndName2Map("houseType");
		long queryDicEnd = System.currentTimeMillis();
		log.info("dictionaryService.valueAndName2Map方法查询耗时{}", (queryDicEnd - queryDicBegin));
		// 查询platform信息
		long queryPlatBegin = System.currentTimeMillis();
		Map<Integer, String> platformMap = platformService.getAllPlatformIdAndName();
		long queryPlatEnd = System.currentTimeMillis();
		log.info("dictionaryService.valueAndName2Map方法查询耗时{}", (queryPlatEnd - queryPlatBegin));
		// 获取方案交付次数
		Map<Integer, String> deliverTimesMap = new HashMap<Integer, String>();
		if (Optional.ofNullable(planIds).isPresent() && planIds.size() > 0) {
			long queryTimesBegin = System.currentTimeMillis();
			deliverTimesMap = designPlanRecommendedService.getDeliverTimesByPlanId(planIds, companyId);
			long queryTimesEnd = System.currentTimeMillis();
			log.info("dictionaryService.valueAndName2Map方法查询耗时{}", (queryTimesEnd - queryTimesBegin));
		}
		// 处理品牌
		List<String> brandIdsAllStr = new ArrayList<String>();
		designPlanBOS.forEach(
				design -> {
					if (StringUtils.isNoneBlank(design.getBrandId())) {
						List<String> tmpList = new ArrayList(Arrays.asList(design.getBrandId().split(COMMA)));
						brandIdsAllStr.addAll(tmpList);
					}
				});
		Map<Integer, String> brandNameMap = new HashMap<Integer, String>();
		if (Optional.ofNullable(brandIdsAllStr).isPresent()) {
			Set<String> sets = new HashSet(brandIdsAllStr.size());
			sets.addAll(brandIdsAllStr);
			brandIdsAllStr.clear();
			brandIdsAllStr.addAll(sets);
			List<Integer> brandIdList =
					brandIdsAllStr.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
			if (Optional.ofNullable(brandIdList).isPresent()) {
				brandNameMap = brandService.getBrandNameByIds(brandIdList);
			}
		}
		List<String> plat2bPlats =
				Arrays.asList(
						ShelfStatus.ONEKEY.getCode(),
						ShelfStatus.OPEN.getCode(),
						ShelfStatus.TEMPLATE.getCode());
		for (DesignPlanBO designPlanBO : designPlanBOS) {
			log.info(
					"##############设置方案id:{}相关信息表开始###########################", designPlanBO.getPlanId());
			// 把风格放进去
			for (BaseProductStyle baseProductStyle : baseProductStyles) {
				boolean existed =
						isNotEmpty(designPlanBO.getDesignStyleId())
								&& designPlanBO.getDesignStyleId().equals(baseProductStyle.getId().intValue());
				if (existed) {
					designPlanBO.setDesignStyleName(baseProductStyle.getName());
				}
			}
			// 设置图片
			designPlanBO.setPicPath(picPathMap.get(designPlanBO.getPicId()));
			// 设置空间名称
			if (designPlanBO.getSpaceTypeId() != null) {
				if (spaceFunctionMap.get(designPlanBO.getSpaceTypeId().longValue()) != null) {
					designPlanBO.setSpaceTypeName(
							houseType.get(spaceFunctionMap.get(designPlanBO.getSpaceTypeId().longValue())));
					designPlanBO.setSpaceTypeId(
							spaceFunctionMap.get(designPlanBO.getSpaceTypeId().longValue()));
				} else {
					designPlanBO.setSpaceTypeId(null);
					designPlanBO.setSpaceTypeName("");
				}
			} else {
				designPlanBO.setSpaceTypeId(null);
				designPlanBO.setSpaceTypeName("");
			}
			// 查询方案品牌id对应的名称
			List<String> brandIdStrs =
					Splitter.on(COMMA)
							.omitEmptyStrings()
							.trimResults()
							.splitToList(Strings.nullToEmpty(designPlanBO.getBrandId()));
			// 设置对应品牌名称
			if (brandIdStrs.size() > 0 && brandIdStrs != null) {
				StringBuffer brandBuffer = new StringBuffer();
				for (String brandId : brandIdStrs) {
					if (brandNameMap.get(Integer.parseInt(brandId)) != null) {
						brandBuffer.append(brandNameMap.get(Integer.parseInt(brandId))).append(",");
					}
				}
				if (StringUtils.isNoneBlank(brandBuffer.toString())) {
					designPlanBO.setBrandName(
							brandBuffer.toString().substring(0, brandBuffer.toString().length() - 1));
				}
			} else {
				designPlanBO.setBrandName("");
			}
			// 设置平台名称
			if (!Strings.isNullOrEmpty(designPlanBO.getPlatformIds())) {
				String[] platformId = designPlanBO.getPlatformIds().split(",");
				StringBuilder sb = new StringBuilder();
				StringBuilder sbIds = new StringBuilder();
				for (String str : platformId) {
					if (!StringUtil.isEmpty(str) && platformMap.containsKey(Integer.valueOf(str))) {
						sbIds.append(str).append(",");
						sb.append(platformMap.get(Integer.valueOf(str))).append(",");
					}
				}
				if (StringUtils.isNoneBlank(sb.toString())) {
					designPlanBO.setPlatformNames(sb.toString().substring(0, sb.toString().length() - 1));
					designPlanBO.setPlatformIds(sbIds.toString().substring(0, sbIds.toString().length() - 1));
				}
			}
			StringBuffer platformIds =
					new StringBuffer(Optional.ofNullable(designPlanBO.getPlatformIds()).orElse(""));
			StringBuffer platformNames =
					new StringBuffer(Optional.ofNullable(designPlanBO.getPlatformNames()).orElse(""));
			List<String> shelStatusList =
					designPlanBO.getShelfStatus() == null
							? Collections.emptyList()
							: Arrays.asList(designPlanBO.getShelfStatus().split(Punctuation.COMMA));
			if (shelStatusList != null) {
				shelStatusList.forEach(
						shelf_status -> {
							plat2bPlats.forEach(
									platform -> {
										if (shelf_status.equals(platform)) {
											if (StringUtils.isNotBlank(platformIds.toString())) {
												platformIds.append(Punctuation.COMMA);
											}
											if (StringUtils.isNotBlank(platformNames.toString())) {
												platformNames.append(Punctuation.COMMA);
											}
											platformIds.append(platform);
											if (shelf_status.equals(ShelfStatus.ONEKEY.getCode())) {
												platformNames.append("通用版-一键方案");
											} else if (shelf_status.equals(ShelfStatus.OPEN.getCode())) {
												platformNames.append("通用版-公开方案");
											} else if (shelf_status.equals(ShelfStatus.TEMPLATE.getCode())) {
												platformNames.append("通用版-样板方案");
											}
										}
									});
						});
				designPlanBO.setPlatformIds(platformIds.toString());
				designPlanBO.setPlatformNames(platformNames.toString());
			}
			// 设置方案交付次数
			if (deliverTimesMap != null
					&& deliverTimesMap.get(designPlanBO.getPlanId().intValue()) != null) {
				designPlanBO.setDeliverStatus(deliverTimesMap.get(designPlanBO.getPlanId().intValue()));
			} else {
				designPlanBO.setDeliverStatus("0");
			}
			log.info(
					"##############设置方案id:{}相关信息表结束###########################", designPlanBO.getPlanId());
		}
		log.info("##############关联方案相关信息表{}结束###########################");
	}

	private void mergeDesignPlan(List<DesignPlanBO> designPlanBOS, Integer companyId) {
		log.info("##############关联方案相关信息表{}开始###########################");
		// 方案图片id
		List<Integer> picIds = designPlanBOS.stream().map(DesignPlanBO::getPicId).collect(toList());
		// 方案风格id
		List<Long> styleIds =
				designPlanBOS
						.stream()
						.filter(s -> s.getDesignStyleId() != null)
						.map(it -> it.getDesignStyleId().longValue())
						.collect(toList());
		//        //方案品牌id
		//        List<String> brands = brandService.getBrandByCompanyId(companyId).stream().map(it ->
		// it.getId().toString()).collect(toList());
		// 方案id
		List<Integer> planIds =
				designPlanBOS.stream().map(it -> it.getPlanId().intValue()).collect(toList());
		// 根据图片id查询图片途径
		Map<Integer, String> picPathMap = resRenderPicService.idAndPathMap(picIds);
		List<BaseProductStyle> baseProductStyles =
				baseProductStyleService.getBaseProductStyleByIds(styleIds);
		// 根据方案id查询方案是否公开
		Map<Integer, Integer> secrecyFlagMap =
				designPlanRecommendedService.idAndSecrecyFlagMap(planIds);
		// 查询字典所有空间类型名称
		Map<Integer, String> houseType = dictionaryService.valueAndName2Map("houseType");
		// 查询方案品牌表brand_id
		List<Long> brandIdList = designPlanBOS.stream().map(DesignPlanBO::getPlanId).collect(toList());
		Map<Integer, String> brandIdMap =
				designPlanBrandService.idAndSpaceTypeMap(brandIdList, companyId);
		// 查询platform信息
		Map<Integer, String> platformMap = platformService.getAllPlatformIdAndName();
		//        //查询空间类型id
		//        List<String> spaceCommonIds =
		// designPlanBOS.stream().map(DesignPlanBO::getSpaceCommonId).collect(toList());
		//        Map<Long, Integer> spaceTypeMap =
		// spaceCommonService.idAndSpaceTypeMap(spaceCommonIds);
		//        //查询方案交付记录
		//        Map<Integer, String> isDeliveredMap =
		// designPlanCopyLogService.idAndDeliveredMap(planIds, companyId);
		//        //查询分配2b端记录
		//        Map<Integer, DesignPlan2bPlatform> platform2bMap =
		// designPlan2bPlatformService.idAnd2bMap(planIds);
		//        //查询分配2c端记录
		//        Map<Integer, DesignPlan2cPlatform> platform2cMap =
		// designPlan2cPlatformService.idAnd2cMap(planIds);
		for (DesignPlanBO designPlanBO : designPlanBOS) {
			log.info(
					"##############设置方案id:{}相关信息表开始###########################", designPlanBO.getPlanId());
			// 把风格放进去
			for (BaseProductStyle baseProductStyle : baseProductStyles) {
				boolean existed =
						isNotEmpty(designPlanBO.getDesignStyleId())
								&& designPlanBO.getDesignStyleId().equals(baseProductStyle.getId().intValue());
				if (existed) {
					designPlanBO.setDesignStyleName(baseProductStyle.getName());
				}
			}
			// 设置图片
			designPlanBO.setPicPath(picPathMap.get(designPlanBO.getPicId()));
			// 设置空间名称
			designPlanBO.setSpaceTypeName(houseType.get(designPlanBO.getSpaceTypeId()));
			// 设置方案品牌id
			designPlanBO.setBrandId(brandIdMap.get(designPlanBO.getPlanId().intValue()));
			// 查询方案品牌id对应的名称
			List<String> brandIdStrs =
					Splitter.on(COMMA)
							.omitEmptyStrings()
							.trimResults()
							.splitToList(Strings.nullToEmpty(designPlanBO.getBrandId()));
			// 设置对应品牌名称
			if (brandIdStrs.size() > 0 && brandIdStrs != null) {
				designPlanBO.setBrandName(brandService.getBrandNamesByIds(brandIdStrs));
			} else {
				designPlanBO.setBrandName("");
			}
			// 设置平台名称
			if (!Strings.isNullOrEmpty(designPlanBO.getPlatformIds())) {
				String[] platformId = designPlanBO.getPlatformIds().split(",");
				StringBuilder sb = new StringBuilder();
				StringBuilder sbIds = new StringBuilder();
				for (String str : platformId) {
					if (!StringUtil.isEmpty(str) && platformMap.containsKey(Integer.valueOf(str))) {
						sbIds.append(str).append(",");
						sb.append(platformMap.get(Integer.valueOf(str))).append(",");
					}
				}
				designPlanBO.setPlatformNames(sb.toString().substring(0, sb.toString().length() - 1));
				designPlanBO.setPlatformIds(sbIds.toString().substring(0, sbIds.toString().length() - 1));
			}
			//            //设置空间类型id
			//
			// designPlanBO.setSpaceTypeId(spaceTypeMap.get(Long.valueOf(designPlanBO.getSpaceCommonId())));
			//            //设置方案交付记录
			//
			// designPlanBO.setDeliverStatus(isDeliveredMap.get(designPlanBO.getPlanId().intValue()));
			//            //设置方案分配情况(2b,2c)
			//
			// designPlanBO.setDistributionStatus(platform2bMap.get(designPlanBO.getPlanId().intValue()).getAtt1() + "," +
			//                    platform2cMap.get(designPlanBO.getPlanId().intValue()).getAtt1());
			log.info(
					"##############设置方案id:{}相关信息表结束###########################", designPlanBO.getPlanId());
		}
		log.info("##############关联方案相关信息表{}结束###########################");
	}

	@Override
	public PageInfo<DesignPlanBO> listOnekeyShareDesignPlan(ShareDesignPlanQuery query) {
		// 一键方案
		query.setDesignPlanType(DEFAULT_ONEKEY);

		List<DesignPlanBO> designPlanBOS = setSharePlanParam(query);

		// 查询要收费的方案
		Set<Integer> planIds =
				designPlanBOS
						.stream()
						.filter(plan -> Objects.equals(1, plan.getSalePriceChargeType()))
						.collect(Collectors.toList())
						.stream()
						.map(DesignPlanBO::getPlanId)
						.map(id -> id.intValue())
						.collect(toSet());

		if (planIds != null && !planIds.isEmpty()) {
			Set<Integer> designPlanIds =
					companyDesignPlanIncomeMapper.isExitsUserBuySaleDesignPlan(planIds, query.getUserId(), 1);

			for (DesignPlanBO designPlanBO : designPlanBOS) {
				// 检验用户是否已经购买了该方案
				vaildUserHaveBuyDesignPlan(designPlanBO, designPlanIds);
			}
		}
		//      designPlanBOS = setSpaceType(designPlanBOS, new DesignPlanRecommendedQuery());

		return new PageInfo<>(designPlanBOS);
	}

	@Override
	public PageInfo<DesignPlanBO> listCommonShareDesignPlan(ShareDesignPlanQuery query) {
		// 普通方案
		query.setDesignPlanType(DEFAULT_COMMON);

		List<DesignPlanBO> designPlanBOS = setSharePlanParam(query);

		//      designPlanBOS = setSpaceType(designPlanBOS, new DesignPlanRecommendedQuery());

		return new PageInfo<>(designPlanBOS);
	}

	/**
	 * 设置分享方案参数
	 *
	 * @param query
	 * @return
	 */
	private List<DesignPlanBO> setSharePlanParam(ShareDesignPlanQuery query) {
		List<Integer> companyIds = listPeerCompanyIds(query.getCompanyId());
		List<Integer> filterIds = null;
		if (companyIds.contains(query.getCompanyId())) {
			filterIds =
					companyIds
							.stream()
							.filter(
									id -> {
										return id.intValue() != query.getCompanyId().intValue();
									})
							.collect(Collectors.toList());
		}
		query.setPeerCompanyIds(filterIds);
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<DesignPlanBO> designPlanBOS = designPlanRecommendedMapper.listShareDesignPlan(query);

    /*
    1.查询相关联表的信息map
    2.根据方案相关信息进行匹配
     */
		log.info("##############设置分享方案相关信息表{}开始###########################");
		// 方案关联图片id集合
		List<Integer> picIds = designPlanBOS.stream().map(DesignPlanBO::getPicId).collect(toList());
		// 空间类型ID
		List<String> spaceCommonIds =
				designPlanBOS
						.stream()
						.filter(s -> StringUtils.isNotBlank(s.getSpaceCommonId()))
						.map(it -> it.getSpaceCommonId())
						.distinct()
						.collect(toList());
		Map<Long, Integer> spaceFunctionMap = spaceCommonService.idAndSpaceTypeMap(spaceCommonIds);
		// 查询空间类型
		Map<Integer, String> houseType = dictionaryService.valueAndName2Map("houseType");
		List<Long> styleIds =
				designPlanBOS
						.stream()
						.filter(s -> s.getDesignStyleId() != null)
						.map(it -> it.getDesignStyleId().longValue())
						.collect(toList());
		// 查询图片
		Map<Integer, String> picPathMap = resRenderPicService.idAndPathMap(picIds);
		// 查询风格
		List<BaseProductStyle> baseProductStyles =
				baseProductStyleService.getBaseProductStyleByIds(styleIds);
		// 查询方案品牌表brand_id
		List<Long> brandIdList = designPlanBOS.stream().map(DesignPlanBO::getPlanId).collect(toList());
		Map<Integer, String> brandIdMap = designPlanBrandService.idAndSpaceTypeMap(brandIdList);
		// 查询公司名称
		List<Integer> companyIdList =
				designPlanBOS.stream().map(DesignPlanBO::getCompanyId).collect(toList());
		Map<Long, String> companyMap = companyService.idAndNameMap(companyIdList);

		for (DesignPlanBO designPlanBO : designPlanBOS) {
			// 设置方案品牌id
			designPlanBO.setBrandId(brandIdMap.get(designPlanBO.getPlanId().intValue()));
			// 查询方案品牌id对应的名称
			List<String> brandIdStrs =
					Splitter.on(COMMA)
							.omitEmptyStrings()
							.trimResults()
							.splitToList(Strings.nullToEmpty(designPlanBO.getBrandId()));
			// 设置对应品牌名称
			if (brandIdStrs.size() > 0 && brandIdStrs != null) {
				designPlanBO.setBrandName(brandService.getBrandNamesByIds(brandIdStrs));
			} else {
				designPlanBO.setBrandName("");
			}
			// 设置空间名称
			if (StringUtils.isNoneBlank(designPlanBO.getSpaceCommonId())) {
				if (spaceFunctionMap.get(Long.valueOf(designPlanBO.getSpaceCommonId())) != null) {
					designPlanBO.setSpaceTypeName(
							houseType.get(spaceFunctionMap.get(Long.valueOf(designPlanBO.getSpaceCommonId()))));
					designPlanBO.setSpaceTypeId(
							spaceFunctionMap.get(Long.valueOf(designPlanBO.getSpaceCommonId())));
				} else {
					designPlanBO.setSpaceTypeId(null);
					designPlanBO.setSpaceTypeName("");
				}
			} else {
				designPlanBO.setSpaceTypeId(null);
				designPlanBO.setSpaceTypeName("");
			}
			// 设置公司名称
			designPlanBO.setCompanyName(companyMap.get(designPlanBO.getCompanyId().longValue()));

			// 把风格放进去
			for (BaseProductStyle baseProductStyle : baseProductStyles) {
				if (isNotEmpty(designPlanBO.getDesignStyleId())
						&& designPlanBO.getDesignStyleId().equals(baseProductStyle.getId().intValue())) {
					designPlanBO.setDesignStyleName(baseProductStyle.getName());
				}
			}
			designPlanBO.setPicPath(picPathMap.get(designPlanBO.getPicId()));

			// 检验方案是否是当前用户所在企业
			checkCompanyDesignPlan(designPlanBO, query.getCompanyId(), query.getUserId());
		}
		log.info("##############设置分享方案相关信息表{}结束###########################");
		return designPlanBOS;
	}

	private void vaildUserHaveBuyDesignPlan(DesignPlanBO designPlanBO, Set<Integer> designPlanIds) {
		if (designPlanIds.contains(designPlanBO.getPlanId().intValue())) {
			designPlanBO.setSalePriceChargeType(0);
		}
	}

	private void checkCompanyDesignPlan(DesignPlanBO designPlanBO, Integer companyId, Long userId) {
		if (designPlanBO.getCompanyId().intValue() == companyId.intValue()) {
			designPlanBO.setWhetherDisplay(0);
			if (validRoles(userId)) {
				designPlanBO.setIsSetPlanPrice(1);
			}
		} else {
			designPlanBO.setWhetherDisplay(1);
			designPlanBO.setIsSetPlanPrice(0);
		}
	}

	/**
	 * 检验用户权限
	 *
	 * @return
	 */
	private boolean validRoles(Long userId) {
		SysRole sysRole = sysRoleService.getRoleByCode(PLAN_ROLE_CODE);
		Set<SysUserRole> sysUserRoles = sysRoleService.getUserRolesByUserId(userId);
		Set<Long> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(toSet());
		return roleIds.contains(sysRole.getId());
	}

	@Override
	public PageInfo<DesignPlanBO> listOnlineDesignPlan(DesignPlanRecommendedQuery query) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<DesignPlanBO> designPlanBOS =
				designPlanRecommendedMapper.listOnlineOneKeyDesignPlan(query);

		//        designPlanBOS = setSpaceType(designPlanBOS, query);

		//        List<Integer> planIds = designPlanBOS.stream().map(it ->
		// it.getPlanId().intValue()).collect(toList());
		//        //查询分配2c端记录
		//        Map<Integer, DesignPlan2cPlatform> platform2cMap =
		// designPlan2cPlatformService.idAnd2cMap(planIds);
		//        //设置线上信息
		//        Iterator<DesignPlanBO> iterator = designPlanBOS.iterator();
		//        while (iterator.hasNext()) {
		//            DesignPlanBO plan = iterator.next();
		//            plan.setPutawayStatusId(platform2cMap.get(plan.getPlanId().intValue()).getAtt2());
		//            plan.setPlatformIds(platform2cMap.get(plan.getPlanId().intValue()).getAtt3());
		//
		// plan.setCompleteDate(platform2cMap.get(plan.getPlanId().intValue()).getGmtModified());
		//            if (!Strings.isNullOrEmpty(query.getShelfStatus())) {
		//                String platId = Arrays.asList(plan.getPlatformIds().split(",")).get(0);
		//                if(query.getShelfStatus().equals("NONE")) {
		//                    List<String> pushId = Arrays.asList(plan.getPutawayStatusId().split(","));
		//                    for(String str : pushId) {
		//                        if(str.equals("1")) {
		//                            iterator.remove();
		//                            break;
		//                        }
		//                    }
		//                }else if(!query.getShelfStatus().equals(platId)) {
		//                    iterator.remove();
		//                }
		//            }
		//        }
		mergeDesignPlan(designPlanBOS, query.getCompanyId());
		return new PageInfo<>(designPlanBOS);
	}

	@Override
	public PageInfo<DesignPlanBO> listChannelDesignPlan(DesignPlanRecommendedQuery query) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<DesignPlanBO> designPlanBOS =
				designPlanRecommendedMapper.listChannelOneKeyDesignPlan(query);

		//        designPlanBOS = setSpaceType(designPlanBOS, query);

		List<Integer> planIds =
				designPlanBOS.stream().map(it -> it.getPlanId().intValue()).collect(toList());
		//        //查询分配2b端记录
		//        Map<Integer, DesignPlan2bPlatform> platform2bMap =
		// designPlan2bPlatformService.idAnd2bMap(planIds);
		//        //设置渠道信息
		//        for (DesignPlanBO designPlanBO : designPlanBOS) {
		//
		// designPlanBO.setPutawayStatusId(platform2bMap.get(designPlanBO.getPlanId().intValue()).getAtt2());
		//
		// designPlanBO.setPlatformIds(platform2bMap.get(designPlanBO.getPlanId().intValue()).getAtt3());
		//
		// designPlanBO.setCompleteDate(platform2bMap.get(designPlanBO.getPlanId().intValue()).getGmtModified());
		//        }
		mergeDesignPlan(designPlanBOS, query.getCompanyId());
		return new PageInfo<>(designPlanBOS);
	}

	/**
	 * 关联空间类型space_common, sys_dictionary(houseType)
	 *
	 * @param designPlanBOS
	 * @param query
	 * @return
	 */
	private List<DesignPlanBO> setSpaceType(
			List<DesignPlanBO> designPlanBOS, DesignPlanRecommendedQuery query) {
		// 查询对应的空间类型和空间名称
		List<Integer> functionIds = new ArrayList<>();

		if (query.getSpaceTypeId() != null) {
			// 根据空间类型id查询数据字典
			Dictionary dictionary =
					dictionaryService.getByTypeAndValue(SYS_HOUSE_TYPE, query.getSpaceTypeId());
			functionIds.add(dictionary == null ? null : dictionary.getValue());
		} else {
			// 根据空间类型名称查询数据字典
			List<Dictionary> dictionarys =
					dictionaryService.getByTypeAndName(SYS_HOUSE_TYPE, query.getSpaceCommonName());
			dictionarys.forEach(dictionary -> functionIds.add(dictionary.getValue()));
		}

		// 设置空间类型查询条件
		Iterator<DesignPlanBO> iterator = designPlanBOS.iterator();
		while (iterator.hasNext()) {
			DesignPlanBO plan = iterator.next();
			SpaceCommon space = spaceCommonService.getById(Integer.valueOf(plan.getSpaceCommonId()));
			if (!functionIds.contains(space.getSpaceFunctionId())) {
				iterator.remove();
			}
		}
		return designPlanBOS;
	}

	/**
	 * Selective 获取集合数据
	 *
	 * @param query 对象
	 * @return list数据集合
	 */
	@Override
	public PageInfo<DesignPlanBO> listDesignPlan(DesignPlanRecommendedQuery query) {
		log.info("##############查询一键方案开始,参数{}###########################", query);
		PageHelper.startPage(query.getPage(), query.getLimit());
		// 根据查询条件查询方案列表
		long queryBeginStart = System.currentTimeMillis();
		List<DesignPlanBO> designPlanBOS = designPlanRecommendedMapper.selectListSelective(query);
		long queryBeginEnd = System.currentTimeMillis();
		log.info("selectListSelective方法查询耗时{}", (queryBeginEnd - queryBeginStart));
		//        //匹配对应的空间类型
		//        designPlanBOS = setSpaceType(designPlanBOS, query);
		// 合并方案其他数据
		long queryStart = System.currentTimeMillis();
		dealWithDesignPlan(designPlanBOS, query.getCompanyId());
		long queryEnd = System.currentTimeMillis();
		log.info("dealWithDesignPlan方法查询耗时{}", (queryEnd - queryStart));
		return new PageInfo<>(designPlanBOS);
	}

	public void copyBrandProductImage(Integer oldPlanId, Integer targetCompanyId, Integer newPlanId) {
		// 获取原报价信息
		List<PlanDecoratePrice> planDecoratePrices =
				planDecoratePriceMapper.selectByDesignPlanId(oldPlanId);
		// 复制报价信息
		for (PlanDecoratePrice planDecoratePrice : planDecoratePrices) {
			planDecoratePrice.setId(null);
			planDecoratePrice.setPlanRecommendId(newPlanId);
			planDecoratePriceMapper.insertSelective(planDecoratePrice);
		}
		// 设置一个品牌 -->设置方案分发/交付公司
		List<Integer> brandIds = new ArrayList<>();
		// 根据公司id查询该公司对应的品牌id
		List<Brand> brands = brandService.getBrandByCompanyId(targetCompanyId);
		for (Brand brand : brands) {
			brandIds.add(brand.getId().intValue());
		}
		if (brandIds.size() == 0) {
			brandIds.add(-1);
		}
		for (Integer id : brandIds) {
			DesignPlanBrand designPlanBrand = new DesignPlanBrand();
			designPlanBrand.setPlanId(newPlanId);
			designPlanBrand.setBrandId(id);
			designPlanBrand.setCompanyId(targetCompanyId);
			designPlanBrand.setIsDeleted(0);
			designPlanBrand.setGmtCreate(new Date());
			designPlanBrand.setGmtModified(new Date());
			designPlanBrandMapper.insertSelective(designPlanBrand);
		}
		// copy plan  product
		List<DesignPlanRecommendedProduct> products =
				designPlanRecommendedProductService.listByPlanId(oldPlanId);
		designPlanRecommendedProductService.batchAddDesignPlanProducts(products, newPlanId + "");
		// copy plan render pic
		List<ResRenderPic> renderPics = resRenderPicMapper.listByPlan(oldPlanId + "");
		// 处理视频、图片、多点
		processImageRoamVideo(renderPics, oldPlanId + "", newPlanId + "");
	}

	/**
	 * 商家后台使用共享方案提示信息优化(http://jira.3du-inc.net/browse/CMS-667)
	 * Added by songjianming@sanduspace.cn on 2018/12/21
	 *
	 * @param planPO
	 * @return
	 */
	@Override
	public Long copyDesignPlanToCompany2(CopyShareDesignPlanPO planPO) {
		// 复制好的方案ID
		Long newPlanId = null;

		for (Integer planId : planPO.getSourceDesignPlanIds()) {
			// 同步id到es
			StringBuilder sb = new StringBuilder();

			DesignPlanCopyLog designPlanCopyLog = new DesignPlanCopyLog();
			DesignPlanRecommended tempDesignPlan = designPlanRecommendedMapper.selectByPrimaryKey(planId.longValue());
			designPlanCopyLog.setSourceCompanyId(tempDesignPlan.getCompanyId());
			tempDesignPlan.setGmtCreate(new Date());
			tempDesignPlan.setId(null);
			tempDesignPlan.setPlanSource(PlanOrigin.SHARE.getCode());
			tempDesignPlan.setShelfStatus("");
			tempDesignPlan.setGmtModified(new Date());
			tempDesignPlan.setCompanyId(planPO.getTargetCompanyId());
			tempDesignPlan.setUseCount(0);
			// 是否改变过：0 -> 否；1 -> 是
			tempDesignPlan.setIsChanged(0);
			designPlanRecommendedMapper.insertSelective(tempDesignPlan);
			sb.append(tempDesignPlan.getId()).append(",");
			// 复制好的方案ID
			if (newPlanId == null) newPlanId = tempDesignPlan.getId();

			final Integer pKey = tempDesignPlan.getId().intValue();
			// 处理单个方案
			if (tempDesignPlan.getGroupPrimaryId() == 0) {
				log.info("##############分享方案{}开始###########################", planId);
				executor.execute(() -> {
					log.info(
							"##############线程处理开始,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
							planId,
							executor.getTaskCount(),
							executor.getActiveCount(),
							executor.getCompletedTaskCount());
					// 复制单个方案品牌，产品，图像，视频，报价信息
					copyBrandProductImage(planId, planPO.getTargetCompanyId(), tempDesignPlan.getId().intValue());
					log.info(
							"##############线程处理结束,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
							planId,
							executor.getTaskCount(),
							executor.getActiveCount(),
							executor.getCompletedTaskCount());
				});
			} else {
				// 处理组合方案
				DesignPlanRecommended modifyGroup = new DesignPlanRecommended();
				modifyGroup.setId(tempDesignPlan.getId());
				modifyGroup.setGroupPrimaryId(tempDesignPlan.getId().intValue());
				designPlanRecommendedMapper.updateByPrimaryKeySelective(modifyGroup);
				executor.execute(
						() -> {
							// 复制主方案品牌，产品，图像，视频
							copyBrandProductImage(planId, planPO.getTargetCompanyId(), tempDesignPlan.getId().intValue());

							List<DesignPlanRecommended> plans = designPlanRecommendedMapper.queryPrimaryPlan(planId);
							plans.forEach(
								plan -> {
									DesignPlanCopyLog designPlanCopyLogSon = new DesignPlanCopyLog();
									DesignPlanRecommended tempDesignPlanSon =
											designPlanRecommendedMapper.selectByPrimaryKey(
													plan.getId().longValue());
									designPlanCopyLogSon.setSourceCompanyId(
											tempDesignPlanSon.getCompanyId());
									tempDesignPlanSon.setGmtCreate(new Date());
									tempDesignPlanSon.setId(null);
									tempDesignPlanSon.setGmtModified(new Date());
									tempDesignPlanSon.setPlanSource(PlanOrigin.SHARE.getCode());
									tempDesignPlanSon.setShelfStatus("");
									tempDesignPlanSon.setCompanyId(
											planPO.getTargetCompanyId());
									tempDesignPlanSon.setGroupPrimaryId(pKey);
									tempDesignPlanSon.setUseCount(0);
									designPlanRecommendedMapper.insertSelective(tempDesignPlanSon);
									sb.append(tempDesignPlanSon.getId()).append(",");
									log.info(
											"##############分享方案{}开始###########################", plan.getId());
									log.info(
											"##############线程处理开始,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
											plan.getId(),
											executor.getTaskCount(),
											executor.getActiveCount(),
											executor.getCompletedTaskCount());
									// 复制子方案品牌，产品，图像，视频
									copyBrandProductImage(
											plan.getId().intValue(),
											planPO.getTargetCompanyId(),
											tempDesignPlanSon.getId().intValue());
									log.info(
											"##############线程处理结束,plan.getId():{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
											plan.getId(),
											executor.getTaskCount(),
											executor.getActiveCount(),
											executor.getCompletedTaskCount());
									designPlanCopyLogSon.setGmtCreate(new Date());
									designPlanCopyLogSon.setKind(CopyType.SHARE.getCode());
									designPlanCopyLogSon.setSourceId(plan.getId().intValue());
									designPlanCopyLogSon.setTargetId(tempDesignPlanSon.getId().intValue());
									designPlanCopyLogSon.setTargetCompanyId(
											planPO.getTargetCompanyId());
									designPlanCopyLogSon.setTargetKind(
											tempDesignPlanSon.getRecommendedType().byteValue());
									designPlanCopyLogMapper.insertSelective(designPlanCopyLogSon);
									log.info(
											"##############分享方案{}结束###########################", plan.getId());
								});
						});
			}
			// 同步到es
			List<Integer> planidList =
					Arrays.stream(sb.toString().split(COMMA))
							.map(s -> Integer.parseInt(s))
							.collect(Collectors.toList());
			List<Integer> sycPlanId =
					designPlanRecommendedService.selectMainSubPlanList(planidList);
			sycMessageDoSend(SyncMessage.ACTION_ADD, sycPlanId);

			designPlanCopyLog.setGmtCreate(new Date());
			designPlanCopyLog.setKind(CopyType.SHARE.getCode());
			designPlanCopyLog.setSourceId(planId);
			designPlanCopyLog.setTargetId(tempDesignPlan.getId().intValue());
			designPlanCopyLog.setTargetCompanyId(planPO.getTargetCompanyId());
			designPlanCopyLog.setTargetKind(tempDesignPlan.getRecommendedType().byteValue());
			designPlanCopyLogMapper.insertSelective(designPlanCopyLog);
			log.info("##############分享方案{}结束###########################", planId);
		}

		return newPlanId;
	}

	@Override
	public void copyDesignPlanToCompany(CopyShareDesignPlanPO copyShareDesignPlanPO) {

		copyShareDesignPlanPO
				.getSourceDesignPlanIds()
				.forEach(
						planId -> {
							// 同步id到es
							StringBuilder sb = new StringBuilder();

							DesignPlanCopyLog designPlanCopyLog = new DesignPlanCopyLog();
							DesignPlanRecommended tempDesignPlan =
									designPlanRecommendedMapper.selectByPrimaryKey(planId.longValue());
							designPlanCopyLog.setSourceCompanyId(tempDesignPlan.getCompanyId());
							tempDesignPlan.setGmtCreate(new Date());
							tempDesignPlan.setId(null);
							tempDesignPlan.setPlanSource(PlanOrigin.SHARE.getCode());
							tempDesignPlan.setShelfStatus("");
							tempDesignPlan.setGmtModified(new Date());
							tempDesignPlan.setCompanyId(copyShareDesignPlanPO.getTargetCompanyId());
							tempDesignPlan.setUseCount(0);
							designPlanRecommendedMapper.insertSelective(tempDesignPlan);
							sb.append(tempDesignPlan.getId()).append(",");
							final Integer pKey = tempDesignPlan.getId().intValue();
							// 处理单个方案
							if (tempDesignPlan.getGroupPrimaryId() == 0) {
								log.info("##############分享方案{}开始###########################", planId);
								executor.execute(
										() -> {
											log.info(
													"##############线程处理开始,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
													planId,
													executor.getTaskCount(),
													executor.getActiveCount(),
													executor.getCompletedTaskCount());
											// 复制单个方案品牌，产品，图像，视频，报价信息
											copyBrandProductImage(
													planId,
													copyShareDesignPlanPO.getTargetCompanyId(),
													tempDesignPlan.getId().intValue());
											log.info(
													"##############线程处理结束,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
													planId,
													executor.getTaskCount(),
													executor.getActiveCount(),
													executor.getCompletedTaskCount());
										});
							} else {
								// 处理组合方案
								DesignPlanRecommended modifyGroup = new DesignPlanRecommended();
								modifyGroup.setId(tempDesignPlan.getId());
								modifyGroup.setGroupPrimaryId(tempDesignPlan.getId().intValue());
								designPlanRecommendedMapper.updateByPrimaryKeySelective(modifyGroup);
								executor.execute(
										() -> {
											// 复制主方案品牌，产品，图像，视频
											copyBrandProductImage(
													planId,
													copyShareDesignPlanPO.getTargetCompanyId(),
													tempDesignPlan.getId().intValue());

											List<DesignPlanRecommended> plans =
													designPlanRecommendedMapper.queryPrimaryPlan(planId);
											plans.forEach(
													plan -> {
														DesignPlanCopyLog designPlanCopyLogSon = new DesignPlanCopyLog();
														DesignPlanRecommended tempDesignPlanSon =
																designPlanRecommendedMapper.selectByPrimaryKey(
																		plan.getId().longValue());
														designPlanCopyLogSon.setSourceCompanyId(
																tempDesignPlanSon.getCompanyId());
														tempDesignPlanSon.setGmtCreate(new Date());
														tempDesignPlanSon.setId(null);
														tempDesignPlanSon.setGmtModified(new Date());
														tempDesignPlanSon.setPlanSource(PlanOrigin.SHARE.getCode());
														tempDesignPlanSon.setShelfStatus("");
														tempDesignPlanSon.setCompanyId(
																copyShareDesignPlanPO.getTargetCompanyId());
														tempDesignPlanSon.setGroupPrimaryId(pKey);
														tempDesignPlanSon.setUseCount(0);
														designPlanRecommendedMapper.insertSelective(tempDesignPlanSon);
														sb.append(tempDesignPlanSon.getId()).append(",");
														log.info(
																"##############分享方案{}开始###########################", plan.getId());
														log.info(
																"##############线程处理开始,planId:{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
																plan.getId(),
																executor.getTaskCount(),
																executor.getActiveCount(),
																executor.getCompletedTaskCount());
														// 复制子方案品牌，产品，图像，视频
														copyBrandProductImage(
																plan.getId().intValue(),
																copyShareDesignPlanPO.getTargetCompanyId(),
																tempDesignPlanSon.getId().intValue());
														log.info(
																"##############线程处理结束,plan.getId():{},taskCount:{},activeCount:{},completedTaskCount{}###########################",
																plan.getId(),
																executor.getTaskCount(),
																executor.getActiveCount(),
																executor.getCompletedTaskCount());
														designPlanCopyLogSon.setGmtCreate(new Date());
														designPlanCopyLogSon.setKind(CopyType.SHARE.getCode());
														designPlanCopyLogSon.setSourceId(plan.getId().intValue());
														designPlanCopyLogSon.setTargetId(tempDesignPlanSon.getId().intValue());
														designPlanCopyLogSon.setTargetCompanyId(
																copyShareDesignPlanPO.getTargetCompanyId());
														designPlanCopyLogSon.setTargetKind(
																tempDesignPlanSon.getRecommendedType().byteValue());
														designPlanCopyLogMapper.insertSelective(designPlanCopyLogSon);
														log.info(
																"##############分享方案{}结束###########################", plan.getId());
													});
										});
							}
							// 同步到es
							List<Integer> planidList =
									Arrays.stream(sb.toString().split(COMMA))
											.map(s -> Integer.parseInt(s))
											.collect(Collectors.toList());
							List<Integer> sycPlanId =
									designPlanRecommendedService.selectMainSubPlanList(planidList);
							sycMessageDoSend(SyncMessage.ACTION_ADD, sycPlanId);

							designPlanCopyLog.setGmtCreate(new Date());
							designPlanCopyLog.setKind(CopyType.SHARE.getCode());
							designPlanCopyLog.setSourceId(planId);
							designPlanCopyLog.setTargetId(tempDesignPlan.getId().intValue());
							designPlanCopyLog.setTargetCompanyId(copyShareDesignPlanPO.getTargetCompanyId());
							designPlanCopyLog.setTargetKind(tempDesignPlan.getRecommendedType().byteValue());
							designPlanCopyLogMapper.insertSelective(designPlanCopyLog);
							log.info("##############分享方案{}结束###########################", planId);
						});
	}

	/**
	 * @param renderPics 图片信息
	 * @param oldPlanId  旧方案ID
	 * @param newPlanId  新方案ID
	 */
	private void processImageRoamVideo(
			List<ResRenderPic> renderPics, String oldPlanId, String newPlanId) {
		try {
			log.info(
					"##############processImageRoamVideo开始,renderPicsSize:{},oldPlanId:{},newPlanId:{}###########################",
					renderPics == null ? 0 : renderPics.size(),
					oldPlanId,
					newPlanId);
			Map<String, Integer> picMap = Maps.newConcurrentMap();
			Integer coverPicId = null;

			//存储要修改taskId的id
			List<Integer> modifyIds = new ArrayList<>();
			//渲染截图id
			Integer needsId = 0;

			for (ResRenderPic resRenderPic : renderPics) {
				log.info(
						"planId:{},picPath:{},id:{}",
						oldPlanId,
						resRenderPic.getPicPath(),
						resRenderPic.getId());
				// 图片拷贝
				Map<String, Object> resultMap =
						downloadImageVideo(
								resRenderPic.getPicSuffix(),
								resRenderPic.getFileKey(),
								resRenderPic.getPicPath(),
								"0");
				if (resultMap != null && resultMap.size() > 0) {
					resRenderPic.setPicName(
							resultMap.get("dbfileName") == null ? "" : resultMap.get("dbfileName").toString());
					resRenderPic.setPicPath(
							resultMap.get("dbfilePath") == null ? "" : resultMap.get("dbfilePath").toString());
				}
				//              Long oldPicId = resRenderPic.getId();
				resRenderPic.setId(null);
				resRenderPic.setPlanRecommendedId(Integer.parseInt(newPlanId));
				resRenderPic.setGmtCreate(new Date());
				resRenderPic.setGmtModified(new Date());
				resRenderPic.setCreateUserId(resRenderPic.getCreateUserId());
				String sysCode =
						Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6);
				resRenderPic.setSysCode(sysCode);
				resRenderPic.setPicCode(sysCode);
				if (picMap != null
						&& picMap.containsKey("screenShotId")
						&& RenderTypeCode.ROAM_720_LEVEL == resRenderPic.getRenderingType()) {
					resRenderPic.setSysTaskPicId((Integer) picMap.get("screenShotId"));
				}
				int resRenderPicId = resRenderPicService.addResRenderPic(resRenderPic);
				// 获取照片级原图Id
				if ("照片级原图".equals(resRenderPic.getPicType())) {
					picMap.put("originPhotoId", resRenderPicId);
				}
				// 设置照片级缩略图的原图ID = 照片级原图Id
				if ("照片级缩略图".equals(resRenderPic.getPicType())) {
					resRenderPic.setPid((Integer) picMap.get("originPhotoId"));
					coverPicId = resRenderPicId;
				}
				// 获取3DMax渲染原图Id
				if ("3DMax渲染原图".equals(resRenderPic.getPicType()) && resRenderPic.getRenderingType() == 4) {
					picMap.put("origin3DMaxId", resRenderPicId);
					modifyIds.add(resRenderPicId);
				}
				// 设置3DMax渲染图-缩略图的原图ID = 3DMax渲染图原图ID
				if ("3DMax渲染图-缩略图".equals(resRenderPic.getPicType())
						&& resRenderPic.getRenderingType() == 4) {
					resRenderPic.setPid((Integer) picMap.get("origin3DMaxId"));
					modifyIds.add(resRenderPicId);
				}
				// 获取渲染截图ID
				if ("渲染截图".equals(resRenderPic.getPicType())) {
					picMap.put("screenShotId", resRenderPicId);
					// 保存旧渲染截图ID
					//                 	picMap.put("oldScreenShotId", oldPicId.intValue());
				}
				// 设置多点3DMax渲染图-缩略图的原图ID =  渲染截图Id
				if ("3DMax渲染图-缩略图".equals(resRenderPic.getPicType())
						&& resRenderPic.getRenderingType() == 8) {
					resRenderPic.setPid((Integer) picMap.get("screenShotId"));
				}
				// 获取视频截屏图片ID - design.designPlanRecommended.render.video.cover
				if ("720渲染视频封面".equals(resRenderPic.getPicType())) {
					picMap.put("videoCoverId", resRenderPicId);
				}
				if (resRenderPic.getPid() != null || picMap.containsKey("screenShotId")) {
					ResRenderPic rrp = new ResRenderPic();
					rrp.setId(Long.parseLong(resRenderPicId + ""));
					rrp.setPid(resRenderPic.getPid());
					rrp.setSysTaskPicId(picMap.get("screenShotId"));
					resRenderPicService.updateResRenderPic(rrp);
				}

				if ("渲染截图".equals(resRenderPic.getPicType()) && resRenderPic.getRenderingType() == 4) {
					ResRenderPic rrp = new ResRenderPic();
					needsId = resRenderPicId;
					rrp.setId(Long.parseLong(resRenderPicId + ""));
					rrp.setSysTaskPicId(0);
					resRenderPicService.updateResRenderPic(rrp);
				}

				//             	 if( "渲染截图".equals(resRenderPic.getPicType())&&RenderTypeCode.ROAM_720_LEVEL
				// == resRenderPic.getRenderingType()){
				//    					sysTaskPicMap.put(resRenderPic.getSysTaskPicId().toString(), resRenderPicId);
				//    					ResRenderPic rrp = new ResRenderPic();
				//    					rrp.setId(Long.parseLong(resRenderPicId+""));
				//    					rrp.setSysTaskPicId(resRenderPicId);
				//    					resRenderPicService.updateResRenderPic(rrp);
				//    				}
				//    				if("720渲染视频封面".equals(resRenderPic.getPicType())) {
				//    					resVideoId = resRenderPicId;
				//    				}
			}

			//修改taskId
			for(Integer id : modifyIds) {
				ResRenderPic rrp = new ResRenderPic();
				rrp.setId(Long.parseLong(id + ""));
				rrp.setSysTaskPicId(needsId);
				resRenderPicService.updateResRenderPic(rrp);
			}

			// 保存720漫游记录
			List<ResRenderPic> pictureList =
					resRenderPicService.selectResRenderPicByParam(
							oldPlanId, "8", "design.designPlanRecommended.render.pic", "渲染截图");
			if (pictureList != null && pictureList.size() > 0) {
				for (ResRenderPic resRenderPic : pictureList) {
					DesignRenderRoam roamTemp =
							designRenderRoamService.selectByScreenShotId(
									Integer.parseInt(resRenderPic.getId() + ""));
					if (roamTemp != null) {
						DesignRenderRoam designRenderRoam = new DesignRenderRoam();
						designRenderRoam.setCreator(roamTemp.getCreator());
						designRenderRoam.setGmtCreate(new Date());
						designRenderRoam.setModifier(roamTemp.getModifier());
						designRenderRoam.setGmtModified(new Date());
						designRenderRoam.setIsDeleted(0);
						designRenderRoam.setRoamConfig(roamTemp.getRoamConfig());
						designRenderRoam.setScreenShotId(picMap.get("screenShotId"));
						designRenderRoam.setUuid(Utils.getUUID());
						designRenderRoamService.add(designRenderRoam);
					}
				}
			}
			// 保存渲染视频,视频只取一个
			ResRenderVideo resRenderVideo = new ResRenderVideo();
			resRenderVideo.setBusinessId(Integer.parseInt(oldPlanId));
			List<ResRenderVideo> videoList = resRenderVideoService.getList(resRenderVideo);
			for (ResRenderVideo video : videoList) {
				log.info("planId:{},videoPath:{},id:{}", oldPlanId, video.getVideoPath(), video.getId());
				// 视频拷贝
				Map<String, Object> resultMap =
						downloadImageVideo(
								video.getVideoSuffix(), video.getFileKey(), video.getVideoPath(), "1");
				if (resultMap != null && resultMap.size() > 0) {
					video.setVideoName(
							resultMap.get("dbfileName") == null ? "" : resultMap.get("dbfileName").toString());
					video.setVideoPath(
							resultMap.get("dbfilePath") == null ? "" : resultMap.get("dbfilePath").toString());
				}
				video.setCreator(video.getCreator());
				video.setGmtCreate(new Date());
				video.setModifier(video.getModifier());
				video.setGmtModified(new Date());
				video.setBusinessId(Integer.parseInt(newPlanId));
				// 视频封面id
				video.setSysTaskPicId(picMap.get("videoCoverId"));
				video.setTaskCreateTime(new Date());
				video.setVideoType("720渲染漫游视频");
				String sysCode =
						Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6);
				resRenderVideo.setVideoCode(sysCode);
				resRenderVideo.setSysCode(sysCode);
				resRenderVideoService.add(video);
				break;
			}
			// 修改封面和备注信息
			DesignPlanRecommended plan = new DesignPlanRecommended();
			plan.setId(Long.parseLong(newPlanId));
			plan.setRemark("copy");
			plan.setCoverPicId(coverPicId);
			designPlanRecommendedMapper.updateByPrimaryKeySelective(plan);
			log.info(
					"##############processImageRoamVideo结束,renderPicsSize:{},oldPlanId:{},newPlanId:{}###########################",
					renderPics == null ? 0 : renderPics.size(),
					oldPlanId,
					newPlanId);
		} catch (Exception e) {
			log.error("processImageRoamVideo出错,错误信息{}", e);
		}
	}

	private List<Integer> listPeerCompanyIds(Integer companyId) {
		return companyService.listPeerCompanys(companyId);
	}

	@Override
	public void initDesignPlanStatus() {
		// 普通方案分配上架
		designPlanRecommendedMapper
				.listNotShelfDesignPlan(DEFAULT_COMMON)
				.forEach(
						it -> {
							DesignPlanBO result =
									designPlanRecommendedService.getBaseInfo(it.getPlanId().longValue());
							if (result.getDesignStyleId() != null) {
								// 分配
								DesignPlanAllotPO designPlanAllotPO = new DesignPlanAllotPO();
								designPlanAllotPO.setPlanId(it.getPlanId());
								designPlanAllotPO.setTargetPlatform(Collections.singletonList("2b"));
								designPlanRecommendedService.allot(designPlanAllotPO);
								// 上架
								DesignPlanChannelShelfPO shelfPO = new DesignPlanChannelShelfPO();
								shelfPO.setPlanId(it.getPlanId().intValue());
								shelfPO.setShelfStatus(Collections.singletonList(ShelfStatus.TEMPLATE.getCode()));
								this.onekeyChannelShelf(shelfPO);
								sycMessageDoSend(
										SyncMessage.ACTION_UPDATE, Arrays.asList(it.getPlanId().intValue()));
							}
						});
		// 一键方案分配上架
		// [3,7]
		List<Integer> platforms2cs =
				platformService.getPlatformIdsByBussinessTypes(Collections.singletonList("2c"));
		designPlanRecommendedMapper
				.listNotShelfDesignPlan(DEFAULT_ONEKEY)
				.forEach(
						(DesignPlanBO it) -> {
							DesignPlanBO result =
									designPlanRecommendedService.getBaseInfo(it.getPlanId().longValue());
							if (result.getDesignStyleId() != null) {
								// 分配方案
								DesignPlanAllotPO designPlanAllotPO = new DesignPlanAllotPO();
								designPlanAllotPO.setPlanId(it.getPlanId());
								designPlanAllotPO.setTargetPlatform(Arrays.asList("2b", "2c"));
								designPlanRecommendedService.allot(designPlanAllotPO);
								// 上架方案 2b
								DesignPlanChannelShelfPO shelfPO = new DesignPlanChannelShelfPO();
								shelfPO.setPlanId(it.getPlanId().intValue());
								//                    if (PlanOrigin.SHARE.getCode().equals(it.getOrigin())) {
								//
								// shelfPO.setShelfStatus(Collections.singletonList(ShelfStatus.OPEN.getCode()));
								//                    } else {
								shelfPO.setShelfStatus(
										Arrays.asList(ShelfStatus.ONEKEY.getCode(), ShelfStatus.OPEN.getCode()));
								//                    }
								shelfPO.setFlag(true);
								this.onekeyChannelShelf(shelfPO);
								// 上架方案 2c
								DesignPlanPushAwayPO designPlanPushAwayPO = new DesignPlanPushAwayPO();
								designPlanPushAwayPO.setPlatformIds(platforms2cs);
								designPlanPushAwayPO.setPlanId(it.getPlanId().intValue());
								log.info(
										"ids is --------------> {} ,  online item  ----> {} ",
										platforms2cs,
										designPlanPushAwayPO);
								designPlanRecommendedService.onlinePushaway(designPlanPushAwayPO);
								sycMessageDoSend(
										SyncMessage.ACTION_UPDATE, Arrays.asList(it.getPlanId().intValue()));
							}
						});
	}

	@Override
	public void synDesignPlanToPlatform() {
		initDesignPlanStatus();
	}

	@Override
	public List<CompanyWithDeliveredBO> listCompanyWithDelivered(Integer companyId, Integer planId) {
		return designPlanRecommendedMapper.listCompanyWithDelivered(companyId, planId);
	}

	@Override
	public int removePlanBrand(Integer planId) {
		if (designPlanRecommendedMapper.getPrimaryPlan(planId) != null) {
			List<DesignPlanRecommended> plans = designPlanRecommendedMapper.queryPrimaryPlan(planId);
			plans.forEach(plan -> designPlanBrandMapper.removePlanBrand(plan.getId().intValue()));
		}
		return designPlanBrandMapper.removePlanBrand(planId);
	}

	@Override
	public int removeLog(Integer targetId, Integer planType) {
		if (designPlanRecommendedMapper.getPrimaryPlan(targetId) != null) {
			List<DesignPlanRecommended> plans = designPlanRecommendedMapper.queryPrimaryPlan(targetId);
			plans.forEach(plan -> designPlanCopyLogMapper.removeLog(plan.getId().intValue(), planType));
		}
		return designPlanCopyLogMapper.removeLog(targetId, planType);
	}

	@Override
	public void setDesignPlanCompanyId() {
		designPlanRecommendedMapper.setDesignPlanCompanyId();
	}

	@Override
	public void copySharePlan2Company() {
		// 获取一个没有copy过的公司.
		Company company = designPlanRecommendedService.getNotUseSharePlanCompanyQueue();
		// 获取全部需要拷贝的方案
		if (isNotEmpty(company)) {
			List<DesignPlanRecommended> needCopyPlans =
					designPlanRecommendedService.listAllCopy2CompanyPlan();
			CopyShareDesignPlanPO copyShareDesignPlanPO = new CopyShareDesignPlanPO();
			copyShareDesignPlanPO.setTargetCompanyId(company.getId().intValue());
			copyShareDesignPlanPO.setSourceDesignPlanIds(
					needCopyPlans.stream().map(it -> it.getId().intValue()).collect(toList()));
			this.copyDesignPlanToCompany(copyShareDesignPlanPO);
		}
	}

	/**
	 * 方案上架逻辑:
	 * 1.当platformIds传入为OPEN(公开方案),ONEKEY(一键方案),TEMPLATE(样板方案)时,需要同步更新design_plan_recommended表中
	 * shelf_status为OPEN,ONEKEY和TEMPLATE,并插入design_plan_2b_platform表中记录,putaway_state和allot_state写入1
	 * 2.当platformIds不为上述三种类型,插入design_plan_2c_platform表中记录,putaway_state和allot_state写入1
	 *
	 * <p>方案下架逻辑:
	 * 1.当platformIds传入为OPEN(公开方案),ONEKEY(一键方案),TEMPLATE(样板方案)时,需要同步更新design_plan_recommended表中
	 * shelf_status相应去除OPEN,ONEKEY,TEMPLATE,并删除design_plan_2b_platform表中记录
	 * 2.当platformIds不为上述三种类型时,直接删除design_plan_2c_platform中的记录即可
	 * 3.若platformIds为空,则清空design_plan_recommended中的shelf_status字段,并删除design_plan_2b_platform和
	 * design_plan_2c_platform中的记录
	 *
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> putlibraryDesignPlan(
			String planId, String platformIds, String designPlanType) {
		Map<String, Object> resultMapAll = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> upPlatformId = Arrays.asList(platformIds.split(Punctuation.COMMA));
		if (upPlatformId.contains("") && upPlatformId.size() == 1) {
			upPlatformId = Collections.emptyList();
		}
		// 全部下架
		if (upPlatformId.isEmpty()) {
			resultMap =
					designPlanRecommendedService.upAndDownPlan(
							planId,
							Collections.emptyList(),
							Collections.emptyList(),
							Platform.PUT_STATUS_DOWN,
							designPlanType);
			resultMapAll.putAll(resultMap);
		} else {
			// 去重
			upPlatformId =
					upPlatformId
							.stream()
							.distinct()
							.filter(StringUtils::isNoneBlank)
							.collect(Collectors.toList());
			// 构造B端的数据
			List<String> plat2bPlats = new ArrayList<String>();
			// 一键方案
			if (Integer.parseInt(designPlanType) == 2) {
				plat2bPlats = Arrays.asList(ShelfStatus.ONEKEY.getCode(), ShelfStatus.OPEN.getCode(), "1");
			} else {
				plat2bPlats = Arrays.asList(ShelfStatus.TEMPLATE.getCode());
			}
			// 获取所有C端的数据
			List<String> plat2cPlats =
					platformService
							.getPlatformIdsByBussinessTypes(
									Collections.singletonList(PlatformType.TO_C.getCode()))
							.stream()
							.map(key -> String.valueOf(key))
							.collect(Collectors.toList());
			// 取需要上架到B端的平台
			List<String> tobPlatformIds = new ArrayList<String>();
			// 取需要上架到C端的平台
			List<String> tocPlatformIds = new ArrayList<String>();
			// 过滤本次需要上架的2b和2c平台
			Map<String, Object> platFormMap =
					getUpDownPlatForms(plat2bPlats, plat2cPlats, upPlatformId, Platform.PUT_STATUS_UP);
			tobPlatformIds =
					platFormMap.get(PlatformType.TO_B.getCode()) == null
							? Collections.emptyList()
							: (List<String>) platFormMap.get(PlatformType.TO_B.getCode());
			tocPlatformIds =
					platFormMap.get(PlatformType.TO_C.getCode()) == null
							? Collections.emptyList()
							: (List<String>) platFormMap.get(PlatformType.TO_C.getCode());
			// 处理上架
			if (!tocPlatformIds.isEmpty() || !tobPlatformIds.isEmpty()) {
				resultMap =
						designPlanRecommendedService.upAndDownPlan(
								planId, tocPlatformIds, tobPlatformIds, Platform.PUT_STATUS_UP, designPlanType);
				resultMapAll.putAll(resultMap);
			}
			tobPlatformIds.clear();
			tocPlatformIds.clear();
			platFormMap.clear();
			// 过滤本次需要下架的2b和2c平台
			platFormMap =
					getUpDownPlatForms(plat2bPlats, plat2cPlats, upPlatformId, Platform.PUT_STATUS_DOWN);
			tobPlatformIds =
					platFormMap.get(PlatformType.TO_B.getCode()) == null
							? Collections.emptyList()
							: (List<String>) platFormMap.get(PlatformType.TO_B.getCode());
			tocPlatformIds =
					platFormMap.get(PlatformType.TO_C.getCode()) == null
							? Collections.emptyList()
							: (List<String>) platFormMap.get(PlatformType.TO_C.getCode());
			// 处理下架
			if (!tocPlatformIds.isEmpty() || !tobPlatformIds.isEmpty()) {
				resultMap =
						designPlanRecommendedService.upAndDownPlan(
								planId, tocPlatformIds, tobPlatformIds, Platform.PUT_STATUS_DOWN, designPlanType);
				resultMapAll.putAll(resultMap);
			}
		}
		return resultMapAll;
	}

	/**
	 * @param tocPlatForms B端平台
	 * @param tocPlatForms c端平台
	 * @param upPlatformId 页面传入的本次需要上下架的平台
	 * @return
	 */
	private Map<String, Object> getUpDownPlatForms(
			List<String> tobPlatForms,
			List<String> tocPlatForms,
			List<String> upPlatformId,
			Integer operateType) {
		HashMap<String, Object> platFormMap = new HashMap<String, Object>();
		List<String> tobPlatformIds = new ArrayList<String>(); // 需要上下架B端的平台
		List<String> tocPlatformIds = new ArrayList<String>(); // 需要上下架C端的平台
		if (Platform.PUT_STATUS_UP == operateType) {
			// 取需要上架到B端的平台,取交集
			tobPlatformIds.addAll(upPlatformId);
			tobPlatformIds.retainAll(tobPlatForms);

			// 取需要上架到c端的平台,取交集
			tocPlatformIds.addAll(upPlatformId);
			tocPlatformIds.retainAll(tocPlatForms);
		}

		if (Platform.PUT_STATUS_DOWN == operateType) {
			tocPlatForms.addAll(tobPlatForms);
			List<String> allPutPlatForms = new ArrayList<String>();
			allPutPlatForms.addAll(tocPlatForms);
			// 取当前需要下架的平台ID
			allPutPlatForms.removeAll(upPlatformId);
			// 从C端移除B端平台
			for (Iterator<String> it = tocPlatForms.iterator(); it.hasNext(); ) {
				String platFormId = (String) it.next();
				if (tobPlatForms.contains(platFormId)) {
					it.remove();
				}
			}
			// 取消分配并下架
			if (!allPutPlatForms.isEmpty()) {
				tocPlatformIds.clear();
				tocPlatForms.forEach(
						k -> {
							if (allPutPlatForms.contains(String.valueOf(k))) {
								tocPlatformIds.add(String.valueOf(k));
							}
						});
				tobPlatformIds.clear();
				tobPlatForms.forEach(
						k -> {
							if (allPutPlatForms.contains(String.valueOf(k))) {
								tobPlatformIds.add(String.valueOf(k));
							}
						});
			}
		}
		platFormMap.put(PlatformType.TO_C.getCode(), tocPlatformIds);
		platFormMap.put(PlatformType.TO_B.getCode(), tobPlatformIds);
		return platFormMap;
	}


	/**
	 * 批量公开或者取消公开
	 */
	@Override
	public boolean batchChangePlanSecrecy(List<Integer> planIds, Integer secrecyFlag) {
		List<Integer> updateIds = new ArrayList<Integer>();
		planIds.forEach(
				planId -> {
					DesignPlanRecommended designPlanRecommended =
							designPlanRecommendedMapper.selectByPrimaryKey(Long.parseLong(planId + ""));
					if (designPlanRecommended != null) {
						updateIds.add(planId);
					}
					if (designPlanRecommended.getGroupPrimaryId() > 0) {
						List<DesignPlanRecommended> plans = new ArrayList<DesignPlanRecommended>();
						plans = designPlanRecommendedMapper.queryPrimaryPlan(planId);
						if (plans != null) {
							updateIds.addAll(
									plans.stream().map(plan -> plan.getId().intValue()).collect(Collectors.toList()));
						}
					}
				});
		if (Optional.ofNullable(updateIds).isPresent()) {
			designPlanRecommendedMapper.batchUpdatePlanSecrecy(updateIds, secrecyFlag);
		}
		return true;
	}

	@Override
	public void configDesignPlan(DesignPlanConfig config) {
		DesignPlanRecommended primaryPlan = designPlanRecommendedMapper.getDesignById(config.getId());
		if (Objects.isNull(primaryPlan)) {
			throw new RuntimeException("获取方案详情异常");
		}
		Long fileId = resFileService.updateFile(primaryPlan.getDescFileId(), config.getContent());
		if (primaryPlan.getDescFileId() == null || primaryPlan.getDescFileId() == 0) {
			primaryPlan.setDescFileId(fileId.intValue());
			designPlanRecommendedMapper.updateByPrimaryKeySelective(primaryPlan);
		}
	}

	@Override
	public String showDesignPlanConfig(@NotNull Integer planId) {
		DesignPlanRecommended primaryPlan = designPlanRecommendedMapper.getDesignById(planId);
		if (Objects.isNull(primaryPlan)) {
			log.warn("获取方案失败,planId:{}", planId);
			return "";
		}
		return resFileService.readFile(primaryPlan.getDescFileId());
	}

	private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
		List<Map> content =
				ids.stream()
						.map(
								item -> {
									HashMap<String, Integer> tmp = new HashMap<>(1);
									tmp.put("id", item);
									tmp.put("planTableType", SyncMessage.PLAN_TYPE_RECOMMENDED);
									return tmp;
								})
						.collect(Collectors.toList());
		SyncMessage message = new SyncMessage();
		message.setAction(messageAction);
		message.setMessageId("S-" + System.currentTimeMillis());
		message.setModule(SyncMessage.MODULE_SOLUTION_RECOMMEND);
		message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
		message.setObject(content);
		queueService.send(message);
	}

	@Override
	public int editPlanPrice(Long planId, Double planPrice, Integer chargeType) {
		DesignPlanRecommended d = new DesignPlanRecommended();
		d.setId(planId);
		d.setPlanPrice(planPrice);
		d.setIsDeleted(0);
		d.setGmtModified(new Date());
		d.setChargeType(chargeType);
		return designPlanRecommendedMapper.updateByPrimaryKeySelective(d);
	}

	@Override
	public PageInfo<CompanyDesignPlanIncomeBO> listCompanyIncome(CompanyIncomeQuery query) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		if (StringUtils.isNotEmpty(query.getStartTime())) {
			query.setStartTime(query.getStartTime() + " 00:00:00");
		}
		if (StringUtils.isNotEmpty(query.getEndTime())) {
			query.setEndTime(query.getEndTime() + " 23:59:59");
		}
		List<CompanyDesignPlanIncomeBO> lists = companyDesignPlanIncomeMapper.listCompanyIncome(query);
		if (lists != null && !lists.isEmpty()) {
			lists.forEach(
					item -> {
						if (item.getUseType() != null) {
							setDesignPlanUseType(item);
						}
						// 截取收益金额后两位小数
						double value =
								new BigDecimal(item.getWithdrawAmount())
										.setScale(2, BigDecimal.ROUND_DOWN)
										.doubleValue();
						item.setWithdrawAmount(value);
					});
			// 获取企业方案受益统计
			return new PageInfo<>(lists);
		}
		return new PageInfo<>(Collections.EMPTY_LIST);
	}

	private void setDesignPlanUseType(CompanyDesignPlanIncomeBO item) {
		switch (item.getUseType()) {
			case 0:
				item.setDesignPlanUseType("装进我家");
				break;
			case 1:
				item.setDesignPlanUseType("产品替换");
				break;
			case 2:
				item.setDesignPlanUseType("方案改造");
				break;
			case 3:
				item.setDesignPlanUseType("硬装替换");
				break;
			case 4:
				item.setDesignPlanUseType("全屋替换");
				break;
			default:
				item.setDesignPlanUseType("装进我家");
				break;
		}
	}

	@Override
	public CompanyDesignPlanIncomeAggregatedBO getCompanyDesignPlanIncomeAggregated(Long companyId) {
		CompanyDesignPlanIncomeAggregatedBO cdpao =
				companyDesignPlanIncomeMapper.selectCompanyIncomeAggregated(companyId);
		if (cdpao != null) {
			double value =
					new BigDecimal(cdpao.getWaitWithdrawAmount().toString())
							.setScale(2, BigDecimal.ROUND_DOWN)
							.doubleValue();
			cdpao.setWaitWithdrawAmount(value);
			double todayIncome =
					new BigDecimal(cdpao.getTodayIncome().toString())
							.setScale(2, BigDecimal.ROUND_DOWN)
							.doubleValue();
			cdpao.setTodayIncome(todayIncome);
			double totalIncome =
					new BigDecimal(cdpao.getTotalIncome().toString())
							.setScale(2, BigDecimal.ROUND_DOWN)
							.doubleValue();
			cdpao.setTotalIncome(totalIncome);
			// 检验今天企业是否有收益
			boolean isNowDay = checkLastUpdatedDate(cdpao.getLastUpdatedDate());
			if (!isNowDay) {
				cdpao.setTodayIncome(0D);
			}
		}
		return cdpao;
	}

	@Override
	public int editSalePrice(Long planId, Double salePrice, Integer salePriceChargeType) {
		DesignPlanRecommended d = new DesignPlanRecommended();
		d.setId(planId);
		d.setSalePrice(salePrice);
		d.setGmtModified(new Date());
		d.setSalePriceChargeType(salePriceChargeType);
		return designPlanRecommendedMapper.updateByPrimaryKeySelective(d);
	}

	@Override
	public Double queryCompanyTotalIncome(Integer companyId) {
		CompanyDesignPlanIncomeAggregatedBO cdpao =
				companyDesignPlanIncomeMapper.selectCompanyIncomeAggregated(new Long(companyId));
		if (cdpao != null) {
			return cdpao.getTotalIncome();
		}
		return null;
	}

	@Override
	public Map<String, String> getRenderPicResult(Integer planId) {
		Map<String, String> resultMap = new HashMap<>();

		String picPath = designPlanRecommendedMapper.getRenderPic(planId);
		String vr720Single = designPlanRecommendedMapper.getVr720Single(planId);
		Integer renderPicId = designPlanRecommendedMapper.getVr720Roam(planId);
		String vr720Roam = designPlanRecommendedMapper.getUUID(renderPicId);
		Integer videoPicId = designPlanRecommendedMapper.getVideoPicPath(planId);
		String videoPicPath = designPlanRecommendedMapper.getPath(videoPicId);

		resultMap.put("picPath", downloadUrl + picPath);
		resultMap.put("videoPath", downloadUrl + videoPicPath);
		resultMap.put("vr720Single", RENDER_URL + "/pages/vr720/vr720Single.htm?code=" + vr720Single);
		resultMap.put("vr720Roam", RENDER_URL + "/pages/vr720/vr720Roam.htm?code=" + vr720Roam);
		return resultMap;
	}

	@Override
	public void transResourceForPathError() {

		ZoneId zone = ZoneId.systemDefault();
		int limit = 100;
		try {
			// 处理模型资源
			ForkJoinPool modelPool = new ForkJoinPool(20);
			Future<?>  futureM = executor.submit(() -> {
				log.info("###################start trans model!!!###################");
				try {
					while (transModelResource(zone, modelPool, limit) > 0) {
						log.info("trans model resource ing...");
						break;
					}
				} catch (Exception e) {
					log.error("trans resource for model error:{}", e);
				}
				log.info("###################end trans model!!!###################");
			});


			// 处理方案资源
			ForkJoinPool planPool = new ForkJoinPool(20);
			Future<?> futureP = executor.submit(() -> {
				log.info("###################start trans plan!!!###################");
				try {
					while (transPlanResource(zone, planPool, limit) > 0) {
						log.info("trans plan resource ing...");
						break;
					}
				} catch (Exception e) {
					log.error("trans resource for plan error:{}", e);
				}
				log.info("###################end trans plan!!!###################");
			});


			while (!futureM.isDone() || !futureP.isDone()) {
				Thread.sleep(2000);
				log.info("###################transing!!!###################");
			}
			log.info("###################trans done!!!###################");

		} catch (Exception e) {
			log.error("trans resource error : {}", e.getMessage());
		}
	}

	private int transPlanResource(ZoneId zone, ForkJoinPool planPool, Integer limit) throws InterruptedException {
		int result;

		//处理视屏
		List<ResRenderVideo> resRenderVideos = resRenderVideoMapper.listPicForFixPath(limit);
		CountDownLatch latch = new CountDownLatch(resRenderVideos.size());
		planPool.submit(() -> {
			resRenderVideos.parallelStream().forEach(it -> {
				String statue = "success;";
				String oldPath = it.getVideoPath();
				String fileKey = it.getFileKey();
				LocalDateTime localDateTime =
						LocalDateTime.ofInstant(it.getGmtCreate().toInstant(), zone);
				String newPath = "";
				String dbPath = "";
				try {
					newPath = getPathByFileKey(fileKey, localDateTime);
					dbPath = transFile(oldPath, newPath);
				} catch (Exception e) {
					log.error("trans file error, exception :{}", e.getMessage());
					statue = "failed, cause : " + e.getMessage();
				}
				if (StringUtils.isNotBlank(dbPath)) {
					it.setVideoPath(dbPath);
				}
				it.setRemark("迁移数据_" + statue + Strings.nullToEmpty(it.getRemark()));
				resRenderVideoMapper.updateByPrimaryKeySelective(it);
				latch.countDown();
			});
		});
		latch.await(120, TimeUnit.SECONDS);
		result = resRenderVideos.size() / limit;


		//处理图片
		List<ResRenderPic> resRenderPics = resRenderPicMapper.listPicForFixPath(limit);
		CountDownLatch latchPic = new CountDownLatch(resRenderPics.size());
		planPool.submit(() -> {
			resRenderPics.parallelStream().forEach(it -> {
				String statue = "success;";
				String oldPath = it.getPicPath();
				String fileKey = it.getFileKey();
				LocalDateTime localDateTime =
						LocalDateTime.ofInstant(it.getGmtCreate().toInstant(), zone);
				String newPath = "";
				String dbPath = "";
				try {
					newPath = getPathByFileKey(fileKey, localDateTime);
					dbPath = transFile(oldPath, newPath);
				} catch (Exception e) {
					log.error("trans file error, exception :{}", e);
					statue = "failed, cause : " + e.getMessage();
				}
				if (StringUtils.isNotBlank(dbPath)) {
					it.setPicPath(dbPath);
				}
				it.setRemark("迁移数据_" + statue + Strings.nullToEmpty(it.getRemark()));
				resRenderPicMapper.updateByPrimaryKeySelective(it);
				latchPic.countDown();
			});
		});

		latchPic.await(120, TimeUnit.SECONDS);
		return result > 0 ? result : resRenderPics.size() / limit;
	}

	private int transModelResource(ZoneId zone, ForkJoinPool modelPool, Integer limit) throws InterruptedException {
		AtomicInteger count = new AtomicInteger();
		List<ResModel> models = resModelService.listModelForFixPath(limit);
		CountDownLatch latch = new CountDownLatch(models.size());
		modelPool.submit(
				() -> {
					models
							.parallelStream()
							.forEach(
									model -> {
										if (StringUtils.isBlank(model.getFileKey())) {
											model.setFileKey(PRODUCT_BASE_PRODUCT_U3DMODEL_WINDOWS_PC);
										}
										String statue = "success;";
										String source3du = model.getFile3duPath();
										String sourceAb = model.getModelPath();
										String sourceThumbPic = model.getThumbPicPath();
										String sourceAbL = "";
										/// 待转换/转换失败  的模型, modelPath 为 3du文件
										if (Objects.equals(source3du, sourceAb)) {
											sourceAb = "";
										}
										// 已转换 modelPath 为 ab包
										if (StringUtils.isNotBlank(sourceAb) && sourceAb.endsWith(".assetbundle")) {
											sourceAbL = "/src" + sourceAb;
										}

										for (String source : Arrays.asList(source3du, sourceAb, sourceAbL, sourceThumbPic)) {
											if (StringUtils.isBlank(source)) {
												continue;
											}

											LocalDateTime localDateTime =
													LocalDateTime.ofInstant(model.getGmtCreate().toInstant(), zone);

											String target = "";
											String dbPath = "";
											// 获取目标路径
											try {
												if (source.endsWith(".assetbundle") || source.endsWith(".3DU")) {
													target = getPathByFileKey(model.getFileKey(), localDateTime);
												} else {
													target = getPathByFileKey(PRODUCT_BASE_PRODUCT_MODEL, localDateTime);
												}

												if (source.startsWith(target)) {
													//路径相同不处理
													continue;
												}
												if (source.startsWith("/src")) {
													// 处理ab未加密文件
													target = "/src" + target;
												}

												// move file
												dbPath = transFile(source, target);
												if (StringUtils.isNotBlank(dbPath)) {
													log.info(
															"move file success model id:{} , 　{} ---> {}",
															model.getId(),
															source,
															dbPath);
												}
											} catch (IOException e) {
												log.error(
														"move file error model id:{} , 　{} ---> {}",
														model.getId(),
														source,
														dbPath);
												statue = "failed, cause : " + e.getMessage() + ";";
												continue;
											}

											// sync db
											if (StringUtils.isBlank(dbPath)) {
												continue;
											} else if (dbPath.endsWith(".assetbundle")) {
												model.setModelPath(dbPath);
											} else if (dbPath.endsWith(".3DU")) {
												// 处理待转换/转换失败  的模型 modelPath 为 3du文件
												if (Objects.equals(model.getModelPath(), model.getFile3duPath())) {
													model.setModelPath(dbPath);
												}
												model.setFile3duPath(dbPath);
											} else {
												// 模型图片
												model.setThumbPicPath(dbPath);
											}
										}
										if (!Objects.equals(model.getThumbPicPath(), sourceThumbPic)) {
											ResPic resPic = new ResPic();
											resPic.setRemark("迁移数据_success;");
											resPic.setBusinessId(model.getId().intValue());
											resPic.setPicPath(model.getThumbPicPath());
											resPicService.updatePicByPicPath(resPic, sourceThumbPic);
										}
										model.setRemark("迁移数据_" + statue + Strings.nullToEmpty(model.getRemark()));
										model.setGmtModified(new Date());
										resModelService.updateByPrimaryKeySelective(model);
										count.incrementAndGet();
										latch.countDown();
									});
				});

		latch.await(120, TimeUnit.SECONDS);
		return count.get() / limit;
	}

	private String transFile(String source, String target) throws IOException {
		Path sourcePath = Paths.get(basePath, source);
		if (java.nio.file.Files.notExists(sourcePath)) {
			log.error("source is not exist, file path is  " + sourcePath.toString() + ";");
			throw new IOException("source is not exist, file path is  " + sourcePath.toString() + ";");
		}
		Path targetPath = Paths.get(basePath, target);
		if (java.nio.file.Files.notExists(targetPath)) {
			java.nio.file.Files.createDirectories(targetPath);
		}
		return java.nio.file.Files.move(sourcePath, targetPath.resolve(sourcePath.getFileName()))
				.toString()
				.replace(basePath, "");
	}

	/**
	 * 检验最后更新时间是否是当天
	 *
	 * @param lastUpdatedDate
	 * @return
	 */
	private boolean checkLastUpdatedDate(Date lastUpdatedDate) {
		// 当前时间
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// 获取今天的日期
		String nowDay = sf.format(now);
		// 对比的时间
		String day = sf.format(lastUpdatedDate);
		return day.equals(nowDay);
	}

	private String getPathByFileKey(String fileKey, LocalDateTime time) throws RuntimeException {
		String pathTemplate = fileKey2Path.get(fileKey);
		if (StringUtils.isBlank(pathTemplate)) {
			throw new RuntimeException("获取文件路径失败,fileKey is " + fileKey + ";");
		}

		DateTimeFormatter df = DateTimeFormatter.ofPattern("/yyyy/MM/dd/HH");
		String formatPart = time.format(df);
		return pathTemplate.replace("/yyyy/MM/dd/HH", formatPart);
	}

	@Override
	public void fixPlanCompanyRelation() {
		//查询需要修复的数据
		List<DesignPlanBrand> datas = designPlanBrandMapper.queryProblemData();
		List<String> companyIds;
		List<String> brandIds;
		if(datas != null && datas.size() > 0) {
			for(DesignPlanBrand data : datas) {
				//方案对应的公司集合
				companyIds = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(data.getAtt1()));
				//品牌集合
				brandIds = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(data.getAtt2()));
				if(!companyIds.contains("2501")) {
					//修改recommended表company为2501
					DesignPlanRecommended designPlanRecommended = new DesignPlanRecommended();
					designPlanRecommended.setId(data.getPlanId().longValue());
					designPlanRecommended.setCompanyId(2501);
					designPlanRecommendedMapper.updateByPrimaryKeySelective(designPlanRecommended);
					//修改designPlanBrand表该方案的一条brand和company为272和2501
					DesignPlanBrand designPlanBrand = new DesignPlanBrand();
					designPlanBrand.setPlanId(data.getPlanId());
					designPlanBrand.setBrandId(272);
					designPlanBrand.setCompanyId(2501);
					designPlanBrandMapper.updateByPlanId(designPlanBrand);
				}
				//重新交付数据
				DesignPlanDeliveryPO designPlanDeliveryPO = new DesignPlanDeliveryPO();
				designPlanDeliveryPO.setDesignPlanId(data.getPlanId());
				designPlanDeliveryPO.setReceiveBrandIds(brandIds.stream().map(Integer::valueOf).filter(id -> id != 272 && id != -1).collect(toList()));
				designPlanDeliveryPO.setDeliveryCompanyId(2501);
				designPlanDeliveryPO.setReceiveCompanyIds(companyIds.stream().map(Integer::valueOf).distinct().filter(id -> id != 2501).collect(toList()));
				this.deliver(designPlanDeliveryPO,false);
			}
				//删除design_plan_brand表中company_id不为2501的数据
				List<Integer> planIds = datas.stream().map(DesignPlanBrand::getPlanId).collect(toList());
				designPlanBrandMapper.fixByPlanIdAndCompanyId(planIds);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(() -> {
			System.out.println("it's :" + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.start();
		thread.join();
		System.out.println("done....");
	}
}
