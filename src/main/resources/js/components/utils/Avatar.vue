<template>
  <v-avatar :color="avatarColor" :size="`${size || 36}px`">
    <v-img v-if="!imgError" @error="errorImg" :src="userpic"/>
    <v-icon v-else dark>account_circle</v-icon>
  </v-avatar>
</template>

<script>
export default {
  name: "Avatar",
  props: ['userpic', 'size', 'className'],
  data() {
    return {
      imgError: false,
    }
  },
  methods: {
    errorImg() {
      this.imgError = true;
    }
  },
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