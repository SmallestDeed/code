let quickNavigationData = {
  'quickNavigation.quickNavigationShow': false
}

let quickNavigationEvent = {
  changeQuickNavigationShow() {
    this.setData({
      'quickNavigation.quickNavigationShow': !this.data.quickNavigation.quickNavigationShow
    })
  },
  routerToFirstPage(e) {
    let type = e.currentTarget.dataset.type
    let url = null
    switch (type) {
      case 'feedback': url = `/pages/feedback/feedback`; break;
      case 'index': url = `/pages/index/index`; break;
      case 'case': url = `/pages/house-case/house-case`; break;
      case 'good': url = `/pages/house-goods/house-goods`; break;
      case 'type': url = `/pages/house-type/house-type`; break;
      case 'myself': url = `/pages/personal-center/personal-center`; break;
    }
    if (type == 'feedback') {
      wx.navigateTo({
        url: url,
      })
      return;
    }
    wx.switchTab({
      url: url,
    })
  }
}

// 声明实例
function quickNavigation() {
  let pages = getCurrentPages()
  let curPage = pages[pages.length - 1]
  Object.assign(curPage, quickNavigationEvent)
  curPage.setData(quickNavigationData)
  curPage.quickNavigation = this
  return this
}

module.exports = {
  quickNavigation
}