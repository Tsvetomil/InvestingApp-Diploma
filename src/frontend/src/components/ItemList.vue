<template>
  <div>
    <h1>Projects</h1>
    <table v-if="isDataLoaded()">
      <div class="row" v-for="i in Math.ceil(list.length / 3)" v-bind:key="i">
        <tr class="project" v-for="item in list.slice((i - 1) * 3, i * 3)" v-bind:key="item.id">
          <td class="pr-caption">{{ item.caption }}</td>
          <td class="pr-raised">{{ item.raised }}</td>
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
    return{list: undefined}
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
  /*position: relative;*/
  display:table-cell;
  border: 1pt solid black;
  padding: 20px;
  margin-right: 20px;
}

.row {
  border: solid 1px #404040;
  margin-left: auto;
  padding: 10px;
}
</style>