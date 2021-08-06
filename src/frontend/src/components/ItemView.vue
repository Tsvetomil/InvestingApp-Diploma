<template>
  <div v-if="ready">
    <NavBar/>
    <Logo/>
    <h1>{{item.caption}}</h1>
    <modal
        name="img"
        transition="scale"
        :height="700"
        :width="1200"
        classes="modal-img"
    >
      <div
          slot="top-right"
          @click="$modal.hide('img')"
      >
      </div>
      <img :src="`${publicPath}images/${item.imgName}`" />
    </modal>
    <img @click="$modal.show('img')" class="img-file" :src="`${publicPath}images/${item.imgName}`">
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
    <CommentSection/>
  </div>
</template>
<script>
import Vue from 'vue';
import VueAxios from "vue-axios";
import axios from "axios";
import Logo from "@/components/Logo";
import NavBar from "@/components/NavBar";
import CommentSection from "@/components/CommentSection";
Vue.use(VueAxios, axios)
export default {
  components: {
    CommentSection,
    NavBar,
    Logo
  },
  name:"ItemView",
  data()
  {
    return{item: undefined, publicPath: process.env.BASE_URL, ready: false}
  },
  mounted()
  {
    let url_string = window.location.href;
    let url = new URL(url_string);
    let id = url.searchParams.get("id")

    Vue.axios.get('/api/project/' + id)
        .then( (resp) => {
          this.item = resp.data;
          this.ready = true
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
.img-file{
  cursor: pointer;
}
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
  /*border: 3px solid #15bf3e;*/
  padding: 10px;
  margin-left: 15%;
  margin-right: 15%;
}
.cr-reasons-to-invest{
  background-color: #f1e6e6;
}
img {
  width: 50%;
  height: auto;
}
p{
  white-space: pre-wrap;
}
</style>
<style lang="scss">

.scale-enter-active,
.scale-leave-active {
  transition: all 0.5s;
}
.scale-enter,
.scale-leave-active {
  opacity: 0;
  transform: scale(0.3) translateY(24px);
}
.modal-img{
  background-color: transparent;
  border-radius: 40px;
}
.modal-img img{
  width: 1200px;
  height: 700px;
}
</style>
