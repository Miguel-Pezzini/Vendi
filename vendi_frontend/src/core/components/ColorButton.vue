<template>
  <button :style="{ backgroundColor: color }" />
</template>

<script setup>
  defineProps({
    color: {
      type: String,
      default: null,
    },
  })
</script>

<style scoped>
  button {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    border: 2px solid transparent;
    transition:
      box-shadow 0.15s,
      border 0.2s;
  }

  button:hover {
    border: 2px solid #000;
    box-shadow: inset 0 0 0 2px white;
  }
</style>
