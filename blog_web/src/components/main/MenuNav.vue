<template>
  <div>
    <el-menu class="el-menu-vertical-cur"
             :collapse="$store.state.isCollapse"
             background-color="#324157"
             text-color="#bfcbd9"
             active-text-color="#20a0ff"
             unique-opened
             style="height: auto"
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
                                index: '/sys/home/article/publish',
                                title: '发表文章'
                            }
                        ]
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
        }
    }
</script>

<style scoped>

</style>
