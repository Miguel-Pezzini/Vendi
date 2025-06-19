<template>
  <Header :cart-active="true" />
  <v-divider />
  <div class="container">
    <Path :old-paths="['Home']" active-path="Cart" style="margin-bottom: 80px" />
    <div class="table">
      <v-row class="row" justify="center">
        <v-col class="d-flex justify-center align-center">
          <span>Produto</span>
        </v-col>
        <v-col class="d-flex justify-center align-center">
          <span>Preço</span>
        </v-col>
        <v-col class="d-flex justify-center align-center">
          <span>Quantidade</span>
        </v-col>
        <v-col class="d-flex justify-center align-center">
          <span>Subtotal</span>
        </v-col>
      </v-row>
      <v-row
        v-for="cartProduct in cartProducts"
        :key="cartProduct.name"
        class="row"
        justify="center">
        <v-col class="d-flex justify-center align-center">
          <div class="d-flex justify-center align-center ga-6">
            <v-img rounded="lg" width="54" height="54" :src="cartProduct.image" /><span>{{
              cartProduct.name
            }}</span>
          </div>
        </v-col>
        <v-col class="d-flex justify-center align-center">
          <span>R$ {{ cartProduct.price }}</span>
        </v-col>
        <v-col class="d-flex justify-center align-center">
          <span>{{ cartProduct.quantity }}</span>
        </v-col>
        <v-col class="d-flex justify-center align-center">
          <span>R$ {{ cartProduct.price * cartProduct.quantity }}</span>
        </v-col>
      </v-row>
      <v-row style="margin-bottom: 80px">
        <button class="button">Retornar à busca</button>
        <v-spacer />
        <button class="button">Atualizar carrinho</button>
      </v-row>
      <v-row align="start">
        <v-col class="d-flex justify-center align-center">
          <v-row class="ga-4">
            <Input
              hide-details="true"
              variant="outlined"
              :max-width="300"
              label="Codigo de cupom" />
            <button class="button golden">Aplicar cupom</button>
          </v-row>
        </v-col>
        <v-spacer />
        <v-col class="cart-total">
          <v-row class="mb-6">
            <h1>Cart Total</h1>
          </v-row>
          <v-row class="mb-4">
            <h2>Subtotal:</h2>
            <v-spacer /> R$ 1750
          </v-row>
          <v-row class="mb-4">
            <v-divider class="border-opacity-50" color="#000" />
          </v-row>
          <v-row class="mb-4">
            <h2>Frete:</h2>
            <v-spacer /> Free
          </v-row>
          <v-row class="mb-4">
            <v-divider class="border-opacity-50" color="#000" />
          </v-row>
          <v-row class="mb-4">
            <h2>Total:</h2>
            <v-spacer /> R$ 1750
          </v-row>
          <v-row justify="center">
            <button class="button golden" @click="irCheckout">Avançar para o checkout</button>
          </v-row>
        </v-col>
      </v-row>
    </div>
  </div>
  <Footer />
</template>

<script setup>
  import card1 from '@/assets/card1.webp'
  import card2 from '@/assets/card2.webp'

  import { ref } from 'vue'
  import router from '@/core/router'
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import Input from '@/core/components/Input.vue'
  import Footer from '@/core/components/Footer.vue'

  const cartProducts = ref([
    {
      name: 'LCD Monitor',
      price: 650,
      quantity: 1,
      subtotal: 650,
      image: card1,
    },
    {
      name: 'LCD Monitor',
      price: 650,
      quantity: 1,
      subtotal: 650,
      image: card2,
    },
  ])

  function irCheckout() {
    router.push({ path: '/checkout', query: { origin: ['Home', 'Cart'] } })
  }
</script>

<style scoped>
  .container {
    margin-top: 80px;
    padding-right: 135px;
    padding-left: 135px;
  }
  .row {
    padding: 12px;
    border-radius: 4px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
  }
  span {
    font-size: 16px;
  }
  .table {
    width: 100%;
    margin-bottom: 140px;

    display: flex;
    flex-direction: column;
    gap: 30px;
  }
  .button {
    font-size: 16px;
    border: 1px solid #000;
    border-radius: 4px;
    padding: 16px 48px;
    color: #000;
    background-color: #fff;
  }
  .golden {
    background-color: #dbb671;
    color: white;
    border: none;
  }
  .cart-total {
    border: 2px solid #000;
    border-radius: 4px;
    padding: 32px 24px 32px 24px;
  }
  h1 {
    font-size: 20px;
    font-weight: 500;
  }
  h2 {
    font-weight: 400;
    font-size: 16px;
  }
</style>
