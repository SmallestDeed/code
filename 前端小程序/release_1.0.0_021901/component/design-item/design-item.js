let app = getApp(), API = getApp().API
Component({
  properties: {
    // 这里定义了接收一个design对象
    design: {
      type: Object,
      value: {}
    },
    index: {
      type: Number,
      value: 0
    },
    caseItem: {
      type: Boolean,
      value: false
    },
    type: {
      type: String,
      value: ''
    }
  },
  data: {
    // 这里是一些组件内部数据
    resourcePath: app.resourcePath,
  },
  methods: {
    // 视频
    toThreeD(e) {
      let type = e.currentTarget.dataset.type;
      let item = this.data.design;
      let routerQueryType = null, webUrl = null, sevenObj = null
      console.log(type)
      API.getRecommendedVideoId({
        planRecommendedId: item.designPlanRecommendId || item.planRecommendedId,
        remark: type
      })
        .then(res => {
          if (res.datalist.length > 0) { return res.datalist[0].id } else { return false }
        })
        .then(res => {
          if (res) {
            API.getRecommendedVideoMessage({ thumbId: res })
              .then(res => {
                console.log(res);
                res.success ? this.toVideo(res.obj.url) : wx.showToast({ title: '打开失败', icon: 'none' })
              })
          }
        })
    },

    toVideo(url) {
      wx.navigateTo({
        url: '/pages/video/video?url=' + url
      })
    },

    /*跳转720*/
    go720(e) {
      let type = e.detail.target.dataset.type;
      let item = this.data.design;
      let formId = e.detail.formId;
      let webUrl = null;
      let sevenObj = null;
      let routerQueryType = type == '720' ? 'seven' : 'roam';
      item.fullHousePlanUUID ? (webUrl = app.wholeHouseUrl, sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 }) :
        (webUrl = app.sevenUrl, sevenObj = {
          token: wx.getStorageSync('token'),
          platformCode: 'brand2c',
          operationSource: 1,
          planId: item.designPlanRecommendId || item.planRecommendedId,
          routerQueryType: routerQueryType,
          customReferer: app.wxUrl,
          platformNewCode: 'miniProgram',
          formId: formId
        })
      for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&' }
      getApp().webUrl = webUrl
      app.myNavigateBack('pages/web-720/web-720')
      console.log(webUrl)
      // let formId = e.detail.formId;
      // let item = this.data.design;
      // let webUrl = null;
      // let sevenObj = null;
      // let routerQueryType = 'seven';
      // if (item.fullHousePlanUUID) {
      //   webUrl = app.wholeHouseUrl;
      //   sevenObj = { uuid: item.fullHousePlanUUID, embedded: 1 };
      // } else {
      //   webUrl = app.sevenUrl;
      //   sevenObj = {
      //     token: wx.getStorageSync('token'),
      //     platformCode: 'brand2c',
      //     operationSource: 1,
      //     planId: item.designPlanRecommendId || item.planRecommendedId,
      //     routerQueryType: routerQueryType,
      //     customReferer: app.wxUrl,
      //     platformNewCode: 'miniProgram',
      //     formId: formId
      //   };
      // }
      // for (let key in sevenObj) { webUrl += key + '=' + sevenObj[key] + '&'};
      // getApp().webUrl = webUrl;
      // console.log(webUrl);
      // wx.navigateTo({ url: '/pages/web-720/web-720' });
      // console.log(webUrl)
    },

    /*查看图片*/
    examineImg() {
      let img = this.data.resourcePath + this.data.design.coverPath
      wx.previewImage({
        urls: [img],
        current: img
      })
    },

    /*装进我家*/
    goSelectHouse(e) {
      console.log(this.data.type)
      let item = this.data.design;
      item.index = this.data.index;
      item.isCaseItem = this.data.caseItem;
      item.formId = e.detail.formId;
      if (this.data.type == 'Onekey') {
        this.data.nowCase ? item.type = 'seven' : 'item';
        this.triggerEvent('putInMyhouse', item, { bubbles: true })
      } else {
        wx.setStorageSync('caseItem', item)
        wx.navigateTo({ url: '/pages/seekHouse/seekHouse' })
      }
    },

    /*点赞，收藏*/
    operation(e) {
      let type = e.currentTarget.dataset.type;
      let design = this.data.design;
      let title, params;
      let designPlanType = design.planHouseType || design.spaceType ? (design.spaceType == 13 ? 2 : 1) : (design.spaceFunctionId == 13 ? 2 : 1)
      //收藏
      if (type == 'collectCase') {
        design.isFavorite == 0 ? design.collectNum++ : design.collectNum--;
        design.isFavorite = design.isFavorite == 0 ? 1 : 0; 
        title = design.isFavorite == 1 ? '收藏' : '取消';
        params = {
          recommendId: design.designPlanRecommendId || design.planRecommendedId,
          designPlanType: designPlanType,
          status: design.isFavorite
        }
      }
      //点赞
      if (type == 'likeCase') {
        design.isLike == 0 ? design.likeNum++ : design.likeNum--;
        design.isLike = design.isLike == 0 ? 1 : 0;
        title = design.isLike == 1 ? '点赞' : '取消';
        params = {
          designId: design.designPlanRecommendId || design.planRecommendedId,
          designPlanType: designPlanType,
          status: design.isLike
        }
      }
      this.request({
        getName: type, // 请求方法名称
        title: title, // 提示信息
        num: type == 'collectCase'? design.collectNum : design.likeNum, // 点赞数，收藏数
        params: params, // 请求参数
        successObj: design // 请求成功后的改变对象
        })
    },

    /*点赞，收藏请求*/
    request(obj) {
      API[obj.getName](obj.params).then(res => {
        if (res.success) {
          this.setData({ design: obj.successObj });
          wx.showToast({ title: obj.title + '成功' });
        } else {
          wx.showToast({ title: obj.title + '失败' });
        }
      });
    }
  }
})