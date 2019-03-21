// pages/decoration/designerDetail/designerDetail.js
import { resourcePath, defaultImg } from '../../../utils/config.js'
var calcTime = require('../../../utils/calcTime.js');
let $App = getApp(), API = getApp().API;
let share = require('../../../utils/shareConfig.js')
/**
 * 店铺详情（根据businessType区分店铺类型）
 * businessType：3 设计师
 * businessType：4 设计公司
 * businessType：5 装修公司
 * businessType：6 工长
 * businessType：2 建材家居
 */
Page({
  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: $App.staticImageUrl,
    defaultImg: defaultImg,
    imgURL: getApp().data.imgURL,
    planCount: 10,
    modelRoomCount: 11,
    commentCount: 12,
    resourcePath: resourcePath,
    currTab: 0,//默认是第一个
    targetId: '',
    favoriteRequest: true,
    isFoldDesigner: true,
    collectRequest: true,
    displayType: 'decorate',
    companyId: '',
    casePageSize: 10,
    caseList: [],
    pageNoList: {
      plan: 1,
      model: 1,
      success: 1,
      comment: 1
    },
    tabContList: {
      plan: [],
      model: [],
      success: [],
      comment: []
    },
    hasMore: {
      plan: true,
      model: true,
      success: true,
      comment: true
    },
    pageSize: 10,
    banners: [
      "http://open.rjhq.cn/xz/wx/images/xq_banner01.jpg",
      "http://open.rjhq.cn/xz/wx/images/xq_banner01.jpg",
      "http://open.rjhq.cn/xz/wx/images/xq_banner01.jpg"
    ],
    targetData: {},
    decorateList: [],
    decorateListNum: 0,
    protoListNum: 0,
    publicList: [],
    teamList: [],
    activitys: [],
    sevenUrl: getApp().sevenUrl,
    decoratePlanNum: '',
    publicPlanNum: '',
    articles: '',
    articlesNum: 0,
    isShowReservePop: false,
    isbindedPhone: false,
    bindedName: '',
    bindingPhoneGetCodeText: '获取验证码',
    bindingPhoneformaTure: false,
    bindingPhoneMobile: '',
    bindingPhoneTimer: null,
    bindingPhoneCode: '',
    isNeededShowPhone: false,
    tabIndex: 0,
    scrollTop: 0,
    tabArr: ['设计师', '真实案例', '博文', '店铺介绍']
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.init(options)
  },
  init(options) { // 初始化
    new $App.newNav() // 注册快速导航组件
    wx.setStorageSync('targetId', options.id);
    if (options.id == undefined) { wx.navigateBack({ delta: 1 }); return }
    this.setData({ targetId: options.id })
    this.updateVisitCount();

    this.getArticles();
    this.getUserPhone();
  },
  //埋点
  sellingPoint(event) {
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: this.data.targetData.shopName
    })
  },
  onPageScroll: function (e) {
    this.setData({ scrollTop: e.scrollTop })
  },
  changeName(e) {
    console.log(e)
    this.setData({ bindedName: e.detail.value })
  },
  goToReserve() {
    if (!this.checkPhone()) {
      wx.showToast({
        title: '请输入正确的手机号',
        icon: 'none'
      })
      return
    }
    if (!this.data.isbindedPhone && this.data.isNeededShowPhone) {
      if (!this.checkCode()) {
        wx.showToast({
          title: '请输入正确的验证码',
          icon: 'none'
        })
        return
      }
    }
    if (this.data.bindedName == '') {
      wx.showToast({
        title: '请输入姓名',
        icon: 'none'
      })
      return
    }
    const reserveData = {
      "serviceType": this.data.serviceType,
      "shopId": this.data.targetId,
      "userName": this.data.bindedName,
      "userPhone": this.data.bindingPhoneMobile,
      "businessType": 0,
      "appointUserId": wx.getStorageSync('id')
    }
    if (this.data.bindingPhoneCode) {
      reserveData.code = this.data.bindingPhoneCode
    }
    API.getReserveSave(reserveData).then(res => {
      if (res.success) {
        wx.showToast({
          title: '预约成功',
          duration: 3000,
          icon: 'success'
        })
        this.setData({
          isShowReservePop: false
        })
        // this.getUserPhone();
      } else {
        wx.showToast({
          title: res.message,
          icon: 'none'
        })
      }
    })
  },
  getUserPhone() {
    API.getUserPhone({
      id: wx.getStorageSync('id')
    }).then(res => {
      if (res.success) {
        this.setData({
          bindedName: res.obj.nickName,
          bindingPhoneMobile: res.obj.mobile
        })
        if (this.data.bindingPhoneMobile) {
          this.setData({
            isbindedPhone: true,
            isNeededShowPhone: true
          })
        }
      }
    })
  },
  showBindPhone() {
    this.setData({
      isbindedPhone: false,
      bindingPhoneMobile: ''
    })
  },
  checkPhone() {
    let regMobile = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/
    return regMobile.test(this.data.bindingPhoneMobile)
  },
  checkCode() {
    return /^\d{6}\b/.test(this.data.bindingPhoneCode)
  },
  beginBinding() { // 开始绑定
    let codeFlag = /^\d{6}\b/.test(this.data.bindingPhoneCode)
    if (!this.data.bindingPhoneMobile || !this.data.bindingPhoneCode) {
      return
    }
    if (!this.checkPhone()) {
      wx.showToast({
        title: '请输入正确的手机号',
        icon: 'none'
      })
      return
    } else if (!codeFlag) {
      wx.showToast({
        title: '请输入正确的验证码',
        icon: 'none'
      })
      return
    }
    API.bindUserMobile({
      mobile: this.data.bindingPhoneMobile,
      authCode: this.data.bindingPhoneCode
    })
      .then(res => {
        if (res.success) {
          wx.showToast({
            title: '绑定成功',
            duration: 2000,
          })
          setTimeout(() => {
            this.setData({
              'bindingPhoneShow': false
            })
            this.bindPhoneCallBack() // 绑定手机号回调
          }, 2000);
        } else {
          wx.showToast({
            title: res.message,
            icon: 'none'
          })
        }
      })
  },
  changeCode(e) {
    this.setData({
      'bindingPhoneCode': e.detail.value
    })
  },
  changeMobile(e) {
    this.setData({
      'bindingPhoneMobile': e.detail.value
    })
    let regMobile = /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/
    let flag = regMobile.test(this.data.bindingPhoneMobile)
    this.setData({
      'bindingPhoneformaTure': flag
    })

  },
  getCode() {
    if (!this.data.bindingPhoneformaTure || this.data.bindingPhoneGetCodeText !== '获取验证码') {
      wx.showToast({ title: '请输入正确的手机号码', icon: 'none' })
      return
    }
    API.getBindPhoneCode({
      phoneNumber: this.data.bindingPhoneMobile,
      msgId: 12
    })
      .then(res => {
        if (res.success) {
          let count = 61, $self = this
          this.data.bindingPhoneTimer = setInterval(() => {
            count--
            $self.setData({
              'bindingPhoneGetCodeText': count + 's'
            })
            if (count <= 0) {
              clearInterval($self.data.bindingPhoneTimer)
              $self.setData({
                'bindingPhoneGetCodeText': '获取验证码'
              })
            }
          }, 1000)
        } else {
          this.setData({
            'bindingPhoneGetCodeText': '获取验证码'
          })
        }
      })
  },
  showReservePop(e) {
    this.getUserPhone();
    this.setData({
      isShowReservePop: true,
      serviceType: e.target.dataset.type,
      bindingPhoneCode: ''
    })
  },
  closeReservePop() {
    this.setData({ isShowReservePop: false })
  },
  routerToChatFrame() { // 跳转至聊天框
    let item = JSON.stringify(this.data.targetData)
    item = item.replace(/=/g, '$')
    item = item.replace(/&/g, '$')
    wx.navigateTo({ url: '/pages/chat-frame/chat-frame?item=' + item })
  },
  getArticles() {
    API.getCompanyBlogArticle({
      limit: 3,
      page: 1,
      start: 0,
      shopId: this.data.targetId
    })
      .then(res => {
        if (res.datalist) {
          for (let i = 0; i < res.datalist.length; i++) {
            res.datalist[i].showTime = calcTime.calcTime(res.datalist[i].releaseTimeStr)
          }
          this.setData({ articles: res.datalist, articlesNum: res.totalCount })
        }
      })
  },
  getPorject() {
    API.getProjectCaseList({
      pageNo: 1,
      pageSize: this.data.casePageSize,
      shopId: this.data.targetId
    })
      .then(res => {
        if (res.success) {
          this.setData({
            caseList: res.datalist || []
          })
        }
      })
  },
  updateVisitCount() {
    API.addCompanyVisitCount({ id: this.data.targetId })
  },
  getPlanNum() {
    API.addRecommendedPlanVisitCount({ displayType: 'decorate', companyId: this.data.companyId, shopId: this.data.targetId })
      .then(res => {
        this.setData({ decoratePlanNum: res.obj })
      })
    API.addRecommendedPlanVisitCount({ displayType: 'public', companyId: this.data.companyId, shopId: this.data.targetId })
      .then(res => {
        this.setData({ publicPlanNum: res.obj })
      })
  },
  getRecommendCaseList(obj) { // 获取方案列表
    let recommendationPlanSearchVo1 = {
      shopId: this.data.targetId,
      displayType: 'decorate',
      platformCode: 'selectDecoration',
      enterType: 'shop'
    }
    let recommendationPlanSearchVo2 = {
      shopId: this.data.targetId,
      displayType: 'prototype',
      platformCode: 'selectDecoration',
      enterType: 'shop'
    }
    let pageVo = {
      pageSize: this.data.pageSize,
      start: 1,
    }
    let parms1 = {
      "recommendationPlanSearchVo": recommendationPlanSearchVo1,
      "pageVo": pageVo
    }
    let parms2 = {
      "recommendationPlanSearchVo": recommendationPlanSearchVo2,
      "pageVo": pageVo
    }
    API.getCompanyPlan(parms1)
      .then(res => {
        if (res.datalist) {
          this.setData({ decorateList: res.datalist.slice(0, 1), decorateListNum: res.totalCount })
        } else {
          this.setData({ decorateList: [] })
        }
      })
    API.getCompanyPlan(parms2)
      .then(res => {
        if (res.datalist) {
          this.setData({ publicList: res.datalist.slice(0, 1), protoListNum: res.totalCount })
        } else {
          this.setData({ publicList: [] })
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
    this.getDetail();
    this.getPorject();
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
    // let size = this.data.pageSize;
    // size = size + 5;
    // this.setData({
    //   pageSize: size
    // })
    // this.getRecommendCaseList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    if (res.from == 'menu') {
      let url = '/pages/decoration/companyDetail/companyDetail',
        parms = {
          id: this.data.targetId
        },
        title = this.data.targetData.shopName;
      let obj = share.shareUrl(url, title, parms)
      return obj;
    }
  },

  collectCase(e) { // 方案收藏
    let that = this,
      item = e.currentTarget.dataset.item,
      index = e.currentTarget.dataset.index,
      status = null,
      title = '收藏',
      type = e.currentTarget.dataset.type + 'List'
    if (that.data.collectRequest == true) {
      that.setData({ collectRequest: false })
      item.isFavorite ? (status = 0, title = '取消收藏') : (status = 1, title = '收藏')
      API.collectCase({ status: status, recommendId: item.designPlanRecommendId || item.planRecommendedId })
        .then(res => {
          if (res.success) {
            status == 0 ? this.data[type][index].collectNum -= 1 : this.data[type][index].collectNum += 1
            this.data[type][index].isFavorite = status
            this.setData({ [type]: this.data[type] })
            wx.showToast({ title: title + '成功' })
          } else {
            wx.showToast({ title: '收藏失败', icon: 'none' })
          }
          setTimeout(function () { that.setData({ collectRequest: true }) }, 500)
        })
    }
  },
  likeCase(e) { // 方案点赞
    let that = this,
      item = e.currentTarget.dataset.item,
      index = e.currentTarget.dataset.index,
      status = null,
      title = '点赞',
      type = e.currentTarget.dataset.type + 'List'
    if (that.data.favoriteRequest == true) {
      that.setData({ favoriteRequest: false })
      item.isLike ? (status = 0, title = '取消点赞') : (status = 1, title = '点赞')
      API.likeCase({ status: status, designId: item.designPlanRecommendId || item.planRecommendedId })
        .then(res => {
          if (res.success) {
            status == 0 ? this.data[type][index].likeNum -= 1 : this.data[type][index].likeNum += 1
            this.data[type][index].isLike = status
            this.setData({ [type]: this.data[type] })
            wx.showToast({ title: title + '成功' })
          } else {
            wx.showToast({ title: title + '失败', icon: 'none' })
          }
          setTimeout(function () { that.setData({ favoriteRequest: true }) }, 500)
        })
    }
  },
  putInMyhouse(e) { // 装进我家
    // this.renderTypeShow() // 显示组件
    let item = e.currentTarget.dataset.item
    wx.setStorageSync('caseItem', item)
    wx.navigateTo({
      url: '../../plan/case-house-type/case-house-type'
    })
  },
  selectTab(e) {
    const index = e.target.dataset.index;
    this.setData({
      tabIndex: index
    })
    const tabIdArrList = ['design-list', 'real-case-list', 'article-list', 'shop-list']
    let query = wx.createSelectorQuery()
    let scrollTop = this.data.scrollTop
    query.select(`#${tabIdArrList[index]}`).boundingClientRect((rect) => {
      if (rect) {
        let top = rect.top
        // 这里是关键
        wx.pageScrollTo({
          scrollTop: scrollTop + top,
          duration: 0
        })
      }
    }).exec()

  },
  getDetail: function () {
    API.getCompanyDetails({ shopId: this.data.targetId, platformValue: 2 }).then(res => { // 怎么加了平台标识
      if (res.code == 200) {
        if (!res.data) return;
        if (res.data.shopIntroduced != null) {
          res.data.shopIntroduced = res.data.shopIntroduced.replace(/\<img/gi, '<img style="width:97%;height:auto" ')
        }
        this.setData({ targetData: res.data, companyId: res.data.companyId })
        console.log('businessType:' + this.data.targetData.businessType)
        if (this.data.companyId != '') {
          this.getRecommendCaseList();
          this.getPlanNum();
          this.getActivity();
        }
        wx.setNavigationBarTitle({
          title: this.data.targetData.shopName
        })
        this.sellingPoint();
      }
    })
  },
  getActivity: function () {
    API.getCompanyDesignerList({ companyId: this.data.companyId, platformValue: 2 }).then(res => { // 增加平台标识
      if (res.code == 200) {
        let arr = [];
        if (res.data.length > 3) {
          for (let i = 0; i < 3; i++) {
            arr.push(res.data[i])
          }
          this.setData({ teamListShow: arr, teamList: res.data })
        } else {
          this.setData({ teamListShow: res.data, teamList: res.data })
        }

      }
    })
  },
  toggleFav: function () {

  },
  toggleAgree: function () {

  },
  getPlan: function () {
    var This = this;
    var tabCont = this.data.tabContList.plan;
    if (pageNo == 1) tabCont = []
    API.getInteractioncomment({
      pageNo: this.data.pageNoList.plan,
      pageSize: 10,
      type: 1,//1:设计师  2:设计方案 3:供求信息 4:企业 5:店铺
      objId: this.data.targetId
    })
      .then(res => {
        if (res.code == 200) {
          if (!res.data) return;
          tabCont = tabCont.concat(res.data.list)
          this.setData({ ["tabContList.plan"]: tabCont, ["pageNoList.plan"]: ++pageNo })
          if (tabCont.length == 0 || res.data.list.length < 10) {
            this.setData({ ["hasMore.plan"]: false })
          }
        } else {
          wx.showToast({ title: res.message || "网络错误 请稍后再试", icon: 'none' })
        }
      })
  },
  getModel: function () {
    let url = 'sandu/mini/CompanyDesigner/designerList?companyId=3101'
  },
  getSuccess: function () {
    let tabCont = this.data.tabContList.success;
    if (pageNo == 1) tabCont = []
    API.getInteractioncomment({
      pageNo: this.data.pageNoList.success,
      pageSize: 10,
      type: 1,//1:设计师  2:设计方案 3:供求信息 4:企业 5:店铺
      objId: this.data.targetId
    })
      .then(res => {
        if (res.code == 200) {
          if (!res.data) return;
          tabCont = tabCont.concat(res.data.list)
          this.setData({ ["tabContList.success"]: tabCont, ["pageNoList.success"]: ++pageNo })
          if (tabCont.length == 0 || res.data.list.length < 10) {
            this.setData({ ["hasMore.success"]: false })
          }
        } else {
          wx.showToast({ title: res.message || "网络错误 请稍后再试", icon: 'none' })
        }
      })
  },
  toggleDesigners: function () {
    this.setData({
      isFoldDesigner: this.data.isFoldDesigner ? false : true,
    })
  },
  getComment: function () {
    let tabCont = this.data.tabContList.comment;
    if (pageNo == 1) tabCont = []
    API.getInteractioncomment({
      pageNo: this.data.pageNoList.comment,
      pageSize: 10,
      type: 1,//1:设计师  2:设计方案 3:供求信息 4:企业 5:店铺
      objId: this.data.targetId
    })
      .then(res => {
        if (res.code == 200) {
          if (!res.data) return;
          tabCont = tabCont.concat(res.data.list)
          this.setData({ commentList: tabCont, ["pageNoList.comment"]: ++pageNo })
          if (tabCont.length == 0 || res.data.list.length < 10) {
            this.setData({ ["hasMore.comment"]: false })
          }
        } else {
          wx.showToast({ title: res.message || "网络错误 请稍后再试", icon: 'none' })
        }
      })
  },
  reloadTabList: function () {
    var currTab = this.data.currTab;
    if (currTab == 0 && this.hasMore.plan == true) {
      this.getPlan();
    } else if (currTab == 1 && this.hasMore.model == true) {
      this.getMoel();
    } else if (currTab == 2 && this.hasMore.success == true) {
      this.getSuccess();
    } else if (currTab == 3 && this.hasMore.comment == true) {
      this.getComment();
    }
  },
  toBack: function () {
    wx.navigateBack({
      delta: 1
    })
  },
  toThreeD(e) { // 调转到3D界面
    let type = e.currentTarget.dataset.type, item = e.currentTarget.dataset.item, routerQueryType = null, webUrl = this.data.sevenUrl
    if (type === 'video') {
      API.getRecommendedVideoId({
        planRecommendedId: item.planRecommendedId,
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
      let sevenObj = {
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 1,
        planId: item.planRecommendedId,
        routerQueryType: routerQueryType,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram'
      }
      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
      getApp().data.webUrl = webUrl
      $App.myNavigateBack('pages/web-720/web-720')
      console.log(webUrl)
    }
  },
  toVideo(url) {
    wx.navigateTo({
      url: '/pages/template/video/video?url=' + url
    })
  },
  toPlanList(e) {
    let type = e.currentTarget.dataset.type;
    let companyId = this.data.companyId
    wx.navigateTo({
      url: '/pages/decoration/planList/planList?type=' + type + '&companyId=' + companyId + '&shopId=' + this.data.targetId,
    })
  },
  toCaseList() {
    wx.navigateTo({
      url: '/pages/decoration/caseList/caseList?shopid=' + this.data.targetId,
    })
  },
  toCaseDetail(e) {
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/caseDetail/caseDetail?id=' + id,
    })
  },
  toTeam() {
    wx.navigateTo({
      url: '/pages/decoration/teamList/teamList?companyId=' + this.data.companyId,
    })
  },
  openMap() {
    let data = this.data.targetData
    var address = data.provinceName + data.cityName + data.areaName + data.shopAddress
    wx.openLocation({
      latitude: data.latitude,
      longitude: data.longitude,
      name: data.shopName,
      address: address
    })

  },
  toArtList() {
    wx.navigateTo({
      url: '/pages/artList/artList?shopid=' + this.data.targetId,
    })
  },
  toArticles(e) {
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/artDetail/artDetail?id=' + id,
    })
  },
  toTeamDetail(e) {
    let id = e.currentTarget.dataset.id
    if (id == undefined || id == null) {
      wx.showToast({
        title: '该设计师未创建店铺',
        icon: 'none',
        duration: 500
      })
    } else {
      wx.navigateTo({
        url: '/pages/decoration/companyDetail/companyDetail?id=' + id,
      })
    }
  }
})