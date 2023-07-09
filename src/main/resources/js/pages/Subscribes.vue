<template>
  <v-container>
    <v-layout justify-space-around>
      <v-list>
        <v-list-tile v-if="subscribes.length !== 0" v-for="item in subscribes">
          <user-link
              :key="item.id"
              :user="item.channel"
              size="24"
          />
          <v-btn @click="changeSubscribe(item.channel.id)">
            Unsubscribe
          </v-btn>
        </v-list-tile>
        <h2 v-if="subscribes.length === 0">You have no subscriptions &#128578;</h2>
      </v-list>
    </v-layout>
  </v-container>
</template>

<script>
import profileApi from "../api/profile";
import UserLink from "../components/utils/UserLink.vue";

export default {
  name: "Subscribers",
  data() {
    return {
      subscribes: [],
    };
  },
  components: {
    UserLink,
  },
  methods: {
    async changeSubscribe(subscribeId) {
      await profileApi.changeSubscription(subscribeId);

      const subscriptionIndex = this.subscribes.findIndex(item => item.channel.id === subscribeId);
      const subscription = this.subscribes[subscriptionIndex];

      this.subscribes = [
        ...this.subscribes.slice(0, subscriptionIndex),
        {
          ...subscription,
          active: !subscription.active
        },
        ...this.subscribes.slice(subscriptionIndex + 1)
      ];
      await this.getSubscriptionList();
    },
    async getSubscriptionList() {
      const response = await profileApi.subscriptionList(this.$store.state.profile.id);
      this.subscribes = await response.json();
    }
  },
  async beforeMount() {
    await this.getSubscriptionList();
  }
};
</script>

<style scoped>

</style>