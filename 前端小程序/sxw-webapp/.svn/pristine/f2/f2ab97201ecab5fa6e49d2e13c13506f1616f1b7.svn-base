import myForEach from '../../utils/utils.js'
let API = getApp().API
// 声明初始数据
let renderTypeData = {
  "renderType.renderTypeShow": false,
  "renderType.renderTypeList": [],
  "renderType.renderValue": 1,
  "renderType.renderCaseItem": {},
  "renderType.houseId": '',
  "renderType.templateId": '',
  "renderType.isMember": false, // 是否包年包月
  "renderType.bindingShow": true  
}
// 声明事件
let renderTypeEvent = {
  chooseRenderType(e) {
    this.setData({
      "renderType.renderValue": parseInt(e.detail.value)
    })
  },
  submitInfo(e){ // 模板消息接口调用
    wx.setStorage({ key: 'formId', data: e.detail.formId })            
    API.saveUserRenderFormId({
      designPlanId: this.data.renderType.renderCaseItem.planRecommendedId || this.data.renderType.renderCaseItem.designPlanRecommendId,//方案id
      formId: e.detail.formId,//表单ID
        forwardPage: 'pages/home/home?navToUrl=/pages/mine/my-favorite-fitment/my-favorite-fitment',//跳转页
      renderType: 0,//渲染类型：0，装进我加；1，替换渲染  
      renderLevel: this.data.renderType.renderValue //渲染级别：1，照片；4：720；6，视频；8，720
    }).then(res => {
        console.log(res, '亮眼')
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
    let planRecommendedId = renderParams.renderCaseItem.designPlanRecommendId || renderParams.renderCaseItem.planRecommendedId
    API.addRenderTask({
      taskSource: 0,
      groupPrimaryId: renderParams.renderCaseItem.groupPrimaryId,
      taskType: 0,
      houseId: renderParams.houseId,
      planRecommendedId: planRecommendedId,
      templateId: renderParams.templateId,
      renderTaskType: renderParams.renderTaskType,
      operationSource: 1,
      totalFee: this.data.renderType.isMember ? 0 : 1
    })
    .then(res => {
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
              res.confirm ? wx.navigateTo({ url: '/pages/my-tasks/my-tasks' }) : null
            }
          })
        } else {
          wx.showToast({ title: '111' + res.message, icon: 'none' })
        }
      } else {
        if (res.message === '账户度币不足，请充值.') {
          this.insufficient()
        } 
        // else if (res.message === '账户户型不足，请购买户型.') {
        //   wx.showModal({
        //     title: '提示',
        //     content: '户型数量已用完，购买更多？',
        //     confirmText: '去购买',
        //     cancelText: '暂不考虑',
        //     cancelColor: '#999999',
        //     confirmColor: '#ff6419',
        //     success: (res) => {
        //       res.confirm ? wx.navigateTo({ url: '/pages/purchase-house/purchase-house' }) : null
        //     }
        //   })
        // } 
        else {
          wx.showModal({
            title: '提示',
            content: res.message,
            confirmText: '确认',
            cancelText: '取消',
            cancelColor: '#999999',
            confirmColor: '#ff6419'
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
// 获取数据
function getRenderType(curPage) {
  API.getRenderValue()
  .then(res => {
    if (res.status) {
      res.obj[0].checked = true
      curPage.setData({ "renderType.renderTypeList": res.obj, "renderType.renderValue": res.obj[0].renderValue })
    } else {
      curPage.setData({ "renderType.renderTypeList": [] })
    }
  })
}
// 获取包年包月信息
function getuserIsPackageInMonthly(curPage) { // 获取用户包年包月信息
  API.getuserIsPackageInMonthly()
  .then(res => {
    if (res.success) {
      if (res.obj.state === '0') {
        curPage.setData({ 'renderType.isMember': false })
      } else if (res.obj.state === '2' || res.obj.state === '3') {
        curPage.setData({ 'renderType.isMember': true })
      }
    }
  })
}

function getIsBindPhone(curPage) { // 绑定手机号
  API.getIsBindingMobile()
  .then(res => {
    if (res.success) {
      if (res.obj === 0) {
        curPage.setData({ 'renderType.bindingShow': false })
      } else if (res.obj === 1) {
        curPage.setData({ 'renderType.bindingShow': true })
      }
    } else {
      this.setData({ 'renderType.bindingShow': false })
    }
  })
}
// 声明实例
function renderTypeExample () {
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
