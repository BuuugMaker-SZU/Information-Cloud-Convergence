
Page({
  data: {
    Major: '', 
    majors: ['软件工程','计算机科学与技术', '机械工程', '经济学', '心理学', '医学'], // 示例专业列表
    selectedMajor: '' ,// 选中的专业
  },
  onLoad: function() {
    // 页面加载时从全局变量获取电话号码
    const app = getApp();
    if (app.globalData.userInfo.Major) {
      this.setData({
        Major: app.globalData.userInfo.Major
      });
    }
  },

  onMajorInput(e) {
    this.setData({
      Major: e.detail.value,
    });
  },

  onMajorChange: function(e) {
    this.setData({
      selectedMajor: this.data.majors[e.detail.value],
      Major: this.data.majors[e.detail.value],
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
      url: 'http://121.199.38.35:5000/update/myMajor',
      method: 'POST',
      data: {
        major: Major,
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

    const app = getApp();
    app.globalData.userInfo.Major = this.data.Major;


    

    // 保存成功后，可以返回上一页或执行其他操作
    // wx.navigateBack();
  },
});
