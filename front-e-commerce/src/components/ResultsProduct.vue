<template>
  <div>
    <v-card width="270" height="300" elevation="0" color="#F5F5F5">
      <div>
        <v-row>
          <v-col cols="auto  mt-3 ml-3">
            <span class="icon-discount"> -{{product.discount}}% </span>
          </v-col>
          <v-spacer />
          <v-col cols="auto" class="icons-container mt-3 mr-3">
            <Button v-if="wishListProduct" density="comfortable" :flat="true" icon="mdi-delete-outline" />
            <Button density="comfortable" :flat="true" icon="mdi-heart-outline" />
          </v-col>
        </v-row>
          
          
        <div class="image-container">
          <img src="../assets/card3.webp"/>
        </div>
        <v-btn color="black" prepend-icon="mdi-cart" :rounded="false" class="w-100 rounded-b" text="Add To Cart" />
      </div>
    </v-card>
    <div class="mt-4">
      <span class="name">{{product.name}}</span>
      <div class="d-flex ga-3 mt-3">
        <span class="actualPrice">{{product.price}} </span>
        <span v-if="product.discount" class="price">{{product.fullPrice}}</span>
      </div>
    </div>
  </div>
</template>
        
<script setup>
      import Button from '../components/Button'
      import { ref } from 'vue';

      defineProps({
        product: {
          type: Object,
          default: null
        },
        wishListProduct: {
          type: Boolean,
          default: false
        },
    })
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
    span {
      font-family: "Poppins", serif;
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
      background-color: #DBB671;
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
      color: #DBB671;
      font-weight: 500;
      font-size: 16px;
    }
</style>
        