// pages/trulyCase/trulyCase.js
const app = getApp(), API = app.API //每个界面都要写上API，才能调
Page({

  /**
   * 页面的初始数据
   */
  data: {    
    resourcePath: app.data.resourcePath, // 这个是图片的前缀
    trueCaseData:[],
    pageSize: 5, // 每页显示的条数
    start: 1, // 开始页数
    pageNum: 0, // 页码
    shoId: 0,
    companyId: 0,
    screenHeight: 0,
  },

  toDetail(e){
    let item = e.currentTarget.dataset.item
    wx.navigateTo({
      url: `/pages/trulyCaseDetail/trulyCaseDetail?caseId=${item.caseId}`,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.data.shopId = options.shopId;
    this.data.companyId = options.companyId;
    this.TureCaseList();
    let that = this;
    wx.getSystemInfo({
      success: function (res) {
        console.log(res.windowHeight, 'screenHeight')
        that.setData({
          screenHeight: res.windowHeight
        })
      },
    })
  },
  TureCaseList(type){
    API.getTrueCaseList({
      pageNo: this.data.start,
      pageSize: this.data.pageSize,
      shopId: this.data.shopId || ''
    }).then((res) => {
      if (res.datalist){
        let arr = type == 'pull' ? this.data.trueCaseData.concat(res.datalist) : res.datalist;
        this.setData({
          trueCaseData: arr,
          totalCount: res.totalCount
        });
      }
    })
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
    
    if (this.data.start * 5 < this.data.totalCount) {
      this.data.start++;
      this.TureCaseList('pull');
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})