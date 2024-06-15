Page({
  data: {
    phonetic_hidden2: false,
    alphabet: [
      { name: "A", phonetic: '[eɪ]' },
      { name: "B", phonetic: '[bi:]' },
      { name: "C", phonetic: '[si:]' },
      { name: "D", phonetic: '[di:]' },
      { name: "E", phonetic: '[i:]' },
      { name: "F", phonetic: '[ef]' },
      { name: "G", phonetic: '[dʒi:]' },
      { name: "H", phonetic: '[eɪtʃ]' },
      { name: "I", phonetic: '[aɪ]' },
      { name: "J", phonetic: '[dʒeɪ]' },
      { name: "K", phonetic: '[keɪ]' },
      { name: "L", phonetic: '[el]' },
      { name: "M", phonetic: '[em]' },
      { name: "N", phonetic: '[en]' },
      { name: "O", phonetic: '[əʊ]' },
      { name: "P", phonetic: '[pi:]' },
      { name: "Q", phonetic: '[kju:]' },
      { name: "R", phonetic: '[ɑː]' },
      { name: "S", phonetic: '[es]' },
      { name: "T", phonetic: '[ti:]' },
      { name: "U", phonetic: '[ju:]' },
      { name: "V", phonetic: '[vi:]' },
      { name: "W", phonetic: '[ˋdʌblju:]' },
      { name: "X", phonetic: '[eks]' },
      { name: "Y", phonetic: '[waɪ]' },
      { name: "Z", phonetic: '[ziː]' }
    ]
  },
  
  phonetic_hidden() {
    var phonetic_hidden2 = this.data.phonetic_hidden2;
    this.setData({
      phonetic_hidden2: !phonetic_hidden2
    });
  },
  
  audioPlay(e) {
    console.log(e);
    var letter = e.currentTarget.dataset.url;
    console.log(letter);
    var charCode = letter.charCodeAt(0);
    console.log(charCode);
    let audio = wx.createInnerAudioContext();
    audio.src = this.generateAudioSrc(charCode);
    audio.play();
  },
  
  generateAudioSrc(charCode) {
    const basePath = "pkgA/pages/abc/phonitic/";
    const fileName = `w${String(charCode - 64).padStart(2, '0')}.mp3`;
    return basePath + fileName;
  }
});
