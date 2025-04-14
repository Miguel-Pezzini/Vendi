<template>
  <v-snackbar
    v-model="snackbarData.visible"
    location="right | top"
    :color="snackbarData.color"
    :timeout="3000"
  >
    {{ snackbarData.message }}
    <template #actions>
      <v-icon
        size="x-large"
        icon="mdi-close"
        @click="snackbarData.visible = false"
      />
    </template>
  </v-snackbar>
</template>
  
  <script setup>
  import { inject } from 'vue';
  
  const snackbarData = inject('snackbar');
  </script>