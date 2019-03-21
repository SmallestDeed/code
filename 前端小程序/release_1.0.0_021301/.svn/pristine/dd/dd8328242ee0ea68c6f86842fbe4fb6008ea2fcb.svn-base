// component/homeBanner/homeBanner.js
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
  },

  /**
   * 组件的方法列表
   */
  methods: {
    goto(e) {
      let url = e.detail.target.dataset.url,
        type = e.detail.target.dataset.type,
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
      }
    }
  }
})