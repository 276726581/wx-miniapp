// pages/test/test.js

const NO_DATA = 0
const HAS_MORE = 1
const NO_MORE = 2

Page({

  /**
   * 页面的初始数据
   */
  data: {
    loadingView: NO_DATA,
    scrollViewHeight: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this
    var query = wx.createSelectorQuery();
    wx.getSystemInfo({
      success: function(res) {
        var windowHeight = res.windowHeight
        query.select('#header')
          .fields({
            size: true
          }, function(res) {
            var headerHeight = res.height
            that.data.scrollViewHeight = windowHeight - headerHeight
            console.log(that.data.scrollViewHeight)
            that.setData({
              scrollViewHeight: that.data.scrollViewHeight
            })
          }).exec()
      }
    });
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

  },

  clickIndex() {
    wx.navigateTo({
      url: "/pages/index/index"
    })
  },
  clickdetail() {
    wx.navigateTo({
      url: "/pages/detail/detail"
    })
  },
  scroll(e) {
    console.log(e)
  },
  touchstart(e) {
    console.log(e)
  },
  touchend(e) {
    console.log(e)
  },
  touchmove(e) {
    console.log(e)
  },
  scrolltoupper(e) {
    console.log(e)
  },
  onScrolltolower() {
    console.log('onScrolltolower')
    this.onLoadMore()
  },
  onClick(e) {
    console.log('onClick')
  },
  onLoadMore() {

  },

})