<template>
  <div>
    <v-hover>
      <v-alert
          slot-scope="{hover}"
          class="alert"
          :value="notification"
          color="white"
          :style="`bottom: ${20 * notificationsLength}px; ` + (!hover ? 'box-shadow: 3px 3px 3px darkgrey;' : 'box-shadow: 3px 3px 3px 3px darkgrey;')"
      >
        <v-flex>
          <strong>
            <user-link :user="user" class-name="" size="24"></user-link>
          </strong>
          <span style="color: rgba(0, 0, 0, 0.87)">{{ actionText }}</span>
          <v-btn @click="changeSubscription">{{ isSubscribed ? 'Dismiss' : 'Approve' }}</v-btn>
          <v-btn class="shrink" fab small round @click="closeNotification">&#10006;</v-btn>
        </v-flex>
      </v-alert>
    </v-hover>
  </div>
</template>

<script>
import profileApi from "../../api/profile";
import UserLink from "../comment/UserLink.vue";

export default {
  name: "Notification",
  props: [
    'notification',
    'alert',
    'actionText',
    'index'
  ],
  components: {
    UserLink
  },
  data() {
    return {
      isSubscribed: false,
      user: {
        id: this.notification.authorId,
        name: this.notification.username
      },
    }
  },
  computed: {
    avatarColor() {
      const colors = ['pink', 'purple', 'deep-purple',
        'indigo', 'blue', 'cyan', 'teal', 'orange', 'yellow', 'amber'];
      return colors[Math.floor(Math.random() * (9 + 1))];
    },

    notificationsLength() {
      const multiply = this.index === 0 ? 1 : 5;
      return this.index * multiply;
    }
  },
  methods: {
    async changeSubscription() {
      await profileApi.changeSubscriptionStatus(this.notification.authorId);
      this.isSubscribed = !this.isSubscribed;
    },
    closeNotification(notification) {
      this.$emit('close-notification', notification);
    }
  }
}
</script>

<style scoped>
.alert {
  padding-right: 50px;
  margin-left: auto;
  text-align: right;
  float: right;
  position: fixed;
  right: 50px;
  box-shadow: 3px 3px 3px darkgrey;
}
</style>