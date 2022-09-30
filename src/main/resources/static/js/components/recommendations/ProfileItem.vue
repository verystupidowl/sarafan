<template>
  <div>
    <v-card class="my-2">
      <v-card-text primary-title>
        <user-link
            :user="profile"
            size="48"
        ></user-link>
<!--        <div class="pt-3">-->
<!--          {{ profile.name }}-->
<!--        </div>-->
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import UserLink from "../comment/UserLink.vue";
export default {
  name: "ProfileItem",
  components: {
    UserLink
  },
  props: ['profile']
}
</script>

<style scoped>

</style>