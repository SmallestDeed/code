// pages/brandHall/bHStore/bHStore.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgURL:getApp().data.imgURL,
    isShowClassify: false,
    isShowSort: false,
    isClassifySlideDown: false,
    isClassifySlideTop: true,//Top开始都是true down都是false
    isSortSlideDown: false,
    isSortSlideTop: true,
    currClassify: { id: "", name: "商品分类" },//当前分类的内容 默认是全部
    currSort: { id: 0, name: "销量" },
    classifyList: [
      {
        name: "新品上市",
        id: "0"
      },
      {
        name: "茶几电视柜",
        id: "1"
      },
      {
        name: "餐桌椅系列",
        id: "2"
      },
      {
        name: "储物系列",
        id: "3"
      }
    ],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
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
  onShareAppMessage: function () {
  
  },
  toggleSort: function () {
    var This = this;
    This.setData({
      isSortSlideTop: This.data.isSortSlideDown,
      isSortSlideDown: This.data.isSortSlideTop,
      isShowSort: !This.data.isShowSort,
      isClassifySlideDown: false,
      isClassifySlideTop: true,
      isShowClassify:false
    })
  },
  toggleClassify: function () {
    var This = this;
    This.setData({
      isClassifySlideTop: This.data.isClassifySlideDown,
      isClassifySlideDown: This.data.isClassifySlideTop,
      isShowClassify: !This.data.isShowClassify,
      isSortSlideDown: false,
      isSortSlideTop: true,
      isShowSort:false
    })
  }
})