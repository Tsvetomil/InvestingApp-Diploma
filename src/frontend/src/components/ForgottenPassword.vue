<template>
  <div class="main">
    <modal
        name="text"
        :width="500"
        :height="100"
    >
      <div
          slot="top-right"
          @click="$modal.hide('text')"
      >
      </div>
      <h2>Изпратихме инструкции за смяна на паролата на имейла ви!</h2>
    </modal>
    <NavBar/>
    <div class="container">
      <Logo/>
      <div class="text-overlay">
        <p>Въведете имейла с който сте се регистрирали.</p>
      </div>
      <div id="loading"></div>
      <div class="form-overlay">
        <div class="sign-in-form">
          <form class="sign-in">
            <input @keyup.enter="send"  type="email" id="email" placeholder="Email" required/>
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
    async send() {
      let email = document.getElementById("email").value;

      if (email) {
        const loader = document.querySelector("#loading");
        loader.classList.add("display");
        let resp = await this.axios.post("/api/users/forgotten-password", {
          email: email,
        }).catch(e =>
            this.errors.push(e)
        )
        loader.classList.remove("display")
        if(resp.data.status === 200) {
          document.getElementById("email").value = "";
          this.$modal.show('text');
        }
      }
    }
  }
}
</script>
<style>
#loading {
  position: absolute;
  width: 2rem;
  height: 2rem;
  border: 5px solid #f3f3f3;
  border-top: 6px solid #9c41f2;
  border-radius: 100%;
  visibility: hidden;
  animation: spin 1s infinite linear;
  /*margin: auto;*/
  margin-top: 20px;
  margin-left: 350px;
  opacity: 1;
}
#loading.display {
  visibility: visible;
}
@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>