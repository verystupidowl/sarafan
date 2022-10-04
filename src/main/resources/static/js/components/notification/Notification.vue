<template>
  <v-hover>
    <v-alert
        slot-scope="{hover}"
        :class="'alert ' + `${className}`"
        :value="notification"
        color="white"
        :style="
          `bottom: ${20 + 20 * notificationsLength}px; `
          // + `right: ${notificationsLength === 0 ? '-250' : '50'}px; `
          + (!hover ? 'box-shadow: 3px 3px 3px darkgrey;' : 'box-shadow: 3px 3px 3px 3px darkgrey;')"
        id="alert"
    >
      <strong>
        <user-link :user="user" class-name="" size="24"/>
      </strong>
      <span style="color: rgba(0, 0, 0, 0.87)">{{ actionText }}</span>
      <v-btn @click="changeSubscription">{{ isSubscribed ? 'Dismiss' : 'Approve' }}</v-btn>
      <v-btn class="shrink" fab small round @click="closeNotification">&#10006;</v-btn>
    </v-alert>
  </v-hover>
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
      className: '',
    }
  },
  mounted() {
    this.notificationsLength === 0 ? this.className = 'created' : this.className = '';
    setTimeout(() => this.className = 'destroyed', 9700);
  },
  beforeDestroy() {
    this.className = 'destroyed';
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
    },
  },
  methods: {
    async changeSubscription() {
      await profileApi.changeSubscriptionStatus(this.notification.authorId);
      this.isSubscribed = !this.isSubscribed;
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
  /*transform: translateX(-300px);*/
  /*transition: 1s ease-in-out;*/
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
    opacity: .3;
    right: -300px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
}

@-moz-keyframes fadein {
  from {
    opacity: .3;
    right: -500px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
}

@-webkit-keyframes fadein {
  from {
    opacity: .3;
    right: -300px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
}

@-ms-keyframes fadein {
  from {
    opacity: .3;
    right: -300px;
  }
  to {
    opacity: 1;
    right: 50px;
  }
}

@-o-keyframes fadein {
  from {
    opacity: .3;
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
    opacity: .3;
    right: -500px;
  }
}

@-moz-keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: .3;
    right: -500px;
  }
}

@-webkit-keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: .3;
    right: -500px;
  }
}

@-ms-keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: .3;
    right: -500px;
  }
}

@-o-keyframes fadeOut {
  from {
    opacity: 1;
    right: 50px;
  }
  to {
    opacity: .3;
    right: -500px;
  }
}
</style>