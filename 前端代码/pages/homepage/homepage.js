// pages/homepage/homepage.js

Page({
  /**
   * 页面的初始数据
   */
  data: {
    searchText: '搜索活动',
    allActivities: [], // 保存原始全部活动数据
    activities: [],    // 用于在页面上展示的活动数据
    bannerList: [],//轮播图图片数组
    showTypeView: false,
    showTimeView: false,
    showHostView: false,
    showNumberView: false,
    activityTypes: ["不限", "义工活动", "计算机", "实习", "家教", "文科竞赛", "商科竞赛", "工科竞赛", "理科竞赛"],
    timeTypes: ["不限", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
    hostTypes: ["不限", "学校", "协会"],
    numberTypes: ["不限", "0-5", "5-10", "10-15", "15-20", "20以上"],
    typeTitleBackgroundColor: '', // 活动类型颜色
    typeTitleClicked: false, // 活动类型状态
    selectedItems: ["不限"], // 活动类型选择的标签组 
    TimeselectedItems: ["不限"], // 开始时间选择的标签组 
    HostselectedItems: ["不限"], // 主办单位选择的标签组 
    NumberselectedItems: ["不限"], // 参与人数选择的标签组 
    customIncludesResult: false,

  },



  
  // 点击搜索框跳转到搜索页面
  searchJump: function () {
    // 检测goeasy是否连接
    if (wx.goEasy.getConnectionStatus() === 'disconnected') {
      // 如果goeasy检测未连接，显示一个弹框提示用户先登录
      wx.showModal({
        title: '提示',
        content: '请先登录',
        showCancel: false, // 移除取消按钮
        confirmText: '确定',
        success: function (res) {
          // 根据需要在这里添加额外的逻辑
          wx.switchTab({
            url: '/pages/userCenter/userCenter',
            success: function () {
              console.log('跳转成功');
            },
            fail: function (error) {
              console.log('跳转失败', error);
            }
          })
        }
      });
    } else {
      // 如果 goeasy连接成功，继续跳转到详情页面
      wx.navigateTo({
        url: '/pages/homepage/search',
        animationType: "none",
      });
    }
  },

  // 点击活动，跳转详情页面
  goToDetailPage: function (event) {
    // 检测goeasy是否连接
    if (wx.goEasy.getConnectionStatus() === 'disconnected') {
      // 如果goeasy检测未连接，显示一个弹框提示用户先登录
      wx.showModal({
        title: '提示',
        content: '请先登录',
        showCancel: false, // 移除取消按钮
        confirmText: '确定',
        success: function (res) {
          // 根据需要在这里添加额外的逻辑
          wx.switchTab({
            url: '/pages/userCenter/userCenter',
            success: function () {
              console.log('跳转成功');
            },
            fail: function (error) {
              console.log('跳转失败', error);
            }
          })
        }
      });
    } else {
      // 如果 goeasy连接成功，继续跳转到详情页面
      var activity = event.currentTarget.dataset.activity;
      wx.navigateTo({
        url: '/pages/detail/detail?activity=' + activity.actid
      });
    }
  },


  // 检索框变色逻辑
  toggleTypeTitleBackground: function (event) {
    this.setData({
      typeTitleClicked: !this.typeTitleClicked,
      showTypeView: !this.data.showTypeView,
      typeTitleBackgroundColor: this.data.typeTitleBackgroundColor === '#f5f5f5' ? '' : '#f5f5f5',
    });
  },
 
  /* 活动类型toggle */
  toggleSelectedItem(e) {
    
    const { item } = e.currentTarget.dataset;
    const { selectedItems } = this.data;

    // 如果selectedItems中有其他元素，则删除"不限"
    if (selectedItems.length > 0 && selectedItems.includes("不限")) {
      const index = selectedItems.indexOf("不限");
      selectedItems.splice(index, 1);
    }

    // 检查项目是否已经在selectedItems中
    const index = selectedItems.indexOf(item);
    if (index > -1) {
      // 如果项目已经存在，从selectedItems中移除
      selectedItems.splice(index, 1);
    } else {
      // 如果项目不存在，将其添加到selectedItems中 
      if (item !== "不限") {
        selectedItems.push(item);
      }
      else {
        selectedItems.splice(0, selectedItems.length);
        selectedItems.push(item);
      }
    }

    // 如果selectedItems为空，则添加"不限"
    if (selectedItems.length === 0) {
      selectedItems.push("不限");
    }
    // 更新selectedItems数组 
    this.setData({
      selectedItems: selectedItems,
    });
  },
  /* 开始时间toggle */
  TimetoggleSelectedItem(e) {
    const { item } = e.currentTarget.dataset;
    const { TimeselectedItems } = this.data;
    // 如果TimeselectedItems中有其他元素，则删除"不限"
    if (TimeselectedItems.length > 0 && TimeselectedItems.includes("不限")) {
      const index = TimeselectedItems.indexOf("不限");
      TimeselectedItems.splice(index, 1);
    }
    // 检查项目是否已经在TimeselectedItems中 
    const index = TimeselectedItems.indexOf(item);
    if (index > -1) {
      // 如果项目已经存在，从TimeselectedItems中移除 
      TimeselectedItems.splice(index, 1);
    } else {
      // 如果项目不存在，将其添加到TimeselectedItems中 
      if (item !== "不限") {
        TimeselectedItems.push(item);
      }
      else {
        TimeselectedItems.splice(0, TimeselectedItems.length);
        TimeselectedItems.push(item);
      }
    }
    // 如果TimeselectedItems为空，则添加"不限"
    if (TimeselectedItems.length === 0) {
      TimeselectedItems.push("不限");
    }
    // 更新TimeselectedItems数组 
    this.setData({
      TimeselectedItems: TimeselectedItems,
    });
  },
  /* 主办单位toggle */
  HosttoggleSelectedItem(e) {
    const { item } = e.currentTarget.dataset;
    const { HostselectedItems } = this.data;
    // 如果HostselectedItems中有其他元素，则删除"不限"
    if (HostselectedItems.length > 0 && HostselectedItems.includes("不限")) {
      const index = HostselectedItems.indexOf("不限");
      HostselectedItems.splice(index, 1);
    }
    // 检查项目是否已经在HostselectedItems中 
    const index = HostselectedItems.indexOf(item);
    if (index > -1) {
      // 如果项目已经存在，从HostselectedItems中移除 
      HostselectedItems.splice(index, 1);
    } else {
      // 如果项目不存在，将其添加到selectedItems中 
      if (item !== "不限") {
        HostselectedItems.push(item);
      }
      else {
        HostselectedItems.splice(0, HostselectedItems.length);
        HostselectedItems.push(item);
      }
    }
    // 如果HostselectedItems为空，则添加"不限"
    if (HostselectedItems.length === 0) {
      HostselectedItems.push("不限");
    }
    // 更新HostselectedItems数组 
    this.setData({
      HostselectedItems: HostselectedItems,
    });
  },
  /* 参与人数toggle */
  NumbertoggleSelectedItem(e) {
    const { item } = e.currentTarget.dataset;
    const { NumberselectedItems } = this.data;
    // 如果NumberselectedItems中有其他元素，则删除"不限"
    if (NumberselectedItems.length > 0 && NumberselectedItems.includes("不限")) {
      const index = NumberselectedItems.indexOf("不限");
      NumberselectedItems.splice(index, 1);
    }
    // 检查项目是否已经在NumberselectedItems中 
    const index = NumberselectedItems.indexOf(item);
    if (index > -1) {
      // 如果项目已经存在，从NumberselectedItems中移除 
      NumberselectedItems.splice(index, 1);
    } else {
      // 如果项目不存在，将其添加到selectedItems中 
      if (item !== "不限") {
        NumberselectedItems.push(item);
      }
      else {
        NumberselectedItems.splice(0, NumberselectedItems.length);
        NumberselectedItems.push(item);
      }
    }
    // 如果NumberselectedItems为空，则添加"不限"
    if (NumberselectedItems.length === 0) {
      NumberselectedItems.push("不限");
    }
    // 更新NumberselectedItems数组 
    this.setData({
      NumberselectedItems: NumberselectedItems,
    });
  },

  selectCompetition: function () {
    // 调用筛选方法，例如按照竞赛类型筛选
    this.filterActivitiesByType('理科竞赛','工科竞赛','文科竞赛','商科竞赛');
  },

  selectWealth: function () {
    // 调用筛选方法，例如按照家教和实习类型筛选
    this.filterActivitiesByType('家教', '实习');
  },

  selectRecent: function () {
    // 调用筛选方法，例如按照近期活动筛选
    this.filterActivitiesByRecent(30); // 假设30代表30天内的活动
  },

  selectRecruitment: function () {
    // 调用筛选方法，例如按照招募中的活动筛选
    this.filterRecruitmentActivities();
  },

  // 筛选方法，根据类型进行筛选
  filterActivitiesByType: function (...types) {
    // 根据活动类型进行筛选逻辑
    // 例如，过滤出类型为types的活动
    const filteredActivities = this.data.allActivities.filter(activity => types.includes(activity.type));

    // 更新页面数据
    this.setData({
      activities: filteredActivities,
    });
  },

  // 筛选方法，根据日期进行筛选
filterActivitiesByRecent: function () {
  // 获取当前日期
  const currentDate = new Date();

  // 获取一个月后的日期
  const oneMonthLaterDate = new Date();
  oneMonthLaterDate.setMonth(oneMonthLaterDate.getMonth() + 1);

  // 根据截止日期进行筛选逻辑
  // 例如，过滤出截止日期在一个月以内的活动
  const filteredActivities = this.data.allActivities.filter(activity => {
    const deadlineDate = new Date(activity.date);
    return deadlineDate >= currentDate && deadlineDate <= oneMonthLaterDate;
  });

  // 更新页面数据
  this.setData({
    activities: filteredActivities,
  });
},


  // 筛选方法，仅显示招募中的活动
filterRecruitmentActivities: function () {
  // 根据招募状态进行筛选逻辑
  // 例如，过滤出招募中的活动
  const filteredActivities = this.data.allActivities.filter(activity => activity.status === '0');

  // 更新页面数据
  this.setData({
    activities: filteredActivities,
  });
},

  /* 提交筛选数据到后端 */
  submitScreen: function () {
    var that = this;
    this.setData({
      typeTitleClicked: !this.typeTitleClicked,
      showTypeView: !this.data.showTypeView,
    });
    // 发起网络请求
    wx.request({
      url: 'http://121.199.38.35:5000/homepage/select',
      method: 'POST',
      header: {
        'content-type': 'application/json', // 设置请求头为 JSON
      },
      data: {
        selectedItems: this.data.selectedItems || [],
        hostselectedItems: this.data.HostselectedItems || [],
        numberselectedItems: this.data.NumberselectedItems || [],
        timeselectedItems: this.data.TimeselectedItems || [],
      },
      success: function (res) {
        console.log('数据传输成功', res.data);
          // 请求成功，处理返回的数据
          const updatedActivities = res.data.results.map(activity => {
            return {
              ...activity,
              imgsrc: activity.imgsrc ? `data:image/png;base64,${activity.imgsrc}` : null
            };
          });
          that.setData({
            activities: updatedActivities,
          });
          console.log("筛选更新后的活动数据"+that.activities);
      },
      fail: function (error) {
        console.error('数据传输失败', error);
        // 可以在失败回调中处理失败情况，例如提示用户重新尝试等
      }
    });
  },
  /* 重置筛选项 */
  resetScreen: function() {
    this.setData({
      selectedItems: ["不限"],
      TimeselectedItems: ["不限"],
      HostselectedItems: ["不限"],
      NumberselectedItems: ["不限"]
    });
  },

  

  /* 初始化主页数据 */
requestData: function () {
  var that = this;
  wx.showLoading({
    title: '加载中',
  });
  // 获取活动数据
  wx.request({
    url: 'http://121.199.38.35:5000/get/activity',
    method: 'GET',
    success: function (res) {
      wx.hideLoading();
      console.log(res.data);
      const updatedActivities = res.data.results.map(activity => {
        return {
          ...activity,
          imgsrc: activity.imgsrc ? `data:image/png;base64,${activity.imgsrc}` : null
        };
      });
      const bannerList = []; // 存放轮播图数据
       // 遍历所有活动
      updatedActivities.forEach(activity => {
      // 如果carousel字段为1，则添加到bannerList
        if (activity.carousel === 1) {
          bannerList.push(activity);
        }
      });
      // 将活动数据保存起来
      that.setData({
        activities: updatedActivities,
        allActivities: updatedActivities,
        bannerList: bannerList,
         //bannerList: updatedActivities.slice(0, 4) // 获取前四个活动数据作为轮播图数据
      });
    },
          
    fail: function (error) {
      wx.hideLoading();
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

    
      

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    // 加载第一页数据
    this.requestData();
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
    this.requestData();
    wx.stopPullDownRefresh();
    wx.showToast({
      title: '已经是最新啦',
      icon: 'success',
      duration: 750,
    });
  },


  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})