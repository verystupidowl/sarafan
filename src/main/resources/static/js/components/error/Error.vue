<template>
  <div>
    <p style="color: red">Error {{ errorHandler.status }} {{ errorHandler.message }}</p>
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