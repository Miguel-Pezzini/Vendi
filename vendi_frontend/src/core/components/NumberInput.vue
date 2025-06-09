<template>
  <v-text-field
    type="number"
    :label="localLabel"
    v-model="formattedValue"
    :variant="variant"
    :prepend-inner-icon="prependIcon"
    :rules="computedRules"
    :hide-details="hideDetails"
    :max-width="maxWidth"
    :density="density"
    @input="onInput()"
    :prefix="prefix"
    :required=" required"
    :validate-on="validateOn"
    @click:append-inner="show = !show"
  />
</template>
    
  <script setup>
  import { ref, computed } from 'vue';
  const show = ref(false)
  let formattedValue = ref();

  function onInput() {
    console.log(formattedValue.value)
    if(Number(formattedValue.value) > props.max) {
        formattedValue = props.max
    }
    if(Number(formattedValue.value)  < props.min) {
        formattedValue = props.min
    }
  }
  
  const localLabel = computed(() => {
    return props.required ? `${props.label}*` : props.label;
  });
  
  const computedRules = computed(() => {
    const autoRules = [];
  
    if (props.required) {
      autoRules.push((value) => !!value || "Este campo é obrigatório.");
    }
  
    return [...autoRules, ...props.rules];
  });
  
  const props = defineProps({
          value: {
            type: Number,
            default: null,
          },
          label: {
              type: String,
              default: null
          },
          prependIcon: {
              type: String,
              default: null
            },
            appendIcon: {
              type: String,
              default: null
            },
            rules: {
              type: Array,
              default: () => []
            },
            variant: {
              type: String,
              default: "underlined"
            },
            hideDetails: {
              type: Boolean,
              default: false
            },
            maxWidth: {
              type: Number,
              default: null,
            },
            density: {
              type: String,
              default: "default"
            },
            required: {
              type: Boolean,
              default: false
            },
            minLength: {
              type: Number,
              default: null
            },
            validateOn: {
              type: String,
              default: undefined
            },
            prefix: {
              type: String,
              default: null,
            },
            min: {
                type: Number,
                default: null
            },
            max: {
                type: Number,
                default: null,
            }
      })
  
  </script>
    
    <style scoped>
    
    </style>
    