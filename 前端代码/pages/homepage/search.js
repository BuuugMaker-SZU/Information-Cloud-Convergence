// pages/homepage/search.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    openid:'',
    searchText: '搜索活动',
    historySearch: [], //保存历史记录的数组
    searchNumber:0,//历史搜索记录总数
  },
  // 同步搜索框内容
  updateSearchText: function (event) {
    this.setData({
      searchText: event.detail.value
    });
  },
  // 重置搜索框
  clearSearchText: function () {
    this.setData({
      searchText: '搜索活动'
    });
  },
  clearSearchTextOnFocus: function () {
    if (this.data.searchText === '搜索活动') {
      this.setData({
        searchText: ''
      });
    }
  },
  clearSearchHistory: function () {
    // 在这里处理清空搜索记录的逻辑
    wx.showModal({
      title: '提示',
      content: '确定要清空搜索记录吗？',
      success: (res) => {
        if (res.confirm) {
          // 用户点击了确定
          // 在这里添加清空搜索记录的具体代码，可以使用 // 发送 POST 请求
        const openid = wx.getStorageSync('openid');
        console.log(openid);
        wx.request({
          url: 'http://121.199.38.35:5000/update/history',
          method: 'POST',
          data: {
            openid: openid,
            history: '',
          },
          success: (res) => {
            if (res.data && res.data.code === 200) {
              console.log('历史搜索记录清空成功:', res.data);
              wx.showToast({
                title: '搜索记录已清空',
                icon: 'success',
                duration: 2000,
                success: () => {
                  // 清空成功后手动刷新页面数据
                  // 更新本地数据
                  this.setData({
                    historySearch: '',
                    searchNumber: 0,
                  });
                },
              });    
              
            } else {
              // 处理请求失败的情况
              console.error('历史搜索记录清空失败:', res);
            }
          },
          fail: (error) => {
            // 处理请求失败的情况
            console.error('历史搜索记录清空失败:', error);
          },
        });
          
        }
      }
    });
  },

   // 点击键盘完成按钮或搜索按钮
   confirmSearch: function () {
    // 获取搜索关键词
    const searchText = this.data.searchText;
  
    // 更新历史搜索记录
    let historySearch = this.data.historySearch;
  
    // Check if searchText is not already in historySearch
    if (historySearch.indexOf(searchText) === -1) {
      historySearch.unshift(searchText);
    }
  
    // 更新本地数据
    this.setData({
      historySearch: historySearch,
      searchNumber: historySearch.length,
    });
  
    // 将数组转为字符串，替换双引号为单引号
    const historySearchString = JSON.stringify(historySearch).replace(/"/g, "'");
    
    // 发送 POST 请求
    const openid = wx.getStorageSync('openid');
    console.log(historySearchString)
    console.log(openid)
    wx.request({
      url: 'http://121.199.38.35:5000/update/history',
      method: 'POST',
      data: {
        openid: openid,
        history: historySearchString,
      },
      success: (res) => {
        if (res.data && res.data.code === 200) {
          // 请求成功，可以在这里处理返回的数据
          console.log('历史搜索记录更新成功:', res.data);
        } else {
          // 处理请求失败的情况
          console.error('历史搜索记录更新失败:', res);
        }
      },
      fail: (error) => {
        // 处理请求失败的情况
        console.error('历史搜索记录更新失败:', error);
      },
    });
  
    // 页面跳转至搜索结果页面
    wx.navigateTo({
      url: '/pages/homepage/searchResult?searchText=' + searchText
    });
  },
  deleteHistory: function (event) {
    const itemToDelete = event.currentTarget.dataset.item;
    const updatedHistory = this.data.historySearch.filter(item => item !== itemToDelete);

    // 在这里添加删除搜索记录的具体代码，可以使用 // 发送 POST 请求
    const openid = wx.getStorageSync('openid');
    wx.request({
      url: 'http://121.199.38.35:5000/update/history',
      method: 'POST',
      data: {
        openid: openid,
        history: JSON.stringify(updatedHistory).replace(/"/g, "'"),
      },
      success: (res) => {
        if (res.data && res.data.code === 200) {
          console.log('历史搜索记录删除成功:', res.data);
          wx.showToast({
            title: '删除成功',
            icon: 'success',
            duration: 2000,
            success: () => {
              // 删除成功后手动刷新页面数据
              // 更新本地数据
              this.setData({
                historySearch: updatedHistory,
                searchNumber: updatedHistory.length,
              });
            },
          });
        } else {
          // 处理请求失败的情况
          console.error('历史搜索记录删除失败:', res);
        }
      },
      fail: (error) => {
        // 处理请求失败的情况
        console.error('历史搜索记录删除失败:', error);
      },
    });
  },


  navigateToSearchResult: function (event) {
    const searchText = event.currentTarget.dataset.item;
    // 页面跳转至搜索结果页面
    wx.navigateTo({
      url: '/pages/homepage/searchResult?searchText=' + searchText
    });
  },
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.loadSearchHistory();
  },
  
  loadSearchHistory: function () {
    var openid = wx.getStorageSync('openid');
    
    wx.request({
      url: 'http://121.199.38.35:5000/get/history',
      method:'GET',
      data: {
        openid: openid,
      },
      success: (res) => {
        if (res.data && res.data.code === 200) {
          // 获取返回的 results 并转为数组
          const resultsArray = JSON.parse(res.data.results.replace(/'/g, '"'));
  
          // 更新 historySearch 和 searchNumber
          this.setData({
            historySearch: resultsArray,
            searchNumber: resultsArray.length,
          });
        } else {
          // 处理请求失败的情况
          console.error('GET请求失败:', res);
        }
      },
      fail: (error) => {
        // 处理请求失败的情况
        console.error('GET请求失败:', error);
      },
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
  onShow: function () {
    console.log('onShow called');
    this.loadSearchHistory();
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