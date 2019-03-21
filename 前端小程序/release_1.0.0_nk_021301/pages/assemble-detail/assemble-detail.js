let API = getApp().API
import {
    TimeStyle
} from '../../lib/data/data'
Page({
    data: {
        staticImageUrl: getApp().staticImageUrl,
        hiddenList:[],
        isHidden:false,
        isShare:true,//外面扫码进来的页面
        timer: '',
        timeValue: '',
        assembleDayNum: '',
        assembleHourNum: '',
        assembleMinNum: '',
        assembleSecNum: '',
        resourcePath: getApp().resourcePath,
        isDialog:false,
        assembleDetail:[],
        friendList:[],
        shareParam: { //分享参数
          userImg: '',
          userName: '',
          goodsImg: '',
          goodsName: '',
          goodsPrice: '',
          basePrice: '',
          assembleNum: '',
          code: ''
        }
    },
    onLoad: function(options) {
        //Do some initialize when page load.
        this.getAssembleDetail(options.id);
        this.TimeCacl() //拼团倒计时方法
    },
    onReady:function(e){
      this.createPoster = this.selectComponent("#createPoster");
      this.shareWay = this.selectComponent("#shareWay");
    },
    onUnload: function () {
        clearInterval(this.data.timer)
    },
    getAssembleDetail(id){
        API.getAssembleDetail({activeId:id}).then(res=>{
            if(res.success&&res.obj){
             let testtimeValue = ''
            if (res.obj.startTime < 0) {
                testtimeValue = res.obj.expireTime / 1000
            } else {
                testtimeValue = res.obj.startTime / 1000
            }
            let arr = res.obj.innerUserInfoList.filter(res => res.userId == wx.getStorageSync('userId'));
            let obj={
              userImg: arr[0].picPath,
              userName: arr[0].userName,
              goodsImg: this.data.resourcePath+res.obj.picPath,
              goodsName: res.obj.spuName,
              goodsPrice: res.obj.activityPrice,
              basePrice: res.obj.basePrice,
              assembleNum: res.obj.totalNumber,
              code: `${getApp().basePath.systemUrl}/v1/group/purchase/mini/code?userId=${arr[0].userId}&url=pages/index/index?id=${res.obj.spuId}&purchaseOpenId=${res.obj.purchaseOpenId}&groupId=${res.obj.activityId}&isAssemble=true`
            }
            res.obj.innerUserInfoList.map(item =>{
               if(item.userId == -1){
                   item.picPath = `${this.data.resourcePath}/AA/group_purchase/roberPic/rober_0${item.picId+1}.png`
                   item.mark='我是机器人'
               }
               return item
            })
            if (res.obj.innerUserInfoList) {
                if (res.obj.innerUserInfoList.length < 5) {
                    this.setData({
                        hiddenList: res.obj.innerUserInfoList,
                        isHidden: false
                    })
                } else if (res.obj.innerUserInfoList.length == 5) {
                    this.setData({
                        hiddenList: res.obj.innerUserInfoList,
                        isHidden: false
                    })
                } else if (res.obj.innerUserInfoList.length > 5) {
                    let arr = []
                    res.obj.innerUserInfoList.forEach((element, index) => {
                        if (index < 4) {
                            arr.push(element)
                        }
                    });
                    this.setData({
                        hiddenList: arr,
                        isHidden: true
                    })
                }
            }
            this.setData({
              friendList: res.obj.innerUserInfoList,
              assembleDetail: res.obj,
              timeValue: testtimeValue,
              assembleDayNum: TimeStyle(0, testtimeValue),
              assembleHourNum: parseInt(TimeStyle(1, testtimeValue)) + parseInt((TimeStyle(0, testtimeValue) * 24)),
              assembleMinNum: TimeStyle(2, testtimeValue),
              assembleSecNum: TimeStyle(3, testtimeValue),
              shareParam:obj
            })
            //拼团失败 或者其他情况 就是没有拼团成功 关掉定时器
            if(this.data.assembleDetail.openStatus !== 0){
               clearInterval(this.data.timer)
            } 
          }
        })
      console.log(this.data.shareParam)
    },
     TimeCacl: function () {
         var _this = this;
         _this.setData({
             timer: setInterval(() => {
                 if (_this.data.timeValue < 1) {
                     clearInterval(_this.data.timer);
                     this.data.assembleDetail.openStatus=2;
                     this.setData({
                         assembleDetail: this.data.assembleDetail
                     })
                 }
                 let newTime = _this.data.timeValue - 1
                 let hours = parseInt(TimeStyle(1, newTime)) + parseInt((TimeStyle(0, newTime) * 24))
                 if(hours<10){
                     hours = `0${hours}`
                 }
                 _this.setData({
                     timeValue: newTime,
                     assembleDayNum: TimeStyle(0, newTime),
                     assembleHourNum: hours,
                     assembleMinNum: TimeStyle(2, newTime),
                     assembleSecNum: TimeStyle(3, newTime),
                 })
             }, 1000)
         })
     },
    showPoster() {
      this.shareWay.toggleDialog(false);
      this.createPoster.toggleDialog(true);
    },
    isShowDialog:function(e){
      this.shareWay.toggleDialog(true);
    },
    onShareAppMessage: function () {
      return {
        title: `【来跟我们一起拼团吧】${this.data.shareParam.goodsName}`,
        path: `/pages/index/index?id=${this.data.assembleDetail.spuId}&purchaseOpenId=${this.data.assembleDetail.purchaseOpenId}&groupId=${this.data.assembleDetail.activityId}&isAssemble=true`,
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