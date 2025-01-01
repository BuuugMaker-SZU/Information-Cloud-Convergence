Page({
  data: {
    showSuccess: false,
    selectedDate: '',
    name:'',
    person:'',
    number:'',
    decription:''
  },

  //日期选择器
  handleDateChange(e) {
    const selectedDate = e.detail.value;
    this.setData({
      selectedDate: selectedDate,
    });
  },

  //输入
  handleInput: function(e) {
    const key = e.currentTarget.dataset.key;
    const value = e.detail.value;
    this.setData({
      [key]: value
    });
  },


//发布
  publish: function() {
    wx.request({
      url: 'http://sd.free.idcfengye.com/post/create_group',
      method:'POST',
      data:{
        groupname:this.data.name,
        person:this.data.person,
        number:this.data.number,
        date:this.data.date,
        introduction:this.data.decription,
      },
      success: function(res){
        wx.showToast({
        title: '发布成功',
        icon: 'success',
        duration: 2000,
        success: function() {
          setTimeout(function() {
            wx.navigateTo({
              url: "/pages/detail/detail",
            })
          }, 1000);
        }
      });
      },
      fail: function(error) {
        console.log(error);
        wx.showToast({
          title: '发布失败',
          icon:'error',
          duration:2000,
        })
      }
  })
}
}) 