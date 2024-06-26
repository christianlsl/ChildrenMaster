import {
    postRequest
} from '../../utils/request.js';
const app = getApp();
var tokenKey = app.globalData.tokenKey;
Page({
    data: {
        loading: false,
        // accesstoken: "",
        error: ""
    },

    onLoad: function () {

    },

    bindKeyInput: function (e) {
        this.setData({
            accesstoken: e.detail.value
        })
    },

    // 验证token(登录)
    isLogin: function (e) {
        console.log(e.detail.value);
        let {
            email,
            pwd
        } = e.detail.value;
        var that = this;

        that.setData({
            loading: true
        });

        postRequest("/user/login", {
            email: email,
            password: pwd
        }).then(res => {
            console.log("登录成功返回数据:")
            console.log(res)
            if (res.data.success) {
                console.log(res.data.data)
                wx.setStorageSync(tokenKey, res.data.data);
                wx.setStorageSync('email', email);
                wx.showToast({
                    title: '登录成功',
                    icon: 'success',
                    duration: 2000
                })
                wx.switchTab({
                    url: '/pages/learn/index'
                })
            } else {
                that.setData({
                    error: res.data.errorMsg
                });
                that.setData({
                    loading: false
                });
                wx.showToast({
                    title: '登录失败' + that.error,
                    icon: 'error',
                    duration: 2000
                })
            }
        })
    },
    isRegister: function () {
        this.pageRouter.navigateTo({
            url: '../register/register'
        })
    }
})