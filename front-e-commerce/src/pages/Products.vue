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
          <v-list>
            <v-list-item title="Filtros de Produtos"/>
            <v-divider class="mb-5"  />

            <!-- Filtros -->
            <v-list-item>
              <span>Preço:</span>
              <span> R${{ mostrarPrecoMin() }} - R${{ mostrarPrecoMax() }}</span>
              <div class="d-flex align-center pb-3 ga-2">
                <!-- Range Slider -->
                <v-range-slider
                  min="0"
                  max="500"
                  class="mx-3"
                  hide-details="false"
                  v-model="priceFilter"
                  strict
                  />
                <!-- Botão "IR" -->
                 
                  <v-btn @click="searchByPrice" color="black" rounded="pill">IR</v-btn>
              </div>
            </v-list-item>

            <div v-for="filter in filters" :key="filter.name">
              <v-list-item>
                <v-list-item-title>{{ filter.name }}</v-list-item-title>
              </v-list-item>
              <!-- Itera pelos itens do filtro -->
              <v-list-item  class="pa-0" v-for="item in filter.items" :key="item">
                <v-checkbox
                  hide-details="true"
                  v-model="selectedFilters[filter.name]"
                  :label="item"
                  :value="item"
                />
              </v-list-item>
              <v-divider />
            </div>

          </v-list>
        </v-sheet>

        <!-- Conteúdo Principal -->
        <v-col class="products-content">
          <!-- Aqui você pode adicionar os produtos -->
          <h2>Resultados</h2>
        </v-col>
      </div>
    </v-container>
    <Footer />
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { useRoute } from 'vue-router'

import { ref } from 'vue';

const route = useRoute()

const filters = [
  {
    name: "CPU",
    items: ["Intel i5", "Intel i7", "Ryzen 5"]
  },
  {
    name: "RAM",
    items: ["8GB", "16GB", "32GB"]
  },
  {
    name: "Gênero",
    items: ["Ficção", "Biografia", "Terror"]
  }
]

const selectedFilters = ref({
  CPU: [],
  RAM: [],
  GENERO: []
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


</script>

<style scoped>
/* Layout base */
body {
  font-family: "Poppins", serif;
}

.page-wrapper {
  display: flex;
  flex-direction: column;
}

.filter-sheet {
  width: 350px;
  padding: 15px;
  flex-shrink: 0; /* Garante que ele não diminua */
}

/* Estilo do conteúdo de produtos */
.products-content {
  flex-grow: 1; /* Faz o conteúdo principal ocupar o espaço restante */
  padding: 20px;
}
</style>
