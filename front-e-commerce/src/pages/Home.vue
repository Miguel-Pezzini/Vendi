<template>
  <Header />
  <Slide :images="images" />
  <v-row
    class="container-images"
    justify="center"
  >
    <CardTransparent
      :image="cardOne.image"
      :title="cardOne.title"
    />
    <CardTransparent
      :image="cardTwo.image"
      :title="cardTwo.title"
    />
    <CardTransparent
      :image="cardThree.image"
      :title="cardThree.title"
    />
    <CardTransparent
      :image="cardFour.image"
      :title="cardFour.title"
    />
  </v-row>

  <h1 class="text-center my-7 text">
    Lançamentos
  </h1>
  <v-sheet>
  <v-slide-group show-arrows>
    <v-slide-group-item
      class="product-item"
      v-for="n in 6"
      :key="n"
    >
      <CardProducts
        :prop="productOne"
        class="mx-2"
      />
    </v-slide-group-item>
  </v-slide-group>
</v-sheet>
  <v-divider class="my-15" color="#DBB671" opacity=1 />
a
</template>
  
<script setup>
  import banner1 from '@/assets/banner1.jpg';
  import banner2 from '@/assets/banner2.jpg';
  import banner3 from '@/assets/banner3.jpg';

  import card1 from '@/assets/card1.webp'
  import card2 from '@/assets/card2.webp'
  import card3 from '@/assets/card3.webp'
  import card4 from '@/assets/card4.webp'

  import Header from '@/components/Header.vue';
  import Slide from '@/components/Slide.vue';
  import CardTransparent from '@/components/CardTransparent.vue';
  import CardProducts from '@/components/CardProducts.vue';

  const images = [banner1, banner2, banner3]
  const cardOne = {image: card1, title: "DECORAÇÃO"}
  const cardTwo = {image: card2, title: "COMPUTADORES"}
  const cardThree = {image: card3, title: "COZINHA"}
  const cardFour = {image: card4, title: "BANHEIRO"}

  const productOne = {image: card4, title: "Interruptor inteligente Sonoff Mini R2 - Wifi - Automação Residencial", price: 10.5, subdivision: 10}
</script>
  
<style scoped>
body {
  font-family: "Poppins", serif;
}
.container-images {
  margin-top: 50px;
}
.text {
  font-weight: 400;
}
.product-item {
  
}
</style>
  