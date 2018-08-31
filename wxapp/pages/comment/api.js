module.exports = {
  sendComment(commentType, providerId, content, callback) {
    var that = this
    var app = getApp()
    if (null == content || content.length <= 0) {
      wx.showToast({
        title: '评论内容能为空',
        mask: true
      })
      return
    }
    app.userApi.login(function(res) {
      var uid = res.uid
      var url = app.config.apiServer + "/api/v1/comment"
      app.httpTemplate.request({
        method: "POST",
        url: url,
        data: {
          senderId: uid,
          providerId: providerId,
          content: content,
          commentType: commentType
        },
        success: callback
      })
    })
  }
}