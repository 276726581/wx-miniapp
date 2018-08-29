//app.js

var configJs = require('lib/js/config.js')
var config = configJs.config
App({
  config: config,
  onLaunch: function() {

  },
  getLoginResult(callback) {
    var that = this
    if (that.globalData.isLogin) {
      console.log('logined')
      callback(that.globalData)
      return
    }

    var authback = function() {
      wx.navigateTo({
        url: "/pages/auth/auth"
      })
    }
    var failback = function(res) {
      wx.showModal({
        content: '请求失败请重试',
        showCancel: false,
        success: function() {
          that.getLoginResult(callback)
        }
      })
    }
    console.log('login')
    wx.login({
      success: function(loginRes) {
        wx.getUserInfo({
          success: function(userInfoRes) {
            console.log(userInfoRes)
            that.globalData.userInfo = userInfoRes.userInfo
            wx.request({
              method: "POST",
              url: config.apiServer + '/api/v1/user/onLogin/' + loginRes.code,
              data: userInfoRes.userInfo,
              success: function(onLoginRes) {
                that.globalData.sessionKey = onLoginRes.data.sessionKey
                that.globalData.uid = onLoginRes.data.uid
                that.globalData.isLogin = true
                callback(that.globalData)
              },
              fail: failback
            })
          },
          fail: authback
        })
      },
      fail: failback
    })
  },
  globalData: {
    isLogin: false,
    userInfo: null,
    uid: null,
    sessionKey: null
  }
})