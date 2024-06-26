// app.js

App({
    towxml:require('./towxml/index'),
    globalData: {
        history: [],
        tokenKey: "userInfo"
    },
    onLaunch() {
        wx.cloud.init({
            env: 'lsl-9g4aoh8je342d9be'
        })
    }
})