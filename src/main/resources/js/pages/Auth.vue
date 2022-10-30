<template>
  <v-container>
    <div v-if="!profile" style="text-align: center;">
      <v-flex>
        <h1>Sarafan</h1>
      </v-flex>
      <v-flex>
        <h3>You need to log in via Google</h3>
      </v-flex>
      <v-flex>
        <v-btn href="/login">Log in</v-btn>
      </v-flex>
    </div>
    <div v-else style="text-align: center;">
      <h2>You already logged in</h2>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "Auth",
  data() {
    return {
      profile: this.$store.state.profile
    }
  }
}
</script>

<style scoped>

</style>