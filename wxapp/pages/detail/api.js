const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

var loadingStatus = false

var processGoods = function(res, props) {
  props.push({
    key: "发布时间",
    value: res.data.date
  })
  props.push({
    key: "分类",
    value: res.data.goodsType
  })
  props.push({
    key: "价格",
    value: res.data.price
  })
  props.push({
    key: "单位",
    value: res.data.unit
  })
  props.push({
    key: "省份",
    value: res.data.province
  })
  props.push({
    key: "城市",
    value: res.data.city
  })
  props.push({
    key: "手机号码",
    value: res.data.phone
  })
  if (null != res.data.address) {
    props.push({
      key: "定位",
      value: res.data.address
    })
  }
}
var processShip = function(res, props) {
  props.push({
    key: "发布时间",
    value: res.data.date
  })
  props.push({
    key: "价格",
    value: res.data.price
  })
  props.push({
    key: "单位",
    value: res.data.unit
  })
  props.push({
    key: "省份",
    value: res.data.province
  })
  props.push({
    key: "城市",
    value: res.data.city
  })
  props.push({
    key: "AIS",
    value: res.data.ais
  })
  props.push({
    key: "手机号码",
    value: res.data.phone
  })
  if (null != res.data.address) {
    props.push({
      key: "定位",
      value: res.data.address
    })
  }
}

module.exports = {
  loadInfo(detailType, id, data, callback) {
    var that = this
    var app = getApp()
    var requestUrl = app.config.apiServer
    if ("goods" == detailType) {
      requestUrl += "/api/v1/goods/" + id
    } else if ("ship" == detailType) {
      requestUrl += "/api/v1/ship/" + id
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
      url: requestUrl,
      success: function(res) {
        var rawData = res.data
        var props = []
        if ("goods" == detailType) {
          processGoods(res, props)
        } else if ("ship" == detailType) {
          processShip(res, props)
        }
        for (var i = 0; i < res.data.images.length; i++) {
          var img = app.config.imageServer + "/api/v1/image/" + res.data.images[i]
          res.data.images[i] = img
        }
        var content = res.data.content
        var images = res.data.images
        callback(rawData, props, content, images)
      }
    })
  },
  loadComments(detailType, data, id, count, callback) {
    if (NO_MORE == data.comment.loadingView) {
      return
    }
    if (true == loadingStatus) {
      return
    }

    var app = getApp()
    var requestUrl = app.config.apiServer + "/api/v1/comment/list"
    requestUrl += "?commentType=" + detailType
    requestUrl += "&providerId=" + id
    requestUrl += "&count=" + count
    if (data.comment.items.length > 0) {
      requestUrl += "&lastId=" + data.comment.items[data.comment.items.length - 1].id
    }
    app.httpTemplate.request({
      url: requestUrl,
      success: function(res) {
        var list = res.data
        callback(list)
        loadingStatus = false
      }
    })
  },
  sendComment(detailType, providerId, content, callback) {
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
          commentType: detailType
        },
        success: callback
      })
    })
  }
}