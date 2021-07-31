<template>
  <div id="container">
    <h2>Коментари</h2>
    <div v-if="authorized" id="comments-container">
      <div id="add-comment">
        <textarea id="comment-id" rows="3" placeholder="Добави коментар..."/>
      </div>
      <button type="submit" class="submit-button" v-on:click="addComment()">Добави Коментар</button>
      <div id="comments">
        <div id="comment" v-for="comment in comments" v-bind:key="comment.id">
          <p id="owner">{{comment.owner}}</p>
          <span id="timestamp">{{comment.timestamp}}</span>
          <p id="msg"> {{ comment.message }}</p>
        </div>
      </div>
    </div>
    <div v-else>
      <p>Влезнете в акаунта си за да видите коментарите.</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";
import VModal from 'vue-js-modal'
import VueAxios from "vue-axios";
Vue.use(VModal, VueAxios, axios)

export default {
  name: "CommentSection",
  data() {
    return {
      authorized: false,
      comments: null,
      projectID: null
    }
  },
  mounted() {
    axios.get("/api/users/isAuthorized").then(resp => {
      if(resp.data.status === 200){
        this.authorized = true;
      }
    })

    let url_string = window.location.href;
    let url = new URL(url_string);
    this.projectID = url.searchParams.get("id")
    this.loadComments();
  },
  methods: {
    async loadComments() {
      await Vue.axios.get('/api/comment/all/' + this.projectID)
          .then( (resp) => {
            // this.item = resp.data;
            this.comments = resp.data.entity;
            console.warn(resp.data)
          })
    },
    async addComment() {
      if(!this.authorized){
        return;
      }
      let msg = document.getElementById("comment-id").value;
      if(msg) {
        await this.axios.post("/api/comment/add", {
          message: msg,
          projectID: this.projectID
        }).catch(e =>
            this.errors.push(e)
        )
        document.getElementById("comment-id").value = "";
        await this.loadComments()
        this.$forceUpdate()
      }
    }
  }

}
</script>
<style scoped>
#container{
  background-color: #e7e7e7;
  margin-top: 100px;
  height: 100%;
  border: 3px solid #000000;
  padding: 10px;
  margin-left: 21%;
  margin-right: 21%;
}
#comments-container{
  background-color: white;
  /*margin-top: 100px;*/
  border: 3px solid #000000;
  padding: 10px;
  margin-left: 10%;
  margin-right: 10%;
  display: block;
}
h2{
  position: relative;
  right: 35%
}
textarea{
  background-color: #bac7d6;
  border-radius: 10px;
  resize: none;
  position: inherit;
  margin-right: 50%;
  width: 70%;
}
textarea:focus, input:focus{
  outline: 0;
}
button{
  width: 30%;
  border-radius: 20px;
  border: 1px solid #009345;
  background-color: #009345;
  color: #fff;
  font-size: 10px;
  font-weight: bold;
  padding: 8px 10px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
}
#owner{
  position: relative;
  /*display: block;*/
  right: 40%;
  font-weight: bold;
}
#comments{
  display: block;
  margin-top: 10%;
}
#msg{
  margin-top: -2%;
  text-align: left;
  margin-left: 7%;
  margin-right: 20%
}
#timestamp{
  position: absolute;
  display: block;
  top: 5%;
  left: 80%;
  font-style: italic;
}
#comment{
  position: relative;
  margin-top: 6%;
  border-radius: 8px;
  background-color: #e7e7e7;
  margin-left: 7%;
  margin-right: 7%;
  /*border: black;*/
}
div{
  word-wrap: break-word
}
</style>