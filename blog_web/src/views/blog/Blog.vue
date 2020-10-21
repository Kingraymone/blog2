<template>
  <div style="overflow-y: auto;height: 100%;background: #f1f1f1;">
    <el-row style="background-color: white;height: 80px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
      <v-head-nav></v-head-nav>
    </el-row>
    <el-row style="height: 100%">
        <div>
          <keep-alive>
            <router-view ref="baseContent" v-if="$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view ref="baseContent" v-if="!$route.meta.keepAlive"></router-view>
        </div>
    </el-row>
  </div>
</template>
<script>
    import vHeadNav from '../../components/blog/HeadNav.vue';
    import Side from '../../components/blog/Side.vue';

    export default {
        components: {
            vHeadNav,
            Side
        }
    }
</script>
<style lang="scss" scoped>
  .navbar{
    background-color:white;
    padding-right:50px;
    padding-left:50px;
  }

  .navbar-brand {
    line-height: 80px;
    height: 80px;
    padding-top: 0;
    color: #323e46;
    font-family: lobster , Serif;
    font-size: 30px;
  }

  .navbar-nav li a {
    line-height: 80px;
    height: 80px;
    padding-top: 0;
    color:black;
  }


  .arrows span{
    color: #23b0ac;
    background-color:#fff;
    cursor:pointer;
    width:40px;
    height:40px;
    text-align:center;
    border-radius:100%;
  }

  .item :hover {
    color: #23b0ac;
    border-bottom: 3px solid #23b0ac;
  }
</style>
