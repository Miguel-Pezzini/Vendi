<template>
  <div class="page-wrapper">
    <Header />
    <v-divider />
    <v-container
      class="pa-0"
      fluid
    >
      <div class="d-flex">
        <!-- Filtros -->
        <v-sheet
          class="filter-sheet"
          elevation="4"
        >
          <v-list v-if="filters">
            <v-list-item title="Filtros de Produtos" />
            <v-divider class="mb-5" />

            <!-- Filtros -->
            <v-list-item>
              <span>Preço:</span>
              <span> R${{ mostrarPrecoMin() }} - R${{ mostrarPrecoMax() }}</span>
              <div class="d-flex align-center pb-3 ga-2">
                <!-- Range Slider -->
                <v-range-slider
                  v-model="priceFilter"
                  min="0"
                  max="500"
                  class="mx-3"
                  hide-details="false"
                  strict
                />
                <!-- Botão "IR" -->
                 
                <v-btn
                  color="black"
                  rounded="pill"
                  @click="searchByPrice"
                >
                  IR
                </v-btn>
              </div>
            </v-list-item>

            <div
              v-for="filter in filters"
              :key="filter.name"
            >
              <v-list-item>
                <v-list-item-title>{{ filter.name }}</v-list-item-title>
              </v-list-item>
              <!-- Itera pelos itens do filtro -->
              <v-list-item
                v-for="item in filter.items"
                :key="item"
                class="pa-0 ml-2"
              >
                <v-checkbox
                  v-model="selectedFilters[filter.name]"
                  density="comfortable"
                  hide-details="true"
                  :label="item"
                  :value="item"
                />
              </v-list-item>
              <v-divider />
            </div>
          </v-list>

          <v-list v-if="categories">
            <v-list-item title="Lista de Categorias" />
            <v-divider class="mb-5" />
            <v-list-item>
              <v-list-item-title class="text-h5">
                {{ categories.name }}
              </v-list-item-title>
            </v-list-item>
            <v-list-item
              v-for="category in categories.childCategories"
              :key="category"
            >
              <span
                class="ml-4"
                @click="getProductsByCategory(category)"
              >{{ category }}</span>
            </v-list-item>
          </v-list>
        </v-sheet>

        <!-- Conteúdo Principal -->
        <v-col class="products-content">
          <!-- Aqui você pode adicionar os produtos -->
          <h2 class="mb-6">
            Resultados
          </h2>
          <v-row
            no-gutters
            class="ml-6 mb-16"
          >
            <v-col
              v-for="n in 8"
              :key="n"
              cols="3"
            >
              <ResultsProduct
                :product="prod1"
                class="pa-3"
                @ver-product="verProduct"
                @add-to-cart="addToCart"
                @toggle-wish-list="toggleWishList"
              />
            </v-col>
          </v-row>
        </v-col>
      </div>
    </v-container>
    <Footer />
  </div>
</template>

<script setup>
import Header from '@/core/Header.vue';
import Footer from '@/core/Footer.vue';
import ResultsProduct from '@/search/ResultsProduct.vue'
import router from '@/router';

import { ref } from 'vue';

const prod1 = ref({
  discount: 35,
  name: "Laptop",
  price: "960",
  fullPrice: "1160",
  isInWishList: false,
})

function toggleWishList(props) {
  prod1.value.isInWishList = props;
}

function addToCart(props) {
  console.log(props)
}

// const filters = [
//   {
//     name: "CPU",
//     items: ["Intel i5", "Intel i7", "Ryzen 5"]
//   },
//   {
//     name: "RAM",
//     items: ["8GB", "16GB", "32GB"]
//   },
//   {
//     name: "Gênero",
//     items: ["Ficção", "Biografia", "Terror"]
//   }
// ]

const categories = {
  name: "Eletronics",
  childCategories: ["Computer", "Cellphone", "Headphone"]
}

const selectedFilters = ref({
  CPU: [],
  RAM: [],
  Gênero: []
});

const priceFilter = ref([0, 500])

function mostrarPrecoMin() {
  return priceFilter.value[0].toFixed(0)
}
function mostrarPrecoMax() {
  return priceFilter.value[1].toFixed(0)
}
function searchByPrice() {
  console.log(priceFilter.value)
  console.log(selectedFilters.value)
}
function getProductsByCategory(category) {
  console.log(category)
}
function verProduct(productName) {
  router.push({ path: "/product", query: { product: productName, origin: ["Home","Store"]}})

}


</script>

<style scoped>
/* Layout base */
body {
  font-family: "Poppins", serif;
}
span {
  color: black;
  font-weight: 300;
}
span:hover {
  font-weight: 500;
  cursor: pointer;
}

.page-wrapper {
  display: flex;
  flex-direction: column;
}

.filter-sheet {
  width: 300px;
  padding: 15px;
  flex-shrink: 0; /* Garante que ele não diminua */
}

/* Estilo do conteúdo de produtos */
.products-content {
  flex-grow: 1; /* Faz o conteúdo principal ocupar o espaço restante */
  padding: 20px;
}
</style>
