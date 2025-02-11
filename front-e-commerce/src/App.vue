<template>
  <v-app>
    <v-main>
      <router-view />
      <GlobalSnackbar />
    </v-main>
  </v-app>
</template>

<script setup>
  import GlobalSnackbar from '@/core/plugins/GlobalSnackbar.vue';
</script>
