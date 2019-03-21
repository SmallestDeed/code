// pages/mine/couponList/couponList.js
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
        imgURL: getApp().staticImageUrl,
        currTab: 0,
        pageNo: 1,
        pageNoList: {
            unUse: 1,
            used: 1,
            timeout: 1
        },
        tabContList: {
            unUse: [],
            used: [],
            timeout: []
        },
        showType:true,
        hasMore: {
            unUse: true,
            used: true,
            timeout: true
        }
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        this.setData({
            showType: options.type != 'home'
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
        this.setData({
            pageNoList: {
                unUse: 1,
                used: 1,
                timeout: 1
            },
            tabContList: {
                unUse: [],
                used: [],
                timeout: []
            },
            hasMore: {
                unUse: true,
                used: true,
                timeout: true
            }
        })
        this.getList();

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
        this.getList();
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(true);
        }
    },
    goUrls: function(e) {
        wx.switchTab({
            url: '../house-goods/house-goods'
        })
    },
    reloadTabList: function() {
        //unUse: true,
        //used: true,
        // timeout: true
        var currTab = this.data.currTab;
        if (currTab == 0 && this.data.hasMore.unUse == true) {
            this.unUse();
        } else if (currTab == 1 && this.data.hasMore.used == true) {
            this.used();
        } else if (currTab == 2 && this.data.hasMore.timeout == true) {
            this.timeout();
        }
    },
    changeTab: function(e) {
        var tab = e.currentTarget.dataset.tab;
        this.setData({
            currTab: tab
        })
        this.getList();
    },
    toHref: function(e) {
        var sHref = e.currentTarget.dataset.href;
        if (sHref == undefined)
            return;
        wx.navigateTo({
            url: sHref,
        })
    },
    getList: function() {
        var This = this;
        var currTab = this.data.currTab;
        var key = "unUse";
        var reqURL = '';
        var tabCont = [];
        if (currTab == 0) {
            key = "unUse";
            reqURL = 'v1/sandu/mini/activity/getUnUsedList';
        } else if (currTab == 1) {
            key = "used";
            reqURL = 'v1/sandu/mini/activity/getUsedList'
        } else {
            key = "timeout";
            reqURL = 'v1/sandu/mini/activity/getExpiredList';
        }
        console.log(key);
        tabCont = This.data.tabContList[key];
        if (!This.data.hasMore[key])
            return;
        var pageNo = This.data.pageNoList[key];
        if (pageNo == 1)
            tabCont = [];
        var reqData = {
            pageNo: This.data.pageNoList[key],
            pageSize: 10,
            userId: wx.getStorageSync("userId"),
            companyId: wx.getStorageSync("companyId") || ""
        }
        This.setData({
            ["hasMore." + key]: false
        })
        fetch(reqURL, 'get', reqData, 'shop').then(res => {
            if (res.code == 200) {
                if (!res.data){
                    This.setData({
                        ["hasMore." + key]: false
                    })
                    return;
                }
                    
                tabCont = tabCont.concat(res.data.list)
                This.setData({
                    ["tabContList." + key]: tabCont,
                    ["pageNoList." + key]: ++pageNo
                })
                if (tabCont.length == 0 || res.data.list.length < 10) {
                    This.setData({
                        ["hasMore." + key]: false
                    })
                }else{
                    This.setData({
                        ["hasMore." + key]: true
                    })
                }
            } else if (res.code == 400) {
                This.setData({
                    ["hasMore." + key]: false
                })
            } else {
                // wx.showToast({
                //   title: res.message || "网络错误 请稍后再试",
                //   icon: 'none'
                // })
            }
            setTimeout(function() {
                if (!reqData.companyId && reqData.companyId !== 0) {
                    wx.showToast({
                        title: "没有获取到您的公司Id",
                        icon: 'none'
                    })
                }
            }, 1000)
        })
    }
})