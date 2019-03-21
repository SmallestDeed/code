// pages/decoration/supplyDetail/supplyDetail.js
import { resourcePath } from '../../../utils/config.js'
let API = getApp().API
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:'',
    siShowDetail: false,
    detail:[],
    resourcePath: resourcePath,
    supplyIcon: '/static/image/service_icon_gong.png',
    demandIcon: '/static/image/service_icon_qiu.png',
    emptyPageObj: {},
    mold: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    new $App.newNav()
    this.setData({ 
      id: options.id,
      mold: options.mold ? options.mold : ''
    })
    this.getDetail();
    this.addViewNum();
  },
  changeTiem(tiem) {
    let leadTime = new Date().getTime() - new Date(tiem.replace(/\-/g, '/')), date;
    if (leadTime / 1000 / 60 < 1) {
      date = '刚刚';
    }
    if (leadTime / 1000 / 60 > 1 && leadTime / 1000 / 60 < 60) {
      date = (leadTime / 1000 / 60).toFixed(0) + '分钟前';
    }
    if (leadTime / 1000 / 3600 > 1 && leadTime / 1000 / 3600 < 60) {
      date = (leadTime / 1000 / 3600).toFixed(0) + '小时前';
    }
    if (leadTime / 1000 / 3600 / 24 > 1) {
      date = '1天前';
    }
    return date;
  },
  goEdit() {
    wx.navigateTo({
      url: '/pages/decoration/editPublish/editPublish',
    });
  },
  getDetail(){
    API.getallsupplydemandinfo({ supplyDemandId: this.data.id })
    .then(res => {
      if (res.obj) {
        let obj = res.obj[0];
        obj.gmtModified = this.changeTiem(obj.gmtModified);
        this.setData({ detail: obj, siShowDetail: true });
      } else {
        this.setData({
          siShowDetail: false,
          emptyPageObj: {
            imgUrl: '/static/image/issue.png',
            title: res.message,
          }
        });
      }
    })
  },
  addViewNum(){
    let url ='union/supplydemand/addsupplydemandviewnum'
    API.addSupplyDemandViewCount({ id: this.data.id })
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