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

  <h1 class="text-center my-7 font-weight-regular">
    Lançamentos
  </h1>
  <v-sheet>
    <v-slide-group show-arrows>
      <v-slide-group-item
        v-for="n in 6"
        :key="n"
        class="product-item"
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
        <input class="py-3 pl-6 input" placeholder="Digite seu e-mail...">
        <v-btn color="black" class="button" icon="mdi-check"></v-btn>
      </div>
      
    </v-col>
  </v-row>

  <v-row class="footer">
    <v-col cols="3">
      <h1 class="title-footer">Categorias</h1>
      <p class="text-footer">Casa inteligente • Interruptores • Tomadas • Relé • Hubs • Sensores • Lâmpadas • Controles</p>
    </v-col>
    <v-col cols="3">
      <h1 class="title-footer">Institucional</h1>
      <p class="text-footer">Fale Conosco • Política de Privacidade • Quem Somos • Troca e Devolução</p>
    </v-col>
    <v-col cols="2">
      <h1 class="title-footer">Contatos</h1>
      <p class="text-footer">Telefone: +55 XX XXXX-XXXX<br/>
        Whatsapp: +55 XX XXXXX-XXXX<br/>
        E-mail: xxxxxxxx@xxxxxxx.xxx</p>
    </v-col>
    <v-col cols="2">
      <h1 class="title-footer">Atendimento</h1>
      <p class="text-footer">Seg à Sáb das 8h00 às 18h00</p>
    </v-col>
  </v-row>
  <v-row class="footer-icons justify-center align-center">
    <v-divider class="divider-footer" opacity="1" color="#FFFFFF"></v-divider>
    <v-row class="row-icons justify-space-evenly">
      <v-btn icon="mdi-facebook" color="black"/>
      <v-btn icon="mdi-twitter" color="black"/>
      <v-btn icon="mdi-instagram" color="black"/>
      <v-btn icon="mdi-youtube" color="black"/>
      <v-btn icon="mdi-pinterest" color="black"/>
    </v-row>
    <v-divider class="divider-footer" opacity="1" color="#FFFFFF"></v-divider>
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
.footer {
  padding-top: 36px;
  padding-bottom: 69px;
  padding-left: 100px;
  gap: 20px;
  color: #FFF;
  background-color: black;
}
.title-footer {
  font-size: 18px;
  font-weight: 600;
}
.text-footer {
  font-size: 14px;
  font-weight: 400;
  line-height: 26px;
}
.footer-icons {
  color: #FFF;
  background-color: black;
  padding-bottom: 50px;
}
.divider-footer {
  flex:auto;
}
</style>
  