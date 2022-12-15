<template>
  <div>
    <p style="color: red; font-style: italic">Error {{ errorHandler.status }} {{ errorHandler.message }}</p>
  </div>
</template>

<script>
export default {
  name: "Error",
  props: ['errorHandler'],
}
</script>

<style scoped>

</style>