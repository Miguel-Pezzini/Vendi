<template>
  <Header account-active />
  <v-divider />
  <v-container class="container">
    <v-row>
      <Path :old-paths="['Home']" active-path="My Products" />
      <v-spacer />
      <Button title="Add Product" variant="outlined" color="#DBB671" to="/user/products/create" />
    </v-row>

    <v-row class="container margin">
      <v-col class="pa-0" cols="4">
        <SideMenu active-my-products />
      </v-col>
      <v-row>
        <v-row>
          <UserProduct v-for="product in products" :key="product.id" :product="prod1" />
          <span v-if="!products.length" class="opacity-50">
            You haven't created any products yet. Click "Add Product" to get started!
          </span>
        </v-row>
      </v-row>
    </v-row>
  </v-container>
  <Footer />
</template>

<script setup>
  import card1 from '@/assets/card1.webp'
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import SideMenu from '@/user/profile/components/SideMenu.vue'
  import Footer from '@/core/components/Footer.vue'
  import Button from '@/core/components/Button.vue'
  import UserProduct from '../components/UserProduct.vue'
  import { ref } from 'vue'

  const products = ref([])

  const prod1 = ref({
    discount: 35,
    name: 'Laptop',
    price: '960',
    fullPrice: '1160',
    isInWishList: false,
    quantity: 10,
    image: card1,
  })
</script>

<style scoped>
  .container {
    margin-top: 80px;
  }
  .margin {
    margin-bottom: 146px;
  }
</style>
