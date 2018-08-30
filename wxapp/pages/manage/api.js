const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

var loadingStatus = false

module.exports = {
  loadData(data, count, callback) {
    var app = getApp()
    if (NO_MORE == data.loadingView) {
      return
    }
    if (true == loadingStatus) {
      return
    }

    app.userApi.login(function(userInfo, hide) {
      var requestUrl = app.config.apiServer
      if ("goods" == data.viewType) {
        requestUrl += "/api/v1/goods/list"
        requestUrl += "?uid=" + userInfo.uid
        requestUrl += "&count=" + count
      } else if ("ship" == data.viewType) {
        requestUrl += "/api/v1/ship/list"
        requestUrl += "?uid=" + userInfo.uid
        requestUrl += "&count=" + count
      } else {
        wx.showToast({
          title: '页面错误',
          success: function() {
            wx.reLaunch()
          }
        })
        return
      }
      if (data.items.length > 0) {
        requestUrl += "&lastId=" + data.items[data.items.length - 1].id
      }
      app.httpTemplate.request({
        url: requestUrl,
        success: function(res, hide) {
          var list = []
          res.data.forEach(function(item) {
            for (var i = 0; i < item.images.length; i++) {
              var img = app.config.imageServer + "/api/v1/image/" + item.images[i]
              item.images[i] = img
            }
            list.push(item)
          })
          callback(list, hide)
          loadingStatus = false
        }
      })
    })
  },
  deleteData(viewType, id, callback) {
    var app = getApp()
    var url = app.config.apiServer
    if ("goods" == viewType) {
      url += "/api/v1/goods/" + id + "?uid=" + app.globalData.uid
    } else if ("ship" == viewType) {
      url += "/api/v1/ship/" + id + "?uid=" + app.globalData.uid
    } else {
      wx.showToast({
        title: '页面错误',
        success: function() {
          wx.reLaunch()
        }
      })
      return
    }
    app.httpTemplate.request({
      method: "DELETE",
      url: url,
      success: callback
    })
  }
}