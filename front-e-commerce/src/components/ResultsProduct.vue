<template>
  <div>
    <v-card width="270" height="280" elevation="0" color="#F5F5F5">
      <div>
        <div class="d-flex justify-space-between">
          <span class="mt-3 ml-3"> 35% </span>
          <v-icon class="mt-3 mr-3" icon="mdi-delete"/> 
        </div>
        <div class="image-container">
          a
        </div>
        <v-btn color="black" prepend-icon="mdi-cart" :rounded="false" class="w-100 rounded-b" text="Add To Cart" />
      </div>
    </v-card>
    <div class="mt-4">
      <span class="name">Guccy duffle bag</span>
      <div class="d-flex ga-3 mt-3">
        <span class="descount">R$ 960 </span>
        <span class="price">R$ 1160 </span>
      </div>
    </div>
  </div>
</template>
        
<script setup>
        
</script>
        
<style scoped>
    .image-container {
      box-sizing: content-box;
      display: flex;
      justify-content: center;
      align-items: center;
      width: 190px;
      height: 180px;
      padding: 15px 40px 15px 40px;
    }
    span {
      font-family: "Poppins", serif;
    }
    .name {
      font-weight: 500;
      font-size: 16px;
    }
    .price {
      text-decoration: line-through;
      opacity: 0.5;
      font-weight: 500;
      font-size: 16px;
    }
    .descount {
      color: #DBB671;
      font-weight: 500;
      font-size: 16px;
    }
</style>
        