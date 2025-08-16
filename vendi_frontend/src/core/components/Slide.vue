<template>
  <v-window show-arrows v-if="images.length">
    <v-window-item v-for="image in images" :key="image">
      <img
        style="object-fit: cover"
        class="w-100"
        :height="$vuetify.display.mdAndUp ? 660 : 300"
        :src="image" />
    </v-window-item>
  </v-window>
  <v-skeleton-loader type="image" class="w-100" v-else />
</template>

<script setup>
  defineProps({
    images: {
      type: Array,
      default: null,
    },
  })
</script>
