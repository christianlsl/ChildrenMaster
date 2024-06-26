const app = getApp()
var tokenKey = app.globalData.tokenKey;
import {
    getRequest
} from '../../utils/request.js';
Page({
    data: {
        coverTransform: 'translateY(0)',
        coverTransition: '',
        userInfo: {
            id: 0,
            nick_name: '',
            email: '',
            age: 0,
        },
        recentPlayList: [],
        isLogin: false,
        history: app.globalData.history,
        mathScores: [],
        poemScores: [],
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
    onLoad() {
        let token = wx.getStorageSync(tokenKey);
        console.log(token);
        getRequest("/user/getUserByToken/" + token,'').then(res => {
            console.log("获取userDTO:", res)
            if (res.data.success) {
                this.setData({
                    'userInfo.id': res.data.data.id,
                    'userInfo.nick_name': res.data.data.nick_name,
                    'userInfo.email': res.data.data.email,
                    'userInfo.age': res.data.data.age,
                    isLogin: true
                })
            } else {
                console.log("获取userDTO失败：", res.data.errorMsg)
            }
        })
    },

    logout: function () {
        wx.showModal({
            title: '提示',
            content: '真的要退出了吗',
            confirmColor: '#000000',
            cancelColor: '#576b95',
            success(res) {
                if (res.confirm) {
                    wx.removeStorageSync(tokenKey)
                    wx.reLaunch({
                        url: '/pages/login/login',
                    })
                } else if (res.cancel) {
                    console.log('用户点击取消')
                }
            }
        })
    },
    
    fetchScores() {
    Promise.all([
      getRequest('/user/math/score'),
      getRequest('/user/poem/score')
    ]).then(([mathRes, poemRes]) => {
      this.setData({
        mathScores: mathRes.data,
        poemScores: poemRes.data
      });
      this.generateScoreCharts();
    }).catch((err) => {
      console.error(err);
      wx.showModal({
        title: '错误',
        content: '获取数据失败',
        showCancel: false
      });
    });
  },
  
  generateScoreCharts() {
    const { mathScores, poemScores } = this.data;
    
    // 示例代码（使用 wx-charts 库）
    new wxCharts({
      canvasId: 'mathScoreCanvas',
      type: 'line',
      categories: mathScores.map((_, index) => `第${index + 1}次`),
      series: [{
        name: 'Math 准确率',
        data: mathScores.map(score => score * 100),
        format: (val) => val.toFixed(2) + '%'
      }],
      yAxis: {
        title: '准确率 (%)',
        min: 0,
        max: 100
      },
      width: 320,
      height: 200
    });
    
    new wxCharts({
      canvasId: 'poemScoreCanvas',
      type: 'line',
      categories: poemScores.map((_, index) => `第${index + 1}次`),
      series: [{
        name: 'Poem 准确率',
        data: poemScores.map(score => score * 100),
        format: (val) => val.toFixed(2) + '%'
      }],
      yAxis: {
        title: '准确率 (%)',
        min: 0,
        max: 100
      },
      width: 320,
      height: 200
    });
  }
})