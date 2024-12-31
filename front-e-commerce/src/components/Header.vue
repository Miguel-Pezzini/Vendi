<template>
  <v-row
    class="header"
    justify="center"
  >
    <v-col class="d-flex justify-center align-center ga-2">
      <span style="font-size: 14px;">Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%!</span>
      <RouterLink
        style="color: white; font-size: 14px; text-decoration: underline;"
        to="/products"
      >
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
      <RouterLink :to="{ path: '/products', query: { category: 'most-seller' } }">
        Mais Vendidos
      </RouterLink>
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink :to="{ path: '/products', query: { category: 'eletronics' } }">
        Eletrônicos
      </RouterLink>
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink :to="{ path: '/products', query: { category: 'household' } }">
        Domésticos
      </RouterLink>
    </v-col>
    <v-col
      cols="1"
      class="d-flex justify-center"
    >
      <RouterLink :to="{ path: '/products', query: { category: 'books' } }">
        Livros
      </RouterLink>
    </v-col>
    <v-col
      cols="1"
      class=" mr-10"
    >
      <RouterLink :to="{ path: '/products', query: { category: 'offers' } }">
        Ofertas
      </RouterLink>
    </v-col>
    <v-col
      cols="2"
      class="d-flex justify-center align-center"
    >
      <form
        style="width: 100%;"
        @submit.prevent="pesquisar"
      >
        <Input
          v-model="dadoPesquisa"
          :hide-details="true"
          label="Pesquise seu Produto"
          append-inner-icon="mdi-magnify"
          variant="outlined"
          @click:append-inner="pesquisar()"
        />
      </form>
    </v-col>
    <v-col cols="2">
      <v-row class="d-flex justify-center ga-4">
        <RouterLink to="/profile">
          <v-icon
            :color="accountActive ? '#DBB671' : 'black'"
            size="x-large"
            :icon="accountActive ? 'mdi-account-circle-outline' : 'mdi-account-outline'"
          />
        </RouterLink>
        <RouterLink>
          <v-icon
            size="x-large"
            :color="shoppingActive ? '#DBB671' : null"
            icon="mdi-shopping-outline"
          />
        </RouterLink>
        <RouterLink>
          <v-icon
            size="x-large"
            icon="mdi-cart-outline"
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
    import Input from './Input.vue';
    import Button from './Button.vue';

defineProps({
  accountActive: {
    type: Boolean,
    default: false
  },
  shoppingActive: {
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