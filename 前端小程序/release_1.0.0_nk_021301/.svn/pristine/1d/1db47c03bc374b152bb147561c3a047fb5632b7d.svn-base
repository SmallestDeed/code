//logs.js
const util = require('../../utils/util.js')
var { shareTitle } = require('../../utils/config.js');
var shareTitle = { shareTitle }.shareTitle;
let flagTitle = shareTitle == '诺克照明';
let fetch = getApp().fetch, myForEach = getApp().myForEach
let $App = getApp(), API = getApp().API;
Page({
  data: {
    logs: [],
    id:'',
    detailList:[],
    staticImageUrl:getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    allPrice:'',
    orderAmount: '',
    totalTransportCost: '',
    discountPrice:'',
    allMoney:'',
    flagTitle: flagTitle,
      businessType: wx.getStorageSync('businessType'),
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageFn(false);
    }
  },
  onLoad: function (options) {
    new $App.quickNavigation() // 注册组件
    var that=this
    this.setData({
      logs: (wx.getStorageSync('logs') || []).map(log => {
        return util.formatTime(new Date(log))
      }),
      id: options.id
    })
    that.getOrderDetail()
  },
  getOrderDetail:function(){ 
    var that = this
    var url = "/order/getOrderByOrderId?id=" + that.data.id
    fetch(url, 'get')
      .then((res) => {
       let Num = res.obj.orderAmount * 100 - res.obj.totalTransportCost*100;
       Num=Num/100;
        if(res.status){
          that.setData({
            allPrice:Num.toFixed(2),
            orderAmount: parseFloat(res.obj.orderAmount).toFixed(2),
            totalTransportCost: parseFloat(res.obj.totalTransportCost).toFixed(2),
            detailList: res.obj,
            discountPrice: res.obj.discountPrice?parseFloat(res.obj.discountPrice).toFixed(2):false,
            allMoney: parseFloat(parseFloat(res.obj.orderAmount) + parseFloat(res.obj.discountPrice || 0)).toFixed(2),
          })
        }else{
          wx.showToast({
            title: '获取订单列表失败',
            icon: 'none',
            duration: 3000
          })
        }
      
      })
  },
  // rzd 190103去评价 start
  goEval() {
    this.data.detailList.fromView = 'detail';
    wx.navigateTo({
      url: '/pages/evaluate/evaluate?item=' + JSON.stringify(this.data.detailList),
    })
  },

  // 删除订单
  deleteBtn(e){
    console.log(e)
    var that = this
    wx.showModal({
      title: '提示',
      content: '确定要删除吗？',
      success(res) {
        if (res.confirm) {
          API.deleteUserOrder({
            orderId: e.currentTarget.dataset.id
          }).then(res => {
            if (res.status) {
              wx.navigateBack({
                delta: 1
              })
            }
          })
        } else if (res.cancel) {

        }
      }
    })
  },

  pay:function(){
    var that=this
   
    let url = `/web/pay/miniProPayOrder/mallOrderPaying`
    fetch(url, 'formData', {
      payMethod: 'miniPay',
      orderNo: that.data.detailList.orderCode
    }, 'pay')
      .then(res => {
        if (res.status) {
          return res.obj
        } else {
          wx.showToast({
            title: res.message,
            icon: 'none'
          })
        }
      })
      .then((res) => {
        if (res) {
          wx.requestPayment({
            'timeStamp': res.timeStamp,
            'nonceStr': res.nonceStr,
            'package': res.packageStr,
            'signType': 'MD5',
            'paySign': res.paySign,
            'success': function (response) {
              wx.navigateTo({
                url: '/pages/my-order/my-order',
                icon:'success'
              })
            },
            'fail': function (err) {
              console.log(err)
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
    var that = this
    console.log(that.data.detailList)
    var orderid = that.data.detailList.id
    
    var url = "/order/updateorderstatus?id="+orderid+"&orderStatus="+"2"
    fetch(url, 'get')
      .then((res) => {
        console.log(res)
        if (res.status){
         
          wx.showToast({
            title: '取消成功',
            icon: 'success',
            duration:3000,
            complete:function(){
              
              setTimeout(function () {
                //要延时执行的代码
                wx.navigateBack({
                  delta: 1
                })
              }, 3000) //延迟时间
            }
          })


        }else{

          wx.showToast({
            title: '取消失败',
            icon: 'none',
            duration: 3000,
            complete: function () {

              setTimeout(function () {
                //要延时执行的代码

                wx.navigateTo({
                  url: '/pages/my-order/my-order'
                })
              }, 3000) //延迟时间
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
  
    let url ="/order/updateorderstatus?id="+id+"&orderStatus=4&shippingStatus=2"
    fetch(url, 'get')
      .then((res) => {
        if (res.status) {

          wx.showToast({
            title: '确认成功',
            icon: 'success',
            duration: 3000,
            complete: function () {

              setTimeout(function () {
                //要延时执行的代码
                wx.navigateTo({
                  url: '/pages/my-order/my-order'
                })
              }, 3000) //延迟时间
            }
          })


        } else {

          wx.showToast({
            title: '确认失败',
            icon: 'none',
            duration: 3000,
            complete: function () {

              setTimeout(function () {
                //要延时执行的代码

                wx.navigateTo({
                  url: '/pages/my-order/my-order'
                })
              }, 3000) //延迟时间
            }
          })
        }
      })
  }


})
