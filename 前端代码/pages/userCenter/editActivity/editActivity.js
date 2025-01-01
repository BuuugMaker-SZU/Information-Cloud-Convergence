// pages/userCenter/editActivity/editActivity.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    images:[],
    actname:"",
    actid:"",
    type:"",
    location:"",
    date:"",
    organizer:"",
    introduction:"",
    reward:"",
    imgsrc:"",
    permission:"1",
    status:"1",
    file:"",
    group:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const group=JSON.parse(decodeURIComponent(options.group));
    this.setData({
      actname:group.actname,
      actid:group.actid,
      type:group.type,
      location:group.location,
      date:group.date,
      organizer:group.organizer,
      introduction:group.introduction,
      reward:group.reward,
      imgsrc:group.imgsrc,
      permission:group.permission,
      status:group.status,
      group:group,
      typeIndex: 0,
      typeArray: ['实习', '义工活动', '竞赛','计算机','家教'],
    });
  },

  onTypeChange: function (event) {
    const newIndex = event.detail.value;
    this.setData({
      typeIndex: newIndex,
    });
  },

  chooseMedia: function () {
    let that = this;
    wx.chooseImage({
      count: 1,
      success: function (res) {
        const tempFilePaths = res.tempFilePaths;
        console.log(tempFilePaths)
        that.setData({
          images: tempFilePaths,
          imgsrc: tempFilePaths,
        });
      },
      fail: function (error) {
        console.error('Failed to choose image:', error);
      }
    });
  },
  

  // 活动名称输入事件处理函数
  inputActName(e) {
    this.setData({
      actname: e.detail.value
    });
  },

  // 活动地点输入事件处理函数
  inputLocation(e) {
    this.setData({
      location: e.detail.value
    });
  },

  // 主办单位输入事件处理函数
  inputOrganizer(e) {
    this.setData({
      organizer: e.detail.value
    });
  },

  // 活动介绍输入事件处理函数
  inputintroduction(e) {
    this.setData({
      introduction: e.detail.value
    });
  },

  // 活动类型选择事件处理函数
  onTypeChange(e) {
    this.setData({
      typeIndex: e.detail.value
    });
  },

  // 活动日期选择事件处理函数
  selectDate(e) {
    this.setData({
      date: e.detail.value
    });
  },

  // 活动奖励输入事件处理函数
  inputReward(e) {
    this.setData({
      reward: e.detail.value
    });
  },

  updateTeamInfo: function () {
    console.log(this.data);
    var that = this;
    var images = that.data.images;
    console.log(images);
    
    // 判断文件是否为空
    if (images.length > 0) {
      // 上传文件
      wx.uploadFile({
        filePath: images[0],
        name: 'file',
        url: 'http://121.199.38.35:5000/update/act',
        formData: {
          actname: this.data.actname,
          actid: this.data.actid,
          date: this.data.date,
          location: this.data.location,
          type: this.data.type,
          organizer: this.data.organizer,
          reward: this.data.reward,
          status: this.data.status,
          permission: this.data.permission,
          introduction: this.data.introduction,
        },
        header: {
          "content-Type": "multipart/form-data",
        },
        success:function(res) {
          console.log('修改成功'+res.data);
          wx.hideLoading();
          wx.showToast({
            title: '活动修改成功',
            icon: 'success',
            duration: 1000
          });
          wx.navigateBack({
            delta: 1,
            success: function() {
              console.log("成功")
            }
          });
        },
        fail: function(res){
          console.log("活动修改失败"+res.errMsg);
          wx.hideLoading();
          wx.showToast({
            title: '活动修改失败',
            icon: 'error',
            duration: 1000
          });
        }
      });
    } else {
      console.log(this.data.actname);
      // 不上传文件，只传递其他表单数据
      wx.request({
        url: 'http://121.199.38.35:5000/update/act1',
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded' // 修改头部信息
        },
        data:`actname=${this.data.actname}&actid=${this.data.actid}&date=${this.data.date}&location=${this.data.location}&type=${this.data.type}&organizer=${this.data.organizer}&reward=${this.data.reward}&status=${this.data.status}&permission=${this.data.permission}&introduction=${this.data.introduction}`,
        success: function(res) {
          console.log('修改成功'+res.data);
          wx.hideLoading();
          wx.showToast({
            title: '活动修改成功',
            icon: 'success',
            duration: 1000
          });
          wx.navigateBack({
            delta: 1,
            success: function() {
              console.log("成功")
            }
          });
        },
        fail: function(res){
          console.log("活动修改失败"+res.errMsg);
          wx.hideLoading();
          wx.showToast({
            title: '活动修改失败',
            icon: 'error',
            duration: 1000
          });
        }
      });
    }
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