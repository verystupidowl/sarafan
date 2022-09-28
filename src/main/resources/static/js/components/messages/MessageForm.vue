<template>
  <div>
    <v-layout row>
      <v-text-field
          label="New message"
          placeholder="Write something"
          v-model="text"
          v-on:keyup.enter="save"
      />
      <v-btn @click="save">
        Save
      </v-btn>
    </v-layout>
    <div v-if="error">
      <p style="color: red">Error {{ errorHandler.status }} {{ errorHandler.message }}</p>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "message-form",
  props: ['messageAttr'],
  data() {
    return {
      text: '',
      id: '',
      error: null
    }
  },
  watch: {
    messageAttr(newVal) {
      this.text = newVal.text;
      this.id = newVal.id;
    }
  },
  methods: {
    ...mapActions(['addMessageAction', 'updateMessageAction']),
    ...mapGetters(['errorGetter']),
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
          this.error = this.errorGetter;
          setTimeout(() => this.error = null, 5000);
        }
        this.text = '';
        this.id = '';
      }
    },
  },
  computed: {
    errorHandler() {
      return this.$store.getters.errorGetter;
    }
  }
}
</script>

<style>
</style>