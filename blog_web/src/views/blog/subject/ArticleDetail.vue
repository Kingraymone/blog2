<template>
  <div class="detail">
    <div class="title">
      <h3>{{blog.title}}</h3>
      <br/>
      <p>发布于:{{blog.createTime}}</p>
    </div>
    <el-divider><i class="fa fa-book" aria-hidden="true"></i></el-divider>
    <div class="container">
      <div class="markdown-body" style="max-width: 1100px;margin: 0 auto" v-html="blog.content"></div>
    </div>
  </div>
</template>

<script>
    import 'github-markdown-css';

    const markdownRender = require('markdown-it')();

    export default {
        name: "Detail",
        data: function () {
            return {
                blog: {
                    content: '',
                    title: '',
                    summary: '',
                    userId: '',
                    createTime:''
                }
            }
        },
        created() {
            // 路由路径获得id
            let id = this.$route.params.id;
            // 通过id查询数据
            this.selectById(id);
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
                        _this.blog = response.data.data;
                        let time = _this.blog.createTime;
                        _this.blog.createTime=time.substring(0,10)+" "+time.substring(11,19);
                        _this.blog.content = markdownRender.render(_this.blog.content);
                        //_this.blog =
                    })
                    .catch(function (error) {
                    });
            }
        }
    }
</script>

<style scoped>

  .title{
    margin: 0 auto;
    text-align: center;
  }
  .detail{
    margin: 0 auto;
    padding: 5px;
  }
  .container {
    padding:40px;
    background: #fff;
    border: 2px solid #ddd;
    border-top: aliceblue;
    border-radius: 5px;
  }
</style>
