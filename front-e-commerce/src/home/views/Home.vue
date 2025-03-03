<template>
  <Header />
  <Slide :images="images" />
  <v-row
    class="mt-13"
    justify="center"
  >
    <CardTransparent
      :image="cardOne.image"
      :title="cardOne.title"
      @click="router.push({ path: '/store', query: { category: 'decoration', origin: ['Home'] }, })"
    />
    <CardTransparent
      :image="cardTwo.image"
      :title="cardTwo.title"
      @click="router.push({ path: '/store', query: { category: 'computers', origin: ['Home'] }, })"
    />
    <CardTransparent
      :image="cardThree.image"
      :title="cardThree.title"
      @click="router.push({ path: '/store', query: { category: 'kitchen', origin: ['Home'] }, })"
    />
    <CardTransparent
      :image="cardFour.image"
      :title="cardFour.title"
      @click="router.push({ path: '/store', query: { category: 'bathroom', origin: ['Home'] },})"
    />
  </v-row>

  <h1 class="text-center my-7 font-weight-regular">
    Lançamentos
  </h1>
  <v-sheet>
    <v-slide-group show-arrows>
      <v-slide-group-item
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
  <v-divider
    class="mt-15 mb-3"
    color="#DBB671"
    opacity="1"
  />
  <v-row class="newsletter justify-center align-center py-8">
    <v-col>
      <h1
        class="font-weight-medium"
        style="font-size: 22px;"
      >
        Newsletter
      </h1>
    </v-col>
    
    <v-spacer />
    <v-col>
      <h2
        class="font-weight-light"
        style="font-size: 18px;"
      >
        Receba nossas ofertas por e-mail
      </h2>
    </v-col>
    <v-col>
      <div class="d-flex">
        <input
          class="py-3 pl-6 input"
          placeholder="Digite seu e-mail..."
        >
        <v-btn
          color="black"
          class="button"
          icon="mdi-check"
        />
      </div>
    </v-col>
  </v-row>

  <Footer />
</template>
  
<script setup>
  import router from '@/core/router';
  import banner1 from '@/assets/banners/banner1.jpg';
  import banner2 from '@/assets/banners/banner2.jpg';
  import banner3 from '@/assets/banners/banner3.jpg';

  import card1 from '@/assets/card1.webp'
  import card2 from '@/assets/card2.webp'
  import card3 from '@/assets/card3.webp'
  import card4 from '@/assets/card4.webp'

  import Header from '@/core/components/Header.vue';
  import Slide from '@/core/components/Slide.vue';
  import CardTransparent from '@/home/components/CardTransparent.vue';
  import CardProducts from '@/home/components/CardProducts.vue';
  import Footer from '@/core/components/Footer.vue';

  const images = [banner1, banner2, banner3]
  const cardOne = {image: card1, title: "DECORAÇÃO"}
  const cardTwo = {image: card2, title: "COMPUTADORES"}
  const cardThree = {image: card3, title: "COZINHA"}
  const cardFour = {image: card4, title: "BANHEIRO"}

  const productOne = {image: card4, title: "Interruptor inteligente Sonoff Mini R2 - Wifi - Automação Residencial", price: 10.5, subdivision: 10}
</script>
  
<style scoped>
button:hover {
  transition: .2s;
  scale: 1.05;
}
.newsletter {
  padding-left: 80px;
  padding-right: 80px;
}
.input {
  height: 50px;
  width: 340px;
  border: 1px solid #000;
  border-top-left-radius: 11px;
  border-bottom-left-radius: 11px;
}
.button {
  height: 50px;
  border-radius: 0;
  border-top-right-radius: 11px;
  border-bottom-right-radius: 11px;
}
</style>
  