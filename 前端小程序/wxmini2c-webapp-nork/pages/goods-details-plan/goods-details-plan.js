// pages/goods-details-plan/goods-details-plan.js
let fetch = getApp().fetch,
    myForEach = getApp().myForEach,
    mySplitUrl = getApp().mySplitUrl,
    myCompoundUrl = getApp().myCompoundUrl,
    $App = getApp()
import {
    emptyTemplate
} from '../../component/emptyTemplate/emptyTemplate'
let opt = {};
Page({

    /**
     * 页面的初始数据
     */
    data: {
        recommendPlanList: [],
        resourcePath: getApp().resourcePath,
        pageSize: 10,
        sevenUrl: getApp().sevenUrl,
        favoriteRequest: true,
        collectRequest: true,
        optionsId: '',
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        opt = options
        this.setData({
            optionsId: options.id
        })

    },
    getRecommendPlan(productId) { // 方案列表
        let url = 'fullsearch-app/goods/recommendationplan/search/list';

        fetch(url, 'get', {
            "spuId": parseInt(productId),
            "start": 0,
            "limit": this.data.pageSize
        }, 'fullsearch').
        then(res => {
            let flag = res.success && res.datalist ? true : false;
            this.setData({
                recommendPlanList: flag ? res.datalist : [],
                planNum: flag ? res.totalCount : 0
            })
            this.emptyTemplateShow(flag ? 'hide' : 'show')
        })
    },
    onShow: function() {
        this.getRecommendPlan(this.data.optionsId)
    },
    onReachBottom: function() {
        if (this.data.pageSize >= this.data.planNum) {
            return
        } else {
            this.setData({
                pageSize: this.data.pageSize + 5
            })
            this.getRecommendPlan(this.data.optionsId)
        }
    },
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return $App.shareAppMessageFn(true, '?id=' + opt.id);
        }
    },
    formToThreeD(e) { // 720
        let type = e.detail.target.dataset.type,
            item = e.detail.target.dataset.item,
            routerQueryType = '',
            formId = e.detail.formId
        if (type === '720' || type === 'roam') {
            let routerQueryType = ''
            if (type === '720') {
                routerQueryType = 'seven'
            } else {
                routerQueryType = 'roam'
            }
            let sevenObj = {
                token: wx.getStorageSync('token'),
                platformCode: 'brand2c',
                operationSource: 1,
                planId: item.designPlanRecommendId,
                routerQueryType: routerQueryType,
                customReferer: $App.wxUrl,
                platformNewCode: 'miniProgram',
                formId: formId
            }
            let webUrl = this.data.sevenUrl
            for (let key in sevenObj) {
                webUrl += key + '=' + sevenObj[key] + '&'
            }
            getApp().data.webUrl = webUrl
            $App.myNavigateBack('pages/web-720/web-720')
            return
        }
    },
    toThreeD(e) { // 调转到3D界面
        let url = `/mobile/designPlanRecommended/getRecommendedPictureList.htm`,
            item = e.currentTarget.dataset.item,
            type = e.currentTarget.dataset.type
        fetch(url, 'post', {
                planRecommendedId: item.designPlanRecommendId,
                remark: type
            }, 'render')
            .then(res => {
                if (res.success) {
                    if (type == 'photo') {
                        return res.datalist[0].pid
                    } else {
                        return res.datalist[0].id
                    }
                } else {
                    return false
                }
            })
            .then(res => {
                if (res) {
                    let url = `/mobile/design/designPlan/getPanoPicture.htm`
                    fetch(url, 'post', {
                            thumbId: res
                        }, 'render')
                        .then(res => {
                            if (res.success) {
                                if (type == 'photo') {
                                    this.enlargeImage(res.obj.url)
                                } else if (type == 'video') {
                                    this.toVideo(res.obj.url)
                                }
                            } else {
                                wx.showToast({
                                    title: '打开失败',
                                    icon: 'none'
                                })
                            }
                        })
                }
            })
    },
    enlargeImage(url) { // 查看大图
        wx.previewImage({
            current: url, // 当前显示图片的http链接
            urls: [url] // 需要预览的图片http链接列表
        })
    },
    toVideo(url) {
        wx.navigateTo({
            url: '../video/video?url=' + url
        })
    },
    putInMyhouse(e) { // 装进我家
        // this.renderTypeShow() // 显示组件
        let item = e.currentTarget.dataset.item
        wx.setStorageSync('caseItem', item)
        wx.navigateTo({
            url: '../case-house-type/case-house-type'
        })
    },
})