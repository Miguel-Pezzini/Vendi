<template>
  <CartMenu v-model:show-cart="showCart" />
  <MobileHeaderSideMenu :mobileMenuOptions="mobileMenuOptions" v-model:show-menu="showMenu" />

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
      <v-col class="d-flex justify-center align-center">
        <v-row class="justify-center ga-2">
          <template v-for="(item, i) in computedMenuOptions" :key="i">
            <component
              :is="item.isRouterLink ? 'RouterLink' : 'div'"
              :to="typeof item.to === 'function' ? item.to() : item.to">
              <v-btn variant="text" icon elevation="0" @click="item.onClick()">
                <Icon
                  size="x-large"
                  :tooltip="item.tooltip"
                  :icon="typeof item.icon === 'function' ? item.icon() : item.icon"
                  :color="typeof item.color === 'function' ? item.color() : item.color" />
              </v-btn>
            </component>
          </template>
        </v-row>
      </v-col>
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
  import { computed, ref } from 'vue'
  import isAdmin from '../utils/isAdmin'
  import Input from '@/core/components/Input.vue'
  import Icon from '@/core/components/Icon.vue'
  import CartMenu from '@/cart/components/CartMenu.vue'
  import MobileHeaderSideMenu from './MobileHeaderSideMenu.vue'
  import { useDisplay } from 'vuetify'
  import headerMenuOptionsDesktop from '../constants/headerMenuOptionsDesktop'
  import headerMenuOptionsMobile from '../constants/headerMenuOptionsMobile'
  import headerCategories from '../constants/headerCategories'

  const props = defineProps({
    accountActive: {
      type: Boolean,
      default: false,
    },
    shoppingActive: {
      type: Boolean,
      default: false,
    },
    wishListActive: {
      type: Boolean,
      default: false,
    },
  })

  const { mdAndUp, smAndDown } = useDisplay()
  const accountIcon = props.accountActive ? 'mdi-account-circle-outline' : 'mdi-account-outline'
  const adminIcon = props.accountActive ? 'mdi-shield-account' : 'mdi-shield-account-outline'

  const categories = headerCategories()

  const showCart = ref(false)
  const showMenu = ref(false)
  const dadoPesquisa = ref(null)

  const menuOptions = ref(
    headerMenuOptionsDesktop({
      mdAndUp: mdAndUp.value,
      smAndDown: smAndDown.value,
      showCart,
      showMenu,
      wishListActive: props.wishListActive,
      shoppingActive: props.shoppingActive,
      accountActive: props.accountActive,
      accountIcon,
      adminIcon,
      isAdmin: isAdmin(),
    })
  )

  const computedMenuOptions = computed(() => {
    return menuOptions.value.filter((item) =>
      item.showOn({ mdAndUp: mdAndUp.value, smAndDown: smAndDown.value })
    )
  })

  const mobileMenuOptions = ref(
    headerMenuOptionsMobile({
      showCart,
      showMenu,
      wishListActive: props.wishListActive,
      shoppingActive: props.shoppingActive,
      accountActive: props.accountActive,
      accountIcon,
      adminIcon,
      isAdmin: isAdmin(),
    })
  )

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
