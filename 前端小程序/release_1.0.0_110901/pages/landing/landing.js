let API = getApp().API,
    myForEach = getApp().myForEach,
    $App = getApp();
Page({
    data: {
      webUrl: '',
      pageType:'',
      title:'',
      shareUrl:''
    },
    onLoad: function(options) {
      this.init(options)
    },
    init(options) {
      wx.setNavigationBarTitle({ title: wx.getStorageSync('pageTitle') }) 
      let info = '?token=' + wx.getStorageSync('token') + '&userId=' + wx.getStorageSync('id') + '&id=' + wx.getStorageSync('id');
      let sharUrl = '/pages/landing/landing?url=' + options.url
      this.setData({
        webUrl: options.url + info,
        shareUrl: sharUrl
      })
      // if (options.token) {
      //   wx.setNavigationBarTitle({ title: options.type =='personalPublicity' ? '红人新势力' : '刘柳个人专题'})
      //   this.setData({ webUrl: `?token=${options.token}&userId=${wx.getStorageSync('id')}` })
      //   console.log(this.data.webUrl)
      // } else if (options.userId){     
      //   this.setData({ webUrl: `${getApp().squeezePageUrl}/landingpage/mobildecoration.html?userId=${options.userId}&id=${options.id || options.iid || ''}&type=${options.type ? (options.type == 1 ? 0 : 1) : ''}` })
      //   wx.setNavigationBarTitle({ title: options.token ? '活动详情' : '免费报价' })        
      // }
    },
    onShow: function() {

    },
    onShareAppMessage: function(res) {
        if (res.from === 'menu') {
            
       console.log("123123")
      let title;
      if (this.data.pageType =='personalPublicity') {
        title = '红人新势力'
      } else if (this.data.pageType == 'onlineSensation'){
        title ='红人新势力'
      } else if (this.data.pageType == 'free'){
        title='免费报价'
      } 
          let url = encodeURIComponent(this.data.shareUrl)
      let shareObj = {
        title: title,
        path: '/pages/home/home?shareType=active&url=' + url + '&name=' + wx.getStorageSync('pageTitle'),
        success: function (res) {
          // 转发成功
        },
        fail: function (res) {
          // 转发失败
        }
      } 
      console.log(shareObj,'123132')
      return shareObj;
        }
      
    } 
})
  