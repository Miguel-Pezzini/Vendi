<template>
  <Header account-active="true" />
  <v-divider />
  <v-container class="container">
    <Path :old-paths="loadPastPaths(route)" active-path="Admin Dashboard" />

    <v-row class="container margin">
      <v-col class="pa-0" cols="4">
        <SideAdminMenu active-minha-conta />
      </v-col>
      <v-col />
    </v-row>
  </v-container>
  <Footer />
</template>

<script setup>
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import SideAdminMenu from '../components/AdminSideMenu.vue'
  import Footer from '@/core/components/Footer.vue'
  import { useRoute } from 'vue-router'
  import loadPastPaths from '@/core/utils/loadPastPaths'

  const route = useRoute()
</script>

<style scoped>
  .container {
    margin-top: 80px;
  }
  .v-card {
    padding: 40px 80px 40px 80px;
    box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
  }
  .title {
    padding-bottom: 16px;
    color: #dbb671;
    font-size: 20px;
    font-weight: 500;
  }
  .margin {
    margin-bottom: 146px;
  }
</style>
