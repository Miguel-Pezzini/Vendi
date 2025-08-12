<template>
  <Header />
  <Slide :images="bannerImages" />
  <v-row
    :class="[$vuetify.display.mdAndUp ? 'mt-12 mx-12' : 'mt-6 mx-3']"
    justify="center"
    v-if="transparentCards.length">
    <CardTransparent
      v-for="transparentCard in transparentCards"
      :image="transparentCard.image"
      :title="transparentCard.title"
      :key="transparentCard.title"
      @click="
        router.push({
          path: '/store',
          query: { category: transparentCard.category, origin: ['Home'] },
        })
      " />
  </v-row>
  <v-skeleton-loader class="w-100" v-else type="image" />
  <div v-if="recentProducts.length">
    <h1 class="text-center my-8 font-weight-regular">Lançamentos</h1>
    <v-sheet>
      <v-slide-group show-arrows>
        <v-slide-group-item v-for="product in recentProducts" :key="product.id">
          <CardProducts :origin="['Home']" :product="product" class="mx-2" />
        </v-slide-group-item>
      </v-slide-group>
    </v-sheet>
  </div>

  <v-divider class="mt-15 mb-3" color="#DBB671" opacity="1" />
  <v-row
    align="center"
    justify="center"
    :class="['py-8', $vuetify.display.smAndDown ? 'mx-3' : 'mx-16']">
    <v-col>
      <h1 class="font-weight-medium" style="font-size: 22px">Newsletter</h1>
    </v-col>

    <v-spacer v-if="$vuetify.display.mdAndUp" />
    <v-col>
      <h2 class="font-weight-light" style="font-size: 18px">Receba nossas ofertas por e-mail</h2>
    </v-col>
    <v-col>
      <div class="d-flex">
        <input class="py-3 pl-6 input" placeholder="Digite seu e-mail..." />
        <v-btn color="black" class="button" icon="mdi-check" />
      </div>
    </v-col>
  </v-row>

  <Footer />
</template>

<script setup>
  import router from '@/core/router'
  import Header from '@/core/components/Header.vue'
  import Slide from '@/core/components/Slide.vue'
  import CardTransparent from '@/home/components/CardTransparent.vue'
  import CardProducts from '@/home/components/CardProducts.vue'
  import Footer from '@/core/components/Footer.vue'
  import { onMounted, ref } from 'vue'
  import productService from '@/core/utils/productService'
  import constantBannerImages from '@/core/constants/BannerSlideHome.js'
  import constantTransparentImages from '@/core/constants/transparentCardsHome'

  let recentProducts = ref([])
  const bannerImages = constantBannerImages
  const transparentCards = constantTransparentImages

  async function loadRecentProducts() {
    recentProducts.value = await productService.loadProducts(`/product?limit=7`)
  }

  onMounted(() => {
    loadRecentProducts()
  })
</script>

<style scoped>
  button:hover {
    transition: 0.2s;
    scale: 1.05;
  }
  .input {
    height: 50px;
    width: 340px;
    border: 1px solid #000;
    border-top-left-radius: 11px;
    border-bottom-left-radius: 11px;
  }
  .button {
    height: 50px;
    border-radius: 0;
    border-top-right-radius: 11px;
    border-bottom-right-radius: 11px;
  }
</style>
