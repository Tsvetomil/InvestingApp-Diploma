<template>
  <div class="main">
    <NavBar/>
    <div class="container">
      <Logo/>
      <div class="text-overlay">
        <p>Ще получите инструкции на имейла си.</p>
      </div>
      <div id="loading"></div>
      <div class="form-overlay">
        <div class="sign-in-form">
          <form class="sign-in">
            <input type="email" id="email" placeholder="Email" required/>
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
    send(){

    }
  }
}
</script>
<style>
</style>