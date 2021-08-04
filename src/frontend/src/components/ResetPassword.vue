<template>
  <div class="main">
    <NavBar/>
    <div class="container">
      <Logo/>
      <div class="text-overlay">
        <p>Въведете нова парола!</p>
      </div>
      <div id="loading"></div>
      <div class="form-overlay">
        <div class="sign-in-form">
          <form class="sign-in">
            <input @keyup.enter="send" type="password" id="pass" placeholder="Парола" required/>
            <button type="button" v-on:click="send">Изпращане</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import axios from 'axios'
import VueAxios from 'vue-axios'
import Logo from "@/components/Logo";
import NavBar from "@/components/NavBar";
Vue.use(VueAxios, axios)

export default {
  components: {NavBar, Logo},
  data() {
    return {
      errors: []
    }
  },
  methods: {
    send() {
      let url_string = window.location.href;
      let url = new URL(url_string);
      let verification_token = url.searchParams.get("verification-code")

      let password = document.getElementById("pass").value;

      if (password) {
        this.axios.post("/api/users/change-password?verification-code=" + verification_token, {
          password: password
        }).catch(e =>
            this.errors.push(e)
        )
      }
      this.$router.push("/login")
    }
  }
}
</script>