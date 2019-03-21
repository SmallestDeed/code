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
    staticImageUrl: getApp().staticImageUrl,
    isShow:false,
    shareImgSrc:'',
    userImg:'',
    goodsImg:'',
    code:''
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
    downImg(path,type){
      var that = this;
      wx.getImageInfo({
        src: path,//服务器返回的图片地址
        success: function (res) {
          //res.path是网络图片的本地地址
          console.log(res.path);
          if (type == 1) {
            that.data.userImg = res.path;
          } else if(type == 2){
            that.data.goodsImg = res.path;
          }else{
            that.data.code = res.path;
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
      that.downImg(shareParam.userImg,1);
      that.downImg(shareParam.goodsImg, 2);
      that.downImg(shareParam.code, 3);
    },
    setPoster() {//绘制图片
      console.log("aaaaaaaaa")
      var that = this;
      let shareParam = this.properties.shareParam;
        console.log(this.data.userImg, this.data.code, this.data.goodsImg);
      const ctx = wx.createCanvasContext('myCanvas');
      ctx.setFillStyle("#ffffff");
      ctx.fillRect(0, 0, 750, 1200);
      ctx.drawImage(that.data.userImg, 20, 20, 44, 44);//头像
      ctx.setFillStyle('#333333');
      ctx.setFontSize(16);
      ctx.fillText(shareParam.userName + '邀您参团', 80, 48);//用户名
      ctx.drawImage(that.data.goodsImg, 20, 80, 335, 335);//商品图片
      ctx.setFillStyle('#333333');
      ctx.setFontSize(16);
      ctx.fillText(shareParam.goodsName, 20, 448);//商品名称
      ctx.setFillStyle('#ff6419');
      ctx.setFontSize(22);
      ctx.fillText('￥' + shareParam.goodsPrice, 20, 488);//优惠价
      ctx.setFillStyle('#ff6419');
      ctx.setFontSize(12);
      ctx.fillText(shareParam.assembleNum + '人拼团价', 120, 485);//几人拼
      ctx.setFillStyle('#999999');
      ctx.setFontSize(12);
      ctx.fillText('原价￥' + shareParam.basePrice, 20, 520); //商品原价
      ctx.drawImage(that.data.code, 260, 470, 70, 70);//二维码
      ctx.setFillStyle('#999999');
      ctx.setFontSize(11);
      ctx.fillText('扫描或长按小程序码', 245, 560);
      ctx.draw();
      wx.canvasToTempFilePath({
        canvasId: 'myCanvas',
        x: 0,
        y: 0,
        width:750,
        height: 1200,
        destWidth: 750,
        destHeight: 1200,
        success: function (res) {
          console.log(res.tempFilePath)
          that.setData({
            shareImgSrc: res.tempFilePath
          })
          that.savePoster();
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
          that.close();
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
    }
  }
})
