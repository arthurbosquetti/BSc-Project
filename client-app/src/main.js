import axios from 'axios'
import Vue from 'vue'
import App from './App.vue'
import Backend from './services/backend'
import router from './router'
import VueRouter from 'vue-router'
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(VueRouter)
Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.prototype.$axios = axios
Vue.prototype.$backend = new Backend()

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

