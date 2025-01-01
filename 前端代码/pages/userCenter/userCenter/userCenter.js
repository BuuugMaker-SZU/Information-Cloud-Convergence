// pages/userCenter/userCenter.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUseGetUserProfile: true,
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName'),// 如需尝试获取用户信息可改为false
    userPhone:'',
    showMakePhone: false,
    serviceTimeDuration:'每周一至周五 9:00-12:00 14:00-17:00',
    showManager: false,
  },
  getUserProfile() {
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
              console.log(openid);
              wx.setStorageSync('openid', openid); 
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })

    wx.getUserProfile({
      desc: '用于完善会员资料', // Reason for requesting profile
      success: (res) => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        });
        // You can also send this data to your server here if needed
      },
      fail: () => {
        // Handle failure
      }
    });

    wx.request({
      url: ' http://sd.free.idcfengye.com/get/administrator', // Replace with your server endpoint
      method: 'GET',
      data: {
        openid: openid
      },

      success: function(res) {
        this.data ({
          showManager: res.administrator,
        }),
        console.log(showManager);    
          // Additional success handling
      },
      fail: function(error) {
          // Handle errors
      }   
    })
  },
  
  bindViewTap() {
    wx.navigateTo({
      url: './person-info/index'
    })
  },

  bindServiceTap(){
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

  bindManangerTap(){
    wx.navigateTo({
      url: 'pages/create/create',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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