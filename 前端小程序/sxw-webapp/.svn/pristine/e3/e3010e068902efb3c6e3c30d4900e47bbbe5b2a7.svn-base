import API from '../../api/api.js'
let bindingPhoneData = {
  'bindingPhone.bindingPhoneShow': false,
  'bindingPhone.bindingPhoneMobile': '',
  'bindingPhone.bindingPhoneCode': '',
  'bindingPhone.bindingPhoneAllow': false,
  'bindingPhone.bindingMobileOk': false,
  'bindingPhone.bindingPhoneGetCodeText': '获取验证码', // 倒计时
  'bindingPhone.bindingPhoneCodeSuccess': false,
  'bindingPhone.bindingPhoneTimer': null,
  'bindingPhone.bindingPhoneGetCodeText': '获取验证码',
  'bindingPhone.bindingPhoneMask': false
}

let bindingPhoneEvent = {
  bindingPhoneShow() {
    this.setData({
      'bindingPhone.staticImageUrl': getApp().staticImageUrl,
      'bindingPhone.bindingPhoneShow': true
    })
  },
  bindingPhoneHide(e) {
    this.setData({
      'bindingPhone.bindingPhoneShow': false,
      'bindingPhone.bindingPhoneAllow': false,
      'bindingPhone.bindingPhoneMobile': '',
      'bindingPhone.bindingPhoneCode': '',
      'bindingPhone.bindingPhoneformaTure': false,
      'bindingPhone.bindingPhoneGetCodeText': '获取验证码'
    })
    if (this.bindgetRenderTypeShow) {
      this.bindgetRenderTypeShow(true)
    }
    clearInterval(this.data.bindingPhone.bindingPhoneTimer)
  },
  changeCode(e) {
    this.setData({
      'bindingPhone.bindingPhoneCode': e.detail.value
    })
    if (this.data.bindingPhone.bindingPhoneMobile && this.data.bindingPhone.bindingPhoneCode) {
      this.setData({
        'bindingPhone.bindingPhoneAllow': true
      })
    } else {
      this.setData({
        'bindingPhone.bindingPhoneAllow': false
      })
    }
  },
  beginBinding() { // 开始绑定
    let regMobile = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/
    let flag = regMobile.test(this.data.bindingPhone.bindingPhoneMobile)
    let codeFlag = /^\d{6}\b/.test(this.data.bindingPhone.bindingPhoneCode)
    if (!this.data.bindingPhone.bindingPhoneMobile || !this.data.bindingPhone.bindingPhoneCode) {
      return
    }
    if (!flag) {
      wx.showToast({
        title: '请输入正确的手机号',
        icon: 'none'
      })
      return
    } else if (!codeFlag) {
      wx.showToast({
        title: '请输入正确的验证码',
        icon: 'none'
      })
      return
    }
    API.bindUserMobile({
        mobile: this.data.bindingPhone.bindingPhoneMobile,
        authCode: this.data.bindingPhone.bindingPhoneCode
      })
      .then(res => {
        if (res.success) {
          wx.showToast({
            title: '绑定成功',
            duration: 2000,
          })
          setTimeout(() => {
            this.setData({
              'bindingPhone.bindingPhoneShow': false
            })
            this.bindPhoneCallBack() // 绑定手机号回调
          }, 2000);
        } else {
          wx.showToast({
            title: res.message,
            icon: 'none'
          })
        }
      })
  },
  getCode() { // 获取验证码
    console.log(this.data.bindingPhone.bindingPhoneformaTure)
    if (!this.data.bindingPhone.bindingPhoneformaTure || this.data.bindingPhone.bindingPhoneGetCodeText !== '获取验证码') {
      console.log(1)
      return
    }
    API.getBindPhoneCode({
        phoneNumber: this.data.bindingPhone.bindingPhoneMobile,
      functionType: 2
      })
      .then(res => {
        if (res.success) {
          let count = 61,
            $self = this
          this.data.bindingPhone.bindingPhoneTimer = setInterval(() => {
            count--
            $self.setData({
              'bindingPhone.bindingPhoneGetCodeText': count + 's'
            })
            if (count <= 0) {
              clearInterval($self.data.bindingPhone.bindingPhoneTimer)
              $self.setData({
                'bindingPhone.bindingPhoneGetCodeText': '获取验证码'
              })
            }
          }, 1000)
        } else {
          this.setData({
            'bindingPhone.bindingPhoneGetCodeText': '获取验证码'
          })
        }
      })
  },
  changeMobile(e) {
    console.log(1)
    this.setData({
      'bindingPhone.bindingPhoneMobile': e.detail.value
    })
    if (this.data.bindingPhone.bindingPhoneMobile && this.data.bindingPhone.bindingPhoneCode) {
      this.setData({
        'bindingPhone.bindingPhoneAllow': true
      })
    } else {
      this.setData({
        'bindingPhone.bindingPhoneAllow': false
      })
    }
    let regMobile = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/
    let flag = regMobile.test(this.data.bindingPhone.bindingPhoneMobile)
    this.setData({
      'bindingPhone.bindingPhoneformaTure': flag
    })

  },
}

// 声明实例
function bindingPhone() {
  let pages = getCurrentPages()
  let curPage = pages[pages.length - 1]
  Object.assign(curPage, bindingPhoneEvent)
  curPage.setData(bindingPhoneData)
  curPage.bindingPhone = this
  return this
}

module.exports = {
  bindingPhone
}