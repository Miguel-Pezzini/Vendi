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

  const images = [banner1, banner2, banner3]
  const cardOne = {image: card1, title: "DECORAÇÃO"}
  const cardTwo = {image: card2, title: "COMPUTADORES"}
  const cardThree = {image: card3, title: "COZINHA"}
  const cardFour = {image: card4, title: "BANHEIRO"}
</script>
  
<style scoped>
.container-images {
  margin-top: 100px;
}
</style>
  