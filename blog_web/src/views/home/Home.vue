<template>

  <div style="width: 100%">

    <el-row type="flex">
      <el-col :xs="5" :sm="5" :md="5" :lg="5" :xl="5"><div class="grid-content bg-purple"></div></el-col>
      <el-col :xs="19" :sm="19" :md="19" :lg="19" :xl="19"><div class="grid-content bg-purple-light"></div></el-col>
    </el-row>
    <p>今日访问数量</p>
    <p>访客数量走势图</p>
    <p>记事工具</p>


  </div>

</template>

<script>
    export default {
        name: "home",
        data() {
            const item = {
                date: '2016-05-02',
                name: '王小虎1',
                address: '上海市普陀区金沙江路 1518 弄1'
            };
            return {
                tableData: Array(20).fill(item)
            }
        }
    }
</script>

<style scoped>
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
