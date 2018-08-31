// components/card/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    index: Number,
    data: Object,
    showDelete: {
      type: Number,
      value: 0
    },
    showPhone: {
      type: Number,
      value: 1
    }
  },
  /**
   * 组件的初始数据
   */
  data: {},
  /**
   * 组件的方法列表
   */
  methods: {
    onTap() {
      let id = this.properties.data.id
      wx.navigateTo({
        url: "/pages/detail/detail?detailType=goods&id=" + id
      })
    },
    onCall() {
      this.triggerEvent('onCall', {
        data: this.properties.data
      })
    },
    onDelete() {
      this.triggerEvent('onDelete', {
        index: this.properties.index,
        data: this.properties.data
      })
    },
    previewImage(e) {
      var urls = []
      console.log(this.properties.data)
      this.properties.data.images.forEach(function(url) {
        urls.push(url)
      })
      var currentUrl = urls[e.currentTarget.id]
      wx.previewImage({
        current: currentUrl, // 当前显示图片的http链接
        urls: urls // 需要预览的图片http链接列表
      })
    },
    showLocation() {
      var that = this
      wx.openLocation({
        latitude: Number(that.data.data.lat),
        longitude: Number(that.data.data.lng),
        address: that.data.data.address
      })
    }
  }
})