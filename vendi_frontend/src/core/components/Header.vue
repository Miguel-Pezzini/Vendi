<template>
  <CartMenu v-model:show-cart="showCart" />
  <SideHeaderMenu v-model:show-menu="showMenu" />

  <v-row class="header ma-0" align="center" justify="center">
    <span class="text-subtitle-2 mr-2">
      Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%!
    </span>
    <RouterLink class="text-subtitle-2 text-decoration-underline" to="/products">
      ShopNow
    </RouterLink>
  </v-row>
  <div class="header-wrapper">
    <v-row class="ma-0 header-container" no-gutters>
      <v-col cols="9" class="d-flex justify-center align-center">
        <RouterLink to="/home">
          <img class="img" src="@/assets/logo.png" alt="Logo" />
        </RouterLink>
        <form class="w-100" @submit.prevent="pesquisar">
          <Input
            v-model="dadoPesquisa"
            :hide-details="true"
            label="Search for categories, products, brands..."
            append-inner-icon="mdi-magnify"
            variant="outlined"
            @click:append-inner="pesquisar()" />
        </form>
      </v-col>
      <v-row class="justify-center ga-6">
        <template v-for="(item, i) in menuOptions" :key="i">
          <component
            :is="item.isRouterLink ? 'RouterLink' : 'div'"
            v-if="
              !item.showOn ||
              (item.showOn === 'mdAndUp' && mdAndUp) ||
              (item.showOn === 'mdAndDown' && mdAndDown)
            "
            :to="typeof item.to === 'function' ? item.to() : item.to">
            <Icon
              :size="'x-large'"
              :tooltip="item.tooltip"
              :icon="
                typeof item.reactiveIcon === 'function' ? item.reactiveIcon() : item.icon || ''
              "
              :color="
                typeof item.color === 'function'
                  ? item.color()
                  : item.reactiveColor
                    ? item.reactiveColor()
                    : item.color || null
              "
              @click="item.onClick && item.onClick()" />
          </component>
        </template>
      </v-row>
    </v-row>
    <v-row v-if="mdAndUp" class="ma-0 header-container">
      <v-col
        v-for="category in categories"
        class="d-flex pa-0 pb-4 align-center justify-center"
        :key="category.category">
        <RouterLink :to="{ path: `${category.path}`, query: category.category }">
          {{ category.name }}
        </RouterLink>
      </v-col>
    </v-row>
  </div>
</template>
<script setup>
  import { ref } from 'vue'
  import isAdmin from '../utils/isAdmin'
  import Input from '@/core/components/Input.vue'
  import Icon from '@/core/components/Icon.vue'
  import CartMenu from '@/cart/components/CartMenu.vue'
  import SideHeaderMenu from './SideHeaderMenu.vue'
  import { useDisplay } from 'vuetify'
  import createMenuOptions from '@/core/constants/menuOptions.js'

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

  const { mdAndUp, mdAndDown } = useDisplay()
  const accountIcon = props.accountActive ? 'mdi-account-circle-outline' : 'mdi-account-outline'
  const adminIcon = props.accountActive ? 'mdi-shield-account' : 'mdi-shield-account-outline'

  const categories = [
    { name: 'Most Sellers', path: '/store', category: 'most-seller' },
    { name: 'Eletronics', path: '/store', category: 'eletronics' },
    { name: 'Domestics', path: '/store', category: 'household' },
    { name: 'Books', path: '/store', category: 'books' },
    { name: 'Clothing', path: '/store', category: 'clothing' },
    { name: 'Shoes', path: '/store', category: 'shoes' },
    { name: 'Beauty & Health', path: '/store', category: 'beauty-health' },
    { name: 'Sports & Fitness', path: '/store', category: 'sports-fitness' },
  ]

  const showCart = ref(false)
  const showMenu = ref(false)
  const dadoPesquisa = ref(null)

  const menuOptions = createMenuOptions({
    wishListActive: props.wishListActive,
    accountActive: props.accountActive,
    shoppingActive: props.shoppingActive,
    cartActive: props.cartActive,
    showCart: showCart.value,
    showMenu: showMenu.value,
    isAdmin,
    adminIcon,
    accountIcon,
  })

  function pesquisar() {
    console.log(display)
    //router.push({ path: '/store', query: { products: dadoPesquisa.value } })
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
    scale: 1.02;
  }
  .header {
    height: 30px;
    background-color: #000;
    color: #fff;
  }
  .header-container {
    background-color: #fff;
  }
  .img {
    height: 100px;
  }
  .header-wrapper {
    max-width: 1200px;
    margin: 0 auto;
    width: 100%;
  }
</style>
