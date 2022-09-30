<template>
  <router-link style="text-decoration: none; color: rgba(0, 0, 0, 0.87)" :to="`/user/${user.id}`" :class="`${className || 'px-3'}`">
    <v-avatar :color="avatarColor" :size="`${size || 36}px`">
      <v-icon dark>account_circle</v-icon>
    </v-avatar>
    <span class="pl-3">{{ userName }}</span>
  </router-link>
</template>

<script>
export default {
  name: "UserLink",
  props: ['user', 'size', 'className'],
  computed: {
    userName() {
      return this.user ? this.user.name : 'Unknown';
    },
    avatarColor() {
      const colors = ['pink', 'purple', 'deep-purple',
        'indigo', 'blue', 'cyan', 'teal', 'orange', 'yellow', 'amber'];
      return colors[Math.floor(Math.random() * (9 + 1))];
    },
  },
}
</script>

<style scoped>

</style>