// pages/userCenter/weixinID/edit_weixinID.js
Page({
  data: {
    weixinID: '', 
  },

  onWeixinIdInput(e) {
    this.setData({
      weixinID: e.detail.value,
    });
  },
  
  onSaveWeixinId() {
    const { weixinID } = this.data;
    // To retrieve the openid from local storage
    const openid = wx.getStorageSync('openid');
    if (openid) {
     // openid is available and can be used
      console.log(openid);
    } else {
    // openid is not set or available
      console.log('openid not found');
    }
  
    wx.request({
      url: ' http://121.199.38.35:5000/update/weixinID', // Replace with your server endpoint
      method: 'POST',
      data: {
        weixinId: weixinID,
        openid : openid
      },
      success: function(res) {
        wx.showToast({
          title: '微信号已保存',
          icon: 'success',
          duration: 2000,
        });
        // Additional success handling
      },
      fail: function(error) {
        // Handle errors
      }
    });
  }
  

});
