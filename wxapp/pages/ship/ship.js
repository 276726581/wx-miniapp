const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2
const requestPath = "/api/v1/ship/list"

//获取应用实例
const app = getApp()

Page({
  data: {
    items: [],
    loadingView: NO_DATA,
    loadingStatus: false
  },
  onLoad: function() {
    this.refresh();
  },
  onPullDownRefresh() {
    console.log('onPullDownRefresh')
  },
  onReachBottom: function() {
    this.loadMore();
  },
  refresh() {
    this.data.items = []
    this.getData(10)
  },
  loadMore() {
    this.getData(10)
  },
  getData(count) {
    let that = this
    if (NO_MORE == this.data.loadingView) {
      return
    }
    if (true == this.data.loadingStatus) {
      return
    }

    var requestUrl = app.config.apiServer + requestPath
    requestUrl += "?count=" + count
    if (that.data.items.length > 0) {
      requestUrl += "&lastId=" + that.data.items[that.data.items.length - 1].id
    }
    console.log(requestUrl)
    wx.showLoading({
      title: "加载中",
      mask: true
    })
    wx.request({
      url: requestUrl,
      success: function(res) {
        res.data.forEach(function(item) {
          for (var i = 0; i < item.images.length; i++) {
            var img = app.config.imageServer + "/api/v1/image/" + item.images[i]
            item.images[i] = img
          }
          that.data.items.push(item)
        })
        if (res.data.length <= 0) {
          that.data.loadingView = NO_MORE
        } else if (that.data.items.length >= 10) {
          that.data.loadingView = HAS_MORE
        } else {
          that.data.loadingView = NO_MORE
        }
        that.data.loadingStatus = false
        that.setData({
          items: that.data.items,
          loadingView: that.data.loadingView,
          loadingStatus: that.data.loadingStatus
        })
        wx.hideLoading()
      }
    })
  },
  onCall: function(e) {
    var phone = e.detail.data.phone
    wx.makePhoneCall({
      phoneNumber: phone
    })
  }
})