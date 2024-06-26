const request = require('../../../utils/request.js');

Page({
  data: {
    started: false,
    finished: false,
    viewingMistakes: false,
    difficulty: null,
    problems: [],
    currentQuestion: null,
    currentQuestionIndex: 0,
    inputAnswer: '',
    score: 0,
    mistakes: [],
    previousAccuracy: 0,
    recentScores: [],
  },

  onLoad: function () {
    this.fetchRecentScores();
  },

  selectDifficulty: function (e) {
    const level = e.currentTarget.dataset.level;
    this.setData({ difficulty: level });
    this.startQuiz(level);
  },

  startQuiz: function (level) {
    request.getRequest(`/user/math/${level}`)
      .then(res => {
        this.setData({
          problems: res.data.problems,
          started: true,
          currentQuestionIndex: 0,
          currentQuestion: res.data.problems[0],
          inputAnswer: '',
          score: 0,
          mistakes: [],
          finished: false,
        });
      })
      .catch(err => {
        console.error(err);
      });
  },

  inputChange: function (e) {
    this.setData({
      inputAnswer: e.detail.value
    });
  },

  submitAnswer: function () {
    const { problems, currentQuestionIndex, inputAnswer, score, mistakes } = this.data;
    const currentProblem = problems[currentQuestionIndex];
    const userAnswer = parseInt(inputAnswer, 10);
    let newScore = score;

    if (userAnswer === currentProblem.answer) {
      newScore += 1;
    } else {
      mistakes.push({
        question: currentProblem.question,
        answer: currentProblem.answer,
        yourAnswer: userAnswer
      });
    }

    const nextIndex = currentQuestionIndex + 1;
    if (nextIndex < problems.length) {
      this.setData({
        currentQuestionIndex: nextIndex,
        currentQuestion: problems[nextIndex],
        inputAnswer: '',
        score: newScore,
        mistakes: mistakes
      });
    } else {
      this.finishQuiz(newScore, problems.length, mistakes);
      this.setData({
        finished: true
      });
    }
  },

  finishQuiz: function (score, total, mistakes) {
    const accuracy = (score / total).toFixed(2);
    this.saveAccuracyRecord(accuracy);

    this.setData({
      accuracy: accuracy,
      mistakes: mistakes
    });

    if (accuracy > this.data.previousAccuracy) {
      wx.showModal({
        title: '恭喜',
        content: '你的准确率提高了！继续加油！',
        showCancel: false
      });
    }

    this.fetchRecentScores();
  },

  saveAccuracyRecord: function (accuracy) {
    request.postRequest('/user/math/record', { correctRate: accuracy })
      .then(res => {
        console.log('准确率记录已保存');
      })
      .catch(err => {
        console.error(err);
      });
  },

  fetchRecentScores: function () {
    request.getRequest('/user/math/score')
      .then(res => {
        this.setData({
          recentScores: res.data,
          previousAccuracy: res.data.length > 0 ? res.data[res.data.length - 1] : 0
        });
        this.generateScoreChart();
      })
      .catch(err => {
        console.error(err);
      });
  },

  generateScoreChart: function () {
    const scores = this.data.recentScores;
    // 这里使用微信小程序的图表库生成折线图，例如 wx-charts 或其他图表库
    // 示例代码如下（使用 wx-charts 库）：
    new wxCharts({
      canvasId: 'scoreCanvas',
      type: 'line',
      categories: scores.map((_, index) => `第${index + 1}次`),
      series: [{
        name: '准确率',
        data: scores.map(score => score * 100),
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
  },

  viewMistakes: function () {
    this.setData({
      viewingMistakes: true
    });
  },

  goBack: function () {
    this.setData({
      viewingMistakes: false
    });
  }
});
