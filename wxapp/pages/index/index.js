//index.js


var indexData = require('../json/index.js');

//获取应用实例
const app = getApp()

Page({
  data: {
    items: [],
    showLoading: false,
    hasMore: true,
    refreshStatus: false,
    loadingStatus: false,
    loadCount: 0
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
    if (true == this.data.refreshStatus) {
      return;
    }

    let that = this
    wx.showLoading({
      title: "加载中",
      mask: true
    })
    wx.request({
      url: 'http://www.baidu.com',
      success: function(res) {
        for (let i = 0; i < 5; i++) {
          indexData.json.forEach(function(item) {
            that.data.items.push(item)
          })
        }
        that.data.refreshStatus = false
        that.data.showLoading = true
        that.setData({
          items: that.data.items,
          refreshStatus: that.data.refreshStatus,
          showLoading: that.data.showLoading
        })
        wx.hideLoading()
      }
    })
  },
  loadMore() {
    if (true == this.data.loadingStatus) {
      return;
    }

    // test
    console.log("loadCount: " + this.data.loadCount)
    if (this.data.loadCount >= 1) {
      this.data.hasMore = false
      this.setData({
        hasMore: this.data.hasMore
      })
      return
    }

    this.data.loadingStatus = true
    this.data.hasMore = true
    this.setData({
      loadingStatus: this.data.loadingStatus,
      hasMore: this.data.hasMore
    })
    let that = this
    wx.request({
      url: 'http://www.baidu.com',
      success: function(res) {
        for (let i = 0; i < 5; i++) {
          indexData.json.forEach(function(item) {
            that.data.items.push(item)
          })
        }
        that.data.loadingStatus = false
        that.data.loadCount++
          that.setData({
            items: that.data.items,
            loadingStatus: that.data.loadingStatus,
            loadCount: that.data.loadCount
          })
      }
    })
  }
})