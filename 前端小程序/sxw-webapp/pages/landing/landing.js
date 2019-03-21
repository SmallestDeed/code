let API = getApp().API,
  myForEach = getApp().myForEach,
  $App = getApp();
Page({
  data: {
    webUrl: '',
    pageType: '',
    title: '',
    shareUrl: '',
    resourcePath: getApp().resourcePath,
  },
  onLoad: function(options) {
    this.init(options)
  },
  init(options) {
    // wx.setNavigationBarTitle({
    //     title: wx.getStorageSync('pageTitle')
    // })
    let url = decodeURIComponent(options.url);
    let info = url.indexOf('?') == -1 ? '?' : '&';
    info += 'token=' + wx.getStorageSync('token') + '&userId=' + wx.getStorageSync('id')
    if (options.info) {
      info = info + '&id=' + options.id + '&type=' + options.type
    }
    let shareUrl = '/pages/landing/landing?url=' + url;
    if (shareUrl.indexOf('festivalActivity')) shareUrl = shareUrl.replace(`festivalActivity`, 'inviteFriend'); // 因元宵活动banner详情页和分享页不是同一个页面导致要这样做
    this.shareUrl = shareUrl;
    this.setData({
      webUrl: url + info,
    })
    console.log(this.data.webUrl, 'SWQ')
    this.getActShareImg(options);
  },
  getActShareImg(options) {
    API.getIndexBanner({
      basePlatformId: 16,
      positionCode: 'xz:home:banner:interior'
    }, 'noLoading').then(res => {
      if (res.datalist) {
        let banner = res.datalist.filter(e => {
          return e.skipPath.indexOf(options.url) != -1
        });
        API.getActShareImg({
          positionCode: 'xz:home:banner:interior',
          bannerId: banner[0].bannerId
        }, 'noLoading').then(res => {
          this.setData({
            shareData: res.obj
          })
        })
      }
    })
  },
  onShow: function() {},
  onShareAppMessage: function(res) {
    if (res.from === 'menu' && this.data.shareData.length != 0) {
      let url = encodeURIComponent(this.shareUrl);
      let shareObj = {
        title: this.data.shareData[0].shareText,
        path: '/pages/home/home?shareType=active&url=' + url + '&name=' + wx.getStorageSync('pageTitle'),
        imageUrl: this.data.resourcePath + this.data.shareData[0].picPath,
        success: function(res) {
          // 转发成功
        },
        fail: function(res) {
          // 转发失败
        }
      }
      return shareObj;
    }
  }
})