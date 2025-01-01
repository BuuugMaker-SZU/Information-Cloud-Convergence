// pages/userCenter/activityManager/activityManager.js
function refreshData() {
  var that = this;
  // 获取活动数据
      wx.request({
        url: 'http://121.199.38.35:5000/get/activity',
        method: 'GET',
        success: function (res) {
          console.log(res.data);
          const updatedActivities = res.data.results.map(activity => {
            return {
              ...activity,
              imgsrc: activity.imgsrc ? `data:image/png;base64,${activity.imgsrc}` : null
            };
          });
          that.setData({
            activities: updatedActivities
          });
        },
        fail: function (error) {
          console.error(error);
          wx.showToast({
            title: '数据请求失败，请重试',
            icon: 'none',
            duration: 2000
          });
          reject(error); // 活动数据获取失败
        },
      });
}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    "actname": null,
    "organizer": null,
    "date": null,
    "introduction": null,
    "type": null,
    "location": null,
    "reward":null,
    "permission":null,
    "status":null,
    "showactivities":null
  },
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    refreshData.call(this); // 页面加载时也获取一次数据
    var that = this;
    // 获取活动数据
        wx.request({
          url: 'http://121.199.38.35:5000/get/activity',
          method: 'GET',
          success: function (res) {
            console.log(res.data);
            const updatedActivities = res.data.results.map(activity => {
              return {
                ...activity,
                imgsrc: activity.imgsrc ? `data:image/png;base64,${activity.imgsrc}` : null
              };
            });
            that.setData({
              activities: updatedActivities
            });
          },
          fail: function (error) {
            console.error(error);
            wx.showToast({
              title: '数据请求失败，请重试',
              icon: 'none',
              duration: 2000
            });
            reject(error); // 活动数据获取失败
          },
        });
  },

  switchChange: function (event) {
    const actid = event.currentTarget.dataset.actid;
    console.log(actid);
    wx.request({
      url: 'http://121.199.38.35:5000/update/carousel?actid='+actid,
      method:'POST',
      success:function(res){
        console.log(res);
      },
      fail:function(res){
        console.log(res);
      }
    })
  },

  showActionSheet: function (event) {
    const activities=event.currentTarget.dataset;
    console.log(activities);
    wx.showActionSheet({
      itemList: ['编辑', '删除'],
      success: (res) => {
        if (res.tapIndex === 0) {
          // 处理编辑，跳转到编辑页面
          wx.navigateTo({
            url: `/pages/userCenter/editActivity/editActivity?group=${encodeURIComponent(JSON.stringify(activities))}`,
          });
        } else if (res.tapIndex === 1) {
          // 处理删除，弹出确认框
          wx.showModal({
            title: '确认删除',
            content: '是否确认删除该活动？',
            success: (res) => {
              if (res.confirm) {
                // 调用删除小组的函数
                this.deleteGroup(activities.actid);
              }
            },
          });
        }
      },
      fail: (res) => {
        console.log(res.errMsg);
      },
    });
  },

  deleteGroup: function (actid) {
    // 调用删除小组的API，删除数据库中的数据
    console.log(actid);
    wx.request({
      url: 'http://121.199.38.35:5000/delete/activity?actid='+actid,
      method: 'DELETE',
      success: (res) => {
        // 处理删除成功的情况
        console.log('活动删除成功');
        // 更新页面，重新从数据库获取数据
        refreshData.call(this); // 通过 call() 方法确保在函数内部使用正确的 this 上下文
      },
      fail: (res) => {
        console.error('活动删除失败', res);
      },
    }); 
  },

  navigateToAnotherPage: function () {
    wx.navigateTo({
      url: '/pages/create/create',
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
    refreshData.call(this);
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