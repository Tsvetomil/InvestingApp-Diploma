<template>
  <div class="main">
    <NavBar/>
    <ItemList/>
  </div>
</template>

<script>
import ItemList from './ItemList.vue'
import axios from "axios";
import NavBar from './NavBar.vue';
export default {
  components: {
    ItemList,NavBar
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
</style>