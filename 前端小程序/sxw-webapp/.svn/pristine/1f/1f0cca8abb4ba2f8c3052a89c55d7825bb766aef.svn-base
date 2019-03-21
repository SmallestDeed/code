// pages/my-render-comment/my-render-comment.js
let API = getApp().API
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    pageSize: 10,
    totalCount: 0,
    commentList:[],
    emptyPageObj: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.getcommentMessage();
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

  /*获取评论信息列表*/
  getcommentMessage() {
    API.commentMessage({
      curPage: 0,
      pageSize: this.data.pageSize
    }).then(res => {
      console.log(res);
      if (res.obj) {
        this.setData({
          commentList: res.obj,
          totalCount: res.totalCount
        });
      } else {
        this.setData({
          emptyPageObj: {
            imgUrl: this.data.staticImageUrl+'undefined.png',
            title: '暂无任何消息'
          }
        });
      }
    });
  },

  /*跳转详情页面*/
  goSupplyDetail(e) {
    let index = e.currentTarget.dataset.index;
    let id = e.currentTarget.dataset.item.businessId;
    let read = e.currentTarget.dataset.item.isRead;
    if (read == 0) {
      API.readMessage({
        reviewsId: e.currentTarget.dataset.item.id
      }).then(res => {
        console.log(res);
        let arr = this.data.commentList
        console.log(arr[index]);
        arr[index].isRead = 1
        this.setData({
          commentList: arr
        });
      });
    }

    API.getSupplydemandinfoDetail({ supplyDemandId: id }).then(res=>{
      let flag;
      if(res.obj==null){
        flag=1
        wx.navigateTo({
          url: '/pages/decoration/supplyDetail/supplyDetail?id=' + id + '&isShowDelete=' + flag,
        })
      }else{
        flag = 0
        wx.navigateTo({
          url: '/pages/decoration/supplyDetail/supplyDetail?id=' + id + '&isShowDelete=' + flag,
        })
      }
    })
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
    this.data.pageSize += 10;
    this.getcommentMessage();
    // if (this.data.totalCount > this.data.pageSize) {
    //   this.data.pageSize++;
    //   this.getcommentMessage();
    // }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})