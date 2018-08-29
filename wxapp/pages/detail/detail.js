// pages/detail/detail.js

const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

var httpJs = require('../../lib/js/http.js')

var app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: null,
    detailType: null,
    tabs: ["介绍", "评论"],
    activeIndex: 0,
    sliderOffset: 0,
    sliderLeft: 0,
    sliderWidth: 96,
    rawData: null,
    detail: {
      props: [],
      content: "",
      images: []
    },
    comment: {
      items: [],
      commentContent: "",
      loadingView: NO_DATA,
      loadingStatus: false
    }
  },
  tabClick: function(e) {
    this.setData({
      sliderOffset: e.currentTarget.offsetLeft,
      activeIndex: e.currentTarget.id
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;

    var id = options.id
    var detailType = options.detailType
    that.data.id = id
    that.data.detailType = detailType

    wx.getSystemInfo({
      success: function(res) {
        that.setData({
          sliderLeft: (res.windowWidth / that.data.tabs.length - that.data.sliderWidth) / 2,
          sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex
        });
      }
    });

    this.loadInfo(detailType, id)
    this.loadComments(detailType, id, 30)
  },
  loadComments(detailType, id, count) {
    var that = this
    if (NO_MORE == this.data.comment.loadingView) {
      return
    }
    if (true == this.data.comment.loadingStatus) {
      return
    }

    wx.showLoading({
      title: "加载中",
      mask: true
    })
    var requestUrl = app.config.apiServer + "/api/v1/comment/list"
    requestUrl += "?commentType=" + detailType
    requestUrl += "&providerId=" + id
    requestUrl += "&count=" + count
    if (that.data.comment.items.length > 0) {
      requestUrl += "&lastId=" + that.data.comment.items[that.data.comment.items.length - 1].id
    }
    console.log("comment: " + requestUrl)
    httpJs.request({
      url: requestUrl,
      success: function(res) {
        res.data.forEach(function(item) {
          that.data.comment.items.push(item)
        })
        if (res.data.length <= 0) {
          that.data.comment.loadingView = NO_MORE
        } else if (that.data.comment.items.length >= 10) {
          that.data.comment.loadingView = HAS_MORE
        } else {
          that.data.comment.loadingView = NO_MORE
        }
        that.data.loadingStatus = false
        that.setData({
          comment: that.data.comment
        })
        wx.hideLoading()
      }
    })
  },
  loadInfo(detailType, id) {
    var that = this
    wx.showLoading({
      title: "加载中",
      mask: true
    })
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
    console.log("detail: " + requestUrl)
    wx.request({
      url: requestUrl,
      success: function(res) {
        that.data.rawData = res.data
        if ("goods" == detailType) {
          that.data.detail.props.push({
            key: "发布时间",
            value: res.data.date
          })
          that.data.detail.props.push({
            key: "分类",
            value: res.data.goodsType
          })
          that.data.detail.props.push({
            key: "价格",
            value: res.data.price
          })
          that.data.detail.props.push({
            key: "单位",
            value: res.data.unit
          })
          that.data.detail.props.push({
            key: "省份",
            value: res.data.province
          })
          that.data.detail.props.push({
            key: "城市",
            value: res.data.city
          })
          that.data.detail.props.push({
            key: "手机号码",
            value: res.data.phone
          })
        } else if ("ship" == detailType) {
          that.data.detail.props.push({
            key: "发布时间",
            value: res.data.date
          })
          that.data.detail.props.push({
            key: "价格",
            value: res.data.price
          })
          that.data.detail.props.push({
            key: "单位",
            value: res.data.unit
          })
          that.data.detail.props.push({
            key: "省份",
            value: res.data.province
          })
          that.data.detail.props.push({
            key: "城市",
            value: res.data.city
          })
          that.data.detail.props.push({
            key: "AIS",
            value: res.data.ais
          })
          that.data.detail.props.push({
            key: "手机号码",
            value: res.data.phone
          })
        }
        for (var i = 0; i < res.data.images.length; i++) {
          var img = app.config.imageServer + "/api/v1/image/" + res.data.images[i]
          res.data.images[i] = img
        }
        that.data.detail.content = res.data.content
        that.data.detail.images = res.data.images
        that.setData({
          detail: that.data.detail
        })
        wx.hideLoading()
      }
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    return {
      title: this.data.detail.title,
      path: "/pages/detail/detail?id=" + this.data.detail.id
    }
  },
  call: function() {
    var phone = this.data.rawData.phone
    wx.makePhoneCall({
      phoneNumber: phone
    })
  },
  onCommentInput(e) {
    this.data.comment.commentContent = e.detail.value
  },
  sendComment() {
    wx.showLoading({
      title: '发送中',
      mask: true
    })
    var that = this
    if (null == that.data.comment.commentContent ||
      that.data.comment.commentContent.length <= 0) {
      wx.showToast({
        title: '评论内容能为空',
        mask: true
      })
      return
    }
    app.getLoginResult(function(res) {
      var uid = res.uid
      var url = app.config.apiServer + "/api/v1/comment"
      httpJs.request({
        method: "POST",
        url: url,
        data: {
          senderId: uid,
          providerId: that.data.id,
          content: that.data.comment.commentContent,
          commentType: that.data.detailType
        },
        success: function(res) {
          that.data.comment.commentContent = ""
          that.setData({
            comment: that.data.comment
          })
          wx.hideLoading()

          that.data.comment.items = []
          that.data.comment.loadingView = NO_DATA
          that.loadComments(that.data.detailType, that.data.id, 30)
        }
      })
    })
  },
  onReachBottom() {
    if (1 != this.data.activeIndex) {
      return
    }
    this.loadComments(this.data.detailType, this.data.id, 30)
  }
})