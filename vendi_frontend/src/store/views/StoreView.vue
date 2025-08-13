<template>
  <div class="d-flex flex-column">
    <Header />
    <v-divider />

    <!-- Aba Filters no mobile -->
    <div v-if="!$vuetify.display.mdAndUp" class="pa-3">
      <v-btn
        color="golden"
        block
        rounded="lg"
        prepend-icon="mdi-filter"
        @click="filterDialog = true">
        Filters
      </v-btn>
    </div>

    <v-container class="pa-0" fluid>
      <div class="d-flex">
        <!-- Filtros no desktop -->
        <v-sheet class="pa-4" elevation="4" width="300" v-if="$vuetify.display.mdAndUp">
          <FiltersContent
            v-model:priceFilter="priceFilter"
            :selectedFilters="selectedFilters"
            :filters="filters"
            :categories="categories"
            @search-by-price="searchByPrice"
            @get-products-by-category="getProductsByCategory" />
        </v-sheet>

        <!-- Conteúdo Principal -->
        <v-col class="pa-4">
          <h2>Resultados</h2>
          <v-row class="ml-6 mb-12" justify="center">
            <v-col v-for="n in 8" :key="n">
              <ResultsProduct
                :product="prod1"
                class="pa-3"
                active-page="Store"
                @add-to-cart="addToCart"
                @toggle-wish-list="toggleWishList" />
            </v-col>
          </v-row>
        </v-col>
      </div>
    </v-container>

    <Footer />

    <!-- Modal de filtros no mobile -->
    <v-dialog v-model="filterDialog" fullscreen>
      <v-card>
        <v-toolbar flat>
          <v-btn icon @click="filterDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>Filtros</v-toolbar-title>
        </v-toolbar>

        <v-divider />

        <v-card-text class="pa-4">
          <FiltersContent
            v-model:priceFilter="priceFilter"
            v-model:selectedFilters="selectedFilters"
            :filters="filters"
            :categories="categories"
            @search-by-price="searchByPrice"
            @get-products-by-category="getProductsByCategory" />
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
  import { ref } from 'vue'
  import card1 from '@/assets/card1.webp'
  import Header from '@/core/components/Header.vue'
  import Footer from '@/core/components/Footer.vue'
  import ResultsProduct from '@/core/components/ResultsProduct.vue'
  import FiltersContent from '../components/FiltersContent.vue'
  const prod1 = ref({
    discount: 35,
    name: 'Laptop',
    price: '960',
    fullPrice: '1160',
    isInWishList: false,
    image: card1,
  })

  function toggleWishList(props) {
    prod1.value.isInWishList = props
  }

  function addToCart(props) {
    console.log(props)
  }

  const filters = [
    {
      name: 'CPU',
      items: ['Intel i5', 'Intel i7', 'Ryzen 5'],
    },
    {
      name: 'RAM',
      items: ['8GB', '16GB', '32GB'],
    },
    {
      name: 'Gênero',
      items: ['Ficção', 'Biografia', 'Terror'],
    },
  ]

  const categories = {
    name: 'Eletronics',
    childCategories: ['Computer', 'Cellphone', 'Headphone'],
  }

  const selectedFilters = ref({
    CPU: [],
    RAM: [],
    Gênero: [],
  })

  const priceFilter = ref([0, 500])
  const filterDialog = ref(false)

  function searchByPrice() {
    console.log(priceFilter.value, selectedFilters.value)
  }
  function getProductsByCategory(category) {
    console.log(category)
  }
</script>

<style scoped>
  .max-w-380 {
    max-width: 380px;
  }
  span {
    color: black;
    font-weight: 300;
  }
  span:hover {
    font-weight: 500;
    cursor: pointer;
  }
</style>
