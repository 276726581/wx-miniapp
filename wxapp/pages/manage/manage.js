// pages/manage/manage.js

const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

const app = getApp()

Page({
  data: {
    viewType: null,
    userInfo: null,
    items: [],
    loadingView: NO_DATA,
    loadingStatus: false
  },
  onLoad: function(options) {
    wx.hideShareMenu()

    var that = this
    that.data.viewType = options.viewType
    that.setData({
      viewType: that.data.viewType
    })
    this.refresh()
  },
  onReachBottom: function() {
    console.log("onReachBottom")
    this.loadMore()
  },
  scrolltolower(e) {
    this.loadMore()
  },
  refresh() {
    var that = this
    this.data.loadingView = NO_DATA
    app.userApi.login(function(res, hide) {
      that.data.items = []
      that.getData(res.uid, 10)
      hide()
    })
  },
  loadMore() {
    var that = this
    that.getData(app.globalData.uid, 10)
  },
  getData: function(uid, count) {
    var that = this
    if (NO_MORE == this.data.loadingView) {
      return
    }
    if (true == this.data.loadingStatus) {
      return
    }

    var requestUrl = app.config.apiServer
    if ("goods" == that.data.viewType) {
      requestUrl += "/api/v1/goods/list"
      requestUrl += "?uid=" + uid
      requestUrl += "&count=" + count
    } else if ("ship" == that.data.viewType) {
      requestUrl += "/api/v1/ship/list"
      requestUrl += "?uid=" + uid
      requestUrl += "&count=" + count
    } else {
      wx.showToast({
        title: '页面错误',
        success: function() {
          wx.reLaunch()
        }
      })
      return
    }

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
      },
      fail: function() {
        wx.showModal({
          content: '请求失败请重试',
          showCancel: false,
          success: function() {
            that.getData()
          }
        })
      }
    })
  },
  onReachBottom: function() {

  },
  onDelete: function(e) {
    var that = this
    var url = app.config.apiServer
    if ("goods" == that.data.viewType) {
      url += "/api/v1/goods/" + e.detail.data.id + "?uid=" + app.globalData.uid
    } else if ("ship" == that.data.viewType) {
      url += "/api/v1/ship/" + e.detail.data.id + "?uid=" + app.globalData.uid
    } else {
      wx.showToast({
        title: '页面错误',
        success: function() {
          wx.reLaunch()
        }
      })
      return
    }
    console.log("onDelete: " + url)
    wx.request({
      method: "DELETE",
      url: url,
      success: function(res) {
        that.data.items.splice(e.detail.index, 1)
        that.setData({
          items: that.data.items
        })
      },
      fail: function() {
        wx.showModal({
          content: '请求失败请重试',
          showCancel: false,
          success: function() {
            that.onDelete(e)
          }
        })
      }
    })
  },
  newPublish: function() {
    var url = '/pages/publish/publish'
    url += "?viewType=" + this.data.viewType
    console.log(url)
    wx.navigateTo({
      url: url
    })
  }
})