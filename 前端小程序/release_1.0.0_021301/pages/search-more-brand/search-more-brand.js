// pages/search-more-brand/search-more-brand.js
import { myPinYin } from '../../utils/Convert_Pinyin.js';
let myForEach = getApp().myForEach
Page({

  /**
   * 页面的初始数据
   */
  data: {
    moreBrandList: {
      A: [],
      B: [],
      C: [],
      D: [],
      E: [],
      F: [],
      G: [],
      H: [],
      I: [],
      J: [],
      K: [],
      L: [],
      M: [],
      N: [],
      O: [],
      P: [],
      Q: [],
      R: [],
      S: [],
      T: [],
      U: [],
      V: [],
      W: [],
      X: [],
      Y: [],
      Z: [],
      '#': []
    },
    threeIndex: -1,
    brandContentSecondIndex: '',
    categoryCode: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    console.log(myPinYin.getFullChars('1'))
    this.setData({
      threeIndex: options.index
    })
    this.getMoreBrandList()
  },
  getMoreBrandList() { // 获取更多品牌列表
    let moreBrandList = wx.getStorageSync('moreBrandList'), count = 0
    myForEach(moreBrandList, (value, index) => {
      let str = myPinYin.getFullChars(value.categoryName).slice(0,1)
      let key = this.findKey(this.data.moreBrandList, (n) => { return n === str })
      if (key) {
        this.data.moreBrandList[key].push(value)
        count == 0?this.setData({
          userScrollKey: key
        }):'';
        count++
      } else {
        this.data.moreBrandList['#'].push(value)
      }
    })
    this.setData({
      moreBrandList: this.data.moreBrandList,
    })
    console.log(this.data.moreBrandList)
  },
  findKey(obj, callback) { // 是否有相应的key
    let flag = false, alone = false
    for (let key in obj) {
      if (callback(key)) {
        alone = key
        flag = true
      }
    }
    if (flag) {
      return alone
    } else {
      return alone
    }
  },
  scrollKey(e) { // 焦点滚动
    let key = e.currentTarget.dataset.key
    this.setData({
      userScrollKey: key
    })
  },
  chooseBrand(e) {
    let key = e.currentTarget.dataset.key, 
      index = e.currentTarget.dataset.index, code = e.currentTarget.dataset.categorycode
    this.setData({
      brandContentSecondIndex: key + index,
      categoryCode: code
    })
  },
  confirmBrand(e) {
    let type = e.currentTarget.dataset.type, pages = getCurrentPages()
    let page = pages[pages.length - 2]    
    let moreBrandList = wx.getStorageSync('moreBrandList')
    if (type === 'confirm') {
      if (this.data.brandContentSecondIndex) {
        let index = moreBrandList.findIndex((n) => { return n.categoryCode == this.data.categoryCode})
        page.data.secondFiltrateActive[this.data.threeIndex] = index
        page.setData({
          secondFiltrateActive: page.data.secondFiltrateActive
        })
        console.log(page.data.secondFiltrateActive, this.data.threeIndex,  index, type, 'wqw2')
      }
    }
    wx.navigateBack()    
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})