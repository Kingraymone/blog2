<template>
  <el-container direction="vertical" style="height:100%; ">
    <vHeadNav></vHeadNav>
    <el-container direction="horizontal" style="height: 400px">
      <el-scrollbar style="height:100%;">
        <MenuNav></MenuNav>
      </el-scrollbar>
      <el-container direction="vertical">
        <TabNav></TabNav>
        <el-scrollbar style="height:100%">
          <div class="content">
            <keep-alive>
              <router-view ref="baseContent" v-if="$route.meta.keepAlive"></router-view>
            </keep-alive>
            <router-view ref="baseContent" v-if="!$route.meta.keepAlive"></router-view>
          </div>
        </el-scrollbar>
      </el-container>
    </el-container>
  </el-container>
</template>
<style scoped>
  .content {
    padding: 10px;
  }
</style>

<script>
    import vHeadNav from '../../components/main/HeadNav.vue';
    import MenuNav from '../../components/main/MenuNav.vue';
    import TabNav from '../../components/main/TabNav.vue';

    export default {
        data() {
            return {
                path: this.$route.params.path,
                isCollapse: true
            }
        },
        components: {
            vHeadNav,
            MenuNav,
            TabNav
        },
        methods:{

        }
    };
</script>
