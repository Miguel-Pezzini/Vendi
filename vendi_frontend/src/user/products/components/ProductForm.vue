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
          v-model="product.price"
          prefix="$"
          variant="outlined"
          label="Price"
          required
          :min="0"
          :precision="2" />
        <NumberInput
          v-model="product.quantity"
          variant="outlined"
          label="Quantity"
          :min="1"
          required
          :precision="0" />
      </v-row>
      <v-row class="ga-4">
        <NumberInput
          v-model="product.installment"
          variant="outlined"
          required
          label="Installment"
          :min="0"
          :precision="0" />
        <NumberInput
          v-model="product.discount"
          prefix="%"
          variant="outlined"
          label="Discount"
          :min="0"
          required
          :max="100"
          :precision="0" />
      </v-row>
      <v-row>
        <v-select
          v-model="product.categories"
          variant="outlined"
          :items="categories"
          label="Categories"
          item-title="name"
          item-value="category_id"
          chips
          required
          multiple></v-select>
      </v-row>
      <v-row>
        <FileInput
          v-model="product.mainPhoto"
          prepend-inner-icon="mdi-camera"
          :prepend-icon="null"
          accept="image/png, image/jpeg, image/bmp"
          variant="outlined"
          label="Main photo"
          showSize
          required />
      </v-row>
      <v-row>
        <FileInput
          v-model="product.photos"
          prepend-inner-icon="mdi-camera"
          :prepend-icon="null"
          variant="outlined"
          accept="image/png, image/jpeg, image/bmp"
          label="Photos"
          multiple
          counter
          showSize
          :max="4"
          required />
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
  import FileInput from '@/core/components/FileInput.vue'
  import NumberInput from '@/core/components/NumberInput.vue'

  import Button from '@/core/components/Button.vue'

  import { reactive, onMounted, ref } from 'vue'
  import api from '@/core/plugins/api'
  const product = reactive({
    price: 0.0,
    quantity: 4.052,
    installment: 0,
    discount: 0,
    categories: [],
    mainPhoto: null,
    photos: [],
  })
  const categories = ref([])

  onMounted(() => {
    loadCategories()
  })

  async function loadCategories() {
    categories.value = await api.getAll('/category')
  }
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
