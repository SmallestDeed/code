// component/jdNav/jdNav.js
import {
  resourcePath,
  staticImageUrl
} from '../../utils/config.js'
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    isShowMask: {
      type: Boolean,
      value: false
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    staticImageUrl: staticImageUrl
  },

  /**
   * 组件的方法列表
   */
  methods: {
    navSwitch() {
      this.setData({
        isShowMask: !this.data.isShowMask
      })
    },
    toUrl(e) {
      if (e.currentTarget.dataset.url) {
        wx.navigateTo(e.currentTarget.dataset);
      }
    },
    toTab(e) {
      if (e.currentTarget.dataset.url) {
        wx.switchTab(e.currentTarget.dataset);
      }
    }
  }
})