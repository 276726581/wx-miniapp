// pages/detail/detail.js

var api = require("api.js")

const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

var app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: null,
    detailType: null,
    detail: {
      rawData: {},
      props: [],
      content: "",
      images: []
    },
    comment: {
      items: [],
      commentContent: "",
      loadingView: NO_DATA,
      loadingStatus: false
    }
  },
  onLoad: function(options) {
    app.pageStack.push(this)
    var that = this

    var id = options.id
    var detailType = options.detailType
    that.data.id = id
    that.data.detailType = detailType

    this.loadInfo(detailType, id)
    this.loadComments(detailType, id, 30)
  },
  onUnload: function() {
    app.pageStack.pop()
  },
  onShareAppMessage: function() {
    return {
      title: this.data.detail.title,
      path: "/pages/detail/detail?id=" + this.data.detail.id
    }
  },
  refresh: function() {
    this.refreshComments(this.data.detailType, this.data.id, 30)
  },
  onCall: function() {
    var phone = this.data.detail.rawData.phone
    wx.makePhoneCall({
      phoneNumber: phone
    })
  },
  refreshComments(detailType, id, count) {
    this.data.comment.items = []
    this.data.comment.loadingView = NO_DATA
    this.loadComments(this.data.detailType, this.data.id, 30)
  },
  loadComments(detailType, id, count) {
    var that = this
    api.loadComments(detailType, this.data, id, count, function(list) {
      if (list.length <= 0) {
        that.data.comment.loadingView = NO_MORE
      } else if (list.length >= 10) {
        that.data.comment.loadingView = HAS_MORE
      } else {
        that.data.comment.loadingView = NO_MORE
      }
      app.concatList(that.data.comment.items, list)
      that.setData({
        comment: that.data.comment
      })
      wx.hideLoading()
    })
  },
  loadInfo(detailType, id) {
    var that = this
    api.loadInfo(detailType, id, this.data, function(rawData, props, content, images) {
      that.data.detail.rawData = rawData
      that.data.detail.props = props
      that.data.detail.content = content
      that.data.detail.images = images
      that.setData({
        detail: that.data.detail
      })
      wx.hideLoading()
    })
  },
  onPublishComment() {
    var url = '/pages/comment/comment'
    url += "?commentType=" + this.data.detailType
    url += "&id=" + this.data.id
    wx.navigateTo({
      url: url
    })
  },
  sendComment() {
    var that = this
    api.sendComment(this.data.detailType, this.data.id, this.data.comment.commentContent, function() {
      that.data.comment.commentContent = ""
      that.setData({
        comment: that.data.comment
      })
      wx.hideLoading()

      that.refreshComments(that.data.detailType, that.data.id, 30)
    })
  },
  onReachBottom() {
    this.loadComments(this.data.detailType, this.data.id, 30)
  }
})