<template>
  <Header account-active />
  <v-divider />
  <div class="container">
    <v-row>
      <Path :old-paths="['Home']" active-path="My Products" />
      <v-spacer />
      <Button title="Add Product" variant="outlined" color="#DBB671" to="/user/products/create" />
    </v-row>

    <v-row class="row-page">
      <v-col class="pa-0" cols="4">
        <SideMenu active-my-products />
      </v-col>
      <v-col cols="8">
        <v-row v-if="!!$loadingState['product/get']">
          <v-col v-for="n in 8">
            <v-skeleton-loader type="card"></v-skeleton-loader>
          </v-col>
        </v-row>
        <v-row v-else>
          <v-col v-for="product in products" :key="product.id">
            <UserProduct :product="product" />
          </v-col>

          <span v-if="!products.length" class="opacity-50">
            You haven't created any products yet. Click "Add Product" to get started!
          </span>
        </v-row>
      </v-col>
    </v-row>
  </div>
  <Footer />
</template>

<script setup>
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import SideMenu from '@/user/profile/components/SideMenu.vue'
  import Footer from '@/core/components/Footer.vue'
  import Button from '@/core/components/Button.vue'
  import UserProduct from '../components/UserProduct.vue'
  import { onMounted } from 'vue'
  import { ref } from 'vue'
  import productService from '@/core/utils/productService'

  let products = ref([])

  onMounted(async () => {
    products.value = await productService.loadProducts('me/products')
  })
</script>

<style scoped>
  .container {
    margin: 80px 100px 146px 100px;
  }
  .row-page {
    margin: 80px 0px 150px 0px;
  }
</style>
