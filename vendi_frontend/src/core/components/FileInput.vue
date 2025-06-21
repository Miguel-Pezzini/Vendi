<template>
  <v-file-input
    v-model="formattedValue"
    :label="localLabel"
    :counter="counter"
    :variant="variant"
    :prepend-inner-icon="prependInnerIcon"
    :prepend-icon="prependIcon"
    :rules="computedRules"
    :hide-details="hideDetails"
    :max-width="maxWidth"
    :density="density"
    :accept="accept"
    :prefix="prefix"
    :required="required"
    :show-size="showSize"
    :multiple="multiple"
    :validate-on="validateOn">
    <template v-slot:selection="{ fileNames }">
      <template v-for="(fileName, index) in fileNames" :key="fileName">
        <v-chip v-if="index < 2" class="me-2" size="small" label>
          {{ fileName }}
        </v-chip>

        <span v-else-if="index === 2" class="text-overline text-grey-darken-3 mx-2">
          +{{ fileNames.length - 2 }} File(s)
        </span>
      </template>
    </template>
  </v-file-input>
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
    const maxSize = 5000000

    if (props.required) {
      autoRules.push((value) => !!value || 'This field is required.')
    }
    if (props.multiple) {
      autoRules.push((value) => {
        if (value && Array.isArray(value)) {
          const totalSize = value.reduce((acc, current) => acc + current.size, 0)
          return totalSize < maxSize || 'Total image size should be less than 5 MB!'
        }
      })
      autoRules.push((value) => {
        if (!value || value.length <= props.max) return true
        return `You can only upload up to ${props.max} images`
      })
    } else {
      autoRules.push(
        (value) => !value || value.size < maxSize || 'Total image size should be less than 5 MB!'
      )
    }

    return [...autoRules, ...props.rules]
  })

  const props = defineProps({
    accept: { type: String, default: null },
    appendIcon: { type: String, default: null },
    counter: { type: Boolean, default: false },
    density: { type: String, default: 'default' },
    hideDetails: { type: Boolean, default: false },
    label: { type: String, default: null },
    max: { type: Number, default: 1 },
    maxWidth: { type: Number, default: null },
    modelValue: { type: [File, Array], default: null },
    multiple: { type: Boolean, default: false },
    prefix: { type: String, default: null },
    prependIcon: { type: String, default: null },
    prependInnerIcon: { type: String, default: null },
    required: { type: Boolean, default: false },
    rules: { type: Array, default: () => [] },
    showSize: { type: Boolean, default: false },
    validateOn: { type: String, default: undefined },
    variant: { type: String, default: 'underlined' },
  })
</script>
