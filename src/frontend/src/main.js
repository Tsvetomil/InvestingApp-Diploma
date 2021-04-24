import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Home from './components/Home.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'

Vue.use(VueRouter);

const routes = [
  {path: '/login', component: Login},
  {path: '/', component: Home},
  {path: '/register', component: Register}
]
const router = new VueRouter({
  routes,
  mode: 'history'
})
new Vue({
  el: '#app',
  router,
  render: h => h(App)
});
