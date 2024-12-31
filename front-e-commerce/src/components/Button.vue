<template>
  <v-btn
    :type="type"
    :block="block"
    :color="color"
    :loading="loading"
    :variant="variant"
    :style="{ backgroundColor: bgColor }"
  >
    {{ title }}
  </v-btn>
</template>
    
  <script setup>
      defineProps({
        title: {
              type: String,
              default: "title"
          },
          type: {
              type: String,
              default: "text"
          },
          block: {
            type: Boolean,
            default: false,
          },
          color: {
            type: String,
            default: "black"
          },
          loading: {
            type: Boolean,
            default: false
          },
          variant: {
            type: String,
            default: null
          },
          bgColor: {
            type: String,
            default: "white",
          }
      })
  </script>
    
    <style scoped>
    
    </style>
    