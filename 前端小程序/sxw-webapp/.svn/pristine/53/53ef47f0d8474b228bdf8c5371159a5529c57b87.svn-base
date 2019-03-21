// component/homeBanner/homeBanner.js
  let API = getApp().API,
  myForEach = getApp().myForEach,
  mySplitUrl = getApp().mySplitUrl,
  myCompoundUrl = getApp().myCompoundUrl,
  $App = getApp(),
  ttt = '';
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    imageArray: {
      type: Array,
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    resourcePath: getApp().resourcePath,
    previousPath:'',
    nowPath:''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    goto(e) {
      // rzd 埋点 190214
      let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
        nowPath = page[page.length - 1].route;
      this.setData({
        previousPath: previousPath,
        nowPath: nowPath
      })
      let url = e.detail.target.dataset.url,
        type = e.detail.target.dataset.type,
        bannerId = e.detail.target.dataset.bannerid,
        name = e.detail.target.dataset.name;
      if (getApp().basePages.some(e => url.indexOf(e) != -1)) {
        wx.switchTab({
          url: url,
        })
      }else{
        wx.setStorage({
          key: 'pageTitle',
          data: name
        })
        wx.navigateTo({
          url: url,
        })
        $App.sellingPoint(API, `homegetFormId${bannerId}`, this.data.nowPath, this.data.previousPath, '首页');
      }
    }
  }
})