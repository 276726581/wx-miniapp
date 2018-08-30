module.exports = {
  execute: function(callback) {
    var retry = function() {
      wx.hideLoading()
      wx.showModal({
        content: '请求失败，请重试',
        showCancel: true,
        success: function(res) {
          if (res.confirm) {
            callback(retry)
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
        callback(retry)
      }
    })
  }
}