
//logs.js
const util = require('../../utils/util.js')
let API = getApp().API
let $App = getApp()
Page({
  data: {
    logs: [],
    id:'',
    detailList:[],
    resourcePath: getApp().resourcePath,
    allPrice:''
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  onLoad: function (options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
    var that=this
    this.setData({
      logs: (wx.getStorageSync('logs') || []).map(log => {
        return util.formatTime(new Date(log))
      }),
      id: options.id
    })
    console.log(options)
    that.getOrderDetail()
  },
  getOrderDetail:function(){ 
    var that = this
    var url = "/order/getOrderByOrderId?id=" + that.data.id
    API.getUserOrderDetails({ id: this.data.id})
    .then((res) => {
      let Num = res.obj.orderAmount * 100 - res.obj.totalTransportCost * 100;
      Num = Num / 100;
      if (res.status) {
        that.setData({ allPrice: Num.toFixed(2), detailList: res.obj })
      } else {
        wx.showToast({ title: '获取订单列表失败', icon: 'none', duration: 3000 })
      }
    })
  },
  pay:function(){
    let that=this
    API.getGoodsPayParams({ payMethod: 'miniPay', orderNo: that.data.detailList.orderCode })
    .then(res => {
      return res
    })
    .then(res => {
      if (res.status) {
        wx.requestPayment({
          'timeStamp': res.obj.timeStamp,
          'nonceStr': res.obj.nonceStr,
          'package': res.obj.packageStr,
          'signType': 'MD5',
          'paySign': res.obj.paySign,
          'success': function (response) {
            wx.showToast({ title: '支付成功'})
            setTimeout(() => { wx.navigateTo({ url: '/pages/my-order/my-order' }) }, 1500)
          },
          'fail': function (err) {
            wx.showToast({ title: '支付失败', icon: 'none'})
          }
        })
      }
    })
  },
  buyAgain:function(){
    var that=this
    var id = that.data.detailList.orderProductList[0].productId
    wx.navigateTo({
      url: '/pages/goods-details/goods-details?id='+id,
    })
  },
  cancle:function(){
    let that = this
    let orderid = that.data.detailList.id
    API.cancelUserOrder({ id: orderid, orderStatus: 2})
    .then(res => {
      if (res.status) {
        wx.showToast({
          title: '取消成功',
          icon: 'success',
          duration: 3000,
          complete: function () {
            setTimeout(function () { wx.navigateBack({ delta: 1 })
            }, 3000)
          }
        })
      } else {
        wx.showToast({
          title: '取消失败',
          icon: 'none',
          duration: 3000,
          complete: function () {
            setTimeout(function () { wx.navigateTo({ url: '/pages/my-order/my-order' })
            }, 3000)
          }
        })
      }
    })
  },
  confirm:function(e){
    var that = this
    let id = that.data.detailList.id
     /* `order_status` int(11) unsigned DEFAULT '0' COMMENT '订单的状态; 0未确认,1已确认,2已取消,3无效,4已完成,5退货',
        `shipping_status` int(11) unsigned DEFAULT '0' COMMENT '商品配送情况; 0未发货,1已发货,2已收货,4退货',
          `pay_status` int(11) unsigned DEFAULT '0' COMMENT '支付状态; 0未付款;1付款中;2已付款',*/
    API.cancelUserOrder({ id: orderid, orderStatus: 2 })
      .then(res => {
        if (res.status) {
          wx.showToast({
            title: '确认成功',
            icon: 'success',
            duration: 3000,
            complete: function () {
              setTimeout(function () {
                wx.navigateBack({ delta: 1 })
              }, 3000)
            }
          })
        } else {
          wx.showToast({
            title: '确认失败',
            icon: 'none',
            duration: 3000,
            complete: function () {
              setTimeout(function () {
                wx.navigateTo({ url: '/pages/my-order/my-order' })
              }, 3000)
            }
          })
        }
      })
  }
})
