// profile.js
Page({
  data: {
    avatar: '', // 默认头像
    nickname: 'lla',
    genderPickerVisible: false, // 控制性别选择器的显示状态
    genderOptions: ['男', '女', '其他'], // 性别选项
    genderIndex: 0, // 默认选择第一项 
  },

  // 头像点击事件
  changeAvatar() {
    const userInfo = wx.getStorageSync('userInfo');
    wx.chooseImage({
      count: 1,
      success: (res) => {
        const tempFilePath = res.tempFilePaths[0];
        this.setData({
          avatar: tempFilePath
        });
        // 可在此处将新头像数据上传到服务器
      }
    });
  },

  // 名字输入事件
  inputName(e) {
    this.setData({
      name: e.detail.value,
      openid : openid
    });
    // 可在此处将新名字数据上传到服务器
  },
    // 点击性别一行时显示性别选择器

  showGenderPicker() {
    this.setData({
      genderPickerVisible: true,
    });
  },
  
    // 处理性别选择器确认事件
  onGenderConfirm(e) {
    const selectedGender = e.detail.value;
    this.setData({
      genderIndex: selectedGender,
      genderPickerVisible: false,
    });
      // 处理性别选择后的逻辑，可以保存到数据或上传到服务器
  },

  
    // 关闭性别选择器
  hideGenderPicker() {
    this.setData({
      genderPickerVisible: false,
    });
  },



 



  
   

  
});
