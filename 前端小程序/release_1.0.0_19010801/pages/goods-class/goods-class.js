

// pages/house-goods/house-goods.js
let API = getApp().API
let myForEach = getApp().myForEach, $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    parentClassificationlist: [],
    childClassificationlist: [],
    childClassificationAllList: [],
    childClassificationImageList: [],
    textActive: 0, // 左部导航焦点样式
    resourcePath: getApp().resourcePath,
    threeClassificationList: [],
    fourClassificationList: [],
    bannerImageStr: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    let id = options.id
    let idArr = ['1000', '2000', '4000'], imageArr = ['jiancai', 'jiaju', 'dianqi']
    this.setData({ bannerImageStr: imageArr[idArr.findIndex((n) => { return n == id })]})
    this.getScreenConditions(id) // 产品分类
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  switchClassification(e) { // 左边焦点切换
    this.setData({
      textActive: e.target.dataset.index,
      childClassificationlist: this.data.childClassificationAllList[e.target.dataset.index]
    })
  },
  linkTogoodsList(e) { // 跳转至商品列表 
    let code = e.currentTarget.dataset.item.longCode
    let id = e.currentTarget.dataset.item.id
    let name = e.currentTarget.dataset.item.name
    let threeClassificationList = []
    let fourClassificationList = []
    // 三级分类
    myForEach(this.data.threeClassificationList, (val) => {
      if (val.pid == id) {
        threeClassificationList.push(val)
      }
    })
    // 四级分类
    myForEach(threeClassificationList, (valP) => {
      valP.fourClassificationList = []
      myForEach(this.data.fourClassificationList, (valC) => {
        if (valP.id === valC.pid) {
          valP.fourClassificationList.push(valC)
        }
      })
    })
    wx.setStorageSync('threeClassificationList', threeClassificationList)
    console.log('name', name, 'id', id, 'code', code);
    wx.navigateTo({
      url: '../goods-list/goods-list?name=' + name + '&id=' + id + '&code=' + code
    })
  },
  getScreenConditions(id) { // 获取产品筛选条件
    let url = '/product/baseproduct/productcategory'
    let childClassificationAllList = []
    API.getProductCategory()
    .then(res => {
      if (res.status) {
        let count = 0, parentClassificationlist = [], childClassificationAllList = []
        myForEach(res.obj[1], (valP, index) => {
          if (valP.pid == id) {
            childClassificationAllList[count] = [] // 存第三级
            parentClassificationlist.push(valP) // 存第二级（分开存储实现性能优化）
            myForEach(res.obj[2], (valC) => {
              if (valC.pid == valP.id) {
                childClassificationAllList[count].push(valC)
              }
            })
            count++
          }
        })
        this.data.threeClassificationList = res.obj[3] // 存储第四级（不写入）
        this.data.fourClassificationList = res.obj[4] // 存储第五级（不写入）
        this.setData({ // 写入
          childClassificationlist: childClassificationAllList[0],
          parentClassificationlist: parentClassificationlist,
          childClassificationAllList: childClassificationAllList
        })
      } else {
        this.setData({ parentClassificationlist: [] })
      }
    })
    .catch(() => {
      this.setData({ parentClassificationlist: [] })
    })
  }

})