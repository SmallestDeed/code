// js规范
// 所有函数采用onshow写法，不要用onload function的写法
const app = getApp(), API = app.API //每个界面都要写上API，才能调

Page({

  /**
   * 页面的初始数据
   */
  data: {
    rename: '',
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    resourcePath: app.data.resourcePath, // 这个是图片的前缀
    isShowMasker: false,
    isShowYuXue: false,
    bowenCounter: 0,
    caseCounter: 0,
    userPhone:'',
    userName: '',
    defaultImg: '/static/images/news_pic_nor.png',
    defaultHeader: '/static/images/header.png',
    shopId:'',
    userId:'',
    detailInfo:{},
    bowenList:[],//博文列表
    trueCaseData: [],
    planCounter:0,
    indicatorDots: false,
    autoplay: true,
    interval: 4000,
    duration: 1000,
    designList: [],
    start: 0,
    contentHeight: 0,
    boxHeight: 0,
    isPull: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
     this.data.shopId=options.shopId;
     this.getDetailInfo();
     this.getOneKeyPlan();
     this.TureCaseList();
     this.getBowenList();
  },

  /*富文本高度*/
  getBoxHeigth() {
    let query = wx.createSelectorQuery();
    query.select('#info').boundingClientRect(rect => {
      this.setData({ contentHeight: rect.height });
      // console.log(this.data.contentHeight, 'contentHeight')
    }).exec();
    query.select('#info-box').boundingClientRect(rect => {
      this.setData({ boxHeight: rect.height });
      // console.log(this.data.boxHeight, 'boxHeight')
    }).exec();
  },

  examine() {
    this.setData({
      isPull: !this.data.isPull
    })
  },

  inputName() {

  },
  // 跳到博文详情
  bowenDetail(e) {
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: `/pages/bowenDetail/bowenDetail?articleid=${item.articleId}`,
    })
  },
  // 跳到一键方案
  oneKeyPlanPage() {
    wx.navigateTo({
      url: `/pages/oneKeyPlan/oneKeyPlan?shopId=${this.data.shopId}`
    })
  },
  getOneKeyPlan(){
    API.getOneKeyPlan({
      pageVo: { pageSize: 10, start: this.data.start },
      recommendationPlanSearchVo: {
        decoratePriceType: "",
        designStyleId: "",
        displayType: 'decorate',
        houseType: "",
        platformCode: "",
        shopId: this.data.shopId
      }
    }).then(res => {
      if (res.datalist) {
        // let arr = type == 'pull' ? this.data.designList.concat(res.datalist) : res.datalist;
        this.setData({
          designList: res.datalist,
          planCounter: res.totalCount
        });
      } else{
        this.setData({
          planCounter: 0
        })
      }
    });
  },
  // 真实案例
  TureCaseList() {
    API.getTrueCaseList({
      pageNo: 1,
      pageSize: 10,
      shopId: this.data.shopId
    }).then((res) => {
      if (res.datalist) {
        this.setData({
          trueCaseData: res.datalist,
          caseCounter: res.totalCount
        })
      } else {
        caseCounter: 0
      }
    })
  },
  // 真实案例详情
  toDetail(e) {
    let item = e.currentTarget.dataset.item
    wx.navigateTo({
      url: `/pages/trulyCaseDetail/trulyCaseDetail?caseId=${item.caseId}`,
    })
  },
  // 去博文列表
  bowenlist(){
    wx.navigateTo({
      url: `/pages/bowen/bowenList?shopId=${this.data.shopId}`,
    })
  },
  // 博文列表
  getBowenList() {
    API.getIndexBowenList({
      limit: 10,
      page: 1,
      shopId:this.data.shopId
    }).then((res) => {
      if (res.datalist) {
        let arr = res.datalist;
        arr.map((value) => {
          value.releaseTimeStr = app.changeTiem(value.releaseTimeStr)
        })
        this.setData({
          bowenList: arr,
          bowenCounter: res.totalCount
        })
      }
    })
  },

  getDetailInfo(){
    API.getDesignerDetail({
      shopId:this.data.shopId,
      platformValue:4
      }).then((res) => {
        res.data.shopIntroduced = getApp().filtrationHtml(res.data.shopIntroduced).length > 0 ? getApp().filtrationHtml(res.data.shopIntroduced).replace(/\<img/gi, '<img class="img"') : ''
         this.setData({
           detailInfo:res.data
         });
         this.data.userId = res.data.userId;
          if (res.data.shopIntroduced && res.data.shopIntroduced.length > 0) {
            setTimeout(() => {
              this.getBoxHeigth();
            }, 500)
          }
    })
  },
  yuxue() {
    this.setData({
      isShowMasker: true,
      isShowYuXue: true,
      userName: wx.getStorageSync('yuXueName') ? wx.getStorageSync('yuXueName') : '', 
      userPhone: wx.getStorageSync('yuXuePhone') ? wx.getStorageSync('yuXuePhone') : '',
    })

  },
  cancleYuxue() {
    this.setData({
      isShowMasker: false,
      isShowYuXue: false,
    })
  },
  goutong(e){
    //let name = e.currentTarget.dataset.name;
    let items = this.data.detailInfo;
    items.shopIntroduced = '';
    let item = JSON.stringify(items);
    wx.navigateTo({
      url: `/pages/chat/chat?item=${item}`,
    })
  },
  /*获取并过滤输入框表情*/
  getRename(e) {
    this.setData({
      userName: app.myEmoticon(e.detail.value)
    })
  },
  userPhone(e){
    this.setData({
      userPhone: app.myEmoticon(e.detail.value)
    })
  },
  formSubmit(e){
   
    API.contactMe({
      serviceType:4,
      shopId: this.data.shopId,
      userName: this.data.userName,
      userPhone: this.data.userPhone,
      businessType:0,
      appointUserId: this.data.userId
    }).then((res) => {
      if (res.success){
        wx.showToast({
          title: '预约成功',
          icon: 'success',
          duration: 4000
        })
        wx.setStorageSync('yuXueName', this.data.userName);
        wx.setStorageSync('yuXuePhone', this.data.userPhone);
        this.setData({
          isShowMasker: false,
          isShowYuXue: false,
        })
      } else{
        wx.showToast({
          title: '预约失败',
          icon: 'info',
          duration: 4000
        })
        this.setData({
          isShowMasker: false,
          isShowYuXue: false,
        })
      }
    })
  },
 
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let timer = setInterval(() => {
      if (app.data.loginStatus === 1) {
        clearInterval(timer)
        this.getOneKeyPlan();
        this.TureCaseList();
        this.getBowenList();
      }
    },100)
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /*
  跳到真实案例
  */
  trulyCase(){
    wx.navigateTo({
      url: `/pages/trulyCase/trulyCase?shopId=${this.data.shopId}`,
    })
  }
})