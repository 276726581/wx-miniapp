//app.js

var configJs = require('lib/js/config.js')
var httpTemplateJs = require('lib/js/http.js')
var retryTemplateJs = require('lib/js/retry.js')
var userApiJs = require('lib/js/user.js')
var locationJs = require('lib/js/location.js')

var config = configJs.config
var httpTemplate = httpTemplateJs.httpTemplate
var retryTemplate = retryTemplateJs.retryTemplate
var userApi = userApiJs.userApi
var locationApi = locationJs.locationApi

App({
  config: config,
  httpTemplate: httpTemplate,
  retryTemplate: retryTemplate,
  userApi: userApi,
  locationApi: locationApi,
  globalData: {},
  refreshPage: function(route) {
    var pages = getCurrentPages()
    for (var i = 0; i < pages.length; i++) {
      var page = pages[i]
      if (route == page.route) {
        page.refresh()
      }
    }
  },
  onLaunch: function() {

  }
})