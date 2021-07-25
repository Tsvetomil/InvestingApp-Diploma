<template>
  <div>
    <Logo/>
    <h1>{{item.caption}}</h1>
    <img onclick="image()" class="img-file" :src="`${publicPath}images/${item.imgName}`">
    <div class="container">
      <table>
        <tr>
          <th>{{item.toRaise}}</th>
          <th>{{item.phone}}</th>
          <th>{{item.companyName}}</th>
<!--          <th>{{item.website}}</th>-->
<!--          <th>{{item.country}}</th>-->
<!--          <th>{{item.address}}</th>-->
        </tr>
        <tr>
          <td><h4>Средства които се търсят</h4></td>
          <td><h4>Телефонен номер</h4></td>
          <td><h4>Име на компанията</h4></td>
<!--          <td>website</td>-->
<!--          <td>country</td>-->
<!--          <td>address</td>-->
        </tr>
      </table>
      <p>{{item.description}}</p>
<!--        <div class="column">-->
<!--          <h3>{{item.toRaise}}</h3>-->
<!--          <h4>Looking to raise</h4>-->
<!--        </div>-->
<!--        <div class="column">-->
<!--          <h3>{{item.toRaise}}</h3>-->
<!--          <h4>Looking to raise</h4>-->
<!--        </div>-->
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import VueAxios from "vue-axios";
import axios from "axios";
import Logo from "@/components/Logo";
Vue.use(VueAxios, axios)
export default {
  components: {
    Logo
  },
  name:"ItemView",
  data()
  {
    return{item: undefined, publicPath: process.env.BASE_URL}
  },
  mounted()
  {
    let url_string = window.location.href;
    let url = new URL(url_string);
    let id = url.searchParams.get("id")

    Vue.axios.get('/api/project/' + id)
        .then( (resp) => {
          this.item = resp.data;
          console.warn(resp.data)
        })
  }
}
</script>
<style>
table{
  border-spacing: 50px 0;
}
h4{
  color: #c3bcbc;
}
</style>
