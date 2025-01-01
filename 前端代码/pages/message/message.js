Page({
  data: {
    messages: [
      { title: "日程提醒", time: "9:00 AM", text: "3天后有一场比赛" },
      { title: "xx聊天群1", time: "Yesterday", text: "xx：我们这个比赛需要准备什么？" },
      { title: "xx聊天群2", time: "Monday", text: "xx：比赛地点应该没变吧" }
    ]
  },

  handleItemClick: function(e) {
    const { index } = e.currentTarget.dataset;
    const messages = [...this.data.messages]; // 浅拷贝 messages 数组
    messages[index].isClicked = true; // 标记按钮已被点击
    this.setData({ messages });

    setTimeout(() => {
      messages[index].isClicked = false; // 恢复按钮为未点击状态
      this.setData({ messages });

      this.navigateToDetailPage();
    }, 100);
  },

  navigateToDetailPage: function() {
    wx.navigateTo({
      url: '/pages/detail/detail' // 跳转到详情页
    });
  }  
});
