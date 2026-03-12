<template>
  <v-navigation-drawer
    :model-value="showCart"
    width="400"
    location="right"
    temporary
    @update:model-value="updateShowCart">
    <v-list-item style="padding: 24px 48px 24px 32px">
      <template #title>
        <span style="font-size: 20px"
          >Seu carrinho tem <span style="font-weight: bold">{{ quantity }} itens</span></span
        >
      </template>
      <template #subtitle>
        <RouterLink style="text-decoration: none; color: blue" to="/cart">
          Mais detalhes
        </RouterLink>
      </template>
    </v-list-item>
    <v-divider />
    <ProductCartCard
      v-for="item in cart.items"
      :key="item.id"
      :product="item"
      :loading="loading" />
  </v-navigation-drawer>
</template>

<script setup>
  import { ref, watch } from 'vue'
  import ProductCartCard from './ProductCartCard.vue'
  import { RouterLink } from 'vue-router'
  import cartService from '@/core/services/cartService'

  const props = defineProps({
    showCart: {
      type: Boolean,
      default: false,
    },
  })

  const loading = ref(false)
  const cart = ref({ items: [], totalItems: 0 })

  const emit = defineEmits(['update:showCart'])

  const updateShowCart = (value) => {
    emit('update:showCart', value)
  }

  const quantity = ref(0)

  watch(
    () => props.showCart,
    async (isOpen) => {
      if (!isOpen) return

      loading.value = true

      try {
        cart.value = await cartService.getCart()
        quantity.value = cart.value.totalItems
      } catch (error) {
        cart.value = { items: [], totalItems: 0 }
        quantity.value = 0
      } finally {
        loading.value = false
      }
    },
    { immediate: true }
  )
</script>

<style scoped></style>
