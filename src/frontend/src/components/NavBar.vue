<template>
  <div class="main">
    <div class="navbar">
      <Logo/>
      <div class="login">
        <a v-show="!authorized" class="login-button" href="/login"> Влизане </a>
        <a v-show="authorized" class="login-button" href="#" @click="logout"> Излизане от профила </a>
        <router-view></router-view>
      </div>
      <div class="menu">
        <div class="menu-item menu-item-left">
          <div class="menu-text">
            <a href="/raise-capital">
              <p class="home-text" id="get-financing"> Вземете финансиране </p>
            </a>
            <router-view></router-view>
          </div>
        </div>
        <div v-show="authorized" class="menu-item menu-item-right">
          <div class="menu-text">
            <a href="/profile">
              <p class="home-text" id="profile"> Профил </p>
            </a>
            <router-view></router-view>
          </div>
        </div>
        <div v-show="authorized && !isAdmin" class="menu-item menu-item-right">
          <div class="menu-text">
            <a href="/my-ads">
              <p class="home-text" id="ad"> Вашите обяви </p>
            </a>
            <router-view></router-view>
          </div>
        </div>
        <div v-show="authorized && isAdmin" class="menu-item menu-item-right">
          <div class="menu-text">
            <a href="/admin-panel">
              <p class="home-text" id="admin-panel"> Админ панел </p>
            </a>
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Logo from "@/components/Logo";
import axios from "axios";
export default {
  components: {
    Logo
  },
  data() {
    return {
      items: null,
      authorized: false,
      isAdmin: false
    }
  },
  name: 'NavBar',
  props: {
    msg: String
  },
  mounted() {
    axios.get("/api/users/isAuthorized").then(resp => {
      if(resp.data.status === 200){
        this.authorized = true;
      }
    })
    axios.get("/api/users/isAdmin").then(resp => {
      if(resp.data.status === 200){
        this.isAdmin = resp.data.entity;
      }
    })
  },
  methods: {
    logout(){
      axios.post("/api/users/logout").then(this.authorized = false)
      this.$router.push('/')
    }
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
  /*flex: 1;*/
  /*text-align: center;*/
  /*display: flex;*/
  /*justify-content: center;*/
  /*align-items: center;*/
}
#get-financing {
  margin-right: 100px;
}
.home-text{
  font-size: 20px;
  color: #818181;
  cursor: pointer;
  border-bottom: 2px solid #e5e0e0;
  width: fit-content;
  margin-right: -120px;
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
.login-button{
  margin-top:7px;
}
li {
  list-style-type: none;
}
.main{
  background: #f1f0f0;
}
</style>