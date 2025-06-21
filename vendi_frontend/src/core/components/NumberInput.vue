<template>
  <v-number-input
    v-model="formattedValue"
    :label="localLabel"
    :variant="variant"
    :prepend-inner-icon="prependIcon"
    :rules="computedRules"
    :hide-details="hideDetails"
    :max-width="maxWidth"
    :density="density"
    :prefix="prefix"
    :required="required"
    :validate-on="validateOn"
    :min="min"
    :max="max"
    :precision="precision" />
</template>

<script setup>
  import { computed } from 'vue'

  const emit = defineEmits(['update:modelValue'])

  const formattedValue = computed({
    get: () => props.modelValue,
    set: (val) => emit('update:modelValue', val),
  })

  const localLabel = computed(() => {
    return props.required ? `${props.label}*` : props.label
  })

  const computedRules = computed(() => {
    const autoRules = []

    if (props.required) {
      autoRules.push((value) => !!value || 'Este campo é obrigatório.')
    }

    return [...autoRules, ...props.rules]
  })

  const props = defineProps({
    modelValue: {
      type: Number,
      default: null,
    },
    label: {
      type: String,
      default: null,
    },
    prependIcon: {
      type: String,
      default: null,
    },
    appendIcon: {
      type: String,
      default: null,
    },
    rules: {
      type: Array,
      default: () => [],
    },
    variant: {
      type: String,
      default: 'outlined',
    },
    hideDetails: {
      type: Boolean,
      default: false,
    },
    maxWidth: {
      type: Number,
      default: null,
    },
    density: {
      type: String,
      default: 'default',
    },
    required: {
      type: Boolean,
      default: false,
    },
    minLength: {
      type: Number,
      default: null,
    },
    validateOn: {
      type: String,
      default: undefined,
    },
    prefix: {
      type: String,
      default: null,
    },
    min: {
      type: Number,
      default: undefined,
    },
    max: {
      type: Number,
      default: undefined,
    },
    precision: {
      type: Number,
      default: 0,
    },
  })
</script>
