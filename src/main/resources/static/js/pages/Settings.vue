<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <h1>Settings</h1>
        <v-layout row justify-space-between>
          <v-layout column>
<!--            <h5>Your notification types: {{ this.$store.state.profile.notificationTypes }}</h5>-->
            <h2>Notification settings:</h2>
            <v-checkbox class="check" v-model="selected" label="SUBSCRIBE" value="SUBSCRIBE"></v-checkbox>
            <v-checkbox class="check" v-model="selected" label="NEW_POSTS" value="NEW_POSTS"></v-checkbox>
          </v-layout>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapActions} from "vuex";

export default {
  name: "Settings",
  data() {
    return {
      selected: []
    }
  },
  methods: {
    ...mapActions(['changeNotificationAction']),
    getNotification() {
      return this.$store.state.profile.notificationTypes;
    },
  },
  watch: {
    selected(newVal) {
      this.changeNotificationAction(newVal);
    }
  },
  beforeMount() {
    this.selected = this.getNotification();
  }
}
</script>

<style scoped>
</style>