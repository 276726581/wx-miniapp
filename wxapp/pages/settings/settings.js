// pages/settings/settings.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: null,
    goodsManageUrl: null,
    shipManageUrl: null,
    collectManageUrl: null,
    isShow: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.loadUserInfo()
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
    if (this.data.isShow) {
      return
    }
    this.loadUserInfo()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {
    this.data.isShow = false
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

  },
  loadUserInfo() {
    var that = this
    app.getLoginResult(function(res) {
      that.data.userInfo = res.userInfo
      that.setData({
        userInfo: that.data.userInfo
      })
    })
    this.data.goodsManageUrl = "/pages/manage/manage"
    this.data.goodsManageUrl += "?viewType=goods"

    this.data.shipManageUrl = "/pages/manage/manage"
    this.data.shipManageUrl += "?viewType=ship"
    this.setData({
      goodsManageUrl: this.data.goodsManageUrl,
      shipManageUrl: this.data.shipManageUrl
    })
  }
})