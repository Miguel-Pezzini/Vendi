<template>
  <v-card elevation="0" :disabled="loading" :loading="loading" class="mx-auto my-8" max-width="364">
    <v-row>
      <v-col cols="4">
        <v-img
          rounded="lg"
          height="104"
          width="104"
          src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
          cover />
      </v-col>
      <v-col cols="7" class="d-flex flex-column">
        <div
          style="
            height: 50px;
            font-size: 16px;
            line-height: 24px;
            font-weight: 400;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
          ">
          {{ product.name }}
        </div>

        <v-row align="end" justify="center" style="margin-bottom: -6px">
          <v-col class="d-flex align-center justify-center">
            <span class="subTitle">R$ {{ formatPrice(product.price) }}</span>
          </v-col>
          <v-col class="d-flex align-center justify-center">
            <v-row align="center" class="d-flex">
              <button class="button" @click="decrementQuantity">
                <v-icon>mdi-minus</v-icon>
              </button>
              <span style="padding: 14px">{{ product.quantity }}</span>
              <button class="button" @click="incrementQuantity">
                <v-icon>mdi-plus</v-icon>
              </button>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup>
  defineProps({
    product: {
      type: Object,
      required: true,
    },
    loading: {
      type: Boolean,
      default: false,
    },
  })

  function incrementQuantity() {}
  function decrementQuantity() {
    //emit('deleteFromCart', props.id)
  }
  function formatPrice(price) {
    return price.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 })
  }
</script>

<style scoped>
  .subTitle {
    font-size: 16px;
    font-weight: bold;
    color: #dbb671;
  }
  .button {
    padding: 6px;
    border-radius: 8px;
    background-color: #000;
    color: #fff;
  }
</style>
