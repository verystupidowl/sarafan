<template>
  <v-layout>
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
  </v-layout>
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

.alert-destroyed {
  -webkit-animation: fadeOut .3s ease-in-out;
  -moz-animation: fadeOut .3s ease-in-out;
  -ms-animation: fadeOut .3s ease-in-out;
  -o-animation: fadeOut .3s ease-in-out;
  animation: fadeOut .3s ease-in-out;
}

@keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: 0;
    right: -300px;
  }
}

@-moz-keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: 0;
    right: -300px;
  }
}

@-webkit-keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: 0;
    right: -300px;
  }
}

@-ms-keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: 0;
    right: -300px;
  }
}

@-o-keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: 0;
    right: -300px;
  }
}
</style>