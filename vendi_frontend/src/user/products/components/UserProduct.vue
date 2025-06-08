<template>
  <div>
    <v-card
      :hover="false"
      width="270"
      height="294"
      @click="verProduct()"
    >
      <div>
        <v-row align="center">
          <v-col cols="auto mt-3 ml-3">
            <span class="icon-discount text-white bg-golden py-1 px-3"> -{{ product.discount }}% </span>
          </v-col>
          <v-spacer />
          <v-col
            cols="auto"
            class="icons-container mt-3 mr-3"
          >
            <Button
              density="comfortable"
              :flat="true"
              icon="mdi-delete-outline"
            />
          </v-col>
        </v-row>
        <div class="image-container">
          <img :src="product.image">
        </div>
        <v-btn
          color="black"
          prepend-icon="mdi-pencil"
          :rounded="false"
          class="w-100 rounded-b"
          text="Modify Product"
          @click="addToCart"
        />
      </div>
    </v-card>
    <div class="mt-4">
      <span class="font-weight-medium">{{ product.name }}</span>
      <div class="d-flex ga-3 mt-3">
        <v-row class="pa-0 ma-0">
          <span class="text-golden font-weight-medium pr-2">R${{ product.price }} </span>
          <span
            v-if="product.discount"
            class="price font-weight-medium"
          >
            R${{ product.fullPrice }}
          </span>
          <v-spacer />
          <span style="opacity: 0.5;">
            {{ product.quantity }} remaining
          </span>
        </v-row>
      </div>
    </div>
  </div>
</template>
          
  <script setup>
        import Button from '@/core/components/Button'
  
        defineProps({
          product: {
            type: Object,
            default: null
          },
      })
  
      function verProduct() {
        
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
      .icons-container {
        display: flex;
        flex-direction: column;
        gap: 8px;
      }
      .icon-discount {
        font-size: 12px;
        border-radius: 4px;
      }
      .price {
        text-decoration: line-through;
        opacity: 0.5;
      }
  </style>
          