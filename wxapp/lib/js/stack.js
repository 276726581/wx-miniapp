class Stack {

  constructor() {
    this.data = []
  }

  push(val) {
    this.data.push(val)
  }

  pop() {
    this.data.pop()
  }

  getList() {
    return this.data
  }
}

module.exports = Stack;