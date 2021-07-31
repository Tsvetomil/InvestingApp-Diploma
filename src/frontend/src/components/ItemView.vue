<template>
  <div>
    <NavBar/>
    <Logo/>
    <h1>{{item.caption}}</h1>
    <img onclick="image()" class="img-file" :src="`${publicPath}images/${item.imgName}`">
    <div class="container">
      <table>
        <tr>
          <th>{{item.toRaise}} лв в замяна на <span style="color:#ca4949">{{item.equity}}% от компанията</span></th>
          <th>{{item.phone}}</th>
          <th>{{item.companyName}}</th>
          <th><a href="javascript:void(0)" v-on:click="this.testFunc"><i>{{item.website}}</i></a></th>
          <th>{{item.city}}</th>
        </tr>
        <tr>
          <td><h4>Средства които се търсят</h4></td>
          <td><h4>Телефонен номер</h4></td>
          <td><h4>Име на компанията</h4></td>
          <td><h4>Уебсайт на компанията</h4></td>
          <td><h4>Град</h4></td>
        </tr>
      </table>


    </div>
    <div class="container-desc">
      <h3>Описание</h3>
      <p>{{item.description}}</p>
      <h3 v-if="item.reasonsToInvest">Причини да инвестирате в компанията</h3>
      <div class="cr-reasons-to-invest">
        <p>{{item.reasonsToInvest}}</p>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import VueAxios from "vue-axios";
import axios from "axios";
import Logo from "@/components/Logo";
import NavBar from "@/components/NavBar";
Vue.use(VueAxios, axios)
export default {
  components: {
    NavBar,
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
  },
  methods: {
    testFunc: function() {
      let website = this.item.website;
      if(!website.includes("http")){
        website = "http://" + website;
      }
      window.location.href = website;
          // this.item.website;
    }
  }
}
</script>
<style scoped>
table{
  border-spacing: 10px 10px;
}
h4{
  color: #c3bcbc;
}
.container-desc{
  margin-top: 100px;
  background-color: white;
  height: 100%;
  border: 3px solid #15bf3e;
  padding: 10px;
  margin-left: 15%;
  margin-right: 15%;
}
.cr-reasons-to-invest{
  background-color: #d05858;
}
img {
  width: 50%;
  height: auto;
}
p{
  white-space: pre-wrap;
}
</style>
