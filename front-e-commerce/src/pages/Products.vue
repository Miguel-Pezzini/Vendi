<template>
  <div class="page-wrapper">
    <Header />
   <v-divider />
    <v-container class="pa-0" fluid>
      <div class="d-flex">
        <!-- Filtros -->
        <v-sheet class="filter-sheet" elevation="4">
          <v-list>
            <v-list-item title="Filtros de Produtos"></v-list-item>
            <v-divider></v-divider>

            <!-- Filtros -->
            <v-list-item>
              <v-checkbox label="Preço baixo a alto" v-model="filters.priceAsc" />
            </v-list-item>
            <v-list-item>
              <v-checkbox label="Preço alto a baixo" v-model="filters.priceDesc" />
            </v-list-item>
            <v-list-item>
              <v-select :items="brands" label="Marcas" v-model="filters.brand" />
            </v-list-item>
            <v-list-item>
              <v-select :items="colors" label="Cores" v-model="filters.color" />
            </v-list-item>
          </v-list>
        </v-sheet>

        <!-- Conteúdo Principal -->
        <v-col class="products-content">
          <!-- Aqui você pode adicionar os produtos -->
          <h2>Produtos</h2>
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
  priceAsc: false,
  priceDesc: false,
  brand: null,
  color: null,
});

const brands = ref(['Marca A', 'Marca B', 'Marca C']);
const colors = ref(['Vermelho', 'Azul', 'Verde']);
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
