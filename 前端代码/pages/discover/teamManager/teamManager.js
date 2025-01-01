function refreshData() {
  const openid = wx.getStorageSync('openid');
  wx.request({
    url: 'http://sd.free.idcfengye.com/get/teaminfo',
    method: 'GET',
    data: { openid: openid },
    success: (res) => {
      // 更新数据
      this.setData({
        groups: res.data,
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
    "actname": null,
      "groupname": null,
      "chargename": null,
      "data": null,
      "introduction": null,
      "required": null,
      "current": null,
      "registered":null,
      "tel":null,
      "openid":null
  },
  onLoad(options) {
    refreshData.call(this); // 页面加载时也获取一次数据
    const openid = wx.getStorageSync('openid');
    console.log(openid);
    wx.request({
      url: 'http://sd.free.idcfengye.com/get/teaminfo',
      method: 'GET',
      data: { openid: openid }, // 将openid放在一个对象中作为请求的数据
      success: (res) => {
        this.setData({
          groups: res.data,
        });
      },
      fail: (res) => {
        console.error('获取数据失败', res);
      },
    });
  },
  showActionSheet: function (event) {

    const openid = event.currentTarget.dataset.openid;
    const actname = event.currentTarget.dataset.actname;
    const groupname = event.currentTarget.dataset.groupname;
    console.log(openid);
    console.log(actname);
    console.log(groupname);
    wx.showActionSheet({
      itemList: ['编辑', '删除'],
      success: (res) => {
        if (res.tapIndex === 0) {
          // 处理编辑，跳转到编辑页面
          wx.navigateTo({
            url: '/pages/createTeam/createTeam?openid=1',
          });
          
        } else if (res.tapIndex === 1) {
          // 处理删除，弹出确认框
          wx.showModal({
            title: '确认删除',
            content: '是否确认删除该小组？',
            success: (res) => {
              if (res.confirm) {
                // 调用删除小组的函数
                this.deleteGroup(openid,actname,groupname);
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

  deleteGroup: function (openid,actname,groupname) {
    // 调用删除小组的API，删除数据库中的数据
    console.log(openid);
    console.log(actname);
    console.log(groupname);
    wx.request({
      url: 'http://sd.free.idcfengye.com/delete/teaminfo',
      method: 'DELETE',
      data: {
        actname:actname,
        groupname: groupname,
        openid: openid,
      },
      success: (res) => {
        // 处理删除成功的情况
        console.log('小组删除成功');
        // 更新页面，重新从数据库获取数据
        refreshData.call(this); // 通过 call() 方法确保在函数内部使用正确的 this 上下文
      },
      fail: (res) => {
        console.error('小组删除失败', res);
      },
    }); 
  },
     
  back(){
    wx.switchTab({
      url: '/pages/detail/detail' // 跳转到详情页
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */

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