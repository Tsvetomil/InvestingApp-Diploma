<template>
  <div class="container">
    <Logo/>
    <div class="text-overlay">
      <h2>Добре дошли!</h2>
      <p>Моля попълнете формата за да се регистрирате</p>
    </div>
    <div class="form-overlay">
      <div class="sign-in-form">
        <form class="sign-in" action="#">
          <div class="text-field">
            <input type="text" id="email" class="text-input" required/>
            <label class="floating-text">Имейл</label>
          </div>
          <div class="text-field">
            <input type="text" id="fname" class="text-input" required/>
            <label class="floating-text">Собствено име</label>
          </div>
          <div class="text-field">
            <input type="text" id="lname" class="text-input" required/>
            <label class="floating-text">Фамилно име</label>
          </div>
          <div class="text-field">
            <input type="password" id="pass" class="text-input" required/>
            <label class="floating-text">Парола</label>
          </div>
          <label for="date">Дата на раждане</label>
          <input type="date" id="date" required/>

          <button v-on:click="register">Регистриране</button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import axios from 'axios'
import VueAxios from 'vue-axios'
import Logo from "@/components/Logo";
Vue.use(VueAxios, axios)

export default {
  components: {Logo},
  data() {
    return {
      msg: ""
    }
  },
  methods: {
    register: function () {
      let email = document.getElementById("email").value;
      let fname = document.getElementById("fname").value;
      let lname = document.getElementById("lname").value;
      let pass = document.getElementById("pass").value;
      let date = document.getElementById("date").value;

      if(email && fname && lname && pass && date){
        this.axios.post("/api/users/register", {
          firstName: fname,
          lastName: lname,
          email: email,
          password: pass,
          dateOfBirth: date
        })
        //validate email
        this.$router.push('/login')
      }

    }
  }
}
</script>
<style>
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
</style>