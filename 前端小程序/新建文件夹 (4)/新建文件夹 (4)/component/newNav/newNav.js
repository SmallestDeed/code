let newNavData = {
    'newNav.newNavShow': false
}
let newNavEvent = {
    changenewNavShow() {
        this.setData({
            'newNav.newNavShow': !this.data.newNav.newNavShow
        })
    },
    toPage(e) {
        let v = e.currentTarget.dataset.type
        let src = e.currentTarget.dataset.src
        wx.switchTab({
            url: '/pages/' + src + v + '/' + v,
        })
    }
}
// 声明实例
function newNav() {
    let pages = getCurrentPages()
    let curPage = pages[pages.length - 1]
    Object.assign(curPage, newNavEvent)
    curPage.setData(newNavData)
    curPage.newNav = this
    return this
}
module.exports = {
    newNav
}