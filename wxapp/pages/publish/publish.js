// pages/publish.js

var cityData = require('city.js')
var typeLogicJs = require('type-logic.js')
const app = getApp()

var logic = {}
var touchStart = 0
var touchEnd = 0

Page({

  /**
   * 页面的初始数据
   */
  data: {
    viewType: {},
    goodsTypeSelect: {
      arr: ["请选择", '粗砂', '中砂', '细砂', '石子', '其他'],
      index: 0
    },
    unitSelect: {
      arr: ["请选择", "吨"],
      index: 0
    },
    provinceSelect: {
      arr: [],
      index: 0
    },
    citySelect: {
      arr: [],
      index: 0
    },
    price: null,
    phone: null,
    location: {},
    content: null,
    contentCount: 0,
    imgs: [],
    showChooseImage: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu()

    var that = this
    if ('goods' == options.viewType) {
      logic = typeLogicJs.goods
    } else if ('ship' == options.viewType) {
      logic = typeLogicJs.ship
    } else {
      wx.showToast({
        title: '页面错误',
        success: function() {
          wx.reLaunch()
        }
      })
      return
    }
    that.data.viewType = logic.viewType

    app.locationApi.getLocationInfo(function(res, hide) {
      that.data.location = res.data
      that.setData({
        location: that.data.location
      })
      hide()
    })

    that.data.provinceSelect.arr.push('请选择')
    that.data.citySelect.arr.push('请选择')
    cityData.citys.forEach(function(item) {
      that.data.provinceSelect.arr.push(item.provinceName)
    })
    that.setData({
      viewType: that.data.viewType,
      provinceSelect: that.data.provinceSelect,
      citySelect: that.data.citySelect
    })
  },
  goodsTypeSelectChange: function(e) {
    this.data.goodsTypeSelect.index = e.detail.value
    this.setData({
      goodsTypeSelect: this.data.goodsTypeSelect
    })
  },
  unitSelectChange: function(e) {
    this.data.unitSelect.index = e.detail.value
    this.setData({
      unitSelect: this.data.unitSelect
    })
  },
  provinceSelectChange: function(e) {
    var that = this
    var index = e.detail.value
    var cityList = cityData.citys[index - 1]
    that.data.provinceSelect.index = index

    that.data.citySelect.arr = ['请选择']
    that.data.citySelect.index = 0
    cityList.cityInfoList.forEach(function(item) {
      that.data.citySelect.arr.push(item.name)
    })
    that.setData({
      provinceSelect: that.data.provinceSelect,
      citySelect: that.data.citySelect
    })
  },
  citySelectChange: function(e) {
    var that = this
    var index = e.detail.value
    that.data.citySelect.index = index
    that.setData({
      citySelect: that.data.citySelect
    })
  },
  onInputPrice: function(e) {
    var price = e.detail.value
    this.data.price = price
    this.setData({
      price: this.data.price
    })
  },
  onInputPhone: function(e) {
    var phone = e.detail.value
    this.data.phone = phone
    this.setData({
      phone: this.data.phone
    })
  },
  onInputAis: function(e) {
    var ais = e.detail.value
    this.data.ais = ais
    this.setData({
      ais: this.data.ais
    })
  },
  onInputContent: function(e) {
    var content = e.detail.value
    var len = content.length
    this.data.content = content
    this.setData({
      content: this.data.content,
      contentCount: len
    })
  },
  bindTouchStart: function(e) {
    touchStart = e.timeStamp
  },
  bindTouchEnd: function(e) {
    touchEnd = e.timeStamp
  },
  deleteImage: function(e) {
    var that = this
    wx.showModal({
      content: "是否删除图片",
      showCancel: true,
      cancelColor: "#ff0000",
      success: function(res) {
        if (res.cancel) {
          return
        }
        var index = e.currentTarget.id
        that.data.imgs.splice(index, 1)
        that.data.showChooseImage = (that.data.imgs.length >= 9 ? false : true)
        that.setData({
          imgs: that.data.imgs,
          showChooseImage: that.data.showChooseImage
        })
      }
    })
  },
  previewImage: function(e) {
    var time = touchEnd - touchStart
    if (time > 350) {
      return
    }
    var urls = []
    this.data.imgs.forEach(function(item) {
      urls.push(item.url)
    })
    var currentUrl = urls[e.currentTarget.id]
    wx.previewImage({
      current: currentUrl, // 当前显示图片的http链接
      urls: urls // 需要预览的图片http链接列表
    })
  },
  chooseImage: function(e) {
    var that = this
    wx.chooseImage({
      count: 9 - that.data.imgs.length,
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function(res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        res.tempFilePaths.forEach(function(item) {
          that.data.imgs.push({
            url: item,
            uploadStatus: false
          })
        })
        that.data.showChooseImage = (that.data.imgs.length >= 9 ? false : true)
        that.setData({
          imgs: that.data.imgs,
          showChooseImage: that.data.showChooseImage
        })
      }
    })
  },
  pubish: function() {
    logic.pubish(this.data, function(hide) {
      wx.navigateBack()
    })
  }
})