// pages/goods-list/goods-list.js
let API = getApp().API
let myForEach = getApp().myForEach
import {
    renderTypeExample
} from '../../component/render-type/render-type.js'
let $App = getApp()
let opt = {};

import fetch from '../../utils/fetch.js';
Page({
    // renderTypeExample,

    /**
     * 页面的初始数据
     */
    data: {
        // 版权收费开始
        staticImageUrl: $App.staticImageUrl,
        permissionItem: {},
        copyrightFree: 20,
        issBindingMobile: false,
        copyrightPayShow: false,
        copyrightPayShowSuc: false,
        copyrightPayShowFail: false,
        designRecommendedStyleName: '',
        putInMyhouseCurrentTargetType: '',
        // 版权收费结束
        // 装进我家弹窗b
        renderTypeData: {
            "renderType.renderCaseItem": {},
            "renderType.houseId": '',
            "renderType.templateId": '',
            "renderType.isMember": true, // 是否包年包月
            "renderType.bindingShow": false
        },
        packDialogRenderShow: false,
        packDialogBuyShow: false,
        // 装进我家弹窗e
        drop: true,
        houseId: 0,
        houseTypelist: [],
        spaceInHouse: [],
        space: [],
        spaceInedxName: "",
        spaceInedxType: "",
        setCaseItem: false,
        spaceList: [],
        resourcePath: getApp().resourcePath,
        sevenUrl: getApp().sevenUrl,
        information: [],
        style: [],
        designPlanStyleId: '',
        caseItem: {},
        isHouseCaseOrHouseType: false,
        caseIsSuitable: false,
        caseStyleActive: -1, // 风格焦点
        caseSpaceActive: 3, // 空间筛选焦点样式
        templateId: '', // 空间ID
        tapComeFromType: '', // 从哪里进
        pageSize: 20,
        totalCount: 0,
        spaceAreas: '',
        favoriteRequest: true,
        collectRequest: true,
        groupFlag: false,
        groupItem: {},
        nowItems: {},
        typeShow: false,
        nowFlag: false,
        fliterFlag: false,
        fitmentWayActive: 0,
        fitmentWayList: [], //装修风格选项,
        decoratePriceType: '',
        decoratePriceRange: '',
        houseCaseIsShow: '',
        showShade: false,
        showShade1: true,
        showShade2: false,
        nowIconId:'',
        nowType:'',
    },
    onShow: function () {
        this.getinformation(this.data.spaceAreas)
        let cases = wx.getStorageSync('caseItem')
        if (cases){
            let caseId = cases.planRecommendedId || cases.designPlanRecommendId
            let types = cases.spaceFunctionId == 13?0:1
            this.isCsritem(caseId,types);
        }
    },
    isCsritem(id,types) {
        let url = '/render/server/checkReplaceDesignPlanPay.htm';
        let that = this;
        API.getRender({
            recommendedPlanId: id,
            planType: types
        }).then((res) => {
            console.log(res);
            if (res.obj.flag) {
                let casess = wx.getStorageSync('caseItem')

                casess.havePurchased = casess.copyRightPermission==1?1:0;
                that.setData({
                    caseItem: casess
                })
                wx.setStorage({
                    key: 'caseItem',
                    data: casess,
                })
            }
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        opt = options;
        console.log($App);
        this.getScore();
        new $App.quickNavigation() // 注册组件
        // new this.renderTypeExample() // 注册组件
        new $App.bindingPhone() // 注册组件
        let item = JSON.parse(options.item)
        let flag = false
        let caseIsSuitable = null
        let thaaat = this;
        let totalArea = opt.totalArea;
        this.setData({
            setCaseItem: options.setCaseItem || false,
            houseCaseIsShow: options.type
        })
        if (options.groupItem) {
            this.setData({
                groupItem: JSON.parse(options.groupItem),
                groupFlag: true,
            })
        }
        if (options.type === 'houseType' || options.type === 'houseSpace') { // 从哪进
            flag = false
        } else if (options.type === 'houseCaseFalse' || options.type === 'houseCaseTrue') {
            flag = true
            caseIsSuitable = options.type === 'houseCaseFalse' ? true : false
        }
        this.setData({
            caseIsSuitable: caseIsSuitable
        })


        if (this.data.groupFlag) {
            let Item = this.data.caseItem;
            this.setData({
                caseItem: this.data.groupItem.designTemplets[0].designPlanRecommended
            })
        }
        // 空间还是户型进
        if (options.type != 'houseSpace') {
            this.setData({
                houseId: item.id,
                isHouseCaseOrHouseType: flag,
                caseIsSuitable: caseIsSuitable,
                tapComeFromType: options.type
            })
            this.gethousetypelist()
            //this.getinformation(totalArea) // 获取方案

        } else {
            this.setData({
                spaceInedxType: item.spaceFunctionId || item.spaceType,
                isHouseCaseOrHouseType: flag,
                tapComeFromType: options.type,
                spaceList: [item],
                spaceAreas: item.spaceAreas
            })
            this.gethousetypelist()
            this.getinformation(item.spaceAreas) // 获取方案
            this.getSpaceDetails(item.spaceCommonId) // 获取空间详细信息
        }
        this.getFitmentQuote() // 获取专修报价筛选条件
        this.getIsBindingMobile()
    },
    //获取所有空间类型
    gethousetypelist: function() {
        var that = this,
            url = '/designplan/designplanconditionmetadata',
            data = {};
        fetch(url, 'POST', data).then((res) => {
            if (res.obj) {
                that.setData({
                    houseTypelist: res.obj
                })
                if (this.data.tapComeFromType === 'houseSpace') { // 如果是空间入口
                    myForEach(this.data.houseTypelist, (val) => {
                        if (val.houseType === this.data.spaceInedxType) {
                            this.setData({
                                spaceInedxName: val.houseName,
                                space: val
                            })
                        }
                    })
                } else { // 不是空间入口需要匹配相应空间
                    that.getSpaceInHouse()
                }
            }
        })
    },
    getCaseItem: function(obj) {
        let that = this;
        if (wx.getStorageSync('caseItem')) {
            let name = '';
            let tts = wx.getStorageSync('caseItem').spaceType || wx.getStorageSync('caseItem').spaceFunctionId
            for (let i = 0; i < obj.length; i++) {
                if (tts == obj[i].houseType) {
                    name = obj[i].houseName;
                }
            }
            if (name) {
                setTimeout(function() {
                    that.setData({
                        caseSpaceActive: tts,
                        spaceInedxType: tts,
                        spaceInedxName: name,

                    })
                    //that.getinformation(that.data.spaceAreas)
                    that.getspace();
                }, 200)
            } else {
                setTimeout(function() {
                    that.setData({
                        caseSpaceActive: obj[0] ? obj[0].houseType : 3,
                        spaceInedxType: obj[0] ? obj[0].houseType : 3,
                        spaceInedxName: obj[0] ? obj[0].name : '客餐厅',
                    })
                    //that.getinformation(that.data.spaceAreas)
                    that.getspace();
                }, 200)
            }
        }
    },
    //根据houseid匹配空间列表
    getSpaceInHouse: function() {
        var that = this,
            url = '/home/basehouse/getSpaceNameInHouse.htm',
            data = {
                houseId: that.data.houseId
            };
        fetch(url, 'POST', data)
            .then((res) => {
                if (res.status) {
                    that.setData({
                        spaceInHouse: res.obj
                    })
                    var space2 = []
                    for (let i = 0; i < that.data.houseTypelist.length; i++) {
                        for (let j = 0; j < that.data.spaceInHouse.length; j++) {
                            if (that.data.houseTypelist[i].houseType == that.data.spaceInHouse[j]) {
                                space2.push(that.data.houseTypelist[i])
                            }
                        }
                    }
                    for (let i = 0; i < space2.length; i++) {
                        if (space2[i] == '13') {
                            arr.splice(i, 1);
                            break;
                        }
                    }
                    that.setData({
                        space: space2,
                        spaceInedxName: space2[0].houseName,
                        spaceInedxType: space2[0].houseType
                    })
                    if (this.data.tapComeFromType == 'houseType') {
                        that.getspace()
                    }
                    that.getCaseItem(space2);

                }
            })
    },
    //获取顶部空间图
    getspace: function() {
        var that = this
        let data = {
            houseId: that.data.houseId,
            spaceFunctionId: that.data.spaceInedxType,
            designPlanRecommendId: wx.getStorageSync('caseItem').planRecommendedId || wx.getStorageSync('caseItem').designPlanRecommendId  || '',
            groupPrimaryId: wx.getStorageSync('caseItem').groupPrimaryId || '',
        };
        let url = '/home/spacecommon/housespacelist'
        fetch(url, 'GET', data).then((res) => {
                if (res.status) {
                    if (res.obj[0].spaceFunctionId != '13') {
                        if (res.obj[0].designTemplets) {
                            that.setData({
                                templateId: res.obj[0].designTemplets[0].id
                            })
                        }
                        that.setData({
                            spaceList: res.obj,
                            spaceAreas: res.obj[0].spaceAreas || ''
                        })
                        let nowCaseItem = res.obj[0].designTemplets[0].designPlanRecommended || '';
                        for (let i = 0; i < nowCaseItem.length; i++) {
                            if (that.data.templateId == nowCaseItem[i].designTemplets[0].id) {
                                nowCaseItem = nowCaseItem[i].designTemplets[0].designPlanRecommended
                            }
                        }
                        setTimeout(function() {
                            that.setData({
                                caseItem: wx.getStorageSync('caseItem'),
                                nowFlag: (that.data.setCaseItem || nowCaseItem) ? true : false,
                            })
                            that.getinformation(that.data.spaceAreas) // 获取方案
                            that.caseIsMatching(that.data.spaceInedxType, that.data.spaceAreas) // 校验方案是否合适
                        }, 200)
                    } else {
                        that.setData({
                            spaceList: res.obj
                        })

                        setTimeout(function() {
                            that.setData({
                                spaceList: res.obj,
                                caseItem: wx.getStorageSync('caseItem'),
                                nowFlag: true
                            })
                            that.getinformation(that.data.spaceAreas) // 获取方案
                        }, 200)

                    }
                } else {
                    that.setData({
                        spaceList: [],
                        spaceAreas: ''
                    })

                    that.caseIsMatching()
                }
            })
            .catch((res) => {
                that.setData({
                    caseItem: '',
                    nowFlag: false,
                })
            })
    },
    //获取推荐方案列表
    getinformation: function(spaceAreas) {
        
        var that = this;
        let url = `fullsearch-app/all/recommendationplan/search/mini/list`
        let data = {
            "recommendationPlanSearchVo": {
                "displayType": "decorate",
                "houseType": that.data.spaceInedxType || 3,
                "spaceArea": spaceAreas || '',
                "designStyleId": that.data.designPlanStyleId || '',
                "decoratePriceType": this.data.decoratePriceType,
                "decoratePriceRange": this.data.decoratePriceRange
            },
            "pageVo": {
                "start": 0,
                "pageSize": this.data.pageSize
            }
        }
        // let data = {
        //     curPage: 1,
        //     pageSize: this.data.pageSize,
        //     spaceType: that.data.spaceInedxType || 3,
        //     designPlanStyleId: that.data.designPlanStyleId,
        //     spaceArea: spaceAreas || '',
        //     displayType: 'dragDecorate',
        //     designRecommendedStyleName: this.data.designRecommendedStyleName,
        //     decoratePriceType: this.data.decoratePriceType,
        //     decoratePriceRange: this.data.decoratePriceRange
        // }
        // let url = '/designplan/designplanrecommendedlist';
        fetch(url, 'post', data,'fullsearch').then((res) => {
            if (res.success) {
                that.setData({
                    information: res.datalist,
                    totalCount: res.totalCount
                })
            }
        })
    },
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {
        if (this.data.fliterFlag) {
            return
        } else {
            if (this.data.pageSize >= this.data.totalCount) {
                return
            } else {
                this.setData({
                    pageSize: this.data.pageSize + 20
                })
                this.getinformation(this.data.spaceAreas)
            }
        }
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            let obj = (opt.item ? ('?item=' + JSON.stringify(opt.item)) : '') +
                (opt.name ? ('&name=' + opt.name) : '') +
                (opt.groupItem ? ('&groupItem=' + JSON.stringify(opt.groupItem)) : '')
            return $App.shareAppMessageFn(false, obj);
        }
    },
    
    isDropMenuShow: function() {
        if (this.data.tapComeFromType === 'houseSpace') {
            return
        }
        this.setData({
            'drop': !this.data.drop
        })
    },
    spaceChange: function(event) {
        this.setData({
            caseStyleActive: -1,
            designPlanStyleId: ''
        })
        let that = this,
            spacetype = event.currentTarget.dataset.spacetype
        that.setData({
            spaceInedxType: event.currentTarget.dataset['spacetype'],
            spaceInedxName: event.currentTarget.dataset['spacename'],
            'drop': !this.data.drop,
            caseSpaceActive: spacetype
        })
        console.log(this.data.tapComeFromType);
        if (this.data.tapComeFromType !='houseType'){
            let flag01 = this.data.spaceInedxType == wx.getStorageSync('caseItem').spaceFunctionId || wx.getStorageSync('caseItem').spaceFunctionId.spaceType;
            flag01 ? this.setData({
                caseIsSuitable: false
            }) : this.setData({
                caseIsSuitable: true
            });
        }
        
        that.getspace();
    },
    styleChange: function(event) {
        let that = this,
            index = event.currentTarget.dataset.index,
            flag02 = event.currentTarget.dataset['styleid'] == undefined;

        flag02 ? that.setData({
            designPlanStyleId: '',
            designRecommendedStyleName: '',
            caseStyleActive: -1
        }) : that.setData({
            designPlanStyleId: event.currentTarget.dataset['styleid'],
            designRecommendedStyleName: event.currentTarget.dataset['name'],
            caseStyleActive: index
        });

        that.getinformation(this.data.spaceAreas)
    },
    styleChange2: function(event) {
        let that = this,
            index = event.currentTarget.dataset.index,
            flag02 = event.currentTarget.dataset['styleid'] == undefined;

        flag02 ? that.setData({
            designPlanStyleId: '',
            caseStyleActive: -1
        }) : that.setData({
            designPlanStyleId: event.currentTarget.dataset['styleid'],
            caseStyleActive: index
        });


    },
    // 版权收费开始
    copyrightPayClose() { // 关闭收费支付弹框
        this.setData({
            copyrightPayShow: false,
            copyrightPayShowSuc: false,
            copyrightPayShowFail: false
        })
    },
    copyrightPay() { // 版权费支付按钮
        let url = '/render/server/payDesignPlanCopyRight.htm',
            _this = this;
        console.log(this.data.permissionItem);
        fetch(url, 'post', {
            planRecommendedId: this.data.permissionItem.planRecommendedId || this.data.permissionItem.designPlanRecommendId,
            payType: 1,
            payMethod: "miniProPay",
            userId: wx.getStorageSync('userId'),
            useType: 0
        }, 'render').then((res) => {
            if (res.success) {
                wx.requestPayment({
                    'timeStamp': res.obj.timeStamp,
                    'nonceStr': res.obj.nonceStr,
                    'package': res.obj.package,
                    'signType': 'MD5',
                    'paySign': res.obj.paySign,
                    'success': function(response) {
                        _this.getinformation(_this.data.spaceAreas)
                        _this.copyrightPayClose();
                        _this.setData({
                            copyrightPayShowSuc: true,
                            ['caseItem.copyRightPermission']: 0,
                            ['caseItem.havePurchased']: 1
                        })
                    },
                    'fail': function(err) {
                        _this.copyrightPayClose();
                        _this.setData({
                            copyrightPayShowFail: true
                        })
                    }
                })
            }
        })
    },
    copyrightPay_putInMyhouse() { // 版权费装进我家按钮
        this.copyrightPayClose();
        let caseItem = this.data.permissionItem;
        let type = caseItem.type || this.data.putInMyhouseCurrentTargetType,
            flag03 = this.data.caseIsSuitable && type == 'caseItem';

        let isMember = false
        // 获取用户包年包月信息
        let url = `/web/pay/checkRenderGroopRef2C`
        fetch(url, 'get', {}, 'pay')
            .then((res) => {
                if (res.flag) {
                    if (res.obj.state === '0') {
                        isMember = false
                    } else if (res.obj.state === '2' || res.obj.state === '3') {
                        isMember = true
                    }
                } else {
                    // wx.showToast({
                    //     title: res.message,
                    //     icon: 'none'
                    // })
                }
            })

        let totalFee = null;
        isMember ? totalFee = 0 : totalFee = 1;
        if (caseItem.planHouseType == '2') {
            let data = {
                taskSource: 0,
                taskType: 0,
                houseId: this.data.houseId,
                planRecommendedId: caseItem.planRecommendedId || caseItem.designPlanRecommendId,
                templateId: this.data.templateId,
                renderTaskType: 'panorama_render',
                operationSource: 1,
                totalFee: totalFee,
                planHouseType: caseItem.planHouseType
            }
            wx.navigateTo({
                url: '/pages/putInMyHouse/putInMyHouse?planData=' + JSON.stringify(data),
            })
        } else {
            flag03 ? wx.showToast({
                title: '该方案不适合您选择的空间',
                icon: "none"
            }) : this.renderTypeShow(caseItem, this.data.houseId, this.data.templateId)
        }
    },
    // 版权收费结束
    // putInMyhouse(e) { // 装进我家
    //     let caseItem = e.detail || e.currentTarget.dataset.item,
    //         type = caseItem.type || e.currentTarget.dataset.type,
    //         flag03 = this.data.caseIsSuitable && type == 'caseItem';
    //         console.log(caseItem)
    //         console.log(type) //seven
    //         console.log(flag03) //false
    //     this.setData({
    //         permissionItem: caseItem,
    //         copyrightFree: caseItem.planPrice
    //     })
    //     let isMember = true
    //     // 获取用户包年包月信息
    //     // let url = `/web/pay/checkRenderGroopRef2C`
    //     // fetch(url, 'get', {}, 'pay')
    //     //     .then((res) => {
    //     //         if (res.success) {
    //     //             if (res.obj.state === '0') {
    //     //                 isMember = false
    //     //             } else if (res.obj.state === '2' || res.obj.state === '3') {
    //     //                 isMember = true
    //     //             }
    //     //         }
    //     //         if (!res.flag) {
    //     //             // wx.showToast({
    //     //             //     title: res.message,
    //     //             //     icon: 'none'
    //     //             // })
    //     //         }
    //     //     })

    //     let totalFee = null;
    //     isMember ? totalFee = 0 : totalFee = 1
    //     if (caseItem.copyRightPermission == 1) {
    //         this.setData({
    //             copyrightPayShow: true,
    //         })
    //     } else if (caseItem.planHouseType == '2') {
    //         let data = {
    //             taskSource: 0,
    //             taskType: 0,
    //             houseId: this.data.houseId,
    //             planRecommendedId: caseItem.designPlanRecommendId || caseItem.planRecommendedId,
    //             templateId: this.data.templateId,
    //             renderTaskType: 'panorama_render',
    //             operationSource: 1,
    //             totalFee: totalFee,
    //             planHouseType: caseItem.planHouseType
    //         }
    //         wx.navigateTo({
    //             url: '/pages/putInMyHouse/putInMyHouse?planData=' + JSON.stringify(data),
    //         })
    //     } else {
    //         flag03 ? wx.showToast({
    //             title: '该方案不适合您选择的空间',
    //             icon: "none"
    //         }) : this.renderTypeShow(caseItem, this.data.houseId, this.data.templateId)
    //     }

    // },
    getrecommedCaseList(e) { // 根据空间获取推荐方案列表
        if (this.data.tapComeFromType === 'houseSpace') {
            return
        }
        let spaceAreas = e.currentTarget.dataset.item.spaceAreas,
            id = e.currentTarget.dataset.item.designTemplets[0].id;
        this.setData({
            spaceAreas: spaceAreas,
            templateId: id
        })
        this.caseIsMatching(this.data.spaceInedxType, this.data.spaceAreas) // 匹配方案是否合适
        this.getinformation(spaceAreas)
    },
    caseIsMatching(id, spaceAreas) { // 方案是否匹配
        if (this.data.caseItem) {
            let caseItem = this.data.caseItem || wx.getStorageSync('caseItem'),
                spaceFunctionId = caseItem.spaceFunctionId || caseItem.spaceType,
                caseItemAreaValue = caseItem.applySpaceAreas?caseItem.applySpaceAreas.split(','):'',
                flag04 = (id === spaceFunctionId && caseItemAreaValue.indexOf(spaceAreas) !== -1);
            flag04 ? this.setData({
                caseIsSuitable: false
            }) : this.setData({
                caseIsSuitable: true
            });
            let ids = caseItem.designPlanRecommendId || caseItem.planRecommendedId
            if (ids != 0 && ids != null) {
                this.setData({
                    caseIsSuitable: false,
                })
            }
        }
    },
    getSpaceDetails(spaceCommonId) { // 空间入口，获取空间详情
        let url = `/design/designtemplet/spacedesign`
        fetch(url, 'get', {
            spaceCommonId: spaceCommonId
        }).then((res) => {
            res.status ? this.setData({
                templateId: res.obj[0].designTempletId
            }) : this.setData({
                templateId: ''
            });
        })
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
                planId: item.planRecommendedId || item.designPlanRecommendId,
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
        let type = e.currentTarget.dataset.type,
            item = e.currentTarget.dataset.item,
            routerQueryType = null,
            webUrl = this.data.sevenUrl
        if (type === 'video') {
            fetch(url, 'post', {
                planRecommendedId: item.planRecommendedId || item.designPlanRecommendId,
                    remark: type
                }, 'render')
                .then(res => {
                    if (res.success) {
                        return res.datalist[0].id
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
                                res.success ? this.toVideo(res.obj.url) : wx.showToast({
                                    title: '打开失败',
                                    icon: 'none'
                                })
                            })
                    }
                })
        } else {
            type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
            let sevenObj = {
                token: wx.getStorageSync('token'),
                platformCode: 'brand2c',
                operationSource: 1,
                planId: item.planRecommendedId || item.designPlanRecommendId,
                routerQueryType: routerQueryType,
                customReferer: $App.wxUrl,
                platformNewCode: 'miniProgram'
            }
            for (let key in sevenObj) {
                webUrl += key + '=' + sevenObj[key] + '&'
            }
            getApp().data.webUrl = webUrl
            $App.myNavigateBack('pages/web-720/web-720')
        }
    },
    enlargeImage(url) { // 查看大图
        wx.previewImage({
            current: url, // 当前显示图片的http链接
            urls: [url] // 需要预览的图片http链接列表
        })
    },
    toVideo(url) {
        wx.navigateTo({
            url: '/pages/template/video/video?url=' + url
        })
    },

    // bindPhoneCallBack() { // 绑定手机号回调
    //     this.hideBindingShow()
    // },
    showFliter() {
        this.setData({
            fliterFlag: !this.data.fliterFlag
        })
    },
    getFitmentQuote() { // 获取装修报价筛选条件
        let url = '/decoratePrice/getDecoratePriceType'
        fetch(url, 'get', '').then((res) => {
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
    switchFitmentWay(e) { // 切换装修方式
        let target = e.currentTarget.dataset,
            value = target.value,
            index = target.index,
            type = target.type,
            key = ''
        if (type == 'parent') {
            this.setData({
                fitmentWayActive: this.data.fitmentWayActive == index ? 0 : index,
                fitmentWayChildActive: 0
            })
        } else {
            this.setData({
                fitmentWayChildActive: this.data.fitmentWayChildActive == index ? 0 : index
            })
        }
    },
    resetFun: function() {
        this.setData({
            fitmentWayChildActive: 0,
            fitmentWayActive: 0,
            designPlanStyleId: '',
            caseStyleActive: -1
        })
    },
    submitFun: function() {
        this.setData({
            fliterFlag: false
        });
        let decoratePriceType = this.data.fitmentWayActive == 0 ? '' : this.data.fitmentWayList[this.data.fitmentWayActive].value,
            decoratePriceRange = this.data.fitmentWayChildActive == 0 ? '' : this.data.fitmentWayList[this.data.fitmentWayActive].sonList[this.data.fitmentWayChildActive].value;
        this.setData({
            decoratePriceType: decoratePriceType,
            decoratePriceRange: decoratePriceRange
        })
        this.getinformation();
    },
    showBIndingPhoneBox() { // 打开绑定手机
        // this.setData({
        //     showShade: false
        // })
        this.bindingPhoneShow()
    },
    getIsBindingMobile() {
        // 检验手机号是否存在
        let url = `/v2/user/center/isUserMobileBinded`
        API.getIsBindingMobile()
            .then(res => {
                this.setData({
                    issBindingMobile: res.success ? res.obj : false
                })
            })
    },
    paymentFun() {
        let data = {
            planRecommendedId: this.data.caseItems.planRecommendedId || this.data.caseItems.designPlanRecommendId,
            useType: 0,
            buyType: 1,
            planType: this.data.caseItems.fullHousePlanUUID ? 0 : 1
        }
        let that = this;
        API.getPayment(data).then(res => {
            console.log(res);
            if (res.obj.flag) {
                that.data.caseItems.havePurchased = 1;
                // that.setData({
                //    'caseItem.havePurchased': 1
                // })
                that.closeFunc();
                wx.showToast({
                    title: '充值成功',
                    icon: 'success'
                })
                that.putInMyhousess(that.data.caseItems);
            } else {
                that.setData({
                    showShade2: true,
                    showShade1: false,
                })
            }
        })
    },
    closeFunc() {
        this.setData({
            showShade: false,
        })
    },
    toFunc() {
        wx.navigateTo({
            url: '/pages/buy-points/buy-points',
        })
    },
    putInMyhousess(obj){
        let caseItem = obj || this.data.caseItems
        let url = `/render/server/addRenderTask.htm`
        let urlTwo = `/web/system/sysUser/getUserBalance`
        this.setData({
            permissionItem: caseItem,
            copyrightFree: caseItem.planPrice
        })
        if (caseItem.copyRightPermission == 1) {
            fetch(urlTwo, 'get', {}, 'pay')
                .then((res) => {
                    if (res.status) {
                        this.setData({
                            userMessage: res.obj
                        })
                        fetch(url, 'post', {
                            taskSource: 0,
                            taskType: 0,
                            houseId: this.data.houseId,
                            groupPrimaryId: caseItem.groupPrimaryId,
                            planRecommendedId: caseItem.planRecommendedId || caseItem.designPlanRecommendId,
                            templateId: this.data.templateId,
                            renderTaskType: 'panorama_render',
                            operationSource: 1,
                            totalFee: 0
                        }, 'render')
                            .then((res) => {
                                if (res.success) {
                                    if (res.message === '创建渲染任务成功！') {
                                      //砍价活动体验
                                      let exep=wx.getStorageSync('isExperience')
                                      if (exep == 1) {
                                        this.isExperience();//是否体验720 砍价活动 
                                      }
                                        this.setData({
                                            packDialogRenderShow: true
                                        })
                                        let ii = this.data.caseItems.index;
                                        let flag1s = caseItem.isCaseItem;
                                        console.log('flag1s------', flag1s, 'inde=' + ii);
                                        if (flag1s){
                                            this.setData({
                                                'caseItem.havePurchased': 1
                                            }) 
                                        }else{
                                            let informations = this.data.information;
                                            informations[ii].havePurchased = 1;
                                            this.setData({
                                                information: informations
                                            })
                                        }
                                    }
                                } else {
                                    this.setData({
                                        packDialogBuyShow: true
                                    })
                                }
                            })
                    } else {
                        this.setData({
                            userMessage: {
                                balanceIntegral: 0,
                                usableHouseNumber: 0
                            }
                        })
                    }
                })
                
        } else {
            fetch(url, 'post', {
                taskSource: 0,
                taskType: 0,
                houseId: this.data.houseId,
                groupPrimaryId: caseItem.groupPrimaryId,
                planRecommendedId: caseItem.planRecommendedId || caseItem.designPlanRecommendId,
                templateId: this.data.templateId,
                renderTaskType: 'panorama_render',
                operationSource: 1,
                totalFee: 0
            }, 'render')
                .then((res) => { 
                    if (res.success) {
                        if (res.message === '创建渲染任务成功！') {
                            let exep = wx.getStorageSync('isExperience')
                            if (exep == 1) {
                              this.isExperience();//是否体验720 砍价活动 
                            }
                            this.setData({
                                packDialogRenderShow: true
                            })
                        }
                    } else {
                        this.setData({
                            packDialogBuyShow: true
                        })
                    }
                })
        }
    },
  isExperience() {//砍价体验
    let self = this;
    console.log(decodeURIComponent(self.data.pageParams.houseName))
    wx.getStorage({
      key: 'actId',
      success: function (res) {
        console.log(res)
        API.cutPriceByDecorate({ houseId: self.data.pageParams.houseId, houseName: decodeURIComponent(self.data.pageParams.houseName), actId: res.data, formId: '', forwardPage: '' }).then(res => {
          if (res.success) {
            wx.setStorageSync('cutPrice', res.obj)
          }
        })
      },
    })
  },
    //装进我家
    putInMyhouse(e) {
        let caseItem = e.detail || e.currentTarget.dataset.item,
            type = caseItem.type || e.currentTarget.dataset.type
        this.setData({
            caseItems: caseItem
        })
        if (this.data.caseItems.copyRightPermission == 1 && !this.data.caseItems.havePurchased) {
            this.setData({
                showShade: true,
            })
        } else {
            this.putInMyhousess(caseItem);
        }
    },
    goTask: function() {
        this.setData({
            packDialogRenderShow: false
        })
        let utl = '/pages/my-tasks/my-tasks';
        wx.navigateTo({
            url: utl
        })
    },
    goBack: function() {
        this.setData({
            packDialogRenderShow: false
        })
    },
    leaveOut: function() {
        this.setData({
            packDialogBuyShow: false
        })
    },
    goBuy: function() {
        this.setData({
            packDialogBuyShow: false
        })
        wx.navigateTo({
            url: '/pages/purchase-house/purchase-house'
        })
    },
    showBIndingPhoneBox() { // 打开绑定手机
        this.bindingPhoneShow()
    },
    getScore: function () {
        API.getUserDuBiMessage()
            .then((res) => {
                if (res.status) {
                    this.setData({ score: parseInt(res.obj.balanceIntegral) })
                } else {
                    wx.showToast({ title: '获取度币信息失败', icon: 'none', duration: 3000 })
                }
            })
    },
    getIsBindingMobile() {
        // 检验手机号是否存在
        let url = `/v2/user/center/isUserMobileBinded`
        
        API.getIsBindingMobile()
            .then(res => {
                if (res.success) {
                    if (res.obj === 0) {
                        this.setData({
                            issBindingMobile: false
                        })
                    } else if (res.obj === 1) {
                        this.setData({
                            issBindingMobile: true
                        })
                    }
                } else {
                    this.setData({
                        issBindingMobile: false
                    })
                }
            })
    },
    // bindPhoneCallBack() {
    //     this.setData({
    //         issBindingMobile: true
    //     })
    // }
})