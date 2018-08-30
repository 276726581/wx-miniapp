var retryTemplate = {
  execute: function(callback) {
    var hide = function() {
      wx.hideLoading()
    }
    var retry = function() {
      wx.hideLoading()
      wx.showModal({
        content: '请求失败，请重试',
        showCancel: true,
        success: function(res) {
          if (res.confirm) {
            callback(hide, retry)
          } else {
            wx.navigateBack()
          }
        }
      })
    }
    wx.showLoading({
      title: '请求中',
      mask: true,
      success: function() {
        callback(hide, retry)
      }
    })
  }
}
module.exports = {
  retryTemplate
}