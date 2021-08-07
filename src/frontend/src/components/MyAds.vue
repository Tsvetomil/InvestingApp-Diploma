<template>
  <div class="main">
    <NavBar/>
    <ItemList/>
  </div>
</template>

<script>
import ItemList from './ItemListByUser'
import axios from "axios";
import NavBar from "@/components/NavBar";
export default {
  components: {
    NavBar,
    ItemList
  },
  data() {
    return {
      items: null,
      authorized: false
    }
  },
  name: 'Home',
  props: {
    msg: String
  },
  mounted() {
    axios.get("/api/users/isAuthorized").then(resp => {
      if(resp.data.status === 200){
        this.authorized = true;
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
#get-financing {
  margin-right: 100px;
}
.menu-item {
  /*flex: 1;*/
  /*text-align: center;*/
  /*display: flex;*/
  /*justify-content: center;*/
  /*align-items: center;*/
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
li {
  list-style-type: none;
}
</style>