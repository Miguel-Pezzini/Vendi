<template>
  <v-row
    class="header"
    justify="center"
  >
    <v-col class="d-flex justify-center align-center ga-2">
      <span style="font-size: 14px;">Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%!</span>
      <RouterLink style="color: white; font-size: 14px; text-decoration: underline;">
        ShopNow
      </RouterLink>
    </v-col>
  </v-row>
  <v-row
    class="header-container"
    align="center"
    no-gutters
  >
    <v-col
      cols="2"
      class="d-flex justify-center align-center"
    >
      <router-link to="/home">
        <img
          class="img"
          src="../assets/logo.png"
          alt="Logo"
        >
      </router-link>
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
      class=" mr-10"
    >
      <RouterLink>Computadores</RouterLink>
    </v-col>
    <v-col
      cols="3"
      class="d-flex justify-center align-center"
    >
      <v-text-field
        v-model="dadoPesquisa"
        hide-details="true"
        label="Pesquise seu Produto"
        append-inner-icon="mdi-magnify"
        variant="outlined"
        @click:append-inner="pesquisar()"
      />
    </v-col>
    <v-col cols="1">
      <v-row class="d-flex justify-center ga-4">
        <RouterLink to="profile">
          <v-icon
            :color="accountActive ? '#DBB671' : null"
            size="x-large"
            :icon="accountActive ? 'mdi-account-circle-outline' : 'mdi-account-outline'"
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
    <v-col>
      <Button
        title="SAIR"
        color="error"
        @click="router.push({name: 'Login'})"
      />
    </v-col>
  </v-row>
</template>
      
    <script setup>
    import { ref } from 'vue';
    import router from '@/router';
    import Button from './Button.vue';

defineProps({
  accountActive: {
    type: Boolean,
    default: false
  }
})

const dadoPesquisa = ref(null)

function pesquisar() {
  console.log(dadoPesquisa.value)
}
    </script>
<style scoped>
body {
  font-family: "Poppins", serif;
}
    a {
        font-weight: 600;
        text-decoration: none;
        cursor: pointer;
        color: black;
      }
      a:hover, .img:hover {
        cursor: pointer;
        transition: .2s;
        scale: 1.05;
      }
      .header {
        height: 48px;
        background-color: #000;
        color: #FFF;
      }
      .header-container {
        height: 100px;
        background-color: #FFF;
      }
      .icon {
        cursor: pointer;
      }
      .img {
        height: 100px;
      }
</style>