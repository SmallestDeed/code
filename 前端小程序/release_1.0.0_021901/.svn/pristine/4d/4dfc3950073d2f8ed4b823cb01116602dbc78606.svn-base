// component/team-list/team-list.js1
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    list: {
      type: Array,
      value: []
    }
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
    goutong(e) {
      let items = e.currentTarget.dataset.item
      let item = JSON.stringify(items);
      wx.navigateTo({
        url: `/pages/chat/chat?item=${item}`,
      }) 
    },
    goDetails(e) {
      let item = e.currentTarget.dataset.item
      wx.navigateTo({
        url: `/pages/designer/designer?shopId=${item.id}`
      })
    },
    examineImg(e) {
      let img = this.data.resourcePath + e.currentTarget.dataset.img
      wx.previewImage({
        urls: [img],
        current: img
      })
    }
  }
})
