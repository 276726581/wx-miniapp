// pages/publish.js

var cityData = require('city.js')
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    props: {},
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
    content: null,
    contentCount: 0,
    imgs: [],
    showChooseImage: true,
    touchStart: 0,
    touchEnd: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.hideShareMenu()

    var that = this
    that.data.props.goodsType = "true" == options.goodsType ? true : false
    that.data.props.price = "true" == options.price ? true : false
    that.data.props.unit = "true" == options.unit ? true : false
    that.data.props.province = "true" == options.province ? true : false
    that.data.props.city = "true" == options.city ? true : false
    that.data.props.phone = "true" == options.phone ? true : false
    that.data.props.ais = "true" == options.ais ? true : false
    that.data.props.content = "true" == options.content ? true : false
    that.data.props.imgs = "true" == options.imgs ? true : false
    that.data.props.pubishUrl = options.pubishUrl
    that.data.props.uploadImageUrl = options.uploadImageUrl
    console.log(that.data.props)

    that.data.provinceSelect.arr.push('请选择')
    that.data.citySelect.arr.push('请选择')
    cityData.citys.forEach(function(item) {
      that.data.provinceSelect.arr.push(item.provinceName)
    })
    that.setData({
      props: that.data.props,
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
  bindTouchStart: function(e) {
    this.data.touchStart = e.timeStamp
  },
  bindTouchEnd: function(e) {
    this.data.touchEnd = e.timeStamp
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
    var time = this.data.touchEnd - this.data.touchStart
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
  pubish: function() {
    var that = this
    wx.showLoading({
      title: "发布中",
      mask: true
    })
    var failCall = function() {
      wx.hideLoading()
      wx.showModal({
        content: '发布失败请重试',
        showCancel: false,
        success: function() {
          that.pubish()
        }
      })
    }
    this.updateImages(this.data.imgs, function(imgs) {
      var pubishData = {
        images: []
      }
      if (!that.setGoodsType(pubishData)) {
        return
      }
      if (!that.setPrice(pubishData)) {
        return
      }
      if (!that.setUnit(pubishData)) {
        return
      }
      if (!that.setProvince(pubishData)) {
        return
      }
      if (!that.setCity(pubishData)) {
        return
      }
      if (!that.setPhone(pubishData)) {
        return
      }
      if (!that.setAis(pubishData)) {
        return
      }
      if (!that.setContent(pubishData)) {
        return
      }
      that.data.imgs.forEach(function(item) {
        pubishData.images.push(item.resultName)
      })
      pubishData.uid = app.globalData.uid
      console.log(pubishData)
      wx.request({
        method: "POST",
        url: that.data.props.pubishUrl,
        data: pubishData,
        success: function(res) {
          wx.hideLoading()
          var pages = getCurrentPages()
          var prevPage = pages[pages.length - 2]
          console.log(prevPage)
          prevPage.refresh()
          wx.navigateBack()
        },
        fail: failCall
      })
    }, failCall)
  },
  setContent: function(pubishData) {
    if (false == this.data.props.content) {
      return true
    }
    if (null == this.data.content || '' == this.data.content) {
      wx.showToast({
        title: "未填写描述信息",
        mask: true
      })
      return false
    }
    pubishData.content = this.data.content
    return true
  },
  setAis: function(pubishData) {
    if (false == this.data.props.ais) {
      return true
    }
    if (null == this.data.ais || '' == this.data.ais) {
      wx.showToast({
        title: "未填写编号(AIS)",
        mask: true
      })
      return false
    }
    pubishData.ais = this.data.ais
    return true
  },
  setPhone: function(pubishData) {
    if (false == this.data.props.phone) {
      return true
    }
    if (null == this.data.phone || '' == this.data.phone) {
      wx.showToast({
        title: "未填写手机号码",
        mask: true
      })
      return false
    }
    pubishData.phone = this.data.phone
    return true
  },
  setCity: function(pubishData) {
    if (false == this.data.props.city) {
      return true
    }
    if (0 == this.data.citySelect.index) {
      wx.showToast({
        title: "城市未选择",
        mask: true
      })
      return false
    }
    pubishData.city = this.data.citySelect.arr[this.data.citySelect.index]
    return true
  },
  setProvince: function(pubishData) {
    if (false == this.data.props.province) {
      return true
    }
    if (0 == this.data.provinceSelect.index) {
      wx.showToast({
        title: "省份未选择",
        mask: true
      })
      return false
    }
    pubishData.province = this.data.provinceSelect.arr[this.data.provinceSelect.index]
    return true
  },
  setUnit: function(pubishData) {
    if (false == this.data.props.unit) {
      return true
    }
    if (0 == this.data.unitSelect.index) {
      wx.showToast({
        title: "单位未选择",
        mask: true
      })
      return false
    }
    pubishData.unit = this.data.unitSelect.arr[this.data.unitSelect.index]
    return true
  },
  setPrice: function(pubishData) {
    if (false == this.data.props.price) {
      return true
    }
    if (null == this.data.price || '' == this.data.price) {
      wx.showToast({
        title: "未填写价格",
        mask: true
      })
      return false
    }
    pubishData.price = this.data.price
    return true
  },
  setGoodsType: function(pubishData) {
    if (false == this.data.props.goodsType) {
      return true
    }
    if (0 == this.data.goodsTypeSelect.index) {
      wx.showToast({
        title: "分类未选择",
        mask: true
      })
      return false
    }
    pubishData.goodsType = this.data.goodsTypeSelect.arr[this.data.goodsTypeSelect.index]
    return true
  },
  updateImages: function(imgs, callback) {
    var that = this
    that.updateImage(that.data.imgs, 0, function(imgs) {
      callback(imgs)
    }, function(res) {
      wx.showToast({
        title: "上传图片失败",
        mask: true
      })
    })
  },
  updateImage: function(imgs, index, successCall, errorCall) {
    var that = this
    if (index >= imgs.length) {
      //已经上传完
      successCall(imgs)
      return
    }

    var imgInfo = imgs[index]
    if (true == imgInfo.uploadStatus) {
      //本张上传完
      that.updateImage(imgs, index + 1, successCall)
      return
    }

    console.log("uploadImage: " + imgInfo.url)
    wx.uploadFile({
      url: that.data.props.uploadImageUrl,
      filePath: imgInfo.url,
      name: "img",
      success: function(res) {
        imgInfo.uploadStatus = true
        imgInfo.resultName = res.data
        that.updateImage(imgs, index + 1, successCall)
      },
      fail: function(res) {
        errorCall(res)
      }
    })
  }
})