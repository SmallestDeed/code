// pages/house-case/house-case.js
let fetch = getApp().fetch, myForEach = getApp().myForEach
let $App = getApp()
// import { renderTypeExample } from '../../component/render-type/render-type'
Page({
  // renderTypeExample,

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    imageArray: ['https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea', 'https://homesiteres.zbjimg.com/homesite%2Fres%2F300X250.jpg%2Forigine%2F4ef2b90d-f4a3-4589-abef-ce2104fa65ea'],
    spaceList: [],
    areaList: [],
    styleList: [],
    conditionActive: -1,
    childConditionActive: [0,-1,-1],
    recommendCaseList: [],
    resourcePath: getApp().resourcePath,
    sevenUrl: getApp().sevenUrl,
    pageSize: 10,
      designRecommendedStyleName:'',
    getCaseParams: {
      spaceType: '',
      designPlanStyleId: '',
      spaceArea: ''
    },
    totalCount: 0,
    favoriteRequest: true,
    collectRequest: true,
  },
  onLoad: function (options) {
    new $App.quickNavigation() // 注册组件
    this.getConditionMetadata() // 获取方案筛选条件
    // new this.renderTypeExample() // 注册组件
  },

  onShow: function () {
      this.getRecommendCaseList({});
  },
  onPullDownRefresh: function () {
  
  },
  onReachBottom: function () {
  
  },
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
    let url = `/mobile/designPlanRecommended/getRecommendedPictureList.htm`,
        item = e.currentTarget.dataset.item,
        type = e.currentTarget.dataset.type
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
        this.setData({
          spaceList: res.obj,
          areaList: res.obj[0].designPlanAreaList,
          styleList: res.obj[0].designPlanStyleVoList
        })
        this.setData({
          'getCaseParams.spaceType': res.obj[0].houseType
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
  },  
  chooseCondition(e) { // 选择筛选条件
    let indexP = e.currentTarget.dataset.indexp, indexC = e.currentTarget.dataset.indexc,
        name = e.currentTarget.dataset.name;
    if (indexP === 0) {
      this.data.childConditionActive[1] = -1
      this.data.childConditionActive[2] = -1      
      this.setData({
        areaList: this.data.spaceList[indexC].designPlanAreaList,
        styleList: this.data.spaceList[indexC].designPlanStyleVoList,
        childConditionActive: this.data.childConditionActive,
          designRecommendedStyleName: ''
      })
    }
    this.data.childConditionActive[indexP] = indexC
    this.setData({
      childConditionActive: this.data.childConditionActive,
      'getCaseParams.spaceType': this.data.spaceList[this.data.childConditionActive[0]].houseType,
      'getCaseParams.designPlanStyleId': this.data.childConditionActive[2]== -1? '' : this.data.styleList[this.data.childConditionActive[2]].styleCode,
        designRecommendedStyleName: name,
      'getCaseParams.spaceArea': this.data.childConditionActive[1] == -1? '': this.data.areaList[this.data.childConditionActive[1]].areaId
    })
    this.getRecommendCaseList(this.data.getCaseParams)
  },
  closeCaseCondition() {
    this.setData({
      conditionActive: -1
    })
  },
  getRecommendCaseList(obj) { // 获取方案列表
      let url = 'fullsearch-app/collect/recommendationplan/search/list'
      let data = {
          "recommendationPlanSearchVo": {
              "houseType": obj.spaceType || 3,
              "designStyleId": obj.designPlanStyleId || '',
              "spaceArea": obj.spaceArea || '',
          },
          "pageVo": {
              "start": 0,
              "pageSize": this.data.pageSize
          }
      }
    //   {
    //       curPage: 1,
    //           pageSize: this.data.pageSize,
    //               designRecommendedStyleName: this.data.designRecommendedStyleName || '',
    //                   spaceType: obj.spaceType || 3,
    //                       designPlanStyleId: obj.designPlanStyleId || '',
    //                           spaceArea: obj.spaceArea || '',
    //                               displayType: 'decorate',
    //                                   isSortByReleaseTime: 0,
    // }
    // let url = `/designplanfavorite/list`
      fetch(url, 'post', data,'fullsearch')
    .then(res => {
        let flag = res.success && res.datalist ? true:false;
          this.setData({
              recommendCaseList: flag ? res.datalist:[],
              totalCount: flag?res.totalCount:0
          })
    }) 
  },
  onReachBottom() { // 底部
    if (this.data.pageSize >= this.data.totalCount) {
      return
    } else {
      this.setData({
        pageSize: this.data.pageSize + 10
      })
      this.getRecommendCaseList(this.data.getCaseParams)
    }
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
  var that=this

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
            title: title + '成功',
            duration: 2000,
            complete: function () {

              setTimeout(function () {
                //要延时执行的代码
                that.setData({
                  childConditionActive:[0,-1,-1]
                })
                that.getConditionMetadata()
              }, 2000) //延迟时间
            }
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
  }else{
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
    }else{
      return;
    }
  },
  goRenderBtn(){
wx.navigateTo({
  url: '/pages/house-case/house-case',
})
  }
})