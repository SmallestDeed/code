 
 // pages/house-goods/house-goods.js
let fetch = getApp().fetch
let myForEach = getApp().myForEach, $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    parentClassificationlist: [],
    childClassificationlist: [],
    childClassificationAllList: [],
    childClassificationImageList: [],
    textActive: 0, // 左部导航焦点样式
    resourcePath: getApp().resourcePath,
    threeClassificationList: [],
    fourClassificationList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getScreenConditions() // 产品分类
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
      return $App.shareAppMessageFn(true);
    }
  },
  switchClassification(e) { // 左边焦点切换
    this.setData({
      textActive: e.target.dataset.index,
      childClassificationlist: this.data.childClassificationAllList[e.target.dataset.index]
    })
  },
  linkTogoodsList(e) { // 跳转至商品列表 
    let code = e.currentTarget.dataset.item.code
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
    wx.navigateTo({
      url: '../goods-list/goods-list?name=' + name + '&id=' + id + '&code=' + code
    })
  },
  getScreenConditions() { // 获取产品筛选条件
    let url = '/product/baseproduct/productcategory'
    let childClassificationAllList = []
    fetch(url, 'get')
      .then((res) => {
        if (res.status) {
          // 子集分类
          myForEach(res.obj[1], (valP, index) => {
            childClassificationAllList[index] = []
            myForEach(res.obj[2], (valC) => {
              if (valC.pid === valP.id) {
                childClassificationAllList[index].push(valC)
              }
            })
            this.data.threeClassificationList = res.obj[3]
            this.data.fourClassificationList = res.obj[4]        
          })
          // 子集分类
          this.setData({
            childClassificationlist: childClassificationAllList[0],
            parentClassificationlist: res.obj[1],
            childClassificationAllList: childClassificationAllList
          })
        } else {
          this.setData({
            parentClassificationlist: []
          })
        }
      })
      .catch(() => {
        this.setData({
          parentClassificationlist: []
        })
      })
  }

})