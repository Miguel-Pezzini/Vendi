<template>
  <Header account-active="true" />
  <v-divider />
  <v-container class="container">
    <v-row>
      <Path :old-paths="['Home']" active-path="Minha Conta" />
      <v-spacer />
      <p>Bem-Vindo! <span style="color: #dbb671">Your name</span></p>
    </v-row>

    <v-row class="container margin">
      <v-col class="pa-0" cols="4">
        <SideMenu active-add-product />
      </v-col>
      <v-col cols="8">
        <ProductForm />
      </v-col>
    </v-row>
  </v-container>
  <Footer />
</template>

<script setup>
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import SideMenu from '@/user/profile/components/SideMenu.vue'
  import ProductForm from '../components/ProductForm.vue'
  import Footer from '@/core/components/Footer.vue'
</script>

<style scoped>
  .container {
    margin-top: 80px;
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
