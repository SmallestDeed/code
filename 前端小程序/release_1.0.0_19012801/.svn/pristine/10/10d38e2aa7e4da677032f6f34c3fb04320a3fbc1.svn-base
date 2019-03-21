
// pages/confirm-order/confirm-order.js
let myForEach = getApp().myForEach, API = getApp().API
let $App = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        addressList: [],
        productDetailsObj: {},
        orderRemaskList: [],
        defaultAddress: {}, // 默认地址
        resourcePath: getApp().resourcePath,
        salePriceTotal: 0,
        productParamsList: []
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        wx.hideShareMenu();
        new $App.newNav() // 注册快速导航组件
        let itemStr = options.item.replace('$', '&')
        let item = JSON.parse(itemStr), salePriceTotal = 0, orderRemaskList = [];
        myForEach(item, (value) => {
            orderRemaskList.push('')
            salePriceTotal += value.productPrice * value.productNumber + Number(value.transportMoney)
        })
        this.setData({
            salePriceTotal: parseFloat(salePriceTotal).toFixed(2),
            orderRemaskList: orderRemaskList,
            productDetailsObj: item
        })
        this.getAddressList() // 获取用户地址
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
        this.getAddressList()
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
            this.setData({
                defaultAddress: wx.getStorageSync('defaultAddress')
            })
        } else {
            API.getAddressList()
                .then(res => {
                    if (res.status) {
                        if (res.obj) {
                            this.setData({ addressList: res.obj, defaultAddress: res.obj[0] })
                        }
                    } else {
                        this.setData({ addressList: [] })
                    }
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
                        wx.navigateTo({ url: '/pages/add-address/add-address' })
                    }
                }
            })
        } else {
            let params = []
            myForEach(this.data.productDetailsObj, (value, index) => {
                params.push(
                    {
                        "productId": value.productId,
                        "productCode": value.productCode || '',
                        "productNumber": value.productNumber,
                        "productName": value.productName,
                        "productPrice": value.productPrice,
                        "remark": this.data.orderRemaskList[index],
                        "productStyleName": value.productStyleName || '默认',
                        "productColorName": value.productColorName || '默认',
                        "productPicPath": value.productPicPath
                    }
                )
            })
            API.setUpGoodOrder({
                consignee: this.data.defaultAddress.consignee,
                mobile: this.data.defaultAddress.mobile,
                province: this.data.defaultAddress.province,
                city: this.data.defaultAddress.city,
                district: this.data.defaultAddress.district,
                address: this.data.defaultAddress.address,
                orderAmount: this.data.salePriceTotal,
                orderProductList: params,
                isCart: 1
            })
                .then(res => {
                    if (res.status) { return API.getGoodsPayParams({ orderNo: res.obj.orderCode, payMethod: 'miniPay' }) }
                    else { return { status: false } }
                })
                .then(params => {
                    if (params.status) {
                        wx.requestPayment({
                            'timeStamp': params.obj.timeStamp,
                            'nonceStr': params.obj.nonceStr,
                            'package': params.obj.packageStr,
                            'signType': 'MD5',
                            'paySign': params.obj.paySign,
                            'success': function (response) {
                                wx.showToast({ title: '支付成功' })
                                wx.navigateTo({ url: '/pages/my-order/my-order' })
                            },
                            'fail': function (err) {
                                wx.showToast({ title: '支付失败', icon: 'none' })
                            }
                        })
                    }
                })
        }
    },
    inputRemask(e) {
        let index = e.currentTarget.dataset.index
        this.data.orderRemaskList[index] = e.detail.value
        this.setData({
            orderRemaskList: this.data.orderRemaskList
        })
    }
})