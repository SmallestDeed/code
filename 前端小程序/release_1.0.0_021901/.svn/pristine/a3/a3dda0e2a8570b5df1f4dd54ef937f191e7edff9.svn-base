// pages/design/design.js
let app = getApp(), API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    designList: [],
    interspaceList: [],
    interspaceIndex: 0,
    interspaceShow: false,
    interspaceTitle: '',
    styleList: [],
    styleIndex: -1,
    styleTitle: '装修风格',
    styleShow: false,
    start: 0,
    totalCount: 0,
    isShowEmpty: false,
    type: '',
    houseType: '',
    styleCode: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.shareGo720(options);
    this.posterSharePanorama(options) // 获取海报分享生成720
    // this.isSevenShare(options);
    if (options.shareType == '720') {
      getApp().webUrl = decodeURIComponent(options.url)
      console.log('weburl', options.url)
      app.myNavigateBack('pages/web-720/web-720')
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    // this.data.start = 0;
    this.gethouseDetailsTypeList();
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  posterSharePanorama(options) {
    if (options.scene) {
      API.getJsonData({
        id: options.scene
      }).then(res => {
        let shareTimer = setInterval(() => {
          if (wx.getStorageSync('token')) { // 获取token后再进行跳转  
            console.log(res, 'swq')
            getApp().webUrl = getApp().myCompoundUrl(Object.assign(JSON.parse(res.obj.jsonData), { token: wx.getStorageSync('token') }))
            console.log(getApp().webUrl, '2313323121')
            wx.navigateTo({ url: '/pages/web-720/web-720' })
            clearInterval(shareTimer)
          }
        }, 100)
      })
    }
  },

  hmove() {return},

  select(e) {
    if (e.currentTarget.dataset.type == '客餐厅') {
      this.setData({
        interspaceShow: !this.data.interspaceShow,
        styleShow: false
      })
    }
    if (e.currentTarget.dataset.type == '装修风格') {
      this.setData({
        interspaceShow: false,
        styleShow: !this.data.styleShow
      })
    }
  },

  colseSelect() {
    this.setData({
      interspaceShow: false,
      styleShow: false,
    })
  },
  /**选择空间类型 */
  selectHouse(e) {
    console.log(e.currentTarget.dataset.type)
    let index = e.currentTarget.dataset.index;
    this.setData({
      styleList: this.data.interspaceList[index].designPlanStyleVoList,
      interspaceIndex: index,
      styleIndex: -1,
      interspaceTitle: this.data.interspaceList[index].houseName,
      styleTitle: '装修风格',
      houseType: e.currentTarget.dataset.type,
      styleCode: "",
      interspaceShow: false,
      styleShow: false,
      start: 0
    })
    this.getdesignList()
  },

  /**选择装修风格 */
  selectStyle(e) {
    let index = e.currentTarget.dataset.index;
    this.setData({
      styleIndex: index,
      styleTitle: this.data.styleList[index].styleName,
      styleCode: e.currentTarget.dataset.code,
      interspaceShow: false,
      styleShow: false,
      start: 0
    })
    this.getdesignList()
  },

  /**方案列表赛选条件数据请求 */
  gethouseDetailsTypeList() {
    API.gethouseDetailsTypeList().then(res => {
      if (res.obj) {
        this.addStyleAll(res.obj)
        this.setData({
          interspaceList: res.obj,
          houseType: res.obj[0].houseType,
          interspaceTitle: res.obj[0].houseName,
          styleList: res.obj[0].designPlanStyleVoList
        })
        this.getdesignList();
      }
    })
  },

  /**装修风格添加全部 */
  addStyleAll(arr) {
    let obj = { styleName: '全部', styleCode: ''}
    arr.forEach(item => {
      item.designPlanStyleVoList.unshift(obj)
    })
    return arr
  },

  /*获取方案列表*/
  getdesignList(type) {
    if (wx.getStorageSync('shopId')) {
      API.getNewRecommendCaseList({
        pageVo: { pageSize: 5, start: this.data.start },
        recommendationPlanSearchVo: {
          decoratePriceRange: "",
          decoratePriceType: "",
          designStyleId: this.data.styleCode,
          houseType: this.data.houseType,
          displayType: 'decorate',
          platformCode: "miniProgram",
          shopId: wx.getStorageSync('shopId'),
          enterType: 'zqProgram'
        }
      }).then(res => {
        if (res.datalist) {
          let arr = type == 'pull' ? this.data.designList.concat(res.datalist) : res.datalist;
          this.setData({
            designList: arr,
            totalCount: res.totalCount,
            isShowEmpty: arr.length > 0 ? false : true
          });
        } else {
          this.setData({ 
            designList: [],
            isShowEmpty: true 
          });
        }
      });
    } else {
      this.setData({ isShowEmpty: true });
    }
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
    console.log('123')
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.data.start += 5;
    if (this.data.start < this.data.totalCount) {
      this.getdesignList('pull')
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})