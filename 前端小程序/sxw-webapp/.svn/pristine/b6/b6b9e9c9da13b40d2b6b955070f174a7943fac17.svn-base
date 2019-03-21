import myForEach from '../../utils/utils.js'
import API from '../../api/api.js'
// 声明初始数据
let renderTypeData = {
  "renderType.renderTypeShow": false,
  "renderType.renderTypeList": [],
  "renderType.renderValue": 1,
  "renderType.renderCaseItem": {},
  "renderType.houseId": '',
  "renderType.templateId": '',
  "renderType.isMember": false, // 是否包年包月
  "renderType.bindingShow": false,
  // "renderType.overlapResolve": 0  
}
// 声明事件
let renderTypeEvent = {
  chooseRenderType(e) {
    this.setData({
      "renderType.renderValue": parseInt(e.detail.value)
    })
  },
  confirmRenderType(e) {
    this.setData({
      "renderType.renderTypeShow": false
    })
    let type = e.currentTarget.dataset.type, renderTaskType = null
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
      that = this,
      planRecommendedId = renderParams.renderCaseItem.designPlanRecommendId || renderParams.renderCaseItem.planRecommendedId,
      totalFee = null
    this.data.renderType.isMember ? totalFee = 0 : totalFee = 1
    API.addRenderTask({
      taskSource: 0,
      taskType: 0,
      houseId: renderParams.houseId,
      planRecommendedId: planRecommendedId,
      templateId: renderParams.templateId,
      renderTaskType: renderParams.renderTaskType,
      operationSource: 1,
      totalFee: totalFee
    })
      .then(res => {
        let mes = res.message;
        if (res.success) {
          mes === '创建渲染任务成功！' ? that.wxShowModal('正在渲染中...', '预计3分钟后完成', '查看任务', '返回', '#999', '#ff6419', '/pages/my-tasks/my-tasks') : wx.showToast({ title: res.message, icon: 'none' });
        } else {
          mes === '账户度币不足，请充值.' ? that.wxShowModal('提示', '度币余额不足，去充值？', '去充值', '暂不考虑', '#999', '#ff6419', '/pages/buy-points/buy-points') : that.wxShowModal('提示', '户型数量已用完，购买更多？', '去购买', '暂不考虑', '#999', '#ff6419', '/pages/purchase-house/purchase-house');
        }
      })
  },
  wxShowModal: function (tit = '', cont = '', conf = '', canct = '', cancc = '', confc = '', url = '') {
    wx.showModal({
      title: tit,
      content: cont,
      confirmText: conf,
      cancelText: canct,
      cancelColor: cancc,
      confirmColor: confc,
      success: (res) => {
        res.confirm ? wx.navigateTo({ url: url }) : '';
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
  ,
  bindFasle() {
    this.setData({ 'renderType.overlapResolve': 0 })
  }
  ,
  bindTrue() {
    this.setData({ 'renderType.overlapResolve': 1 })
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
  API.getRenderType()
    .then(res => {
      if (res.status) {
        res.obj[0].checked = true
        curPage.setData({
          "renderType.renderTypeList": res.obj,
          "renderType.renderValue": res.obj[0].renderValue
        })
      } else {
        curPage.setData({ "renderType.renderTypeList": [] })
      }
    })
    .catch(() => {
      curPage.setData({ "renderType.renderTypeList": [] })
    })
}
// 获取包年包月信息
function getuserIsPackageInMonthly(curPage) { // 获取用户包年包月信息
  API.getuserIsPackageInMonthly()
    .then((res) => {
      if (res.success) {
        if (res.obj.state === '0') {
          curPage.setData({ 'renderType.isMember': false })
        } else if (res.obj.state === '2' || res.obj.state === '3') {
          curPage.setData({ 'renderType.isMember': true })
        }
      }
    })
}

function getIsBindPhone(curPage) {
  // 检验手机号是否存在
  API.getIsBindingMobile()
    .then(res => {
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
  // getIsBindPhone(curPage)
  getuserIsPackageInMonthly(curPage)
  Object.assign(curPage, renderTypeEvent)
  curPage.setData(renderTypeData)
  curPage.renderType = this
  return this
}

module.exports = {
  renderTypeExample
}
