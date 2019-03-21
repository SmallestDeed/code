// pages/mine/my-favorite-house/my-favorite-house.js
let API = getApp().API
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    emptyPageObj: {},
    houseList: [],
    limit: 10,
    totalCount: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.getHouselist();
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

  /*请求户型列表*/
  getHouselist() {
    API.collectHouselist({
      start: 0,
      limit: this.data.limit
    }).then(res => {
      if (res.obj) {
        this.setData({
          houseList: res.obj,
          totalCount: res.totalCount
        });
      } else {
        this.setData({
          emptyPageObj: {
            imgUrl: this.data.staticImageUrl+'huxing_bg_no.png',
            title: '您没有收藏户型哦',
            // btnText: '去逛一逛',
            // url: '/pages/brandHall/bHIndex/bHIndex',
            // switchTab: true
          }
        });
      }
    });
  },

  /*查看大图*/
  lookImg(e) {
    let item = e.currentTarget.dataset.item;
    let imgList = [item.thumbnailPath ? this.data.resourcePath+item.thumbnailPath : this.data.staticImageUrl + 'huxing_pic_no.png'];
    wx.previewImage({
      urls: imgList
    });
  },
  /*一键装修*/
  goFinish(e) {
    let item = e.currentTarget.dataset.item;
    this.setData({
      houseId: item.id ? item.id : ''
    })
    wx.navigateTo({
      url: '/pages/house-type/house-details/house-details?houseId=' + item.id + '&type=search',
    })
  },
  /*收藏*/
  goCollect(e) {
    let i = e.currentTarget.dataset.index;
    let item = this.data.houseList;
    let status;
    if (item[i].isFavorite == 0) {
      status = 1;
    } else {
      status = 0;
    }
    API.getSearchHouseCollect({
      contentId: item[i].id,
      nodeType: 3,
      detailType: 1,
      status: status
    }).then(res => {
      console.log(res);
      if (res.success) {
        item.forEach((value, index) => {
          if (i == index) {
            if (status == 1) {
              value.favoriteNum++;
              value.isFavorite = 1;
            } else {
              value.favoriteNum--;
              value.isFavorite = 0;
            }
          }
        })
        this.setData({ houseList: item });
        if (status == 1) {
          wx.showToast({ title: '收藏成功', icon: 'success' });
        } else {
          wx.showToast({ title: '取消成功', icon: 'success' });
        }
      } else {
        if (status == 1) {
          wx.showToast({ title: '收藏失败', icon: 'none' });
        } else {
          wx.showToast({ title: '取消失败', icon: 'none' });
        }
      }
    });
  },

  /*发布信息*/
  goPublish(e) {
    let id = e.currentTarget.dataset.item.id ? e.currentTarget.dataset.item.id : 1;
    let imgUrl = e.currentTarget.dataset.item.thumbnailPath ? e.currentTarget.dataset.item.thumbnailPath : this.data.staticImageUrl + 'huxing_pic_no.png';
    let type = 'house' // house:户型， plan:方案，
    wx.navigateTo({
      url: `/pages/publishMessage/publishMessage?id=${id}&imgUrl=${imgUrl}&type=${type}`
    })
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
    if (this.data.totalCount > this.data.limit) {
      this.data.limit += 10;
      this.getHouselist();
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    let item = res.target.dataset.item;
    let imgUrl = item.thumbnailPath ? this.data.resourcePath + item.thumbnailPath : this.data.staticImageUrl + 'huxing_pic_no.png';
    console.log(imgUrl)
    let shareObj = {
      title: item.houseName,
      // path: '/pages/home/home?shareType=houseDetail&houseId=' + item.id,
      path: '/pages/home/home?shareType=active&url=' + encodeURIComponent(`/pages/house-type/house-details/house-details?houseId=${item.id}&type=search`),
      imageUrl: imgUrl,
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }

    }
    return shareObj;
  }
})