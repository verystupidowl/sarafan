<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <message-form :messageAttr="message"/>
      <select v-model="selected">
        <option>Newest</option>
        <option>Oldest</option>
        <option>By name</option>
        <option>By population</option>
      </select>
      <message-row v-for="message in (getSortedMessages || sortedMessages)"
                   :key="message.id"
                   :message="message"
                   :editMessage="editMessage"/>
      <lazy-loader/>
    </v-layout>
  </v-container>
</template>

<script>
import MessageRow from '../components/messages/MessageRow.vue';
import MessageForm from '../components/messages/MessageForm.vue';
import LazyLoader from "../components/messages/LazyLoader.vue";
import {mapActions} from "vuex";

export default {
  components: {
    LazyLoader,
    MessageRow,
    MessageForm
  },
  data() {
    return {
      message: null,
      selected: 'Newest',
      sortedMessages: [],
    }
  },
  computed: {
    getSortedMessages() {
      return this.$store.getters.sortedMessages(this.selected);
    }
  },
  watch: {
    selected(newVal) {
      this.loadPageAction();
      this.sortedMessages = this.$store.getters.sortedMessages(newVal);
    }
  },
  methods: {
    ...mapActions(['loadPageAction']),
    editMessage(message) {
      this.message = message;
    },
  }
}
</script>

<style>
</style>