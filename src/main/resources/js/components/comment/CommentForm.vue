<template>
  <div>
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
    <div v-if="errorHandler">
      <error :error-handler="errorHandler"></error>
    </div>
  </div>
</template>

<script>
import {mapActions} from "vuex";
import Error from "../error/Error.vue";

export default {
  name: "CommentForm",
  props: ['messageId'],
  data() {
    return {
      text: '',
      rules: [v => v.trim().length <= 50 || 'Max size is 50 characters!'],
    };
  },
  components: {
    Error,
  },
  methods: {
    ...mapActions(['addCommentAction']),
    async save() {
      if (this.text.trim() !== '') {
        await this.addCommentAction({
          text: this.text,
          messageId: this.messageId,
        });
      }
      this.text = '';
    },
  },
  computed: {
    errorHandler() {
      return this.$store.getters.commentErrorGetter;
    },
  },
};
</script>

<style scoped>

</style>