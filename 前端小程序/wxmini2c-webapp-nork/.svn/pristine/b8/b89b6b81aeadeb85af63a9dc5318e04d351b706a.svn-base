// pages/brandHall/storeCoupon/storeCoupon.js
import {
    resourcePath
} from '../../utils/config.js'
import fetch from '../../utils/fetch.js';
let $App = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        imgURL: getApp().data.imgURL,
      staticImageUrl: getApp().staticImageUrl,
        resourcePath: resourcePath,
        shopId: "",
        couList: [],
        introList: [],
        targetData: [],
        query: {
            pageNo: 1,
            pageSize: 2,
            userId: wx.getStorageSync("userId")
        },
        isFromPersonal: false
    },

    /**
     * 生命周期函数--监听页面加载
     */
    goUrls: function(e) {
        wx.switchTab({
            url: '../house-goods/house-goods'
        })
    },
    onLoad: function(options) {
        if (options.from && options.from == "personal") {
            this.setData({
                isFromPersonal: true
            })
        }
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {
        this.getCouponList();
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
        var This = this;
        setTimeout(function() {
            This.getIntroList();
        }, 1000)
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(true);
        }
    },
    toHref: function (e) {
        wx.navigateTo({
            url: '/pages/hotGoodsList/hotGoodsList'
        })
    },
    toHref2: function (e) {
        wx.navigateTo({
            url: '/pages/my-card/my-card?type=home'
        })
    },
    goGoodsInfo: function(e) {
        let id = e.currentTarget.dataset.id;
        wx.navigateTo({
            url: '/pages/goods-details/goods-details?id=' + id,
        })

    },
    getCouponList: function() {
        var This = this;
        var shopId = this.data.shopId;
        var reqData = {
            userId: wx.getStorageSync("userId"),
            companyId: wx.getStorageSync("companyId") || ""
        }
        fetch('v1/sandu/mini/activity/getWaitingReceiveList', 'get', reqData, 'shop').then(res => {
            if (res.code == 200) {
                if (!res.data)
                    return;
                res.data.list = res.data.list || [];
                for (var i = 0; i < res.data.list.length; i++) {
                    var item = res.data.list[i];
                    let flag = res.data.list[i].effectiveDateMode == 2 && res.data.list[i].receiveState == 1 && res.data.list[i].isEffectiveDate == 0
                    res.data.list[i].isShow = flag ? false : true;
                    if (res.data.list[i].startTime) {
                        res.data.list[i].startTime = This.getDate(res.data.list[i].startTime)
                    }
                    if (res.data.list[i].endTime) {
                        res.data.list[i].endTime = This.getDate(res.data.list[i].endTime)
                    }
                }

                This.setData({
                    targetData: res.data.list || []
                })
            } else {
                // wx.showToast({
                //   title: res.message || "网络错误 请稍后再试",
                //   icon: 'none'
                // })
            }

            if (!reqData.companyId && reqData.companyId !== 0) {
                setTimeout(function() {
                    wx.showToast({
                        title: "没有获取到您的公司Id",
                        icon: 'none'
                    })
                }, 1000)
            }

        }).then(res => {})
    },
    receiveCoupon: function(e) {
        var couponId = e.target.dataset.couid;
        if (!couponId)
            return;
        var This = this;
        var reqData = {
            couponId: couponId,
            userId: wx.getStorageSync("userId")
        }
        fetch('v1/sandu/mini/activity/receive', 'get', reqData, 'shop').then(res => {
            if (res.code == 200) {
                wx.showToast({
                    title: "领取成功",
                    icon: 'success'
                })
                This.getCouponList();
            } else if (res.code == 10000102) {
                wx.showToast({
                    title: res.message,
                    icon: 'none'
                })
            }
        })
    },
    getIntroList: function() {
        var This = this;
        var companyId = wx.getStorageSync("companyId")
        // if (!companyId && companyId!="0"){
        //   wx.showToast({
        //     title: "获取热销商品列表失败",
        //     icon: 'none'
        //   })
        //   return;
        // }
        let data = {
            pageSize: 2,
            pageNo: 1,
            companyId: companyId || "",
        }
        fetch('v1/sandu/mini/goodsrecommend/getTopList', 'get', data, 'shop').then(res => {
            if (res.code == 200) {
                This.setData({
                    introList: res.data.list
                })
            } else {
                // wx.showToast({
                //   title: res.message || "网络错误 请稍后再试",
                //   icon: 'none'
                // })
            }
        }).then(res => {})
    },
    getDate(timeStamp) {
        if (!timeStamp)
            return '';
        var dDate = new Date(timeStamp);
        var YYYY = dDate.getFullYear(),
            MM = (dDate.getMonth() + 1) > 9 ? (dDate.getMonth() + 1) : "0" + (dDate.getMonth() + 1),
            dd = dDate.getDate() > 9 ? dDate.getDate() : "0" + dDate.getDate(),
            HH = dDate.getHours() > 9 ? dDate.getHours() : "0" + dDate.getHours(),
            mm = dDate.getMinutes() > 9 ? dDate.getMinutes() : "0" + dDate.getMinutes(),
            ss = dDate.getSeconds() > 9 ? dDate.getSeconds() : "0" + dDate.getSeconds();
        var str = YYYY + "." + MM + "." + dd;
        return str;
    },
})