<template>
  <div class="main">
    <NavBar/>
    <div id="menu">
      <div id="categories">
        <h2> категории</h2>
        <span v-on:click="redirectToEquities">Продажба на бизнес</span>
        <span v-on:click="redirectToEquities">Бизнес активи</span>
      </div>
    </div>
<!--    <ItemList/>-->
  </div>
</template>

<script>
// import ItemList from './ItemList.vue'
import axios from "axios";
import NavBar from './NavBar.vue';
export default {
  components: {
    NavBar
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
    },
    redirectToEquities(){
      this.$router.push("/offers")
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
a{
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
}
.home-text a:link, a:visited{
  text-decoration: none;
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
li {
  list-style-type: none;
}
#menu {
  position: initial;
  margin-top: 10%;
  background-color: #c2c8d4;
  margin-left: 30%;
  margin-right: 30%;
  padding: 20px;
}
span {
  position: relative;
  margin-right: 30px;
  border-bottom: 2px solid #134aab;
  border-spacing: 20px;
  border-collapse: separate;
  padding: 5px;
  cursor: pointer;
}
h2{
  position: relative;
  border-collapse: separate;
  /*padding-bottom: 20px*/
}
#categories{
  background-color: #e9e6e6;
  padding-bottom: 20px;
  border-radius: 50px;
}
span:hover {
  color: #046cff;
}
</style>