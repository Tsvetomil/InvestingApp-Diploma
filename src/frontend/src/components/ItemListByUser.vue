<template>
  <div>
    <v-dialog/>
    <h1>Вашите активни обяви</h1>
    <table v-if="isDataLoaded()">
      <div class="row" v-for="i in Math.ceil(list.length / itemsInRow)" v-bind:key="i">
        <tr class="project" v-for="item in list.slice((i - 1) * itemsInRow, i * itemsInRow)" v-bind:key="item.id">
          <td class="pr-companyName"> {{ item.companyName }}</td>
          <td class="pr-caption">{{ item.caption }}</td>
          <img class="img-file" :src="`${publicPath}images/${item.imgName}`">
          <td class="pr-toRaise"><span id="investment-price-text">Търсена инвестиция:</span> <span id="price-text">{{ item.toRaise }}</span> лв</td>
          <button type="submit" ref="item" class="edit-button" v-on:click="editItem(item)">Редактирай</button>
          <button type="submit" ref="item" class="submit-button" v-on:click="showModal(item)">Изтрий</button>
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
import VModal from 'vue-js-modal'
Vue.use(VModal, { dialog: true }, VueAxios, axios)
export default {

  name:"ItemListByUser",
  components: {Spinner},
  data()
  {
    return{list: undefined, itemsInRow: 3, publicPath: process.env.BASE_URL}
  },
  mounted()
  {
    let url_string = window.location.href;
    let url = new URL(url_string);
    let id = url.searchParams.get("id")
    if(id){
      Vue.axios.get('/api/project/admin/user/' + id)
          .then( (resp) => {
            this.list = resp.data;
          })
    }
    else {
      Vue.axios.get('/api/project/user')
          .then((resp) => {
            this.list = resp.data;
          })
    }
  },
  methods: {
    isDataLoaded: function(){
      return this.list !== undefined;
    },
    view(item){
      this.$router.push('/project?id=' + item.id);
    },
    async deleteItem(item) {
      await axios.delete('api/project/remove/' + item.id);
      window.location.reload()
    },
    editItem(item) {
      this.$router.push('/project/edit?id=' + item.id);
    },
    showModal(item) {
      this.$modal.show('dialog', {
        title: 'Сигурни ли сте че искате да продължите',
        text: 'Промените са необратими',
        buttons: [
          {
            title: 'ОТКАЗ',
            handler: () => {
              this.$modal.hide('dialog')
            }
          },
          {
            title: 'Изтриване',
            handler: () => {
              this.deleteItem(item)
            }
          }
        ]
      })
    }
  }
}
</script>
<style scoped>
.submit-button{
  background-color: #ff0000;
  margin-left: 2%;
}
table {
  margin-left: auto;
  margin-right: auto;
}

.project {
  display:table-cell;
  /*border: 1pt solid black;*/
  padding: 20px;
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
  font-style: italic;
}
.pr-toRaise{
  color: #00a056;
}
</style>