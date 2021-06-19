<template>
  <div>
    <h1>Projects</h1>
    <table v-if="isDataLoaded()">
      <tr class="project" v-for="item in list" v-bind:key="item.id">
        <td>{{ item.caption }}</td>
        <td>{{ item.raised }}</td>
        <td>{{ item.description }}</td>
      </tr>
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
  border-collapse: collapse;
}

tr {
  border: 1pt solid black;
}
</style>