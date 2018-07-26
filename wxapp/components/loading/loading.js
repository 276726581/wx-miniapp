// components/loading/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    hasMore: {
      type: Boolean,
      value: true,
      observer: function(newVal, oldVal, changedPath) {
        this.onLoadMoreChange(newVal)
      }
    },
    show: {
      type: Boolean,
      value: false,
      observer: function(newVal, oldVal, changedPath) {
        this.onshowChange(newVal)
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    loadingText: "加载中",
    showLoadingIcon: true
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoadMoreChange(status) {
      if (true == status) {
        this.data.loadingText = "加载中"
        this.data.showLoadingIcon = true
        this.setData({
          loadingText: this.data.loadingText,
          showLoadingIcon: this.data.showLoadingIcon
        })
      } else {
        this.data.loadingText = "没有更多了"
        this.data.showLoadingIcon = false
        this.setData({
          loadingText: this.data.loadingText,
          showLoadingIcon: this.data.showLoadingIcon
        })
      }
    },
    onshowChange(status) {
      console.log(status)
    }
  }
})