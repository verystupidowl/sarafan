<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <h2>Settings</h2>
        <v-layout row justify-space-between>
          <v-layout column>
            <v-flex>
              <form>
                <input type="checkbox" id="notification-subscription-checkbox" v-model="notificationSubscription">
              </form>
              Notification of subscriptions
            </v-flex>
            <v-flex>
              <h4>Second setting</h4>
            </v-flex>
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
    return{
      notificationSubscription: false
    }
  }
}
</script>

<style scoped>
#notification-subscription-checkbox {
  margin-left: auto;
  text-align: right;
  float: right;
  right: 100px;
  box-shadow: 3px 3px 3px darkgrey;
}
</style>