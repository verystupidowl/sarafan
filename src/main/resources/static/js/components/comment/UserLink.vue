<template>
  <router-link :to="`/user/${user.id}`" class="px-3">
    <!--        <v-avatar v-if="message.author && message.author.userpic" size="36px">-->
    <!--          <img :src="message.author.userpic" :alt="authorName"/>-->
    <!--        </v-avatar>-->
    <v-avatar :color="avatarColor" :size="`${size || 36}px`">
      <v-icon dark>account_circle</v-icon>
    </v-avatar>
    <span class="pl-3">{{ userName }}</span>
  </router-link>
</template>

<script>
export default {
  name: "UserLink",
  props: ['user', 'size'],
  computed: {
    userName() {
      return this.user ? this.user.name : 'Unknown'
    },
    avatarColor() {
      const colors = ['pink', 'purple', 'deep-purple',
        'indigo', 'blue', 'cyan', 'teal', 'orange', 'yellow', 'amber']
      return colors[Math.floor(Math.random() * (9 + 1))]
    },
  },
}
</script>

<style scoped>

</style>