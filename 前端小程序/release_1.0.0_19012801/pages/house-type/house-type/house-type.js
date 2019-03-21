   
let API = getApp().API
let myFindIndex = getApp().myFindIndex
let myFind = getApp().myFind
let $App = getApp()
Page({
  data: {
    resourcePath: getApp().resourcePath,
    cityData: [],
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
    spcacNums:-1,
    productList: [],
    cityCode: "",
    isAdress: false,
    productListImgs: [],
    message: "加载中",
    livingName: "",
    spaceHeader: {
      houseName: "客餐厅",
      areaName: "面积",
      shapeName: "形状",
      spaceFunctionId: 3,
      areaValue: "",
      spaceShape: "",
    },
    isTopFix: false,
    shapeNum: -1,
    historySearchList: [],
    historySearchListShow: true,
    region: '', // 整个地址
    districtCode: '',
    houseListShow: false,
    spaceSearchPaseSize: 10,
    spaceSearchTotalCount: 0
  },
  isLableShow: false,
  provincialLinkageHide(e) { // 确认地址接口
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
      url: '/pages/mine/upload-house/upload-house',
    })
  },
  routerToMyHouse() { // 我的户型
    wx.navigateTo({
      url: '/pages/mine/my-house-type/my-house-type',
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

    // var val = e.detail.value
    // var t = this.data.values;
    // var cityData = this.data.cityData;
    // if (val[0] != t[0]) {
    //   const citys = [];
    //   const countys = [];
    //   for (let i = 0; i < cityData[val[0]].baseAreaVos.length; i++) {
    //     citys.push(cityData[val[0]].baseAreaVos[i].areaName)
    //   }
    //   for (let i = 0; i < cityData[val[0]].baseAreaVos[0].baseAreaVos.length; i++) {
    //     countys.push(cityData[val[0]].baseAreaVos[0].baseAreaVos[i].areaName)
    //   }
    //   this.setData({
    //     citys: citys,
    //     countys: countys,
    //     values: [val[0], 0, 0]
    //   })
    //   val[1] = 0
    // }else if (val[1] != t[1]) {
    //   const countys = [];
    //   for (let i = 0; i < cityData[val[0]].baseAreaVos[val[1]].baseAreaVos.length; i++) {
    //     countys.push(cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[i].areaName)
    //   }
    //   this.setData({
    //     city: this.data.citys[val[1]],
    //     county: cityData[val[0]].baseAreaVos[val[1]].baseAreaVos[0].areaName,
    //     countys: countys,
    //     values: [val[0], val[1], 0],
    //     value: [val[0], val[1], 0]
    //   })
    //   val[2] = 0
    // }
    // this.setData({
    //   county: this.data.countys[val[2]],
    //   values: val
    // })
  },
  isListShow: function(e) {
    this.setData({
      isListShow: false,
      contentlist: [],
      curPage: 1
    })
    if (this.data.livingName.length > 0 && this.data.cityCode.length > 0) {
      this.setData({
        historySearchListShow: false,
        houseListShow: true
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
  ishow: function(e) {
    // 选项样式的改变
    var that = this;
    var type = e.currentTarget.dataset.type;
    type == 1 ? this.setData({ ishow: false, houseListShow: true }) : this.setData({ ishow: true, houseListShow: false })
    this.setData({ isListShow: true })
    if (type == 2) {
      that.setData({
        curPage: 1,
        hasMoreData: true,
        spaceHeader: {
          houseName: "客餐厅",
          areaName: "面积",
          shapeName: "形状",
          spaceFunctionId: 3,
          areaValue: "",
          spaceShape: "",
        }
      });
      that.spacesearch("正在刷新数据")
      // 获取空间和面积
      if (!this.data.spaceList.length) {
        API.getConditionMetadata()
        .then(res => {
          if (res.success) {
            this.setData({
              spaceList: res.obj,
              spcacNumsList: res.obj[0].designPlanAreaList,
              spaceHeader: {
                houseName: res.obj[0].houseName,
                areaName: "面积",
                shapeName: "形状",
                spaceFunctionId: 3,
                areaValue: "",
                spaceShape: "",
              }
            })
          } else {
            wx.showToast({ title: res.message })
          }
        })
      }
      // 获取图形
      if (!this.data.shapeList.length) {
        API.getHouseTypeSpacePatterning()
        .then(res => {
          res.success ? this.setData({ shapeList: res.obj }) : wx.showToast({ title: res.message })
        })
        .catch((res) => { wx.showToast({ title: '加载数据失败' }) })
      }
    }
    if (type == 1) {
      this.setData({ productList: [] })
    }
  },
  isTypeShow: function(e) {
    var that = this;
    var type = e.currentTarget.dataset.type;
    var nowIsTypeNum = this.data.isTypeNum;
    var isLableShow = !this.data.isLableShow;
    if (type !== nowIsTypeNum) { isLableShow = true }
    that.setData({  isTypeNum: type, isLableShow: isLableShow });
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
    wx.hideShareMenu();
    // 获取缓存信息
    let historySearchList = wx.getStorageSync('historySearchList') ? wx.getStorageSync('historySearchList') : []
    this.setData({ historySearchList: historySearchList })
    // 获取省市区
    this.getCityData()
  },
  onShow() {
    // 获取缓存信息
    let historySearchList = wx.getStorageSync('historySearchList') ? wx.getStorageSync('historySearchList') : []
    this.setData({
      historySearchList: historySearchList
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
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
      districtCode: item.districtCode,
      houseListShow: true
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
    API.getSearchResluts({
      livingName: that.data.livingName,
      cityCode: that.data.cityCode,
      pageSize: that.data.pageSize,
      curPage: that.data.curPage,
      districtCode: that.data.districtCode
    })
    .then(res => {
      let contentlistTem = this.data.contentlist
      if (res.success) {
        let contentlist = res.obj, hasMoreData = null, obj = null
        this.data.curPage == 1? contentlistTem = []: '' 
        contentlist.length < this.data.pageSize ? obj = { hasMoreData: false } : obj = { hasMoreData: true, curPage: this.data.curPage + 1}
        this.setData(Object.assign({ contentlist: contentlistTem.concat(contentlist)}, obj))
      } else {
        wx.showToast({ title: res.message })
      }
    })
  },
  spacesearch: function(message) {
    let that = this
    API.getHouseTypeSpaceList({
      spaceFunctionId: that.data.spaceHeader.spaceFunctionId,
      areaValue: that.data.spaceHeader.areaValue,
      spaceShape: that.data.spaceHeader.spaceShape,
      houseId: "",
      pageSize: that.data.pageSize,
      curPage: that.data.curPage
    })
    .then(res => {
      let productListTem = that.data.productList
      let productImageTem = that.data.productListImgs;
      if (res.success) {
        if (res.obj) {
          let productListImgs = [], productList = res.obj, obj = null
          that.data.curPage == 1 ? (productListTem = [], productImageTem = []) : null
          for (let i = 0; i < productList.length; i++) {
            productListImgs.push(that.data.resourcePath + "/" + productList[i].spaceviewPlanPathPic)
          }
          productList.length < that.data.pageSize ? obj = { hasMoreData: false } : obj = { hasMoreData: true, curPage: that.data.curPage + 1}
          this.setData(Object.assign({
            productList: productListTem.concat(productList),
            productListImgs: productImageTem.concat(productListImgs),
          }, obj))
        } else {
          that.setData({ message: '暂无数据' })
        }
      } else {
        that.setData({ message: res.message })
      }
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    if (this.data.isTypeNum >=0 && this.data.ishow && this.data.isLableShow) {
      wx.stopPullDownRefresh()
    } else {
      if (!this.data.condition) {
        this.data.page = 1
        this.getSearchResluts('正在刷新数据')
      }
    }
  },
  onPageScroll: function(e) {
    var dis = e.scrollTop
   
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (this.data.ishow) {
      this.spacesearch('正在刷新数据')
      return
    }
    if (this.data.isTypeNum >=0 && this.data.ishow && this.data.isLableShow) {
      wx.stopPullDownRefresh()
    } else {
      if (!this.data.condition) {
        let flag = this.data.contentlist.length > 0 && this.data.houseListShow 
        if (!flag) {
          return
        }
        if (this.data.hasMoreData ) {
          this.getSearchResluts('加载更多数据')
        } else {
          wx.showToast({
            title: '没有更多数据',
          })
        }
      }
    }
  },
  chooseHouseType: function(e) {
    var that = this;
    var type = e.currentTarget.dataset.type;
    var index = e.currentTarget.dataset.index;
    var name = e.currentTarget.dataset.name;
    var spcacNumsList = this.data.spaceList[index].designPlanAreaList;
    var isLableShow = !this.data.isLableShow;
    this.setData({
      isLableShow: isLableShow,
      curPage: 1,
      spaceNum: index,
      isTypeNum: -1,
      spcacNums:-1,
      shapeNum:-1,
      spcacNumsList: spcacNumsList,
      productList: [],
      spaceHeader: {
        houseName: name,
        areaName: '面积',
        shapeName: '形状',
        spaceFunctionId: type,
        areaValue:'',
        spaceShape:'',
      }
    })
    this.spacesearch('正在刷新数据');
  },
  spcacNums: function(e) {
    var that = this;
   
    var type = e.currentTarget.dataset.id;
    var index = e.currentTarget.dataset.index;
    var name = e.currentTarget.dataset.name;
    var isLableShow = !this.data.isLableShow;
    if (index == -1) {
      name = '全部'
    } else {
      name = name + " m²"
    }
    this.setData({
      isLableShow: isLableShow,
      curPage: 1,
      spcacNums: index,
      isTypeNum: -1,
      productList: [],
      spaceHeader: {
        houseName: that.data.spaceHeader.houseName,
        areaName: name,
        shapeName: that.data.spaceHeader.shapeName,
        spaceFunctionId: that.data.spaceHeader.spaceFunctionId,
        areaValue: type,
        spaceShape: that.data.spaceHeader.spaceShape,
      }
    })
    this.spacesearch('正在刷新数据');
  },
  changeShape: function(e) {
    var that = this;
   
    var isLableShow = !this.data.isLableShow;
    var type = e.currentTarget.dataset.id;
    var index = e.currentTarget.dataset.index;
    var name = e.currentTarget.dataset.name;
    this.setData({
      isLableShow: isLableShow,
      curPage: 1,
      shapeNum: index,
      isTypeNum: -1,
      productList: [],
      spaceHeader: {
        houseName: that.data.spaceHeader.houseName,
        areaName: that.data.spaceHeader.areaName,
        shapeName: name,
        spaceFunctionId: that.data.spaceHeader.spaceFunctionId,
        areaValue: that.data.spaceHeader.areaValue,
        spaceShape: type,
      }
    })
    this.spacesearch('正在刷新数据');
  },
  closeLabel: function() {
    this.setData({
      isLableShow: false
    })
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
  removeCaseItem() { // 删除caseItem
    wx.removeStorageSync('caseItem')
  },
  toHouseDetails(e) { // 空间入口
    wx.removeStorageSync('caseItem')
    let item = e.currentTarget.dataset.item
    wx.navigateTo({
      url: '../house-details/house-details?type=search&item=' + JSON.stringify(item)
    })
  }
})