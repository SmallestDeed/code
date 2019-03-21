Component({
  properties: {
    /** 这里定义了innerText属性，属性值可以在组件使用时指定
     * emptyPageObj接收4个参数
     * imgUrl: 显示图片的URL
     * title: 显示文字内容
     * btnText：按钮内容
     * url: 按钮需要跳转的页面url
    */
    emptyPageObj: {
      type: Object,
      show: false
    }
  },
  data: {
    // 这里是一些组件内部数据
  },
  pageLifetimes: {
    // 组件所在页面的生命周期函数

  },
  methods: {
    // 这里是一个自定义方法
    goPage() {
      if (this.data.emptyPageObj.url) {
        if (this.data.emptyPageObj.switchTab) {
          wx.switchTab({
            url: this.data.emptyPageObj.url
          })
        } else {
          wx.navigateTo({
            url: this.data.emptyPageObj.url
          });
        }
      }
    }
  }
})