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

Vue.use(VueRouter);

const routes = [
  {path: '/login', component: Login},
  {path: '/', component: Home},
  {path: '/register', component: Register},
  {path: '/raise-capital', component: AddProject},
  {path: '/project', component: ItemView},
  {path: '/my-ads', component: MyAds},
  {path: '/project/edit', component: EditItem}
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
