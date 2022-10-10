<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <h2>Settings</h2>
        <v-layout row justify-space-between>
          <v-layout column>
            <h4>There is no settings yet</h4>
          </v-layout>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "Settings",
  data() {
    return {
      notificationSettings: {
        subscriptions: false
      }
    }
  }
}
</script>

<style scoped>

</style>