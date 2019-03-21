const state = {
  isRenew: false, // 是否要续费
  favoritesList: [], // 账户收藏夹列表
  typeList: [], // 产品类型库
  presentPath: '', // 当前的路由地址
  isRecom: false, // 是否是方案进的搜索页面
  isHard: false, // 是硬装进去的还是软装进去的
  isMyTask: true, // 是否是我的任务或我的消息进的720
  newsList: [], // 消息列表
  showDot: false, // 消息的已读标识
  isPersonal: false, // 是从个人中心进的720
  showIframe: false, // 显示iframe标签
  showVideo: false, // 显示video标签
  fasttype: '', // 一键装修进入720的类型
  audioObj: {}, // 720背景音乐audio对象
  goBackPath: '', // 720返回路由
  firstClass: '', // 一键装修从方案带过来的收藏按钮样式
  isFirst: false, // 是否是一键装修的第一个方案进的收藏夹
  isCollectIndex: 0, // 选择要收藏的方案索引
  recomSelIndex: 0, // 方案选择的下标
  errorCount: 0, // 登录失败次数
  swiperFlag: false, // 下边栏显示状态
  smallShowIndex: 0, // 下边栏选中的索引
  timeOut: false, // 判断登录是否超时
  hintMsg: false, // 用来判断弹窗提示信息的文本内容应该是什么
  subHintPopup: false, // 取消收藏成功提示弹窗显示
  favoritesFlag: false, // 收藏夹显示状态
  detailsSeeType: '', // 2表示是我的设计的720多点和漫游视频的,1表示是推荐的
  searchBtnFlag: false, // 搜索按钮显示
  searchContentShow: false, // 搜索界面显示
  selareaFlag: false, // 选择省市区的页面显示
  contentFlag: true, // 判断显示的是省或者市
  provinceIndex: 5, // 选择的省下标
  cityIndex: 11, // 选择的市下标
  areaTxt: '请选择省份', // 头部提示字段
  provinceName: '广东省', // 选择的省份
  cityName: '深圳市', // 选择的城市
  happenCollect: false, // 发生收藏事件
  happenCancelCollect: false, // 发生取消收藏事件
  initLoad: false, // 上拉加载内容初始化
  resourcesUrl: 'show.sanduspace.cn', // 资源地址
  isPad: false, // 判断机型是否为平板
  toastShow: false,  // 吐司显示
  popupShow: false, // 消息弹窗
  popupMsg: '', // 弹窗的提示信息
  account: '', // 账户
  pwd: '', // 密码
  loginToDesign: false, // 从登陆进我的设计
  loginToRecom: false, // 登陆失效重新登陆的时候用来判断是否重新加载
  comComponentsShow: false, // 控制头部底部组件显示false为隐藏
  comeFromPlan: false, // 判断进入一键装修页面前的路由(方案进入的为true,其他的为false)
  isLoader: true, // 是否需要加载组件 （上拉加载时候禁止）
  loadingFlag: true,  // 正在加载组件显示隐藏
  view720LoadingFlag: false, // 720图片的加载页面显示
  headerTxt: '',
  activeIndex: 0,
  spaceList: [], // 空间下拉列表
  designStyleList: [], // 我的设计下拉列表
  recomStyleList: [], // 推荐风格下拉列表
  collectStyleList: [], // 我的设计下拉列表
  recomItem: 0, // 方案列表被选中的项的索引
  decorateStyleList: [], // 一键装修风格下拉列表
  shapeList: [], // 形状下拉列表
  decorateTipArr: [], // 一键装修tip数组
  searchPath: '', // 搜索入口路径
  shapeTitleFlag: false,
  collectSignFlag: false, // 720、图片级中的收藏按钮显示样式
  design: { // 我的设计参数
    designNavFlag: true,
    activeIndex: 0   // 当前nav
  },
  recom: { // 推荐搜索参数
    recomNavFlag: true,
    activeIndex: 1,   // 当前nav
    brandName: '', // 品牌
    creator: '', // 设计者
    livingName: '', // 小区名称
    planName: '' // 方案名称
  },
  collect: {
    collectNavFlag: true,
    activeIndex: 3,   // 当前nav
    collectListShow: false // 收藏夹
  },
  personalFlag: false,
  fullView: {
    fullViewTitle: '',
    fullViewUrl: ''
  },
  smallContent: [], // 缩略图列表
  smallTitle: '', // 缩略图的字段说明
  view720: { // 720°全景
    view720Id: 0, // 720°全景id
    view720Small: [], // 720°缩略图列表
    view720Content: [], // 720显示的内容
    QRcodeText: 'http://www.baidu.com'
  },
  renderSign: false,
  photoView: { // 图片级
    photoViewId: 0, // 图片级id
    photoViewSmall: [], // 图片级数组对象
    photoContent: [] // 图片级显示的大图
  },
  videoView: { // 漫游视频
    videoViewId: 0, // 漫游视频id
    videoViewSmall: [], // 漫游视频数组对象
    videoContent: [] // 图片级显示的大图
  },
  search: { // 搜索模块
    bigImageShow: false, // 控制显示大图
    houseTxt: '', // 小区的名字
    houseList: [], // 户型的种类
    sizeTxt: '', // 小区单元的详细介绍
    spaceSize: [], // 空间大小
    shapeTxt: '', // 空间形状的介绍
    spaceFunctionId: '', // 搜索空间类型不同大小接口的空间类型参数
    areaValue: '' // 搜索空间类型不同大小接口的空间大小参数
  },
  result: { // 按小区搜索结果
    spaceId: 0,
    styles: [], // 一键装修风格列表
    spaces: [], // 一键装修空间列表
    spaceCommonName: '' // 一键装修title
  }
};
export default state;
