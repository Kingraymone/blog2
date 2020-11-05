<template>
<el-upload
  class="avatar-uploader"
  :action="remoteAddress"
  :data="myData"
  :show-file-list="false"
  :headers="myHeader"
  :on-success="handleAvatarSuccess"
  :before-upload="beforeAvatarUpload"
  :on-error="handleError">
  <img v-if="imageUrl" :src="imageUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
</template>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

<script>
    export default {
        data() {
            return {
                imageUrl: '',
                remoteAddress:this.$axios.defaults.baseURL+"user/img"
            };
        },
        props: ['username'],
        computed:{
            myHeader() {
                return {"Authorization": this.$store.state.TOKEN}
            },
            myData() {
                return {"username": this.username}
            }
        },
        methods: {
            handleError(err, file, fileList){
                this.commons.kMessage("上传失败", 'error');
            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
                this.commons.kMessage("上传成功", 'success');
                this.$emit('closeUpload');
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isJPG) {
                    this.commons.kMessage("上传头像图片只能是 JPG 格式!", 'error');
                }
                if (!isLt2M) {
                    this.commons.kMessage("上传头像图片大小不能超过 2MB!", 'error');
                }
                this.remoteAddress=this.$axios.defaults.baseURL+"user/img";
                return isJPG && isLt2M;
            }
        }
    }
</script>
