import { resourcePath, defaultImg} from '../../../utils/config.js'
let API = getApp().API
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: resourcePath,
    companyId:'',
    teamList:[],
    defaultImg: defaultImg
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let id=options.companyId;
    this.setData({
      companyId:id
    })
    this.getList();
  },
  getList() {
    API.getCompanyDesignerList({ companyId: this.data.companyId, platformValue: 2 }).then(res => { // 增加平台标识
      if (res.code == 200) {
        this.setData({ teamList: res.data })
      } 
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
    
  },
  toDetail(e){  
    let id = e.currentTarget.dataset.id
    if (id == undefined || id == null) {
      wx.showToast({
        title: '该设计师未创建店铺',
        icon: 'none',
        duration: 500
      })
    } else {
      wx.navigateTo({
        url: '/pages/decoration/companyDetail/companyDetail?id=' + id,
      })
    }
  }
})