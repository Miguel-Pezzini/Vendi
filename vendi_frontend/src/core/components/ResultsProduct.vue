<template>
  <v-card class="product-card" rounded="xl" elevation="0">
    <div class="product-card__media-shell">
      <div class="product-card__toolbar">
        <span v-if="product.discount" class="product-card__badge">-{{ product.discount }}%</span>

        <Button
          v-if="product.isInWishList"
          density="comfortable"
          :flat="true"
          class="product-card__icon-button"
          icon="mdi-delete-outline" />
        <Button
          v-else
          density="comfortable"
          :flat="true"
          class="product-card__icon-button"
          :icon="product.isInWishList ? 'mdi-heart' : 'mdi-heart-outline'"
          @click="addToWishlist()" />
      </div>

      <button type="button" class="product-card__image-button" @click="verProduct()">
        <img :src="product.image" :alt="product.name" class="product-card__image" />
      </button>
    </div>

    <div class="product-card__content">
      <p v-if="product.storeName || product.extraInfo" class="product-card__meta">
        {{ product.storeName || product.extraInfo }}
      </p>

      <h3 class="product-card__title">{{ product.name }}</h3>

      <div class="product-card__price-row">
        <span class="product-card__price">R$ {{ product.price }}</span>
        <span
          v-if="product.discount"
          class="product-card__full-price text-decoration-line-through">
          R$ {{ product.fullPrice }}
        </span>
      </div>

      <div v-if="!product.isInWishList" class="product-card__rating">
        <v-rating
          readonly
          half-increments
          :size="20"
          active-color="#dbb671"
          color="rgba(15, 23, 42, 0.18)"
          :model-value="3.5" />
        <p class="product-card__rating-count">(65)</p>
      </div>

      <v-btn
        color="black"
        prepend-icon="mdi-cart"
        rounded="pill"
        class="product-card__cta"
        text="Add To Cart"
        @click="addToCart" />
    </div>
  </v-card>
</template>

<script setup>
  import Button from '@/core/components/Button'
  import router from '../router'
  import { useRoute } from 'vue-router'
  import loadPastPaths from '../utils/loadPastPaths'

  const route = useRoute()

  const emit = defineEmits(['addToWishlist', 'addToCart'])

  const props = defineProps({
    product: {
      type: Object,
      default: null,
    },
    activePage: {
      type: String,
      default: null,
    },
  })

  function addToWishlist() {
    emit('addToWishlist', props.product.isInWishList)
  }

  function addToCart() {
    emit('addToCart', props.product)
  }

  function verProduct() {
    router.push({
      path: `/product/${props.product.id}`,
      query: { origin: loadPastPaths(route, props.activePage) },
    })
  }
</script>

<style scoped>
  .product-card {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
    border: 1px solid rgba(15, 23, 42, 0.08);
    background:
      linear-gradient(180deg, rgba(248, 250, 252, 0.95) 0%, rgba(255, 255, 255, 1) 32%),
      #fff;
    box-shadow:
      0 18px 40px rgba(15, 23, 42, 0.06),
      0 2px 8px rgba(15, 23, 42, 0.04);
    overflow: hidden;
    transition:
      transform 0.2s ease,
      box-shadow 0.2s ease,
      border-color 0.2s ease;
  }

  .product-card__media-shell {
    position: relative;
    padding: 0.75rem;
  }

  .product-card__toolbar {
    position: absolute;
    top: 0.875rem;
    left: 0.875rem;
    right: 0.875rem;
    z-index: 2;
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    gap: 0.75rem;
  }

  .product-card__badge {
    display: inline-flex;
    align-items: center;
    min-height: 1.75rem;
    padding: 0.2rem 0.65rem;
    border-radius: 999px;
    background: rgba(219, 182, 113, 0.96);
    color: #fff;
    font-size: 0.7rem;
    font-weight: 700;
    letter-spacing: 0.02em;
  }

  .product-card__icon-button {
    border-radius: 999px;
    background: rgba(255, 255, 255, 0.92);
    box-shadow: 0 10px 24px rgba(15, 23, 42, 0.12);
    backdrop-filter: blur(12px);
  }

  .product-card__image-button {
    display: block;
    width: 100%;
    padding: 0;
    border: 0;
    border-radius: 1rem;
    background: linear-gradient(180deg, #f8fafc 0%, #eef2f7 100%);
    overflow: hidden;
    cursor: pointer;
  }

  .product-card__image {
    display: block;
    width: 100%;
    aspect-ratio: 1 / 1;
    object-fit: cover;
    transition:
      transform 0.28s ease,
      filter 0.28s ease;
  }

  .product-card__content {
    display: flex;
    flex: 1;
    flex-direction: column;
    gap: 0.625rem;
    padding: 0 0.875rem 0.875rem;
  }

  .product-card__meta {
    margin: 0;
    color: rgba(15, 23, 42, 0.62);
    font-size: 0.76rem;
    font-weight: 600;
    letter-spacing: 0.01em;
  }

  .product-card__title {
    margin: 0;
    color: #101828;
    font-size: clamp(0.95rem, 1.3vw, 1.05rem);
    line-height: 1.4;
    font-weight: 600;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    min-height: 2.7em;
  }

  .product-card__price-row {
    display: flex;
    flex-wrap: wrap;
    align-items: baseline;
    gap: 0.5rem 0.75rem;
  }

  .product-card__price {
    color: #dbb671;
    font-size: 1rem;
    font-weight: 700;
  }

  .product-card__full-price {
    color: rgba(15, 23, 42, 0.45);
    font-size: 0.82rem;
  }

  .product-card__rating {
    display: flex;
    align-items: center;
    gap: 0.35rem;
    margin-top: -0.1rem;
  }

  .product-card__rating-count {
    margin: 0;
    color: rgba(15, 23, 42, 0.5);
    font-size: 0.78rem;
    font-weight: 700;
  }

  .product-card__cta {
    width: 100%;
    min-height: 2.625rem;
    margin-top: auto;
    text-transform: none;
    letter-spacing: 0;
    font-size: 0.92rem;
    font-weight: 600;
  }

  @media (hover: hover) {
    .product-card:hover {
      transform: translateY(-4px);
      border-color: rgba(219, 182, 113, 0.35);
      box-shadow:
        0 24px 44px rgba(15, 23, 42, 0.1),
        0 6px 16px rgba(15, 23, 42, 0.06);
    }

    .product-card:hover .product-card__image {
      filter: brightness(0.97);
      transform: scale(1.035);
    }
  }

  @media (max-width: 599px) {
    .product-card__media-shell {
      padding: 0.7rem;
    }

    .product-card__content {
      padding: 0 0.8rem 0.8rem;
    }

    .product-card__toolbar {
      top: 0.8rem;
      left: 0.8rem;
      right: 0.8rem;
    }
  }
</style>
