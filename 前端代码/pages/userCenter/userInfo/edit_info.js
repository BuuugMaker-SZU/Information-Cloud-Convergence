// profile.js
const app = getApp();
Page({
  data: {
    localUserInfo: {},
    gender: null, // 0: 男, 1: 女, 2: 保密
    genderText: '',
    newAvatarUrl:'',
  },
  onLoad() {
    this.setData({
      localUserInfo: app.globalData.userInfo,
    });
  },
  onShow: function() {
    // 页面出现在前台时执行
    this.setData({
      localUserInfo: app.globalData.userInfo,
    });
  },

  // 头像点击事件
changeAvatar() {
  const that = this;
  const openid = wx.getStorageSync('openid');
  wx.chooseImage({
    count: 1, // 选择图片的数量
    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
    success: function (res) {
      // 返回选定照片的本地文件路径列表 tempFilePaths
      var tempFilePaths = res.tempFilePaths;
      wx.showLoading({
        title: '上传中',
      });

      wx.uploadFile({
        url: 'http://121.199.38.35:5000/update/avatar', // 服务器接口地址
        filePath: tempFilePaths[0],
        header: {
          "content-Type": "multipart/form-data",
        },
        name: 'file',
        formData: {
          openid: openid
        },
        success: (uploadRes) => {
          // 上传成功后，更新本地头像
          that.setData({
            'localUserInfo.avatarUrl': tempFilePaths[0]
          });
          wx.hideLoading();
          // 可以处理服务器返回的响应，如果需要
        },
        fail: (error) => {
          // 错误处理
          wx.hideLoading();
          wx.showToast({
            title: '上传失败',
            icon: 'error',
            duration: 2000
          });
        }
      });   
    }
  });
},


  

  

  showGenderPicker: function() {
    const that = this;
    wx.showActionSheet({
      itemList: ['男', '女', '保密'],
      success: (res) => {
        if (!res.cancel) {
          // res.tapIndex 是用户点击的按钮序号，从上到下的顺序，从0开始
          let genderText = ['男', '女', '保密'][res.tapIndex];
          that.setData({
          genderText: genderText,
          });
        }
        const { genderText } = this.data;
        const openid = wx.getStorageSync('openid');
        wx.request({
          url: 'http://121.199.38.35:5000/update/gender', // Replace with your server endpoint
          method: 'POST',
          data: {
            gender: genderText,
            openid : openid
          },
          success: function(res) {
            wx.showToast({
              title: '已保存',
              icon: 'success',
              duration: 2000,
            });
            that.setData({
              'localUserInfo.gender': genderText,
            });
            // Additional success handling
          },
          fail: function(error) {
            // Handle errors
          }
        });


      }
    });
  }

});
