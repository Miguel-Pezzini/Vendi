<template>
  <v-card>
    <v-form ref="form" class="rounded" @submit.prevent="saveProduct()">
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
          item-value="id"
          chips
          required
          multiple />
      </v-row>
      <v-alert
        text="Select your main photo. It will appear as the product cover."
        type="info"></v-alert>
      <FileUpload
        v-model="product.mainPhoto"
        density="compact"
        title="Drop your main photo here"
        clearable
        showSize />
      <v-alert
        text="Here you can select up to 4 additional photos. These images will appear in your product gallery, but not as the main photo."
        type="info"></v-alert>
      <FileUpload
        v-model="product.photos"
        class="mb-6"
        clearable
        density="compact"
        title="Add product photos"
        showSize
        multiple />
      <v-row>
        <v-spacer />
        <Button title="CANCELAR" variant="text" to="/user/products" />
        <Button :title="edit ? 'SALVAR MUDANCAS' : 'ADICIONAR PRODUTO'" type="submit" bg-color="#DBB671" />
      </v-row>
    </v-form>
  </v-card>
</template>

<script lang="js" setup>
  import { getCurrentInstance, onMounted, reactive, ref, watch } from 'vue'

  import Button from '@/core/components/Button.vue'
  import FileUpload from '@/core/components/FileUpload.vue'
  import Input from '@/core/components/Input.vue'
  import NumberInput from '@/core/components/NumberInput.vue'
  import Select from '@/core/components/Select.vue'
  import api from '@/core/plugins/api'
  import router from '@/core/router'
  import imageService from '@/core/utils/imageService'

  const { proxy } = getCurrentInstance()

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

  const form = ref(null)
  const categories = ref([])

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

  onMounted(() => {
    loadCategories()
  })

  watch(
    () => props.productProp,
    (newProduct) => {
      if (newProduct) {
        Object.assign(product, {
          ...defaultProduct(),
          ...newProduct,
          categories: newProduct.categories?.map((category) => category.id) || [],
        })
        return
      }

      Object.assign(product, defaultProduct())
    },
    { immediate: true }
  )

  async function loadCategories() {
    categories.value = await api.getAll('category')
  }

  function normalizeSingleFile(fileValue) {
    if (Array.isArray(fileValue)) return fileValue[0] ?? null
    return fileValue ?? null
  }

  function normalizeFiles(fileValue) {
    if (!fileValue) return []
    return Array.isArray(fileValue) ? fileValue.filter(Boolean) : [fileValue]
  }

  function getErrorMessage(err) {
    if (typeof err === 'string') return err
    if (Array.isArray(err?.errors) && err.errors.length > 0) return err.errors[0]
    return err?.message || err?.error || err?.details || err?.title || 'Failed to save product'
  }

  function validateBeforeSave(mainPhotoFile, additionalPhotos) {
    if (!props.edit && !mainPhotoFile) {
      proxy.$showMessage('error', 'Select a main photo before saving the product')
      return false
    }

    if (!Array.isArray(product.categories) || product.categories.length === 0) {
      proxy.$showMessage('error', 'Select at least one category before saving the product')
      return false
    }

    if (additionalPhotos.length > 4) {
      proxy.$showMessage('error', 'You can upload up to 4 additional photos')
      return false
    }

    return true
  }

  async function buildPhotosPayload(mainPhotoFile, additionalPhotos) {
    const photos = []

    if (mainPhotoFile) {
      const mainPhotoData = await imageService.fileToDataBase64(mainPhotoFile)
      photos.push({
        isMainPhoto: true,
        filename: mainPhotoFile.name,
        contentType: mainPhotoFile.type,
        data: mainPhotoData,
      })
    }

    for (const photo of additionalPhotos) {
      const photoData = await imageService.fileToDataBase64(photo)
      photos.push({
        isMainPhoto: false,
        filename: photo.name,
        contentType: photo.type,
        data: photoData,
      })
    }

    return photos
  }

  async function saveProduct() {
    const validation = await form.value.validate()
    if (!validation.valid) return

    const mainPhotoFile = normalizeSingleFile(product.mainPhoto)
    const additionalPhotos = normalizeFiles(product.photos)

    if (!validateBeforeSave(mainPhotoFile, additionalPhotos)) return

    const payload = {
      name: product.name,
      price: product.price,
      quantity: product.quantity,
      installment: product.installment,
      discount: product.discount,
      categoriesIds: product.categories,
    }

    if (!product.id) {
      payload.photos = await buildPhotosPayload(mainPhotoFile, additionalPhotos)
    }

    try {
      const resource = product.id ? `products/${product.id}` : 'products'
      const method = product.id ? 'save' : 'create'
      await api[method](resource, payload)

      if (product.id) {
        proxy.$showMessage('success', 'Your product was updated with success')
        return
      }

      proxy.$showMessage('success', 'Your product was created with success')
      router.push({ path: '/user/products' })
    } catch (err) {
      proxy.$showMessage('error', getErrorMessage(err))
    }
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
