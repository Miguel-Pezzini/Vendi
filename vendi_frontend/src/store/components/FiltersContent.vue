<template>
  <v-list v-if="filters || categories">
    <!-- Título Filtros -->
    <v-list-item v-if="filters" title="Filtros de Produtos" />
    <v-divider class="mb-5" v-if="filters" />

    <!-- Filtros de Preço -->
    <v-list-item v-if="filters">
      <span>Preço:</span>
      <span> R${{ mostrarPrecoMin() }} - R${{ mostrarPrecoMax() }}</span>
      <div class="d-flex align-center pb-3 ga-2">
        <v-range-slider
          :model-value="priceFilter"
          @update:modelValue="updatePrice"
          min="0"
          hide-details
          max="500"
          class="mx-3"
          strict />
        <v-btn color="black" rounded="pill" @click="searchByPrice">IR</v-btn>
      </div>
    </v-list-item>

    <!-- Demais filtros -->
    <div v-for="filter in filters" :key="filter.name">
      <v-list-item>
        <v-list-item-title>{{ filter.name }}</v-list-item-title>
      </v-list-item>
      <v-list-item v-for="item in filter.items" :key="item" class="pa-0 ml-2">
        <v-checkbox
          v-model="selectedFilters[filter.name]"
          density="comfortable"
          hide-details
          :label="item"
          :value="item" />
      </v-list-item>
      <v-divider />
    </div>

    <!-- Categorias -->
    <v-list-item v-if="categories" title="Lista de Categorias" />
    <v-divider class="mb-5" v-if="categories" />
    <v-list-item v-if="categories">
      <v-list-item-title class="text-h5">
        {{ categories.name }}
      </v-list-item-title>
    </v-list-item>
    <v-list-item v-for="category in categories.childCategories" :key="category">
      <span class="ml-4" @click="$emit('select-category', category)">
        {{ category }}
      </span>
    </v-list-item>
  </v-list>
</template>

<script setup>
  const props = defineProps({
    filters: Array,
    categories: Object,
    priceFilter: Array,
    selectedFilters: Object,
  })

  const emit = defineEmits(['search-by-price', 'select-category, update:priceFilter'])

  function updatePrice(value) {
    emit('update:priceFilter', value)
  }

  function mostrarPrecoMin() {
    return props.priceFilter[0].toFixed(0)
  }
  function mostrarPrecoMax() {
    return props.priceFilter[1].toFixed(0)
  }
  function searchByPrice() {
    emit('search-by-price')
  }
</script>
