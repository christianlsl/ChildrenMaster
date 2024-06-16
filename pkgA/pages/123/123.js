var apple,apple2;
var digit;
var character='add';
function create_apple(){
  apple=[];
  var n=digit;
  console.log(n);
  for(var i=1;i<=n;i++){
    console.log(i);
    apple.push("./img/apple.png");
  }
}
function create_apple2(){
  apple2=[];
  var n=digit;
  for(var i=1;i<=n;i++){
    console.log(i);
    apple2.push("./img/apple.png");
  }
}
Page({
  data:{
    num:[
      { name:1,imgurl:"../123/img/1.png"},
      {name:2,imgurl:"../123/img/2.png"},
      {name:3,imgurl:"../123/img/3.png"},
      {name:4,imgurl:"../123/img/4.png"},
      {name:5,imgurl:"../123/img/5.png"},
      {name:6,imgurl:"../123/img/6.png"},
      {name:7,imgurl:"../123/img/7.png"},
      {name:8,imgurl:"../123/img/8.png"},
      {name:9,imgurl:"../123/img/9.png"}
    ],
    character_src:"../123/img/add.png"
  },

  num_change(e){
    digit=parseInt(e.currentTarget.dataset.digit);
    create_apple();
    this.setData({
      apple:apple
    })
  },
  num_change2(e){
    digit=parseInt(e.currentTarget.dataset.digit);
    create_apple2();
    this.setData({
      apple2:apple2
    })
  },
  ansSubmit(e){
    var ans=parseInt(e.detail.value.ans);
    console.log(apple.length+apple2.length);
    console.log(ans);
    if(character=="add"){
      if(ans==(apple.length+apple2.length)){
        wx.showToast({
          title: '回答正确！',
          icon:'success',
          mask:true
        })
        wx.vibrateShort();
      }else{
        wx.showToast({
          title: '仔细思考一下哦',
          icon:'error',
          mask:true
        })
        wx.vibrateShort();
      }
    }else{
      if(ans==(apple.length-apple2.length)){
        wx.showToast({
          title: '回答正确！',
          icon:'success',
          mask:true
        })
        wx.vibrateShort();
      }else{
        wx.showToast({
          title: '仔细思考一下哦',
          icon:'error',
          mask:true
        })
        wx.vibrateShort();
      }
    }
  },
  change_add(){
    character="add";
    console.log(character);
    this.setData({
      character_src:"../123/img/add.png"
    })
  },
  change_minus(){
    character="minus";
    console.log(character);
    this.setData({
      character_src:"../123/img/minus.png"
    })
  }
  
})