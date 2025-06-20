<template>
  <v-card>
    <v-form class="rounded">
      <v-row>
        <h1 class="title">Add a Product</h1>
      </v-row>
      <v-row>
        <Input variant="outlined" label="Name" />
      </v-row>
      <v-row class="ga-4">
        <NumberInput
          v-model="price"
          prefix="$"
          variant="outlined"
          label="Price"
          :min="0"
          :precision="2" />
        <NumberInput
          v-model="quantity"
          variant="outlined"
          label="Quantity"
          :min="1"
          :precision="0" />
      </v-row>
      <v-row>
        <v-spacer />
        <Button title="CANCELAR" variant="text" />
        <Button title="SALVAR MUDANçAS" bg-color="#DBB671" />
      </v-row>
    </v-form>
  </v-card>
</template>

<script lang="js" setup>
  import Input from '@/core/components/Input.vue'

  import Button from '@/core/components/Button.vue'

  import NumberInput from '@/core/components/NumberInput.vue'

  import { ref } from 'vue'
  const price = ref(0.0)
  const quantity = ref(4.052)
</script>

<style scoped>
  .v-card {
    padding: 40px 80px 40px 80px;
    box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
  }
  .title {
    padding-bottom: 16px;
    color: #dbb671;
    font-size: 20px;
    font-weight: 500;
  }
</style>
