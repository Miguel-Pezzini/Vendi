<template>
  <v-btn
    :type="type"
    :block="block"
    :color="color"
    :loading="loading"
    :variant="variant"
    :style="{ backgroundColor: bgColor }"
    :icon="icon"
    :to="to"
    :flat="flat"
    :density="density">
    <template v-if="title">
      {{ title }}
    </template>
    <template v-if="icon">
      <v-icon>{{ icon }}</v-icon>
    </template>
  </v-btn>
</template>

<script setup>
  defineProps({
    to: {
      type: String,
      default: undefined,
    },
    title: {
      type: String,
      default: null,
    },
    type: {
      type: String,
      default: 'text',
    },
    block: {
      type: Boolean,
      default: false,
    },
    color: {
      type: String,
      default: 'black',
    },
    loading: {
      type: Boolean,
      default: false,
    },
    variant: {
      type: String,
      default: null,
    },
    bgColor: {
      type: String,
      default: 'white',
    },
    density: {
      type: String,
      default: 'default',
    },
    flat: {
      type: Boolean,
      default: false,
    },
    icon: {
      type: String,
      default: null,
    },
  })
</script>
