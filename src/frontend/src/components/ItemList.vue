<template>
  <div>
    <h1>Projects</h1>
    <table v-if="isDataLoaded()">
      <div class="row" v-for="i in Math.ceil(list.length / itemsInRow)" v-bind:key="i">
        <tr class="project" v-for="item in list.slice((i - 1) * itemsInRow, i * itemsInRow)" v-bind:key="item.id">
          <td class="pr-caption">{{ item.caption }}</td>
          <img :src="`${publicPath}images/${item.imgName}`">
          <td class="pr-desc">{{ item.description }}</td>
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
      console.warn(resp.data)
    })
  },
  methods: {
    isDataLoaded: function(){
      return this.list !== undefined;
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
}

.row {
  border: solid 1px #404040;
  margin-left: auto;
  padding: 10px;
  border-collapse: separate;
  border-spacing: 30px;
}
</style>