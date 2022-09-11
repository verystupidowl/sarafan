<template>
  <div>
    <div v-if="!profile">
      Необходимо авторизироваться через <a href="/login">Google</a>
    </div>
    <div v-else>
      <div>{{ profile.name }}&nbsp;<a href="/logout">Выйти</a></div>
      <messages-list :messages="messages"/>
    </div>
  </div>
</template>

<script>
import MessagesList from "../components/messages/MessagesList.vue";

export default {
  comments: {
    MessagesList
  },
  data() {
    return {
      messages: frontendData.meggages,
      profile: frontendData.profile
    }
  },
  // created() {
  //   this.$resource('/api/getData').get({}).then(result => result.json().then(data => ))
  // }
}
</script>

<style>

</style>