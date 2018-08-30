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
    collectManageUrl: null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu()
    this.loadUserInfo()
  },
  refresh() {
    this.loadUserInfo()
  },
  loadUserInfo() {
    var that = this
    app.userApi.login(function(res) {
      that.data.userInfo = res.userInfo
      that.setData({
        userInfo: that.data.userInfo
      })
      wx.hideLoading()
    })
    this.data.goodsManageUrl = "/pages/manage/manage"
    this.data.goodsManageUrl += "?viewType=goods"

    this.data.shipManageUrl = "/pages/manage/manage"
    this.data.shipManageUrl += "?viewType=ship"

    this.data.collectManageUrl = "/pages/collect/collect"

    this.setData({
      goodsManageUrl: this.data.goodsManageUrl,
      shipManageUrl: this.data.shipManageUrl,
      collectManageUrl: this.data.collectManageUrl
    })
  }
})