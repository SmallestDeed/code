// component/share-wx/share-wx.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    isShow:false
  },

  /**
   * 组件的方法列表
   */
  methods: {
     close(){
       this.setData({
         isShow:false
       })
     },
    toggleDialog(type) { //弹窗显示隐藏
      this.setData({
        isShow: type
      })
    },
    createPoster(){ //
      this.triggerEvent('myevent','')
    }
  }
})
