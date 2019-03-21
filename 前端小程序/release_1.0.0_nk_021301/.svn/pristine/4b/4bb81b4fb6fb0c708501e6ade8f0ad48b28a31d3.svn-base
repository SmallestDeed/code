// pages/web-detail-complicated/web-detail-complicated.js
import fetch from '../../utils/fetch.js'
import {
    emptyTemplate
} from '../../component/emptyTemplate/emptyTemplate'
let $App = getApp();
let opt = {};
var WxParse = require('../../utils/wxParse/wxParse.js');
var { shareTitle } = require('../../utils/config.js');
var shareTitle = { shareTitle }.shareTitle;
let API = getApp().API
Page({
    emptyTemplate, // 无数据组件
    data: {
        type: 'plan',
        shopid: '',
        List: {},
        selectNumber: 1,
        favoriteRequest: true,
        isNk:true,
        staticImageUrl: getApp().staticImageUrl,
        collectRequest: true,
        recommendCaseList: [],
        resourcePath: getApp().resourcePath,
        sevenUrl: getApp().sevenUrl,
        pageSize: 5,
        getCaseParams: {
            spaceType: '',
            designPlanStyleId: '',
            spaceArea: ''
        },
        totalCount: 0,
        caseListheight: '',
        caseListOverflow: 'none',
        shopList: {},
        richtxt: '',
        casePageSize: 5,
        caseList: [],
        cityName: '',
        longitude: '',
        latitude: '',
        citys: '',
        distance: '',
        shoptype: 0,
        showFlag: false,
        planPageSize:5,
        planCount:'',
        planList:[]
    },
    onLoad: function(options) {
        new $App.quickNavigation()
        this.setData({
            shopid: options.id,
            distance: this.setNum(options.distance),
            longitude: options.longitude ? options.longitude : '',
            latitude: options.latitude ?  options.latitude : '',
            cityName: options.name,
            shoptype: options.shoptype,
            showFlag: options.shoptype!=1,
        })
        console.log(options);
        console.log(this.data.shoptype);
        opt = options;
        this.getDetail();
        new this.emptyTemplate() // 注册组件；
        this.getCase()
        
    },
    setNum:function(e){
        if (!e || e == 'null' | e =='undefined')
            return ''
        let num = parseFloat(e);
        return num > 999 ? num.toFixed(1) : num.toFixed(1);
    },
    goLocation: function () {
        if ((this.data.latitude != 'null' && this.data.latitude) && (this.data.longitude != 'null' && this.data.longitude)){
            wx.openLocation({
                latitude: parseFloat(this.data.latitude),
                longitude: parseFloat(this.data.longitude),
                name: this.data.cityName,
                address: this.data.citys
            })
        }else{
            wx.showToast({
                title: '未获取该门店经纬度',
                duration: 1000,
                icon: 'none'
            })
        }
    },
    getDetail() {
        let flag = this.data.shoptype!=1
        console.log(flag);
        let url = '/v1/sandu/mini/companyshop' +(flag ? '' :'/offline')+'/detail';
        fetch(url, 'get', {
            shopId: this.data.shopid,
            platformValue:1
            }, 'shop')
            .then((res) => {
                console.log(res);
                if (res.code == 200) {
                    let datass = res.data.shopIntroduced  ||  ''
                    let shopIntroduced = datass.indexOf('<img') !=-1||datass.replace(/<[^>]+>/g, "").trim() ? datass : ""
                    console.log(datass.indexOf('<img') != -1, datass.replace(/<[^>]+>/g, "").trim());
                    this.setData({
                        List: res.data,
                        citys: res.data.provinceName + res.data.cityName + res.data.areaName + res.data.shopAddress,
                        richtxt: shopIntroduced
                    })
                }
                WxParse.wxParse('article', 'html', this.data.richtxt, this, 0);
            })
    },
    getCase() {
        let url = '/v1/sandu/mini/companyshop/projectCaseList';
        fetch(url, 'get', {
                pageNo: 1,
                pageSize: this.data.casePageSize,
                shopId: this.data.shopid
            }, 'shop')
            .then((res) => {
                if (res.success) {
                    this.setData({
                        caseList: res.datalist
                    })
                }
            })
    },
    onShow: function() {
      this.getPlan();
    },
    getPlan(){
      let url = '/fullsearch-app/all/recommendationplan/search/mini/list';
      let recommendationPlanSearchVo = {
        companyId: wx.getStorageSync('companyId'),
        shopId: this.data.shopid,
        displayType: 'decorate',
        enterType: 'shop'
      }
      let pageVo = {
        pageSize: this.data.planPageSize,
        start: 1,
      }
      let parms = {
        "recommendationPlanSearchVo": recommendationPlanSearchVo,
        "pageVo": pageVo
      }
      fetch(url, 'post', parms, 'fullsearch')
        .then((res) => {
          if (res.success) {
            this.setData({
              planList: res.datalist,
              planCount: res.totalCount
            })
          }
        })

    
    },
    onReachBottom: function() {
    },

    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(false);
        }
    },
    changeplan(e) {
        let type = e.currentTarget.dataset.type,
            num = e.currentTarget.dataset.num
        if (num == this.data.selectNumber) {
            return
        } else {
            this.setData({
                type: type,
                selectNumber: num,
                pageSize: 5
            })
        }

    },
    call(e) {
        let phone = e.currentTarget.dataset.phone
        wx.makePhoneCall({
            phoneNumber: phone,
        })
    },
    toWorkCase(e) {
        let id = e.currentTarget.dataset.id
        wx.navigateTo({
            url: '/pages/work-case/work-case?id=' + id,
        })
    },
    toList(){
        wx.navigateTo({
            url: '/pages/engineList/engineList?id=' + this.data.shopid,
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
  formToThreeD(e) {
    let type = e.detail.target.dataset.type, item = e.detail.target.dataset.item, routerQueryType = '',
      formId = e.detail.formId, webUrl = null, sevenObj = null
    type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
    item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
      (webUrl = $App.sevenUrl, sevenObj = {
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 1,
        planId: item.designPlanRecommendId || item.planRecommendedId,
        routerQueryType: routerQueryType,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram',
        formId: formId
      })
    for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
    getApp().data.webUrl = webUrl
    $App.myNavigateBack('pages/web-720/web-720')
    console.log(webUrl)
  },
  toVideo(url) {
    wx.navigateTo({
      url: '/pages/video/video?url=' + url
    })
  },
  toThreeD(e) { // 调转到3D界面
    let type = e.currentTarget.dataset.type, item = e.currentTarget.dataset.item, routerQueryType = null, webUrl = null, sevenObj = null
    if (type === 'video') {
      API.getRecommendedVideoId({
        planRecommendedId: item.designPlanRecommendId || item.planRecommendedId,
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
        (webUrl = $App.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 1,
          planId: item.designPlanRecommendId || item.planRecommendedId,
          routerQueryType: routerQueryType,
          customReferer: $App.wxUrl,
          platformNewCode: 'miniProgram'
        })
      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
      getApp().data.webUrl = webUrl
      $App.myNavigateBack('pages/web-720/web-720')
      console.log(webUrl)
    }
  },
  collectCase(e) { // 方案收藏
    let index = e.currentTarget.dataset.index
    this.collectOrLikeCase({
      title: '收藏',
      flag: 'collectRequest',
      num: 'collectNum',
      status: 'isFavorite',
      api: 'collectCase',
      param: 'recommendId',
      index,
      designPlanType: 'designPlanType'
    })
  },
  likeCase(e) { // 方案点赞
    let index = e.currentTarget.dataset.index
    this.collectOrLikeCase({
      title: '点赞',
      flag: 'favoriteRequest',
      num: 'likeNum',
      status: 'isLike',
      api: 'likeCase',
      param: 'designId',
      index,
      designPlanType: 'designPlanType'
    })
  },
  collectOrLikeCase(obj) {
    let that = this, status = null, title = null
    console.log(obj)
    if (this.data[obj.flag] == true) {
      this.setData({ [obj.flag]: false })
      this.data.planList[obj.index][obj.status] ? (status = 0, title = '取消' + obj.title) : (status = 1, title = obj.title)
      API[obj.api]({
        status: status,
        [obj.param]: this.data.planList[obj.index].designPlanRecommendId || this.data.planList[obj.index].planRecommendedId,
        [obj.designPlanType]: this.data.planList[obj.index].spaceType == 13 ? 2 : 1
      })
        .then(res => {
          if (res.success) {
            status == 0 ? this.data.planList[obj.index][obj.num] -= 1 : this.data.planList[obj.index][obj.num] += 1
            this.data.planList[obj.index][obj.status] = status
            this.setData({ planList: this.data.planList })
            wx.showToast({ title: title + '成功' })
          } else {
            wx.showToast({ title: title + '失败', icon: 'none' })
          }
          setTimeout(function () { that.setData({ [obj.flag]: true }) }, 500)
        })
    }
  },
  routerToCaseDetails(e) {
    let id = e.currentTarget.dataset.id, type = e.currentTarget.dataset.type
    wx.navigateTo({ url: `/pages/case-details/case-details?id=${id}&type=${type || 0}` })
  },
  toPlanList(){
    console.log(this.data.shopid)
    wx.navigateTo({
      url: "/pages/planList/planList?shopId=" + this.data.shopid,
    })
  }
})