<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex v-if="!error" :xs6="!$vuetify.breakpoint.xsOnly">
        <div class="title mb-3">User Profile</div>
        <v-layout row justify-space-between>
          <v-flex class="px-1">
            <avatar :userpic="profile.userpic" size="106"/>
          </v-flex>
          <v-flex class="px-1">
            <v-layout column>
              <v-flex>Name: {{ profile.name }}</v-flex>
              <v-divider></v-divider>
              <v-flex>Region: {{ profile.locale }}</v-flex>
              <v-divider></v-divider>
              <v-flex>Gender: {{ profile.gender ? profile.gender : 'Not specified' }}</v-flex>
              <v-divider v-if="isMyProfile || isIApproved"></v-divider>
              <v-flex v-if="isMyProfile || isIApproved">Email: {{ profile.email }}</v-flex>
              <v-divider></v-divider>
              <v-flex>Last Visit: {{ profile.lastVisit }}</v-flex>
              <v-divider></v-divider>
              <router-link style="text-decoration: none; color: rgba(0, 0, 0, 0.87)" v-if="isMyProfile"
                           :to="`/subscribes/${profile.id}`"
              >
                {{ profile.subscriptions && profile.subscriptions.length }} subscriptions
              </router-link>
              <v-flex v-else>{{ profile.subscriptions && profile.subscriptions.length }} subscriptions</v-flex>
              <v-divider></v-divider>
              <router-link style="text-decoration: none; color: rgba(0, 0, 0, 0.87)" v-if="isMyProfile"
                           :to="`/subscriptions/${profile.id}`">
                {{ profile.subscribers && profile.subscribers.length }} subscribers
              </router-link>
              <v-flex v-else>{{ profile.subscribers && profile.subscribers.length }} subscribers</v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
        <v-btn :color="isISubscribed ? 'white' : '#FFFAFA' " v-if="!isMyProfile" @click="changeSubscription">
          {{ isISubscribed ? 'Unsubscribe' : 'Subscribe' }}
        </v-btn>
      </v-flex>
      <v-flex v-else>
        <ProfileNotFound :error="error"></ProfileNotFound>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import profileApi from '../api/profile'
import ProfileNotFound from "./ProfileNotFound.vue";
import Avatar from "../components/utils/Avatar.vue";

export default {
  name: "Profile",
  components: {ProfileNotFound, Avatar},
  computed: {
    isMyProfile() {
      return !this.$route.params.id || this.$store.state.profile.id === this.$route.params.id;
    },
    isISubscribed() {
      return this.profile.subscribers &&
          this.profile.subscribers.find(subscriptions => {
            return subscriptions.subscriber === this.$store.state.profile.id;
          })
    },
    isIApproved() {
      let value = -1;
      if (this.profile.subscribers) {
        this.profile.subscribers.forEach(s => {
          if (s.subscriber === this.$store.state.profile.id && s.active === true) {
            value = 1;
          }
        })
      }
      return value === 1;
    },
  },
  watch: {
    '$route'() {
      this.updateProfile();
    }
  },
  methods: {
    async changeSubscription() {
      const data = await profileApi.changeSubscription(this.profile.id);
      this.profile = await data.json();
    },
    async updateProfile() {
      const id = this.$route.params.id || this.$store.state.profile.id;

      profileApi.get(id).then(response => {
        response.json().then(data => this.profile = data);
      }, err => err.json().then(errorBody => this.error = {
        status: err.status,
        message: errorBody.message,
        timestamp: errorBody.timestamp
      }))
      this.$forceUpdate();
    },
  },
  beforeMount() {
    this.updateProfile();
  },
  data() {
    return {
      profile: {},
      error: null,
    }
  }

}
</script>

<style scoped>
img {
  max-width: 100%;
  height: auto;
}
</style>