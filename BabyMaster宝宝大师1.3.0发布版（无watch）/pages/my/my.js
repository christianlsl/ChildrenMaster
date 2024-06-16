const app = getApp()
Page({
  data: {
    coverTransform: 'translateY(0)',
    coverTransition: '',
    userInfo: {},
    recentPlayList: [],
    isLogin: false,
    history: app.globalData.history
  },
  getUserProfile(e) {
    wx.getUserProfile({
      desc: '用于完善用户资料',
      success: (res) => {
        this.setData({
          userInfo: res.userInfo,
          isLogin: true
        })
      }
    })
  },
  onShow() {
    console.log(app.globalData.history)
    this.setData({
      history: app.globalData.history
    })
  },
  logout: function () {
    wx.removeStorage({
      key: 'userInfo',
      success(res) {
        wx.showModal({
          title: '提示',
          content: '真的要退出了吗',
          confirmColor: '#000000',
          cancelColor: '#576b95',
          success(res) {
            if (res.confirm) {
              wx.reLaunch({
                url: '/pages/learn/index',
              })
            } else if (res.cancel) {
              console.log('用户点击取消')
            }
          }
        })
      }
    })
  }

})