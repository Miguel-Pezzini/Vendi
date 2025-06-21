<template>
  <v-select
    v-model="formattedValue"
    :label="localLabel"
    :counter="counter"
    :variant="variant"
    :prepend-inner-icon="prependInnerIcon"
    :prepend-icon="prependIcon"
    :rules="computedRules"
    :hide-details="hideDetails"
    :item-value="itemValue"
    :item-title="itemTitle"
    :max-width="maxWidth"
    :items="items"
    :density="density"
    :chips="chips"
    :required="required"
    :show-size="showSize"
    :multiple="multiple"
    :validate-on="validateOn" />
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
      autoRules.push((value) => {
        if (props.multiple) {
          return (Array.isArray(value) && value.length > 0) || 'This field is required.'
        }
        return !!value || 'This field is required.'
      })
    }

    return [...autoRules, ...props.rules]
  })

  const props = defineProps({
    appendIcon: { type: String, default: null },
    chips: { type: Boolean, default: false },
    counter: { type: Boolean, default: false },
    density: { type: String, default: 'default' },
    hideDetails: { type: Boolean, default: false },
    items: { type: Array, default: [] },
    itemTitle: { type: String, default: 'title' },
    itemValue: { type: String, default: 'value' },
    label: { type: String, default: null },
    max: { type: Number, default: 1 },
    maxWidth: { type: Number, default: null },
    modelValue: { type: [File, Array], default: null },
    multiple: { type: Boolean, default: false },
    prependIcon: { type: String, default: null },
    prependInnerIcon: { type: String, default: null },
    required: { type: Boolean, default: false },
    rules: { type: Array, default: () => [] },
    showSize: { type: Boolean, default: false },
    validateOn: { type: String, default: undefined },
    variant: { type: String, default: 'outlined' },
  })
</script>
