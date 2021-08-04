<template>
  <div>
    <v-dialog/>
    <div v-if="ready">
      <button type="submit" ref="item" class="submit-button" v-on:click="save">Запази</button>
      <Logo/>
      <div contenteditable class="editable-field" id="caption-container"><h1 id=caption-id>{{item.caption}}</h1></div>
      <div id="loading"></div>
      <div class="image-container">
        <img onclick="image()" class="img-file" :src="`${publicPath}images/${item.imgName}`">
        <label for="image" class="label-for-image">Сменете изображението от тук!</label>
        <input type="file" id="image" class="change-image" accept="image/*" @change="onFileSelected" ref="uploadImage"/>
      </div>
      <div class="container">
        <table>
          <tr>
            <th><div contenteditable class="editable-field" onKeypress="if(event.keyCode < 48 || event.keyCode > 57){return false;}"><p id="toRaise-id">{{item.toRaise}}</p></div> лв в замяна на <span style="color:#ca4949"><div contenteditable class="editable-field" onKeypress="if(event.keyCode < 48 || event.keyCode > 57){return false;}"><p id="equity-id">{{item.equity}}</p></div>% от компанията</span></th>
            <th><div contenteditable class="editable-field" onKeypress="if(event.keyCode < 48 || event.keyCode > 57){return false;}"><p id="phone-id">{{item.phone}}</p></div></th>
            <th><div contenteditable class="editable-field"><p id="companyName-id">{{item.companyName}}</p></div></th>
            <th><i><div contenteditable class="editable-field"><p id="website-id">{{item.website}}</p></div></i></th>
            <th><i><div contenteditable class="editable-field"><p id="city-id">{{item.city}}</p></div></i></th>
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
        <div contenteditable class="editable-field" id="desc-id"><p>{{item.description}}</p></div>
        <br><br>
        <h3>Reasons to Invest in the company</h3>
        <div contenteditable class="cr-reasons-to-invest" id="reasons-id">
          <p>{{item.reasonsToInvest}}</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import VueAxios from "vue-axios";
import axios from "axios";
import Logo from "@/components/Logo";
import VModal from 'vue-js-modal'
Vue.use(VModal, VueAxios, axios)
export default {
  components: {
    Logo
  },
  name:"ItemView",
  data()
  {
    return{item: undefined, publicPath: process.env.BASE_URL, selectedImage: null, ready: false}
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
    this.showModal()
  },
  methods: {
    showModal(){
      this.$modal.show('dialog', {
        title: 'Всички полета в пунктираните линии могат да се редактират',
        buttons: [
          {
            title: 'Добре',
            handler: () => {
              this.$modal.hide('dialog')
            }
          }
        ]
      })
    },
    onFileSelected() {
      this.selectedImage = this.$refs.uploadImage.files[0];
    },
    async save(){
      const loader = document.querySelector("#loading");
      loader.classList.add("display");
      //save project first
      let caption = document.getElementById("caption-id").innerText;
      let toRaise = document.getElementById("toRaise-id").innerText;
      let equity = document.getElementById("equity-id").innerText;
      let phone = document.getElementById("phone-id").innerText;
      let companyName = document.getElementById("companyName-id").innerText;
      let website = document.getElementById("website-id").innerText;
      let description = document.getElementById("desc-id").innerText;
      let reasonsToInvest = document.getElementById("reasons-id").innerText;
      let imageName = document.getElementById("image");
      let city = document.getElementById("city-id").innerText;
      if(imageName.value) {
        imageName = document.getElementById("image").files[0].name;
      } else{
        imageName = null;
      }

      let resp = await this.axios.post("/api/project/edit/" + this.item.id, {
        toRaise: toRaise,
        equity: equity,
        companyName: companyName,
        phone: phone,
        website: website,
        description: description,
        caption: caption,
        imgName: imageName,
        reasonsToInvest: reasonsToInvest,
        city: city
      }).catch(e =>
          this.errors.push(e)
      )
      if(resp.status === 200) {
        if (imageName) {
          let config = {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          };
          let fd = new FormData();
          fd.append("imageFile", this.selectedImage);
          await this.axios.post("/api/project/uploadImage", fd, config).catch(e =>
              this.errors.push(e)
          )
          if(resp.status === 200) {
            this.redirect()
          }
        } else{
          loader.classList.remove("display")
          this.redirect();
        }
      }
      this.$modal.show('dialog', {
        title: 'Не успяхме да запазим промените ви!',
        text: 'Моля опитайте отново!',
        buttons: [
          {
            title: 'Добре',
            handler: () => {
              this.$modal.hide('dialog')
            }
          }
        ]
      })
    },
    redirect() {
      this.$modal.show('dialog', {
        title: 'Промените ви бяха успешно запазени',
        buttons: [
          {
            title: 'Добре',
            handler: () => {
              this.$router.push("/my-ads")
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
  position: absolute;
  top:40px;
  right:100px;
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
  border: 3px solid #15bf3e;
  padding: 10px;
  margin-left: 15%;
  margin-right: 15%;
}
.cr-reasons-to-invest{
  background-color: #f1e6e6;
  border-style: dotted;
}
.editable-field{
  border-style: dotted;
}

.image-container {
  position: relative;
  /*width: 50%;*/
}

/*!* Make the image responsive *!*/
.image-container img {
  width: 50%;
  height: auto;
}

/*!* Style the button and place it in the middle of the container/image *!*/
.image-container .change-image {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  background-color: #555;
  color: white;
  font-size: 16px;
  padding: 12px 24px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}
.label-for-image{
  position: absolute;
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  background-color: #555;
  color: white;
  font-size: 16px;
  padding: 12px 24px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}
.image-container .change-image:hover {
  background-color: black;
}
#caption-container{
  margin: auto;
  width: 60%;
  padding: 10px;
}
p{
  white-space: pre-wrap;
}
#loading {
  position: relative;
  width: 5rem;
  height: 5rem;
  border: 20px solid #f3f3f3;
  border-top: 20px solid #9c41f2;
  border-radius: 100%;
  visibility: hidden;
  animation: spin 1s infinite linear;
}
#loading.display {
  visibility: visible;
}
</style>
