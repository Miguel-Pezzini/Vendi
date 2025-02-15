<template>
    <v-snackbar location="right | top" v-model="snackbarData.visible" :color="snackbarData.color" :timeout="3000">
      {{ snackbarData.message }}
          <template v-slot:actions>
            <v-icon
            @click="snackbarData.visible = false"
            size="x-large"
            :color="shoppingActive ? '#DBB671' : null"
            icon="mdi-close"
          />
      </template>
    </v-snackbar>
  </template>
  
  <script setup>
  import { inject } from 'vue';
  
  const snackbarData = inject('snackbar');
  </script>