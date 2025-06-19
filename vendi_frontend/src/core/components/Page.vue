<template>
  <Header />
  <slot />
  <Footer />
</template>

<script setup>
  import Header from './Header.vue'
  import Footer from './Footer.vue'
  defineProps({
    images: {
      type: Array,
      default: null,
    },
  })
</script>

<style scoped></style>
