// pages/comment/comment.js

var api = require("api.js")

var app = getApp()

Page({

  data: {
    id: null,
    commentType: null,
    commentArea: {
      width: "100%",
      height: "100%"
    },
    content: ""
  },
  onLoad: function(options) {
    app.pageStack.push(this)
    wx.hideShareMenu()
    var that = this

    var id = options.id
    var commentType = options.commentType
    this.data.id = id
    this.data.commentType = commentType

    wx.getSystemInfo({
      success: function(res) {
        that.data.commentArea.width = res.windowWidth * 2 - 10 * 2
        that.data.commentArea.height = res.windowHeight
        that.setData({
          commentArea: that.data.commentArea
        })
      }
    })
  },
  onUnload: function() {
    app.pageStack.pop()
  },
  onCommentInput: function(e) {
    this.data.content = e.detail.value
  },
  sendComment: function() {
    api.sendComment(this.data.commentType, this.data.id, this.data.content, function() {
      app.refreshPage("pages/detail/detail")
      wx.hideLoading()
      wx.navigateBack()
    })
  }
})