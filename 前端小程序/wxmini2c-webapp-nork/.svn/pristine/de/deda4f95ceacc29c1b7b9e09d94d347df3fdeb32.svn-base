import fetch from '../../utils/fetch.js';
import myForEach from '../../utils/utils.js'
// 声明初始数据
let renderTypeData = {
    "renderType.renderTypeShow": false,
    "renderType.renderTypeList": [],
    "renderType.renderValue": 1,
    "renderType.renderCaseItem": {},
    "renderType.houseId": '',
    "renderType.templateId": '',
    "renderType.isMember": false, // 是否包年包月
    "renderType.bindingShow": false
}
// 声明事件
let renderTypeEvent = {
    chooseRenderType(e) {
        this.setData({
            "renderType.renderValue": parseInt(e.detail.value)
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
            designPlanId: this.data.renderType.renderCaseItem.planRecommendedId || this.data.renderType.renderCaseItem.designPlanRecommendId, //方案id
            formId: e.detail.formId, //表单ID
            forwardPage: 'pages/index/index?navToUrl=/pages/my-tasks/my-tasks', //跳转页
            renderType: 0, //渲染类型：0，装进我加；1，替换渲染
            renderLevel: this.data.renderType.renderValue //渲染级别：1，照片；4：720；6，视频；8，720
        }
        console.log(data);
        fetch(url, 'formData', data, 'system').then(res => {
            console.log(res, '亮眼')
        })
    },
    confirmRenderType(e) {
        this.setData({
            "renderType.renderTypeShow": false
        })
        let type = e.currentTarget.dataset.type,
            renderTaskType = null
        if (type === 'confirm') {
            if (this.data.renderType.renderValue === 1) {
                renderTaskType = 'common_render'
            } else if (this.data.renderType.renderValue === 4) {
                renderTaskType = 'panorama_render'
            } else if (this.data.renderType.renderValue === 8) {
                renderTaskType = 'roam720'
            } else {
                renderTaskType = 'video'
            }
            let renderParams = {
                renderTaskType: renderTaskType,
                renderCaseItem: this.data.renderType.renderCaseItem,
                houseId: this.data.renderType.houseId,
                templateId: this.data.renderType.templateId
            }
            console.log(renderParams);
            this.renderIng(renderParams)
        }
    },
    renderTypeShow(item, houseId, templateId) {
        this.setData({
            "renderType.renderTypeShow": true,
            "renderType.renderCaseItem": item,
            "renderType.houseId": houseId,
            "renderType.templateId": templateId
        })
    },
    renderIng(renderParams) {
        let url = `/render/server/addRenderTask.htm`,
            planRecommendedId = renderParams.renderCaseItem.designPlanRecommendId || renderParams.renderCaseItem.planRecommendedId,
            totalFee = null
        this.data.renderType.isMember ? totalFee = 0 : totalFee = 1
        fetch(url, 'post', {
                taskSource: 0,
                taskType: 0,
                houseId: renderParams.houseId,
                groupPrimaryId: renderParams.renderCaseItem.groupPrimaryId,
                planRecommendedId: planRecommendedId,
                templateId: renderParams.templateId,
                renderTaskType: renderParams.renderTaskType,
                operationSource: 1,
                totalFee: totalFee
            }, 'render')
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
                                if (res.confirm) {
                                    let utl = '/pages/my-tasks/my-tasks';
                                    wx.navigateTo({
                                        url: utl
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
                            content: res.message,
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
    bindingMobileBoxShow() { // 绑定手机号
        this.bindgetRenderTypeShow(false)
        this.bindingPhoneShow()
    },
    bindgetRenderTypeShow(type) {
        this.setData({
            "renderType.renderTypeShow": type
        })
    },
    hideBindingShow() {
        this.setData({
            'renderType.bindingShow': true
        })
    }
}
// 面向对象编程
//   (function () { 
//     function requestOperation() {
//       this.prototype.requestOperationClass = {
//         getRenderType: function (curPage) { // 获取数据
//           let url = `/web/pay/payOrder/getRenderType`
//           fetch(url, 'get', {}, 'pay')
//             .then(res => {
//               if (res.status) {
//                 res.obj[0].checked = true
//                 curPage.setData({
//                   "renderType.renderTypeList": res.obj,
//                   "renderType.renderValue": res.obj[0].renderValue
//                 })
//               } else {
//                 curPage.setData({
//                   "renderType.renderTypeList": []
//                 })
//               }
//             })
//             .catch(() => {
//               curPage.setData({
//                 "renderType.renderTypeList": []
//               })
//             })
//         },
//         getuserIsPackageInMonthly: function (curPage) { // 获取用户包年包月信息
//           let url = `/web/pay/checkRenderGroopRef2C`
//           fetch(url, 'get', {}, 'pay')
//             .then((res) => {
//               if (res.success) {
//                 if (res.obj.state === '0') {
//                   curPage.setData({
//                     'renderType.isMember': false
//                   })
//                 } else if (res.obj.state === '2' || res.obj.state === '3') {
//                   curPage.setData({
//                     'renderType.isMember': true
//                   })
//                 }
//               }
//             })
//         },
//         getIsBindPhone: function () { // 获取用户绑定手机号信息

//         }
//       }
//     }
//   })()
// let renderRequest = new requestOperation()
// 获取数据
function getRenderType(curPage) {
    let url = `/web/pay/payOrder/getRenderType`
    fetch(url, 'get', {}, 'pay')
        .then(res => {
            if (res.status) {
                res.obj[0].checked = true
                curPage.setData({
                    "renderType.renderTypeList": res.obj,
                    "renderType.renderValue": res.obj[0].renderValue
                })
            } else {
                curPage.setData({
                    "renderType.renderTypeList": []
                })
            }
        })
        .catch(() => {
            curPage.setData({
                "renderType.renderTypeList": []
            })
        })
}
// 获取包年包月信息
function getuserIsPackageInMonthly(curPage) { // 获取用户包年包月信息
    let url = `/web/pay/checkRenderGroopRef2C`
    fetch(url, 'get', {}, 'pay')
        .then((res) => {
            if (res.success) {
                if (res.obj.state === '0') {
                    curPage.setData({
                        'renderType.isMember': false
                    })
                } else if (res.obj.state === '2' || res.obj.state === '3') {
                    curPage.setData({
                        'renderType.isMember': true
                    })
                }
            }
        })
}

function getIsBindPhone(curPage) {
    // 检验手机号是否存在
    let url = `/v2/user/center/isUserMobileBinded`
    fetch(url, 'get', {}, 'user')
        .then((res) => {
            if (res.success) {
                if (res.obj === 0) {
                    curPage.setData({
                        'renderType.bindingShow': false
                    })
                } else if (res.obj === 1) {
                    curPage.setData({
                        'renderType.bindingShow': true
                    })
                }
            } else {
                this.setData({
                    'renderType.bindingShow': false
                })
            }
        })
}
// 声明实例
function renderTypeExample() {
    let pages = getCurrentPages()
    let curPage = pages[pages.length - 1]
    getRenderType(curPage)
    getIsBindPhone(curPage)
    getuserIsPackageInMonthly(curPage)
    Object.assign(curPage, renderTypeEvent)
    curPage.setData(renderTypeData)
    curPage.renderType = this
    return this
}

module.exports = {
    renderTypeExample
}