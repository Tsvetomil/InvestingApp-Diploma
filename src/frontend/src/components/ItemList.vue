<template>
  <div>
    <h1>Projects</h1>
    <table>
      <tr class="project" v-for="item in list" v-bind:key="item.id">
        <td>{{ item.caption }}</td>
        <td>{{ item.raised }}</td>
        <td>{{ item.description }}</td>
      </tr>
    </table>
  </div>
</template>
<script>
import Vue from 'vue';
import VueAxios from "vue-axios";
import axios from "axios";
Vue.use(VueAxios, axios)
export default {
  name:"ItemList",
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