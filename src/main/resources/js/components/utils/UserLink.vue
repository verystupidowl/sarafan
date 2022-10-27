<template>
  <router-link style="text-decoration: none; color: rgba(0, 0, 0, 0.87)" :to="`/user/${user.id}`"
               :class="`${className || 'px-3'}`">
    <avatar :userpic="user.userpic" :size="size"/>
    <span class="pl-3">{{ userName }}</span>
  </router-link>
</template>

<script>
import Avatar from "./Avatar.vue";

export default {
  name: "UserLink",
  props: ['user', 'size', 'className'],
  components: {
    Avatar
  },
  computed: {
    userName() {
      return this.user ? this.user.name : 'Unknown';
    },
  },
}
</script>

<style scoped>

</style>