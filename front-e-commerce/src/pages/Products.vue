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
              <div class="d-flex align-center pb-3">
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

            <v-list-item>
              <v-autocomplete
                v-model="filters.brand"
                :items="brands"
                label="Marcas"
                variant="outlined"
              />
            </v-list-item>
            
            <v-list-item>
              <v-select
                v-model="filters.color"
                :items="colors"
                label="Cores"
                variant="outlined"
              />
            </v-list-item>
          </v-list>
        </v-sheet>

        <!-- Conteúdo Principal -->
        <v-col class="products-content">
          <!-- Aqui você pode adicionar os produtos -->
          <h2>Resultados</h2>
          <p>Adicione a lista de produtos aqui...</p>
        </v-col>
      </div>
    </v-container>
    <Footer />
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';

import { ref } from 'vue';

const filters = ref({
  brand: null,
  color: null,
});

const priceFilter = ref([0, 500])

const brands = ref(['Marca A', 'Marca B', 'Marca C']);
const colors = ref(['Vermelho', 'Azul', 'Verde']);

function mostrarPrecoMin() {
  return priceFilter.value[0].toFixed(0)
}
function mostrarPrecoMax() {
  return priceFilter.value[1].toFixed(0)
}
function searchByPrice() {
  console.log(priceFilter.value)
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
