Page({
  data: {
    showSuccess: false,
    selectedDate: '',
    name:'',
    person:'',
    number:'',
    decription:'',
    phone:'',
    actname:'',
    openid:'',
    current:'',
    actid:''
  },

//接受detail页面的actname
onLoad: function(options){
  const actid = options.actid;
  const actname = options.actname;
  this.setData({
    actid: actid,
    actname: actname,
  });
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

    var value = wx.getStorageSync('openid');
    this.setData({
      openid: value,
    })
    //非空判断
    var that = this;
    var groupname = that.data.name;
    var chargename = that.data.person;
    var required = that.data.number;
    var date = that.data.selectedDate;
    var introduction = that.data.decription;
    var tel =that.data.phone;
    var current =that.data.current;

    if(!groupname || !chargename || !required || !date || !introduction || !tel || !current){
      wx.showToast({
        title: '请完整填写信息',
        icon: 'error',
        }); 
        return;
    }

    wx.showLoading({
      title: '上传中...',
      mask: true
    });

    wx.request({
      url: 'http://121.199.38.35:5000/post/create_group',
      method:'POST',
      data:{
        groupname:this.data.name,
        chargename:this.data.person,
        required:this.data.number,
        date:this.data.selectedDate,
        introduction:this.data.decription,
        tel:this.data.phone,
        current:this.data.current,
        openid:this.data.openid,
        actid:this.data.actid,
        actname:this.data.actname
      },
      success: function(res){
        wx.hideLoading();
        wx.showToast({
        title: '发布成功',
        icon: 'success',
        duration: 2000,
        success: function() {
          setTimeout(function() {
            wx.navigateBack({//返回上一层页面
              delta: 1,  // 返回页面的层数，1为上一个页面
            });
          }, 1000);
        }
      });
      },
      fail: function(error) {
        wx.hideLoading();
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