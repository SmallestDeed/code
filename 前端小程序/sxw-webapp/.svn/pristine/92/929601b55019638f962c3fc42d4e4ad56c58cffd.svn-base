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

        if (e.currentTarget.dataset.type == 'search-houseType') {
            wx.setStorageSync('caseItem', {})
        }
        let baseUrl = e.currentTarget.dataset.type, url = `/pages/${e.currentTarget.dataset.src}${baseUrl}/${baseUrl}`
        url = url + (baseUrl == 'search-houseType' ? '?type=search' : '');
        console.log(url);
        wx[(baseUrl === 'search-houseType' || baseUrl === 'feedback') ? 'navigateTo' : 'switchTab']({ url })

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