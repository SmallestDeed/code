// component/empty-page/empty-page.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
     text:{
        type:String,
        value:'暂无信息'
     }
  },

  /**
   * 组件的初始数据
   */
  data: {
    isShow:false,
    staticImageUrl: getApp().staticImageUrl,
  },

  /**
   * 组件的方法列表
   */
  methods: {
      toggleDialog(type){ //页面显示隐藏
        this.setData({
          isShow: type
        })
      }
  }
})
