// pages/oneKeyPlan/oneKeyPlan.js
// js规范
// 所有函数采用onshow写法，不要用onload function的写法
const app = getApp(), API = app.API //每个界面都要写上API，才能调

Page({

  /**
   * 页面的初始数据
   */
  data: {
    oneKeyPlanList: [],
    pageSize:5, // 每页显示的条数
    start:1, // 开始页数
    pageNum:0, // 页码
    shopId: 0,
    screenHeight: 0,
    totalCount: 0
    // requestPageNum: 1,   // 设置加载的第几次，默认是第一次  
    // callbackcount: 10,      //返回数据的个数 ，前端自己可以定制返回数据的个数
    // requestLoading: false, //"上拉加载"的变量，默认false，隐藏  
    // requestLoadingComplete: false  //“没有数据”的变量，默认false，隐藏  

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.data.shopId = options.shopId;
    let that = this;
    wx.getSystemInfo({
      success: function (res) {
        console.log(res.windowHeight, 'screenHeight')
        that.setData({
          screenHeight: res.windowHeight
        })
      },
    })
    this.getOneKeyPlan();
  },
  // 一键方案
  getOneKeyPlan(type) {
    //let that = this;
    // let requestPageNum = this.data.requestPageNum, // 第几次加载数据(第几页)
    //   callbackcount = this.data.callbackcount; //返回数据的个数(一次性取数据的个数)
    API.getOneKeyPlan({
      pageVo: { pageSize: this.data.pageSize, start: this.data.start },
      recommendationPlanSearchVo: {
        decoratePriceType: "",
        designStyleId: "",
        displayType: 'decorate',
        houseType: "",
        platformCode: "",
        shopId: this.data.shopId
      }
    }).then(res => {
      if (res.datalist) {
        let arr = type == 'pull' ? this.data.oneKeyPlanList.concat(res.datalist) : res.datalist;
        this.setData({
          oneKeyPlanList: arr,
          totalCount: res.totalCount
        });
      }
    });
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.data.start += 5;
    if (this.data.start < this.data.totalCount) {
      this.getOneKeyPlan('pull');
    }

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
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})