// pages/putInMyHouse/putInMyHouse.js
import fetch from '../../utils/fetch.js';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: '',
        pData: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        let pData = JSON.parse(options.planData)
        console.log(pData)
        this.setData({
            pData: pData
        })
        this.getUserInfo();
    },
    getUserInfo: function() {
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
    submitInfo(e) { //模板消息接口调用
        console.log(e, 'wq')
        wx.setStorage({
            key: 'formId',
            data: e.detail.formId,
        })
        let url = '/v1/notify/wx/saveUserRenderFormId'
        let data = {
            designPlanId: this.data.pData.planRecommendedId || this.data.pData.designPlanRecommendId, //方案id
            formId: e.detail.formId, //表单ID
            forwardPage: 'pages/index/index?navToUrl=/pages/my-tasks/my-tasks', //跳转页
            renderType: 0, //渲染类型：0，装进我加；1，替换渲染
            renderLevel: 4 //渲染级别：1，照片；4：720；6，视频；8，720
        }
        console.log(data);
        fetch(url, 'formData', data, 'system').then(res => {
            console.log(res, '亮眼')
        })
    },
    confirm(e) {
        let url = `/render/server/addRenderTask.htm`;
        let that = this;
        
        fetch(url, 'post', that.data.pData, 'render')
            .then((res) => {
                if (res.success) {
                    if (res.message === '创建渲染任务成功！') {
                        wx.showModal({
                            title: '正在渲染中...',
                            content: '预计3分钟后完成',
                            confirmText: '查看任务',
                            cancelText: '返回',
                            cancelColor: '#999999',
                            confirmColor: '#ff6419',
                            success: (res) => {
                                that.submitInfo(e);
                                that.getUserInfo();
                                if (res.confirm) {
                                    wx.navigateTo({
                                        url: '/pages/my-tasks/my-tasks'
                                    })
                                } else {

                                }
                            }
                        })
                    } else {
                        wx.showToast({
                            title: res.message,
                            icon: 'none'
                        })
                    }
                } else {
                    if (res.message === '账户度币不足，请充值.') {
                        wx.showModal({
                            title: '提示',
                            content: '度币余额不足，去充值？',
                            confirmText: '去充值',
                            cancelText: '暂不考虑',
                            cancelColor: '#999999',
                            confirmColor: '#ff6419',
                            success: (res) => {
                                if (res.confirm) {
                                    wx.navigateTo({
                                        url: '/pages/buy-points/buy-points'
                                    })
                                } else {

                                }
                            }
                        })
                    } else {
                        wx.showModal({
                            title: '提示',
                            content: '户型数量已用完，购买更多？',
                            confirmText: '去购买',
                            cancelText: '暂不考虑',
                            cancelColor: '#999999',
                            confirmColor: '#ff6419',
                            success: (res) => {
                                if (res.confirm) {
                                    wx.navigateTo({
                                        url: '/pages/purchase-house/purchase-house'
                                    })
                                }
                            }
                        })
                    }
                }
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
    onShareAppMessage: function() {

    },
    putInMyHouse() {

    }
})