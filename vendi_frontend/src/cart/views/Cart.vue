<template>
  <Header cart-active />
  <v-divider />

  <div class="cart-page">
    <div class="cart-container">
      <Path :old-paths="['Home']" active-path="Cart" class="cart-path" />

      <section class="cart-table">
        <div class="cart-table__head">
          <span>Produto</span>
          <span>Preco</span>
          <span>Quantidade</span>
          <span>Subtotal</span>
        </div>

        <article
          v-for="cartProduct in cartProducts"
          :key="cartProduct.name"
          class="cart-item">
          <div class="cart-item__product">
            <v-img rounded="xl" width="72" height="72" :src="cartProduct.image" cover />
            <div class="cart-item__details">
              <span class="cart-item__label cart-item__label--mobile">Produto</span>
              <span class="cart-item__name">{{ cartProduct.name }}</span>
            </div>
          </div>

          <div class="cart-item__meta">
            <span class="cart-item__label cart-item__label--mobile">Preco</span>
            <span>R$ {{ cartProduct.price }}</span>
          </div>

          <div class="cart-item__meta">
            <span class="cart-item__label cart-item__label--mobile">Quantidade</span>
            <span>{{ cartProduct.quantity }}</span>
          </div>

          <div class="cart-item__meta cart-item__meta--subtotal">
            <span class="cart-item__label cart-item__label--mobile">Subtotal</span>
            <span>R$ {{ cartProduct.price * cartProduct.quantity }}</span>
          </div>
        </article>
      </section>

      <div class="cart-actions">
        <button class="button">Retornar a busca</button>
        <button class="button">Atualizar carrinho</button>
      </div>

      <section class="cart-summary-layout">
        <div class="coupon-card">
          <h2 class="coupon-card__title">Cupom</h2>
          <p class="coupon-card__subtitle">Adicione um codigo promocional antes de finalizar.</p>
          <div class="coupon-card__form">
            <Input hide-details="true" variant="outlined" label="Codigo de cupom" />
            <button class="button golden">Aplicar cupom</button>
          </div>
        </div>

        <div class="cart-total">
          <h1>Cart Total</h1>
          <div class="cart-total__row">
            <h2>Subtotal:</h2>
            <span>R$ 1750</span>
          </div>
          <v-divider class="border-opacity-50" color="#000" />
          <div class="cart-total__row">
            <h2>Frete:</h2>
            <span>Free</span>
          </div>
          <v-divider class="border-opacity-50" color="#000" />
          <div class="cart-total__row cart-total__row--total">
            <h2>Total:</h2>
            <span>R$ 1750</span>
          </div>
          <button class="button golden cart-total__button" @click="irCheckout">
            Avancar para o checkout
          </button>
        </div>
      </section>
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
  .cart-page {
    padding: 2rem 1rem 4.5rem;
  }

  .cart-container {
    max-width: 1320px;
    margin: 0 auto;
  }

  .cart-path {
    margin-bottom: 2rem;
  }

  .cart-table {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  .cart-table__head,
  .cart-item {
    display: grid;
    grid-template-columns: minmax(0, 2.1fr) repeat(3, minmax(110px, 1fr));
    gap: 1rem;
    align-items: center;
    padding: 1.25rem 1.5rem;
    border-radius: 1.25rem;
    border: 1px solid rgba(15, 23, 42, 0.08);
    background: #fff;
    box-shadow:
      0 18px 36px rgba(15, 23, 42, 0.05),
      0 2px 8px rgba(15, 23, 42, 0.04);
  }

  .cart-table__head {
    color: rgba(17, 24, 39, 0.72);
    font-size: 0.95rem;
    font-weight: 600;
  }

  .cart-item__product {
    display: flex;
    align-items: center;
    gap: 1rem;
    min-width: 0;
  }

  .cart-item__details {
    display: flex;
    min-width: 0;
    flex-direction: column;
    gap: 0.3rem;
  }

  .cart-item__name {
    color: #111827;
    font-size: 1rem;
    font-weight: 600;
    line-height: 1.45;
    word-break: break-word;
  }

  .cart-item__meta {
    display: flex;
    flex-direction: column;
    gap: 0.25rem;
    color: rgba(17, 24, 39, 0.92);
    font-size: 0.98rem;
    font-weight: 500;
    text-align: center;
  }

  .cart-item__meta--subtotal {
    font-weight: 700;
  }

  .cart-item__label {
    color: rgba(17, 24, 39, 0.56);
    font-size: 0.76rem;
    font-weight: 700;
    letter-spacing: 0.04em;
    text-transform: uppercase;
  }

  .cart-item__label--mobile {
    display: none;
  }

  .cart-actions {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 1rem;
    margin: 1.5rem 0 2rem;
  }

  .cart-summary-layout {
    display: grid;
    grid-template-columns: minmax(0, 1.1fr) minmax(320px, 420px);
    gap: 1.5rem;
    align-items: start;
  }

  .coupon-card,
  .cart-total {
    border-radius: 1.5rem;
    border: 1px solid rgba(15, 23, 42, 0.08);
    background: linear-gradient(180deg, #ffffff 0%, #f8fafc 100%);
    box-shadow:
      0 18px 36px rgba(15, 23, 42, 0.05),
      0 2px 8px rgba(15, 23, 42, 0.04);
  }

  .coupon-card {
    padding: 1.5rem;
  }

  .coupon-card__title {
    margin: 0;
    color: #111827;
    font-size: 1.25rem;
    font-weight: 600;
  }

  .coupon-card__subtitle {
    margin: 0.5rem 0 1.25rem;
    color: rgba(17, 24, 39, 0.62);
    font-size: 0.95rem;
    line-height: 1.55;
  }

  .coupon-card__form {
    display: grid;
    grid-template-columns: minmax(0, 1fr) auto;
    gap: 0.875rem;
    align-items: start;
  }

  .button {
    min-height: 48px;
    padding: 0.875rem 1.5rem;
    font-size: 1rem;
    font-weight: 600;
    border: 1px solid rgba(15, 23, 42, 0.16);
    border-radius: 999px;
    color: #000;
    background-color: #fff;
    transition:
      transform 0.2s ease,
      background-color 0.2s ease,
      border-color 0.2s ease;
  }

  .golden {
    background-color: #dbb671;
    color: white;
    border: none;
  }

  .cart-total {
    padding: 1.75rem 1.5rem;
  }

  .cart-total__row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 1rem;
    padding: 1rem 0;
    color: #111827;
  }

  .cart-total__row--total {
    font-weight: 700;
  }

  .cart-total__button {
    width: 100%;
    margin-top: 1rem;
  }

  h1 {
    margin: 0 0 0.5rem;
    font-size: 1.25rem;
    font-weight: 600;
    color: #111827;
  }

  h2 {
    margin: 0;
    font-weight: 500;
    font-size: 1rem;
  }

  @media (hover: hover) {
    .button:hover {
      transform: translateY(-1px);
      background-color: #f8fafc;
      border-color: rgba(219, 182, 113, 0.45);
    }

    .golden:hover {
      background-color: #cfa455;
    }
  }

  @media (max-width: 1023px) {
    .cart-summary-layout {
      grid-template-columns: 1fr;
    }
  }

  @media (max-width: 767px) {
    .cart-page {
      padding: 1.5rem 0.75rem 3.5rem;
    }

    .cart-table__head {
      display: none;
    }

    .cart-item {
      grid-template-columns: 1fr;
      gap: 1rem;
      padding: 1rem;
    }

    .cart-item__meta {
      align-items: flex-start;
      text-align: left;
    }

    .cart-item__label--mobile {
      display: inline-flex;
    }

    .cart-actions {
      flex-direction: column;
    }

    .cart-actions .button,
    .coupon-card__form .button {
      width: 100%;
    }

    .coupon-card__form {
      grid-template-columns: 1fr;
    }
  }
</style>
