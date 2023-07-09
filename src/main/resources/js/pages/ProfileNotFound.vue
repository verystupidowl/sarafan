<template>
  <v-container>
    <v-layout>
      <v-flex>
        <div>
          <h1>Error {{ error.status }}</h1>
        </div>
        <div>
          <h3>{{ error.message }}</h3>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  props: ['error'],
  name: "ProfileNotFound"
};
</script>

<style scoped>

</style>