<template>
  <div>
    <h1>Бизнеси</h1>
    <table v-if="isDataLoaded()">
      <div class="row" v-for="i in Math.ceil(list.length / itemsInRow)" v-bind:key="i">
        <tr class="project" v-for="item in list.slice((i - 1) * itemsInRow, i * itemsInRow)" v-bind:key="item.id">
          <td class="pr-companyName"> {{ item.companyName }}</td>
          <td class="pr-caption">{{ item.caption }}</td>
          <img class="img-file" :src="`${publicPath}images/${item.imgName}`">
          <td class="pr-toRaise">Търси се инвестиция от: {{ item.toRaise }} лв</td>
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
  border: 1pt solid black;
  padding: 20px;
  background-color: #dcdfe3;
}

.row {
  border: solid 1px #404040;
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