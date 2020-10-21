<template>
  <div>
    <el-menu class="el-menu-vertical"
             :collapse="$store.state.isCollapse"
             text-color="#E7E1D9"
             active-text-color="#20a0ff"
             unique-opened
             router
    >
      <template v-for="item in items">
        <template v-if="item.subs">
          <el-submenu :index="item.index" :key="item.index">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.title }}</span>
            </template>
            <template v-for="subItem in item.subs">
              <el-submenu
                v-if="subItem.subs"
                :index="subItem.index"
                :key="subItem.index"
              >
                <template slot="title">{{ subItem.title }}</template>
                <el-menu-item
                  v-for="(threeItem,i) in subItem.subs"
                  :key="i"
                  :index="threeItem.index"
                >{{ threeItem.title }}
                </el-menu-item>
              </el-submenu>
              <el-menu-item
                v-else
                :index="subItem.index"
                :key="subItem.index"
              >{{ subItem.title }}
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.index" :key="item.index">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.title }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
    export default {
        name: "MenuNav",
        data() {
            return {
                path: this.$route.params.path,
                items: [
                    {
                        icon: 'el-icon-s-home',
                        index: '/sys/home',
                        title: '系统首页'
                    },
                    {
                        icon: 'el-icon-s-custom',
                        index: '/sys/home/user',
                        title: '用户管理'
                    },
                    {
                        icon: 'el-icon-notebook-2',
                        index: 'article',
                        title: '文章管理',
                        subs: [
                            {
                                index: '/sys/home/article/list',
                                title: '文章列表'
                            },
                            {
                                index: '/sys/home/article/publish/add',
                                title: '发表文章'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-s-order',
                        index: '3',
                        title: '常用功能',
                        subs: [
                            {
                                index: 'form',
                                title: '电话轰炸'
                            },
                            {
                                index: '3-2',
                                title: '小说下载',
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
                        title: '页面导航'
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
        }
    }
</script>

<style scoped>
  .el-menu-vertical:not(.el-menu--collapse) {
    width: 222px;
  }
  /*{
    background: -webkit-linear-gradient(90deg, rgb(11, 8, 10), rgb(16, 40, 63));
    background: linear-gradient(90deg, rgb(11, 8, 10), rgb(16, 40, 63));
  }*/
  .el-menu-item{
    background: -webkit-linear-gradient(90deg, rgb(11, 8, 10), rgb(16, 40, 63));
    background: linear-gradient(90deg, rgb(11, 8, 10), rgb(16, 40, 63));
  }
  .el-submenu{
    background: -webkit-linear-gradient(90deg, rgb(11, 8, 10), rgb(16, 40, 63));
    background: linear-gradient(90deg, rgb(11, 8, 10), rgb(16, 40, 63));
  }
  .el-menu-item:hover {
    background: #ecf5ff !important;
  }
  .el-menu-vertical{
    background: -webkit-linear-gradient(90deg, rgb(11, 8, 10), rgb(16, 40, 63));
    background: linear-gradient(90deg, rgb(11, 8, 10), rgb(16, 40, 63));
  }

</style>
