// pages/confirm-order/confirm-order.js
let fetch = getApp().fetch,
   API = getApp().API
var {
    shareTitle
} = require('../../utils/config.js');
var shareTitle = {
    shareTitle
}.shareTitle;
let $App = getApp()
let lastSalePriceTotal = ''
let lastTap = '';
let flagTitle = shareTitle == '诺克照明';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        isFail:false,//满团 弹框
        purchaseOpenId:'',
        skuId:'',
        spuId:'',
        activeId:'',//拼团ID
        isMaster:'',//是否发起人
        isAssem:'',//是否团购订单
        addressList: [],
        productDetailsObj: {},
        orderRemask: '',
        defaultAddress: {}, // 默认地址
        resourcePath: getApp().resourcePath,
        totalPrice: 0, //总价格
        couponList: [],
        couponListT: [],
        staticImageUrl: getApp().staticImageUrl,
        couponListF: [],
        fCilck:false,
        isShowZzc: false,
        active: false,
        ssMoney: '',
        chindex: 0,
        couponId: '',
        allMoney: '',
        flagTitle: flagTitle,
        fastList: '',
        goodsPrice: '',
        lastChindex: 0,
        distributor:false,
        distributorObj:[],
        distributorVal:'',
        distrId:-1,
        flagJxs:false,
        businessType: wx.getStorageSync('businessType'),
        receiveNo: null,
      formId: [],
      formIndex: 0,
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
    chooseBind(e){
        let curr = e.currentTarget.dataset;
        console.log(curr.item);
        this.setData({
            distrId: curr.i,
            distributorVal: curr.val,
            distributor: false,
            itemObj: curr.item
        })
    },
    subTap: function() {
        let flags = this.data.chindex != '-1';
        let t = this.data.couponListT[this.data.chindex]
        let ssMoney = flags ? parseFloat(t.discountAmount).toFixed(2) : '';
        if (flags && t.discountAmount == 0 && t.rebateFactor && t.rebateFactor > 0) {
            ssMoney = parseFloat(this.data.goodsPrice * (1 - t.rebateFactor / 10)).toFixed(2)
        }
        this.setData({
            active: flags,
            ssMoney: ssMoney,
            isShowZzc: false,
            couponId: flags ? t.couponId : '',
            lastChindex: this.data.chindex,
            receiveNo: flags ? t.receiveNo : null,
        })
        let allMoney = lastSalePriceTotal;
        allMoney = this.data.ssMoney ? (parseFloat(allMoney - this.data.ssMoney).toFixed(2)) : allMoney;

        this.setData({
            totalPrice: allMoney,
        })

    },
    /**
     * 生命周期函数--监听页面加载
     */
    getCanBeUsedCouponList: function(id, free) {
        let url = '/product/baseCompany/getCompanyId';
        let url2 = 'v1/sandu/mini/activity/getCanBeUsedCouponList';
        let that = this;
        lastSalePriceTotal = free;
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
                                let ssMoney = parseFloat(t[0].discountAmount).toFixed(2);
                                if (t[0].discountAmount == 0 && t[0].rebateFactor && t[0].rebateFactor > 0) {
                                    ssMoney = parseFloat(that.data.goodsPrice * (1 - t[0].rebateFactor / 10)).toFixed(2)
                                }
                                that.setData({
                                    active: true,
                                    ssMoney: ssMoney,
                                    couponId: t[0].couponId,
                                    receiveNo: t[0].receiveNo,
                                    chindex: 0,
                                    lastTap: 0
                                })
                                console.log(that.data.receiveNo);
                                let allMoney = lastSalePriceTotal;
                                allMoney = that.data.ssMoney ? (parseFloat(allMoney - that.data.ssMoney).toFixed(2)) : allMoney;
                                that.setData({
                                    totalPrice: allMoney,
                                })
                            }
                        }, 200)

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
        new $App.quickNavigation() // 注册组件
        let itemStr = options.item.replace('$', '&')
        let item = JSON.parse(itemStr)
        let allMoney = parseFloat(item.price * item.purchaseNumber + Number(item.transportMoney * item.purchaseNumber)).toFixed(2);
        allMoney = this.data.ssMoney ? (parseFloat(allMoney).toFixed(2) - parseFloat(this.data.ssMoney).toFixed(2)) : allMoney;
        this.setData({
            productDetailsObj: item,
            totalPrice: allMoney,
            goodsPrice: parseFloat(item.price * item.purchaseNumber).toFixed(2),
            isAssem:wx.getStorageSync('isAssemble'),
            isMaster:options.isMather,
            activeId:options.activeId,
            skuId:options.skuId,
            spuId:options.spuId
        })
        console.log('this.data.productDetailsObj.productPicPath----------', item.productPicPath);
        if(options.isMather=='0'){
            this.setData({
                purchaseOpenId: options.purchaseOpenId
            })
        }
        //flagTitle ? this.getCanBeUsedCouponList(item.productId, parseFloat(allMoney).toFixed(2)) : '';
        this.getCanBeUsedCouponList(item.productId, parseFloat(allMoney).toFixed(2))
        //this.getAddressList() // 获取用户地址
    },
    onShow: function() {
        this.getAddressList() // 获取用户地址    
    },

    onShareAppMessage: function() {
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
                res.status && res.obj.length !== 0 ? this.setDataFun(res.obj, res.obj[0]) : this.setDataFun()
                this.getDistributorObj(res.obj[0].id);
            })
    },
    setDataFun: function(a, b) {
        this.setData({
            addressList: a || [],
            defaultAddress: b || {}
        })
    },
    closeDistributor(){
        this.setData({
            distributor: !this.data.distributor
        })
    },
    getDistributorObj(addressId){
        let url = '/order/get/deliver/shop';
        fetch(url, 'get', { addressId: addressId, companyId: wx.getStorageSync('companyId')})
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
                    distributorObj: res.obj||[],
                    flagJxs: res.success && res.obj.length>0?true:false
                })
            })
    },
    immediatePayment(e) { // 立即支付
      let that = this
      this.data.formIndex++;
      this.data.formId.push(e.detail.formId);
      this.setData({
        formId: this.data.formId,
        formIndex: this.data.formIndex
      })
      console.log(this.data.formId,this.data.formIndex)
      if (this.data.formIndex >= 3 ) {
        API.collectMiniUserFormId(this.data.formId).then(res => {
          console.log(res)
          this.setData({
            formIndex: 0,
            formId: []
          })
        })
      }
        if (this.data.fCilck&&!this.data.isAssem) {
            wx.navigateTo({
                url: '/pages/my-order/my-order',
            })
            return;
        } else {
            
        }
        let flag = this.data.defaultAddress;
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
        let data = {
            consignee: this.data.defaultAddress.consignee,
            mobile: this.data.defaultAddress.mobile,
            province: this.data.defaultAddress.province,
            city: this.data.defaultAddress.city,
            district: this.data.defaultAddress.district,
            address: this.data.defaultAddress.address,
            remark: this.data.orderRemask,
            couponId: this.data.couponId,
            receiveNo: this.data.receiveNo ? this.data.receiveNo + '' : null,
            couponUserNo: this.data.receiveNo ? this.data.receiveNo + '' : null,
            orderAmount: this.data.productDetailsObj.salePrice * this.data.productDetailsObj.purchaseNumber,
            orderProductList: [{
                "productId": this.data.productDetailsObj.productId,
                "productCode": this.data.productDetailsObj.productCode || '',
                "productNumber": this.data.productDetailsObj.purchaseNumber,
                "productName": this.data.productDetailsObj.productName,
                "productPrice": this.data.productDetailsObj.price,
                "productStyleName": this.data.productDetailsObj.productStyleName || '默认',
                "productColorName": this.data.productDetailsObj.productColorName || '默认',
                "productPicPath": this.data.productDetailsObj.productPicPath,
                remark: this.data.orderRemask,
            }]
        }
        
        if (!itemObj && this.data.flagJxs && this.data.businessType!=9) {
            wx.showToast({
                title: '请选择配送商!',
                icon: 'none'
            })
            return
        }
        if (this.data.flagJxs && this.data.businessType!=9) {
            data.orderSource = 0
            data.shopId = itemObj.shopId
            data.franchiserId = itemObj.companyId
            data.deliverProvinceCode = itemObj.provinceCode
            data.deliverCityCode = itemObj.cityCode
            data.deliverAreaCode = itemObj.areaCode
            data.deliverStreetCode = itemObj.streetCode
            data.deliverAddress = itemObj.shopAddress
            data.deliverAreaLongCode = itemObj.longAreaCode
        }
        let req ='';
        if (!this.data.isAssem) {
           let url = `/order/createorder`
           req = fetch(url, 'post', data);
        }else{
            //检验是否满团接口
            // if (this.data.isMaster==0){
            //    API.checkAssembleStatus({
            //        purchaseOpenId: this.data.purchaseOpenId
            //    }).then(res => {
            //        if (!res.obj) {
            //            this.setData({
            //                isFail: true
            //            })
            //            return
            //        }
            //    })
            // }
                let url = `/v1/group/purchase/pay`
                data.userId = wx.getStorageSync('userId')
                data.mobile = this.data.defaultAddress.mobile,
                data.isMaster =  parseInt(this.data.isMaster) //是否发起人
                data.companyId = wx.getStorageSync('companyId')
                data.skuNum = this.data.productDetailsObj.purchaseNumber, //下单数量
                data.purchaseActiveId = this.data.activeId //拼团活动id
                data.skuId = this.data.skuId, //拼团 规格id
                data.spuId = this.data.spuId //拼团 商品id
                data.purchaseOpenId = this.data.purchaseOpenId //参团 必填 的 团id
                console.log(data)
                if (data.isMaster == 1) {
                    data.purchaseOpenId = undefined
                }
                data.couponId==''?data.couponId=undefined:''
                req = fetch(url, 'post', data, 'system');
        }
       
          if (!this.data.isAssem) {
              req.then(res => {
                      return res.status ? res.obj : false
                  })
                  .then(params => {
                      if (params) {
                          let url = `/web/pay/miniProPayOrder/mallOrderPaying`
                          fetch(url, 'formData', {
                                  payMethod: 'miniPay',
                                  orderNo: params.orderCode
                              }, 'pay')
                              .then(res => {
                                  return res.status ? res.obj : false
                              })
                              .then((res) => {
                                  if (res) {
                                      that.commonPay(res)
                                  }
                              })
                      }
                  })
          }else{
              req.then(res => { 
                  if (res.msgId &&res.msgId==-1) {
                      this.setData({
                          isFail: true
                      })
                      return
                  } else if (res.success == false) {
                        wx.showToast({
                            title: res.message,
                            icon: 'none',
                            duration: 2000
                        })
                        return
                    }  
                  let bodyRes = res.obj;
                  bodyRes.packageStr = bodyRes.package
                  that.commonPay(bodyRes)
              })
          }
    },
    commonPay(res){
    let that=this;
    that.setData({
        fCilck: true
    })
    wx.requestPayment({
        'timeStamp': res.timeStamp,
        'nonceStr': res.nonceStr,
        'package': res.packageStr,
        'signType': 'MD5',
        'paySign': res.paySign,
        'success': function (response) {
            wx.showToast({
                title: '支付成功',
            })
            if (!that.data.isAssem) {
              wx.navigateTo({ // 成功跳转
                  url: '/pages/my-order/my-order'
              })
            }else{
                //拼团订单 成功跳转
                wx.navigateTo({ // 成功跳转
                    url: `/pages/assemble-detail/assemble-detail?id=${res.purchaseOpenId}`
                })
            }
            
        },
        'fail': function (err) {
           
            //如果是拼团商品  跑一下取消订单接口
            if (that.data.isAssem) {
               API.cancelAssembleOrder({
                   orderNo: res.orderNo
               }).then(res =>{
                   if(res.success){
                       wx.showToast({
                         title: '取消订单成功!',
                         icon: 'none'
                     })
                   }else{
                       wx.showToast({
                           title: res.message,
                           icon: 'none'
                       })
                   }
                     
               })
            }else{
                wx.showToast({
                    title: '支付失败',
                    icon: 'none'
                })
            }
            // setTimeout(function () {
            //     console.log(1);
            //     wx.navigateBack({ changed: true });
            // }, 300)
        }
    })
    },
    inputRemask(e) {
        this.setData({
            orderRemask: e.detail.value
        })
    },
    //拼团 校验是否满团 接口
    closeDialog(e){
        console.log(e.currentTarget.dataset.type)
        this.setData({
            isFail: false
        })
        if (e.currentTarget.dataset.type=='open') {
            this.setData({
                isMaster:1//变为发起人
            })
           this.immediatePayment()
        }
    }
})