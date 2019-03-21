let app = getApp(), API = getApp().API
Component({
  properties: {
    // 这里定义了接收一个design对象
    emptyData: {
      type: Object,
      value: {}
    },
    marginTop: {
      type: String,
      value: '250rpx'
    },
  },
  data: {
    // 这里是一些组件内部数据
    resourcePath: app.resourcePath,
  },
  methods: {
    
  }
})