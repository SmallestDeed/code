let API = getApp().API;
import { appid, resourcePath } from '../../utils/config.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabList:[
      { type:1, text:'我发起的团',active:true },
      { type:2, text: '我参与的团', active: false },
    ],
    assembleData:'',
    isMaster:1, //我发起的
    page:1,
    code:'',
    staticImageUrl: getApp().staticImageUrl,
    resourcePath: getApp().resourcePath,
    currentIndex:'',
    shareParam:{ //分享参数
      userImg:'',
      userName:'',
      goodsImg:'',
      goodsName:'',
      goodsPrice:'',
      basePrice:'',
      assembleNum:'',
      code:''
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
   
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.assembleList();
    //获得createPoster,emptyPage组件
    this.createPoster = this.selectComponent("#createPoster");
    this.emptyPage = this.selectComponent("#emptyPage");
    this.shareWay = this.selectComponent("#shareWay");
  },
  assembleList() {
    API.getAssembleList({ isMaster: this.data.isMaster, limit: 10, page: this.data.page, purchaseOpenId: '', userId: wx.getStorageSync("userId") }).then(res => {
       if(res.success){
         res.datalist.length == 0 && this.data.page==1 ? this.emptyPage.toggleDialog(true) : this.emptyPage.toggleDialog(false);
         this.data.page == 1 ? '' : res.datalist = this.data.assembleData.concat(res.datalist)
          res.datalist.map(item => {
             item.innerUserInfoList.map(listItem =>{
              if (listItem.userId == -1) {
                listItem.picPath = `${this.data.resourcePath}/AA/group_purchase/roberPic/rober_0${listItem.picId+1}.png`
                listItem.mark = '我是机器人'
              }
              return listItem
              })
            })
          console.log(res.datalist)
         this.setData({
           assembleData: res.datalist
         })
       }
    })
  },
  toggleTab(e) {
    console.log(6666)
    console.log(e)
    this.data.tabList.map(res => {
      res.active = false;
    })
    this.data.tabList[e.target.dataset.num].active=true;
    e.target.dataset.num == 0 ? this.data.isMaster = 1 : this.data.isMaster=0
    this.setData({
      tabList: this.data.tabList,
      isMaster: this.data.isMaster,
      page:1
    })
    this.assembleList();
  },
  showPoster(){
    this.shareWay.toggleDialog(false);
    this.createPoster.toggleDialog(true);
  },
  inviteFriend(e){
    this.shareWay.toggleDialog(true);
    this.getShareParam(e)//获取分享参数
  },
  getShareParam(e){
     console.log(e)
     let arr=this.data.assembleData[e.currentTarget.dataset.listnum].innerUserInfoList.filter(res => res.userId == wx.getStorageSync('userId'));
     let obj={
       userImg: arr[0].picPath,
       userName: arr[0].userName,
       goodsImg: this.data.resourcePath+this.data.assembleData[e.currentTarget.dataset.listnum].picPath,
       goodsName: this.data.assembleData[e.currentTarget.dataset.listnum].spuName,
       goodsPrice: this.data.assembleData[e.currentTarget.dataset.listnum].activityPrice,
       basePrice: this.data.assembleData[e.currentTarget.dataset.listnum].basePrice,
       assembleNum: this.data.assembleData[e.currentTarget.dataset.listnum].totalNumber,
       code: `${getApp().basePath.systemUrl}/v1/group/purchase/mini/code?userId=${arr[0].userId}&url=pages/index/index?id=${this.data.assembleData[e.currentTarget.dataset.listnum].spuId}&purchaseOpenId=${this.data.assembleData[e.currentTarget.dataset.listnum].purchaseOpenId}&groupId=${this.data.assembleData[e.currentTarget.dataset.listnum].activityId}&isAssemble=true`
     }
      console.log(`${getApp().basePath.systemUrl}/v1/group/purchase/mini/code?userId=${arr[0].userId}&url=pages/index/index?id=${this.data.assembleData[e.currentTarget.dataset.listnum].spuId}&purchaseOpenId=${this.data.assembleData[e.currentTarget.dataset.listnum].purchaseOpenId}&groupId=${this.data.assembleData[e.currentTarget.dataset.listnum].activityId}&isAssemble=true`)
     this.setData({
       shareParam: obj,
       currentIndex: e.currentTarget.dataset.listnum
     })
  },
  toAssembleDetail(e){
    let that=this;
    wx.navigateTo({
      url: `/pages/assemble-detail/assemble-detail?id=${that.data.assembleData[e.currentTarget.dataset.num].purchaseOpenId}&activityId=${that.data.assembleData[e.currentTarget.dataset.num].activityId}&isAssemble=true`,
    })
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
    this.data.page++;
    this.setData({
      page:this.data.page
    })
    this.assembleList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      title: `【来跟我们一起拼团吧】${this.data.shareParam.goodsName}`,
      path: `/pages/index/index?id=${this.data.assembleData[this.data.currentIndex].spuId}&purchaseOpenId=${this.data.assembleData[this.data.currentIndex].purchaseOpenId}&groupId=${this.data.assembleData[this.data.currentIndex].activityId}&isAssemble=true`,
      imageUrl: this.data.shareParam.goodsImg,
      success: function (res) {
        // 转发成功
        console.log(res)
      },
      fail: function (res) {
        // 转发失败
      }
    }

  }

})