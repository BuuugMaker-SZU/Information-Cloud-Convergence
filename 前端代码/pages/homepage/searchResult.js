// pages/homepage/searchResult.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    searchText: '',// 搜索关键字
    activities: [],// 初始化为空数组，存放与搜索关键词相关的活动信息
  },

   // 点击搜索框跳转到搜索页面
   searchJump: function () {
    wx.navigateTo({
      url: '/pages/homepage/search',
      animationType: "none",
    });
  },
  goToDetailPage: function (event) {
    var activity = event.currentTarget.dataset.activity;
    wx.navigateTo({
      url: '/pages/detail/detail?activity=' + activity.actid
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
      // 获取搜索关键字
      var searchText=options.searchText;
      var that=this;
      console.log("打印搜索关键字"+searchText);
      // 发送GET请求到后端服务器
      wx.request({
        url: 'http://121.199.38.35:5000/homepage/search',
        method:'GET',
        data: {
          keyword: searchText
        },
        success: function (res) {
          console.log('搜索数据传输成功', res.data);
            // 请求成功，处理返回的数据
            const updatedActivities = res.data.results.map(activity => {
              return {
                ...activity,
                imgsrc: activity.imgsrc ? `data:image/png;base64,${activity.imgsrc}` : null
              };
            });
            that.setData({
              activities: updatedActivities,
              searchText: searchText,
            });
            console.log("搜索更新后的活动数据"+that.activities);
        },
        fail: function (error) {
          console.error('数据传输失败', error);
          // 可以在失败回调中处理失败情况，例如提示用户重新尝试等
        }

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