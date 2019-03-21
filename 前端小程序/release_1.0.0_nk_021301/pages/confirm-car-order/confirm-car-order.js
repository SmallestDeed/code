// pages/confirm-order/confirm-order.js
let fetch = getApp().fetch,
    myForEach = getApp().myForEach
let $App = getApp()
let lastSalePriceTotal = ''
let formatTime = require('../../utils/formatTime.js');
var {
    shareTitle
} = require('../../utils/config.js');
var shareTitle = {
    shareTitle
}.shareTitle;
let flagTitle = shareTitle == '诺克照明';
let lastTap = '';
let opt = {};
Page({

    /**
     * 页面的初始数据
     */
    data: {
        addressList: [],
        staticImageUrl: getApp().staticImageUrl,
        fCilck:false,
        productDetailsObj: {},
        orderRemaskList: [],
        defaultAddress: {}, // 默认地址
        resourcePath: getApp().resourcePath,
      staticImageUrl: getApp().staticImageUrl,
        salePriceTotal: 0,
        productParamsList: [],
        productPrice: '',
        transportMoney: '',
        couponList: [],
        couponListT: [],
        couponListF: [],
        isShowZzc: false,
        active: false,
        ssMoney: '',
        chindex: 0,
        couponId: '',
        flagTitle: flagTitle,
        lastChindex: 0,
        receiveNo: null,
        distributor: false,
        distributorObj: [],
        distributorVal: '',
        flagJxs:false,
        distrId: -1,
        businessType: wx.getStorageSync('businessType'),
    },
    chTap: function(e) {
        let chindex = e.currentTarget.dataset.chindex
        if (lastTap == chindex && this.data.chindex != '-1') {
            this.setData({
                chindex: '-1'
            })
        } else {
            this.setData({
                chindex: chindex
            })
            lastTap = chindex
        }
    },
    getDiscount: function(v) {
        return parseFloat(1 - v / 10).toFixed(2)
    },
    subTap: function() {
        let flags = this.data.chindex != '-1';
        let t = this.data.couponListT[this.data.chindex]
        let ssMoney = flags ? parseFloat(t.discountAmount).toFixed(2) : '';
        let allP = parseFloat(lastSalePriceTotal - (flags ? parseFloat(t.discountAmount).toFixed(2) : 0)).toFixed(2)
        if (flags && t.discountAmount == 0 && t.rebateFactor && t.rebateFactor > 0) {
            ssMoney = parseFloat(this.data.salePriceTotal * this.getDiscount(t.rebateFactor)).toFixed(2)
            allP = parseFloat(lastSalePriceTotal - ssMoney).toFixed(2)
        }
        this.setData({
            active: flags,
            ssMoney: ssMoney,
            isShowZzc: false,
            couponId: flags ? t.couponId : '',
            salePriceTotal: allP,
            lastChindex: this.data.chindex,
            receiveNo: flags ? t.receiveNo : null,
        })

    },
    /**
     * 生命周期函数--监听页面加载
     */
    getCanBeUsedCouponList: function(id, free) {
        let url = '/product/baseCompany/getCompanyId';
        let url2 = 'v1/sandu/mini/activity/getCanBeUsedCouponList';
        let that = this;
        fetch(url, 'get').then((res) => {
            if (res.obj) {
                let data = {
                    userId: wx.getStorageSync("userId"),
                    companyId: res.obj,
                    productId: id,
                    totalPrice: free
                }
                fetch(url2, 'post', data, 'shop').then((e) => {
                    if (e.data) {
                        let f = [],
                            t = [];
                        for (let i = 0; i < e.data.length; i++) {
                            e.data[i].canBeUseThisTime == 1 ? t.push(e.data[i]) : f.push(e.data[i]);
                        }
                        that.setData({
                            couponList: e.data,
                            couponListT: t,
                            couponListF: f,
                        })

                        setTimeout(function() {
                            if (t.length > 0) {
                                let ssMoney = parseFloat(t[0].discountAmount).toFixed(2) || 0;
                                let allP = parseFloat(lastSalePriceTotal - parseFloat(t[0].discountAmount).toFixed(2)).toFixed(2)
                                if (t[0].discountAmount == 0 && t[0].rebateFactor && t[0].rebateFactor > 0) {
                                    ssMoney = parseFloat(that.data.salePriceTotal * that.getDiscount(t[0].rebateFactor)).toFixed(2)
                                    allP = parseFloat(lastSalePriceTotal - ssMoney).toFixed(2)
                                }
                                that.setData({
                                    active: true,
                                    ssMoney: ssMoney,
                                    couponId: t[0].couponId,
                                    receiveNo:  t[0].receiveNo,
                                    chindex: 0,
                                    lastTap: 0,
                                    salePriceTotal: allP
                                })
                            }
                        }, 500)
                    }
                })
            }
        })
    },
    chooseTapT: function() {
        this.setData({
            isShowZzc: true,
        })
    },
    chooseTapF: function() {
        this.setData({
            isShowZzc: false,
            chindex: this.data.lastChindex
        })
    },
    onLoad: function(options) {
        opt = options;
        new $App.quickNavigation() // 注册组件
        let item = JSON.parse(options.item),
            salePriceTotal = 0,
            orderRemaskList = [];
        let a1 = 0,
            a2 = 0;
        this.setData({
            productDetailsObj: item
        })
        let idlist = '';
        for (let i = 0; i < item.length; i++) {
            idlist += i == 0 ? item[i].productId : ',' + item[i].productId;
        }
        myForEach(this.data.productDetailsObj, (value) => {
            orderRemaskList.push('')
            salePriceTotal += parseFloat(value.productPrice) * parseFloat(value.productNumber) + parseFloat(value.transportMoney) * parseFloat(value.productNumber)
            a1 += parseFloat(value.productPrice) * parseFloat(value.productNumber);
            a2 += parseFloat(value.transportMoney) * parseFloat(value.productNumber);
        })
        this.setData({
            salePriceTotal: salePriceTotal.toFixed(2),
            orderRemaskList: orderRemaskList,
            productPrice: a1.toFixed(2),
            transportMoney: a2.toFixed(2)
        })
        lastSalePriceTotal = this.data.salePriceTotal
        //flagTitle ? this.getCanBeUsedCouponList(idlist, parseFloat(salePriceTotal).toFixed(2)) : '';
        this.getCanBeUsedCouponList(idlist, parseFloat(salePriceTotal).toFixed(2))
    },
    onShow: function() {
        this.getAddressList()
    },
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(false);
        }
    },
    toAddAddress() { // 跳转到添加收货地址
        wx.navigateTo({
            url: '../add-address/add-address',
        })
    },
    toAddressList() { // 跳转到地址列表
        wx.navigateTo({
            url: '../address-list/address-list',
        })
    },
    getAddressList() { // 获取用户地址列表
        if (wx.getStorageSync('defaultAddress')) {
            this.setData({
                defaultAddress: wx.getStorageSync('defaultAddress')
            })
            this.getDistributorObj(wx.getStorageSync('defaultAddress').id);
            return
        }
        
        let url = `/order/getAddressByUserId`
        fetch(url, 'get')
            .then(res => {
                if (res.status) {
                    if (res.obj) {
                        this.setData({
                            addressList: res.obj,
                            flagJxs: res.obj.length > 0 ? true : false,
                            defaultAddress: res.obj[0]
                        })
                        this.getDistributorObj(res.obj[0].id);
                    }
                } else {
                    this.setData({
                        addressList: []
                    })
                }
            })
    },
    chooseBind(e) {
        let curr = e.currentTarget.dataset;
        
        this.setData({
            distrId: curr.i,
            distributorVal: curr.val,
            distributor: false,
            itemObj: curr.item
        })
    },
    closeDistributor() {
        this.setData({
            distributor: !this.data.distributor
        })
    },
    getDistributorObj(addressId) {
        let url = '/order/get/deliver/shop';
        fetch(url, 'get', { addressId: addressId, companyId: wx.getStorageSync('companyId') })
            .then(res => {
                for (let i = 0; i < res.obj.length; i++) {
                    res.obj[i].distributorVal = res.obj[i].provinceName + res.obj[i].cityName + res.obj[i].areaName + res.obj[i].streetName + res.obj[i].shopAddress
                }
                let flags = res.obj.length == 1;
                this.setData({
                    distrId: flags ? 0 : -1,
                    itemObj: flags ? res.obj[0] : '',
                    distributorVal: flags ? res.obj[0].distributorVal : ''
                })
                this.setData({
                    distributorObj: res.obj || [],
                    flagJxs: res.success && res.obj.length > 0 ? true : false
                })
            })
    },
    immediatePayment() { // 立即支付
        
        let flag = this.data.defaultAddress;
        let _that = this;
        if (JSON.stringify(flag) === '{}' || flag == "" || !flag) {
            wx.showModal({
                title: '提示',
                content: '请填写收货地址',
                success: (res) => {
                    if (res.confirm) {
                        wx.navigateTo({
                            url: '../add-address/add-address',
                        })
                    }
                }
            })
            return
        }           
        let itemObj = this.data.itemObj
        if (!itemObj && this.data.flagJxs && this.data.businessType!=9) {
            wx.showToast({
                title: '请选择配送商!',
                icon: 'none'
            })
            return
        }
        if (this.data.fCilck) {
            wx.navigateTo({
                url: '/pages/my-order/my-order',
            })
            return;
        } else {
            
        }
        let url = `/order/createorder`,
            params = []
        myForEach(this.data.productDetailsObj, (value, index) => {
            params.push({
                "productId": value.productId,
                "productCode": value.productCode || '',
                "productNumber": value.productNumber,
                "productName": value.productName,
                "productPrice": value.productPrice,
                "remark": this.data.remark,
                "productStyleName": value.productStyleName || '默认',
                "productColorName": value.productColorName || '默认',
                "productPicPath": value.productPicPath,
            })
        })
        let orderAmounts = this.data.salePriceTotal;
        fetch(url, 'post', {
                consignee: this.data.defaultAddress.consignee,
                mobile: this.data.defaultAddress.mobile,
                province: this.data.defaultAddress.province,
                city: this.data.defaultAddress.city,
                district: this.data.defaultAddress.district,
                address: this.data.defaultAddress.address,
                orderAmount: orderAmounts,
                orderProductList: params,
                remark: this.data.remark,
                couponId: this.data.couponId,
                couponUserNo: this.data.receiveNo ? this.data.receiveNo + '' : null,
                receiveNo: this.data.receiveNo ? this.data.receiveNo + '' : null,
                isCart: 1,
                orderSource: 0,
                shopId: itemObj.shopId,
                franchiserId: itemObj.companyId,
                deliverProvinceCode: itemObj.provinceCode,
                deliverCityCode: itemObj.cityCode,
                deliverAreaCode: itemObj.cityCode,
                deliverStreetCode: itemObj.streetCode,
                deliverAddress: itemObj.shopAddress,
                deliverAreaLongCode: itemObj.longAreaCode,
            })
            .then(res => {
                return res.status ? res.obj : false
            })
            .then(params => {
                // let flag = false
                if (params) {
                    let url = `/web/pay/miniProPayOrder/mallOrderPaying`
                    fetch(url, 'formData', {
                            orderNo: params.orderCode,
                            payMethod: 'miniPay'
                        }, 'pay')
                        .then(res => {
                            return res.status ? res.obj : false
                        })
                        .then((res) => {
                            if (res) {
                                wx.requestPayment({
                                    'timeStamp': res.timeStamp,
                                    'nonceStr': res.nonceStr,
                                    'package': res.packageStr,
                                    'signType': 'MD5',
                                    'paySign': res.paySign,
                                    'success': function(response) {
                                        wx.showToast({
                                            title: '支付成功'
                                        })
                                        wx.navigateTo({ // 成功跳转
                                            url: '/pages/my-order/my-order'
                                        })
                                        _that.setData({
                                            fCilck: true
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
            })
    },
    inputRemask(e) {
        this.setData({
            remark: e.detail.value
        })
    }
})