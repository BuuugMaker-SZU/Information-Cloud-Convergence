// pages/teamManager/teamMemberManager/teamMemberManager.js
function refreshData() {
  const groupid=this.data.groupID;
  wx.request({
    url: 'http://121.199.38.35:5000/getUserByGroupid?groupid='+groupid,
    method: 'GET',
    success: (res) => {
      // 更新数据
      this.setData({
        list: res.data.results,
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
    groupID:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const groupid=options.groupid;
    console.log(groupid);
    wx.request({
      url: 'http://121.199.38.35:5000/getUserByGroupid?groupid='+groupid,
      method:'GET',
      success:(res)=>{
        console.log(res);
        this.setData({
          list:res.data.results,
          groupID:groupid
        })
      }
    })
  },

  showActionSheet: function (event) {
    const group=event.currentTarget.dataset;
    const groupid=this.data.groupID;
    const openid=group.openid;
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
        console.log(res);
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