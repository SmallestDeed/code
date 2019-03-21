let myForEach = getApp().myForEach,
    mySplitUrl = getApp().mySplitUrl,
    myCompoundUrl = getApp().myCompoundUrl,
    $App = getApp(),
    API = getApp().API,
    ttt = 3
import {
    shareTitle
} from '../../../utils/config.js';
import { emptyTemplate } from '../../../component/emptyTemplate/emptyTemplate'
Page({
    emptyTemplate, // 无数据组件
    /**
     * 页面的初始数据
     */
    data: {
        spaceList: [],
        areaList: [],
        styleList: [],
        types: 0,
        recommendCaseList: [],
        areaId: '',
        styleCode: '',
        oneAreaId: '',
        resourcePath: getApp().resourcePath,
        sevenUrl: getApp().sevenUrl,
        wholeHouseUrl: getApp().wholeHouseUrl,
        pageSize: 5,
        getCaseParams: {
            spaceType: 3,
            designPlanStyleId: '',
            spaceArea: ''
        },
        isShow: true,
        totalCount: 1,
        caseListheight: '',
        caseListOverflow: 'none',
        scroolLeft: 0,
        fitmentWayList: [],
        fitmentWayActive: 0,
        fitmentWayChildActive: 0,
        decoratePriceList: [],
        animate: '',
        showList: [],
        SuperiorList: [],
        showChose: false,
        showChoseType: '',
        spaceTypeName: '客餐厅',
        styleName: '装修风格',
        fitmentWayName: '装修方式'
    },

    onLoad: function(options) {
        this.init(options)
    },
    init(options) { // 初始化
        let that = this
        this.getDesignplanconditionmetadata() // 获取空间
        this.isSevenShare(options) // 是否720分享
        this.getFitmentQuote() // 获取专修报价筛选条件
        new this.emptyTemplate() // 注册组件
        let timer = setInterval(() => {
            if (wx.getStorageSync('openId')) {
                this.sellingPoint()
                clearInterval(timer)
            }
        }, 100)
        this.posterSharePanorama(options) // 获取海报分享生成720
    },
    posterSharePanorama(options) {
        if (options.scene) {
            API.getJsonData({
                id: options.scene
            }).then(res => {
                getApp().data.webUrl = myCompoundUrl(JSON.parse(res.obj.jsonData))
                wx.navigateTo({
                    url: '/pages/web-720/web-720'
                })
            })
        }
    },
    routerToCaseDetails(e) {
        let id = e.currentTarget.dataset.id,
            type = e.currentTarget.dataset.type
        wx.navigateTo({
            url: `/pages/case-details/case-details?id=${id}&type=${type || 0}`
        })
    },
    getFitmentQuote() { // 获取装修报价筛选条件
        API.getFitmentQuote()
            .then(res => {
                let arr = []
                if (res.code === 200) {
                    arr = res.list
                    arr.unshift({
                        name: '全部',
                        sonList: [],
                        value: ''
                    })
                    myForEach(arr, (value) => {
                        value.sonList.unshift({
                            name: '全部',
                            sonList: [],
                            value: ''
                        })
                    })
                }
                this.setData({
                    fitmentWayList: arr
                })
            })
    },
    isSevenShare(options) {
        if (options.item) { // 720
            let item = JSON.parse(options.item)
            item.url = decodeURIComponent(item.url)
            getApp().data.webUrl = myCompoundUrl(item)
            wx.navigateTo({
                url: '/pages/web-720/web-720',
            })
        } else if (options.url) { // 视频
            wx.navigateTo({
                url: '/pages/video/video?url=' + options.url,
            })
        } else if (options.scene) { // 表示通用版本扫码
            // let scene = decodeURIComponent(options.scene), sceneObj = mySplitUrl(scene)
            // getApp().data.webUrl = myCompoundUrl({
            //   url: getApp().grassSevenUrl,8
            //   renderId: sceneObj.i,
            //   sceneType: sceneObj.t,
            //   planSourceType: sceneObj.s || '',
            //   hasChanged: sceneObj.b || '',
            //   customReferer: $App.wxUrl,
            //   platformCode: 'brand2c',
            //   platformNewCode: 'miniProgram'
            // })
            // wx.navigateTo({
            //   url: '/pages/web-720/web-720?type=grass',
            // })
        }
    },
    getDesignplanconditionmetadata() {
        let timer = setInterval(() => {
            if (wx.getStorageSync('token')) {
                this.getPlan()
                clearInterval(timer)
            }
        }, 100)
    },
    getPlan() {
        API.getSuperiorPlan({
            bizType: 1,
            spaceType: this.data.getCaseParams.spaceType
        }).then(res => {
            this.setData({
                showList: res.obj ? res.obj : [],
                SuperiorList: res.obj ? res.obj : []
            })
            if (this.data.showList.length < 5) {
                this.getRecommendCaseList(this.data.getCaseParams)
                return;
            } else {
                this.emptyTemplateShow('hide')
            }
            if (this.data.pageSize == 0) {
                return;
            } else {
                this.getRecommendCaseList(this.data.getCaseParams)
            }
        })

    },

    areaIdFun: function(e) {
        this.setData({
            areaId: e.currentTarget.dataset.info || '',
            'getCaseParams.spaceArea': e.currentTarget.dataset.info || ''
        })
    },
    styleCodeFun: function(e) {
        this.setData({
            styleCode: e.currentTarget.dataset.info || '',
            'getCaseParams.designPlanStyleId': e.currentTarget.dataset.info || ''
        })
    },
    onShow: function() {
        this.getConditionMetadata();
    },
    sellingPoint(event) {
        let page = getCurrentPages(),
            previousPath = page.length > 1 ? page[page.length - 2].route : '',
            nowPath = page[page.length - 1].route
        API.sellingPoint({
            uid: wx.getStorageSync('openId'),
            cp: nowPath,
            lp: previousPath,
            e: event ? event : '',
            pt: '方案'
            // data: [Object.assign({
            //   openId: wx.getStorageSync('openId'),
            //   currentPage: nowPath,
            //   lastPage: previousPath
            // }, event ? { event: event } : {})]
        })
    },
    onReachBottom: function() {
        if (this.data.pageSize < this.data.totalCount && this.data.isShow) {
            this.setData({
                pageSize: this.data.pageSize + 5
            })
            this.getRecommendCaseList(this.data.getCaseParams)
        } 
    },

    onPullDownRefresh: function() {
        if (!this.data.isShow)
            return;
        this.getConditionMetadata() // 获取方案筛选条件
        wx.stopPullDownRefresh() //在标题栏中显示加载
    },
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            return {
                title: shareTitle,
                path: `/pages/plan/house-case/house-case`,
                success(res) {},
                fail(err) {}
            }
            // return $App.shareAppMessageObj

        }
    },
    enlargeImage(url) { // 查看大图
        wx.previewImage({
            current: url, // 当前显示图片的http链接
            urls: [url] // 需要预览的图片http链接列表
        })
    },
    toThreeD(e) { // 调转到3D界面
        let type = e.currentTarget.dataset.type,
            item = e.currentTarget.dataset.item,
            routerQueryType = null,
            webUrl = null,
            sevenObj = null
        if (type === 'video') {
            API.getRecommendedVideoId({
                    planRecommendedId: item.designPlanRecommendId,
                    remark: type
                })
                .then(res => {
                    if (res.success) {
                        return res.datalist[0].id
                    } else {
                        return false
                    }
                })
                .then(res => {
                    if (res) {
                        API.getRecommendedVideoMessage({
                                thumbId: res
                            })
                            .then(res => {
                                res.success ? this.toVideo(res.obj.url) : wx.showToast({
                                    title: '打开失败',
                                    icon: 'none'
                                })
                            })
                    }
                })
        } else {
            type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
            item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = {
                    uuid: item.fullHousePlanUUID,
                    embedded: 1
                }) :
                (webUrl = this.data.sevenUrl, sevenObj = {
                    token: wx.getStorageSync('token'),
                    platformCode: 'brand2c',
                    operationSource: 1,
                    planId: item.designPlanRecommendId,
                    routerQueryType: routerQueryType,
                    customReferer: $App.wxUrl,
                    platformNewCode: 'miniProgram'
                })
            for (let key in sevenObj) {
                webUrl += key + '=' + sevenObj[key] + '&'
            }
            getApp().data.webUrl = webUrl
            $App.myNavigateBack('pages/web-720/web-720')
            console.log(webUrl)
        }
    },
    getConditionMetadata() { // 获取方案筛选条件
        API.getConditionMetadata()
            .then(res => {
                if (res.status) {
                    myForEach(res.obj[0].designPlanAreaList, (value) => {
                        value.areaName = value.areaName.replace('~', '-')
                    })
                    let list = res.obj[0].designPlanStyleVoList;
                    list.unshift({ styleName: '全部', styleCode: '' })
                    this.setData({
                        spaceList: res.obj,
                        areaList: res.obj[0].designPlanAreaList,
                        styleList: list,
                        'getCaseParams.spaceType': ttt
                    })
                    let timer = setInterval(() => {
                        if (wx.getStorageSync('token')) {
                            this.getRecommendCaseList(this.data.getCaseParams)
                            clearInterval(timer)
                        }
                    }, 100)
                } else {
                    this.setData({
                        spaceList: [],
                        areaList: [],
                        styleList: []
                    })
                }
            })
    },
    getRecommendCaseList(o) { // 获取方案列表
        let obj = {
            spaceType: o ? o.spaceType : '',
            designPlanStyleId: o ? o.designPlanStyleId : '',
            decoratePriceType: this.data.fitmentWayActive == 0 ? '' : this.data.fitmentWayList[this.data.fitmentWayActive].value
        }
        //shopPlatformType: 2,
        let recommendationPlanSearchVo = {
            houseType: obj.spaceType || 3, // 默认请求客餐厅数据，181122随选网UI调整去掉全部
            designStyleId: obj.designPlanStyleId || '',
            displayType: 'decorate',
            decoratePriceType: obj.decoratePriceType,
            platformCode: 'selectDecoration'
        }
        let parm = {
            "recommendationPlanSearchVo": recommendationPlanSearchVo,
            "pageVo": {
                start: 0,
                pageSize: this.data.pageSize,
            }
        }
        //shopPlatformType: 2,
        API.getNewRecommendCaseList(parm).then(res => {
            if (res.success) {
                //1124 jyk新加逻辑-筛选不显示置顶方案
                let flags = (obj.designPlanStyleId || obj.decoratePriceType)?  true:false;
                if (res.datalist) {
                    let list = this.data.SuperiorList.concat(res.datalist)
                    this.setData({
                        showList: flags ? res.datalist : list,
                        totalCount: res.totalCount
                    })
                } else {
                    this.setData({
                        showList: flags?[]:this.data.SuperiorList,
                        totalCount: 1,
                    })
                    this.emptyTemplateShow(flags?'show':'hide')
                }
                if (this.data.showList.length < 1) {
                    this.emptyTemplateShow('show')
                    this.setData({
                        recommendCaseList: [],
                        totalCount: 1
                    })
                } else {
                    this.emptyTemplateShow('hide')
                }

            } else {
                this.setData({
                    showList: this.data.SuperiorList
                })
            }

        })
    },
    showChoseClose(){
        this.setData({
            showChose: false,
        })
    },
    setObj() {
        let obj = {
            spaceType: this.data.spaceType || 3,
            designPlanStyleId: this.data.designPlanStyleId || '',
            decoratePriceType: this.data.fitmentWayActive,
        }
        this.showChoseClose();
        return obj;
    },
    styleTypeFun: function (e) {
        this.setData({
            spaceType: e.currentTarget.dataset.info.houseType || 3,
            spaceTypeName: e.currentTarget.dataset.info.houseName || '客餐厅',
            'getCaseParams.spaceType': e.currentTarget.dataset.n || 3
        })
        this.getPlan();
        this.getRecommendCaseList(this.setObj())
    },
    styleCodeFun: function (e) {
        let styleName = e.currentTarget.dataset.info.styleName
        this.setData({
            styleName: styleName == '全部' ? '装修风格' : styleName,
            designPlanStyleId: e.currentTarget.dataset.info.styleCode || '',
            'getCaseParams.designPlanStyleId': e.currentTarget.dataset.info.styleCode || ''
        })
        this.getRecommendCaseList(this.setObj())
    },
    switchFitmentWay(e) { // 切换装修方式
        let fitmentWayName = e.currentTarget.dataset.info.name
        this.setData({
            fitmentWayName: fitmentWayName == '全部' ? '装修方式' : fitmentWayName,
            fitmentWayActive: e.currentTarget.dataset.info.value
        })
        this.getRecommendCaseList(this.setObj())
    },
    showChose(e) {
        let types = e.currentTarget.dataset.types;
        let flag = types == this.data.types
        this.setData({
            showChose: flag ? !this.data.showChose : true,
            types: types
        })
    },
})