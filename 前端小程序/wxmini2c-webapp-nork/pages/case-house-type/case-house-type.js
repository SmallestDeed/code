
var tcity = require("../../utils/citys.js");
let fetch = getApp().fetch
let myFindIndex = getApp().myFindIndex
let myFind = getApp().myFind
let $App = getApp()
Page({
  data: {
    resourcePath: getApp().resourcePath,
    staticImageUrl:getApp().staticImageUrl,
    provinces: [],
    province: "",
    citys: [],
    city: "",
    countys: [],
    county: '',
    value: [0, 0, 0],
    threeLevelValue: [0, 0, 0],
    condition: false,
    ishow: false,
    isListShow: true,
    isTypeNum: -1,
    curPage: 1,
    pageSize: 10,
    hasMoreData: true,
    contentlist: [],
    spaceList: [],
    spcacNumsList: [],
    shapeList: [],
    spaceNum: 0,
    spcacNums: 0,
    productList: [],
    cityCode: "",
    productListImgs: [],
    message: "加载中",
    livingName: "",
   isAdress:false,
   historySearchList: [],
   historySearchListShow: true,
   region: '', // 整个地址
   districtCode: ''

  },
  provincialLinkageHide(e) { // 确认地址
    let flag = e.currentTarget.dataset.flag, val = this.data.threeLevelValue
    this.setData({
      condition: false
    })
    if (flag) {
      let region = this.data.cityData[val[0]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].areaName + this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaName
      this.setData({
        province: this.data.cityData[val[0]].areaName,
        city: this.data.cityData[val[0]].baseAreaVos[val[1]].areaName,
        cityCode: this.data.cityData[val[0]].baseAreaVos[val[1]].areaCode,
        districtCode: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[val[2]].areaCode,
        region: region,
        value: this.data.threeLevelValue
      })
    }
  },
  deleteHistorySearchList() { // 删除本地搜索记录
    let $self = this
    wx.showModal({
      title: '提示',
      content: '是否清空所有历史搜索记录？',
      cancelColor: '#333',
      confirmColor: '#ff6419',
      success: function (res) {
        if (res.confirm) {
          wx.removeStorageSync('historySearchList')
          $self.setData({
            historySearchList: []
          })
        }
      }
    })
  },
  routerToUploadHouse() { // 上传户型
    wx.navigateTo({
      url: '/pages/upload-house/upload-house',
    })
  },
  routerToMyHouse() { // 我的户型
    wx.navigateTo({
      url: '/pages/my-house-type/my-house-type',
    })
  },
  bindChange: function(e) {
    const val = e.detail.value
    const temp = this.data.threeLevelValue
    if (temp[0] !== val[0]) {
      this.setData({
        value: [val[0], 0, 0]
      })
      val[1] = 0
    } else if (temp[1] !== val[1]) {
      this.setData({
        value: [val[0], val[1], 0]
      })
      val[2] = 0
    }
    this.setData({
      citys: this.data.cityData[val[0]].baseAreaVos,
      countys: this.data.cityData[val[0]].baseAreaVos[val[1]].baseAreaVos,
      threeLevelValue: val
    })
  },
  isListShow: function(e) {
    this.setData({
      curPage: 1,
      isListShow: false,
      contentlist: [],
    })
    if (this.data.livingName.length > 0 && this.data.cityCode.length > 0) {
      this.setData({
        historySearchListShow: false
      })
      let historySearchList = wx.getStorageSync('historySearchList') || []
      let index = myFindIndex(historySearchList, (n) => {
        return n.livingName === this.data.livingName && n.cityCode === this.data.cityCode
      })
      if (index != -1) {
        historySearchList.splice(index, 1)
      }
      historySearchList.unshift({
        livingName: this.data.livingName,
        cityCode: this.data.cityCode,
        region: this.data.region,
        districtCode: this.data.districtCode
      })
      historySearchList.length > 5 ? historySearchList = historySearchList.slice(0, 5) : ''
      wx.setStorageSync('historySearchList', historySearchList)
      this.getSearchResluts();
    } else {
      wx.showToast({
        title: '请填写完整信息',
        icon: 'none'
      })
    }
  },
  valChange: function(e) {
    this.setData({
      livingName: e.detail.value
    })
  },
  open: function(e) {
    this.setData({
      condition: true
    })
  },
  getCityData() {
    let cityData = $App.cityDataFilter(wx.getStorageSync('cityData'))
    let provinces = []
    $App.myForEach(cityData, (value) => {
      provinces.push({
        areaCode: value.areaCode,
        areaName: value.areaName,
        id: value.id,
        levelId: value.levelId,
        pid: value.pid
      })
    })
    this.setData({
      provinces: provinces,
      citys: cityData[0].baseAreaVos,
      countys: cityData[0].baseAreaVos[0].baseAreaVos
    })
    this.data.cityData = cityData 
  },
  onLoad: function() {
    new $App.quickNavigation() // 注册组件
    // 获取缓存信息
    let historySearchList = wx.getStorageSync('historySearchList') ? wx.getStorageSync('historySearchList') : []
    this.setData({
      historySearchList: historySearchList
    })
    // 获取省市区
    this.getCityData()
    // let url = `/base/basearea/getAllArea`
    // fetch(url, 'get')
    //   .then((res) => {
    //     if (res.status) {
    //     } else {
    //       wx.setStorageSync('cityData', [])
    //     }
    //   })
    //   .catch(() => {
    //     wx.setStorageSync('cityData', [])
    //   })

  },
  onShow() {
    // 获取缓存信息
    let historySearchList = wx.getStorageSync('historySearchList') ? wx.getStorageSync('historySearchList') : []
    this.setData({
      historySearchList: historySearchList
    })
  },
  historySearch(e) { // 通过历史数据进行搜索
    let item = e.currentTarget.dataset.item
    this.setData({
      pageSize: 10,
      curPage: 1,
      historySearchListShow: false,
      contentlist: [],
      livingName: item.livingName,
      cityCode: item.cityCode,
      region: item.region,
      districtCode: item.districtCode      
    })
    this.getSearchResluts()
  },
  getSearchResluts: function () {
    let that = this
    var url = "/base/baseliving/getlvinglist"
    var data = {
      livingName: that.data.livingName,
      cityCode: that.data.cityCode,
      pageSize: that.data.pageSize,
      curPage: that.data.curPage,
      districtCode: that.data.districtCode
    }
    fetch(url, 'get', data)
      .then((res) => {
        var contentlistTem = that.data.contentlist
        if (res.success) {
          if (that.data.curPage == 1) {
            contentlistTem = []
          }
          var contentlist = res.obj;
          if (contentlist.length > 0) {
            if (contentlist.length < that.data.pageSize) {
              that.setData({
                contentlist: contentlistTem.concat(contentlist),
                hasMoreData: false
              })
            } else {
              that.setData({
                contentlist: contentlistTem.concat(contentlist),
                hasMoreData: true,
                curPage: that.data.curPage + 1
              })
            }
          } else {

            wx.showToast({
            title: '暂无数据',
          })

          }
        } else {
          wx.showToast({
            title: res.message,
          })
        }
      })
      .catch((res) => {
        wx.showToast({
          title: '加载数据失败',
        })
      })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    if (!this.data.condition) {
      this.data.page = 1
      this.getSearchResluts('正在刷新数据')
    }
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (!this.data.condition) {
      let flag = this.data.contentlist.length > 0
      if (!flag) {
        return
      }
      if (this.data.hasMoreData) {
        this.getSearchResluts('加载更多数据')
      } else {
        wx.showToast({
          title: '没有更多数据',
        })
      }
    }
    if (this.data.ishow) {
      this.spacesearch('正在刷新数据')
    }
  },
  previewImage: function(e) {
    var that = this,
      //获取当前图片的下表
      index = e.currentTarget.dataset.index,
      //数据源
      productListImgs = this.data.productListImgs;
    wx.previewImage({
      //当前显示下表
      current: productListImgs[index],
      //数据源
      urls: productListImgs
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageFn(false);
    }
  },
})