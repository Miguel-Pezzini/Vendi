<template>
  <div class="page-wrapper">
    <Header />
    <v-divider />

    <div
      class="container"
      @click="loadPaths"
    >
      <v-row>
        <Path
          :old-paths="oldPaths"
          :active-path="prod.name"
        />
      </v-row>

      <v-row style="margin-top: 80px;">
        <v-col>
          <div class="d-flex product-container ga-8">
            <div class="d-flex flex-column ga-4">
              <v-img
                v-for="i in 4"
                :key="i"
                aspect-ratio="16/9"
                cover
                :src="card1"
                :height="calcularHeightImg(4)"
                width="170"
              />
            </div>
            <div style="max-width: 500px;">
              <v-img
                aspect-ratio="1/1"
                cover
                class="fill-height fill-width"
                :src="card1"
                :height="heightBigImg"
                width="500"
              />
            </div>
          </div>
        </v-col>
        <v-col class="ml-16">
          <div class="d-flex flex-column">
            <div class="d-flex flex-column ga-4">
              <h1>{{ prod.name }}</h1>
              <div class="d-flex ga-4">
                <div class="d-flex ga-2">
                  <v-rating
                    :size="24"
                    active-color="yellow"
                    :model-value="prod.rating"
                    readonly
                  />
                  <p
                    v-if="prod.reviews"
                    style="opacity: 0.5;"
                  >
                    ({{ prod.reviews }} Avaliações)
                  </p>
                </div>
                <p style="opacity: 0.5;">
                  |
                </p>
                <span :style="{ color: prod.inStock ? '#00FF66' : '#FF0000' }">
                  {{ prod.inStock ? 'Em Estoque' : 'Indisponível' }}
                </span>
              </div>
              <h2>R$ {{ prod.price }}</h2>
            </div>
          </div>
        </v-col>
      </v-row>
    </div>
     
    <Footer />
  </div>
</template>
  
  <script setup>
  import card1 from '@/assets/card1.webp'
  // import card2 from '@/assets/card2.webp'
  // import card3 from '@/assets/card3.webp'
  //  import card4 from '@/assets/card4.webp'

  import { useRoute } from 'vue-router';
  import { ref, onMounted } from 'vue'
  import loadPastPaths from "../utils/loadPastPaths";
  import Header from '@/core/Header.vue';
  import Path from '@/core/Path.vue';
  import Footer from '@/core/Footer.vue';
  //import router from '@/router';
  const oldPaths = ref([])

  const heightBigImg = 600;

  const route = useRoute()

  onMounted(() => {
    oldPaths.value = loadPastPaths(route)
  })
  
  function calcularHeightImg(timeImages) {
    return (heightBigImg / timeImages) - 64
  }

  const prod = ref({
    discount: 35,
    name: "Laptop",
    price: "960",
    fullPrice: "1160",
    isInWishList: true,
    rating: 4,
    reviews: 150,
    inStock: true,
  })

  </script>
  
  <style scoped>
  /* Layout base */
  body {
    font-family: "Poppins", serif;
  }
  .container {
    font-family: "Poppins", serif;
    margin-top: 80px;
    margin-bottom: 140px;
    margin-right: 135px;
    margin-left: 135px;
  }
  .product-container {
    max-height: 600px;
    max-width: 800px ;
  }
  </style>
  