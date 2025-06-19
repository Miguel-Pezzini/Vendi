<template>
  <button :style="{ backgroundColor: isActive ? '#DBB671' : '' }">
    {{ size }}
  </button>
</template>

<script setup>
  defineProps({
    size: {
      type: String,
      default: null,
    },
    isActive: {
      type: Boolean,
      default: false,
    },
    value: {
      type: String,
      default: null,
    },
  })
</script>

<style scoped>
  button {
    width: 32px;
    height: 32px;
    border-radius: 4px;
    border: 1px solid #aaa;
  }

  button:hover {
    background-color: #dbb671;
  }
</style>
