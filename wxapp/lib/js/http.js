var request = function(options) {
  wx.request({
    method: options.method,
    url: options.url,
    data: options.data,
    header: options.header,
    dataType: options.dataType,
    success: options.success,
    fail: function(res) {
      wx.showToast({
        title: '请求失败'
      })
      if (null != options.fail) {
        options.fail(res)
      }
    },
    complete: options.complete
  })
}
module.exports = {
  request
}