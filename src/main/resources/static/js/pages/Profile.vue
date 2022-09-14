<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div class="title mb-3">User Profile</div>
        <v-layout row justify-space-between>
          <v-flex class="px-1">
            <v-avatar :color="avatarColor" size="100px">
              <v-icon dark>account_circle</v-icon>
            </v-avatar>
          </v-flex>
          <v-flex class="px-1">
            <v-layout column>
              <v-flex>Имя: {{ profile.name }}</v-flex>
              <v-flex>Регион: {{ profile.locale }}</v-flex>
              <v-flex>Пол: {{ profile.gender ? profile.gender : 'Не указан' }}</v-flex>
              <v-flex>Электронная почта: {{ profile.email }}</v-flex>
              <v-flex>Последний раз заходил: {{ profile.lastVisit }}</v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "Profile",
  computed: mapState(['profile']),
  data() {
    const colors = ['pink', 'purple', 'deep-purple',
      'indigo', 'blue', 'cyan', 'teal', 'orange', 'yellow', 'amber']
    return {avatarColor: colors[Math.floor(Math.random() * (9 + 1))]}
  },

}
</script>

<style scoped>
img {
  max-width: 100%;
  height: auto;
}
</style>