<template>
  <div>
    <NavBar/>
    <h1>Потребители</h1>
    <div id="filters">
      <div id="search">
        <div class="text-field">
          <input @keyup.enter="search" type="text" id="search-input" required/>
          <label for="search-input" class="floating-text" id="search-text">Търсене...</label>
        </div>
        <img id="search-img" src="../pic/search.png" alt="Search" v-on:click="search">
      </div>
    </div>
    <table v-if="isDataLoaded()">
      <div class="row" v-for="i in Math.ceil(list.length / itemsInRow)" v-bind:key="i">
        <tr class="project" v-for="item in list.slice((i - 1) * itemsInRow, i * itemsInRow)" v-bind:key="item.id">
          <td class="pr-companyName">Потребител: {{ item.firstName }}     {{ item.lastName }}</td>
          <br/>
          <td class="pr-companyName">Дата на раждане: {{ item.dateOfBirth }}</td>
          <br/>
          <td class="pr-companyName">Имейл: {{ item.email }}</td>
          <br/>
          <td class="pr-companyName">Последно влизане: {{ item.lastLogin }}</td>
          <br/>
          <br/>
          <button type="submit" ref="item" class="submit-button" v-on:click="view(item)">Виж обявите</button>
        </tr>
      </div>
    </table>
    <div v-else><spinner/></div>
  </div>
</template>
<script>
import Vue from 'vue';
import VueAxios from "vue-axios";
import axios from "axios";
import Spinner from './Spinner.vue';
import NavBar from "@/components/NavBar";
Vue.use(VueAxios, axios)
export default {

  name:"ItemList",
  components: {NavBar, Spinner},
  data()
  {
    return{list: undefined, itemsInRow: 3, publicPath: process.env.BASE_URL}
  },
  mounted()
  {
    Vue.axios.get('/api/users/all')
        .then( (resp) => {
          this.list = resp.data.entity;
          console.log(resp)
        })
  },
  methods: {
    isDataLoaded: function(){
      return this.list !== undefined;
    },
    view(item){
      this.$router.push('/my-ads?id=' + item.id);
    },
    async search(){
      this.list = undefined;
      let query = document.getElementById("search-input").value;
      await Vue.axios.get('/api/project/search?q=' + query)
          .then( (resp) => {
            this.list = resp.data;
          })
    }
  }
}
</script>
<style>
table {
  margin-left: auto;
  margin-right: auto;
}

.project {
  display:table-cell;
  /*border: 1pt solid black;*/
  border-radius: 40px;
  padding: 20px;
  background-color: #cad3e0;
}

.row {
  /*border: solid 1px #404040;*/
  margin-left: auto;
  padding: 10px;
  border-collapse: separate;
  border-spacing: 30px;
}
.pr-caption{
  display: block;
  justify-content: center;
  font-size: 25px;
  color: #000000;
  /*border: 2px solid green;*/
}
.pr-companyName{
  display: flex;
  color: #436186;
  font-size: 20px;
  font-style: italic;
}
</style>
<style scoped>
#filters{
  display: inline-flex;
}
#search{
  display: inline-flex;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {background-color: #f1f1f1}

#search-img{
  width:26px;
  height: 25px;
  margin-top: 29px;
  cursor: pointer;
}
</style>
<style scoped>
.floating-text {
  color: #999;
  position: absolute;
  pointer-events: none;
  left: 28px;
  top: -4px;
  transition: 0.2s;
}
input:focus ~ .floating-text, input:valid ~ .floating-text {
  top: -30px;
  left: 15px;
  font-size: small;
  color: #000900;
  background-color: transparent;
  padding:0 5px 0 5px;
  pointer-events: none;
}
#search-input{
  margin-top: 10px;
}
#search-text{
  margin-top: 25px;
  margin-left: 18px;

}
</style>