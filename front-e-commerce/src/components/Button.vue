<template>
  <v-btn
    :type="type"
    :block="block"
    :color="color"
    :loading="loading"
    :variant="variant"
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
          }
      })
  </script>
    
    <style scoped>
    
    </style>
    