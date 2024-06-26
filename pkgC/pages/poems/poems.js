const { getRequest, postRequest } = require('../../../utils/request.js');

Page({
  data: {
    poemList: [],
    currentIndex: 0,
    score: 0,
    accuracy: 0,
    poemOptions: [],
    isAnswered: false,
    timeLeft: 60, // 60 seconds
    timer: null,
  },

  onLoad: function () {
    this.loadPoems();
    this.startTimer();
  },

  onUnload: function () {
    clearInterval(this.data.timer);
  },

  loadPoems: function () {
    getRequest('/user/poem/kid', {})
      .then((res) => {
        if (res.data.length > 0) {
          this.setData({
            poemList: res.data,
            currentIndex: 0,
            poemOptions: res.data[0].poemOptions.split(',')
          });
        } else {
          this.showErrorModal("No data received");
        }
      })
      .catch((err) => {
        this.showErrorModal(err.errMsg);
      });
  },

  selectAnswer: function (e) {
    if (this.data.isAnswered) return;

    const answer = String.fromCharCode(65 + e.currentTarget.dataset.answer);
    const correctAnswer = this.data.poemList[this.data.currentIndex].answer;

    this.setData({
      isAnswered: true
    });

    if (answer === correctAnswer) {
      this.setData({
        score: this.data.score + 1
      });
      this.showCorrectAnswer();
    } else {
      this.showIncorrectAnswer(correctAnswer);
    }
  },

  showCorrectAnswer: function () {
    wx.showToast({
      title: '回答正确!',
      icon: 'success',
      duration: 500
    });

    setTimeout(() => {
      this.showNextQuestion();
    }, 500);
  },

  showIncorrectAnswer: function (correctAnswer) {
    wx.showModal({
      title: '回答错误',
      content: `正确答案是 ${correctAnswer}`,
      showCancel: false,
      success: (res) => {
        if (res.confirm) {
          this.showNextQuestion();
        }
      }
    });
  },

  showNextQuestion: function () {
    const nextIndex = this.data.currentIndex + 1;
    if (nextIndex < this.data.poemList.length) {
      this.setData({
        currentIndex: nextIndex,
        poemOptions: this.data.poemList[nextIndex].poemOptions.split(','),
        isAnswered: false
      });
    } else {
      this.calculateAccuracy();
      this.saveAccuracyRecord();
    }
  },

  calculateAccuracy: function () {
    const accuracy = ((this.data.score / this.data.poemList.length) * 100).toFixed(2);
    this.setData({
      accuracy: accuracy
    });
  },

  saveAccuracyRecord: function () {
    postRequest('/user/poem/record', {
      correctRate: this.data.accuracy / 100
    })
    .then(() => {
    })
    .catch((err) => {
      this.showErrorModal(err.errMsg);
    });
  },


  showErrorModal: function (errorMessage) {
    wx.showModal({
      title: '错误',
      content: errorMessage,
      showCancel: false
    });
  },

  startTimer: function () {
    this.setData({
      timeLeft: 60,
      timer: setInterval(() => {
        this.setData({
          timeLeft: this.data.timeLeft - 1
        });
        if (this.data.timeLeft === 0) {
          clearInterval(this.data.timer);
          this.calculateAccuracy();
          this.saveAccuracyRecord();
        }
      }, 1000)
    });
  },
});
