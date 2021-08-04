import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Home from './components/Home.vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import AddProject from './components/AddProject.vue'
import ItemView from './components/ItemView.vue'
import MyAds from './components/MyAds.vue'
import EditItem from './components/EditItem.vue'
import ForgottenPassword from './components/ForgottenPassword.vue'
import ResetPassword from './components/ResetPassword.vue'
import VModal from 'vue-js-modal'

Vue.use(VueRouter, VModal);

const routes = [
  {path: '/login', component: Login},
  {path: '/', component: Home},
  {path: '/register', component: Register},
  {path: '/raise-capital', component: AddProject},
  {path: '/project', component: ItemView},
  {path: '/my-ads', component: MyAds},
  {path: '/project/edit', component: EditItem},
  {path: '/reset-password', component: ForgottenPassword},
  {path: '/password-reset', component: ResetPassword}
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
