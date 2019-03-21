// component/case-list/case-list.js
let myForEach = getApp().myForEach, mySplitUrl = getApp().mySplitUrl,
  myCompoundUrl = getApp().myCompoundUrl, $App = getApp(), API = getApp().API, ttt = ''
let flagn = 0;
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    navText: {
      type: String,
      value: ''
    },
    isHomeCase: {
      type: Boolean,
      value: false
    },
    recommendCaseList: {
      type: Array,
      value: [],
    },
    border: {
      type: Boolean,
      value: false
    },
      newView:{
          type: Boolean,
          value: false
      },
    isOneKeyToDecorate: {
      type: Boolean,
      value: false
    },
    nowCase: {
      type: Boolean,
      value: false
    },
    isRender: {
      type: Number,
      value: 0
    },
    planDetailFlag: {
      type: Boolean,
      value: false
    },
    showCopyRightPrice: {
      type: Boolean,
      value: false,
    },
    /**
     * 渲染参
     */
    renderParams: {
      type: Object,
      value: {}
    },
    templateId: {
      type: Number,
      value: 0
    },
    shopId: {
      type: Number,
      value: 0
    },
    isGoods: {
      type: String,
      value: ''
    },
    isRecommonPlan: {
      type: Number,
      value: 0
    },
    isShowDetailsBtn: {
      type: String,
      value: '0'
    },
    recommendCaseType: {
      type: String,
      value: 'recommend'
    },
    sdId:{
      type:Number,
      value:0
    },
    rcType:{
      type:Number,
      value:0
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    collectRequest: true,
    favoriteRequest: true,
    decoratePriceBox: false,
    decoratePriceList: [],
    decotateMaskHeight: wx.getSystemInfoSync().windowHeight,
    planCostShow: false,
    havePurchased:0,
    planPrice: 0,
    copyRightPermission: 0,
    flagn:0,
  },
  /**
   * 组件的方法列表
   */
  methods: {
    closePlanCost() {
      this.setData({
        planCostShow: false
      })
    },
    ShowplanCost(e) {    
      let item = e.currentTarget.dataset.item;
        this.data.newView ? this.triggerEvent('myevent', { paramBtoA: item }):''
      this.setData({
        planCostShow: true,
        havePurchased: item.havePurchased,
        planPrice: item.planPrice,
        copyRightPermission: item.copyRightPermission
      })
    },
    //子组件向父组件传参
     
    routerToHouseSearch(e) {
        let item = e.detail.target.dataset.item, formId = e.detail.formId
        wx.setStorageSync('caseItem', Object.assign(item, { formId: formId}))
        wx.navigateTo({ url: '/pages/search-houseType/search-houseType?type=plan' })
        this.sellingPoint('Renovation-house')
    },
    sellingPoint(event) {
      let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
        nowPath = page[page.length - 1].route
      console.log(event)
      API.sellingPoint({
        uid: wx.getStorageSync('openId'),
        cp: nowPath,
        lp: previousPath,
        e: event ? event : '',
        pt: this.data.navText
        // }, event ? { event: event } : {}
      })
    },
    formToThreeD(e) {
        // if (e.detail.target.dataset.type =='push'){
        //     flagn = 1;
        //     this.routerToHouseSearch(e.detail.target.dataset.item, e.detail.formId);
        // }else{
        //     if (flagn)
        //         return;
      let type = e.detail.target.dataset.type, item = e.detail.target.dataset.item, routerQueryType = '',
        formId = e.detail.formId, webUrl = null, sevenObj = null, planHT
      wx.setStorageSync('caseItem', item)
      this.sellingPoint('bigPth')
      // if (this.data.isRender === 0 && this.data.isRecommonPlan === 1) { // 推荐方案立即装修
      //   wx.navigateTo({ url: '/pages/search-houseType/search-houseType?type=plan' })
      //   return 
      // }
      type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
      if (item.planHouseType) {
        planHT = item.planHouseType
      } else if (item.spaceType) {
        item.spaceType == 13 ? planHT = 2 : planHT = 1
      } else {
        item.spaceFunctionId == 13 ? planHT = 2 : planHT = 1
      }

      item.fullHousePlanUUID ? (webUrl = $App.wholeHouse3dUrl, sevenObj = {
        uuid: item.fullHousePlanUUID,
        token: wx.getStorageSync('token'),
        platformCode: 'brand2c',
        planId: item.designPlanRecommendId || item.planRecommendedId,
        customReferer: $App.wxUrl,
        platformNewCode: 'miniProgram',
        formId: formId,
        isRender: this.data.isRender,
        groupPrimaryId: item.groupPrimaryId || '',
        houseId: item.houseId || '',
        templateId: item.templateId,
        planHouseType: planHT,
        operationSource: 0,
        isGoods: this.data.isGoods
      }) :
        (webUrl = $App.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 1,
          planId: item.designPlanRecommendId || item.planRecommendedId,
          routerQueryType: routerQueryType,
          customReferer: $App.wxUrl,
          platformNewCode: 'miniProgram',
          formId: formId,
          isRender: this.data.isRender,
          shopId: this.data.shopId || item.shopIn720Page ||'',
          isGoods: this.data.isGoods
        });
        if(this.data.sdId){
          sevenObj = Object.assign(sevenObj,{
            sdId:this.data.sdId
          })
        }
      if (this.data.isRender === 1) {
        Object.assign(sevenObj, {
          taskSource: 0,
          planHouseType: 0,
          taskType: 0,
          totalFee: 0,
          fullHousePlanAction: this.data.renderParams.fullHousePlanId ? 2 : 1,
          renderTaskType: 'panorama_render',
          groupPrimaryId: item.groupPrimaryId,
          houseId: this.data.renderParams.houseId,
          templateId: this.data.templateId,
          designTempletId: this.data.templateId,
          spaceFunctionId: item.spaceType || item.spaceFunctionId,
          fullHousePlanId: this.data.renderParams.fullHousePlanId || '',
          taskId: this.data.renderParams.mainTaskId || '',
          preRenderSceneId: this.data.renderParams.preRenderSceneId || '',
          // planId: '87792'
        }, item.spaceType === 13 ? {
          bizType: 2
        } : {});
      
      }

      console.log(sevenObj);

      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }

      getApp().data.webUrl = webUrl;

      /** // 渲染参数
       * "houseId": 413485,
       * "operationSource": 1, // 1 方案进 0 我的进
       * "planHouseType": 3,
       * "planRecommendedId": 293555,
       * "renderTaskType": "panorama_render",
       * "taskSource": 1, // 0 C端
       * "taskType": 1,  // 0 自动渲染 1 产品替换
       * "totalFee": 10,
       * "fullHousePlanAction": 2,  // 1 第一次渲染 2 替换旧方案且不产生新的全屋方案 3 替换旧方案并且产生新的全屋方案
       * "templateId": 392023,
       * "groupPrimaryId": 0,
       * "houseGuidePicInfoId": 529,
       * "designTempletId": 392023,
       * "spaceFunctionId": 4,
       * "fullHousePlanId": 274,
       * "taskId": 52396,
       * "preRenderSceneId": 47560
       */
      wx.navigateTo({ url: '/pages/web-720/web-720' });

      console.log(webUrl)
        // }
    },
    toThreeD(e) { // 调转到3D界面
      let type = e.currentTarget.dataset.type, item = e.currentTarget.dataset.item, routerQueryType = null, webUrl = null, sevenObj = null
      if (type === 'video') {
        API.getRecommendedVideoId({
          planRecommendedId: item.designPlanRecommendId || item.planRecommendedId,
          remark: type
        })
          .then(res => {
            if (res.success) { return res.datalist[0].id } else { return false }
          })
          .then(res => {
            if (res) {
              API.getRecommendedVideoMessage({ thumbId: res })
                .then(res => {
                  res.success ? this.toVideo(res.obj.url) : wx.showToast({ title: '打开失败', icon: 'none' })
                })
            }
          })
      } else {
        type === '720' ? routerQueryType = 'seven' : routerQueryType = 'roam'
        item.fullHousePlanUUID ? (webUrl = $App.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
          (webUrl = $App.sevenUrl, sevenObj = {
            token: wx.getStorageSync('token'),
            platformCode: 'brand2c',
            operationSource: 1,
            planId: item.designPlanRecommendId || item.planRecommendedId,
            routerQueryType: routerQueryType,
            customReferer: $App.wxUrl,
            platformNewCode: 'miniProgram'
          })
        for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
        getApp().data.webUrl = webUrl
        wx.navigateTo({ url: '/pages/web-720/web-720' })
        console.log(webUrl)
      }
    },
    collectCase(e) { // 方案收藏
      let index = e.currentTarget.dataset.index
      this.collectOrLikeCase({
        title: '收藏',
        flag: 'collectRequest',
        num: 'collectNum',
        status: 'isFavorite',
        api: 'collectCase',
        param: 'recommendId',
        index
      })
    },
    test() {
      console.log("123456789")
    },
    likeCase(e) { // 方案点赞
      console.log("123")
      let index = e.currentTarget.dataset.index
      this.collectOrLikeCase({
        title: '点赞',
        flag: 'favoriteRequest',
        num: 'likeNum',
        status: 'isLike',
        api: 'likeCase',
        param: 'designId',
        index
      })
    },
    collectOrLikeCase(obj) {
      console.log("123", this.data.recommendCaseList[obj.index].spaceType ? (this.data.recommendCaseList[obj.index].spaceType == 13 ? 2 : 1) : (this.data.recommendCaseList[obj.index].spaceFunctionId == 13 ? 2 : 1))
      let that = this, status = null, title = null
      if (this.data[obj.flag] == true) {
        this.setData({ [obj.flag]: false })
        this.data.recommendCaseList[obj.index][obj.status] ? (status = 0, title = '取消' + obj.title) : (status = 1, title = obj.title)
        API[obj.api]({ status: status, [obj.param]: this.data.recommendCaseList[obj.index].designPlanRecommendId || this.data.recommendCaseList[obj.index].planRecommendedId, designPlanType: this.data.recommendCaseList[obj.index].planHouseType || (this.data.recommendCaseList[obj.index].spaceType ? (this.data.recommendCaseList[obj.index].spaceType == 13 ? 2 : 1) : (this.data.recommendCaseList[obj.index].spaceFunctionId == 13 ? 2 : 1)) })
          .then(res => {
            if (res.success) {
              status == 0 ? this.data.recommendCaseList[obj.index][obj.num] -= 1 : this.data.recommendCaseList[obj.index][obj.num] += 1
              if (this.data.recommendCaseList[obj.index][obj.num]<0){
                this.data.recommendCaseList[obj.index][obj.num] = 0
              }
                
              this.data.recommendCaseList[obj.index][obj.status] = status
              this.setData({ recommendCaseList: this.data.recommendCaseList })
              wx.showToast({ title: title + '成功' })
            } else {
              wx.showToast({ title: title + '失败', icon: 'none' })
            }
            setTimeout(function () { that.setData({ [obj.flag]: true }) }, 500)
          })
      }
    },
    routerToCaseDetails(e) {
      let id = e.currentTarget.dataset.id

        , type = e.currentTarget.dataset.type
      wx.navigateTo({ url: `/pages/case-details/case-details?id=${id}&type=${type || 0}` })
    },
    putInMyhouse(e) { // 装进我家
      let item = e.currentTarget.dataset.item
      if (this.data.isOneKeyToDecorate) {
        this.data.nowCase ? item.type = 'seven' : 'item'
        this.triggerEvent('putInMyhouse', item, { bubbles: true })
      } else {
        let item = e.currentTarget.dataset.item
        wx.setStorageSync('caseItem', item)
        wx.navigateTo({
          url: '/pages/search-houseType/search-houseType?type=plan'
        })
      }
    },
    showDecoratePriceBox(e) {
      console.log("123")
      let item = e.currentTarget.dataset.item, height = wx.getSystemInfoSync().windowHeight
      myForEach(item, (value) => {
        switch (value.decorateTypeName) {
          case "半包": value.text = '辅材+人工费+管理费'; break;
          case "清水": value.text = '人工费+管理费'; break;
          case "全包": value.text = '主材+辅材+人工费+管理费'; break;
          case "包软装": value.text = '主材+辅材+人工费+管理费+部分软装'; break;
        }
      })
      console.log(this.data.isHomeCase)
      if (this.data.isHomeCase) {
        this.triggerEvent('showDecoratePriceBox', item, { bubbles: true })
      } else {
        this.setData({ decoratePriceList: item, decoratePriceBox: true })
        this.triggerEvent('showDecoratePriceBox', true, { bubbles: true })
      }
    },
    hideDecoratePriceBox() {
      this.setData({ decoratePriceBox: false })
      this.triggerEvent('showDecoratePriceBox', false, { bubbles: true })
    },
    toVideo(url) {
      wx.navigateTo({
        url: '/pages/template/video/video?url=' + url
      })
    },
    toImg(e) {
      let imgArr = [this.data.resourcePath + e.currentTarget.dataset.item.designPlanCoverPath];
      wx.previewImage({
        //当前显示下表
        current: imgArr[0],
        //数据源
        urls: imgArr
      })
    },
    toPlanDetail(e) {
      let type;
      console.log(e.currentTarget.dataset.item)
      if (e.currentTarget.dataset.item.spaceType == 13) {
        type = 1
      } else {
        type = 0
      }
      let id = e.currentTarget.dataset.item.planRecommendedId
      wx.navigateTo({
        url: '/pages/decoration/planIntroduction/planIntroduction?id=' + id + '&type=' + type,
      })
    },
  }
})
