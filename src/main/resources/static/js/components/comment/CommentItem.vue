<template>
  <v-list-tile>
    <v-avatar :color="avatarColor" size="36px">
      <v-icon dark>account_circle</v-icon>
    </v-avatar>
    <v-list-tile-content class="pl-3">
      <v-list-tile-title>{{comment.text}}</v-list-tile-title>
      <v-list-tile-sub-title>{{comment.author.name}}</v-list-tile-sub-title>
    </v-list-tile-content>
  </v-list-tile>
</template>

<script>
export default {
  name: "CommentItem",
  props: ['comment'],
  computed: {
    avatarColor() {
      const colors = ['pink', 'purple', 'deep-purple',
        'indigo', 'blue', 'cyan', 'teal', 'orange', 'yellow', 'amber']
      return colors[Math.floor(Math.random() * (9 + 1))]
    },
  }
}
</script>

<style scoped>

</style>