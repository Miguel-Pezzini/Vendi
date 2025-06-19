<template>
  <div>
    <v-card :hover="false" width="270" height="294" @click="verProduct()">
      <div>
        <v-row align="center">
          <v-col cols="auto  mt-3 ml-3">
            <span class="icon-discount"> -{{ product.discount }}% </span>
          </v-col>
          <v-spacer />
          <v-col cols="auto" class="icons-container mt-3 mr-3">
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
              @click="toggleWishList()" />
          </v-col>
        </v-row>

        <div class="image-container">
          <img :src="product.image" />
        </div>
        <v-btn
          color="black"
          prepend-icon="mdi-cart"
          :rounded="false"
          class="w-100 rounded-b"
          text="Add To Cart"
          @click="addToCart" />
      </div>
    </v-card>
    <div class="mt-4">
      <span class="name">{{ product.name }}</span>
      <div class="d-flex ga-3 mt-3">
        <span class="actualPrice">R${{ product.price }} </span>
        <span v-if="product.discount" class="price">R${{ product.fullPrice }}</span>
      </div>
      <div v-if="!product.isInWishList" class="d-flex align-center mt-2">
        <v-rating
          readonly="true"
          half-increments
          :size="24"
          active-color="yellow"
          color="rgba(0, 0, 0, 0.25)"
          :model-value="3.5" />
        <p style="font-size: 14px; font-weight: 700; opacity: 0.5">(65)</p>
      </div>
    </div>
  </div>
</template>

<script setup>
  import Button from '@/core/components/Button'
  import router from '../router'
  import { useRoute } from 'vue-router'
  import loadPastPaths from '../utils/loadPastPaths'

  const route = useRoute()

  const emit = defineEmits(['toggleWishList', 'addToCart'])

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

  function toggleWishList() {
    //props.product.isInWishList = !props.product.isInWishList;
    emit('toggleWishList', props.product.isInWishList)
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
  .image-container {
    box-sizing: content-box;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 270px;
    height: 180px;
    padding: 15px 0px 15px 0px;
    overflow: hidden;
  }
  .image-container img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
  }
  .name {
    font-weight: 500;
    font-size: 16px;
  }
  .icons-container {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  .icon-discount {
    font-size: 12px;
    padding: 4px 12px 4px 12px;
    background-color: #dbb671;
    color: white;
    border-radius: 4px;
  }
  .price {
    text-decoration: line-through;
    opacity: 0.5;
    font-weight: 500;
    font-size: 16px;
  }
  .actualPrice {
    color: golden;
    font-weight: 500;
    font-size: 16px;
  }
</style>
