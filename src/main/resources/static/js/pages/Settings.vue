<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <h2>Settings</h2>
        <v-layout row justify-space-between>
          <v-layout column>
            <v-flex>First setting</v-flex>
            <v-flex>Second setting</v-flex>
          </v-layout>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "Settings"
}
</script>

<style scoped>

</style>