<template>
  <v-row
    height="90"
    class="header-container"
    align="center"
    no-gutters
  >
    <v-col
      cols="3"
      class="d-flex justify-center"
    >
      Logo
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink>Mais Vendidos</RouterLink>
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink>Decoração</RouterLink>
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink>Cozinha</RouterLink>
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink>Banheiro</RouterLink>
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink>Ideias de Presente</RouterLink>
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink>Computadores</RouterLink>
    </v-col>
    <v-col cols="2">
      <v-row class="d-flex justify-center ga-4">
        <RouterLink>
          <v-icon
            size="x-large"
            icon="mdi-magnify"
          />
        </RouterLink>
        <RouterLink>
          <v-icon
            size="x-large"
            icon="mdi-account-outline"
          />
        </RouterLink>
        <RouterLink>
          <v-icon
            size="x-large"
            icon="mdi-shopping-outline"
          />
        </RouterLink>
      </v-row>
    </v-col>
  </v-row>
</template>
      
    <script setup>
        defineProps({
          
        })
    </script>
<style scoped>
    a {
        font-family: "Poppins", serif;
        font-weight: 600;
        text-decoration: none;
        cursor: pointer;
        color: black;
      }
      a:hover {
        scale: 1.05;
      }
      .header-container {
        height: 90px;
      }
      .icon {
        cursor: pointer;
      }
</style>