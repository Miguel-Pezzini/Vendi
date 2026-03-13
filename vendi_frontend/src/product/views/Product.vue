<template>
  <div class="product-page">
    <Header />
    <v-divider />

    <v-container class="product-page__container" fluid>
      <Path class="product-page__path" :old-paths="oldPaths" :active-path="product?.name || 'Product'" />

      <section v-if="loading" class="product-page__state">
        <v-row dense>
          <v-col cols="12" md="7">
            <v-skeleton-loader type="image" class="product-page__skeleton" />
          </v-col>
          <v-col cols="12" md="5">
            <v-skeleton-loader type="article, actions" class="product-page__skeleton" />
          </v-col>
        </v-row>
      </section>

      <section v-else-if="product" class="product-page__content">
        <div class="product-page__layout">
          <section class="product-gallery">
            <div class="product-gallery__media">
              <div class="product-gallery__thumbs" :class="{ 'product-gallery__thumbs--horizontal': smAndDown }">
                <button
                  v-for="image in listImages"
                  :key="image.img"
                  type="button"
                  class="product-gallery__thumb"
                  :class="{ 'product-gallery__thumb--active': image.active }"
                  :aria-label="`Preview image of ${product.name}`"
                  @click="setActiveImg(image.img)">
                  <v-img aspect-ratio="1" cover :src="image.img" />
                </button>
              </div>

              <v-sheet class="product-gallery__main" rounded="xl" elevation="0">
                <v-img class="product-gallery__main-image" :src="activeImage" :alt="product.name" cover />
              </v-sheet>
            </div>
          </section>

          <section class="product-summary">
            <v-sheet class="product-summary__card" rounded="xl" elevation="0">
              <div class="product-summary__header">
                <div>
                  <p class="product-summary__eyebrow">Detalhes do produto</p>
                  <h1 class="product-summary__title">{{ product.name }}</h1>
                </div>
                <span
                  class="product-summary__stock"
                  :class="product.quantity > 0 ? 'product-summary__stock--available' : 'product-summary__stock--unavailable'">
                  {{ product.quantity > 0 ? 'Em estoque' : 'Sem estoque' }}
                </span>
              </div>

              <div class="product-summary__pricing">
                <h2 class="product-summary__price">R$ {{ product.price }}</h2>
                <p v-if="product.discount" class="product-summary__discount">
                  {{ product.discount }}% de desconto
                </p>
              </div>

              <div class="product-summary__details">
                <div class="product-summary__detail">
                  <span class="product-summary__label">Parcelamento</span>
                  <strong>{{ product.installment || 'Consulte opções de pagamento' }}</strong>
                </div>
                <div class="product-summary__detail">
                  <span class="product-summary__label">Categorias</span>
                  <strong>{{ formattedCategories }}</strong>
                </div>
                <div class="product-summary__detail">
                  <span class="product-summary__label">Disponibilidade</span>
                  <strong>{{ product.quantity }} unidade(s)</strong>
                </div>
              </div>

              <v-divider class="product-summary__divider" />

              <div class="product-summary__purchase">
                <div class="product-summary__field">
                  <label class="product-summary__label" for="product-quantity">Quantidade</label>
                  <v-number-input
                    id="product-quantity"
                    v-model="quantity"
                    density="comfortable"
                    variant="solo"
                    hide-details
                    control-variant="split"
                    inset
                    :min="1"
                    :max="Math.max(product.quantity, 1)"
                    :disabled="product.quantity <= 0" />
                </div>

                <v-btn
                  class="product-summary__add-button"
                  color="golden"
                  size="large"
                  rounded="lg"
                  block
                  :disabled="product.quantity <= 0"
                  @click="addToCart">
                  Adicionar ao carrinho
                </v-btn>
              </div>
            </v-sheet>
          </section>
        </div>
      </section>

      <v-alert v-else type="error" variant="tonal" class="product-page__state">
        Product not found.
      </v-alert>
    </v-container>

    <Footer />
  </div>
</template>

