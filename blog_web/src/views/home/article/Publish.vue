<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-notebook-2"></i> 文章管理</el-breadcrumb-item>
        <el-breadcrumb-item>发表文章</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="myContainer">
      <div style="max-width: 1100px">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" >
          <el-form-item label="标题" prop="title">
            <el-input v-model="ruleForm.title"></el-input>
          </el-form-item>
          <el-form-item label="摘要" prop="summary">
            <el-input type="textarea" v-model="ruleForm.summary"></el-input>
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <mavon-editor v-model="ruleForm.content" ref="md" @imgAdd="$imgAdd" @change="change"
                          style="min-height: 300px"/>
          </el-form-item>
          <el-form-item>
            <el-button @click="saveForm(ruleForm)">保存</el-button>
            <el-button type="primary" @click="publishForm(ruleForm)">立即发表</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
    import {mavonEditor} from 'mavon-editor'
    import 'mavon-editor/dist/css/index.css'

    export default {
        name: 'markdown',
        data: function () {
            return {
                html: '',
                configs: {},
                ruleForm: {
                    title: '',
                    summary: '',
                    content: ''
                },
                rules: {
                    title: [
                        {required: true, message: '请输入文章标题', trigger: 'blur'},
                        {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
                    ],
                    summary: [
                        {required: true, message: '请输入摘要', trigger: 'blur'}
                    ],
                    content: [
                        {required: true, message: '请填写内容', trigger: 'blur'}
                    ]
                }
            }
        },
        components: {
            mavonEditor
        },
        created() {
            let id = this.$route.params.id;
            if (!isNaN(id)) {
                this.selectById(id);
            }
        },
        methods: {
            selectById(primaryKey) {
                let _this = this;
                this.$axios.get("/article/id", {
                    params: {
                        primaryKey: primaryKey
                    }
                })
                    .then(function (response) {
                        _this.ruleForm = response.data.data;
                    })
                    .catch(function (error) {
                    });
            },
            // 将图片上传到服务器，返回地址替换到md中
            $imgAdd(pos, $file) {
                let formdata = new FormData();
                formdata.append('file', $file);
                this.$axios({
                    url: '/article/img',
                    method: 'post',
                    data: formdata,
                    headers: {'Content-Type': 'multipart/form-data'},
                }).then((response) => {
                    this.$refs.md.$img2Url(pos, response.data.data);
                })
            },
            change(value, render) {
                // render 为 markdown 解析后的结果
                this.html = render;
            },
            saveForm(form) {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        let _this = this;
                        form.keyWord = "0";
                        this.$axios.post("/article/add", form)
                            .then(function (response) {
                                _this.commons.kMessage("保存文章成功！", 'success');
                                // 跳转
                            })
                            .catch(function (error) {
                            });
                    } else {
                        return false;
                    }
                });
            },
            publishForm(form) {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        let _this = this;
                        form.keyWord = "1";
                        this.$axios.post("/article/add", form)
                            .then(function (response) {
                                _this.commons.kMessage("发表文章成功！", 'success');
                                // 跳转
                            })
                            .catch(function (error) {
                            });
                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>
