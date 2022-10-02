<template>
  <div>
    <v-hover>
      <v-alert
          slot-scope="{hover}"
          class="alert"
          :value="notification"
          color="white"
          :style="
          `bottom: ${20 + 20 * notificationsLength}px; `
          + `right: ${notificationsLength === 0 ? '-250' : '50'}px; `
          + (!hover ? 'box-shadow: 3px 3px 3px darkgrey;' : 'box-shadow: 3px 3px 3px 3px darkgrey;')"
          id="alert"
      >
        <v-flex>
          <strong>
            <user-link :user="user" class-name="" size="24"/>
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
  mounted() {
    const alert = document.getElementById('alert');
    setTimeout(() => alert.classList.add('created'), 1);
  },
  computed: {
    avatarColor() {
      const colors = ['pink', 'purple', 'deep-purple',
        'indigo', 'blue', 'cyan', 'teal', 'orange', 'yellow', 'amber'];
      return colors[Math.floor(Math.random() * (9 + 1))];
    },

    notificationsLength() {
      const multiply = this.index === 0 ? 1 : 5;
      console.log(this.index * multiply)
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
  box-shadow: 3px 3px 3px darkgrey;
}

.alert.created {
  transform: translateX(-300px);
  transition: all .3s ease-in-out;
}
</style>