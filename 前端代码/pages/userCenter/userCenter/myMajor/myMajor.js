
Page({
  data: {
    Major: '', 
  },

  onMajorInput(e) {
    this.setData({
      Major: e.detail.value,
    });
  },
  
  onSaveMajor() {
    // To retrieve the openid from local storage
    const openid = wx.getStorageSync('openid');
    if (openid) {
     // openid is available and can be used
      console.log(openid);
    } else {
    // openid is not set or available
      console.log('openid not found');
    }
    const { Major } = this.data;
    wx.request({
      url: ' http://121.199.38.35:5000/update/myMajor',
      method: 'POST',
      data: {
        Major: Major,
        openid : openid
      },
      success: function(response) {
              // Handle login success
              wx.showToast({
                title: '学院信息已保存',
                icon: 'success',
                duration: 2000,
              });
      },
      fail: function(error) {
              // Handle errors
      }
          
    })


    

    // 保存成功后，可以返回上一页或执行其他操作
    // wx.navigateBack();
  },
});
