package com.nork.sync.service.impl;

/*import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;*/

/**
 * @Title: BaseAreaServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-行政区域ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 15:31:09
 * @version V1.0   
 */
//@Service("syncDataService")
//@Transactional
public class SyncDataServiceImpl /*implements SyncDataService*/{/*

	private static Logger logger = org.apache.log4j.Logger.getLogger(ClientDataService.class);

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-svr-mybatis.xml");

	//applicationContext.getBean("spaceCommonService");
	
	private SpaceCommonMapper spaceCommonMapper_svr;

	private DesignTempletMapper designTempletMapper_svr;

	private DesignTempletProductMapper  designTempletProductMapper_svr;
	
	private ProductRecommendationMapper productRecommendationMapper_svr;

	private BaseBrandMapper   baseBrandMapper_svr;
	
	private BaseProductMapper baseProductMapper_svr;
	
	private ResFileMapper resFileMapper_svr;
	
	private ResPicMapper  resPicMapper_svr;
	
	private ResModelMapper resModelMapper_svr;

	@Autowired
	private ClientDataService clientDataService;
	
	public void setSpaceCommonMapper_svr(SpaceCommonMapper spaceCommonMapper_svr) {
		this.spaceCommonMapper_svr = (SpaceCommonMapper)applicationContext.getBean("spaceCommonMapper");
	}

	public void setDesignTempletMapper_svr(
			DesignTempletMapper designTempletMapper_svr) {
		this.designTempletMapper_svr = (DesignTempletMapper)applicationContext.getBean("designTempletMapper");
	}

	public void setDesignTempletProductMapper_svr(
			DesignTempletProductMapper designTempletProductMapper_svr) {
		this.designTempletProductMapper_svr = (DesignTempletProductMapper)applicationContext.getBean("designTempletProductMapper");
	}

	public void setProductRecommendationMapper_svr(
			ProductRecommendationMapper productRecommendationMapper_svr) {
		this.productRecommendationMapper_svr = (ProductRecommendationMapper)applicationContext.getBean("productRecommendationMapper");
	}

	public void setBaseBrandMapper_svr(BaseBrandMapper baseBrandMapper_svr) {
		this.baseBrandMapper_svr = (BaseBrandMapper)applicationContext.getBean("baseBrandMapper");
	}

	public void setBaseProductMapper_svr(BaseProductMapper baseProductMapper_svr) {
		this.baseProductMapper_svr = (BaseProductMapper)applicationContext.getBean("baseProductMapper");
	}

	public void setResFileMapper_svr(ResFileMapper resFileMapper_svr) {
		this.resFileMapper_svr = (ResFileMapper)applicationContext.getBean("resFileMapper");
	}

	public void setResPicMapper_svr(ResPicMapper resPicMapper_svr) {
		this.resPicMapper_svr = (ResPicMapper)applicationContext.getBean("resPicMapper");
	}

	public void setResModelMapper_svr(ResModelMapper resModelMapper_svr) {
		this.resModelMapper_svr = (ResModelMapper)applicationContext.getBean("resModelMapper");
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public SpaceCommonMapper getSpaceCommonMapper_svr() {
		return spaceCommonMapper_svr;
	}

	public DesignTempletMapper getDesignTempletMapper_svr() {
		return designTempletMapper_svr;
	}

	public DesignTempletProductMapper getDesignTempletProductMapper_svr() {
		return designTempletProductMapper_svr;
	}

	public ProductRecommendationMapper getProductRecommendationMapper_svr() {
		return productRecommendationMapper_svr;
	}

	public BaseBrandMapper getBaseBrandMapper_svr() {
		return baseBrandMapper_svr;
	}

	public BaseProductMapper getBaseProductMapper_svr() {
		return baseProductMapper_svr;
	}

	public ResFileMapper getResFileMapper_svr() {
		return resFileMapper_svr;
	}

	public ResPicMapper getResPicMapper_svr() {
		return resPicMapper_svr;
	}

	public ResModelMapper getResModelMapper_svr() {
		return resModelMapper_svr;
	}

	*//**
	 * 样板房同步
	 * @param designTempletIds
	 * @return  int 
	 *//*
	@Override
	public JSONObject syncDesignTemplet(String designTempletIds,LoginUser loginUser) {
		//是否给客户端推送同步信息
		Session webSocketSession = null;
		if(CommonServer.clientMap.containsKey(loginUser.getId())){
			webSocketSession = CommonServer.clientMap.get(loginUser.getId());
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("success",true);
		// 组装样板房数据
		if( StringUtils.isNotBlank(designTempletIds) ){
			String[] designTempletIdsArr = designTempletIds.split(",");
			if( designTempletIdsArr != null && designTempletIdsArr.length > 0 ){
				//给客户端发送同步日志
				CommonServer.sendMessage(webSocketSession,"样板房同步开始！本次共同步【"+designTempletIdsArr.length+"】个样板房\r\n");
				for( int i=0;i<designTempletIdsArr.length;i++ ){
					CommonServer.sendMessage(webSocketSession,"开始同步第【"+(i+1)+"】个样板房\r\n");
					if( StringUtils.isBlank(designTempletIdsArr[0]) ){
						continue;
					}
					Integer designTempletId = Integer.valueOf(designTempletIdsArr[0]);
//					JSONObject syncDesignTempletJSONObject = clientDataService.assembleDesignTemplet(designTempletId, loginUser);
					Map<String,Object> syncDesignTempletMap = clientDataService.assembleDesignTemplet(designTempletId, loginUser);
					if( (Boolean)syncDesignTempletMap.get("success") ){
						Map<String,Class> classMap = new HashMap<>();
						classMap.put("syncDesignTempletProducts", SyncDesignTempletProduct.class);
						classMap.put("syncProductRecommendations", SyncProductRecommendation.class);
//						SyncDesignTemplet syncDesignTemplet = (SyncDesignTemplet) JSONObject.toBean((JSONObject) syncDesignTempletJSONObject.get("obj"), SyncDesignTemplet.class,classMap);
						SyncDesignTemplet syncDesignTemplet = (SyncDesignTemplet) syncDesignTempletMap.get("obj");
						
						JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
						factory.setAddress("http://192.168.1.211:48080/nork/webservice/serverDataService?wsdl");
						factory.setServiceClass(ServerDataService.class);
						ServerDataService serverDataService = (ServerDataService) factory.create();
						Client client = ClientProxy.getClient(serverDataService);
						HTTPConduit conduit = (HTTPConduit) client.getConduit();
						HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
						httpClientPolicy.setConnectionTimeout(10000);
						httpClientPolicy.setReceiveTimeout(180000);
						httpClientPolicy.setAllowChunking(false);
						httpClientPolicy.setAllowChunking(false);
						conduit.setClient(httpClientPolicy);
						
//						ResEntity resEntity = new ResEntity();
//						resEntity.setFile(ClientDataServiceImpl.constructDataHandler("home\\spaceCommon\\pic\\140846_20160520174714811.jpg"));
//						syncDesignTemplet.setMacBookModelU3dEntity(resEntity);
//						jsonObject = (JSONObject) serverDataService.insertDesignTemplet(syncDesignTemplet);
//						CommonServer.sendMessage(webSocketSession,"组装完成，等待服务器返回执行结果\r\n");
//						Object result = serverDataService.insertDesignTemplet(syncDesignTemplet);
//						return JSONObject.fromObject(result);
					}else{
						jsonObject.remove("success");
						jsonObject.accumulate("success", false);
						jsonObject.accumulate("message", syncDesignTempletMap.get("message"));
					}
				}
			}
		}
		return jsonObject;
	}

	*//**
	 * 同步空间
	 * @param spaceCommonId
	 * @return
	 *//*
	@Override
	public JSONObject syncSpaceCommon(Integer spaceCommonId,LoginUser loginUser){

		return null;
	}

	*//**
	 * 分类同步
	 * @param baseArea
	 * @return  int 
	 *//*
	@Override
	public JSONObject syncProductCatory() {
		//检查分类定义是否齐全
		//检查分类中的产品是否齐全
		return null;
	}
	
	*//**
	 * 产品同步
	 * @param productId
	 * @return  int 
	 *//*
	@Override
	public JSONObject syncBaseProduct(Integer productId,LoginUser loginUser) {
		//检查数据
		//检查关联数据（品牌）
		//同步数据
		//同步文件
		return null;
	}

	public static void main(String[] args) {
		Integer a = Integer.valueOf("a");
	}
*/}
