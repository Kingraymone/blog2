<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  @import "./assets/css/main.css";
  @import "./assets/css/color-dark.css";

  .el-scrollbar__wrap{
    overflow-x: hidden;
  }
  .container {
    padding: 15px;
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
  }
  .router-link-active {
    text-decoration: none;
  }
  .crumbs {
    margin: 10px 0;
  }
  .editor-btn{
    margin-top: 20px;
  }
  .el-dialog__header{
    border-bottom: 1px solid #e8eaec;
  }
  .el-dialog__footer {
    border-top: 1px solid #e8eaec;
  }
  .myContainer {
    padding: 10px;
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
    margin: 0 auto;
  }
</style>
