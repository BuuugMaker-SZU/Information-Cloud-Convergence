Page({
  data: {
    images: []
  },

  handleInput: function(e) {
    const key = e.currentTarget.dataset.key;
    const value = e.detail.value;
    this.setData({
      [key]: value
    });
  },

  chooseImage() {
    wx.chooseImage({
      success: (res) => {
        const tempFilePaths = res.tempFilePaths;
        const images = this.data.images.concat(tempFilePaths);
        this.setData({ images });
      },
    });
  },

  deleteImage(e) {
    const index = e.currentTarget.dataset.index;
    const images = this.data.images;
    images.splice(index, 1);
    this.setData({ images });
  },
});
