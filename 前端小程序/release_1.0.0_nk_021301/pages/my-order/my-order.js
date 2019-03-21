let fetch = getApp().fetch, API = getApp().API;
let $App = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl:getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    curPage: 1,
    pageSize: 5,
    contentlist: [],
    message:"加载中",
    selectData:[],
    orderType:0,
    orderStatusStr: '',
    payStatusStr: '',
    shippingStatus: '',
    totalNumArr:[],
    commentStatus: 0
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    new $App.quickNavigation() // 注册组件
    
    // 用户数据

    //this.getSearchResluts('加载数据');
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    if(this.data.orderType==4) {
      this.getSearchResluts('加载数据', 4);
    }else {
      this.getSearchResluts('加载数据');
    }
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
      return $App.shareAppMessageFn(false);
    }
  },
  // rzd 190103去评价 start
  goEval(e) {
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: '/pages/evaluate/evaluate?item=' + JSON.stringify(item),
    })
  },
  getSearchResluts: function(message, type) {
    
    var that = this;
    let parmas = type!=4?{
      curPage: this.data.curPage,
      pageSize: this.data.pageSize,
      payStatusStr: this.data.payStatusStr,
      shippingStatus: this.data.shippingStatus,
      orderStatusStr: this.data.orderStatusStr
    }:{
        curPage: this.data.curPage,
        pageSize: this.data.pageSize,
        commentStatus: this.data.commentStatus
    }
    API.getUserOrderList(parmas)
      .then((res) => {
        var contentlistTem = that.data.contentlist
        if (res.success) {
          if(res.obj) { //rzd 190107设置fromView用于评价页面界面回退 start
            let selectData = res.obj;
            selectData.map(item => {
              item.fromView = 'list'
            });
            res.obj = selectData;//rzd 190107设置fromView用于评价页面界面回退 end
          }
          if (that.data.curPage == 1) {
            contentlistTem = []
            this.setData({selectData:res.obj});
          }
          var contentlist = res.obj;
          if (contentlist) {
            if (contentlist.length < that.data.pageSize) {
              that.setData({
                contentlist: contentlistTem.concat(contentlist),
                hasMoreData: false
              })
            } else {
              that.setData({
                contentlist: contentlistTem.concat(contentlist),
                hasMoreData: true,
                
              })
            }
          } else {

            that.setData({message:"您还没有相关订单哦~"})
          }

        }

        if (!res.flag) {

          that.setData({message:"您还没有相关订单哦~"})

        }


        if (this.data.contentlist.length != 0 || this.data.contentlist != null){
          let arr = [];
          for (let i = 0; i < this.data.contentlist.length;i++){
            let num=0;
            for (let j = 0; j < this.data.contentlist[i].orderProductList.length;j++){
              num = num + this.data.contentlist[i].orderProductList[j].productNumber

            }
            arr[i]=num
          }
          this.setData({
            totalNumArr:arr
          })
        }

      })
      .catch((res) => {
        that.setData({message:res.message})
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
    if (type == 4) {
      that.setData({
        selectData: [],
        pageSize: 10
      })
      this.getSearchResluts('加载数据', 4)
    }
    

    
   

  },
  toDetail(e){
    var orderid = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/my-order-detail/my-order-detail?id='+orderid,
    })
  },
  delete(e){
    var that = this
    wx.showModal({
      title: '提示',
      content: '确定要删除吗？',
      success(res){
        if(res.confirm){
          API.deleteUserOrder({
            orderId: e.currentTarget.dataset.item.id
          }).then(res => {
            if (res.status) {
              that.getSearchResluts('加载数据');
            }
          })
        }else if (res.cancel){

        }
      }
    })
  }
})