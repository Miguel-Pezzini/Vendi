<template>
    <v-snackbar v-model="snackbarData.visible" :color="snackbarData.color" top :timeout="3000">
      {{ snackbarData.message }}
      <v-icon
            @click="snackbarData.visible = false"
            size="x-large"
            :color="shoppingActive ? '#DBB671' : null"
            icon="mdi-close"
          />
    </v-snackbar>
  </template>
  
  <script setup>
  import { inject } from 'vue';
  
  const snackbarData = inject('snackbar');
  </script>