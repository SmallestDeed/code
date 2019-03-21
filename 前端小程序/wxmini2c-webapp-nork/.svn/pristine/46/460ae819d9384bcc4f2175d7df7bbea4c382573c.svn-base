// pages/house-case/house-case.js
let fetch = getApp().fetch, myForEach = getApp().myForEach, mySplitUrl = getApp().mySplitUrl,
  myCompoundUrl = getApp().myCompoundUrl, $App = getApp(), API = getApp().API
import {shareTitle} from '../../utils/config.js';
import { emptyTemplate } from '../../component/emptyTemplate/emptyTemplate'
let ttt = '';
Page({
  emptyTemplate, // 无数据组件
  /**
   * 页面的初始数据
   */
  data: {
    scrollTop: 0,
    floorstatus: false,
    spaceList: [],
    areaList: [],
    styleList: [],
    types: 0,
      names:'',
    favoriteRequest: true,
    collectRequest: true,
    conditionActive: -1,
    childConditionActive: [0, -1, -1],
    recommendCaseList: [],
    areaId:'',
    styleCode:'',
    oneAreaId:'',
    resourcePath: getApp().resourcePath,
    staticImageUrl:getApp().staticImageUrl,
    sevenUrl: getApp().sevenUrl,
    pageSize: 5,
    getCaseParams: {
      spaceType: '',
      styleName:'',
      designPlanStyleId: '',
      spaceArea: ''
    },
    isShow: true,
    totalCount: 0,
    caseListheight: '',
    caseListOverflow: 'none',
    scroolLeft: 0,
    lastTap1: "",
    lastTap1: "",
    fitmentWayList: [],
    fitmentWayActive: 0,
    fitmentWayChildActive: 0,
    spaceFlag:false,
    styleFlag:false,
    styleNameNow:'全部风格',
    spaceNameNow:'全部空间'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getDesignplanconditionmetadata() // 获取空间
    this.isSevenShare(options) // 是否720分享
    this.getConditionMetadata() // 获取方案筛选条件
    this.getFitmentQuote() // 获取专修报价筛选条件
    new this.emptyTemplate() // 注册组件
    this.posterSharePanorama(options) // 获取海报分享生成720
  },
  posterSharePanorama(options) {
    console.log(options,'sss')
    if (options.scene) {
      API.getJsonData({ id: options.scene }).then(res => {
        getApp().data.webUrl = myCompoundUrl(JSON.parse(res.obj.jsonData))
        wx.navigateTo({ url: '/pages/web-720/web-720' })
      })
    }
  },
  getFitmentQuote() { // 获取装修报价筛选条件
    let url = '/decoratePrice/getDecoratePriceType'
    fetch(url , 'get')
    .then(res => {
      let arr = []
      if (res.code === 200) {
        arr = res.list
        arr.unshift({
          name: '全部',
          sonList: [],
          value: ''
        })
        myForEach(arr, (value) => {
          value.sonList.unshift({
            name: '全部',
            sonList: [],
            value: ''
          })
        })
      }
      this.setData({
        fitmentWayList: arr
      })
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
    let url = `/designplan/designplanconditionmetadata`
    fetch(url, 'get')
      .then(res => {
        myForEach(res.obj[0].designPlanAreaList, (value) => {
          value.areaName = value.areaName.replace('~', '-')
        })
        let objAll={
          styleCode:0,
          styleName:"全部风格"
        }
        res.obj[0].designPlanStyleVoList.splice(0,0,objAll)
        this.setData({
          spaceList: res.obj,
          areaList: res.obj[0].designPlanAreaList,
          oneAreaId: '',
          styleList: res.obj[0].designPlanStyleVoList,
          'getCaseParams.spaceType': '',
          'getCaseParams.styleName':''
        })
        this.getRecommendCaseList(this.data.getCaseParams)
      })
  },
  resetFun: function(){
    let oneAreaId = this.data.oneAreaId;
    this.setData({
      areaId: '',
      styleCode: '',
      'getCaseParams.designPlanStyleId': '',
      'getCaseParams.spaceArea': '',
      fitmentWayChildActive: 0,
      fitmentWayActive: 0,
    })
    //this.getRecommendCaseList();
  },
  submitFun: function(){
    this.setData({ isShow: true });
    let obj = {};
    obj.spaceType = this.data.types || '';
      obj.styleName = this.data.names||''
    obj.designPlanStyleId = this.data.styleCode || '';
    obj.spaceArea = this.data.areaId|| '';
    this.getRecommendCaseList(this.data.getCaseParams);
  },
  
  shouMoreFun: function(){
    this.data.isShow ? this.setData({ isShow: false }) : this.setData({ isShow: true });
  },
  areaIdFun: function (e) {
    let info = e.currentTarget.dataset.info || '',
        tap = this.data.lastTap1
    let str = info==tap?'':info;
    this.setData({
      areaId: str,
      'getCaseParams.spaceArea': str,
      lastTap1: str
    })
    
  },
  styleCodeFun: function (e) {
    let info = e.currentTarget.dataset.info || '',
      tap = this.data.lastTap2,
      name = e.currentTarget.dataset.name
    let str = info == tap ? '' : info;

    this.setData({
      styleCode: str,
      'getCaseParams.designPlanStyleId': str,
      lastTap2: str,
      styleNameNow: name
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
      return $App.shareAppMessageFn(true);
    }
  },
  enlargeImage(url) { // 查看大图
    wx.previewImage({
      current: url, // 当前显示图片的http链接
      urls: [url] // 需要预览的图片http链接列表
    })
  },
  formToThreeD(e) { // 720
    let type = e.detail.target.dataset.type, item = e.detail.target.dataset.item, routerQueryType = '',
      formId = e.detail.formId
    if (type === '720' || type === 'roam') {
      let routerQueryType = ''
      if (type === '720') {
        routerQueryType = 'seven'
      } else {
        routerQueryType = 'roam'
      }
      let sevenObj = {
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        operationSource: 1,
        planId: item.designPlanRecommendId,
        routerQueryType: routerQueryType,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram',
        formId: formId
      }
      let webUrl = this.data.sevenUrl
      for (let key in sevenObj) {
        webUrl += key + '=' + sevenObj[key] + '&'
      }
      getApp().data.webUrl = webUrl
      console.log(webUrl)
      $App.myNavigateBack('pages/web-720/web-720')
      return
    } 
  },
  toThreeD(e) { // 调转到3D界面
    let type = e.currentTarget.dataset.type, item = e.currentTarget.dataset.item
    let url = `/mobile/designPlanRecommended/getRecommendedPictureList.htm`
    fetch(url, 'post', {
      planRecommendedId: item.designPlanRecommendId,
      remark: type
    }, 'render')
      .then(res => {
        if (res.success) {
          if (type == 'photo') {
            return res.datalist[0].pid
          } else {
            return res.datalist[0].id
          }
        } else {
          return false
        }
      })
      .then(res => {
        if (res) {
          let url = `/mobile/design/designPlan/getPanoPicture.htm`
          fetch(url, 'post', {
            thumbId: res
          }, 'render')
            .then(res => {
              if (res.success) {
                if (type == 'photo') {
                  this.enlargeImage(res.obj.url)
                } else if (type == 'video') {
                  this.toVideo(res.obj.url)
                }
              } else {
                wx.showToast({
                  title: '打开失败',
                  icon: 'none'
                })
              }
            })
        }
      })
    // getApp().data.webUrl = 'https://zuoyou.m.sanduspace.com'
    // wx.navigateTo({
    //   url: '../web-720/web-720',
    // })
  },
  getConditionMetadata() { // 获取方案筛选条件
    let url = `/designplan/designplanconditionmetadata`
    fetch(url, 'get')
      .then(res => {
        if (res.status) {
          myForEach(res.obj[0].designPlanAreaList, (value) => {
            value.areaName = value.areaName.replace('~', '-')
          })
          let objAll = {
            styleCode: 0,
            styleName: "全部风格"
          }
          
          for (let i = 0; i < res.obj.length;i++){
            res.obj[i].designPlanStyleVoList.splice(0, 0, objAll)
          }
          let objA = {
            houseType: 0,
            houseName: "全部空间",
            designPlanStyleVoList: [{
              styleCode: 0,
              styleName: "全部风格"
            }]
          }
          res.obj.splice(0, 0, objA)
          
          this.setData({
            spaceList: res.obj,
            areaList: res.obj[0].designPlanAreaList,
            styleList: res.obj[0].designPlanStyleVoList
          })
          
       
          this.setData({
            'getCaseParams.spaceType': ttt
          })
          this.getRecommendCaseList(this.data.getCaseParams)
        } else {
          this.setData({
            spaceList: [],
            areaList: [],
            styleList: []
          })
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
        styleName:e.currentTarget.dataset.name,
        childConditionActive: this.data.childConditionActive
      })
    }
    this.data.childConditionActive[indexP] = indexC
     
    this.setData({
      childConditionActive: this.data.childConditionActive,
      'getCaseParams.spaceType': this.data.spaceList[this.data.childConditionActive[0]].houseType,
        'getCaseParams.styleName': this.data.spaceList[this.data.childConditionActive[0]].houseName,
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
        styleName: o ? o.styleName:'',
      designPlanStyleId: o ? o.designPlanStyleId : '',
      spaceArea: o ? o.spaceArea : '',
      decoratePriceType: this.data.fitmentWayActive == 0 ? '' :this.data.fitmentWayList[this.data.fitmentWayActive].value,
      decoratePriceRange: this.data.fitmentWayChildActive == 0 ? '' : this.data.fitmentWayList[this.data.fitmentWayActive].sonList[this.data.fitmentWayChildActive].value
    }
      let spaceList =  this.data.spaceList;
    //   let styleName = '';
    // console.log(spaceList,"spaceList")
    //   for(let i = 0;i<spaceList.length;i++){
    //       if (spaceList[i].houseType == obj.spaceType){

    //           for (let j = 0; j < spaceList[i].designPlanStyleVoList.length;j++){
    //               if (obj.designPlanStyleId == spaceList[i].designPlanStyleVoList[j].styleCode){
    //                   styleName = spaceList[i].designPlanStyleVoList[j].styleName;
    //               }
    //           }
    //       }
    //   }
      //new fullsearch   fullsearch-app/all/recommendationplan/search/mini/list  post
      //old /designplan/designplanrecommendedlist  get
      let url = `fullsearch-app/all/recommendationplan/search/mini/list`
    let data = {};
    data = {
        "recommendationPlanSearchVo": {
            "displayType":"decorate",
            "houseType": obj.spaceType || '',
            "spaceArea": obj.spaceArea || '',
            "designStyleId": obj.designPlanStyleId || '',
            "decoratePriceType": obj.decoratePriceType,
            "decoratePriceRange": obj.decoratePriceRange,
	    },
        "pageVo": {
            "start": 0,
            "pageSize": this.data.pageSize
        }
	}
    // data = {
    //     curPage: 1,
    //     pageSize: this.data.pageSize,
    //     spaceType: obj.spaceType || '',
    //     designRecommendedStyleName: styleName || '',
    //     designPlanStyleId: obj.designPlanStyleId || '',
    //     spaceArea: obj.spaceArea || '',
    //     displayType: 'decorate',
    //     isSortByReleaseTime: 0,
    //     decoratePriceType: obj.decoratePriceType,
    //     decoratePriceRange: obj.decoratePriceRange
    // }
      fetch(url, 'post', data,'fullsearch')
      .then(res => {
          console.log(res);
          if (res.success) {
            if (res.datalist) {
            this.setData({
                recommendCaseList: res.datalist,
              totalCount: res.totalCount
            })
            this.emptyTemplateShow('hide')
          } else {
            this.emptyTemplateShow('show')
            this.setData({
              recommendCaseList: [],
              totalCount: 0
            })
          }
        } else {
          this.emptyTemplateShow('show')
          this.setData({
            recommendCaseList: [],
            totalCount: 0
          })
        }
      })
  },

  toVideo(url) {
    wx.navigateTo({
      url: '../video/video?url=' + url
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
    var that = this
    if (that.data.collectRequest == true) {
      that.setData({
        collectRequest: false
      })
      let url = `/designplanfavorite/setFavoriteOrUnfavorite`,
        item = e.currentTarget.dataset.item,
        index = e.currentTarget.dataset.index,
        status = null,
        title = '收藏'
      if (item.isFavorite) {
        status = 0
      } else {
        status = 1
      }
      status == 0 ? title = '取消收藏' : '收藏'
      fetch(url, 'post', {
        status: status,
        recommendId: item.designPlanRecommendId
      })
        .then((res) => {

          if (res.success) {
            this.data.recommendCaseList[index].isFavorite = status
            status == 0 ? this.data.recommendCaseList[index].collectNum -= 1 : this.data.recommendCaseList[index].collectNum += 1
            this.setData({
              recommendCaseList: this.data.recommendCaseList
            })
            wx.showToast({
              title: title + '成功'
            })
            setTimeout(function () {
              that.setData({
                collectRequest: true
              })
            }, 500)

          } else {
            wx.showToast({
              title: '收藏失败',
              icon: 'none'
            })
            setTimeout(function () {
              that.setData({
                collectRequest: true
              })
            }, 500)
          }
        })
    } else {
      return
    }

  },
  likeCase(e) { // 方案点赞
    var that = this;
    if (that.data.favoriteRequest == true) {
      that.setData({
        favoriteRequest: false
      })
      let url = `/designPlanLike/setLikeOrDislike`,
        item = e.currentTarget.dataset.item,
        index = e.currentTarget.dataset.index,
        status = null,
        title = '点赞'
      if (item.isLike) {
        status = 0
      } else {
        status = 1
      }
      status == 0 ? title = '取消点赞' : '点赞'
      fetch(url, 'post', {
        status: status,
        designId: item.designPlanRecommendId
      })
        .then((res) => {
          if (res.success) {
            this.data.recommendCaseList[index].isLike = status
            status == 0 ? this.data.recommendCaseList[index].likeNum -= 1 : this.data.recommendCaseList[index].likeNum += 1
            this.setData({
              recommendCaseList: this.data.recommendCaseList
            })
            wx.showToast({
              title: title + '成功'
            })
            setTimeout(function () {
              that.setData({
                favoriteRequest: true
              })
            }, 500)
          } else {
            wx.showToast({
              title: title + '失败',
              icon: 'none'
            })
            setTimeout(function () {
              that.setData({
                favoriteRequest: true
              })
            }, 500)
          }
        })
    } else {
      return;
    }
  },
  stylePick(){
    
    this.setData({
      styleFlag: !this.data.styleFlag,
      spaceFlag:false
    })
  },
  spacePick(){
    this.setData({
      spaceFlag: !this.data.spaceFlag,
      styleFlag:false
    })
  },
  pickStyle(e){
    console.log("1")
    let info=e.currentTarget.dataset.info,
        name = e.currentTarget.dataset.name
    if(info===0){
      this.setData({
        'getCaseParams.styleName':'',
        'getCaseParams.designPlanStyleId':'',
        styleFlag:false,
        styleNameNow: name
      })
    }else{
      this.setData({
        'getCaseParams.styleName': name,
        'getCaseParams.designPlanStyleId': info,
        styleFlag: false,
        styleNameNow: name
      })
    }    
    this.getRecommendCaseList(this.data.getCaseParams)
  },
pickSpace(e) {

    let info = e.currentTarget.dataset.info,
      name = e.currentTarget.dataset.name,
      index = e.currentTarget.dataset.index
    
      this.setData({
        'getCaseParams.spaceType': info,
        spaceFlag: false,
        spaceNameNow: name,
        styleList: this.data.spaceList[index].designPlanStyleVoList,
        'getCaseParams.styleName':'',
        'getCaseParams.designPlanStyleId': '',
        styleNameNow: '全部风格'
      })
    

    this.getRecommendCaseList(this.data.getCaseParams)
  }
}) 