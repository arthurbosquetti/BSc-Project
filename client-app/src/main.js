import axios from 'axios'
import { createApp } from 'vue'
import App from './App.vue'
import Backend from './services/backend'
import router from './router'
import "bootstrap/dist/css/bootstrap.min.css";

const app = createApp(App)

app.use(router)
app.config.globalProperties.axios = axios
app.config.globalProperties.$backend = new Backend()

app.mount('#app')
