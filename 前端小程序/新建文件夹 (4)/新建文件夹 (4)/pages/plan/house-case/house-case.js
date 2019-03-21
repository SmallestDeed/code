// pages/house-case/house-case.js
let myForEach = getApp().myForEach, mySplitUrl = getApp().mySplitUrl,
  myCompoundUrl = getApp().myCompoundUrl, $App = getApp(), API = getApp().API, ttt = ''
import {shareTitle} from '../../../utils/config.js';
import { emptyTemplate } from '../../../component/emptyTemplate/emptyTemplate'
Page({
  emptyTemplate, // 无数据组件
  /**
   * 页面的初始数据
   */
  data: {
    scrollTop: 0,
    floorstatus: false,
    imageArray: ['https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea', 'https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea'],
    spaceList: [],
    areaList: [],
    styleList: [],
    types: 0,
    favoriteRequest: true,
    collectRequest: true,
    conditionActive: -1,
    childConditionActive: [0, -1, -1],
    recommendCaseList: [],
    areaId:'',
    styleCode:'',
    oneAreaId:'',
    resourcePath: getApp().resourcePath,
    sevenUrl: getApp().sevenUrl,
    wholeHouseUrl: getApp().wholeHouseUrl,
    pageSize: 5,
    getCaseParams: {
      spaceType: '',
      designPlanStyleId: '',
      spaceArea: ''
    },
    isShow: true,
    totalCount: 0,
    caseListheight: '',
    caseListOverflow: 'none',
    scroolLeft: 0,
    fitmentWayList: [],
    fitmentWayActive: 0,
    fitmentWayChildActive: 0,
    decoratePriceBox: false,
    decoratePriceList: [],
    animate: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getDesignplanconditionmetadata() // 获取空间
    this.isSevenShare(options) // 是否720分享
    // this.getConditionMetadata() // 获取方案筛选条件
    this.getFitmentQuote() // 获取专修报价筛选条件
    new this.emptyTemplate() // 注册组件 
  },
  routerToCaseDetails(e) {
    let id = e.currentTarget.dataset.id, type = e.currentTarget.dataset.type
    wx.navigateTo({ url: `/pages/case-details/case-details?id=${id}&type=${type || 0}` })
  },
  showDecoratePriceBox(e) { // 处理底部滑动
    // let height = wx.getSystemInfoSync().windowHeight 
    // e.detail ? this.setData({ caseListheight: height + 'px', caseListOverflow: 'hidden' }) :
    //   this.setData({ caseListheight: '', caseListOverflow: 'none' })
  },
  getFitmentQuote() { // 获取装修报价筛选条件
    API.getFitmentQuote()
    .then(res => {
      let arr = []
      if (res.code === 200) {
        arr = res.list
        arr.unshift({ name: '全部', sonList: [], value: '' })
        myForEach(arr, (value) => {
          value.sonList.unshift({ name: '全部', sonList: [], value: '' })
        })
      }
      this.setData({ fitmentWayList: arr })
    })
  },
  switchFitmentWay(e) { // 切换装修方式
    let target = e.currentTarget.dataset, value = target.value, index = target.index, type = target.type, key = ''
    if (type == 'parent') {
      this.setData({
        fitmentWayActive: this.data.fitmentWayActive == index ? 0 : index,
        fitmentWayChildActive: 0
      })
    } else {
      this.setData({
        fitmentWayChildActive: this.data.fitmentWayChildActive == index ? 0 : index
      })
    }
  },
  isSevenShare(options) {
    if (options.item) { // 720
      let item = JSON.parse(options.item)
      item.url = decodeURIComponent(item.url)
      getApp().data.webUrl = myCompoundUrl(item)
      wx.navigateTo({
        url: '/pages/web-720/web-720',
      })
    } else if (options.url) { // 视频
      wx.navigateTo({
        url: '/pages/video/video?url=' + options.url,
      })
    }
  },
  getDesignplanconditionmetadata() {
    API.getDesignplanconditionmetadata()
    .then(res => {
      myForEach(res.obj[0].designPlanAreaList, (value) => {
        value.areaName = value.areaName.replace('~', '-')
      })
      this.setData({
        spaceList: res.obj,
        areaList: res.obj[0].designPlanAreaList,
        oneAreaId: '',
        styleList: res.obj[0].designPlanStyleVoList,
        'getCaseParams.spaceType': '',
      })
      this.getRecommendCaseList(this.data.getCaseParams)
    })
  },
  resetFun: function(){
    let oneAreaId = this.data.oneAreaId;
    this.setData({
      areaId: '',
      styleCode: '',
      //'getCaseParams.spaceType': '',
      'getCaseParams.designPlanStyleId': '',
      'getCaseParams.spaceArea': '',
      fitmentWayChildActive: 0,
      fitmentWayActive: 0
    })
    this.getRecommendCaseList();
  },
  submitFun: function(){
    this.setData({ isShow: true });
    let obj = {};
    obj.spaceType = this.data.types || '';
    obj.designPlanStyleId = this.data.styleCode || '';
    obj.spaceArea = this.data.areaId|| '';
    this.getRecommendCaseList(this.data.getCaseParams);
  },
  //顶部导航切换
  cyhangeType :function(e){
    let ty = e.currentTarget.dataset.ty;
    let i = e.currentTarget.dataset.i;
    let n = e.currentTarget.dataset.n;
        n = n>2?n-2:0;
    let width = parseInt((wx.getSystemInfoSync().windowWidth||750) / 5);
    if (ty == this.data.getCaseParams.spaceType)//不可重复点击
      return
    ttt = ty;

    let spaceList = this.data.spaceList;
    this.setData({
      scroolLeft: n * width,
      'getCaseParams.spaceType': ty,
      types:ty,
      styleCode: '',
      areaId: '',
      'getCaseParams.designPlanStyleId': '',
      'getCaseParams.spaceArea': '',
      areaList: spaceList[i].designPlanAreaList,
      styleList: spaceList[i].designPlanStyleVoList,
      fitmentWayActive: 0,
      fitmentWayChildActive: 0
    })
    console.log(this.data.scroolLeft);
    this.getRecommendCaseList(this.data.getCaseParams);
  }, 
  shouMoreFun: function(){
    this.setData({ isShow: !this.data.isShow });
  },
  areaIdFun: function (e) {
    this.setData({
      areaId: e.currentTarget.dataset.info || '',
      'getCaseParams.spaceArea': e.currentTarget.dataset.info || ''
    })
  },
  styleCodeFun: function (e) {
    this.setData({
      styleCode: e.currentTarget.dataset.info || '',
      'getCaseParams.designPlanStyleId': e.currentTarget.dataset.info || ''
    })
  },
  addZero(num){
    num=num.toString();
    if(num.length==1){
      num='0'+num
    }
    return num
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
    this.getConditionMetadata();
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

  onReachBottom: function () {
    
    if(!this.data.isShow)
      return;
    if (this.data.pageSize >= this.data.totalCount) {
      return
    } else {
      this.setData({
        pageSize: this.data.pageSize + 5
      })
      this.getRecommendCaseList(this.data.getCaseParams)
    }
  },

  onPullDownRefresh: function () {
    if (!this.data.isShow)
      return;
    this.getConditionMetadata() // 获取方案筛选条件
    wx.stopPullDownRefresh() //在标题栏中显示加载
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return {
        title: shareTitle,
        path: `/pages/plan/house-case/house-case`,
        success(res) {
        },
        fail(err) {
        }
      }
      // return $App.shareAppMessageObj

    }
  },
  enlargeImage(url) { // 查看大图
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: [url] // 需要预览的图片http链接列表
    })
  },
  toThreeD(e) { // 调转到3D界面
    let type = e.currentTarget.dataset.type, item = e.currentTarget.dataset.item, routerQueryType = null, webUrl = null, sevenObj = null
    if (type === 'video') {
      API.getRecommendedVideoId({
        planRecommendedId: item.designPlanRecommendId,
        remark: type
      })
      .then(res => {
        if (res.success) { return res.datalist[0].id } else { return false }
      })
      .then(res => {
        if (res) {
          API.getRecommendedVideoMessage({ thumbId: res })
            .then(res => {
              res.success ? this.toVideo(res.obj.url) : wx.showToast({ title: '打开失败', icon: 'none' })
            })
        }
      })
    } else {
      type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
      item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
        (webUrl = this.data.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 1,
          planId: item.designPlanRecommendId,
          routerQueryType: routerQueryType,
          customReferer: $App.wxUrl,
          platformNewCode: 'miniProgram'
        })
      for (let key in sevenObj) {webUrl += key + '=' + sevenObj[key] + '&'}
      getApp().data.webUrl = webUrl
      $App.myNavigateBack('pages/web-720/web-720')
      console.log(webUrl)
    }
  },
  getConditionMetadata() { // 获取方案筛选条件
    API.getConditionMetadata()
    .then(res => {
      if (res.status) {
        myForEach(res.obj[0].designPlanAreaList, (value) => {value.areaName = value.areaName.replace('~', '-')})
        this.setData({
          spaceList: res.obj,
          areaList: res.obj[0].designPlanAreaList,
          styleList: res.obj[0].designPlanStyleVoList,
          'getCaseParams.spaceType': ttt
        })
        this.getRecommendCaseList(this.data.getCaseParams)
      } else {
        this.setData({ spaceList: [],areaList: [],styleList: [] })
      }
    })
  },
  switchCondition(e) { // 切换选择框\
    let index = e.currentTarget.dataset.index
    if (index === this.data.conditionActive) {
      index = -1
    }
    this.setData({
      conditionActive: index
    })
    if (this.data.conditionActive === -1) {
      this.setData({
        caseListheight: '',
        caseListOverflow: 'none'
      })
    } else {
      let caseListheight = wx.getSystemInfoSync().windowHeight
      this.setData({
        caseListheight: caseListheight + 'px',
        caseListOverflow: 'hidden'
      })
    }
  },
  chooseCondition(e) { // 选择筛选条件
    let indexP = e.currentTarget.dataset.indexp, indexC = e.currentTarget.dataset.indexc
    if (indexP === 0) {
      this.data.childConditionActive[1] = -1
      this.data.childConditionActive[2] = -1
      myForEach(this.data.spaceList[indexC].designPlanAreaList, (value) => {
        value.areaName = value.areaName.replace('~', '-')
      })
      this.setData({
        areaList: this.data.spaceList[indexC].designPlanAreaList,
        styleList: this.data.spaceList[indexC].designPlanStyleVoList,
        childConditionActive: this.data.childConditionActive
      })
    }
    this.data.childConditionActive[indexP] = indexC
    this.setData({
      childConditionActive: this.data.childConditionActive,
      'getCaseParams.spaceType': this.data.spaceList[this.data.childConditionActive[0]].houseType,
      'getCaseParams.designPlanStyleId': this.data.childConditionActive[2] == -1 ? '' : this.data.styleList[this.data.childConditionActive[2]].styleCode,
      'getCaseParams.spaceArea': this.data.childConditionActive[1] == -1 ? '' : this.data.areaList[this.data.childConditionActive[1]].areaId
    })
    this.getRecommendCaseList(this.data.getCaseParams)
  },
  closeCaseCondition() {
    this.setData({
      conditionActive: -1
    })
    if (this.data.conditionActive === -1) {
      this.setData({
        caseListheight: '',
        caseListOverflow: 'none'
      })
    } else {
      let caseListheight = wx.getSystemInfoSync().windowHeight
      this.setData({
        caseListheight: caseListheight + 'px',
        caseListOverflow: 'hidden'
      })
    }
  },
  getRecommendCaseList(o) { // 获取方案列表
    let obj = {
      spaceType: o?o.spaceType:'',
      designPlanStyleId: o ? o.designPlanStyleId : '',
      spaceArea: o ? o.spaceArea : '',
      decoratePriceType: this.data.fitmentWayActive == 0 ? '' : this.data.fitmentWayList[this.data.fitmentWayActive].value,
      decoratePriceRange: this.data.fitmentWayChildActive == 0 ? '' : this.data.fitmentWayList[this.data.fitmentWayActive].sonList[this.data.fitmentWayChildActive].value
    }
    API.getRecommendCaseList({
      curPage: 1,
      pageSize: this.data.pageSize,
      spaceType: obj.spaceType || '',
      designPlanStyleId: obj.designPlanStyleId || '',
      spaceArea: obj.spaceArea || '',
      displayType: 'decorate',
      isSortByReleaseTime: 0,
      decoratePriceType: obj.decoratePriceType,
      decoratePriceRange: obj.decoratePriceRange
    })
    .then(res => {
      if (res.status) {
        if (res.obj) {
          this.setData({ recommendCaseList: res.obj,totalCount: res.totalCount })
          this.emptyTemplateShow('hide')
        } else {
          this.emptyTemplateShow('show')
          this.setData({ recommendCaseList: [],totalCount: 0 })
        }
      } else {
        this.emptyTemplateShow('show')
        this.setData({ recommendCaseList: [], totalCount: 0 })
      }    
    })
  },

  toVideo(url) {
    wx.navigateTo({
      url: '/pages/template/video/video?url=' + url
    })
  },
  putInMyhouse(e) { // 装进我家
    // this.renderTypeShow() // 显示组件  
    let item = e.currentTarget.dataset.item
    wx.setStorageSync('caseItem', item)
    wx.navigateTo({
      url: '../case-house-type/case-house-type'
    })
  },

  collectCase(e) { // 方案收藏
    let that = this,
        item = e.currentTarget.dataset.item,
        index = e.currentTarget.dataset.index,
        status = null,
        title = '收藏'
    if (that.data.collectRequest == true) {
      that.setData({collectRequest: false })
      item.isFavorite ? (status = 0, title = '取消收藏'): (status = 1, title = '收藏')
      API.collectCase({ status: status, recommendId: item.designPlanRecommendId})
      .then(res => {
        if (res.success) {
          status == 0 ? this.data.recommendCaseList[index].collectNum -= 1 : this.data.recommendCaseList[index].collectNum += 1
          this.data.recommendCaseList[index].isFavorite = status          
          this.setData({ recommendCaseList: this.data.recommendCaseList })
          wx.showToast({title: title + '成功'})
        } else {
          wx.showToast({title: '收藏失败',icon: 'none'})
        }
        setTimeout(function () { that.setData({ collectRequest: true }) }, 500)        
      })
    }
  },
  // likeCase(e) { // 方案点赞
  //   let that = this,
  //       item = e.currentTarget.dataset.item,
  //       index = e.currentTarget.dataset.index,
  //       status = null,
  //       title = '点赞'
  //   if (that.data.favoriteRequest == true) {
  //     that.setData( {favoriteRequest: false} )
  //     item.isLike ? (status = 0, title = '取消点赞') : (status = 1, title = '点赞')
  //     API.likeCase({ status: status, designId: item.designPlanRecommendId})
  //     .then(res => {
  //       if (res.success) {
  //         status == 0 ? this.data.recommendCaseList[index].likeNum -= 1 : this.data.recommendCaseList[index].likeNum += 1
  //         this.data.recommendCaseList[index].isLike = status          
  //         this.setData({ recommendCaseList: this.data.recommendCaseList })
  //         wx.showToast({ title: title + '成功' })
  //       } else {
  //         wx.showToast({ title: title + '失败', icon: 'none' })
  //       }
  //       setTimeout(function () { that.setData({ favoriteRequest: true }) }, 500)        
  //     })
  //   }
  // }

}) 