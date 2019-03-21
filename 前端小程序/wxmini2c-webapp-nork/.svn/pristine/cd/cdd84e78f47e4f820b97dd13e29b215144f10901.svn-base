// pages/activeKj/activeKj.js
let $App = getApp(),
  fetch = getApp().fetch
import { appid } from '../../utils/config.js';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    appid:appid,
    resourcePath: getApp().resourcePath,
    staticImageUrl: getApp().staticImageUrl,
    activeList:'',
    endList:[],
    countDownList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getList();
  },
  getList(){
    let url = '/core/company/actbargain/'+this.data.appid
    fetch(url, 'get', '', 'core')
      .then(res => {
        
        if(res.datalist){
          this.setData({
            activeList:res.datalist
          })
          let endList=[];
          for(let i=0;i<res.datalist.length;i++){
            res.datalist[i].endTime = res.datalist[i].endTime.replace(/-/g, "/")
            endList.push(res.datalist[i].endTime)
          }
          this.setData({
            endList: endList
          })
          this.countDown();
        }
      })
  },
  countDown() {//倒计时函数


    // 获取当前时间，同时得到活动结束时间数组
    let newTime = new Date().getTime();
    let endTimeList = this.data.endList;
    let countDownArr = [];
    // 对结束时间进行处理渲染到页面
    endTimeList.forEach(o => {
      let endTime = new Date(o).getTime();
      let obj = null;
      // 如果活动未结束，对时间进行处理
      if (endTime - newTime > 0) {
        let time = (endTime - newTime) / 1000;
        // 获取天、时、分、秒
        let day = parseInt(time / (60 * 60 * 24));
        let hou = parseInt(time % (60 * 60 * 24) / 3600);
        let min = parseInt(time % (60 * 60 * 24) % 3600 / 60);
        let sec = parseInt(time % (60 * 60 * 24) % 3600 % 60);
        obj = {
          day: this.timeFormat(day),
          hou: this.timeFormat(hou),
          min: this.timeFormat(min),
          sec: this.timeFormat(sec)
        }
      } else {//活动已结束，全部设置为'00'
        obj = {
          day: '00',
          hou: '00',
          min: '00',
          sec: '00'
        }
      }
      countDownArr.push(obj);
    })
    // 渲染，然后每隔一秒执行一次倒计时函数
    this.setData({ countDownList: countDownArr })
    setTimeout(this.countDown, 1000);
  },
  timeFormat(param) {//小于10的格式化函数
    return param < 10 ? '0' + param : param;
  },
  toActive(e){
    let id =e.currentTarget.dataset.id;
    wx.navigateTo({
      url: "/pages/cutprice/cutprice?actId=" + id + "&userId=" + wx.getStorageSync('userId') +'&regId=',
    })
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