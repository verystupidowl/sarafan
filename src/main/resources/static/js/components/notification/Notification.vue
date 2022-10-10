<template>
  <v-hover>
    <v-alert
        slot-scope="{hover}"
        :class="'alert ' + `${className}`"
        :value="notification"
        color="white"
        :style="
          `bottom: ${20 + 20 * notificationsLength}px; `
          + (!hover ? 'box-shadow: 3px 3px 3px darkgrey;' : 'box-shadow: 3px 3px 3px 3px darkgrey;')"
    >
      <strong>
        <user-link :user="user" class-name="" size="24"/>
      </strong>
      <span style="color: rgba(0, 0, 0, 0.87)">{{ notification.notificationType === 'SUBSCRIBE' ? 'subscribed to you' : 'posted a new message' }}</span>
      <v-btn @click="clickAction">{{ bntText }}</v-btn>
      <v-btn class="shrink" fab small round @click="closeNotification">&#10006;</v-btn>
    </v-alert>
  </v-hover>
</template>

<script>
import profileApi from "../../api/profile";
import UserLink from "../comment/UserLink.vue";
import notifications from "../../api/notifications";

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
        id: this.notification.senderId,
        name: this.notification.username
      },
      className: '',
    }
  },
  mounted() {
    this.notificationsLength === 0 ? this.className = 'created' : this.className = '';
    setTimeout(() => this.className = 'destroyed', 9700);
  },
  computed: {
    notificationsLength() {
      const multiply = this.index === 0 ? 1 : 5;
      return this.index * multiply;
    },
    bntText() {
      if (this.notification.notificationType === 'SUBSCRIBE') {
        return this.isSubscribed ? 'Dismiss' : 'Approve';
      } else if (this.notification.notificationType === 'NEW_POSTS') {
        return 'Check';
      }
    }
  },
  methods: {
    async clickAction() {
      if (this.notification.notificationType === 'SUBSCRIBE') {
        await profileApi.changeSubscriptionStatus(this.notification.senderId);
        this.isSubscribed = !this.isSubscribed;
      } else if (this.notification.notificationType === 'NEW_POSTS') {
        this.$router.push('/');
      }
    },
    closeNotification(notification) {
      this.$emit('close-notification', notification);
    },
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

.alert.created {
  -webkit-animation: fadein .3s ease-in-out;
  -moz-animation: fadein .3s ease-in-out;
  -ms-animation: fadein .3s ease-in-out;
  -o-animation: fadein .3s ease-in-out;
  animation: fadein .3s ease-in-out;
}

.alert.destroyed {
  -webkit-animation: fadeOut .3s ease-in-out;
  -moz-animation: fadeOut .3s ease-in-out;
  -ms-animation: fadeOut .3s ease-in-out;
  -o-animation: fadeOut .3s ease-in-out;
  animation: fadeOut .3s ease-in-out;
}

@keyframes fadein {
  from {
    opacity: 0;
    right: -300px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
}

@-moz-keyframes fadein {
  from {
    opacity: 0;
    right: -500px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
}

@-webkit-keyframes fadein {
  from {
    opacity: 0;
    right: -300px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
}

@-ms-keyframes fadein {
  from {
    opacity: 0;
    right: -300px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
}

@-o-keyframes fadein {
  from {
    opacity: 0;
    right: -300px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
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