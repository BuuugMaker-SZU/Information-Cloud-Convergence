Page({
  data: {
    
    phoneNumber: '', // 存储联系电话
  },

  onPhoneNumberInput(e) {
    // 用户输入联系电话时触发，将输入的值保存到数据中
    this.setData({
      phoneNumber: e.detail.value,
    });
  },
  
  onSavePhoneNumber() {
    // 用户点击保存按钮时触发，保存联系电话到后端服务器或本地存储
    const { phoneNumber } = this.data;
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
      url: 'http://121.199.38.35:5000/update/tel', // Replace with your server endpoint
      method: 'POST',
      data: {
        phoneNumber: phoneNumber,
        openid : openid
      },
      success: function(res) {
        wx.showToast({
          title: '联系电话已保存',
          icon: 'success',
          duration: 2000,
        });
        // Additional success handling
      },
      fail: function(error) {
        // Handle errors
      }
    });

    // 执行保存联系电话的逻辑，可以与后端交互或使用本地存储
    // 例如：将联系电话发送到服务器保存
    // 请根据你的需求自行添加保存逻辑


    // 保存成功后，可以返回上一页或执行其他操作
    // wx.navigateBack();
  },
});
