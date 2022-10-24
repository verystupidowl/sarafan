<template>
  <div>
    <v-layout row>
      <v-text-field
          label="New message"
          placeholder="Write something"
          v-model="text"
          @keyup.enter="save"
      />
      <v-btn @click="save">
        Save
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
  name: "message-form",
  props: ['messageAttr'],
  data() {
    return {
      text: '',
      id: '',
    }
  },
  components: {
    Error
  },
  watch: {
    messageAttr(newVal) {
      this.text = newVal.text;
      this.id = newVal.id;
    }
  },
  methods: {
    ...mapActions(['addMessageAction', 'updateMessageAction']),
    save() {
      if (this.text.trim() !== '') {
        const message = {
          id: this.id,
          text: this.text,
        }
        if (this.id) {
          this.updateMessageAction(message);
        } else {
          this.addMessageAction(message);
        }
        this.text = '';
        this.id = '';
      }
    },
  },
  computed: {
    errorHandler() {
      return this.$store.getters.messageErrorGetter;
    }
  }
}
</script>

<style>
</style>