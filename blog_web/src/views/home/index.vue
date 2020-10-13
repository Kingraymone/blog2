<template>
  <el-container direction="vertical" style="height:100%; ">
    <vHeadNav></vHeadNav>
    <el-container direction="horizontal" style="height: 400px">
      <el-scrollbar style="height:100%">
        <MenuNav style="background-color: #324157;"></MenuNav>
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
                isCollapse: true,
                items: [
                    {
                        icon: 'el-icon-s-home',
                        index: '/home',
                        title: '系统首页'
                    },
                    {
                        icon: 'el-icon-s-custom',
                        index: '/home/user',
                        title: '用户管理'
                    },
                    {
                        icon: 'el-icon-s-order',
                        index: 'tabs',
                        title: 'tab选项卡'
                    },
                    {
                        icon: 'el-icon-s-order',
                        index: '3',
                        title: '表单相关',
                        subs: [
                            {
                                index: 'form',
                                title: '基本表单'
                            },
                            {
                                index: '3-2',
                                title: '三级菜单',
                                subs: [
                                    {
                                        index: 'editor',
                                        title: '富文本编辑器'
                                    },
                                    {
                                        index: 'markdown',
                                        title: 'markdown编辑器'
                                    }
                                ]
                            },
                            {
                                index: 'upload',
                                title: '文件上传'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-s-order',
                        index: 'icon',
                        title: '自定义图标'
                    },
                    {
                        icon: 'el-icon-s-order',
                        index: 'charts',
                        title: 'schart图表'
                    },
                    {
                        icon: 'el-icon-rank',
                        index: '6',
                        title: '拖拽组件',
                        subs: [
                            {
                                index: 'drag',
                                title: '拖拽列表'
                            },
                            {
                                index: 'dialog',
                                title: '拖拽弹框'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-s-order',
                        index: 'i18n',
                        title: '国际化功能'
                    },
                    {
                        icon: 'el-icon-s-order',
                        index: '7',
                        title: '错误处理',
                        subs: [
                            {
                                index: 'permission',
                                title: '权限测试'
                            },
                            {
                                index: '404',
                                title: '404页面'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-s-order',
                        index: '/donate',
                        title: '支持作者'
                    }
                ]
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
