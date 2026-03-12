<template>
  <div class="page-wrapper">
    <Header />
    <v-divider />

    <div class="container">
      <v-row>
        <Path :old-paths="oldPaths" :active-path="product?.name || 'Product'" />
      </v-row>

      <v-row v-if="loading" style="margin-top: 80px">
        <v-col cols="12">
          <v-skeleton-loader type="image, article" />
        </v-col>
      </v-row>

      <v-row v-else-if="product" style="margin-top: 80px">
        <v-col>
          <div class="d-flex product-container ga-8">
            <div class="d-flex flex-column ga-4">
              <div
                v-for="image in listImages"
                :key="image.img"
                class="d-flex align-center"
                :style="{ border: image.active ? '2px solid #DBB671' : '1px solid #000' }"
                style="width: 80px; height: 80px; border-radius: 4px; padding: 5px; cursor: pointer"
                @mouseover="setActiveImg(image.img)">
                <v-img aspect-ratio="1" cover :src="image.img" />
              </div>
            </div>
            <div style="max-width: 600px">
              <v-img class="fill-height fill-width" :src="activeImage" :height="600" :width="600" />
            </div>
          </div>
        </v-col>
        <v-col class="ml-16">
          <div class="d-flex flex-column">
            <div class="d-flex flex-column ga-2">
              <h1>{{ product.name }}</h1>
              <div class="d-flex ga-4">
                <span :style="{ color: product.quantity > 0 ? '#00AA55' : '#FF0000' }">
                  {{ product.quantity > 0 ? 'In stock' : 'Out of stock' }}
                </span>
              </div>
              <h2>R$ {{ product.price }}</h2>
              <p v-if="product.discount">Discount: {{ product.discount }}%</p>
            </div>
            <div class="d-flex flex-column ga-4 mt-6">
              <p style="font-size: 14px">
                Installments available: {{ product.installment }}
              </p>
              <p style="font-size: 14px">
                Categories:
                {{ product.categories.map((category) => category.name).join(', ') || 'None' }}
              </p>
              <v-divider opacity="0.5" />
              <div class="d-flex align-center">
                <v-col cols="5" class="pl-0">
                  <v-number-input
                    v-model="quantity"
                    density="comfortable"
                    variant="solo"
                    hide-details="false"
                    control-variant="split"
                    :min="1"
                    :max="product.quantity" />
                </v-col>
                <v-col cols="5">
                  <button class="button" @click="addToCart">Adicionar ao carrinho</button>
                </v-col>
              </div>
            </div>
          </div>
        </v-col>
      </v-row>

      <v-alert v-else type="error" style="margin-top: 80px">
        Product not found.
      </v-alert>
    </div>

    <Footer />
  </div>
</template>

<script setup>
  import { useRoute } from 'vue-router'
  import { getCurrentInstance, onMounted, ref } from 'vue'
  import loadPastPaths from '@/core/utils/loadPastPaths'
  import Header from '@/core/components//Header.vue'
  import Path from '@/core/components/Path.vue'
  import Footer from '@/core/components/Footer.vue'
  import productService from '@/core/utils/productService'
  import cartService from '@/core/services/cartService'

  const { proxy } = getCurrentInstance()
  const oldPaths = ref([])
  const route = useRoute()
  const loading = ref(false)
  const product = ref(null)
  const listImages = ref([])
  const activeImage = ref(null)
  const quantity = ref(1)

  onMounted(async () => {
    oldPaths.value = loadPastPaths(route)
    await loadProduct()
  })

  async function loadProduct() {
    loading.value = true

    try {
      product.value = await productService.loadProductDetails(route.params.productId)
      listImages.value = [product.value.mainPhoto, ...product.value.photos].map((photo, index) => ({
        img: photo.dataURI,
        active: index === 0,
      }))
      activeImage.value = listImages.value[0]?.img || null
    } catch (error) {
      product.value = null
    } finally {
      loading.value = false
    }
  }

  function setActiveImg(image) {
    listImages.value = listImages.value.map((img) => ({
      ...img,
      active: img.img === image,
    }))
    activeImage.value = image
  }

  async function addToCart() {
    if (!product.value) return

    try {
      await cartService.addItem(product.value.id, quantity.value)
      proxy.$showMessage('success', 'Product added to cart.')
    } catch (error) {
      proxy.$showMessage('error', 'You need to be logged in to manage the cart.')
    }
  }
</script>

<style scoped>
  .container {
    margin-top: 80px;
    margin-bottom: 140px;
    margin-right: 135px;
    margin-left: 135px;
  }
  h1 {
    font-size: 24px;
    font-weight: 700;
  }
  h2 {
    font-size: 24px;
    font-weight: 400;
  }
  .product-container {
    max-height: 600px;
    max-width: 800px;
  }
  .button {
    padding: 10px 40px 10px 40px;
    background-color: #dbb671;
    border: none;
    border-radius: 4px;
    color: #fff;
    font-size: 16px;
    font-weight: 500;
    height: 48px;
    width: 100%;
  }
</style>
