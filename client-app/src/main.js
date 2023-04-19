import axios from 'axios'
import Vue from 'vue'
import App from './App.vue'
import Backend from './services/backend'
import router from './router'
import "bootstrap/dist/css/bootstrap.min.css";
import VueRouter from 'vue-router'

Vue.use(VueRouter)

Vue.prototype.$axios = axios
Vue.prototype.$backend = new Backend()

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

