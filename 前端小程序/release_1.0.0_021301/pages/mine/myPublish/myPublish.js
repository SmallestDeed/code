import {
  resourcePath
} from '../../../utils/config.js'
let API = getApp().API;
let $App = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: resourcePath,
    type: 0,
    curPage: 1,
    pageSize: 5,
    status: 1,
    List: {},
    emptyPageObj: {},
    staticImageUrl: getApp().staticImageUrl,
    sDDefaultImg:'',
    // 埋点数据
    previousPath: '',
    nowPath: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function() {
    wx.hideShareMenu();
    console.log('加载');
    new $App.newNav() // 注册快速导航组件
    this.setData({
      sDDefaultImg: this.data.staticImageUrl + 'news_pic_nor.png'
    })
    // rzd 埋点 190214 start
    let page = getCurrentPages(), previousPath = page.length > 1 ? page[page.length - 2].route : '',
      nowPath = page[page.length - 1].route;
    this.setData({
      previousPath: previousPath,
      nowPath: nowPath
    })
    // rzd 埋点 190214 end
  },
  getList() {
    API.getAllSupplyDemandInfo({
        curPage: this.data.curPage,
        mark: "MySupplydemandinfo",
        pageSize: this.data.pageSize,
        pushStatus: this.data.type
      })
      .then(res => {
        if (res.obj) {
          let arr = res.obj;
          arr.forEach((value, index) => {
            value.gmtModified = this.changeTiem(value.gmtModified);
          });
          this.setData({
            List: arr
          });
        } else {
          this.setData({
            List: [],
            emptyPageObj: {
                imgUrl: this.data.staticImageUrl+'issue.png',
              title: '暂未发布内容',
            }
          });
        }
      })
  },
  changeTiem(item) {
    let leadTime = new Date().getTime() - new Date(item.replace(/\-/g, '/')),
      date;
    if (leadTime / 1000 / 60 < 1) {
      date = '刚刚';
    }
    if (leadTime / 1000 / 60 > 1 && leadTime / 1000 / 60 < 60) {
      date = (leadTime / 1000 / 60).toFixed(0) + '分钟前';
    }
    if (leadTime / 1000 / 3600 > 1 && leadTime / 1000 / 3600 < 59) {
      date = (leadTime / 1000 / 3600).toFixed(0) + '小时前';
    }
    if (leadTime / 1000 / 3600 / 24 > 1) {
      date = '1天前';
    }
    return date;
  },
  lowerFrame: function(e) {
    $App.sellingPoint(API, 'mypublishlowerFrame', this.data.nowPath, this.data.previousPath, '我的发布');
    let that = this
    wx.showModal({
      title: '提示',
      content: this.data.type == 0 ? '确认删除么？' : '',
      success: function(sm) {
        if (sm.confirm) {
          API.getMeDeleteComment({
            supplyDemandId: e.currentTarget.dataset.id
          })
            .then(res => {
              that.getList()
            })
        }
      }
    })
  },
  editFun: function(e) {
    $App.sellingPoint(API, 'mypublisheditFun', this.data.nowPath, this.data.previousPath, '我的发布');
    let index = e.currentTarget.dataset.index
    let type = 'edit';
    let id = this.data.List[index].supplyDemandId;
    wx.setStorage({
      key: 'editItem',
      data: this.data.List[index],
    })
    wx.navigateTo({
      url: `/pages/publishMessage/publishMessage?id=${id}&type=${type}`
    })
    // wx.navigateTo({
    //   url: '/pages/decoration/editPublish/editPublish',
    // })
  },
  toPublish() {
    $App.sellingPoint(API, 'mypublishtoPublish', this.data.nowPath, this.data.previousPath, '我的发布');
    wx.navigateTo({
      url: '/pages/publishMessage/publishMessage',
    })
  },
  toDetail: function(e) {
    $App.sellingPoint(API, 'mypublishtoDetail', this.data.nowPath, this.data.previousPath, '我的发布');
    console.log(e.currentTarget.dataset.id);
    wx.setStorage({
      key: 'editItem',
      data: e.currentTarget.dataset.id
    })
    let id = e.currentTarget.dataset.id.supplyDemandId
    wx.navigateTo({
      url: '/pages/decoration/supplyDetail/supplyDetail?id=' + id + '&mold=发布',
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    console.log('显示');
    this.getList();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    let num = this.data.pageSize + 5;
    this.setData({
      pageSize: num
    })
    this.getList();
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {},
  // changeType(e) {
  //   let type = e.currentTarget.dataset.type;
  //   this.setData({
  //     type: type,
  //     status: type
  //   })
  //   this.getList()
  // }
})