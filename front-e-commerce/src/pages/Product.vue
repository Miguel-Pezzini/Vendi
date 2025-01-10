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
              <div
                v-for="image in listImages"
                :key="image.img"
                class="d-flex align-center"
                :style="{ border: image.active ? '2px solid #DBB671' : '1px solid #000' }"
                style="width: 80px; height: 80px; border-radius: 4px; padding: 5px; cursor: pointer;"
                @mouseover="setActiveImg(image.img)"
              >
                <v-img
                  aspect-ratio="16/9"
                
                
                  cover
                  :src="image.img"
                />
              </div>
            </div>
            <div style="max-width: 600px;">
              <v-img
                class="fill-height fill-width"
                :src="activeImage"
                :height="600"
                :width="600"
              />
            </div>
          </div>
        </v-col>
        <v-col class="ml-16">
          <div class="d-flex flex-column">
            <div class="d-flex flex-column ga-2">
              <h1>{{ prod.name }}</h1>
              <div class="d-flex ga-4">
                <div class="d-flex ga-2 align-center">
                  <v-rating
                    v-model="prod.rating"
                    half-increments
                    :size="24"
                    active-color="yellow"
                    readonly
                    color="#AAA"
                  />
                  <p
                    v-if="prod.reviews"
                    style="opacity: 0.5; font-size: 14px;"
                  >
                    ({{ prod.reviews }} Avaliações)
                  </p>
                </div>
                <p style="opacity: 0.5;">
                  |
                </p>
                <span
                  :style="{ color: prod.inStock ? '#00FF66' : '#FF0000' }"
                  style="font-size: 14px;;"
                >
                  {{ prod.inStock ? 'Em Estoque' : 'Indisponível' }}
                </span>
              </div>
              <h2>R$ {{ prod.price }}</h2>
            </div>
            <div class="d-flex flex-column ga-4 mt-6">
              <p style="font-size: 14px;">
                {{ prod.description }}
              </p>
              <v-divider opacity="0.5" />
              <div class="d-flex align-center ga-6">
                <h3>Cores: </h3>
                <div class="d-flex align-center ga-2">
                  <ColorButton color="red" />
                  <ColorButton color="blue" />
                </div>
              </div>
              <div class="d-flex align-center ga-6">
                <h3>Tamanho: </h3>
                <div class="d-flex align-center ga-4">
                  <SizeButton
                    size="XS"
                    :is-active="true"
                  />
                  <SizeButton
                    value="S"
                    size="S"
                    @click="emitClick(value)"
                  />
                  <SizeButton
                    value="M"
                    size="M"
                    @click="emitClick(value)"
                  />
                  <SizeButton
                    value="L"
                    size="L"
                    @click="emitClick(value)"
                  />
                  <SizeButton
                    value="XL"
                    size="XL"
                    @click="emitClick(value)"
                  />
                </div>
              </div>
              <div class="d-flex align-center">
                <v-col
                  cols="5"
                  class="pl-0"
                >
                  <v-number-input
                    density="comfortable"
                    variant="solo"
                    hide-details="false"
                    control-variant="split"
                    :min="1"
                    :model-value="prod.quantity"
                  />
                </v-col>
                <v-col cols="5">
                  <button class="button">
                    Comprar
                  </button>
                </v-col>
                <v-col cols="2">
                  <button class="button-icon">
                    <v-icon size="large">
                      mdi-heart-outline
                    </v-icon>
                  </button>
                </v-col>
              </div>
              <div class="delivery d-flex flex-column">
                <div class="delivery-container-one d-flex align-center ga-4">
                  <v-icon size="x-large">
                    mdi-truck-delivery-outline
                  </v-icon>
                  <div class="d-flex flex-column ga-2">
                    <h4>Delivery</h4>
                    <p style="font-size: 12px; font-weight: 500;">
                      Insira seu CEP para verificar opções de entrega
                    </p>
                  </div>
                </div>
                <div class="delivery-container-two d-flex align-center  ga-4">
                  <v-icon size="x-large">
                    mdi-cached
                  </v-icon>
                  <div class="d-flex flex-column ga-2">
                    <h4>Delivery</h4>
                    <p style="font-size: 12px; font-weight: 500;">
                      Devoluções de Entrega Grátis em 30 Dias. Detalhes
                    </p>
                  </div>
                </div>
              </div>
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
  import card2 from '@/assets/card2.webp'
  import card3 from '@/assets/card3.webp'
   import card4 from '@/assets/card4.webp'

  import { useRoute } from 'vue-router';
  import { ref, onMounted } from 'vue'
  import loadPastPaths from "../utils/loadPastPaths";
  import Header from '@/core/Header.vue';
  import Path from '@/core/Path.vue';
  import ColorButton from '@/core/ColorButton.vue';
  import SizeButton from '@/core/SizeButton.vue';
  import Footer from '@/core/Footer.vue';
  //import router from '@/router';
  const oldPaths = ref([])

  const listImages = [{img: card1, active: true}, {img: card2, active: false}, {img: card3, active: false}, {img: card4, active: false}]
  const activeImage = ref(card1)

  const route = useRoute()

  onMounted(() => {
    oldPaths.value = loadPastPaths(route)
  })

  function setActiveImg(image) {
    listImages.forEach(img => {
      img.img == image ? img.active = true : img.active = false
    })
    activeImage.value = image
  }

  const prod = ref({
    discount: 35,
    name: "Laptop",
    description: "PlayStation 5 Controller Skin High quality vinyl with air channel adhesive for easy bubble free install & mess free removal Pressure sensitive.",
    price: "960",
    fullPrice: "1160",
    isInWishList: true,
    rating: 4.5,
    quantity: 1,
    reviews: 150,
    inStock: true,
  })

  function emitClick(value) {
    console.log(value)
  }

  </script>
  
  <style scoped>
  .container {
    font-family: "Poppins", serif;
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
  h3 {
    font-size: 20px;
    font-weight: 400;
  }
  .product-container {
    max-height: 600px;
    max-width: 800px ;
  }
  .button {
    padding: 10px 40px 10px 40px;
    background-color: #DBB671;
    border: none;
    border-radius: 4px;
    color: #FFF;
    font-size: 16px;
    font-weight: 500;
    height: 48px;
    width: 100%;
  }
  .button-icon {
    padding: 4px;
    border: 1px solid #AAA;
    border-radius: 4px;
    height: 48px;
    width: 56px;
  }
  .delivery {
    border-radius: 4px;
    border: 1px solid #aaa;

  }
  h4 {
    font-size: 16px;
    font-weight: 500;
  }
  .delivery-container-one {
    padding: 20px 16px 16px 16px;
    border-bottom: 1px solid #aaa;
  }
  .delivery-container-two {
    padding: 16px 16px 20px 16px;
  }
  </style>
  