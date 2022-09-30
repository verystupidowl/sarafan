<template>
  <div>
    <v-alert
        class="alert"
        :value="alert"
        color="white"
    >
      <v-flex>
        <strong>
          <user-link :user="user" class-name="" size="24"></user-link>
        </strong>
        <span style="color: rgba(0, 0, 0, 0.87)">subscribed to you</span>
        <v-btn @click="changeSubscription">{{ isSubscribed ? 'Dismiss' : 'Approve' }}</v-btn>
      </v-flex>
    </v-alert>
  </div>
</template>

<script>
import profileApi from 'api/profile';
import UserLink from "./comment/UserLink.vue";

export default {
  name: "Notification",
  props: ['notification', 'alert'],
  components: {
    UserLink
  },
  data() {
    return {
      isSubscribed: false,
      user: {
        id: this.notification.subscriberId,
        name: this.notification.username
      }
    }
  },
  computed: {
    avatarColor() {
      const colors = ['pink', 'purple', 'deep-purple',
        'indigo', 'blue', 'cyan', 'teal', 'orange', 'yellow', 'amber'];
      return colors[Math.floor(Math.random() * (9 + 1))];
    },
  },
  methods: {
    async changeSubscription() {
      await profileApi.changeSubscriptionStatus(this.notification.subscriberId);
      this.isSubscribed = !this.isSubscribed;
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
  bottom: 20px;
  box-shadow: 3px 3px 3px darkgrey;
}
</style>