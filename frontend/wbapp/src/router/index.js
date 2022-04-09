import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',

    name: 'home',
    component: () => import('../views/ViewerView.vue'),
  },

  {
    path: '/User',
    name: 'UserView',
    redirect: '/User/UserHome/HomePage1/HomePage',
    component: () => import('../views/UserView.vue'),
    children: [
      {
        path: 'UserPage',
        name: 'userPage',
        redirect: '/User/UserPage/MainUser',
        component: () => import('../pages/UserPage.vue'),
        children: [{
          path: 'MainUser',
          name: 'MuserInfo',
          component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/components/common/UserInfo/MainPage.vue'),
          meta: {
            keepAlive: true
          },
          props: true,
        },
        {
          path: 'ChangeInfo',
          name: 'ChangeInfo',
          component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/UserPages/AlertInfo.vue'),
          meta: {
            keepAlive: true
          }
        },
        {
          path: 'AlertInfo',
          name: 'AlertInfo',
          component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/UserPages/AlertInfo.vue')
        },

        ]
      },

      {
        //微博主页
        path: 'UserHome',
        redirect: '/User/UserHome/HomePage1/HomePage',
        component: () => import('../pages/UserHome.vue'),
        //一级子页面
        children: [
          {
            path: 'HomePage1',
            name: 'HomePage1',
            component: () => import('../pages/HomePages/HomePage1.vue'),
            props: true,
            //二级子页面
            children: [{
              path: 'HomePage',
              name: 'HomePage',
              component: () => import('../pages/HomePages/HomePage'),
            }]
          },
          //一级子页面
          {
            path: 'UserInfo',
            name: 'userInfo',
            component: () => import('../pages/UserPages/UserInfo.vue'),
            children: [
              //主用户信息页
              {
                name: 'RandomInfo',
                path: 'RandomInfo/:path',
                component: () => import('../components/common/UserInfo/MainPage.vue'),
                props: true,
              },
              {
                name: 'FansFollow',
                path: 'FansFollow/:carryInfo',
                component: () => import('../components/common/UserInfo/FansFollow.vue'),
                props: true,
              },
              // 用户粉丝页
            ]
          },

          // {
          //   name: 'userInfo',
          //   path: 'User/:path',
          //   component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/components/common/UserInfo/MainPage.vue'),
          //   props: true,
          // },
          {
            name: 'comInfo',
            path: 'comInfo/:info',
            component: () => import('/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/pages/ComentInfo.vue'),

          }
        ]
      },
      {
        path: 'UserHot',
        component: () => import('../pages/UserHot.vue')
      },
      {
        path: 'Topics/:type/:topic',
        name: 'TopicsPage',
        component: () => import('../pages/TopicsPage.vue'),
        props: true,
      }
    ]
  },
  {
    path: '/SignIn',
    component: () => import('../views/SignIn.vue')
  },
  {
    path: '/SignUp',
    component: () => import('../views/SignUp.vue')
  },
  {
    path: '/ToSign',
    component: () => import("../views/ToSign.vue")
  }
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
