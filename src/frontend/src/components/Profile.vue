<template>
  <div class="main" v-if="ready">
    <NavBar/>
    <table>
      <tr>
        <th><span class="th-text">Име</span></th>
        <td>{{ this.user.firstName }}</td>
      </tr>
      <tr>
        <th><span class="th-text">Фамилия</span></th>
        <td>{{ this.user.lastName }}</td>
      </tr>
      <tr>
        <th><span class="th-text">Имейл</span></th>
        <td>{{ this.user.email }}</td>
      </tr>
    </table>
    <table>
      <tr>
        <modal name="email-change" transition="pop-out" :width="500" :height="350">
          <h3>Въведете имейл</h3>
          <input type="email" id="email" required placeholder="Имейл"/>
          <h3>Въведете парола за потвърждение</h3>
          <input @keyup.enter="changeEmail" type="password" id="password" required placeholder="Парола"/>
          <p v-show="this.error === true">Грешна парола</p>
          <button type="submit" class="edit-button" v-on:click="changeEmail">Смяна</button>
          <div class="loading"></div>
        </modal>
        <button type="submit" class="edit-button" v-on:click="$modal.show('email-change')">Смяна на Имейл</button>
      </tr>
      <tr>
        <modal name="pass-change" transition="pop-out" :width="500" :height="350">
          <h3>Въведете текущата си парола</h3>
          <input type="password" id="curr-pass" required placeholder="Парола"/>
          <h3> Въведете нова парола</h3>
          <input @keyup.enter="changePass" type="password" id="new-pass" required placeholder="Нова Парола"/>
          <p v-show="this.error === true">Грешна парола</p>
          <button type="submit" class="edit-button" v-on:click="changePass">Смяна</button>
          <div class="loading"></div>
        </modal>
        <button type="submit" class="edit-button" v-on:click="$modal.show('pass-change')">Смяна на парола</button>
      </tr>
    </table>
    <modal name="error" transition="pop-out" :width="300" :height="200">
      <h2>Грешна парола</h2>
    </modal>
    <modal name="success-email" transition="pop-out" :width="500" :height="80">
      <h2>Имейла ви беше сменен успешно</h2>
    </modal>
    <modal name="success-pass" transition="pop-out" :width="500" :height="80">
      <h2>Паролата ви беше сменен успешно</h2>
    </modal>
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
      user: null,
      ready: false,
      error: false
    }
  },
  mounted()
  {
    Vue.axios.get('/api/users/user')
        .then( (resp) => {
          this.ready = true;
          this.user = resp.data.entity;
        })
  },
  methods: {
    async changeEmail() {
      const loader = document.querySelector(".loading");
      loader.classList.add("display");
      let email = document.getElementById("email").value;
      let password = document.getElementById("password").value;
      if(email && password) {
        let resp = await this.axios.post('/api/users/change-email',{
          email: email,
          password: password
        })
        if(resp.data.status === 200) {
          this.$modal.hide('email-change');
          this.$modal.show("success-email");
        } else{
          this.error = true
          setTimeout(() => this.error = false, 2500)
        }
      }
      loader.classList.remove("display")
    },
    async changePass() {
      const loader = document.querySelector(".loading");
      loader.classList.add("display");
      let currPass = document.getElementById("curr-pass").value;
      let newPass = document.getElementById("new-pass").value;

      if(currPass && newPass){
        let resp = await this.axios.post('/api/users/user/change-pass',{
          currPassword: currPass,
          newPassword: newPass
        })
        if(resp.data.status === 200) {
          this.$modal.hide("pass-change");
          this.$modal.show("success-pass");
        } else{
          this.error = true
          setTimeout(() => this.error = false, 2500)
        }
      }
      loader.classList.remove("display")
    }
  }
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
input{
  margin-left: 150px;
}
.loading {
  width: 2rem;
  height: 2rem;
  border: 5px solid #f3f3f3;
  border-top: 6px solid #9c41f2;
  border-radius: 100%;
  visibility: hidden;
  animation: spin 1s infinite linear;
  margin: auto;
  left: 220px
}
.loading.display {
  visibility: visible;
}
.th-text{
  font-style: italic;
  letter-spacing: 3px;
}
h3{
  display: flex;
  text-align: left;
  margin-left: 50px;
}
</style>