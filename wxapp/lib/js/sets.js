class HashSet {

  constructor() {
    this.data = []
  }

  put(val) {
    var find = false
    for (var i = 0; i < this.data.length; i++) {
      var v = this.data[i];
      if (v == val) {
        find = true
        break
      }
    }
    if (!find) {
      this.data.push(val)
    }
  }

  remove(val) {
    for (var i = 0; i < this.data.length; i++) {
      var v = this.data[i];
      if (v == val) {
        this.data.splice(i, 1)
        return
      }
    }
  }

  has(val) {
    for (var i = 0; i < this.data.length; i++) {
      var v = this.data[i];
      if (v == val) {
        return true
      }
    }
  }
}

module.exports = HashSet;