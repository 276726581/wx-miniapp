var configJs = require('config.js')
var httpTemplateJs = require('http.js')
var retryTemplateJs = require('retry.js')

var config = configJs
var httpTemplate = httpTemplateJs
var retryTemplate = retryTemplateJs

module.exports = {
  getLocation(callback) {
    retryTemplate.execute(function(hide, retry) {
      wx.getLocation({
        type: "wgs84",
        success: function(res) {
          callback(res)
        },
        fail: retry
      })
    })
  },
  getLocationInfo(callback) {
    var that = this
    retryTemplate.execute(function(retry) {
      that.getLocation(function(res) {
        var url = config.apiServer + "/api/v1/location/info";
        url += "?lat=" + res.latitude
        url += "&lng=" + res.longitude
        httpTemplate.request({
          url: url,
          success: function(res) {
            callback(res)
          },
          fail: retry
        })
      })
    })
  }
}