<template>
  <Header account-active />
  <v-divider />
  <div class="container">
    <v-row>
      <Path :old-paths="['Home']" active-path="My Products" />
      <v-spacer />
      <Button title="Add Product" variant="outlined" color="#DBB671" to="/user/products/create" />
    </v-row>

    <v-row class="container margin">
      <v-col class="pa-0" cols="4">
        <SideMenu active-my-products />
      </v-col>
      <v-col cols="8">
        <v-row>
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
  import card1 from '@/assets/card1.webp'
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import SideMenu from '@/user/profile/components/SideMenu.vue'
  import Footer from '@/core/components/Footer.vue'
  import Button from '@/core/components/Button.vue'
  import UserProduct from '../components/UserProduct.vue'
  import { onMounted } from 'vue'
  import { ref } from 'vue'
  import api from '@/core/plugins/api'
  import loadProductPhoto from '@/core/utils/loadProductPhoto'

  let products = ref([])

  onMounted(async () => {
    await getUserProducts()
  })

  async function getUserProducts() {
    const userProducts = await api.getAll('product/user')

    await Promise.all(
      userProducts.map(async (product) => {
        const photo = await loadProductPhoto(product.mainPhoto.id)
        product.mainPhoto = { ...product.mainPhoto, data: photo }
      })
    )
    products.value = userProducts
  }
</script>

<style scoped>
  .container {
    margin: 80px 100px 146px 100px;
  }
  .margin {
    margin-bottom: 146px;
  }
</style>
