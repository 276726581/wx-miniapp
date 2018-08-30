const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

var loadingStatus = false

module.exports = {
  loadData(data, count, callback) {
    if (NO_MORE == data.loadingView) {
      return
    }
    if (true == loadingStatus) {
      return
    }

    var app = getApp()
    var requestUrl = app.config.apiServer + "/api/v1/ship/list"
    requestUrl += "?count=" + count
    if (data.items.length > 0) {
      requestUrl += "&lastId=" + data.items[data.items.length - 1].id
    }
    app.httpTemplate.request({
      url: requestUrl,
      success: function(res) {
        var list = []
        res.data.forEach(function(item) {
          for (var i = 0; i < item.images.length; i++) {
            var img = app.config.imageServer + "/api/v1/image/" + item.images[i]
            item.images[i] = img
          }
          list.push(item)
        })
        callback(list)
        loadingStatus = false
      }
    })
  }
}