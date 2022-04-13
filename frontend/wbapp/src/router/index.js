import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',

    name: 'home',
    redirect: '/wb/newLogin',
    component: () => import('../layouts/MainLayout.vue')
    //主页面

  },
  {
    path: '/wb',
    name: 'wb',
    props: true,
    redirect: '/wb/homePage/homeAll',
    component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/views/wbView.vue'),
    children: [
      //渲染在wbView center 中
      //home
      {
        path: 'homePage',
        name: 'homePage',
        redirect: '/wb/homePage/homeAll',
        component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/HomePages/HomePage1.vue'),
        children: [
          //homePage1
          {
            path: 'homeAll',
            name: 'homeAll',
            component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/HomePages/HomePage.vue')
          }
        ]
      },
      //hot
      {
        path: 'hotPage',
        name: 'hotPage',

        component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/HotPages/HotPage.vue')
      },
      // user
      {
        path: 'userPage',
        name: 'userPage',
        // redirect: 'userPage',
        props: true,
        component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/UserPage.vue'),
        children: [
          //共享用户信息页
          {
            path: 'userInfo/:path',
            props: true,
            name: 'userInfo',
            component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/UserPages/MainPage.vue')
          },
          //用户编辑信息页
          {
            path: 'edit',
            name: 'edit',
            component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/UserPages/AlertInfo.vue')
          },
          //粉丝与关注
          {
            path: 'FansFollow/:carryInfo',
            name: 'FansFollow',
            component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/components/UserInfoComs/FansFollow.vue')
          }
        ]

      },
      //topics
      {
        path: 'topicPage/:type/:topic',
        name: 'topicPage',
        component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/TopicsPage.vue'),
        props: true,

      },
      // allComment
      {
        path: 'allCommentPage/:info',
        name: 'allCommentPage',
        // 允许跳转路由传参
        props: true,
        component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/ComentInfo.vue')
      },
      //newLogin
      {
        path: 'newLogin',
        name: 'newLogin',
        component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/HotPages/HotPage.vue')
      }
    ]

  }
  ,
  {
    path: '/ToSign/:page',
    name: 'Sign',
    component: () => import("../views/ToSign.vue"),
    props: true,
  }



]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
