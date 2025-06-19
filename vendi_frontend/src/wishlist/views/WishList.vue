<template>
  <div class="page-wrapper">
    <Header :wish-list-active="true" />
    <v-divider />
    <div class="d-flex container">
      <v-container>
        <v-row>
          <h1 style="font-size: 20px; font-weight: 400">Lista de Desejos (4)</h1>
          <v-spacer />
          <button class="button">Mover todos para o carrinho</button>
        </v-row>
        <v-row class="mt-15">
          <v-col v-for="n in 4" :key="n" cols="3">
            <ResultsProduct active-page="Wishlist" :product="prod1" />
          </v-col>
        </v-row>

        <v-row class="mt-16 pt-4" align="center">
          <div class="decoration mr-4" />
          <h1 style="font-size: 20px; font-weight: 400">Só Para Você</h1>
          <v-spacer />
          <button class="button">Ver Todos</button>
        </v-row>
        <v-row class="mt-15">
          <v-col v-for="n in 4" :key="n" cols="3">
            <ResultsProduct active-page="Wishlist" :product="prod2" />
          </v-col>
        </v-row>
      </v-container>
    </div>
    <Footer />
  </div>
</template>

<script setup>
  import Header from '@/core/components/Header.vue'
  import Footer from '@/core/components/Footer.vue'
  import ResultsProduct from '@/core/components/ResultsProduct.vue'
  import card1 from '@/assets/card1.webp'

  import { ref } from 'vue'

  const prod1 = ref({
    discount: 35,
    name: 'Laptop',
    price: '960',
    fullPrice: '1160',
    isInWishList: true,
    image: card1,
  })

  const prod2 = ref({
    discount: 35,
    name: 'Laptop',
    price: '960',
    fullPrice: '1160',
    isInWishList: false,
    image: card1,
  })

  // const filters = [
  //   {
  //     name: "CPU",
  //     items: ["Intel i5", "Intel i7", "Ryzen 5"]
  //   },
  //   {
  //     name: "RAM",
  //     items: ["8GB", "16GB", "32GB"]
  //   },
  //   {
  //     name: "Gênero",
  //     items: ["Ficção", "Biografia", "Terror"]
  //   }
  // ]
  //   function verProduct(productName) {
  //     router.push({ path: "/product", query: { product: productName, origin: ["Home","Store"]}})
  //   }
</script>

<style scoped>
  .container {
    margin: 80px 135px 140px 135px;
  }
  .button {
    padding: 16px 48px 16px 48px;
    color: #000;
    border-radius: 4px;
    border: 1px solid #000;
  }
  .decoration {
    height: 40px;
    width: 20px;
    background-color: #dbb671;
    border-radius: 4px;
  }
</style>
