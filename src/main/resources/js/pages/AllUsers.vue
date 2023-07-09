<template>
  <v-container>
    <h2>Users:</h2>
    <v-layout align-space-around justify-start column>
      <div :key="profile.id" v-for="profile in profiles">
        <profile-item :profile="profile"/>
      </div>
    </v-layout>
  </v-container>
</template>

<script>
import AllUsers from "../api/allUsers";
import ProfileItem from "../components/profile/ProfileItem.vue";

export default {
  name: "AllUsers",
  components: {
    ProfileItem
  },
  data() {
    return {
      profiles: [],
    };
  },
  methods: {
    updateRecommendations() {
      AllUsers.get()
          .then(result => {
            result.json()
                .then(data => this.profiles = data);
          });
      this.$forceUpdate();
    }
  },
  beforeMount() {
    this.updateRecommendations();
  }
};
</script>

<style scoped>

</style>