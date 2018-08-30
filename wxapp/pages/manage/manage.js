// pages/manage/manage.js

var api = require("api.js");

const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

const app = getApp()
var userInfo = {}

Page({

  data: {
    viewType: null,
    items: [],
    loadingView: NO_DATA
  },
  onLoad: function(options) {
    app.pageStack.push(this)
    wx.hideShareMenu()

    var that = this
    that.data.viewType = options.viewType
    that.setData({
      viewType: that.data.viewType
    })
    this.refresh()
  },
  onUnload: function() {
    app.pageStack.pop()
  },
  onReachBottom: function() {
    this.loadData(30)
  },
  onDelete: function(e) {
    var that = this
    api.deleteData(that.data.viewType, e.detail.data.id, function(res) {
      that.data.items.splice(e.detail.index, 1)
      that.setData({
        items: that.data.items
      })
      wx.hideLoading()
    })
  },
  newPublish: function() {
    var url = '/pages/publish/publish'
    url += "?viewType=" + this.data.viewType
    console.log(url)
    wx.navigateTo({
      url: url
    })
  },
  refresh() {
    var that = this
    that.data.loadingView = NO_DATA
    that.data.items = []
    that.loadData(30)
  },
  loadData(count) {
    var that = this
    api.loadData(that.data, count, function(list) {
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
  }
})