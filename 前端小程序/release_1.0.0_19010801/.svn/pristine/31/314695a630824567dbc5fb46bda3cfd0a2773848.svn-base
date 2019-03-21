// pages/address-list/address-list.js
let API = getApp().API
let myForEach = getApp().myForEach
let $App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staticImageUrl: $App.staticImageUrl,
    addressList: [],
    startX: 0,
    startY: 0,
    emptyPageObj: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu();
      new $App.newNav() // 注册快速导航组件
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
    this.getAddressList() 
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
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      return $App.shareAppMessageObj
    }
  },
  toAddAddress(e) {
    let region = ''
    let temp = e.currentTarget.dataset.item
    if (temp) {
      if (temp.cityName === temp.provinceName) {
        region = temp.provinceName + temp.districtName + temp.address
      } else {
        region = temp.provinceName + temp.cityName + temp.districtName
      }
      let item = {
        consignee: temp.consignee,
        region: region,
        address: temp.address,
        mobile: temp.mobile,
        province: temp.province,
        city: temp.city,
        district: temp.district,
        id: temp.id
      }
      wx.navigateTo({
        url: '../add-address/add-address?item=' + JSON.stringify(item),
      })
    } else {
      wx.navigateTo({
        url: '../add-address/add-address',
      })
    }
  },
  getAddressList() {
    let url = `/order/getAddressByUserId`
    let defaultAddress = wx.getStorageSync('defaultAddress')
    API.getAddressList()
    .then(res => {
      if (res.obj && res.obj.length > 0) {
        myForEach(res.obj, (value, index) => {
          value.isMove = false
          if (defaultAddress) {
            if (value.id === defaultAddress.id) {
              wx.setStorageSync('defaultAddress', value)
              value.checked = true
              return
            } else {
              value.checked = false
            }
          } else {
            index === 0 ? value.checked = true : value.checked = false
          }
        })
        this.setData({ addressList: res.obj })
      } else {
        this.setData({ 
          addressList: [],
          emptyPageObj: {
            imgUrl: this.data.staticImageUrl+'undefined.png',
            title: '您还没有添加过地址呢',
            // btnText: '去逛一逛',
            // url: '/pages/plan/house-case/house-case'
          }
        })
      }
    })
  },
  radioChange(e) { // 设置默认地址
    let index = parseInt(e.detail.value)
    this.radioChangeCommon(index)
  },
  changeRadio(e) { // 动态改变radio值
    let index = e.currentTarget.dataset.index
    if (this.data.addressList[index].checked) {
      return
    }
    myForEach(this.data.addressList, (value, i) => {
      if (index == i) {
        value.checked = true
      } else {
        value.checked = false        
      }
    })
    this.setData({
      addressList: this.data.addressList
    })
    this.radioChangeCommon(index)
  },
  radioChangeCommon(index) { // 抽取公共类
    wx.setStorageSync('defaultAddress', this.data.addressList[index])
    myForEach(this.data.addressList, (value, i) => {
      value.checked = (i == index)
    })
    this.setData({
      addressList: this.data.addressList
    })
    let pages = getCurrentPages()
    let prePage = pages[pages.length - 2]
    if (prePage.getAddressList) {
      // prePage.getAddressList()
      wx.navigateBack({
        delta: 1
      })
    }
  },
  carTouchStart(e) { // 购物车开始滑动
    let index = e.currentTarget.dataset.index
    this.setData({
      startX: e.touches[0].clientX,
      startY: e.touches[0].clientY,
    })
    // 物归原位
    myForEach(this.data.addressList, (value) => {
      value.isMove = false
    })
    this.setData({
      addressList: this.data.addressList
    })
  },
  carTouchMove(e) { // 购物车滑动过程 
    let index = e.currentTarget.dataset.index,
      moveX = e.touches[0].clientX,
      moveY = e.touches[0].clientY
    let actualDistanceX = moveX - this.data.startX,
      actualDistanceY = moveY - this.data.startY
    if (this.computeAngle(actualDistanceX, actualDistanceY)) {
      if (moveX > this.data.startX) {
        this.data.addressList[index].isMove = false
      } else {
        this.data.addressList[index].isMove = true
      }
      this.setData({
        addressList: this.data.addressList
      })
    }
  },
  computeAngle(x, y) { // 计算角度
    let angle = Math.atan(y / x) * (180 / Math.PI)
    return !(Math.abs(angle) > 30)
  },
  deleteAloneAddress(e) { // 删除地址
    let id = e.currentTarget.dataset.item.id,
        index = e.currentTarget.dataset.index
    wx.showModal({
      title: '提示',
      content: '确定删除此地址',
      confirmText: '确定',
      cancelText: '取消',
      cancelColor: '#999999',
      confirmColor: '#ff6419',
      success: (res) => {
        if (res.confirm) {
          API.deleteUserAddress({ addressId: id })
          .then(res => {
            if (res.status) {
              this.data.addressList.splice(index, 1)
              this.setData({ addressList: this.data.addressList })
              wx.showToast({ title: '删除成功' })
              if (wx.getStorageSync('defaultAddress')) { // 如果本地有地址
                if (this.data.addressList.length === 0) {
                  wx.removeStorageSync('defaultAddress')
                } else {
                  if (id === wx.getStorageSync('defaultAddress').id) {
                    wx.setStorageSync('defaultAddress', this.data.addressList[0])
                    this.data.addressList[0].checked = true
                    this.setData({ addressList: this.data.addressList })
                  }
                }
              }
            } else {
              wx.showToast({ title: '删除失败', icon: 'none' })
            }
          })
        }
      }
    })
  }
})