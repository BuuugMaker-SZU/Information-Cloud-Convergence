// app.js
import GoEasy from './static/lib/goeasy-2.6.6.esm.min';

App({
  globalData: {
    userInfo: {
      openid:'',
      nickName:'游客同学捏',
      avatarUrl:'/images/homepage/Activity-book.png',
      phoneNumber:'1',
      weixinID:'',
      Major:'',
      introduction:'',
      gender:'保密',
    },

    //测试对话插件
    currentUser: null,
  },
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    //测试对话插件
    wx.goEasy = GoEasy.getInstance({
      host:'hangzhou.goeasy.io',//应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
      appkey: 'BC-a5d24e165cff4eafbf9d42009b01c9d8',// common key
      modules:['im']
  });
  wx.GoEasy = GoEasy;

  }
})
