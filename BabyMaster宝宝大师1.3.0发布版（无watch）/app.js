// app.js

App({
  globalData: {
    history: []
  },
    onLaunch() {
        wx.cloud.init({
            env: 'lsl-9g4aoh8je342d9be'
        })
    }
})