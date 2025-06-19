<template>
  <CartMenu v-model:show-cart="showCart" />

  <v-row class="header" justify="center">
    <v-col class="d-flex justify-center align-center ga-2">
      <span style="font-size: 14px"
        >Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%!</span
      >
      <RouterLink style="color: white; font-size: 14px; text-decoration: underline" to="/products">
        ShopNow
      </RouterLink>
    </v-col>
  </v-row>
  <v-row class="header-container" align="center" no-gutters>
    <v-col cols="3" class="d-flex justify-center align-center">
      <router-link to="/home">
        <img class="img" src="@/assets/logo.png" alt="Logo" />
      </router-link>
    </v-col>
    <v-col cols="1" class="d-flex justify-center">
      <RouterLink :to="{ path: '/store', query: { category: 'most-seller', origin: ['Home'] } }">
        Mais Vendidos
      </RouterLink>
    </v-col>
    <v-col cols="1" class="d-flex justify-center">
      <RouterLink :to="{ path: '/store', query: { category: 'eletronics', origin: ['Home'] } }">
        Eletrônicos
      </RouterLink>
    </v-col>
    <v-col cols="1" class="d-flex justify-center">
      <RouterLink :to="{ path: '/store', query: { category: 'household', origin: ['Home'] } }">
        Domésticos
      </RouterLink>
    </v-col>
    <v-col cols="1" class="d-flex justify-center">
      <RouterLink :to="{ path: '/store', query: { category: 'books', origin: ['Home'] } }">
        Livros
      </RouterLink>
    </v-col>
    <v-col cols="2" class="d-flex justify-center align-center ml-8">
      <form style="width: 100%" @submit.prevent="pesquisar">
        <Input
          v-model="dadoPesquisa"
          :hide-details="true"
          label="Pesquise seu Produto"
          append-inner-icon="mdi-magnify"
          variant="outlined"
          @click:append-inner="pesquisar()" />
      </form>
    </v-col>
    <v-col>
      <v-row class="d-flex justify-center ga-4">
        <RouterLink :to="{ path: '/wishlist', query: { origin: ['Home'] } }">
          <v-icon
            size="x-large"
            :color="wishListActive ? '#DBB671' : null"
            :icon="wishListActive ? 'mdi-heart' : 'mdi-heart-outline'" />
        </RouterLink>
        <RouterLink>
          <v-icon
            size="x-large"
            :color="shoppingActive ? '#DBB671' : null"
            icon="mdi-shopping-outline" />
        </RouterLink>

        <RouterLink>
          <v-icon
            size="x-large"
            :color="cartActive ? '#DBB671' : null"
            icon="mdi-cart-outline"
            @click="showCart = !showCart" />
        </RouterLink>

        <RouterLink :to="{ path: isAdmin() ? '/admin' : '/profile', query: { origin: ['Home'] } }">
          <v-icon
            :color="accountActive ? '#DBB671' : 'black'"
            size="x-large"
            :icon="isAdmin() ? adminIcon : accountIcon" />
        </RouterLink>

        <Button class="ml-5" title="SAIR" color="error" @click="router.push({ name: 'Login' })" />
      </v-row>
    </v-col>
  </v-row>
</template>

<script setup>
  import { ref } from 'vue'
  import isAdmin from '../utils/isAdmin'
  import router from '@/core/router'
  import Input from '@/core/components/Input.vue'
  import Button from '@/core/components/Button.vue'
  import CartMenu from '@/cart/components/CartMenu.vue'

  const props = defineProps({
    accountActive: {
      type: Boolean,
      default: false,
    },
    shoppingActive: {
      type: Boolean,
      default: false,
    },
    cartActive: {
      type: Boolean,
      default: false,
    },
    wishListActive: {
      type: Boolean,
      default: false,
    },
  })

  // const prod1 = ref({
  //   discount: 35,
  //   name: "Laptop",
  //   price: "960",
  //   fullPrice: "1160",
  //   isInWishList: true,
  // })

  const accountIcon = props.accountActive ? 'mdi-account-circle-outline' : 'mdi-account-outline'
  const adminIcon = props.accountActive ? 'mdi-shield-account' : 'mdi-shield-account-outline'

  const showCart = ref(false)
  const dadoPesquisa = ref(null)

  function pesquisar() {
    router.push({ path: '/store', query: { products: dadoPesquisa.value } })
  }
</script>

<style scoped>
  a {
    font-weight: 600;
    text-decoration: none;
    cursor: pointer;
    color: black;
  }
  a:hover,
  .img:hover {
    cursor: pointer;
    transition: 0.2s;
    scale: 1.05;
  }
  .header {
    height: 48px;
    background-color: #000;
    color: #fff;
  }
  .header-container {
    height: 100px;
    background-color: #fff;
  }
  .icon {
    cursor: pointer;
  }
  .img {
    height: 100px;
  }
</style>
