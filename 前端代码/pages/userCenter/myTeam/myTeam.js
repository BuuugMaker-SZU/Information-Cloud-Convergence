// pages/userCenter/myTeam/myTeam.js
function refreshData() {
  const openid = wx.getStorageSync('openid');
  wx.request({
    url: 'http://121.199.38.35:5000/getRegGroup',
    method: 'GET',
    data: { openid: openid },
    success: (res) => {
      // 更新数据
      this.setData({
        groups: res.data.results,
      });
    },
    fail: (res) => {
      console.error('获取数据失败', res);
    },
  });
}


Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    refreshData.call(this); // 页面加载时也获取一次数据
    const openid = wx.getStorageSync('openid');
    // var openid=1;
    console.log(openid);
    wx.request({
      url: 'http://121.199.38.35:5000/getRegGroup',
      method: 'GET',
      data: { openid: openid }, // 将openid放在一个对象中作为请求的数据
      success: (res) => {
        this.setData({
          groups: res.data.results,
        });
        console.log(this.data.groups);
      },
      fail: (res) => {
        console.error('获取数据失败', res);
      },
    });
  },

  showActionSheet: function (event) {
    const group=event.currentTarget.dataset;
    const groupid=group.groupid;
    const openid = wx.getStorageSync('openid');
    console.log(groupid);
    console.log(openid);
    wx.request({
      url: 'http://121.199.38.35:5000/cancelsign',
      method:'POST',
      header: {
        'content-type': 'application/json', // 指定请求头为 JSON
      },
      data:{
        groupid:groupid,
        openid:openid
      },
      success:(res)=>{
        console.log('退出成功');
        refreshData.call(this);
      },
      fail:(res)=>{
        console.log(res);
      }
    })
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