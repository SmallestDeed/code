// pages/confirm-order/confirm-order.js
let API = getApp().API
let $App = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        addressList: [],
        productDetailsObj: {},
        orderRemask: '',
        defaultAddress: {}, // 默认地址
        resourcePath: getApp().resourcePath,
        totalPrice: 0 //总价格
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        wx.hideShareMenu();
        new $App.newNav() // 注册快速导航组件
        let item = JSON.parse(options.item)
        this.setData({
            productDetailsObj: item,
            totalPrice: parseFloat(item.price * item.purchaseNumber + Number(item.transportMoney * item.purchaseNumber)).toFixed(2),
            goodsPrice: parseFloat(item.price * item.purchaseNumber).toFixed(2)
        })
        
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
        this.getAddressList() // 获取用户地址    
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageObj
        }
    },
    toAddAddress() { // 跳转到添加收货地址
        wx.navigateTo({
            url: '/pages/mine/add-address/add-address',
        })
    },
    toAddressList() { // 跳转到地址列表
        wx.navigateTo({
            url: '/pages/mine/address-list/address-list',
        })
    },
    getAddressList() { // 获取用户地址列表
        if (wx.getStorageSync('defaultAddress')) {
            console.log(wx.getStorageSync('defaultAddress'));
            this.setData({
                defaultAddress: wx.getStorageSync('defaultAddress')
            })
        } else {
            API.getAddressList()
                .then(res => {
                    let obj = res.obj.length > 0 ? res.obj[0] : ''
                    this.setData({
                        addressList: res.obj,
                        defaultAddress: obj
                    })
                })
        }
    },
    immediatePayment() { // 立即支付
        let flag = this.data.defaultAddress;
        if (JSON.stringify(flag) === '{}' || flag == "" || !flag) {
            wx.showModal({
                title: '提示',
                content: '请填写收货地址',
                success: (res) => {
                    if (res.confirm) {
                        wx.navigateTo({
                            url: '/pages/mine/add-address/add-address',
                        })
                    }
                }
            })
        } else {
            API.setUpGoodOrder({
                    consignee: this.data.defaultAddress.consignee,
                    mobile: this.data.defaultAddress.mobile,
                    province: this.data.defaultAddress.province,
                    city: this.data.defaultAddress.city,
                    district: this.data.defaultAddress.district,
                    address: this.data.defaultAddress.address,
                    remark: this.data.orderRemask,
                    orderProductList: [{
                        "productId": this.data.productDetailsObj.productId,
                        "productCode": this.data.productDetailsObj.productCode || '',
                        "productNumber": this.data.productDetailsObj.purchaseNumber,
                        "productName": this.data.productDetailsObj.productName,
                        "productPrice": this.data.productDetailsObj.price,
                        "productStyleName": this.data.productDetailsObj.productStyleName || '默认',
                        "productColorName": this.data.productDetailsObj.productColorName || '默认',
                        "productPicPath": this.data.productDetailsObj.productPicPath
                    }]
                })
                .then(res => {
                    if (res.status) {
                        return API.getGoodsPayParams({
                            orderNo: res.obj.orderCode,
                            payMethod: 'miniPay'
                        })
                    } else {
                        return {
                            status: false
                        }
                    }
                })
                .then(params => {
                    if (params.status) {
                        wx.requestPayment({
                            'timeStamp': params.obj.timeStamp,
                            'nonceStr': params.obj.nonceStr,
                            'package': params.obj.packageStr,
                            'signType': 'MD5',
                            'paySign': params.obj.paySign,
                            'success': function(response) {
                                wx.showToast({
                                    title: '支付成功'
                                })
                                wx.navigateTo({
                                    url: '/pages/mine/my-order/my-order'
                                })
                            },
                            'fail': function(err) {
                                wx.showToast({
                                    title: '支付失败',
                                    icon: 'none'
                                })
                            }
                        })
                    }
                })
        }
    },
    inputRemask(e) {
        this.setData({
            orderRemask: e.detail.value
        })
    }
})