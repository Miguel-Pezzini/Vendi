<template>
  <v-window show-arrows>
    <v-window-item
      v-for="image in images"
      :key="image"
    >
      <img
        class="banner"
        :src="image"
      >
    </v-window-item>
  </v-window>
</template>
    
<script setup>
    defineProps({
        images: {
            type: Array,
            default: null
        }
    })
</script>
    
<style scoped>
.banner {
    width: 100%;
    height: 660px;
    object-fit: cover;
}
</style>
    