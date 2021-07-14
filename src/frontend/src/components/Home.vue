<template>
  <div class="main">
    <div class="navbar">
      <Logo/>
      <div class="login">
        <a class="login-button" href="/login"> Sign In </a>
        <router-view></router-view>
      </div>
      <div class="menu">
        <div class="menu-item menu-item-left">
          <div class="menu-text">
            <a href="/raise-capital">
              <p class="home-text"> Get Funding </p>
            </a>
            <router-view></router-view>
          </div>
        </div>
        <div class="menu-item menu-item-right">
          <div class="menu-text">
            <a href="/">
              <p class="home-text"> Invest </p>
            </a>
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
    <ItemList/>
  </div>
</template>

<script>
import ItemList from './ItemList.vue'
import Logo from "@/components/Logo";
export default {
  components: {
    ItemList,Logo
  },
  data() {
    return {
      items: null
    }
  },
  name: 'Home',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.navbar {
  border-bottom: solid;
  border-width: 1px;
}
.login {
  float:right;
  font-size: 17px;
  margin-right: 50px;
  cursor: pointer;
  color: #436186;
}
a{
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
}
.menu {
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-top: 10px;
  height: 60px;
}
.menu-item {
  flex: 1;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}
.home-text{
  font-size: 20px;
  color: #818181;
  cursor: pointer;
  border-bottom: 2px solid #e5e0e0;
  width: fit-content;
}
.home-text:hover {
  border-bottom: 2px solid #2e2437;
  color: #2e2437;
}
.home-text a:link, a:visited{
  text-decoration: none;
}
.menu-item-left {
  margin-left: 250px;
}
.menu-item-right {
  margin-right: 220px
}
.login a:link, a:visited {
  background-color: white;
  color: black;
  border: 2px solid #008000;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
}

.login-button:hover{
  background-color: green;
  color: white;
}
.login-button:active{
  background-color: green;
  color: white;
}
li {
  list-style-type: none;
}
</style>