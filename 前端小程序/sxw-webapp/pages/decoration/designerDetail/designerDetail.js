// pages/decoration/designerDetail/designerDetail.js

import { resourcePath } from '../../../utils/config.js'
let API = getApp().API
Page({
  /**
   * 页面的初始数据
   */
  data: {
    planCount: 10,
    imgURL: getApp().data.imgURL,
    modelRoomCount: 11,
    commentCount: 12,
    targetId: 102,
    currTab: 0,//默认是第一个
    resourcePath: resourcePath,
    pageNoList: {
      plan: 1,
      model: 1,
      success: 1,
      comment: 1
    },
    tabContList: {
      plan: [],
      model: [],
      success: [],
      comment: []
    },
    hasMore: {
      plan: true,
      model: true,
      success: true,
      comment: true
    },
    banners: [
      "http://open.rjhq.cn/xz/wx/images/xq_banner01.jpg",
      "http://open.rjhq.cn/xz/wx/images/xq_banner01.jpg",
      "http://open.rjhq.cn/xz/wx/images/xq_banner01.jpg"
    ],
    designer: {},
    planList: [
      {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计1",
        fav: "9000",
        agree: "5000",
        id: '1'
      },
      {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计2",
        fav: "10000",
        agree: "5000",
        id: '1'
      }, {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计3",
        fav: "11000",
        agree: "5000",
        id: '1'
      }, {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计4",
        fav: "9000",
        agree: "5000",
        id: '1'
      }
    ],
    modelList: [
      {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计1",
        fav: "9000",
        agree: "5000",
        id: '1'
      },
      {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计2",
        fav: "10000",
        agree: "5000",
        id: '1'
      }, {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计3",
        fav: "11000",
        agree: "5000",
        id: '1'
      }, {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计4",
        fav: "9000",
        agree: "5000",
        id: '1'
      }
    ],
    successList: [
      {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计1",
        fav: "9000",
        agree: "5000",
        id: '1'
      },
      {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计2",
        fav: "10000",
        agree: "5000",
        id: '1'
      }, {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计3",
        fav: "11000",
        agree: "5000",
        id: '1'
      }, {
        imgPath: "http://open.rjhq.cn/xz/wx/images/xq_img01.jpg",
        name: "奥园国际单身公寓设计4",
        fav: "9000",
        agree: "5000",
        id: '1'
      }
    ],
    commentList: [
      {
        userName: "一一",
        serviceScore: "5",
        address: "万科001号",
        servicer: "",
        userAvatar: "http://open.rjhq.cn/xz/wx/images/zxfw_logo01.png",
        comment: "挺不错",
        commentLabel: ["设计专业", "服务好", "效率高"],
        id: "1"
      },
      {
        userName: "二二",
        serviceScore: "3",
        address: "万科002号",
        servicer: "",
        userAvatar: "http://open.rjhq.cn/xz/wx/images/zxfw_logo01.png",
        comment: "挺不错",
        commentLabel: ["设计专业", "服务好", "效率高"],
        id: "1"
      },
      {
        userName: "三三",
        serviceScore: "4",
        address: "万科001号",
        servicer: "",
        userAvatar: "http://open.rjhq.cn/xz/wx/images/zxfw_logo01.png",
        comment: "挺不错",
        commentLabel: ["设计专业", "服务好", "效率高"],
        id: "1"
      },
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    var targetId = options.id || 13;
    this.setData({
      targetId: targetId
    })
    if (targetId == undefined) {
      wx.navigateBack({
        delta: 1
      })
      return;
    }
    this.getDetail();
    this.getComment();
    this.getCommentLabel();
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
  changeTab: function (e) {
    var nIndex = e.currentTarget.dataset.index;
    if (nIndex == undefined)
      return;
    this.setData({
      currTab: nIndex
    })
  },
  getDetail: function () {
    API.getCompanyDetails({ shopId: this.data.targetId, platformValue: 2 })
      .then(res => {
        if (res.code == 200) {
          if (!res.data) return;
          this.setData({ designer: res.data })
        } else {
          wx.showToast({ title: res.message || "网络错误 请稍后再试", icon: 'none' })
        }
      })
  },
  getCommentLabel: function () {
    API.getSysdictionaryList()
      .then(res => {
        if (res.code == 200) {
        } else {
          if (res.message) {
            wx.showToast({ title: res.message, icon: 'none' })
          }
        }
      })
  },
  getPlan: function () {
    let tabCont = this.data.tabContList.plan
    if (pageNo == 1) tabCont = [];    
    API.getInteractioncomment({
      pageNo: this.data.pageNoList.plan,
      pageSize: 10,
      type: 1,//1:设计师  2:设计方案 3:供求信息 4:企业 5:店铺
      objId: this.data.targetId
    })
      .then(res => {
        if (res.code == 200) {
          if (!res.data) return;
          tabCont = tabCont.concat(res.data.list)
          this.setData({ ["tabContList.plan"]: tabCont, ["pageNoList.plan"]: ++pageNo })
          if (tabCont.length == 0 || res.data.list.length < 10) {
            this.setData({ ["hasMore.plan"]: false })
          }

        } else {
          wx.showToast({ title: res.message || "网络错误 请稍后再试", icon: 'none' })
        }
      })
  },
  getModel: function () {
    let tabCont = this.data.tabContList.plan
    if (pageNo == 1) tabCont = [];
    API.getInteractioncomment({
      pageNo: this.data.pageNoList.model,
      pageSize: 10,
      type: 1,//1:设计师  2:设计方案 3:供求信息 4:企业 5:店铺
      objId: this.data.targetId
    })
      .then(res => {
        if (res.code == 200) {
          if (!res.data) return;
          tabCont = tabCont.concat(res.data.list)
          this.setData({ ["tabContList.model"]: tabCont, ["pageNoList.model"]: ++pageNo })
          if (tabCont.length == 0 || res.data.list.length < 10) {
            this.setData({ ["hasMore.model"]: false })
          }
        } else {
          wx.showToast({
            title: res.message || "网络错误 请稍后再试",
            icon: 'none'
          })
        }
      })
  },
  getSuccess: function () {
    var tabCont = this.data.tabContList.success;
    if (pageNo == 1) tabCont = [];
    API.getInteractioncomment({
      pageNo: this.data.pageNoList.success,
      pageSize: 10,
      type: 1,//1:设计师  2:设计方案 3:供求信息 4:企业 5:店铺
      objId: this.data.targetId
    })
    .then(res => {
      if (res.code == 200) {
        if (!res.data) return;
        tabCont = tabCont.concat(res.data.list)
        this.setData({ ["tabContList.success"]: tabCont, ["pageNoList.success"]: ++pageNo })
        if (tabCont.length == 0 || res.data.list.length < 10) {
          this.setData({ ["hasMore.success"]: false })
        }
      } else {
        wx.showToast({
          title: res.message || "网络错误 请稍后再试",
          icon: 'none'
        })
      }
    })
  },
  getComment: function () {
    var tabCont = this.data.tabContList.success;
    if (pageNo == 1) tabCont = [];
    API.getInteractioncomment({
      pageNo: this.data.pageNoList.comment,
      pageSize: 10,
      type: 1,//1:设计师  2:设计方案 3:供求信息 4:企业 5:店铺
      objId: targetId
    })
    .then(res => {
      if (res.code == 200) {
        if (!res.data) return;
        tabCont = tabCont.concat(res.data.list)
        this.setData({ commentList: tabCont, ["pageNoList.comment"]: ++pageNo })
        if (tabCont.length == 0 || res.data.list.length < 10) {
          this.setData({ ["hasMore.comment"]: false })
        }
      } else {
        wx.showToast({
          title: res.message || "网络错误 请稍后再试",
          icon: 'none'
        })
      }
    })
  },
  reloadTabList: function () {
    var currTab = this.data.currTab;
    if (currTab == 0 && this.hasMore.plan == true) {
      this.getPlan();
    } else if (currTab == 1 && this.hasMore.model == true) {
      this.getMoel();
    } else if (currTab == 2 && this.hasMore.success == true) {
      this.getSuccess();
    } else if (currTab == 3 && this.hasMore.comment == true) {
      this.getComment();
    }
  },
  toBack: function () {
    console.log("退")
    wx.navigateBack({
      delta: 1
    })
  },
})