<template>
  <v-list-tile>
    <user-link
        :user="comment.author"
    />
    <v-list-tile-content class="pl-3">
      <v-list-tile-title>{{ comment.text }}</v-list-tile-title>
    </v-list-tile-content>
  </v-list-tile>
</template>

<script>
import UserLink from "./UserLink.vue";

export default {
  name: "CommentItem",
  components: {UserLink},
  props: ['comment'],
  computed: {
    avatarColor() {
      const colors = ['pink', 'purple', 'deep-purple',
        'indigo', 'blue', 'cyan', 'teal', 'orange', 'yellow', 'amber'];
      return colors[Math.floor(Math.random() * (9 + 1))];
    },
  }
}
</script>

<style scoped>

</style>