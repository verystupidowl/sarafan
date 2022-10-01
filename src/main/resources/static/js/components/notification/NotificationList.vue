<template>
  <div>
    <v-list v-for="(notification, index) in notifications">
      <notification
          :key="notification.creationDate"
          :action-text="notificationActionText"
          @close-notification="closeNotification"
          v-if="index <= 6"
          :notification="notification"
          :index="index"
      />
    </v-list>
  </div>
</template>

<script>
import Notification from "./Notification.vue";

export default {
  name: "NotificationList",
  props: [
    'notifications',
    'notificationActionText',
  ],
  components: {
    Notification
  },
  methods: {
    closeNotification(notification) {
      this.$emit('close-notification', notification);
    }
  }
}
</script>

<style scoped>

</style>