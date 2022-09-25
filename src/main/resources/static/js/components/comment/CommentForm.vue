<template>
  <v-layout row class="px-3">
    <v-text-field
        label="Add Comment"
        placeholder="Write something"
        v-model="text"
        v-on:keyup.enter="save"
    />
    <v-btn @click="save">
      Add
    </v-btn>
  </v-layout>
</template>

<script>
import {mapActions} from "vuex";

export default {
  name: "CommentForm",
  props: ['messageId'],
  data() {
    return {
      text: ''
    }
  },
  methods: {
    ...mapActions(['addCommentAction']),
    async save() {
      console.log(this.text)
      if (this.text.trim() !== '') {
        await this.addCommentAction({
          text: this.text,
          messageId: this.messageId
        })
        this.text = ''
      }
    }
  }
}
</script>

<style scoped>

</style>