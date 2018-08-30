var retryTemplateJs = require('retry.js')
var retryTemplate = retryTemplateJs.retryTemplate
var httpTemplate = {
  request: function(options) {
    retryTemplate.execute(function(hide, fail) {
      wx.request({
        url: options.url,
        data: options.data,
        header: options.header,
        method: options.method,
        dataType: options.dataType,
        responseType: options.responseType,
        success: function(res) {
          if (200 == res.statusCode) {
            options.success(res, hide)
          } else {
            options.fail(res)
          }
        },
        fail: function() {
          fail()
        }
      })
    })
  },
  uploadFile: function(options) {
    retryTemplate.execute(function(hide, fail) {
      wx.uploadFile({
        url: options.url,
        filePath: options.filePath,
        name: options.name,
        success: function(res) {
          if (200 == res.statusCode) {
            options.success(res, hide)
          } else {
            options.fail(res)
          }
        },
        fail: function() {
          fail()
        }
      })
    })
  }
}
module.exports = {
  httpTemplate
}