<script setup>
  import { computed, getCurrentInstance, onMounted, ref, watch } from 'vue'
  import { useRoute } from 'vue-router'
  import { useDisplay } from 'vuetify'
  import loadPastPaths from '@/core/utils/loadPastPaths'
  import Header from '@/core/components//Header.vue'
  import Path from '@/core/components/Path.vue'
  import Footer from '@/core/components/Footer.vue'
  import productService from '@/core/utils/productService'
  import cartService from '@/core/services/cartService'

  const { proxy } = getCurrentInstance()
  const route = useRoute()
  const { smAndDown } = useDisplay()

  const oldPaths = ref([])
  const loading = ref(false)
  const product = ref(null)
  const listImages = ref([])
  const activeImage = ref(null)
  const quantity = ref(1)

  const formattedCategories = computed(() =>
    product.value?.categories?.map((category) => category.name).join(', ') || 'Nenhuma'
  )

  onMounted(async () => {
    oldPaths.value = loadPastPaths(route)
    await loadProduct()
  })

  watch(
    () => route.params.productId,
    async () => {
      oldPaths.value = loadPastPaths(route)
      await loadProduct()
    }
  )

  async function loadProduct() {
    loading.value = true
    quantity.value = 1

    try {
      product.value = await productService.loadProductDetails(route.params.productId)
      listImages.value = [product.value.mainPhoto, ...(product.value.photos || [])]
        .filter(Boolean)
        .map((photo, index) => ({
          img: photo.dataURI,
          active: index === 0,
        }))
      activeImage.value = listImages.value[0]?.img || null
    } catch (error) {
      product.value = null
      listImages.value = []
      activeImage.value = null
    } finally {
      loading.value = false
    }
  }

  function setActiveImg(image) {
    listImages.value = listImages.value.map((img) => ({
      ...img,
      active: img.img === image,
    }))
    activeImage.value = image
  }

  async function addToCart() {
    if (!product.value || product.value.quantity <= 0) return

    try {
      await cartService.addItem(product.value.id, quantity.value)
      proxy.$showMessage('success', 'Product added to cart.')
    } catch (error) {
      proxy.$showMessage('error', 'You need to be logged in to manage the cart.')
    }
  }
</script>

