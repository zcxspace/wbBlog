import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/User',
    name: 'home',
    component: () => import('../views/ViewerView.vue')
  },

  {
    path: '/User',
    component: () => import('../views/UserView.vue'),
    children: [
      {
        path: 'UserPage',
        component: () => import('../pages/UserPage.vue')
      },
      {
        path: 'UserHome',
        component: () => import('../pages/UserHome.vue')
      }
    ]
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
