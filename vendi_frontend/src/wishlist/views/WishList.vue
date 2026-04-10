<template>
  <div class="page-wrapper">
    <Header :wish-list-active="true" />
    <v-divider />

    <div class="wishlist-shell">
      <v-container class="wishlist-container">
        <section class="wishlist-section">
          <div class="section-header">
            <div>
              <h1 class="section-title">Lista de Desejos ({{ wishlistItems.length }})</h1>
              <p class="section-subtitle">
                Seus produtos salvos, organizados para comparar e comprar depois.
              </p>
            </div>
            <button
              class="button"
              :disabled="movingAllToCart || !wishlistItems.length"
              @click="moveAllToCart">
              {{ movingAllToCart ? 'Movendo...' : 'Mover todos para o carrinho' }}
            </button>
          </div>

          <v-alert v-if="errorMessage" type="error" variant="tonal">
            {{ errorMessage }}
          </v-alert>

          <v-row v-if="loading" class="cards-grid">
            <v-col v-for="n in 4" :key="`wishlist-loading-${n}`" cols="12" sm="6" lg="4" xl="3">
              <v-skeleton-loader type="card" />
            </v-col>
          </v-row>

          <v-row v-else-if="wishlistItems.length" class="cards-grid">
            <v-col
              v-for="item in wishlistItems"
              :key="`wishlist-${item.product.id}`"
              cols="12"
              sm="6"
              lg="4"
              xl="3">
              <ResultsProduct
                active-page="Wishlist"
                :product="item.product"
                @add-to-cart="addToCart"
                @add-to-wishlist="removeFromWishlist" />
            </v-col>
          </v-row>

          <v-sheet v-else class="empty-state" rounded="xl" border="sm" color="white">
            <h2 class="section-title">Sua wishlist está vazia</h2>
            <p class="section-subtitle">
              Salve produtos no catálogo para encontrá-los aqui depois.
            </p>
          </v-sheet>
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
            <button class="button" @click="goToStore">Ver Todos</button>
          </div>

          <v-row class="cards-grid">
            <v-col
              v-for="product in recommendedProducts"
              :key="`recommended-${product.id}`"
              cols="12"
              sm="6"
              lg="4"
              xl="3">
              <ResultsProduct
                active-page="WishlistRecommendations"
                :product="product"
                @add-to-cart="addToCart"
                @add-to-wishlist="toggleRecommendedWishlist" />
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
  import { computed, getCurrentInstance, onMounted, ref } from 'vue'
  import { useRouter } from 'vue-router'
  import productService from '@/core/utils/productService'
  import wishlistService from '@/core/services/wishlistService'
  import cartService from '@/core/services/cartService'

  const { proxy } = getCurrentInstance()
  const router = useRouter()

  const wishlist = ref({ items: [], totalItems: 0 })
  const recommendedProducts = ref([])
  const loading = ref(false)
  const errorMessage = ref('')
  const movingAllToCart = ref(false)

  const wishlistItems = computed(() => wishlist.value.items || [])

  async function loadWishlist() {
    loading.value = true
    errorMessage.value = ''

    try {
      wishlist.value = await wishlistService.getWishlist()
    } catch (error) {
      errorMessage.value = 'Nao foi possivel carregar sua wishlist.'
    } finally {
      loading.value = false
    }
  }

  async function loadRecommendations() {
    const products = await productService.loadProducts('products', { limit: 8 })
    const wishlistIds = new Set(wishlistItems.value.map((item) => item.product.id))

    recommendedProducts.value = products
      .filter((product) => !wishlistIds.has(product.id))
      .slice(0, 4)
      .map((product) => ({
        ...product,
        isInWishList: false,
      }))
  }

  async function removeFromWishlist(product) {
    try {
      wishlist.value = await wishlistService.removeItem(product.id)
      await loadRecommendations()
      proxy.$showMessage('success', 'Product removed from wishlist.')
    } catch (error) {
      proxy.$showMessage('error', 'Could not update the wishlist.')
    }
  }

  async function toggleRecommendedWishlist(product) {
    try {
      if (product.isInWishList) {
        wishlist.value = await wishlistService.removeItem(product.id)
      } else {
        wishlist.value = await wishlistService.addItem(product.id)
      }

      await loadRecommendations()
      proxy.$showMessage('success', product.isInWishList ? 'Product removed from wishlist.' : 'Product added to wishlist.')
    } catch (error) {
      proxy.$showMessage('error', 'Could not update the wishlist.')
    }
  }

  async function addToCart(product) {
    try {
      await cartService.addItem(product.id, 1)
      proxy.$showMessage('success', 'Product added to cart.')
    } catch (error) {
      proxy.$showMessage('error', 'Could not add the product to the cart.')
    }
  }

  async function moveAllToCart() {
    movingAllToCart.value = true

    try {
      const productsToMove = wishlistItems.value.map((item) => item.product)

      for (const product of productsToMove) {
        await cartService.addItem(product.id, 1)
        wishlist.value = await wishlistService.removeItem(product.id)
      }

      await loadRecommendations()
      proxy.$showMessage('success', 'Wishlist moved to cart.')
    } catch (error) {
      proxy.$showMessage('error', 'Could not move the wishlist to the cart.')
    } finally {
      movingAllToCart.value = false
    }
  }

  function goToStore() {
    router.push('/store')
  }

  onMounted(async () => {
    await loadWishlist()
    await loadRecommendations()
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

  .button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }

  .decoration {
    height: 48px;
    width: 16px;
    background-color: #dbb671;
    border-radius: 999px;
  }

  .empty-state {
    padding: 2rem;
    border-color: rgba(15, 23, 42, 0.08);
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
