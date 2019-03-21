Component({
  properties: {
    // 这里定义了innerText属性，属性值可以在组件使用时指定
    issBindingMobile: {
      type: Boolean,
      value: false
    }
  },
  data: {
    // 这里是一些组件内部数据
  },
  lifetimes: {
    // 生命周期函数，可以为函数，或一个在methods段中定义的方法名
    attached: function () { },
    moved: function () { },
    detached: function () { },
  },
  pageLifetimes: {
    // 组件所在页面的生命周期函数
    show: function () {
      console.log(1);
    },
  },
  methods: {
    // 这里是一个自定义方法
    shut(e) {
      this.triggerEvent('myevent', e.target.dataset.type);
    }
  }
})