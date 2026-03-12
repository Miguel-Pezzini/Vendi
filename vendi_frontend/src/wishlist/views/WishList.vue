<template>
  <div class="page-wrapper">
    <Header :wish-list-active="true" />
    <v-divider />

    <div class="wishlist-shell">
      <v-container class="wishlist-container">
        <section class="wishlist-section">
          <div class="section-header">
            <div>
              <h1 class="section-title">Lista de Desejos (4)</h1>
              <p class="section-subtitle">
                Seus produtos salvos, organizados para comparar e comprar depois.
              </p>
            </div>
            <button class="button">Mover todos para o carrinho</button>
          </div>

          <v-row class="cards-grid">
            <v-col
              v-for="n in 4"
              :key="`wishlist-${n}`"
              cols="12"
              sm="6"
              lg="4"
              xl="3">
              <ResultsProduct active-page="Wishlist" :product="prod1" />
            </v-col>
          </v-row>
        </section>

        <section class="wishlist-section wishlist-section--secondary">
          <div class="section-header">
            <div class="section-title-wrap">
              <div class="decoration" />
              <div>
                <h2 class="section-title">So Para Voce</h2>
                <p class="section-subtitle">
                  Sugestoes alinhadas ao que voce ja demonstrou interesse.
                </p>
              </div>
            </div>
            <button class="button">Ver Todos</button>
          </div>

          <v-row class="cards-grid">
            <v-col
              v-for="n in 4"
              :key="`recommended-${n}`"
              cols="12"
              sm="6"
              lg="4"
              xl="3">
              <ResultsProduct active-page="Wishlist" :product="prod2" />
            </v-col>
          </v-row>
        </section>
      </v-container>
    </div>

    <Footer />
  </div>
</template>

<script setup>
  import Header from '@/core/components/Header.vue'
  import Footer from '@/core/components/Footer.vue'
  import ResultsProduct from '@/core/components/ResultsProduct.vue'
  import card1 from '@/assets/card1.webp'

  import { ref } from 'vue'

  const prod1 = ref({
    discount: 35,
    name: 'Laptop',
    price: '960',
    fullPrice: '1160',
    isInWishList: true,
    image: card1,
  })

  const prod2 = ref({
    discount: 35,
    name: 'Laptop',
    price: '960',
    fullPrice: '1160',
    isInWishList: false,
    image: card1,
  })
</script>

<style scoped>
  .wishlist-shell {
    padding: 2rem 1rem 4.5rem;
  }

  .wishlist-container {
    max-width: 1320px;
    padding: 0;
  }

  .wishlist-section {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
  }

  .wishlist-section + .wishlist-section {
    margin-top: 3rem;
  }

  .wishlist-section--secondary {
    padding-top: 0.5rem;
  }

  .section-header {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-between;
    gap: 1rem;
  }

  .section-title-wrap {
    display: flex;
    align-items: center;
    gap: 1rem;
  }

  .section-title {
    margin: 0;
    color: #111827;
    font-size: clamp(1.35rem, 2vw, 1.75rem);
    font-weight: 600;
    line-height: 1.2;
  }

  .section-subtitle {
    margin: 0.4rem 0 0;
    color: rgba(17, 24, 39, 0.65);
    font-size: 0.96rem;
    line-height: 1.55;
  }

  .cards-grid {
    margin: 0 -0.5rem;
  }

  .button {
    min-height: 48px;
    padding: 0.875rem 1.5rem;
    color: #000;
    border-radius: 999px;
    border: 1px solid rgba(15, 23, 42, 0.18);
    background: #fff;
    font-weight: 600;
    transition:
      background-color 0.2s ease,
      border-color 0.2s ease,
      transform 0.2s ease;
  }

  .decoration {
    height: 48px;
    width: 16px;
    background-color: #dbb671;
    border-radius: 999px;
  }

  @media (hover: hover) {
    .button:hover {
      background-color: #f8fafc;
      border-color: rgba(219, 182, 113, 0.45);
      transform: translateY(-1px);
    }
  }

  @media (max-width: 959px) {
    .wishlist-shell {
      padding: 1.5rem 1rem 3.5rem;
    }
  }

  @media (max-width: 599px) {
    .wishlist-shell {
      padding-inline: 0.75rem;
    }

    .section-header {
      align-items: stretch;
    }

    .section-title-wrap {
      align-items: flex-start;
    }

    .button {
      width: 100%;
    }

    .decoration {
      height: 42px;
      width: 14px;
    }
  }
</style>
