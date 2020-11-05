<template>
  <div style="height: 100%">
    <h2 style="text-align: center;color: #bcbec2">文章归档</h2>
    <div class="main">
      <el-timeline>
        <el-timeline-item v-for="(item,key,index) in timeline" :key="index" placement="top" icon="el-icon-time" type="primary" size="large">
          <h2><strong>{{key.substring(1)}}年</strong><span > | </span> <span style="font-size:20px;color: cornflowerblue">共{{item.length}}篇文章</span></h2>
          <br/>

          <p v-for="article in item" style="font-size: 16px">
            <span style="color: #bcbec2;">{{article.date}} :  </span>
            <router-link class="title" :to="'/blog/detail/'+article.id">{{article.header}}</router-link>
            <el-badge :value="article.comment" type="info"/>
          </p>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script>
    export default {
        name: "Archives",
        data() {
            return {
                timeline: {}
            }
        },
        created() {
            this.selectData();
        },
        methods: {
            selectData() {
                let _this = this;
                _this.$axios.post('/article/all')
                    .then(function (response) {
                        let data = response.data.data;
                        _this.timeline = {};
                        data.forEach((item) => {
                            let curParams = {};
                            let year = "a"+item.createTime.substring(0, 4);
                            curParams.date = item.createTime.substring(5, 10);
                            curParams.header = item.title;
                            curParams.id = item.uniqueId;
                            curParams.comment = item.comment;
                            if (!_this.timeline[year]) {
                                _this.timeline[year]=[];
                            }
                            _this.timeline[year].push(curParams);
                        });
                    })
                    .catch(function (error) {
                    });
            }
        }
    }
</script>

<style scoped>

  .title{
    color: rgba(30, 30, 30, 0.82);
  }
  .title:hover{
    color: #6495ED;
  }
  .main {
    margin: 10px 100px;
    padding: 50px;
    background: #fff;
    border: 2px solid #ddd;
    border-radius: 4px;
    min-height: 400px;
  }
</style>
