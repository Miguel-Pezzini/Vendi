<template>
  <v-navigation-drawer
    :model-value="showCart"
    width="400"
    location="right"
    temporary
    @update:model-value="updateShowCart"
  >
    <v-list-item style="padding: 24px 48px 24px 32px">
      <template #title>
        <span style="font-size: 20px;">Seu carrinho tem <span style="font-weight: bold;">{{ quantity }} itens</span></span>
      </template>
    </v-list-item>
    <v-divider />
    <ProductCartCard :product="prodCart" />
    <ProductCartCard :product="prodCart" />
    <v-list-item
      link
      title="List Item 2"
    />
    <v-list-item
      link
      title="List Item 3"
    />
  </v-navigation-drawer>
</template>
        
<script setup>
    import { ref } from 'vue';
    import ProductCartCard from './ProductCartCard.vue';
    import { defineEmits } from 'vue';
  
    defineProps({
        showCart: {
        type: Boolean,
        default: false
        },
    })

    const emit = defineEmits(['update:showCart']);

    const updateShowCart = (value) => {
    emit('update:showCart', value);
    };
    
    const prodCart = ref({
        name: 'Monitor Gamer Curvo 49 DQHD, 240Hz, 1ms, HDMI e DisplayPort, HDR 1000, FreeSync Premium, Ajuste de Altura - LC49G95TSSLXZD',
        price: 1000.01,
        quantity: 2,
  })
  const quantity = ref(4)
</script>

<style scoped>
</style>