var configJs = require('config.js')
var httpTemplateJs = require('http.js')
var retryTemplateJs = require('retry.js')

var config = configJs.config
var httpTemplate = httpTemplateJs.httpTemplate
var retryTemplate = retryTemplateJs.retryTemplate

module.exports = {
  userApi: {
    isLogin: false,
    login: function(callback) {
      var that = this
      if (that.isLogin) {
        var app = getApp()
        callback(app.globalData, function() {})
        return
      }
      retryTemplate.execute(function(hide, retry) {
        wx.login({
          success: function(loginRes) {
            wx.getUserInfo({
              success: function(userInfoRes) {
                var url = config.apiServer + '/api/v1/user/onLogin/' + loginRes.code
                wx.request({
                  method: "POST",
                  url: url,
                  data: userInfoRes.userInfo,
                  success: function(onLoginRes) {
                    that.isLogin = true
                    var app = getApp()
                    app.globalData.userInfo = userInfoRes.userInfo
                    app.globalData.sessionKey = onLoginRes.data.sessionKey
                    app.globalData.uid = onLoginRes.data.uid
                    callback(app.globalData, hide)
                  },
                  fail: retry
                })
              },
              fail: function() {
                hide()
                wx.navigateTo({
                  url: "/pages/auth/auth"
                })
              }
            })
          },
          fail: retry
        })
      })
    }
  }
}