<style scoped>
  .product-page {
    display: flex;
    min-height: 100vh;
    flex-direction: column;
  }

  .product-page__container {
    width: min(100%, 1280px);
    padding: 1rem 1rem 3.5rem;
    margin: 0 auto;
  }

  .product-page__path {
    margin-bottom: 1.5rem;
  }

  .product-page__state {
    margin-top: 1rem;
  }

  .product-page__skeleton {
    border-radius: 1.5rem;
  }

  .product-page__layout {
    display: grid;
    grid-template-columns: minmax(0, 1.1fr) minmax(320px, 0.9fr);
    gap: 2rem;
    align-items: start;
  }

  .product-gallery__media {
    display: grid;
    grid-template-columns: 96px minmax(0, 1fr);
    gap: 1rem;
    align-items: start;
  }

  .product-gallery__thumbs {
    display: flex;
    flex-direction: column;
    gap: 0.75rem;
  }

  .product-gallery__thumb {
    width: 96px;
    height: 96px;
    padding: 0.35rem;
    overflow: hidden;
    border: 1px solid rgba(15, 23, 42, 0.12);
    border-radius: 1rem;
    background: #fff;
    cursor: pointer;
    transition:
      border-color 0.2s ease,
      box-shadow 0.2s ease,
      transform 0.2s ease;
  }

  .product-gallery__thumb:hover,
  .product-gallery__thumb:focus-visible {
    border-color: #dbb671;
    box-shadow: 0 0 0 3px rgba(219, 182, 113, 0.22);
    transform: translateY(-1px);
    outline: none;
  }

  .product-gallery__thumb--active {
    border-color: #dbb671;
    box-shadow: 0 0 0 3px rgba(219, 182, 113, 0.18);
  }

  .product-gallery__main {
    min-width: 0;
    overflow: hidden;
    border: 1px solid rgba(15, 23, 42, 0.08);
    background:
      radial-gradient(circle at top, rgba(219, 182, 113, 0.12), transparent 42%),
      #fff;
    box-shadow:
      0 18px 44px rgba(15, 23, 42, 0.08),
      0 4px 14px rgba(15, 23, 42, 0.06);
  }

  .product-gallery__main-image {
    width: 100%;
    aspect-ratio: 1 / 1;
  }

  .product-summary__card {
    padding: 1.5rem;
    border: 1px solid rgba(15, 23, 42, 0.08);
    background: linear-gradient(180deg, #ffffff 0%, #fcfaf5 100%);
    box-shadow:
      0 18px 44px rgba(15, 23, 42, 0.08),
      0 4px 14px rgba(15, 23, 42, 0.05);
  }

  .product-summary__header {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 0.75rem 1rem;
  }

  .product-summary__eyebrow {
    margin: 0 0 0.45rem;
    color: rgba(17, 24, 39, 0.56);
    font-size: 0.83rem;
    font-weight: 600;
    letter-spacing: 0.08em;
    text-transform: uppercase;
  }

  .product-summary__title {
    margin: 0;
    color: #111827;
    font-size: clamp(1.75rem, 3vw, 2.45rem);
    line-height: 1.08;
  }

  .product-summary__stock {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    min-height: 2.25rem;
    padding: 0.4rem 0.85rem;
    border-radius: 999px;
    font-size: 0.92rem;
    font-weight: 700;
    white-space: nowrap;
  }

  .product-summary__stock--available {
    background: rgba(22, 163, 74, 0.12);
    color: #15803d;
  }

  .product-summary__stock--unavailable {
    background: rgba(220, 38, 38, 0.12);
    color: #b91c1c;
  }

  .product-summary__pricing {
    margin-top: 1.5rem;
  }

  .product-summary__price {
    margin: 0;
    color: #111827;
    font-size: clamp(1.8rem, 4vw, 2.7rem);
    line-height: 1;
  }

  .product-summary__discount {
    margin: 0.65rem 0 0;
    color: #8a6630;
    font-size: 0.98rem;
    font-weight: 600;
  }

  .product-summary__details {
    display: grid;
    gap: 0.85rem;
    margin-top: 1.5rem;
  }

  .product-summary__detail {
    display: grid;
    gap: 0.25rem;
    padding: 0.9rem 1rem;
    border-radius: 1rem;
    background: rgba(255, 255, 255, 0.72);
    border: 1px solid rgba(15, 23, 42, 0.06);
  }

  .product-summary__label {
    color: rgba(17, 24, 39, 0.56);
    font-size: 0.82rem;
    font-weight: 600;
    letter-spacing: 0.02em;
  }

  .product-summary__divider {
    margin: 1.5rem 0;
  }

  .product-summary__purchase {
    display: grid;
    grid-template-columns: minmax(0, 180px) minmax(0, 1fr);
    gap: 1rem;
    align-items: end;
  }

  .product-summary__field {
    min-width: 0;
  }

  .product-summary__add-button {
    min-height: 56px;
    font-weight: 700;
    letter-spacing: 0.01em;
    text-transform: none;
    box-shadow: 0 16px 30px rgba(219, 182, 113, 0.28);
  }

  @media (max-width: 959px) {
    .product-page__container {
      padding: 1rem 0.75rem 3rem;
    }

    .product-page__layout {
      grid-template-columns: minmax(0, 1fr);
      gap: 1.25rem;
    }

    .product-summary__card {
      padding: 1.25rem;
    }
  }

  @media (max-width: 600px) {
    .product-gallery__media {
      grid-template-columns: minmax(0, 1fr);
    }

    .product-gallery__thumbs--horizontal {
      flex-direction: row;
      overflow-x: auto;
      padding-bottom: 0.25rem;
      margin-bottom: 0.25rem;
      scrollbar-width: thin;
    }

    .product-gallery__thumb {
      width: 78px;
      height: 78px;
      flex: 0 0 auto;
    }

    .product-summary__header {
      align-items: start;
    }

    .product-summary__purchase {
      grid-template-columns: minmax(0, 1fr);
    }

    .product-summary__add-button {
      position: sticky;
      bottom: 0.75rem;
      z-index: 1;
    }
  }
</style>
