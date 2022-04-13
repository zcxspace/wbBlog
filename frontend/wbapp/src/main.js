import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '../src/assets/style/overStyle.css'
import '/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/iconfont/wbIcon/iconfont.css'
//全局引入加载组件
import LoadingCom from './components/LoadingComs/LoadingCom.vue'
import SkeletonCom from './components/LoadingComs/SkeletonCom.vue'
createApp(App).use(router).use(store).use(ElementPlus).component('LoadingCom', LoadingCom).component('SkeletonCom', SkeletonCom).mount('#app')
