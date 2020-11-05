<template>
  <div class="myContainer">
    <div class="markdown-body" style="max-width: 1100px;margin: 0 auto" v-html="blog.content"></div>
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
                    userId: ''
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

</style>
