<template>
  <v-card>
    <v-form @submit.prevent="saveProduct()" ref="form" class="rounded">
      <v-row>
        <h1 class="title">{{ edit ? 'Edit this product' : 'Add a Product' }}</h1>
      </v-row>
      <v-row>
        <Input v-model="product.name" class="mb-2" required label="Name" />
      </v-row>
      <v-row class="ga-4 my-2">
        <NumberInput
          v-model="product.price"
          prefix="$"
          label="Price"
          required
          :min="0"
          :precision="2" />
        <NumberInput v-model="product.quantity" label="Quantity" :min="1" required :precision="0" />
      </v-row>
      <v-row class="ga-4 my-2">
        <NumberInput
          v-model="product.installment"
          required
          label="Installment"
          :min="0"
          :precision="0" />
        <NumberInput
          v-model="product.discount"
          prefix="%"
          label="Discount"
          :min="0"
          required
          :max="100"
          :precision="0" />
      </v-row>
      <v-row class="my-2">
        <Select
          v-model="product.categories"
          :items="categories"
          label="Categories"
          item-title="name"
          item-value="category_id"
          chips
          required
          multiple />
      </v-row>
      <v-row class="my-2">
        <FileInput
          v-model="product.mainPhoto"
          prepend-inner-icon="mdi-camera"
          :prepend-icon="null"
          accept="image/png, image/jpeg, image/bmp"
          label="Main photo"
          showSize
          required />
      </v-row>
      <v-row class="mt-2">
        <FileInput
          v-model="product.photos"
          prepend-inner-icon="mdi-camera"
          :prepend-icon="null"
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
        <Button title="SALVAR MUDANçAS" type="submit" bg-color="#DBB671" />
      </v-row>
    </v-form>
  </v-card>
</template>

<script lang="js" setup>
  import Input from '@/core/components/Input.vue'
  import FileInput from '@/core/components/FileInput.vue'
  import NumberInput from '@/core/components/NumberInput.vue'
  import Select from '@/core/components/Select.vue'
  import Button from '@/core/components/Button.vue'

  import imageService from '@/core/utils/imageService'

  import { reactive, onMounted, ref, getCurrentInstance, watch } from 'vue'
  import api from '@/core/plugins/api'
  import router from '@/core/router'

  const { proxy } = getCurrentInstance()
  const form = ref(null)
  const categories = ref([])

  const props = defineProps({
    productProp: {
      type: Object,
      default: null,
    },
    edit: {
      type: Boolean,
      default: false,
    },
  })

  const defaultProduct = () => ({
    name: null,
    price: 0.0,
    quantity: 1,
    installment: 0,
    discount: 0,
    categories: [],
    mainPhoto: null,
    photos: [],
  })

  const product = reactive(defaultProduct())

  watch(
    () => props.productProp,
    (newProduct) => {
      if (newProduct) {
        Object.assign(product, newProduct)
      }
    },
    { immediate: true }
  )

  onMounted(() => {
    loadCategories()
  })

  async function loadCategories() {
    categories.value = await api.getAll('/category')
  }
  async function saveProduct() {
    const isValid = await form.value.validate()
    if (!isValid.valid) return

    const mainPhotoData = await imageService.fileToBase64(product.mainPhoto)
    const mainPhoto = {
      isMainPhoto: true,
      filename: product.mainPhoto.name,
      contentType: product.mainPhoto.type,
      data: mainPhotoData,
    }

    let photos = [mainPhoto]
    for (const photo of product.photos) {
      const photoData = await imageService.fileToBase64(photo)
      photos.push({
        isMainPhoto: false,
        filename: photo.name,
        contentType: photo.type,
        data: photoData,
      })
    }
    const method = product.id ? 'save' : 'create'
    await api[method]('/product', {
      name: product.name,
      price: product.price,
      quantity: product.quantity,
      installment: product.installment,
      discount: product.discount,
      photos,
      categoriesIds: product.categories,
    })
      .then((res) => {
        if (product.id) {
          proxy.$showMessage('success', 'Your product was updated with success')
          product = res
        } else {
          proxy.$showMessage('success', 'Your product was created with success')
          router.push({ path: '/user/products' })
        }
      })
      .catch((err) => {
        proxy.$showMessage('error', err)
      })
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
