Page({
  data: { formats: {} },
  onEditorReady() {
    this.createSelectorQuery().select('#editor1').context(res => {
      this.editorCtx = res.context; // 获取编辑器实例
    }).exec();
  },
  // 格式设置（如加粗、斜体）
  format(e) {
    const { name, value } = e.target.dataset;
    this.editorCtx.format(name, value);
  },
  onBlur(e) {
    const htmlContent = e.detail.html;
    this.setData({ content: htmlContent });
  },
  // 插入图片（需配置上传接口）
  insertImage() {
    wx.chooseImage({
      success: (res) => {
        wx.uploadFile({
          url: 'your_image_upload_url',
          filePath: res.tempFilePaths[0],
          name: 'file',
          success: (res) => {
            const imgUrl = JSON.parse(res.data).url;
            this.editorCtx.insertImage({ src: imgUrl });
          }
        });
      }
    });
  }
});