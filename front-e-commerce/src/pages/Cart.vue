<template>
  <Header :cartActive="true" />
  <v-divider />
  <div class="container">
    <Path
      :old-paths="['Home']"
      active-path="Cart"
      style="margin-bottom: 80px;"
    />
    <div class="table">
      <v-row
        class="row"
        justify="center"
      >
        <v-col class="d-flex justify-center align-center">
          <span>Produto</span>
        </v-col>
        <v-col class="d-flex justify-center align-center">
          <span>Preco</span>
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
        justify="center"
      >
        <v-col class="d-flex justify-center align-center">
          <span>{{ cartProduct.name }}</span>
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
    </div>
  </div>
  <Footer />
</template>
    
<script setup>
    import { ref } from 'vue'
    import Header from '@/core/Header.vue';
    import Path from '@/core/Path.vue';
    //import Input from  '@/core/Input.vue';
    import Footer from '@/core/Footer.vue';

    const cartProducts = ref([{
      name: 'LCD Monitor',
      price: 650,
      quantity: 1,
      subtotal: 650,
    },
    {
      name: 'LCD Monitor',
      price: 650,
      quantity: 1,
      subtotal: 650,
    }])
</script>
    
<style scoped>
.container {
  margin-top: 80px;
  padding-right: 135px;
  padding-left: 135px;
}
.row {
  padding: 24px 24px 24px 24px;
  border-radius: 4px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
}
span {
  font-size: 16px;
}
.table {
  width: 100%;

  display: flex;
  flex-direction: column;
  gap: 30px;
}
</style>
    