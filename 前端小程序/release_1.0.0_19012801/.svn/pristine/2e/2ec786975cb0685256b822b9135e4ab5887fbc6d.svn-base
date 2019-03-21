
// 数据
let emptyTemplateData = {
  "emptyTemplate.emptyTemplateShow": false,
  "emptyTemplate.emptyTemplateText": '抱歉，还没有可供推荐的设计方案~',
  "emptyTemplate.staticImageUrl": getApp().staticImageUrl
}
// 事件
let emptyTemplateEvent = {
  emptyTemplateShow(type) {
    console.log(type)
    if (type === 'show') {
      this.setData({
        "emptyTemplate.emptyTemplateShow": true
      })
    } else {
      this.setData({
        "emptyTemplate.emptyTemplateShow": false
      })
    }
  },
  setEmptyTemplateText(text) {
    this.setData({
      "emptyTemplate.emptyTemplateText": text
    })
  }
}

// 声明实例
function emptyTemplate() {
  let pages = getCurrentPages()
  let curPage = pages[pages.length - 1]
  Object.assign(curPage, emptyTemplateEvent)
  curPage.setData(emptyTemplateData)
  curPage.emptyTemplate = this
  return this
}

module.exports = {
  emptyTemplate
}