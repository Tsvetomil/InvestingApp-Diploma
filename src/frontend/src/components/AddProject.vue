<template>
  <div class="container">
    <Logo/>
    <div class="text-overlay">
      <h2>Find Your Investors!</h2>
      <p>Please fill out the form to get started</p>
    </div>

    <div class="form-overlay">
      <div class="sign-in-form">
        <form id="add-project-form" class="sign-in">
          <div class="inputs-row">
            <input type="text" id="fname" placeholder="First Name*" required/>
            <input type="text" id="lname" placeholder="Last Name*" required/>
          </div>
          <div class="inputs-row">
            <input type="text" id="companyName" placeholder="Company Name*" required/>
            <input type="email" id="email" placeholder="Email*" required/>
          </div>
          <input type="number" id="phone" placeholder="Phone Number*" required/>
          <input type="text" id="caption" placeholder="Caption for the project*" required/>
          <label for="toRaise">How much are you looking to raise?*</label>
          <select id="toRaise" name="toRaise" required>
            <option selected="true" disabled="disabled">Please Choose</option>
            <option value="$0 - $100K"> $0 - $100K </option>
            <option value="$100K - $500K"> $100K - $500K </option>
            <option value="$500K - $1M"> $500K - $1M </option>
            <option value="$1M+"> $1M+  </option>
          </select>
          <label for="desc">Description (Why should investors invest in you?)</label>
          <textarea id="desc" rows="4" cols="50">
          </textarea>
          <label for="file-input"> Thumbnail for the project </label>
          <input type="file" accept="image/*" @change="onFileSelected" id="file-input" ref="uploadImage" required>
          <div id="loading"></div>
          <p class="error-msg" v-if="errors.length">
            {{this.errors[0].response.data.msg}}
          </p>
        </form>
        <button type="submit" class="submit-button" v-on:click="submit()">Submit</button>
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
      selectedImage: null,
      msg: "",
      errors: []
    }
  },
  methods: {
    onFileSelected() {
      this.selectedImage = this.$refs.uploadImage.files[0];
    },
    submit: async function() {
      if(!this.validateForm()){
        return;
      }
      const loader = document.querySelector("#loading");
      loader.classList.add("display");
      this.errors = [];
      let fname = document.getElementById("fname").value;
      let lname = document.getElementById("lname").value;
      let companyName = document.getElementById("companyName").value;
      let email = document.getElementById("email").value;
      let phone = document.getElementById("phone").value;
      let description = document.getElementById("desc").value;
      let toRaise = document.getElementById("toRaise").value;
      let imgName = document.getElementById("file-input").files[0].name;
      let caption = document.getElementById("caption").value;

      let resp = await this.axios.post("/api/project/add", {
        email: email,
        fname: fname,
        lname: lname,
        companyName: companyName,
        phone: phone,
        description: description,
        toRaise: toRaise,
        caption: caption,
        imgName: imgName
      }).catch(e =>
          this.errors.push(e)
      )


      if(resp.status === 200){
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };
        let fd = new FormData();
        fd.append("imageFile", this.selectedImage);
        resp = await this.axios.post("/api/project/uploadImage", fd, config).catch(e =>
            this.errors.push(e)
        )
        if(resp.status === 200) {
          this.$router.push('/');
        }
      }
      loader.classList.remove("display")
    },
    validateForm: function(){
      let elements = document.getElementById("add-project-form").elements;

      for (let i = 0; i < elements.length; i++) {
        let element = elements[i];
        if(element.required){
          if(element.value === ""){
            return false;
          }
          if(element.tagName === 'SELECT'){
            if(element.selectedOptions[0].disabled){
              // element.style.borderColor = "red"
              return false;
            }
          }
        }
      }
      return true;
    }
  }
}
</script>
<style>
.inputs-row {
  /*float:left;*/
  display: flex;
  flex-direction: row;
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
/*button {*/
/*  border-radius: 20px;*/
/*  border: 1px solid #009345;*/
/*  background-color: #009345;*/
/*  color: #fff;*/
/*  font-size: 1rem;*/
/*  font-weight: bold;*/
/*  padding: 10px 40px;*/
/*  letter-spacing: 1px;*/
/*  text-transform: uppercase;*/
/*  cursor: pointer;*/
/*}*/
/*button:active {*/
/*  transform: scale(.9);*/
/*}*/

/*button:focus {*/
/*  outline: none;*/
/*}*/
/*.sign-up-overlay button {*/
/*  border: 1px solid #000000;*/
/*  background-color: #2a342f;*/
/*}*/
.form-overlay {
  display: inline-flex;
  position: relative;
  align-items: center;
  margin: auto;
}
input{
  margin-right: 10px;
  margin-left: 10px;
}
select{
  background-color: #c6e7ae;
  width: 95%;
  height: 30px;
  position: inherit;
  /*margin-left: 20px;*/
}
label{
  display: flex;
  flex-direction: row;
  /*margin: auto;*/
  margin-top: 35px;
  margin-bottom: 6px;
}
#desc{
  width: 95%;
  border: 1px solid #cdcdcd;
  resize: vertical;
  background-color: #ffffff;
  font-size: 16px;
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
.error-msg {
  color: red;
  font-weight: bold;
  font-style: italic;
}
#caption{
  width: 85%;
}
</style>