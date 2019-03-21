let myForEach = getApp().myForEach,
  $App = getApp(),
  API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: getApp().resourcePath,
    productListImgs: [],
    mername: "",
    curPage: 1,
    pageSize: 10,
    livingId: "",
    contentlist: [],
    type: '',
    status: '',
    houseId:'',
    showHouseTypeBoot: false,
    totalCount: 0,
    houseId:'',
    collectRequest: true,
    staticImageUrl: getApp().staticImageUrl,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu();
    this.sellingPoint();
    new $App.newNav() // 注册快速导航组件
    // 注册组件
    var that = this;
    that.setData({
      mername: options.name,
      livingId: options.id,
      type: options.type,
      region: options.region
    })
    wx.setNavigationBarTitle({
      title: that.data.mername //页面标题为路由参数
    })
    that.getSearchResluts("加载数据中")

    setTimeout(()=>{
      that.setData({
        showHouseTypeBoot: true
      })
    },1000)
  },

  getSearchResluts: function(message) {
    let that = this
    API.getbasehouselist({
        livingId: that.data.livingId,
        pageSize: that.data.pageSize,
        curPage: that.data.curPage
      })
      .then(res => {
        let contentlistTem = that.data.contentlist,
          productImageTem = that.data.productListImgs;
        if (res.success) {
          that.setData({
            totalCount: res.totalCount
          })
          that.data.curPage == 1 ? (contentlistTem = [], productImageTem = []) : ''
          let productListImgs = [],
            contentlist = res.obj;
          for (let i = 0; i < contentlist.length; i++) {
            productListImgs.push(that.data.resourcePath + "/" + contentlist[i].largeThumbnailPath)
          }
          that.setData({
            contentlist: contentlistTem.concat(contentlist),
            productListImgs: productImageTem.concat(productListImgs),
            hasMoreData: contentlist.length >= that.data.pageSize
          })
          contentlist.length >= that.data.pageSize ? this.setData({
            curPage: that.data.curPage + 1
          }) : ''
        } else {
          wx.showToast({
            title: res.message,
            icon: 'none'
          })
        }
      })
  },
  routerToUploadHouse() { // 上传户型
    let region = this.data.region, livingName = this.data.mername
    wx.navigateTo({
      url: '/pages/mine/upload-house/upload-house?region=' + region + '&livingName=' + livingName,
    })
    this.sellingPoint('boot-but');  
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

    if (this.data.hasMoreData) {
      this.getSearchResluts('加载更多数据')
    } else {
      wx.showToast({
        title: '没有更多数据',
        icon: 'none'
      })
    }

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function(res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
    if (res.from === 'button') {
      let item = res.target.dataset.item,
        imgUrl = this.data.resourcePath + item.thumbnailPath
      console.log(imgUrl)
      let shareObj = {
        title: item.houseCommonCode,
        path: '/pages/home/home?shareType=active&url=' + encodeURIComponent(`/pages/house-type/house-details/house-details?houseId=${item.id}&type=search`),
        imageUrl: imgUrl,
        success: function(res) {
          // 转发成功
        },
        fail: function(res) {
          // 转发失败
        }

      }
      return shareObj;
    }
  },
  previewImage: function(e) {
    var that = this,
      //获取当前图片的下表
      index = e.currentTarget.dataset.index,
      //数据源
      productListImgs = this.data.productListImgs;
    var url = productListImgs[index]
    wx.previewImage({
      //当前显示下表
      current: productListImgs[index],
      //数据源
      urls: [url]
    })
  },
  sellingPoint(event) {
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route
    API.sellingPoint({
      uid: wx.getStorageSync('openId'),
      cp: nowPath,
      lp: previousPath,
      e: event ? event : '',
      pt: this.data.mername
    })
  },
  toHouseDetail(e) { // 检验是否合适
    let item = e.currentTarget.dataset.item
    this.setData({
      houseId: item.id
    })
    this.sellingPoint('finish-btn');
    let caseItem = wx.getStorageSync('caseItem')
    if (this.data.type == "search") {
      wx.navigateTo({
        url: '/pages/house-type/house-details/house-details?houseId=' + item.id + '&type=search',
      })
    }
    if (this.data.type == "plan") {
      if (caseItem.spaceType == 13 || caseItem.spaceFunctionId == 13) {
        let obj = {
          templateId: ''
        }
        wx.setStorageSync('matchData', obj)
        wx.navigateTo({
          url: '/pages/house-type/house-details/house-details?type=plan&matchState=3&houseId=' + item.id
        })
        return;
      } else {
        API.matchPlanHouse({
            houseId: item.id,
            recommendedPlanId: caseItem.designPlanRecommendId || caseItem.planRecommendedId
          })
          .then(res => {

            if (res.obj.status == 1) {
              this.setData({
                status: 1,
                icon: 'none'
              })
            } else if (res.obj.status == 2) {
              this.setData({
                status: 2
              })
            } else {
              wx.setStorageSync('matchData', res.obj)
              wx.navigateTo({
                url: '/pages/house-type/house-details/house-details?type=plan&matchState=' + res.obj.status + '&houseId=' + item.id
              })
            }
          })

      }
    }

  },
  planImproper(e) {
    let item = e.currentTarget.dataset.item
    let options = e.currentTarget.dataset.options
    if (options == 1) {
      this.setData({
        status: ''
      })
    } else {
      wx.navigateTo({
        url: '/pages/house-type/house-details/house-details?type=search&houseId=' + this.data.houseId,
      })
    }
  },
  goToMessage(e) {
    let item = e.currentTarget.dataset.item
    let type = 'house'
    wx.navigateTo({
      url: '/pages/publishMessage/publishMessage?' + 'imgUrl=' + item.largeThumbnailPath + '&id=' + item.id + '&type=' + type
    })
  },
  /*收藏*/
  goCollect(e) {
    let that = this,
      index = e.currentTarget.dataset.index,
      item = e.currentTarget.dataset.item,
      status = null,
      title = '收藏'
    if (that.data.collectRequest == true) {
      this.setData({
        collectRequest: false
      })
      item.isFavorite ? (status = 0, title = '取消收藏') : (status = 1, title = '收藏')
      API.getSearchHouseCollect({
        contentId: item.id,
        nodeType: 3,
        detailType: 1,
        status: status
      }).
      then(res => {
        if (res.status) {
          status == 0 ? this.data.contentlist[index].favoriteNum -= 1 : this.data.contentlist[index].favoriteNum += 1
          this.data.contentlist[index].isFavorite = status
          this.setData({
            contentlist: this.data.contentlist
          })
          wx.showToast({ title: title + '成功' })
        } else {
          wx.showToast({ title: '收藏失败', icon: 'none' })
        }
        setTimeout(function () { that.setData({ collectRequest: true }) }, 500)
      })
    }
  },
})