var app = getApp()

var methods = {
  updateImage: function(uploadUrl, imgs, index, callback) {
    var that = this
    if (index >= imgs.length) {
      //已经上传完
      callback(imgs)
      return
    }

    var imgInfo = imgs[index]
    if (true == imgInfo.uploadStatus) {
      //本张上传完
      that.updateImage(imgs, index + 1, callback)
      return
    }

    app.httpTemplate.uploadFile({
      url: uploadUrl,
      filePath: imgInfo.url,
      name: "img",
      success: function(res, hide) {
        imgInfo.uploadStatus = true
        imgInfo.resultName = res.data
        hide()
        that.updateImage(uploadUrl, imgs, index + 1, callback)
      }
    })
  },
  updateImages: function(uploadUrl, imgs, callback) {
    this.updateImage(uploadUrl, imgs, 0, function(imgs) {
      callback(imgs)
    })
  },
  setContent: function(data, pubishData) {
    if (null == data.content || '' == data.content) {
      wx.showToast({
        title: "未填写描述信息",
        mask: true
      })
      return false
    }
    pubishData.content = data.content
    return true
  },
  setPhone: function(data, pubishData) {
    if (null == data.phone || '' == data.phone) {
      wx.showToast({
        title: "未填写手机号码",
        mask: true
      })
      return false
    }
    pubishData.phone = data.phone
    return true
  },
  setAis: function(data, pubishData) {
    if (null == data.ais || '' == data.ais) {
      wx.showToast({
        title: "未填写编号(AIS)",
        mask: true
      })
      return false
    }
    pubishData.ais = data.ais
    return true
  },
  setUnit: function(data, pubishData) {
    if (0 == data.unitSelect.index) {
      wx.showToast({
        title: "单位未选择",
        mask: true
      })
      return false
    }
    pubishData.unit = data.unitSelect.arr[data.unitSelect.index]
    return true
  },
  setPrice: function(data, pubishData) {
    if (null == data.price || '' == data.price) {
      wx.showToast({
        title: "未填写价格",
        mask: true
      })
      return false
    }
    pubishData.price = data.price
    return true
  },
  setGoodsType: function(data, pubishData) {
    if (0 == data.goodsTypeSelect.index) {
      wx.showToast({
        title: "分类未选择",
        mask: true
      })
      return false
    }
    pubishData.goodsType = data.goodsTypeSelect.arr[data.goodsTypeSelect.index]
    return true
  }
}

var goods = {
  viewType: {
    goodsType: true,
    price: true,
    unit: true,
    province: false,
    city: false,
    phone: true,
    ais: false,
    location: true,
    content: true,
    imgs: true,
    pubishUrl: app.config.apiServer + "/api/v1/goods",
    uploadImageUrl: app.config.imageServer + "/api/v1/image/upload"
  },
  checkSetData: function(data, pubishData) {
    if (!methods.setGoodsType(data, pubishData)) {
      return false
    }
    if (!methods.setPrice(data, pubishData)) {
      return false
    }
    if (!methods.setUnit(data, pubishData)) {
      return false
    }
    if (!methods.setPhone(data, pubishData)) {
      return false
    }
    if (!methods.setContent(data, pubishData)) {
      return false
    }
    return true
  },
  pubish: function(data, callback) {
    var that = this
    var pubishData = {
      images: []
    }
    if (!this.checkSetData(data, pubishData)) {
      return
    }

    app.retryTemplate.execute(function(hide, retry) {
      methods.updateImages(that.viewType.uploadImageUrl, data.imgs, function(imgs) {
        data.imgs.forEach(function(item) {
          pubishData.images.push(item.resultName)
        })
        pubishData.uid = app.globalData.uid
        pubishData.province = data.location.province
        pubishData.city = data.location.city
        pubishData.lat = data.location.lat
        pubishData.lng = data.location.lng
        pubishData.address = data.location.address
        wx.request({
          method: "POST",
          url: that.viewType.pubishUrl,
          data: pubishData,
          success: function(res) {
            app.refreshPage("pages/manage/manage")
            app.refreshPage("pages/goods/goods")
            callback(hide)
          },
          fail: retry
        })
      })
    })
  }
}

var ship = {
  viewType: {
    price: true,
    unit: true,
    province: false,
    city: false,
    phone: true,
    ais: true,
    location: true,
    content: true,
    imgs: true,
    pubishUrl: app.config.apiServer + "/api/v1/ship",
    uploadImageUrl: app.config.imageServer + "/api/v1/image/upload"
  },
  checkSetData: function(data, pubishData) {
    if (!methods.setPrice(data, pubishData)) {
      return false
    }
    if (!methods.setUnit(data, pubishData)) {
      return false
    }
    if (!methods.setPhone(data, pubishData)) {
      return false
    }
    if (!methods.setAis(data, pubishData)) {
      return false
    }
    if (!methods.setContent(data, pubishData)) {
      return false
    }
    return true
  },
  pubish: function(data, callback) {
    var that = this
    var pubishData = {
      images: []
    }
    if (!this.checkSetData(data, pubishData)) {
      return
    }

    app.retryTemplate.execute(function(hide, retry) {
      methods.updateImages(that.viewType.uploadImageUrl, data.imgs, function(imgs) {
        data.imgs.forEach(function(item) {
          pubishData.images.push(item.resultName)
        })
        pubishData.uid = app.globalData.uid
        pubishData.province = data.location.province
        pubishData.city = data.location.city
        pubishData.lat = data.location.lat
        pubishData.lng = data.location.lng
        pubishData.address = data.location.address
        wx.request({
          method: "POST",
          url: that.viewType.pubishUrl,
          data: pubishData,
          success: function(res) {
            app.refreshPage("pages/manage/manage")
            app.refreshPage("pages/ship/ship")
            callback(hide)
          },
          fail: retry
        })
      })
    })
  }
}

module.exports = {
  goods,
  ship
}