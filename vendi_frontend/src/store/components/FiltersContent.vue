<template>
  <v-list v-if="filters || categories.length">
    <v-list-item v-if="filters" title="Product Filters" />
    <v-divider class="mb-5" v-if="filters" />

    <v-list-item v-if="filters">
      <span>Price:</span>
      <span> R${{ mostrarPrecoMin() }} - R${{ mostrarPrecoMax() }}</span>
      <div class="d-flex align-center pb-3 ga-2">
        <v-range-slider
          :model-value="priceFilter"
          @update:modelValue="updatePrice"
          min="0"
          hide-details
          max="5000"
          class="mx-3"
          strict />
        <v-btn color="black" rounded="pill" @click="searchByPrice">Go</v-btn>
      </div>
    </v-list-item>

    <v-list-item v-if="categories.length" title="Categories" />
    <v-divider class="mb-5" v-if="categories.length" />

    <v-list-item v-for="category in categories" :key="category.id">
      <span class="ml-4" @click="$emit('select-category', category.id)">
        {{ category.name }}
      </span>
    </v-list-item>
  </v-list>
</template>

<script setup>
  const props = defineProps({
    filters: {
      type: Array,
      default: null,
    },
    categories: {
      type: Array,
      default: () => [],
    },
    priceFilter: {
      type: Array,
      default: () => [0, 5000],
    },
    selectedFilters: {
      type: Object,
      default: () => ({}),
    },
  })

  const emit = defineEmits(['search-by-price', 'select-category', 'update:priceFilter'])

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
