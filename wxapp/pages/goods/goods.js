var api = require("api.js")

const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

const app = getApp()

Page({
  data: {
    items: [],
    loadingView: NO_DATA
  },
  onLoad: function() {
    app.pageStack.push(this)
    this.refresh()
  },
  onUnload: function() {
    app.pageStack.pop()
  },
  onReachBottom: function() {
    this.loadMore()
  },
  refresh() {
    this.data.loadingView = NO_DATA
    this.data.items = []
    this.loadData(30)
  },
  loadMore() {
    this.loadData(30)
  },
  loadData(count) {
    var that = this
    api.loadData(this.data, count, function(list) {
      if (list.length <= 0) {
        that.data.loadingView = NO_MORE
      } else if (list.length >= 10) {
        that.data.loadingView = HAS_MORE
      } else {
        that.data.loadingView = NO_MORE
      }
      app.concatList(that.data.items, list)
      that.setData({
        items: that.data.items,
        loadingView: that.data.loadingView
      })
      wx.hideLoading()
    })
  },
  onCall: function(e) {
    var phone = e.detail.data.phone
    wx.makePhoneCall({
      phoneNumber: phone
    })
  }
})