// pages/trulyCaseDetail/trulyCaseDetail.js
const app = getApp(), API = app.API //每个界面都要写上API，才能调
Page({
  data: {
    resourcePath: app.data.resourcePath, // 这个是图片的前缀
    defaultHeader: '/static/images/header.png',
    caseId:'',
    detailInfo:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.data.caseId = options.caseId
    this.caseDetalInfo()
  },
  goutong(e) {
    //let name = e.currentTarget.dataset.name;
    let items = this.data.detailInfo[0].shopDetail;
    items.shopIntroduced = ''
    let item = JSON.stringify(items);
    wx.navigateTo({
      url: `/pages/chat/chat?item=${item}`,
    })
  },
  caseDetalInfo(){
    API.getTrueCaseList({
      caseId: this.data.caseId,
      pageType:'detail'
    }).then((res) => {
      if (res.datalist) {
        res.datalist[0].content = res.datalist[0].content.replace(/\<img/gi, '<img class="img" ')
        this.setData({
          detailInfo: res.datalist,
          shopDetail: res.datalist[0].shopDetail
        })
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})