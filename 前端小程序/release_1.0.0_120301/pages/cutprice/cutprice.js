let API = getApp().API;
Page({

  /**
   * 页面的初始数据
   */
  data: {
      url:'',
      actId:'',
      regId:'',
    produceName:'',
    shareImg:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    wx.setStorage({ //体验活动需要传的参数
      key: 'actId',
      data: options.actId,
    })
    //体验完一次后 之后就不显示体验成功的弹窗
    var cutPrice = wx.getStorageSync('cutPrice');
    //判断是否是当前用户
    let userId=wx.getStorageSync('id');
    let isUser = options.isUser || 1;//isUser==2 好友进入
    if (options.userId){
      userId == options.userId ? isUser = 1 : '';
    }
    //跳转h5页面
    this.getRegId(options,isUser,cutPrice)
    console.log(this.data.url)
  },
  getRegId(options,isUser,cutPrice){
    let self = this;
    let userId = wx.getStorageSync('id');
    API.getRegStatus({ actId: options.actId }).then(item => {//2种分享方式先跳转首页再跳活动也 生成海报的分享获取的是 cutpriceUrl 是整个域名（线上可以测）
      let regId = options.regId || item.obj&&item.obj.registrationId;
        //https://ad.ci.sanduspace.com  http://192.168.3.136:8080
      let url = `https://ad.ci.sanduspace.com/#/cutPrice?actId=${options.actId}&regId=${regId}&isUser=${isUser}&cutPrice=${cutPrice}&token=${wx.getStorageSync('token')}&appId=wx42e6b214e6cdaed3`
        options.actId ? '' : url = getApp().data.cutpriceUrl
        this.setData({
          actId: options.actId,
          regId: regId,
          url: url
      }) 
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
  getmessage(e){
    console.log(e)
    e.detail.data.map(res=>{
      if (res.produceName || res.shareImg){
        this.setData({
          produceName: res.produceName,
          shareImg: res.shareImg
        })
      } else if(res.regId){
        this.setData({
          regId: res.regId
        })
      }
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    console.log(this.data.regId)
    let self=this;
    return {
      title: '我在免费拿' + this.data.produceName+'，快来砍我一刀吧~',
      path: "/pages/home/home?actId=" + self.data.actId + "&regId=" + self.data.regId + "&isUser=2&cutPrice=&userId=" + wx.getStorageSync('id'),
      imageUrl: this.data.shareImg,
      success: function (res) {
        // 转发成功
        console.log(res)
      },
      fail: function (res) {
        // 转发失败
      }
    }
  }
})