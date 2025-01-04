<template>
  <v-card
    elevation="0"
    :disabled="loading"
    :loading="loading"
    class="mx-auto my-12"
    max-width="364"
  >
    <v-row>
      <v-col>
        <v-img
          height="104"
          src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
          cover
        />
      </v-col>
      <v-col class="d-flex flex-column">
        <v-row>
          <v-card-title>{{ product.name }}</v-card-title>
        </v-row>
        <v-row align="end">
          <v-col>
            <span class="me-1 subTitle">R${{ product.price }}</span>
          </v-col>
          <v-col>
            <v-row class="pa-4">
              <Button
                color="white"
                bg-color="black"
                density="compact"
                icon="mdi-minus"
                @click="decrementQuantity"
              />
              <span>{{ product.quantity }}</span>
              <Button
                color="white"
                bg-color="black"
                density="compact"
                icon="mdi-plus"
                @click="incrementQuantity"
              />
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup>
  import Button from '../components/Button'

  const props = defineProps({
        product: {
          type: Object,
          default: null
        },
    })

  function incrementQuantity() {
    //props.product.quantity++
  }
  function decrementQuantity() {
    if(props.product.quantity == 1) {
      //emit('deleteFromCart', props.id)
    }
    //props.product.quantity--
  }
    
</script>

<style scoped>
    .subTitle {
        color: #DBB671;
    }
</style>