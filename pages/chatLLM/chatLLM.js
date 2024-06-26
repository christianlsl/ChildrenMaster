// pages/chatLLM/chatLLM.js
import TextDecoder from '../../utils/miniprogram-text-decoder'
const app = getApp();
Page({
    data: {
        isShowFinish: false,
        currentContent: '',
        loading: false,
    },
    onLoad(options) {},
    send(e) {
        let str = ''
        let that = this
        let {
            query,
        } = e.detail.value;
        query = query.trim();
        if (query.slice(-1) !== '?'&&query.slice(-1) !== '？') {
            query += '？';
        }
        console.log(query);
        const requestTask = wx.request({
            url: `http://116.63.180.106:6060/completion`,
            enableChunked: true,
            data: {
                prompt: query,
                n_predict: 512,
                stream: true,
                stop: "<|im_end|>\n<|im_start|>assistant\n"
            },
            header: {
                'content-type': 'application/json' // 默认值
            },
            method: "POST",
            responseType: "arraybuffer",
            timeout: '120000',
            success(res) {
            },
            fail: function (error) {
                // 请求失败
                console.error(error);
            },
            complete: function () {
                console.log('complete: ', str)
                // that.handleRequestResolve(str)
            }
        })
        // 监听服务端返回的数据
        requestTask.onChunkReceived(res => {
            // Uint8Array to string //方案一，只可用于开发工具
            // let arrayBuffer = res.data;
            // let decoder = new TextDecoder('utf-8');
            // let text = decoder.decode(arrayBuffer);
            const arrayBuffer = new Uint8Array(res.data)//方案二，可用于开发工具&真机
            let text = new TextDecoder().decode(arrayBuffer)

            //正则匹配上所有event:data后面的文字
            const eventRegex = /"content":"(.*?)"/g;
            let matches = [];
            let match;
            while ((match = eventRegex.exec(text)) !== null) {
                matches.push(match[1]);
            }
            //处理成字符串
            str = str + matches.join('')
            this.setData({
                currentContent: app.towxml(str.replace(/\\n/g, '\n'),'markdown')
                // currentContent:str.replace(/\\n/g, '\n')
            })
        })
        requestTask.offChunkReceived(res => {})
    },
    handleRequestResolve(result) {
        this.setData({
            currentContent: ''
        })
        const contentCharArr = result.trim().split("")
        this.setData({
            isShowFinish: false
        })
        this.showText(0, contentCharArr);
    },
    showText(key = 0, value) {
        if (key >= value.length) {
            // wx.vibrateShort()
            this.setData({
                isShowFinish: true
            })
            return;
        }
        /* 渲染回话内容 */
        this.setData({
            currentContent: this.data.currentContent + value[key],
        }, () => {
            setTimeout(() => {
                this.showText(key + 1, value);
            }, 20);
        })
    },
    arrayBufferToString(arr) {
        if (typeof arr === 'string') {
            return arr;
        }
        var dataview = new DataView(arr);
        var ints = new Uint8Array(arr.byteLength);
        for (var i = 0; i < ints.length; i++) {
            ints[i] = dataview.getUint8(i);
        }
        var str = '',
            _arr = ints;
        for (var i = 0; i < _arr.length; i++) {
            if (_arr[i]) {
                var one = _arr[i].toString(2),
                    v = one.match(/^1+?(?=0)/);
                if (v && one.length == 8) {
                    var bytesLength = v[0].length;
                    var store = _arr[i].toString(2).slice(7 - bytesLength);
                    for (var st = 1; st < bytesLength; st++) {
                        if (_arr[st + i]) {
                            store += _arr[st + i].toString(2).slice(2);
                        }
                    }
                    str += String.fromCharCode(parseInt(store, 2));
                    i += bytesLength - 1;
                } else {
                    str += String.fromCharCode(_arr[i]);
                }
            }
        }
        return str;
    },
})