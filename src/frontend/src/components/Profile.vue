<template>
  <div class="main">
    <NavBar/>
    <table>
      <tr>
        <th>Име</th>
        <td>{{ this.user.firstName }}</td>
      </tr>
      <tr>
        <th>Фамилия</th>
        <td>{{ this.user.lastName }}</td>
      </tr>
    </table>
    <table>
      <tr>
        <th>Имейл</th>
        <td>{{ this.user.email }}</td>
      </tr>
      <tr>
        <button type="submit" ref="item" class="edit-button" v-on:click="editItem(item)">Смяна на парола</button>
      </tr>
    </table>
  </div>
</template>
<script>
import Vue from 'vue';
import axios from 'axios'
import VueAxios from 'vue-axios'
import NavBar from "@/components/NavBar";
Vue.use(VueAxios, axios)

export default {
  components: {NavBar},
  data() {
    return {
      user: null
    }
  },
  mounted()
  {
    Vue.axios.get('/api/users/user')
        .then( (resp) => {
          console.log(resp)
          this.user = resp.data.entity;
        })
  },
}
</script>
<style scoped>
table{
  margin-left: 20%;
  margin-top: 7%;
  font-size: 19px;
  border-spacing: 50px 40px;
  /*display: inline-block;*/
  float: left;
}
</style>