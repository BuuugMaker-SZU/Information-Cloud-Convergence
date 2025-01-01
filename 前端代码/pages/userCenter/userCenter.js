// pages/userCenter/userCenter.js
const app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    localUserInfo: {},
    hasUserInfo: false,
    showMakePhone: false,
    serviceTimeDuration: '每周一至周五 9:00-12:00 14:00-17:00',
    administrator: 0,
    currentUser: null,
  },
  getUserinfo() 
  {
    const openid = wx.getStorageSync('openid');
    wx.request({
      url: 'http://121.199.38.35:5000/get/userinfo', // 用于获取用户信息的服务器接
      data: {
        openid: openid
      },
      method: 'GET',
      success: (res) => {
        var userInfo = res.data;
        console.log(userInfo);
        this.setData({
          'localUserInfo.nickName': userInfo.results.name,
          'localUserInfo.avatarUrl': userInfo.results.imgSrc ? `data:image/png;base64,${userInfo.results.imgSrc}` : null,
          'administrator':userInfo.results.administrator,
        });

        const picture = 'http://121.199.38.35:5000/get_avatar?openid=' + openid;
        // 打开goeasy
        const user = {
          id: openid,
          name: app.globalData.userInfo.nickName,
          password: '123',
          avatar: picture,
          phone: app.globalData.userInfo.phoneNumber,
          email: 'Tracy@goeasy.io',
        };
        app.globalData.currentUser = user;
        this.setData({
          currentUser: app.globalData.currentUser
        });
        console.log("Connection Status:", wx.goEasy.getConnectionStatus());

        if (wx.goEasy.getConnectionStatus() === 'disconnected') {
          this.connectGoEasy();  //连接goeasy
        }
      },
      fail: function (error) {
      }
    });
    wx.request({
      url: 'http://121.199.38.35:5000/get/extra_user_Info', // 用于获取用户信息的服务器接
      data: {
        openid: openid
      },
      method: 'GET',
      success: (res) => {
        var userInfo = res.data;
        console.log(userInfo);
        this.setData({
          'localUserInfo.nickName': userInfo.results.name,
          
          'localUserInfo.gender': userInfo.results.gender,
          'localUserInfo.phoneNumber': userInfo.results.tel,
          'localUserInfo.weixinID': userInfo.results.wechat,
          'localUserInfo.Major': userInfo.results.major,
          'localUserInfo.introduction': userInfo.results.biography,
          administrator: userInfo.results.administrator,
        });
      },
      fail: function (error) {
      }
    });
    this.setData({
      hasUserInfo: true,
    });
  },


  onLogin() {
    const that = this;
    wx.login({
      success(res) {
        if (res.code) {
          wx.request({
            url: 'https://api.weixin.qq.com/sns/jscode2session',
            data: {
              appid: 'wx80455b40095c86df',
              secret: 'f47bbb37bb14d6c9dd536e8bbda3d9ab',
              js_code: res.code,
              grant_type: 'authorization_code'
            },
           
            success(res) {
              const openid = res.data.openid;
              wx.setStorageSync('openid', openid);
              app.globalData.userInfo.openid = openid;
              that.getUserinfo();
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg);
          wx.showToast({
            title: '加载失败',
            icon: 'none'
          });

        }
      }
    });
    
  },
  
  connectGoEasy() {
    wx.goEasy.connect({
      id: this.data.currentUser.id,
      data: {
        name: this.data.currentUser.name,
        avatar: this.data.currentUser.avatar
      },
      onSuccess: () => {
        console.log('GoEasy connect successfully.')
      },
      onFailed: (error) => {
        console.log('Failed to connect GoEasy, code:' + error.code + ',error:' + error.content);
      },
      onProgress: (attempts) => {
        console.log('GoEasy is connecting', attempts);
      }
    });
  },

  bindViewTap() {
    wx.navigateTo({
      url: '/pages/userCenter/userInfo/edit_info',
    })
  },


  bindServiceTap() {
    this.setData({ showMakePhone: true });
  },
  closeMakePhone() {
    this.setData({ showMakePhone: false });
  },
  call() {
    wx.makePhoneCall({
      phoneNumber: '8888888',
    });
  },

  bindManangerTap() {
    wx.navigateTo({
      url: '/pages/userCenter/activityManager/activityManager',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      localUserInfo: app.globalData.userInfo,
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    wx.setNavigationBarTitle({
      title: '信息云汇'
    })
    wx.setNavigationBarColor({
      backgroundColor: '#ffffff',
      frontColor: '#000000',
    })
    this.setData({
      localUserInfo: app.globalData.userInfo,
    });


  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})