<template>
  <v-card
    class="ma-4"
    height="560"
    width="400"
  >
    <v-img
      class="image"
      height="350"
      :src="prop.image"
    />
    <v-card-item class="pb-0">
      <v-card-text class="text-center title pb-2">
        {{ prop.title }}
      </v-card-text>
      <v-card-subtitle class="text-center pa-0 descount">
        {{ prop.subdivision }}x de R${{ mostrarPriceDescontado(prop.price, prop.subdivision) }}
      </v-card-subtitle>
      <v-card-subtitle class="text-center price">
        R${{ mostrarPrice(prop.price) }}
      </v-card-subtitle>
    </v-card-item>
    <v-card-actions class="d-flex justify-center pa-0">
      <Button
        title="Ver mais"
        class="button"
        bg-color="#DBB671"
      />
    </v-card-actions>
  </v-card>
</template>
    
    <script setup>
    import Button from './Button.vue';
    defineProps({
    prop: {
      type: Object,
      default: null
    }
    })

  function mostrarPrice(price) {
    let priceFormatado = price.toFixed(2)

    return priceFormatado.replace('.', ',');
  }

  function mostrarPriceDescontado(price, subdivision) {
    let priceDescontado = price / subdivision;

    let priceFormatado = priceDescontado.toFixed(2)

    return priceFormatado.replace('.', ',')
  }
    </script>
    
    <style scoped>
    body {
      font-family: "Poppins", serif;
    }
    .title {
      font-size: 16px;
      font-weight: 400;
    }
    .price {
      font-size: 18px;
      color: black;
      font-weight: 600;
      opacity: 1;
    }
    .descount {
      font-size: 14px;
      font-weight: 500;
    }
    .button {
      width: 50%;
      height: 53px;
    }
    .button:hover {
      transition: .2s;
      scale: 1.01;
    }
</style>
    