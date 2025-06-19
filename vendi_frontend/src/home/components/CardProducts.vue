<template>
  <v-card class="ma-4" height="560" width="400">
    <v-img
      class="image"
      height="350"
      :src="`data:${product.mainPhoto.contentType};base64,${product.mainPhoto.data}`" />
    <v-card-item class="pb-0">
      <v-card-text class="text-center title pb-2">
        {{ product.name }}
      </v-card-text>
      <v-card-subtitle class="text-center pa-0 descount">
        {{ product.installment }}x de R${{
          mostrarPriceDescontado(product.price, product.installment)
        }}
      </v-card-subtitle>
      <v-card-subtitle class="text-center price">
        R${{ mostrarPrice(product.price) }}
      </v-card-subtitle>
    </v-card-item>
    <v-card-actions class="d-flex justify-center pa-0">
      <Button title="Ver mais" class="button" bg-color="#DBB671" @click="goToProduct" />
    </v-card-actions>
  </v-card>
</template>

<script setup>
  import Button from '@/core/components/Button.vue'
  import mostrarPrice from '@/core/utils/mostrarPrice'
  import router from '@/core/router'
  const props = defineProps({
    product: {
      type: Object,
      default: null,
    },
    origin: {
      type: Array,
      default: () => [],
    },
  })

  function mostrarPriceDescontado(price, subdivision) {
    let priceDescontado = price / subdivision

    let priceFormatado = priceDescontado.toFixed(2)

    return priceFormatado.replace('.', ',')
  }

  function goToProduct() {
    router.push({ path: `/product/${props.product.id}`, query: { origin: props.origin } })
  }
</script>

<style scoped>
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
    transition: 0.2s;
    scale: 1.01;
  }
</style>
