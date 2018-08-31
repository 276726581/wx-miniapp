//app.js

var configJs = require('lib/js/config.js')
var httpTemplateJs = require('lib/js/http.js')
var retryTemplateJs = require('lib/js/retry.js')
var userApiJs = require('lib/js/user.js')
var locationJs = require('lib/js/location.js')
var Stack = require('lib/js/stack.js')

var config = configJs
var httpTemplate = httpTemplateJs
var retryTemplate = retryTemplateJs
var userApi = userApiJs
var locationApi = locationJs
var pageStack = new Stack()

App({
  config: config,
  httpTemplate: httpTemplate,
  retryTemplate: retryTemplate,
  userApi: userApi,
  locationApi: locationApi,
  isLogin: false,
  globalData: {},
  pageStack: pageStack,
  refreshPage: function(route) {
    var pages = pageStack.getList()
    for (var i = 0; i < pages.length; i++) {
      var page = pages[i]
      if (route == page.route) {
        page.refresh()
      }
    }
  },
  concatList(list, items) {
    items.forEach(function(item) {
      list.push(item)
    })
  },
  onLaunch: function() {

  }
})