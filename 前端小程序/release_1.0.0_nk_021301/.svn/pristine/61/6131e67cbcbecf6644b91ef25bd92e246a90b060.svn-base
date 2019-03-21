// pages/test/test.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    resourcePath: getApp().resourcePath,
    shareParam:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // var that = this;
    // const ctx = wx.createCanvasContext('myCanvas');
    // ctx.setFillStyle("#f7f7f7");
    // ctx.fillRect(0, 0, 622, 898);
    // ctx.drawImage("http://tmp/wx0d37f598e1028825.o6zAJs7aBhsLL8JTKipILrBv_46c.LzvZ40LKfcGT51e2556ccb118003f8e2f8b5b3d48e19.png", 200, 40, 60, 60)
    // ctx.globalCompositeOperation = 'destination-in'
    // ctx.fillStyle = '#fff'
    // ctx.arc(230, 70, 30, 0, 2 * Math.PI)
    // ctx.fill()
    // ctx.globalCompositeOperation = 'lighter'
    
    // ctx.drawImage("http://tmp/wx0d37f598e1028825.o6zAJs7aBhsLL8JTKipILrBv_46c.drCiosfgI5JRbe6cf68c68aa03f5cd97ab9036133b0b.png", 17, 17, 280, 150);//背景
    // ctx.setFillStyle('#ffffff');
    // ctx.setFontSize(12);
    // ctx.fillText("1212312131321", 36, 36);//公司名
    // ctx.setFillStyle('#ffffff');
    // ctx.setFontSize(18);
    // ctx.fillText("asd", 36, 70);//姓名
    // ctx.setFillStyle('#ffffff');
    // ctx.setFontSize(10);
    // ctx.fillText("working", 36, 88);//职位
    // ctx.setFillStyle('#cccccc');
    // ctx.setFontSize(12);
    // ctx.fillText("13112345678", 36, 125);//电话
    // ctx.setFillStyle('#cccccc');
    // ctx.setFontSize(12);
    // ctx.fillText("123456789@qq.com", 36, 140);//邮箱
    // ctx.setFillStyle('#cccccc');
    // ctx.setFontSize(12);
    // ctx.fillText("akjsdhuncjasdhiwd", 36, 155);//地址
    // ctx.drawImage("http://tmp/wx0d37f598e1028825.o6zAJs7aBhsLL8JTKipILrBv_46c.EbZHcGMpSIHTf0039fa6e87b8cf7be3c553f0b90de0c.png", 98, 200, 125, 125);//背景
    // ctx.setFontSize(12);
    // ctx.fillText("长按识别二维码,收下名片", 90, 355);//提示
    // ctx.setFillStyle('#999999');
    // ctx.draw();


    this.setData({
      shareParam:wx.getStorageSync('vistingCard')
    })
    let sp = this.data.shareParam;
    this.downImg(sp.backImg, 1);
    this.downImg(this.data.resourcePath + sp.headPic, 2);
    this.downImg(sp.QRCode, 3);
    
  },
  setPoster(){
    let code;
    wx.downloadFile({
      url: this.data.shareParam.QRCode,
      success:function(res){
        code: res.tempFilePath
      }
    })
    console.log(this.data.shareParam.QRCode,'123')
    var that = this;
    let sp=this.data.shareParam
    let headPic = this.data.resourcePath + sp.headPic;
    const ctx = wx.createCanvasContext('myCanvas');
    // ctx.setFillStyle("#ffffff");
    // ctx.fillRect(0, 0, 622, 898);
    //圆角头像
    // ctx.drawImage(headPic, 200, 40, 60, 60)
    // ctx.globalCompositeOperation = 'destination-in'
    // ctx.fillStyle = '#fff'
    // ctx.arc(230, 70, 30, 0, 2 * Math.PI)
    // ctx.fill()
    // ctx.globalCompositeOperation = 'lighter'
    // ctx.drawImage(sp.backImg, 17, 17, 280, 150);//背景
    
   
    // ctx.setFillStyle('#ffffff');
    // ctx.setFontSize(12);
    // ctx.fillText(sp.company, 36, 36);//公司名
    // ctx.setFillStyle('#ffffff');
    // ctx.setFontSize(18);
    // ctx.fillText(sp.name, 36, 70);//姓名
    // ctx.setFillStyle('#ffffff');
    // ctx.setFontSize(10);
    // ctx.fillText(sp.job, 36, 88);//职位
    // ctx.setFillStyle('#cccccc');
    // ctx.setFontSize(12);
    // ctx.fillText(sp.phone, 36, 125);//电话
    // ctx.setFillStyle('#cccccc');
    // ctx.setFontSize(12);
    // ctx.fillText(sp.email, 36, 140);//邮箱
    // ctx.setFillStyle('#cccccc');
    // ctx.setFontSize(12);
    // ctx.fillText(sp.address, 36, 155);//地址
    // ctx.globalCompositeOperation = 'lighter'
    ctx.drawImage(code, 98, 200, 125, 125);//背景
    // ctx.setFontSize(12);
    // ctx.fillText("长按识别二维码,收下名片", 90, 355);//提示
    // ctx.setFillStyle('#999999');
    ctx.draw();
    wx.canvasToTempFilePath({
      canvasId: 'myCanvas',
      x: 0,
      y: 0,
      width: 622,
      height: 898,
      destWidth: 622,
      destHeight: 898,
      fileType: 'jpg',
      success: function (res) {

        console.log(res.tempFilePath, '99999')
        that.setData({
          shareImgSrc: res.tempFilePath
        })
        that.savePoster();
      },
      complete: function (res) {
        console.log(res, 'com')
      },
      fail: function (res) {
        console.log(res, 'fail')
      }
    })
  },
  downImg(path, type) {
    var that = this;

    wx.getImageInfo({
      src: path,//服务器返回的图片地址
      success: function (res) {
        //res.path是网络图片的本地地址
        console.log(res.path);
        if (type == 1) {
          that.data.backImg = res.path;
          console.log(res.path, 'back')
        } else if (type == 2) {
          that.data.headPic = res.path;
          console.log(res.path, 'head')
        } else {
          that.data.QRCode = res.path;
          console.log(res.path, 'qr')
          that.setPoster();
        }
      },
      fail: function (res) {
        //失败回调
      }
    })
  },
  savePoster() { //保存图片
    //保存到相册
    console.log("987978")
    var that = this;
    wx.saveImageToPhotosAlbum({
      filePath: that.data.shareImgSrc,
      success(res) {
        wx.hideLoading();
        // that.close();
        wx.showToast({
          title: '成功',
          icon: 'success',
          duration: 2000
        })
      },
      fail: function (res) {
        console.log(res)
        wx.hideLoading();
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

  }
})