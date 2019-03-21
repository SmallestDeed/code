// pages/evaluate/evaluate.js
let myForEach = getApp().myForEach,
  API = getApp().API,
  socket = null
let $App = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: $App.staticImageUrl,
    resourcePath: $App.resourcePath,
    curData:{},
    // 用于数据展示
    orderProductList: [],
    // 用于接口调用传参
    mark:[],
    // 评价成功弹框
    esShow: false,
    // 设置不可重复评价
    haveSubmint: false,
    // 用于屏蔽输入框BUG
    textShow:true,
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (opt) {
    let orderProductList = JSON.parse(opt.item).orderProductList;
    orderProductList.map((item, index)=> {
      item.descLevel=-1;
      item.isShowName = 0;
      item.commentnum = 500;
      item.comment = '';
      item.pics = [];
      item.fen = 5;
    })
    this.setData({
      orderProductList: orderProductList,
      curData: JSON.parse(opt.item)
    })
    console.log(this.data.curData)
    this.resetMark();
  },
  // 关闭评价成功弹框
  closeES() {
    this.setData({
      esShow: false,
      textShow: true
    })
    let deltaNum = this.data.curData.fromView == 'detail' ? 2 : 1;
    console.log(deltaNum+'-------------------')
    wx.navigateBack({
      delta: deltaNum
    })
  },
  // 回到首页
  goHome() {
    this.setData({
      esShow: false
    })
    wx.switchTab({
      url: '/pages/index/index',
    })
  },
  // 提交评价
  submintEval(){
    let bool = true;
      this.data.mark.map(item => {
        console.log(item.picIds.length);
        if (typeof item.picIds == 'object'&& item.picIds.length != 0) {
          item.picIds = item.picIds.join();
        } else {
          item.picIds = null;
        }
        if(item.descLevel==0||item.comment=='') {
          bool = false
        }

      })
      if(bool) {
        API.addEval(JSON.stringify(this.data.mark)).then(res => {
          if (res.status) {
            this.setData({
              esShow: true,
              haveSubmint: true,
              textShow: false
            })
            this.resetMark();
          } else {
            wx.showToast({
              title: res.message,
              icon: 'none'
            })
          }
        })
      } else {
        this.data.mark.map(item=>{
          if(!item.picIds) {
            item.picIds = []
          }
        })
        wx.showToast({
          title: '评分不能为0，评论不能为空',
          icon: 'none'
        })
      }
  },
  // 初始化mark 
  resetMark(){
    this.data.mark = [];
    this.data.orderProductList.map((item, index) => {
      let obj = {
        "orderId": this.data.curData.id,
        "userId": wx.getStorageSync('userId'),
        "productId": item.productId,
        "descLevel": 0,
        "comment": "",
        "picIds": [],
        "type": 0,
        "isShowName": 0
      }
      this.data.mark.push(obj);
    })
  },
  // 添加图片
  addImg(e) {
    let index = e.currentTarget.dataset.index, _this = this;
    if (_this.data.orderProductList[index].pics.length <= 5) {
      wx.chooseImage({
        count: 5 - _this.data.orderProductList[index].pics.length,
        success: function (res) {
          myForEach(res.tempFilePaths, (v) => {
            API.uploadEvaluate({
              'path': v,
              'type': 'image',
              'filekey': 'message.comment',
              'platform': 'brand2c'
            }).then(res => {
              let orderProductList = _this.data.orderProductList;
              let img = res.obj.url;
              let id = res.obj.resId;

              orderProductList[index].pics.push(img);
              _this.data.mark[index].picIds.push(id);
              _this.setData({
                orderProductList: orderProductList
              })
            })
          })
        },
      })
    } else {
      wx.showToast({
        title: '最多上传5张!!!',
        icon: 'none'
      })
    }
  },
  // 删除图片
  delImg(e) {
    let index = e.currentTarget.dataset.index;
    let pindex = e.currentTarget.dataset.pindex;
    this.setMark(index, 'delimg', pindex, '')
  },
  // 评论输入
  textInpu(e) {
    let index = e.currentTarget.dataset.index;
    this.setMark(index, 'comment', e.detail.value, '')
  },
  // 设置匿名
  setShowName(e) {
    let index = e.currentTarget.dataset.index, isShowName = e.currentTarget.dataset.ishowname;
    this.setMark(index, 'showName', isShowName==0?1:0, '')
  },
  // 设置评分
  setDescLevel(e) {
    let findex = e.currentTarget.dataset.findex, index = e.currentTarget.dataset.index;
    this.setMark(index, 'descLevel', findex, '')
  },
  // 设置数据 index:当前选择的是哪一个item, type:进行的是什么操作, parma1:用于数据改变的主要参数, parma2:用于数据改变的次要参数
  setMark(index, type, parma1, parma2) {
    let orderProductList = this.data.orderProductList, mark = this.data.mark;
    for (let i = 0; i < orderProductList.length; i++) {
      if (type =='comment') {
        if (i == index) {
          orderProductList[i].commentnum = 500 - parma1.length;
          orderProductList[i].comment = parma1;
          mark[i].comment = parma1;
        }
      }else if(type == 'showName') {
        if (i == index) {
          orderProductList[i].isShowName = parma1;
          mark[i].isShowName = parma1;
        }
      } else if (type == 'descLevel'){
        if (i == index) {
          orderProductList[i].descLevel = parma1;
          mark[i].descLevel = parma1 + 1;
        }
      }else if (type == 'delimg'){
        if (i == index) {
          orderProductList[i].pics.splice(parma1, 1);
          mark[i].picIds.splice(parma1, 1);
        }
      }
    }
    this.setData({
      orderProductList: orderProductList,
      mark: mark
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