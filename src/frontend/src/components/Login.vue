<template>
  <div class="main">
    <NavBar/>
    <div class="container">
      <Logo/>
      <div class="text-overlay">
        <h2>Здравейте отново!</h2>
        <p>Моля влезте за да продължите.</p>
      </div>
      <div id="loading"></div>
      <div class="form-overlay">
        <div class="sign-in-form">
          <form class="sign-in">
            <div class="text-field">
              <input type="text" id="email" class="text-input" required/>
              <label class="floating-text">Имейл</label>
            </div>
            <div class="text-field">
              <input @keyup.enter="login" type="password" id="pass" class="text-input" required/>
              <label class="floating-text">Парола</label>
            </div>
            <p v-if="errors.length">
              Имейла или паролата е неправилна
            </p>
            <a href="#" class="f-pass-b" @click="forgottenPassword">Забравена парола</a>

            <button type="button" v-on:click="login">Влизане</button>
          </form>
        </div>

        <div class="sign-up-overlay">
          <h3> Нямате акаунт ?</h3>
          <router-link to="/register">
            <button>Регистриране</button>
          </router-link>
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
    register: function () {
      this.$router.push('register');
    },
    login: async function() {
      const loader = document.querySelector("#loading");
      loader.classList.add("display");
      this.errors = [];
      let email = document.getElementById("email").value;
      let pass = document.getElementById("pass").value;

      if(email && pass){
        let resp = await this.axios.post("/api/users/login", {
          email: email,
          password: pass
        }).catch(e =>
            this.errors.push(e)
        )

        loader.classList.remove("display")
        if(resp.status === 200){
          this.$router.push('/');
        }
      } else{
        loader.classList.remove("display")
      }
    },
    forgottenPassword(){
      this.$router.push('/reset-password');
    }
  }
}
</script>
<style>
.text-field {
  position: relative;
  margin: 10px 2.5px 20px 2.5px;
}

input {
  display: inline-block;
  color: #444;
  background-color: #fafafa;
  padding: 10px 10px 10px 10px;
}

.text-input:focus {
  background-color:#fff;
  border-color:#32cd32;
}

.floating-text {
  color: #999;
  position: absolute;
  pointer-events: none;
  left: 28px;
  top: -25px;
  transition: 0.2s;
}

input:focus ~ .floating-text, input:valid ~ .floating-text {
  top: -50px;
  left: 15px;
  font-size: small;
  color: #32cd32;
  background-color: #ffffff;
  padding:0 5px 0 5px;
}
.text-overlay {
  position: relative;
  background: #c6e7ae;
  margin-left: auto;
  margin-right: auto;
  width: 40%;
  padding: 10px;
  border-radius: 50px;
}
.container {
  padding: 10px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 8%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  background: #ffffff;
}
.sign-in-form {
  top: 0;
  padding: 50px 60px;
}
input {
  display: block;
  background-color: #c6e7ae;
  padding: 12px 26px;
  margin: 20px 0;
  border: none;
  border-radius: 15px;
  overflow: hidden;
  border-bottom: 1px solid #ddd;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, .4),
  0 -1px 1px #fff,
  0 1px 0 #fff;
  outline: none;
}
button {
  border-radius: 20px;
  border: 1px solid #009345;
  background-color: #009345;
  color: #fff;
  font-size: 1rem;
  font-weight: bold;
  padding: 10px 40px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
}
button:active {
   transform: scale(.9);
 }

button:focus {
   outline: none;
 }
.f-pass-b {
  display: block;
  margin: 20px 0;
}
.sign-up-overlay button {
  border: 1px solid #000000;
  background-color: #2a342f;
}
.form-overlay {
  display: inline-flex;
  position: relative;
  align-items: center;
  margin: auto;
}
.sign-up-overlay {
  /*position: relative;*/
  /*left: 30%;*/
  float: right;
}
#loading {
  width: 2rem;
  height: 2rem;
  border: 5px solid #f3f3f3;
  border-top: 6px solid #9c41f2;
  border-radius: 100%;
  visibility: hidden;
  animation: spin 1s infinite linear;
  margin: auto;
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