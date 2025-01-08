<template>
  <div class="page-wrapper">
    <Header />
    <v-divider />

    <div class="container" @click="loadPaths">
      <v-row>
        <Path
        :old-paths="oldPaths"
        :active-path="prod.name"
        />
      </v-row>

      <v-row style="margin-top: 80px;">
        <v-col>
          <div class="d-flex">
            <div class="d-flex flex-column">
              <img v-for="i in 4" :key="i"
                :src="card1"
                width="138"
                height="170"
              />
            </div>
            <div style="max-width: 500px;">
              <img
              class="fill-height fill-width"
                :src="card1"
                width="500"
              />
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
  // import card4 from '@/assets/card4.webp'

  import { useRoute } from 'vue-router';
  import { ref, onMounted } from 'vue'
  import loadPastPaths from "../utils/loadPastPaths";
  import Header from '@/core/Header.vue';
  import Path from '@/core/Path.vue';
  import Footer from '@/core/Footer.vue';
  //import router from '@/router';
  const oldPaths = ref([])

  const route = useRoute()

  onMounted(() => {
    oldPaths.value = loadPastPaths(route)
  })

  const prod = ref({
    discount: 35,
    name: "Laptop",
    price: "960",
    fullPrice: "1160",
    isInWishList: true,
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
  </style>
  