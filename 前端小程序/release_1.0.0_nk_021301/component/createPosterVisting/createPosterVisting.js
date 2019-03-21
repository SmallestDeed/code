// component/create-poster/create-poster.js

Component({
  /**
   * 组件的属性列表
   */
  properties: {
      shareParam:{
        type: Object,
        value:''
      }
      // 对象中需传的值
    // userImg: {
    //   type: String,
    //   value: '用户头像'
    // },
    // userName: {
    //   type: String,
    //   value: '用户昵称'
    // },
    // goodsImg: {
    //   type: String,
    //   value: '商品图片'
    // },
    // goodsName: {
    //   type: String,
    //   value: '商品名称'
    // },
    // goodsPrice: {
    //   type: Number,
    //   value: '商品价格'
    // },
    // basePrice: {
    //   type: Number,
    //   value: '商品原价'
    // },
    // assembleNum: {
    //   type: Number,
    //   value: "拼团人数"
    // },
    // code: {
    //   type: String,
    //   value: '二维码'
    // }
  },
  
  /**
   * 组件的初始数据
   */
  data: {
    isShow:false,
    shareImgSrc:'',
    userImg:'',
    goodsImg:'',
    code:'',
    resourcePath: getApp().resourcePath,
    backImg:'',
    headPic:'',
    QRCode:''
  },
  ready(){
  },
  /**
   * 组件的方法列表
   */
  methods: {
    close(){
      this.setData({
        isShow: false
      })
    },
    toggleDialog(type) { //弹窗显示隐藏
      this.setData({
        isShow: type
      })
    },
    saveImg() { //绘制图片
      //获取相册授权
      var that = this;
      wx.getSetting({
        success(res) {
          if (!res.authSetting['scope.writePhotosAlbum']) {
            wx.authorize({
              scope: 'scope.writePhotosAlbum',
              success() {
                console.log('授权成功');
                that.getPoster();
              }
            })
          }else{
            wx.showLoading({
              title: '加载中',
            })
            that.getPoster();
          }
        }
      })
      

    },
    downImg(path, type) {
      var that = this;
      wx.getImageInfo({
        src: path,//服务器返回的图片地址
        success: function (res) {
          //res.path是网络图片的本地地址
          if (type == 1) {
            console.log(res,'res')
            that.data.backImg = res.path;
          } else if (type == 2) {
            that.data.headPic = res.path;
          } else {
            that.data.QRCode = res.path;
            that.setPoster();
          }
        },
        fail: function (res) {
          //失败回调
        }
      })
    },
    getPoster(){ //下载图片到本地

      var that = this;
      let shareParam=this.properties.shareParam;
      that.downImg(shareParam.backImg,1);
      that.downImg(this.data.resourcePath+shareParam.headPic, 2);
      that.downImg(shareParam.QRCode, 3);

    },
    setPoster() {
      
      var that = this;
      let sp = that.data.shareParam
      const ctx = wx.createCanvasContext('myCanvas');
      ctx.setFillStyle("#ffffff");
      ctx.fillRect(0, 0, 350, 350);
      ctx.drawImage(that.data.backImg, 20, 20, 300, 150);//背景
      ctx.save()
      ctx.beginPath()
      ctx.arc(255, 85, 40, 0, 2 * Math.PI)
      ctx.stroke()
      ctx.clip()
      ctx.drawImage(that.data.headPic, 215, 45, 80, 80);
      ctx.restore()
      ctx.setFillStyle('#ffffff');
      ctx.setFontSize(12);
      ctx.fillText(sp.company, 36, 45);//公司名
      ctx.setFillStyle('#ffffff');
      ctx.setFontSize(20);
      ctx.fillText(sp.name, 36,80);//姓名
      ctx.setFillStyle('#ffffff');
      ctx.setFontSize(10);
      ctx.fillText(sp.job, 36, 95);//职位
      ctx.setFillStyle('#cccccc');
      ctx.setFontSize(10);
      ctx.fillText(sp.phone, 36,130);//电话
      ctx.setFillStyle('#cccccc');
      ctx.setFontSize(10);
      ctx.fillText(sp.email, 36,145);//邮箱
      ctx.setFillStyle('#cccccc');
      ctx.setFontSize(10);
      ctx.fillText(sp.address, 36,160);//地址
      ctx.drawImage(that.data.QRCode, 120, 200, 100, 100);//背景
      ctx.setFontSize(12);
      ctx.fillText("长按识别二维码,收下名片", 105, 330);//提示
      ctx.setFillStyle('#999999');
      ctx.draw();
      console.log("http://tmp/wx0d37f598e1028825.o6zAJs7aBhsLL8JTKipILrBv_46c.Me3NTS6hDOrx847d6e065a04cd515582ada5b6892849.png")
      wx.canvasToTempFilePath({
        canvasId: 'myCanvas',
        x: 0,
        y: 0,
        width: 340,
        height: 350,
        destWidth: 1020,
        destHeight: 1050,
        fileType: 'jpg',
        quality:1,
        success: function (res) {
          console.log(res,'123')
          that.setData({
            shareImgSrc: res.tempFilePath
          })
          that.savePoster();
        },
        
        complete: function (res) {
        },
        fail: function (res) {
        }
      })
    },
    savePoster() { //保存图片
      //保存到相册
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
          wx.hideLoading();
        }
      })
    },
  }
})
