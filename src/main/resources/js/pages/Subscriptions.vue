<template>
  <v-container>
    <v-layout justify-space-around>
      <v-list>
        <v-list-tile v-if="subscriptions.length !== 0" v-for="item in subscriptions">
          <user-link
              :user="item.subscriber"
              size="24"
          />
          <v-btn @click="changeSubscriptionStatus(item.subscriber.id)">
            {{ item.active ? "Dismiss" : "Approve" }}
          </v-btn>
        </v-list-tile>
        <h2 v-if="subscriptions.length === 0">You have no subscribers &#128578;</h2>
      </v-list>
    </v-layout>
  </v-container>
</template>

<script>
import profileApi from '../api/profile';
import UserLink from "../components/utils/UserLink.vue";

export default {
  name: "Subscriptions",
  components: {UserLink},
  data() {
    return {
      subscriptions: [],
    };
  },
  methods: {
    async changeSubscriptionStatus(subscriberId) {
      await profileApi.changeSubscriptionStatus(subscriberId);

      const subscriptionIndex = this.subscriptions.findIndex(item => item.subscriber.id === subscriberId);
      const subscription = this.subscriptions[subscriptionIndex];

      this.subscriptions = [
        ...this.subscriptions.slice(0, subscriptionIndex),
        {
          ...subscription,
          active: !subscription.active
        },
        ...this.subscriptions.slice(subscriptionIndex + 1)
      ];
    }
  },
  async beforeMount() {
    const response = await profileApi.subscriberList(this.$store.state.profile.id);
    this.subscriptions = await response.json();
  }
};
</script>

<style scoped>

</style>