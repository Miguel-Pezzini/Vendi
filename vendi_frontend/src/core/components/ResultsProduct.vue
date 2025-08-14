<template>
  <v-card>
    <div>
      <v-row align="center" class="ma-2">
        <v-col cols="auto" class="pa-0 ma-0">
          <span
            class="text-white rounded px-3 py-1"
            style="background-color: #dbb671; font-size: 12px">
            -{{ product.discount }}%
          </span>
        </v-col>
        <v-spacer />
        <v-col cols="auto" class="d-flex flex-column gap-2 pa-0 ma-0">
          <Button
            v-if="product.isInWishList"
            density="comfortable"
            :flat="true"
            icon="mdi-delete-outline" />
          <Button
            v-else
            :color="product.isInWishList ? 'red' : 'black'"
            density="comfortable"
            :flat="true"
            :icon="product.isInWishList ? 'mdi-heart' : 'mdi-heart-outline'"
            @click="addToWishlist()" />
        </v-col>
      </v-row>

      <div @click="verProduct()" class="product-image">
        <img :src="product.image" class="w-100 h-100" />
      </div>
    </div>

    <v-btn
      color="black"
      prepend-icon="mdi-cart"
      :rounded="false"
      class="w-100 rounded-b"
      text="Add To Cart"
      @click="addToCart" />
  </v-card>

  <div class="mt-4">
    <span class="font-weight-medium" style="font-size: 16px">{{ product.name }}</span>
    <div class="d-flex gap-3 mt-3 align-center">
      <span class="font-weight-medium pr-2" style="color: golden; font-size: 16px"
        >R${{ product.price }}</span
      >
      <span v-if="product.discount" class="text-subtitle-2 text-decoration-line-through opacity-50">
        R${{ product.fullPrice }}
      </span>
    </div>

    <div v-if="!product.isInWishList" class="d-flex align-center mt-2 gap-2">
      <v-rating
        readonly
        half-increments
        :size="24"
        active-color="yellow"
        color="rgba(0,0,0,0.25)"
        :model-value="3.5" />
      <p class="text-caption font-weight-bold opacity-50 mb-0">(65)</p>
    </div>
  </div>
</template>

<script setup>
  import Button from '@/core/components/Button'
  import router from '../router'
  import { useRoute } from 'vue-router'
  import loadPastPaths from '../utils/loadPastPaths'

  const route = useRoute()

  const emit = defineEmits(['addToWishlist', 'addToCart'])

  const props = defineProps({
    product: {
      type: Object,
      default: null,
    },
    activePage: {
      type: String,
      default: null,
    },
  })

  function addToWishlist() {
    //props.product.isInWishList = !props.product.isInWishList;
    emit('addToWishlist', props.product.isInWishList)
  }

  function addToCart() {
    emit('addToCart', props.product)
  }

  function verProduct() {
    router.push({
      path: '/product',
      query: { product: props.product.name, origin: loadPastPaths(route, props.activePage) },
    })
  }
</script>

<style scoped>
  .product-image {
    width: 250px;
    height: 200px;
    cursor: pointer;
    overflow: hidden;
    transition: filter 0.3s ease;
  }

  .product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition:
      transform 0.3s ease,
      filter 0.3s ease;
  }

  .product-image:hover img {
    filter: brightness(0.9);
    transform: scale(1.03);
  }
</style>
