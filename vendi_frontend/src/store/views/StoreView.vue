<template>
  <div class="store-page">
    <Header />
    <v-divider />

    <div v-if="!$vuetify.display.mdAndUp" class="store-mobile-filter">
      <v-btn
        color="golden"
        block
        rounded="pill"
        prepend-icon="mdi-filter"
        @click="filterDialog = true">
        Filters
      </v-btn>
    </div>

    <v-container class="store-container" fluid>
      <div class="store-layout">
        <v-sheet class="store-sidebar" rounded="xl" elevation="0" v-if="$vuetify.display.mdAndUp">
          <FiltersContent
            v-model:priceFilter="priceFilter"
            :selectedFilters="selectedFilters"
            :filters="filters"
            :categories="categories"
            @search-by-price="searchByPrice"
            @select-category="getProductsByCategory" />
        </v-sheet>

        <section class="store-results">
          <div class="store-results__header">
            <div>
              <h2 class="store-results__title">Recommended for You</h2>
              <p class="store-results__subtitle">
                Produtos organizados com filtros e uma grade otimizada para qualquer tela.
              </p>
            </div>
            <h4 class="store-results__summary">Showing {{ products.length }} products</h4>
          </div>

          <v-alert v-if="errorMessage" type="error" class="mb-4" variant="tonal">
            {{ errorMessage }}
          </v-alert>

          <v-row v-if="loading" class="store-grid">
            <v-col v-for="n in 8" :key="n" cols="12" sm="6" lg="4" xl="3">
              <v-skeleton-loader type="card" />
            </v-col>
          </v-row>

          <v-row v-else class="store-grid">
            <v-col v-for="product in products" :key="product.id" cols="12" sm="6" lg="4" xl="3">
              <ResultsProduct
                :product="product"
                class="store-product-card"
                active-page="Store"
                @add-to-cart="addToCart"
                @add-to-wishlist="addToWishlist" />
            </v-col>
          </v-row>

          <p v-if="!loading && !products.length" class="store-results__subtitle">
            No products matched the current filters.
          </p>
        </section>
      </div>
    </v-container>

    <Footer />

    <v-dialog v-model="filterDialog" fullscreen>
      <v-card>
        <v-toolbar flat>
          <v-btn icon @click="filterDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>Filters</v-toolbar-title>
        </v-toolbar>

        <v-divider />

        <v-card-text class="pa-4">
          <FiltersContent
            v-model:priceFilter="priceFilter"
            v-model:selectedFilters="selectedFilters"
            :filters="filters"
            :categories="categories"
            @search-by-price="searchByPrice"
            @select-category="getProductsByCategory" />
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
  import { getCurrentInstance, onMounted, ref, watch } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import Header from '@/core/components/Header.vue'
  import Footer from '@/core/components/Footer.vue'
  import ResultsProduct from '@/core/components/ResultsProduct.vue'
  import FiltersContent from '../components/FiltersContent.vue'
  import productService from '@/core/utils/productService'
  import categoryService from '@/core/services/categoryService'
  import cartService from '@/core/services/cartService'

  const { proxy } = getCurrentInstance()
  const route = useRoute()
  const router = useRouter()

  const products = ref([])
  const categories = ref([])
  const loading = ref(false)
  const errorMessage = ref('')
  const filters = null
  const selectedFilters = ref({})
  const priceFilter = ref([0, 5000])
  const filterDialog = ref(false)

  function addToWishlist(props) {
    return props
  }

  async function addToCart(product) {
    try {
      await cartService.addItem(product.id, 1)
      proxy.$showMessage('success', 'Product added to cart.')
    } catch (error) {
      proxy.$showMessage('error', 'You need to be logged in to manage the cart.')
    }
  }

  async function loadProducts() {
    loading.value = true
    errorMessage.value = ''

    try {
      const params = {}

      if (route.query.search) {
        params.search = route.query.search.toString()
      }

      if (route.query.category) {
        params.categoryId = route.query.category.toString()
      }

      const loadedProducts = await productService.loadProducts('products', params)

      products.value = loadedProducts.filter(
        (product) => product.price >= priceFilter.value[0] && product.price <= priceFilter.value[1]
      )
    } catch (error) {
      errorMessage.value = 'Could not load products.'
    } finally {
      loading.value = false
    }
  }

  async function loadCategories() {
    categories.value = await categoryService.getCategories()
  }

  function searchByPrice() {
    loadProducts()
  }

  function getProductsByCategory(categoryId) {
    filterDialog.value = false
    router.push({ path: '/store', query: { ...route.query, category: categoryId } })
  }

  onMounted(async () => {
    try {
      await Promise.all([loadCategories(), loadProducts()])
    } catch (error) {
      proxy.$showMessage('error', 'Could not load the catalog.')
    }
  })

  watch(
    () => route.query,
    () => {
      loadProducts()
    }
  )
</script>

<style scoped>
  .store-page {
    display: flex;
    min-height: 100vh;
    flex-direction: column;
  }

  .store-mobile-filter {
    padding: 0.875rem 1rem 0;
  }

  .store-container {
    padding: 1.25rem 1rem 3.5rem;
  }

  .store-layout {
    display: grid;
    grid-template-columns: minmax(260px, 300px) minmax(0, 1fr);
    gap: 1.5rem;
    align-items: start;
    max-width: 1440px;
    margin: 0 auto;
  }

  .store-sidebar {
    position: sticky;
    top: 1.5rem;
    padding: 1rem;
    border: 1px solid rgba(15, 23, 42, 0.08);
    background: linear-gradient(180deg, #ffffff 0%, #f8fafc 100%);
    box-shadow:
      0 16px 36px rgba(15, 23, 42, 0.05),
      0 2px 8px rgba(15, 23, 42, 0.04);
  }

  .store-results {
    min-width: 0;
  }

  .store-results__header {
    display: flex;
    flex-wrap: wrap;
    align-items: end;
    justify-content: space-between;
    gap: 0.75rem 1rem;
    margin-bottom: 1.5rem;
  }

  .store-results__title {
    margin: 0;
    color: #111827;
    font-size: clamp(1.4rem, 2vw, 1.85rem);
    font-weight: 600;
  }

  .store-results__subtitle {
    margin: 0.45rem 0 0;
    color: rgba(17, 24, 39, 0.62);
    font-size: 0.95rem;
    line-height: 1.55;
  }

  .store-results__summary {
    margin: 0;
    color: rgba(17, 24, 39, 0.58);
    font-size: 0.95rem;
    font-weight: 500;
    text-align: right;
  }

  .store-grid {
    margin: 0 -0.5rem;
  }

  .store-product-card {
    height: 100%;
  }

  @media (max-width: 1279px) {
    .store-layout {
      grid-template-columns: minmax(250px, 280px) minmax(0, 1fr);
    }
  }

  @media (max-width: 959px) {
    .store-container {
      padding: 1rem 0.75rem 3rem;
    }

    .store-layout {
      display: block;
    }

    .store-results__header {
      margin-bottom: 1.25rem;
    }

    .store-results__summary {
      text-align: left;
    }
  }

  @media (max-width: 599px) {
    .store-mobile-filter {
      padding: 0.75rem 0.75rem 0;
    }

    .store-results__header {
      gap: 0.5rem;
    }
  }
</style>
