import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Home from './components/Home.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import AddProject from './components/AddProject.vue'

Vue.use(VueRouter);

const routes = [
  {path: '/login', component: Login},
  {path: '/', component: Home},
  {path: '/register', component: Register},
  {path: '/raise-capital', component: AddProject}
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
