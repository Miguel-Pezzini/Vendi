<template>
  <Header account-active="true" />
  <v-container class="container">
    <v-row>
      <Path
        :old-paths="['Home']"
        active-path="Minha Conta"
      />
      <v-spacer />
      <p>Bem-Vindo! <span style="color: #DBB671;">Your name</span></p>
    </v-row>

    <v-row class="container">
      <v-col class="pa-0">
        <SideMenu active-minha-conta="true" />
      </v-col>

      <v-col />
    </v-row>
  </v-container>
</template>
  
<script setup>
    import Header from '@/components/Header.vue';
    import Path from '@/components/Path.vue';
    import SideMenu from '@/components/SideMenu.vue';
</script>
  
  <style scoped>
  body {
    font-family: "Poppins", serif;
  }
  .container {
    margin-top: 80px;
  }
  ul {
    list-style: none;
  }
  h1 {
    font-size: 16px;
    font-weight: 500;
  }
  </style>
  