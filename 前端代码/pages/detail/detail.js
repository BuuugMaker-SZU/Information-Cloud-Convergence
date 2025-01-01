
Page({
  data: {
    actname:'',
    date:'',
    imgsrc:'',
    introduction:'',
    location:'',
    organizer:'',
    permission:'',
    reward:'',
    status:'',
    type:'',
    actid:'',
    grouplist: [],
    total: '', // 当前小组的数量

    isFoldedIntro: true ,// 初始状态为折叠
    foldedIntro:'',
    isFoldedReward: true ,
    foldedReward:'',
    maxLen: 65 ,// 折叠时显示的最大长度
    maxlen: 20 ,
  },

  toggleFoldIntro: function() {
    this.setData({
      isFoldedIntro: !this.data.isFoldedIntro
    });
  },
  toggleFoldReward: function() {
    this.setData({
      isFoldedReward: !this.data.isFoldedReward
    });
  },
  toggleFoldItemIntro: function(e) {
    var index = e.currentTarget.dataset.index; // 获取当前项的索引
    var key = `grouplist[${index}].isFolded`;
    this.setData({
      [key]: !this.data.grouplist[index].isFolded
    });
  },


  // onShow: function() {
  //   // 在页面显示时重新请求数据
  //   this.loadData();
  // },
  
  loadData: function() {
    var actid = this.data.actid;
    // 发送请求获取数据
    wx.request({
      url: 'http://121.199.38.35:5000/get/detail',
      method: 'GET',
      data: {
        actid: actid,
      },
      success: (res) => {
        // 获取服务器返回的数据
        var activity = res.data;
        console.log(activity);
        // 设置数据到 data 中
        this.setData({
          actid:activity.results.actid,
          actname: activity.results.actname,
          date: activity.results.date,
          location: activity.results.location,
          introduction: activity.results.introduction,
          foldedIntro: activity.results.introduction.substring(0,65),
          reward: activity.results.reward,
          foldedReward: activity.results.reward.substring(0,65),
          imgsrc: activity.results.imgsrc ? `data:image/png;base64,${activity.results.imgsrc}` : null,
          organizer: activity.results.organizer,
          type: activity.results.type,
        });
      },
      fail: (res) => {
        // 处理请求失败的情况
        console.error('请求失败', res);
        wx.showToast({
          title: '请求失败，请重试',
          icon: 'none',
          duration: 2000,
        });
      },
    });
  
    wx.request({
      url: 'http://121.199.38.35:5000/get/teaminfobyactid',
      method: 'GET',
      data: {
        actid: actid,
      },
      success: (res) => {
        // 获取服务器返回的数据
        var group = res.data.results;
        console.log(group);
        // 设置数据到 data 中
        this.setData({
          grouplist: group,
          total: group.length
        });
        wx.hideLoading();
      },
      fail: (res) => {
        // 处理请求失败的情况
        console.error('请求失败', res);
        wx.showToast({
          title: '请求失败，请重试',
          icon: 'none',
          duration: 2000,
        });
      },
    });
  },
  

  onLoad: function (options) {
    wx.showLoading({
      title: '加载中',
    });
    // 获取活动名称
    var actid= options.activity;
    console.log("print actid:"+actid);
    // 发送GET请求到后端服务器
    wx.request({
      url: 'http://121.199.38.35:5000/get/detail',
      method: 'GET',
      data: {
        actid: actid,
      },
      success: (res) => {
        // 获取服务器返回的数据
        var activity = res.data;
        console.log(activity);
        // 设置数据到data中
        this.setData({
          actid:activity.results,actid,
          actname: activity.results.actname,
          date: activity.results.date,
          location: activity.results.location,
          introduction: activity.results.introduction,
          foldedIntro: activity.results.introduction.substring(0,65),
          reward: activity.results.reward,
          foldedReward: activity.results.reward.substring(0,65),
          imgsrc: activity.results.imgsrc ? `data:image/png;base64,${activity.results.imgsrc}` : null,
          organizer: activity.results.organizer,
          type: activity.results.type,
        });
      },
      fail: (res) => {
        // 处理请求失败的情况
        console.error('请求失败', res);
        wx.showToast({
          title: '请求失败，请重试',
          icon: 'none',
          duration: 2000,
        });
      },
    });

    wx.request({
      url: 'http://121.199.38.35:5000/get/teaminfobyactid',
      method: 'GET',
      data: {
        actid: actid,
      },
      success: (res) => {
        // 获取服务器返回的数据
        var group = res.data.results;
        console.log(group);
        group.forEach(function(item) {
          item.isFolded = true; // 初始状态设置为折叠
        });
        group.forEach(function(item) {
          item.foldIntro = item.introduction.substring(0,20); 
        });
        // 设置数据到data中
        this.setData({
          grouplist:group,
          total:group.length
        });
        wx.hideLoading();
      },
      fail: (res) => {
        // 处理请求失败的情况
        console.error('请求失败', res);
        wx.showToast({
          title: '请求失败，请重试',
          icon: 'none',
          duration: 2000,
        });
      },
    });
  },

  // 每次刷新更新列表
  onPullDownRefresh() {
    wx.showLoading({
      title: '加载中',
    });
    // 发送GET请求到后端服务器
    wx.request({
      url: 'http://121.199.38.35:5000/get/teaminfobyactid',
      data: {
        actid: this.data.actid,
      },
      method: 'GET',
      success: (res) => {
        var newGroup = res.data.results;
        
        newGroup.forEach(function(item) {
          item.isFolded = true; // 初始状态设置为折叠
        });
        newGroup.forEach(function(item) {
          item.foldIntro = item.introduction.substring(0,20); 
        });

        if (newGroup.length > this.data.total && this.data.total > 0) {
          wx.hideLoading();
          // 更新数据
          this.setData({
            grouplist: newGroup,
            total: newGroup.length,
          });
          // 停止下拉刷新动画
          wx.stopPullDownRefresh();
        } else {
          // 没有新数据时也停止下拉刷新动画
          wx.stopPullDownRefresh();
          wx.hideLoading();
          wx.showToast({
            title: '已经是最新啦',
            icon: 'success',
            duration: 750,
          });
        }
      },
      fail: (res) => {
        // 处理请求失败的情况
        console.error('请求失败', res);
        wx.showToast({
          title: '请求失败，请重试',
          icon: 'none',
          duration: 2000,
        });
      },
    });
  },

  getContact(event) {
    const charge=event.currentTarget.dataset;
    const to = {
      id: charge.openid,
      name:charge.chargename,
    }
    const path = '../chat/privateChat/privateChat?to=' + (JSON.stringify(to));
    console.log('打印路径'+path);
    wx.navigateTo({url:path});
  },

goToCreatePage: function (e) {
  // 使用 wx.navigateTo 跳转到 create 页面，并传递 actid 和 actname 参数
  wx.navigateTo({
    url: '/pages/teamManager/createTeam/createTeam?actid=' + this.data.actid + '&actname=' + this.data.actname,
  });
},
});