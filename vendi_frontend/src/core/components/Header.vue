<template>
  <CartMenu v-model:show-cart="showCart" />

  <v-row class="header ma-0" align="center" justify="center">
    <span class="text-subtitle-2 mr-2">
      Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%!
    </span>
    <RouterLink class="text-subtitle-2 text-decoration-underline" to="/products">
      ShopNow
    </RouterLink>
  </v-row>
  <v-row class="header-container" align="center" no-gutters>
    <v-col cols="2" class="d-flex justify-center align-center">
      <RouterLink to="/home">
        <img class="img" src="@/assets/logo.png" alt="Logo" />
      </RouterLink>
    </v-col>
    <v-col v-for="category in categories" class="d-flex justify-center">
      <RouterLink :to="{ path: `${category.path}`, query: category.category }">
        {{ category.name }}
      </RouterLink>
    </v-col>
    <v-col cols="2" class="d-flex justify-center align-center">
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
    <v-col cols="2">
      <v-row class="d-flex justify-center ga-4">
        <RouterLink :to="{ path: '/wishlist' }">
          <Icon
            size="x-large"
            tooltip="Wish List"
            :color="wishListActive ? 'golden' : null"
            :icon="wishListActive ? 'mdi-heart' : 'mdi-heart-outline'" />
        </RouterLink>
        <RouterLink>
          <Icon
            size="x-large"
            tooltip="My Orders"
            :color="shoppingActive ? 'golden' : null"
            icon="mdi-shopping-outline" />
        </RouterLink>

        <RouterLink>
          <Icon
            size="x-large"
            :color="cartActive ? 'golden' : null"
            icon="mdi-cart-outline"
            tooltip="Cart"
            @click="showCart = !showCart" />
        </RouterLink>

        <RouterLink :to="{ path: isAdmin() ? '/admin' : '/profile' }">
          <Icon
            :color="accountActive ? 'golden' : 'black'"
            size="x-large"
            tooltip="My Account"
            :icon="isAdmin() ? adminIcon : accountIcon" />
        </RouterLink>
      </v-row>
    </v-col>
    <v-col>
      <RouterLink :to="{ path: '/login' }">
        <Icon tooltip="Logout" color="red" size="x-large" icon="mdi-logout" />
      </RouterLink>
    </v-col>
  </v-row>
</template>

<script setup>
  import { ref } from 'vue'
  import isAdmin from '../utils/isAdmin'
  import router from '@/core/router'
  import Input from '@/core/components/Input.vue'
  import Icon from '@/core/components/Icon.vue'
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
  const accountIcon = props.accountActive ? 'mdi-account-circle-outline' : 'mdi-account-outline'
  const adminIcon = props.accountActive ? 'mdi-shield-account' : 'mdi-shield-account-outline'

  const categories = [
    { name: 'Mais Vendidos', path: '/store', category: 'most-seller' },
    { name: 'Eletrônicos', path: '/store', category: 'eletronics' },
    { name: 'Domésticos', path: '/store', category: 'household' },
    { name: 'Livros', path: '/store', category: 'books' },
  ]

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
    height: 30px;
    background-color: #000;
    color: #fff;
  }
  .header-container {
    height: 100px;
  }
  .icon {
    cursor: pointer;
  }
  .img {
    height: 100px;
  }
</style>
