
// pages/brandFilter/brandFilter.js
import { myPinYin } from '../../utils/Convert_Pinyin.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    brandList: [],
    ListId:'A',
    code:'',
    name:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
    this.getBrand();
  },
  
  getBrand(){
    let pyList = [{ key: 'A', val: [] }, { key: 'B', val: [] }, { key: 'C', val: [] }, { key: 'D', val: [] }, { key: 'E', val: [] }, { key: 'F', val: [] }, { key: 'G', val: [] }, { key: 'H', val: [] }, { key: 'I', val: [] }, { key: 'J', val: [] }, { key: 'K', val: [] }, { key: 'L', val: [] }, { key: 'M', val: [] }, { key: 'N', val: [] }, { key: 'O', val: [] }, { key: 'P', val: [] }, { key: 'Q', val: [] }, { key: 'R', val: [] }, { key: 'S', val: [] }, { key: 'T', val: [] }, { key: 'U', val: [] }, { key: 'V', val: [] }, { key: 'W', val: [] }, { key: 'X', val: [] }, { key: 'Y', val: [] }, { key: 'Z', val: [] }]
    let storageList = wx.getStorageSync('threeClassificationList'),
        List=[]
    console.log(storageList, 'wqwq')        
    for (let i = 0; i < storageList.length;i++){
      if (storageList[i].name=='品牌'){
        List = storageList[i]
      }
    }
    console.log("list", List.fourClassificationList)
      for (let i = 0; i < List.fourClassificationList.length;i++){
        for(let j=0;j<pyList.length;j++){
          let a = List.fourClassificationList[i].name
          
          if (myPinYin.getFullChars(a).substr(0,1) == pyList[j].key){
            pyList[j].val.push(List.fourClassificationList[i])
          }
        }
      }
      let arr = []
      for (var i = 0; i < pyList.length; i++) {
        if (pyList[i].val.length > 0) {
          arr.push(pyList[i])
        }
      }
      console.log(pyList)
      this.setData({
        brandList: arr
      })
  },
  deleteNull(e){
    let nullIndex=[];

    
    for (let i = 0; i < e.length; i++) {
      if (e[i].val.length < 1) {
        nullIndex.push(i)
      }
    }
    for (let i = 0; i < nullIndex.length;i++){
      e.splice(nullIndex[i], 1)
    }
    return e
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
  change(e){
    let index = e.currentTarget.dataset.index;
    let key = e.currentTarget.dataset.key;
    this.setData({
      code: this.data.brandList[key].val[index].longCode,
      name: this.data.brandList[key].val[index].name
    })
    
  },
  toWord(e) {
    let Item = e.currentTarget.dataset.item;
    console.log(Item, 'item')
    this.setData({
      ListId: Item
    });
   
  },
  cancle(){
    let pages = getCurrentPages();//当前页面 
    let prevPage = pages[pages.length - 2];//上一页面
    prevPage.setData({
      brandType: 'cancle'
    })
    wx.navigateBack({

    })
  },
  confirm(){
    let pages = getCurrentPages();//当前页面 
    let prevPage = pages[pages.length - 2];//上一页面
    prevPage.setData({
      brandCode:this.data.code,
      brandName: this.data.name,
      brandType: 'confirm'
    })
    wx.navigateBack({
      
    })
  }

  
})