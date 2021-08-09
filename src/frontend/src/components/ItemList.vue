<template>
  <div>
    <h1>Бизнеси</h1>
    <div id="filters">
      <div id="search">
<!--        <h3 id="search-text">Търсене</h3>-->
        <input @keyup.enter="search" type="text" id="search-input" placeholder="Търсене..."/>
        <img id="search-img" src="../pic/search.png" alt="Search" v-on:click="search">
      </div>
      <div class="dropdown">
        <button class="dropbtn">Сортирай</button>
        <div class="dropdown-content">
          <a href="javascript:void(0)" v-on:click="sortItems('mostRecent')">Най-скорошни</a>
          <a href="javascript:void(0)" v-on:click="sortItems('invPriceAsc')">Инвестиция ↑ </a>
          <a href="javascript:void(0)" v-on:click="sortItems('invPriceDesc')">Инвестиция ↓</a>
        </div>
      </div>
      <h3 id="clear-filters" v-on:click="clearFilters">Изчисти филтрите</h3>
    </div>
    <table v-if="isDataLoaded()">
      <div class="row" v-for="i in Math.ceil(list.length / itemsInRow)" v-bind:key="i">
        <tr class="project" v-for="item in list.slice((i - 1) * itemsInRow, i * itemsInRow)" v-bind:key="item.id">
          <td class="pr-companyName"> {{ item.companyName }}</td>
          <td class="pr-caption">{{ item.caption }}</td>
          <img class="img-file" :src="`${publicPath}images/${item.imgName}`">
          <td class="pr-toRaise">Търсена инвестиция: {{ item.toRaise }} лв</td>
          <button type="submit" ref="item" class="submit-button" v-on:click="view(item)">Виж повече</button>
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
Vue.use(VueAxios, axios)
export default {

  name:"ItemList",
  components: {Spinner},
  data()
  {
    return{list: undefined, itemsInRow: 3, publicPath: process.env.BASE_URL}
  },
  mounted()
  {
    Vue.axios.get('/api/project/')
    .then( (resp) => {
      this.list = resp.data;
    })
  },
  methods: {
    isDataLoaded: function(){
      return this.list !== undefined;
    },
    view(item){
      this.$router.push('/project?id=' + item.id);
    },
    async search(){
      this.list = undefined;
      let query = document.getElementById("search-input").value;
      await Vue.axios.get('/api/project/search?q=' + query)
          .then( (resp) => {
            this.list = resp.data;
          })
    },
    async sortItems(sortBy){
      this.list = undefined;
      await Vue.axios.get('/api/project/filter/' + sortBy)
          .then( (resp) => {
            this.list = resp.data;
          })
    },
    clearFilters(){
      this.list = undefined;
      Vue.axios.get('/api/project/')
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

.img-file {
  width: 360px;
  height: 250px;
  background-size: cover;
  /*border: 6px solid green;*/
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
}
.pr-toRaise{
  color: #00a056;
}
</style>
<style scoped>
#filters{
  display: inline-flex;
}
#search{
  display: inline-flex;
}
.dropbtn {
  background-color: #00a056;
  color: white;
  padding: 16px;
  font-size: 16px;
  border: none;
  cursor: pointer;
}

.dropdown {
  position: relative;
  display: inline-block;
  margin-left: 80px;
  margin-top: 13px;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {background-color: #f1f1f1}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown:hover .dropbtn {
  background-color: #3e8e41;
}
#search-img{
  width:26px;
  height: 25px;
  margin-top: 29px;
  cursor: pointer;
}
#clear-filters{
  margin-top:27px;
  margin-left: 60px;
  cursor: pointer;
  color: #818181;
  border-bottom: 2px solid #e5e0e0;
}
#clear-filters:hover{
  border-bottom: 2px solid #2e2437;
  color: #2e2437;
}
</style>