Page({
  data: {
    images: [],
    showSuccess: false,
    activityTypes: ['义工活动', '计算机', '实习', '家教','文科竞赛','商科竞赛','工科竞赛','理科竞赛'], // 活动类型的选项数组
    selectedValue: '',//活动类型
    selectedDate: '',//日期选择的内容
    uploadedImageCount:0,
    maxImageCount:1,
    permission:"1",
    status:"1",                 
  },

//小队许可
handleRadioChange1: function(e) {
  const selectedValue = e.detail.value;
  this.setData({
    permission: selectedValue
  });
},
//报名许可
handleRadioChange2: function(e) {
  const selectedValue = e.detail.value;
  this.setData({
    status: selectedValue
  });
},

//选择器
pickerChange: function(e) {
  var selectedIndex = e.detail.value;
  var selectedValue = this.data.activityTypes[selectedIndex];
  this.setData({
    selectedValue: selectedValue
  });
},

//日期选择器
handleDateChange(e) {
  const selectedDate = e.detail.value;
  this.setData({
    selectedDate: selectedDate,
  });
},

//活动详情和活动奖励
  handleInput: function(e) {
    const key = e.currentTarget.dataset.key;
    const value = e.detail.value;
    this.setData({
      [key]: value
    });
  },


  //选择图片
  chooseImage: function(e) {
    var that = this;
    wx.chooseImage({
      count: that.data.maxImageCount - that.data.uploadedImageCount, // 限制选择的图片数量为剩余可上传的数量
      success: function(res) {
        const tempFilePaths = res.tempFilePaths;
        const NewImages = that.data.images.concat(tempFilePaths);
  
        console.log(tempFilePaths)
        // 检查总的图片数量是否超过限制
        if (NewImages.length > that.data.maxImageCount) {
          wx.showToast({
            title: '已达到图片数量限制',
            icon: 'none'
          });
          return;
        }
        
    
        // 更新已上传图片数量和图片列表
        that.setData({
          images: NewImages,
          uploadedImageCount: NewImages.length,
        });
  
        
      }
    });
  },
  
  

  deleteImage(e) {
    var imgData=this.data.images;
    imgData.splice(e.detail.index,1);
    this.setData({
      images:imgData
    })
  },

  publish(e) {
    
    //非空判断
    var that = this;
    var images = that.data.images; 
    var name = that.data.name; var organization = that.data.organization; var organizor = that.data.organizor; var selectedDate = that.data.selectedDate; var selectedValue = that.data.selectedValue; var description1 = that.data.description1; var description2 = that.data.description2;

    if (!name || !organization || !organizor || !selectedDate || !selectedValue || !description1 || !description2) {
      wx.showToast({
      title: '请完整填写信息',
      icon: 'error',
      }); 
      return;
    }
    if (images.length === 0) {
      wx.showToast({
        title: '请先选择图片',
        icon: 'none',
      });
      return;
    }

    wx.showLoading({
      title: '上传中...',
      mask: true
    });


      wx.uploadFile({
        url: 'http://121.199.38.35:5000/post/create_act',
        filePath: images[0],
        formData:{
          actname: this.data.name,
          location: this.data.organization,
          organizer: this.data.organizor,
          permission: this.data.permission,
          date: this.data.selectedDate,
          type: this.data.selectedValue,
          introduction: this.data.description1,
          reward: this.data.description2,
          status:this.data.status
        },
        name: "file",
        header: {
          "content-Type": "multipart/form-data",
        },
        success: function(res) {
          console.log('上传数据成功，返回内容是: ' + res.data);
          wx.hideLoading();
          wx.showToast({
            title: '数据上传成功',
            icon: 'success',
            duration: 1000
          });
          console.log(1),
          wx.reLaunch({
            url: '/pages/homepage/homepage',
                success: function() {
                  console.log('跳转成功');
                }, 
                fail: function() {
                  console.log('跳转失败');
                }
              });
            },
        fail: function(res) {
          console.log("上传数据至服务器失败");
          wx.hideLoading();
          wx.showToast({
            title: '数据上传失败',
            icon: 'error',
            duration: 1000
          });
        }
      });
   
  }
})
    
  

 