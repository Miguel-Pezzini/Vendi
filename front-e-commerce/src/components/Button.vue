<template>
  <v-btn
    :type="type"
    :block="block"
    :color="color"
    :loading="loading"
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
            default: "white"
          },
          loading: {
            type: Boolean,
            default: false
          }
      })
  </script>
    
    <style scoped>
    
    </style>
    