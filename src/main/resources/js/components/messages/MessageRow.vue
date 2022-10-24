<template>
  <v-card class="my-2">
    <v-card-text primary-title>
      <user-link
          :user="message.author"
          size="48"
      />
      <div class="pt-3">
        {{ message.text }}
      </div>
    </v-card-text>
    <media v-if="message.link" :message="message"/>
    <v-card-actions>
      <div v-if="message.author.id === this.$store.state.profile.id">
        <v-btn value="Edit" @click="edit" small flat round>Edit</v-btn>
        <v-btn icon @click="del" small>
          <v-icon>delete</v-icon>
        </v-btn>
      </div>
    </v-card-actions>
    <div v-if="message.edited" class="edited" :title="'Edited At: ' + message.editedDate">Edited</div>
    <comment-list :comments="message.comments" :message-id="message.id"/>
  </v-card>
</template>

<script>
import {mapActions} from 'vuex';
import Media from '../media/Media.vue';
import CommentList from "../../pages/CommentList.vue";
import UserLink from "../utils/UserLink.vue";

export default {
  props: ['message', 'editMessage'],
  components: {UserLink, CommentList, Media},
  methods: {
    ...mapActions(['loadPageAction']),
    ...mapActions(['removeMessageAction']),
    edit() {
      this.editMessage(this.message);
    },
    del() {
      this.removeMessageAction(this.message);
    }
  },
}
</script>

<style scoped>

.edited {
  margin-left: auto;
  float: right;
  padding-right: 50px;
  color: #7d7f7d;
  display: flex;
  text-align: right;
}

</style>