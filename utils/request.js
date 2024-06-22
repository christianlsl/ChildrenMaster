var tokenKey = "userInfo"; // 将登陆凭证储存以key为“token”储存在本地
var serverUrl = "http://47.96.125.121:8080"; 

// 例外不用token的地址
// var exceptionAddrArr = ['/user/login', ];
var exceptionAddrArr = ['/user/login', ];

//请求头处理函数
function CreateHeader(url, type) {
    let header = {}
    if (type == 'POST_PARAMS') {
        header = {
            'content-type': 'application/x-www-form-urlencoded'
        }
    } else {
        header = {
            'content-type': 'application/json'
        }
    }
    if (exceptionAddrArr.indexOf(url) == -1) { //排除请求的地址不须要token的地址
        let token = wx.getStorageSync(tokenKey).token;
        // header.Authorization = token;
        header['authorization'] = token;
    }
    return header;
}

/**
 * 请求拦截器:
 * 在这里实现的作用是将所有请求前判断用户是否授权获取用户信息
 * @param {*} config 
 */
function requestInterceptor(config) {
    console.log("经过了请求拦截器")
    return new Promise((resolve, reject) => {
        if (!config.header.authorization) {
            userLogin().then(res =>{
                if(res){
                    config.header.authorization = wx.getStorageSync('userInfo').token
                    resolve(config);
                }
            })
        } else {
            resolve(config);
        }
    });
}

// 响应拦截器
function responseInterceptor(response) {
    console.log("经过响应拦截器")
    return new Promise((resolve, reject) => {
        // 处理响应结果
        if (response.data.flag) {
            resolve(response);
        } else {
            if (response.data.code === 40001) {
                userLogin().then(res => {
                    reject(response)
                })
            } else {
                wx.showToast({
                    title: response.data.message,
                    icon: "error",
                    duration: 2000
                })
            }
        }
    });
}

/**
 * 封装wx.getUserProfile()方法
 */
function wxGetUserProfile() {
    return new Promise((resolve, reject) => {
        wx.getUserProfile({
            desc: '获取你的昵称、头像、地区及性别',
            success: (res) => {
                let userInfo = {
                    userName: res.userInfo.nickName,
                    iconUrl: res.userInfo.avatarUrl
                }
                wx.setStorageSync('userInfo', userInfo)
                resolve(userInfo)
            },
            fail: (res) => {
                reject(res)
            }
        })
    })
}

/**
 * 封装wx.login
 */
function wxLogin() {
    return new Promise((resolve, reject) => {
        wx.login({
            success: (res) => {
                console.log("wxLogin()获取验证码：" + res.code)
                resolve(res.code)
            },
            fail: (res) => {
                reject(res)
            }
        })
    })
}

/**
 * 封装后端登陆方法
 * @param {验证码} code 
 */
function mpLogin(data) {
    return new Promise((resolve, reject) => {
        wx.request({
            url: serverUrl + '/user/login',
            data: data,
            method: 'POST',
            success: (res => {
                resolve(res.data)
            }),
            fail: (res => {
                reject(res)
            }),
        })
    })
}

/**
 * 调用wx.login 和 mplogin 完成用户后端登陆
 */
async function userLogin() {
    let userInfo = wx.getStorageSync('userInfo');
    if (!userInfo) {
        userInfo = await wxGetUserProfile()
    }
    if(!userInfo){
        return;
    }
    let code = await wxLogin();
    let data = {
        code: code,
        userName: userInfo.userName,
        iconUrl: userInfo.iconUrl
    }
    return new Promise((resolve, reject) => {
        mpLogin(data).then(res => {
            if (res.flag) {
                console.log("userLogin()登陆成功返回信息:" + res)
                wx.setStorageSync('userInfo', res.data)
                resolve(true)
            } else {
                wx.showToast({
                    title: res.message,
                    icon: "error",
                    duration: 2000
                })
                resolve(false)
            }
        })
    })
}


//post请求，数据按照query方式传给后端
/**
 * 
 * @param {请求地址} url 
 * @param {请求数据} data 
 * @param {重试次数} times 
 */
function postRequest(url, data = {}, times) {
    // 获取请求头
    let header = CreateHeader(url, 'POST');
    return new Promise((resolve, reject) => {
        const config = {
            url: serverUrl + url,
            data: data,
            header:  header,
            method: 'POST',
            success: (res => {
                // 对响应统一处理
                responseInterceptor(res)
                    .then(res => {
                        resolve(res.data);
                    }).catch(res => {
                        // 重
                        if (times > 0) {
                            postRequest(url, data, times - 1).then(res => {
                                resolve(res)
                            })
                        } else {
                            wx.showToast({
                                title: '请稍后再试',
                                icon: "loading",
                            })
                        }
                    })
            }),
            fail: (res => {
                reject(res)
            }),
        }
        // 请求拦截器
        requestInterceptor(config)
            .then(config => {
                wx.request(config);
            }).catch(error => {
                reject(error);
            });
    })
}


//get 请求
function getRequest(url, data, times) {
    let header = CreateHeader(url, 'GET');
    return new Promise((resolve, reject) => {
        const config = {
            url: serverUrl + url,
            data: data,
            header: header,
            method: 'GET',
            success: (res => {
                responseInterceptor(res)
                    .then(res => {
                        resolve(res.data);
                    }).catch(res => {
                        // 重
                        if (times > 0) {
                            getRequest(url, data, times - 1).then(res => {
                                resolve(res)
                            })
                        } else {
                            wx.showToast({
                                title: '请稍后再试',
                                icon: "loading",
                            })
                        }
                    })
            }),
            fail: (res => {
                reject(res)
            })
        }
        // 请求拦截器
        requestInterceptor(config)
            .then(config => {
                wx.request(config);
            }).catch(error => {
                reject(error);
            });

    })
}
//put请求
function putRequest(url, data, times) {
    let header = CreateHeader(url, 'PUT');
    return new Promise((resolve, reject) => {
        const config = {
            url: serverUrl + url,
            data: data,
            header: header,
            method: 'PUT',
            success: (res => {
                responseInterceptor(res)
                    .then(res => {
                        resolve(res.data);
                    }).catch(res => {
                        // 重
                        if (times > 0) {
                            putRequest(url, data, times - 1).then(res =>{
                                resolve(res)
                            })
                        } else {
                            wx.showToast({
                                title: '请稍后再试',
                                icon: "loading",
                            })
                        }
                    })
            }),
            fail: (res => {
                reject(res)
            })
        }
    })
}
//delete请求
function deleteRequest(url, data, times) {
    let header = CreateHeader(url, 'DELETE');
    return new Promise((resolve, reject) => {
        const config = {
            url: serverUrl + url,
            data: data,
            header: header,
            method: 'DELETE',
            success: (res => {
                responseInterceptor(res)
                    .then(res => {
                        resolve(res.data);
                    }).catch(res => {
                        if (times > 0) {
                            deleteRequest(url, data, times - 1).then(res =>{
                                resolve(res)
                            })
                        } else {
                            wx.showToast({
                                title: '请稍后再试',
                                icon: "loading",
                            })
                        }
                    })
            }),
            fail: (res => {
                reject(res)
            })
        }
        // 请求拦截器
        requestInterceptor(config)
            .then(config => {
                wx.request(config);
            }).catch(error => {
                reject(error);
            });
    })
}



//导入
module.exports = {
    getRequest: getRequest,
    postRequest: postRequest,
    putRequest: putRequest,
    deleteRequest: deleteRequest,
    userLogin: userLogin,
}
