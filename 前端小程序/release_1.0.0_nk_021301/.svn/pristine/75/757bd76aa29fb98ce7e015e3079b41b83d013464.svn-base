// pages/goods-details/goods-details.js
let fetch = getApp().fetch
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    radioItems: [
            {name: '1套户型 = 100度币', value: '0', checked: true},
            {name: '1套户型 = 200度币', value: '1'},
            
        ],
    wxInfo: '',
    score:'',
    scorePrinceList:[],
    selectNub:0
  },
 
  
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    new $App.quickNavigation() // 注册组件
    var that=this
    this.getUserInfo();
    var wxInfo = getApp().globalData;

  
    this.setData({
      wxInfo: wxInfo,
      score: options.score
    })

    that.getScorePrince()
  },
  getScorePrince:function(){
    var that=this
    var url ='/web/pay/payOrder/getRechargeIntegral'
    fetch(url,'POST','','pay')
      .then((res) => {
         console.log(res) 
         if (res.success){
        that.setData({
          scorePrinceList:res.obj
        })
         }else{
           wx.showToast({
             title: '获取产品列表失败',
             icon: 'none',
             duration: 3000
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
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageFn(false);
    }
  },
  switchGoodsAndCase(e) { // 商品及案例的切換
   
  },
  radioChange: function (e) {
        console.log('radio发生change事件，携带value值为：', e.detail.value);

        var radioItems = this.data.radioItems;
        for (var i = 0, len = radioItems.length; i < len; ++i) {
            radioItems[i].checked = radioItems[i].value == e.detail.value;
        }

        this.setData({
            radioItems: radioItems
        });
    },
  getUserInfo: function () {
    let url = "/web/system/sysUser/getUserBalance"
    var that = this;
    fetch(url, 'get', '', 'pay')
      .then((res) => {
        var userInfo = res.obj;
        that.setData({
          userInfo: userInfo
        })
      })
  },
  select:function(e){
    var that=this
    let index =e.currentTarget.dataset.index
    that.setData({
      selectNub:index
    })
  },
  pay:function(){
    var that = this;
    var i = that.data.selectNub
    let id = that.data.scorePrinceList[that.data.selectNub].rechargeId
    let url = "/web/pay/miniProPayOrder/recharge"
        fetch(url, 'formData', {
          productId: id,
          payMethod: 'miniPay'
        }, 'pay')
        .then((res) => {
          if (res.status){
            wx.requestPayment({
              'timeStamp': res.obj.timeStamp,
              'nonceStr': res.obj.nonceStr,
              'package': res.obj.packageStr,
              'signType': 'MD5',
              'paySign': res.obj.paySign,
              'success': function (response) {
                console.log(response)
                wx.showToast({
                  title: '充值成功',
                  icon: 'success',
                  duration: 3000,
                  complete: function () {

                    setTimeout(function () {
                      //要延时执行的代码

                      wx.navigateBack({
                        delta: 1
                      })
                    }, 3000) //延迟时间
                  }
                })
              },
              'fail': function (err) {
                wx.showToast({
                  title: '充值失败',
                  icon: 'none',
                  duration: 3000,
                  complete: function () {

                    setTimeout(function () {
                      //要延时执行的代码

                      wx.navigateBack({
                        delta: 1
                      })
                    }, 3000) //延迟时间
                  }
                })
              }
            })
          }else{
            wx.showToast({
              title: '充值失败',
              icon: 'none',
              duration: 3000,
              complete: function () {

                setTimeout(function () {
                  //要延时执行的代码

                  wx.navigateBack({
                    delta: 1
                  })
                }, 3000) //延迟时间
              }
            })
          }
        })
  }
})