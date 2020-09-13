import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'blog',
      redirect:'/blog'
    },
    {
      path:'/blog',
      name:'blog',
      component: () => import("@/views/blog/Blog.vue")
    },
    {
      path:'/sys',
      name:'login',
      redirect:'/sys/login'
    },
    {
      path:'/sys/login',
      name:'login',
      component:()=>import("@/views/login/Index.vue")
    },
    {
      path: '/sys/home',
      name: 'home',
      component: () => import("@/views/home/Index.vue"),
      children: [
        {
          path: '',
          component: () => import('@/views/home/Home.vue'),
          meta: { title: '系统首页' }
        },
        {
          path: 'user',
          component: () => import('@/views/home/User.vue'),
          meta: { title: '用户管理' }
        },
        {
          path: 'article/publish',
          component: () => import('@/views/home/article/Publish.vue'),
          meta: { title: '发表文章' }
        },
        {
          path: 'article/list',
          component: () => import('@/views/home/article/List.vue'),
          meta: { title: '文章列表' }
        }

        ]
    }
  ]
})
