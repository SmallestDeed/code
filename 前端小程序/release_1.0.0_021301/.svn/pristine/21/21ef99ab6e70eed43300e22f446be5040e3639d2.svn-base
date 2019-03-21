let $App = getApp(), API = getApp().API
Page({
  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: getApp().resourcePath,
    staticImageUrl: getApp().staticImageUrl,
    curPage: 1,
    pageSize: 10,
    contentlist: [],
    message:"加载中",
    selectData:[],
    orderType:0,
    orderStatusStr: '',
    payStatusStr: '',
    shippingStatus: '',
    emptyPageObj: {}
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    
    // 用户数据

    this.getSearchResluts('加载数据');
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.getSearchResluts('加载数据');
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {},
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  getSearchResluts: function(message) {
    
    var that = this
    var url = "/order/dynamicQueryOrder"
    API.getUserOrderList({
      curPage: this.data.curPage,
      pageSize: this.data.pageSize,
      payStatusStr: this.data.payStatusStr,
      shippingStatus: this.data.shippingStatus,
      orderStatusStr: this.data.orderStatusStr
    })
    .then((res) => {
      let contentlistTem = that.data.contentlist
      if (res.success) {
        let contentlist = res.obj;
        if (that.data.curPage == 1) {
          contentlistTem = []
          this.setData({ selectData: res.obj })
        }
        if (contentlist) {
          if (contentlist.length < that.data.pageSize) {
            that.setData({ contentlist: contentlistTem.concat(contentlist), hasMoreData: false })
          } else {
            that.setData({ contentlist: contentlistTem.concat(contentlist), hasMoreData: true})
          }
        } else {
          that.setData({ 
            message: "您还没有相关订单哦~",
            emptyPageObj: {
              imgUrl: this.data.staticImageUrl + 'carList.png',
              title: '您还没有任何订单哦',
            }
          })
        }
      }
      if (!res.flag) {
        that.setData({ 
          message: "您还没有相关订单哦~",
          emptyPageObj: {
            imgUrl: this.data.staticImageUrl + 'carList.png',
            title: '您还没有任何订单哦~',
          }
        })
      }
    })
    .catch((res) => {
      that.setData({ message: res.message })
    })
  },
  changeData:function(e){
    var that = this
    var type = e.currentTarget.dataset.type;

    that.setData({
      orderType: type
    })
    if (type == 0) {
      that.setData({
        selectData: [],
        payStatusStr: '',
        shippingStatus: '',
        orderStatusStr: '',
        pageSize: 10,
        orderStatusStr: ''
      })

      this.getSearchResluts('加载数据')
    }

    if (type == 1) {
      that.setData({
        selectData: [],
        pageSize: 10,
        payStatusStr: '0,1',
        shippingStatus: 0,
        orderStatusStr: '0'
      })
      this.getSearchResluts('加载数据')
    }
    if (type == 2) {
      that.setData({
        selectData: [],
        pageSize: 10,
        payStatusStr: '2',
        shippingStatus: 0,
        orderStatusStr: '0,1'
      })
      this.getSearchResluts('加载数据')
    }
    if (type == 3) {
      that.setData({
        selectData: [],
        pageSize: 10,
        payStatusStr: '2',
        shippingStatus: 1,
        orderStatusStr: '1'
      })
      this.getSearchResluts('加载数据')
    }

    

    
   

  },
  toDetail(e){
    var orderid = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/my-order-detail/my-order-detail?id='+orderid,
    })
  }
})