import {
    putRequest,
    postParamsRequest
} from '../../utils/request.js';
import WxValidate from "../../utils/WxValidate.js";
const app = getApp();
var tokenKey = app.globalData.tokenKey;
Page({
    data: {
        loading: false,
        error: "",
        checkEmail: "",
    },

    onLoad: function () {
        this.initValidate() //验证规则函数
    },
    sendCode: function (e) {
        console.log(e.detail.value);
        let {
            email,
        } = e.detail.value;
        var that = this;

        this.setData({
            loading: true,
            checkEmail: email
        });

        postParamsRequest("/user/code", {
            email: email,
        }).then(res => {
            console.log(res)
            if (res.data.success) {
                wx.showToast({
                    title: '发送成功',
                    icon: 'success',
                    duration: 2000
                })
                that.setData({
                    loading: false
                })
            } else {
                that.setData({
                    error: res.errorMsg
                });
                this.setData({
                    loading: false
                });
                wx.showToast({
                    title: '发送失败' + that.error,
                    icon: 'error',
                    duration: 2000
                })
            }
        })
    },
    //报错信息 
    showModal(error) {
        wx.showModal({
            content: error.msg,
            showCancel: false,
        })
    },
    forgetPwd: function (e) {
        console.log(e.detail.value);
        let {
            code,
            email,
            password,
        } = e.detail.value;
        var that = this;
        if(email!=that.checkEmail){
            that.showModal("邮箱有变动！")
        }
        // 传入表单数据，调用验证方法
        if (!this.WxValidate.checkForm(e.detail.value)) {
            const errorMsg = this.WxValidate.errorList[0]
            that.showModal(errorMsg)
        } else {
            that.setData({
                loading: true
            });
            that.setData({
                error: ''
            });
            putRequest("/user/resetPasswd", {
                email: email,
                password: password,
                code: code,
            }).then(res => {
                console.log("修改返回数据:")
                console.log(res)
                if (res.data.success) {
                    console.log(res.data.data)
                    wx.setStorageSync(tokenKey, res.data.data);
                    wx.setStorageSync('email', email);
                    wx.showToast({
                        title: '修改成功',
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
                        title: '修改失败',
                        icon: 'error',
                        duration: 2000
                    })
                }
            })
        }
    },
    initValidate() {
        //规则
        const rules = {
            password: {
                required: true,
                minlength: 6,
                maxlength: 16,
            },
            checkPassword: {
                required: true,
                equalTo: 'password',
            },
        }

        //返回信息
        const messages = {
            password: {
                required: '请填写密码',
                minlength: '密码长度不能少于6位',
                maxlength: '密码长度不能超过16位'
            },
            checkPassword: {
                required: '请填写确认密码',
                equalTo: '两次输入的密码不一致'
            },
        }
        this.WxValidate = new WxValidate(rules, messages)
    }
})