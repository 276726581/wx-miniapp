// pages/detail/detail.js

var detailData = require('../json/detail.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    detail: {
      imgs: []
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    console.log("id: " + options.id)
    let that = this
    wx.showLoading({
      title: "加载中",
      mask: true
    })
    wx.request({
      url: 'http://www.baidu.com?id=' + options.id,
      success: function(res) {
        console.log(detailData.json)
        that.data.detail = detailData.json
        that.setData({
          detail: that.data.detail
        })
        wx.hideLoading()
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    return {
      title: this.data.detail.title,
      path: "/pages/detail/detail?id=" + this.data.detail.id
    }
  }
})