// pages/teamManager/editTeam/editTeam.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    groupname:"",
    current:"",
    required:"",
    date:"",
    introduction:"",
    tel:"",
    group:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const group=JSON.parse(decodeURIComponent(options.group));
    console.log(group);
    this.setData({
      groupname: group.groupname,
      current: group.current,
      required: group.required,
      date: group.date,
      introduction: group.introduction,
      tel: group.tel,
      group:group
    });
  },

  inputTeamName(e) {
    this.setData({
      'group.groupname': e.detail.value,
    });
  },

  inputCaptain(e) {
    this.setData({
      'group.current': e.detail.value,
    });
  },

  inputLocation(e) {
    this.setData({
      'group.required': e.detail.value,
    });
  },

  inputTel(e){
    this.setData({
      'group.tel':e.detail.value,
    });
  },

  selectDate(e) {
    this.setData({
      'group.date': e.detail.value,
    });
  },

  inputintroduction(e) {
    this.setData({
      'group.introduction': e.detail.value,
    });
  },

  updateTeamInfo(){
    console.log(this.data.group);
    wx.request({
      url: 'http://121.199.38.35:5000/put/update_group',
      method:'PUT',
      data:this.data.group,
      success:(res) =>{
        console.log("修改成功");
        wx.navigateBack({
          delta: 1, // 返回的页面数，如果是1表示返回上一页面，依此类推
        });
      },
      fail:(error) =>{
        console.log("修改失败");
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