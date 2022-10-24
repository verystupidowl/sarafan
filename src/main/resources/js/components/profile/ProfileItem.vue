<template>
  <div>
    <v-card class="my-2">
      <v-card-text primary-title>
        <user-link
            :user="profile"
            size="48"
        />
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import UserLink from "../utils/UserLink.vue";
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