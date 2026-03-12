<template>
  <v-card
    elevation="0"
    :disabled="loading"
    :loading="loading"
    class="product-cart-card"
    rounded="xl">
    <div class="product-cart-card__layout">
      <v-img
        class="product-cart-card__image"
        rounded="xl"
        height="104"
        width="104"
        :src="product.product.image"
        cover />

      <div class="product-cart-card__content">
        <div class="product-cart-card__name">
          {{ product.product.name }}
        </div>

        <div class="product-cart-card__footer">
          <span class="subTitle">R$ {{ formatPrice(product.product.price) }}</span>

          <div class="product-cart-card__quantity">
            <button class="button" @click="decrementQuantity">
              <v-icon>mdi-minus</v-icon>
            </button>
            <span class="product-cart-card__count">{{ product.quantity }}</span>
            <button class="button" @click="incrementQuantity">
              <v-icon>mdi-plus</v-icon>
            </button>
          </div>
        </div>
      </div>
    </div>
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
  .product-cart-card {
    margin: 1rem;
    border: 1px solid rgba(15, 23, 42, 0.08);
    background: linear-gradient(180deg, #ffffff 0%, #f8fafc 100%);
    box-shadow:
      0 18px 36px rgba(15, 23, 42, 0.05),
      0 2px 8px rgba(15, 23, 42, 0.04);
  }

  .product-cart-card__layout {
    display: grid;
    grid-template-columns: auto minmax(0, 1fr);
    gap: 1rem;
    align-items: center;
    padding: 1rem;
  }

  .product-cart-card__content {
    display: flex;
    min-width: 0;
    flex-direction: column;
    gap: 0.85rem;
  }

  .product-cart-card__name {
    color: #111827;
    font-size: 1rem;
    line-height: 1.5;
    font-weight: 500;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }

  .product-cart-card__footer {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-between;
    gap: 0.75rem;
  }

  .product-cart-card__quantity {
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.3rem;
    border-radius: 999px;
    background: rgba(15, 23, 42, 0.05);
  }

  .product-cart-card__count {
    min-width: 2rem;
    text-align: center;
    font-weight: 600;
  }

  .subTitle {
    font-size: 1rem;
    font-weight: 700;
    color: #dbb671;
  }

  .button {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 2rem;
    height: 2rem;
    padding: 0;
    border-radius: 999px;
    background-color: #000;
    color: #fff;
  }

  @media (max-width: 420px) {
    .product-cart-card {
      margin: 0.75rem;
    }

    .product-cart-card__layout {
      grid-template-columns: 1fr;
    }

    .product-cart-card__image {
      justify-self: center;
    }

    .product-cart-card__footer {
      flex-direction: column;
      align-items: flex-start;
    }
  }
</style>
