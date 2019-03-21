let API = getApp().API
let $App = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: getApp().resourcePath,
    curPage: 1,
    pageSize: 5,
    spaceFunctionId: "",
    houseId: "",
    isSort: 0,
    contentlist: [],
    imgList: [],
    uncontentlist: [],
    message: "加载中",
    userAlreadyBoughtHouseCount: "",
    userValidHouseCount: "",
    resourcePath: getApp().resourcePath,
    totalCount: 0,
    getContentListFlag: true,
    emptyPageObj: {},
    staticImageUrl: getApp().staticImageUrl,
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu();
    // 注册组件
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getSearchResluts('加载数据')
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (this.data.getContentListFlag) {
      if (this.data.totalCount > this.data.contentlist.length) {
        this.data.pageSize += 5
        this.getSearchResluts()
      }
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function(res) {
    let that = this;
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
    if (res.from === 'button') {
      let item = res.target.dataset.item, imgUrl = this.data.resourcePath + item.thumbnailPath
      console.log(imgUrl)
      let shareObj = {
        title: item.houseCommonCode,
        path: '/pages/home/home?shareType=houseDetail&houseId=' + item.id,
        imageUrl: imgUrl,
        success: function (res) {
          // 转发成功
        },
        fail: function (res) {
          // 转发失败
        }

      }
      return shareObj;
    }
  },
  getSearchResluts: function(message) {
    this.data.getContentListFlag = false
    let that = this
    API.getUserHouseTypeList({
        pageSize: this.data.pageSize,
        curPage: this.data.curPage
      })
      .then(res => {
        this.data.getContentListFlag = true
        if (res.obj) {
          if (res.obj.houselist.length > 0) {
            for (let i = 0; i < res.obj.houselist.length; i++) {
              res.obj.houselist[i].houseTypeStr ? res.obj.houselist[i].houseTypeStr = res.obj.houselist[i].houseTypeStr.substr(0, 6) : res.obj.houselist[i].houseTypeStr = '暂无'
            }
            let arrs = new Array(res.obj.userValidHouseCount);
            this.setData({
              // userValidHouseCount: res.obj.userValidHouseCount,
              // userAlreadyBoughtHouseCount: res.obj.userUsedHouseCount,
              contentlist: res.obj.houselist,
              totalCount: res.totalCount,
              // uncontentlist: arrs,
            })
          } else {
            this.setData({
              // userValidHouseCount: 0,
              // userAlreadyBoughtHouseCount: 0,
              contentlist: [],
              totalCount: 0,
              emptyPageObj: {
                imgUrl: $App.staticImageUrl+'undefined.png',
                title: '您还没有使用过户型~',
                // btnText: '购买户型',
                // url: '/pages/purchase-house/purchase-house',
              }
            })
          }
        } else {
          this.setData({
            // userValidHouseCount: 0,
            // userAlreadyBoughtHouseCount: 0,
            contentlist: [],
            totalCount: 0,
            emptyPageObj: {
              imgUrl: $App.staticImageUrl+'undefined.png',
              title: '您还没有使用过户型~',
              // btnText: '购买户型',
              // url: '/pages/purchase-house/purchase-house',
            }
          })
        }
      })
  },
  examineImg(e) {
    let imgArr = [];
    for (let i = 0, len = this.data.contentlist.length; i < len; i++) {
      imgArr.push(this.data.resourcePath + this.data.contentlist[i].largeThumbnailPath);
    }
    this.setData({
      imgList: imgArr
    });
    // console.log(this.data.resourcePath + this.data.imgList);return;
    wx.previewImage({
      //当前显示下表
      current: this.data.imgList[e.currentTarget.dataset.index],
      //数据源
      urls: this.data.imgList
    })
  },
  routerToFitment(e) { // 去装修
    wx.removeStorageSync('caseItem')
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: '/pages/house-type/house-details/house-details?type=search&houseId=' + item.id
    })
  },
  // routerTopurchaseHouse(e) { // 购买户型
  //   let type = e.currentTarget.dataset.type
  //   if (type == 'use') {
  //     wx.navigateTo({
  //       url: '/pages/plan/case-house-type/case-house-type',
  //     })
  //   } else {
  //     wx.navigateTo({
  //       url: '/pages/purchase-house/purchase-house'
  //     })
  //   }
  // },
  goToNews(e) {
    let item = e.currentTarget.dataset.item
    let type = 'house'
    console.log(item);
    wx.navigateTo({
      url: '/pages/publishMessage/publishMessage?' + 'imgUrl=' + item.largeThumbnailPath + '&id=' + item.id + '&type=' + type
    })
  }
})