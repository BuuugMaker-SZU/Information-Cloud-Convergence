const app = getApp();
Page({
  data: {
    localUserInfo: {},
    nickName: '', 
  },
  onLoad() {
    this.setData({
      localUserInfo: app.globalData.userInfo,
    });
  },

  onNickNameInput(e) {
    // 用户输入联系电话时触发，将输入的值保存到数据中
    this.setData({
      nickName: e.detail.value,
    });
  },
  
  onSaveNickName() {
    // 用户点击保存按钮时触发，保存联系电话到后端服务器或本地存储
    this.setData({
      'localUserInfo.nickName': this.data.nickName,
    });
    
    // To retrieve the openid from local storage
    const openid = wx.getStorageSync('openid');
    if (openid) {
     // openid is available and can be used
      console.log(openid);
    } else {
    // openid is not set or available
      console.log('openid not found');
    }
    const { nickName } = this.data;
    wx.request({
      url: 'http://121.199.38.35:5000/update/name', // Replace with your server endpoint
      method: 'POST',
      data: {
        name : nickName,
        openid : openid
      },
      success: function(res) {
        wx.showToast({
          title: '昵称已保存',
          icon: 'success',
          duration: 2000,
        });
        // Additional success handling
      },
      fail: function(error) {
        // Handle errors
      }
    });


    // 保存成功后，可以返回上一页或执行其他操作
    // wx.navigateBack();
  },
});
