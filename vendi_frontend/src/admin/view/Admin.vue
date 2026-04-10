<template>
  <Header account-active />
  <v-divider />
  <v-container class="container">
    <Path :old-paths="loadPastPaths(route)" active-path="Admin Dashboard" />

    <v-row class="dashboard-row">
      <v-col class="pa-0" cols="12" md="3" lg="2">
        <SideAdminMenu active-dashboard />
      </v-col>

      <v-col cols="12" md="9" lg="10" class="content-column">
        <section class="hero-card">
          <div class="hero-copy">
            <p class="eyebrow">Visao operacional</p>
            <h1>Central administrativa do catalogo</h1>
            <p class="hero-description">
              Acompanhe a saude do inventario, revise produtos com urgencia e acesse as
              principais acoes do backoffice em um unico lugar.
            </p>
          </div>

          <div class="hero-actions">
            <v-btn
              color="#DBB671"
              variant="flat"
              prepend-icon="mdi-plus"
              to="/user/products/create">
              Novo produto
            </v-btn>
            <v-btn variant="outlined" color="black" prepend-icon="mdi-package-variant" to="/user/products">
              Gerenciar catalogo
            </v-btn>
            <v-btn variant="text" color="black" prepend-icon="mdi-refresh" :loading="isRefreshing" @click="loadDashboard">
              Atualizar painel
            </v-btn>
          </div>
        </section>

        <v-alert
          v-if="errorMessage"
          class="mb-6"
          type="error"
          variant="tonal"
          border="start"
          closable
          @click:close="errorMessage = null">
          {{ errorMessage }}
        </v-alert>

        <v-row v-if="isLoading" class="mb-6">
          <v-col v-for="n in 4" :key="`metric-skeleton-${n}`" cols="12" sm="6" xl="3">
            <v-skeleton-loader type="article" class="metric-skeleton" />
          </v-col>
        </v-row>

        <template v-else>
          <v-row class="mb-2">
            <v-col v-for="metric in metrics" :key="metric.title" cols="12" sm="6" xl="3">
              <article class="metric-card">
                <div class="metric-icon" :style="{ backgroundColor: metric.iconBg }">
                  <v-icon :icon="metric.icon" />
                </div>
                <div>
                  <p class="metric-label">{{ metric.title }}</p>
                  <p class="metric-value">{{ metric.value }}</p>
                  <p class="metric-caption">{{ metric.caption }}</p>
                </div>
              </article>
            </v-col>
          </v-row>

          <v-row class="dashboard-grid">
            <v-col cols="12" xl="7">
              <section class="panel-card">
                <div class="panel-heading">
                  <div>
                    <p class="section-label">Atencao imediata</p>
                    <h2>Produtos com estoque baixo</h2>
                  </div>
                  <span class="section-meta">{{ lowStockProducts.length }} item(ns)</span>
                </div>

                <div v-if="lowStockProducts.length" class="product-list">
                  <article
                    v-for="product in lowStockProducts"
                    :key="product.id"
                    class="product-row">
                    <div class="product-main">
                      <div class="product-thumb">
                        <img v-if="product.image" :src="product.image" :alt="product.name" />
                        <v-icon v-else icon="mdi-image-off-outline" size="28" />
                      </div>

                      <div class="product-copy">
                        <div class="product-title-line">
                          <h3>{{ product.name }}</h3>
                          <span class="stock-badge critical">{{ stockLabel(product.quantity) }}</span>
                        </div>
                        <p>
                          {{ formatCurrency(product.price) }}
                          <span v-if="product.discount"> • {{ product.discount }}% off </span>
                        </p>
                        <p>{{ formatCategoryNames(product.categories) }}</p>
                      </div>
                    </div>

                    <v-btn
                      variant="text"
                      color="black"
                      append-icon="mdi-arrow-right"
                      @click="goToProduct(product.id)">
                      Editar
                    </v-btn>
                  </article>
                </div>

                <div v-else class="empty-state">
                  <v-icon icon="mdi-check-circle-outline" color="#DBB671" size="24" />
                  <p>Nenhum produto esta em zona critica de estoque.</p>
                </div>
              </section>
            </v-col>

            <v-col cols="12" xl="5">
              <section class="panel-card">
                <div class="panel-heading">
                  <div>
                    <p class="section-label">Distribuicao</p>
                    <h2>Cobertura por categoria</h2>
                  </div>
                  <span class="section-meta">{{ categoryBreakdown.length }} grupo(s)</span>
                </div>

                <div v-if="categoryBreakdown.length" class="category-list">
                  <article
                    v-for="category in categoryBreakdown"
                    :key="category.name"
                    class="category-row">
                    <div class="category-copy">
                      <div class="d-flex justify-space-between ga-3">
                        <strong>{{ category.name }}</strong>
                        <span>{{ category.count }} produto(s)</span>
                      </div>
                      <p>{{ category.share }} do catalogo</p>
                    </div>
                    <v-progress-linear
                      :model-value="category.percentage"
                      color="#DBB671"
                      bg-color="#f3ead7"
                      rounded />
                  </article>
                </div>

                <div v-else class="empty-state">
                  <v-icon icon="mdi-shape-outline" color="#DBB671" size="24" />
                  <p>As categorias ainda nao possuem produtos associados.</p>
                </div>
              </section>
            </v-col>

            <v-col cols="12" lg="6">
              <section class="panel-card">
                <div class="panel-heading">
                  <div>
                    <p class="section-label">Publicacao</p>
                    <h2>Entradas recentes</h2>
                  </div>
                  <span class="section-meta">Top {{ recentProducts.length }}</span>
                </div>

                <div v-if="recentProducts.length" class="spotlight-grid">
                  <article
                    v-for="product in recentProducts"
                    :key="product.id"
                    class="spotlight-card"
                    @click="goToProduct(product.id)">
                    <div class="spotlight-image">
                      <img v-if="product.image" :src="product.image" :alt="product.name" />
                      <v-icon v-else icon="mdi-package-variant-closed" size="28" />
                    </div>
                    <div>
                      <h3>{{ product.name }}</h3>
                      <p>{{ formatCurrency(product.price) }}</p>
                      <p>{{ stockLabel(product.quantity) }}</p>
                    </div>
                  </article>
                </div>

                <div v-else class="empty-state">
                  <v-icon icon="mdi-package-variant-remove" color="#DBB671" size="24" />
                  <p>O catalogo ainda nao possui produtos cadastrados.</p>
                </div>
              </section>
            </v-col>

            <v-col cols="12" lg="6">
              <section class="panel-card">
                <div class="panel-heading">
                  <div>
                    <p class="section-label">Promocoes</p>
                    <h2>Maior desconto ativo</h2>
                  </div>
                  <span class="section-meta">{{ discountedProducts.length }} campanha(s)</span>
                </div>

                <div v-if="discountedProducts.length" class="promotion-list">
                  <article
                    v-for="product in discountedProducts"
                    :key="product.id"
                    class="promotion-row">
                    <div>
                      <h3>{{ product.name }}</h3>
                      <p>
                        {{ product.discount }}% off • de {{ formatCurrency(product.fullPrice || product.price) }}
                        por {{ formatCurrency(product.price) }}
                      </p>
                    </div>
                    <v-btn
                      variant="text"
                      color="#DBB671"
                      append-icon="mdi-pencil"
                      @click="goToProduct(product.id)">
                      Ajustar
                    </v-btn>
                  </article>
                </div>

                <div v-else class="empty-state">
                  <v-icon icon="mdi-ticket-percent-outline" color="#DBB671" size="24" />
                  <p>Nao ha produtos promocionais ativos no momento.</p>
                </div>
              </section>
            </v-col>
          </v-row>
        </template>
      </v-col>
    </v-row>
  </v-container>
  <Footer />
</template>

<script setup>
  import { computed, onMounted, ref } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import Footer from '@/core/components/Footer.vue'
  import SideAdminMenu from '../components/AdminSideMenu.vue'
  import loadPastPaths from '@/core/utils/loadPastPaths'
  import api from '@/core/plugins/api'
  import productService from '@/core/utils/productService'
  import { enrichSummaryProduct } from '@/core/utils/createProductService'
  import categoryService from '@/core/services/categoryService'

  const route = useRoute()
  const router = useRouter()

  const LOW_STOCK_THRESHOLD = 5
  const RECENT_PRODUCTS_LIMIT = 4
  const LOW_STOCK_PRODUCTS_LIMIT = 5
  const DISCOUNT_PRODUCTS_LIMIT = 4

  const isLoading = ref(true)
  const isRefreshing = ref(false)
  const errorMessage = ref(null)
  const products = ref([])
  const categories = ref([])
  const recentProducts = ref([])
  const lowStockProducts = ref([])
  const discountedProducts = ref([])

  const categoryBreakdown = computed(() => {
    if (!products.value.length) {
      return []
    }

    const counts = new Map()

    for (const category of flattenCategoryTree(categories.value)) {
      counts.set(category.name, 0)
    }

    for (const product of products.value) {
      for (const category of product.categories || []) {
        counts.set(category.name, (counts.get(category.name) || 0) + 1)
      }
    }

    return [...counts.entries()]
      .filter(([, count]) => count > 0)
      .sort((left, right) => right[1] - left[1])
      .slice(0, 6)
      .map(([name, count]) => {
        const percentage = Math.round((count / products.value.length) * 100)

        return {
          name,
          count,
          percentage,
          share: `${percentage}%`,
        }
      })
  })

  const activeCategoryCount = computed(() => categoryBreakdown.value.length)

  const metrics = computed(() => {
    const totalProducts = products.value.length
    const totalInventory = products.value.reduce((sum, product) => sum + (product.quantity || 0), 0)
    const discountedCount = products.value.filter((product) => product.discount > 0).length
    const lowStockCount = products.value.filter(
      (product) => Number(product.quantity || 0) <= LOW_STOCK_THRESHOLD
    ).length

    return [
      {
        title: 'Produtos cadastrados',
        value: totalProducts,
        caption: `${activeCategoryCount.value} categorias com cobertura ativa`,
        icon: 'mdi-package-variant-closed',
        iconBg: '#f3ead7',
      },
      {
        title: 'Unidades em estoque',
        value: totalInventory,
        caption: lowStockCount
          ? `${lowStockCount} produto(s) exigem reposicao`
          : 'Sem alertas criticos de estoque',
        icon: 'mdi-warehouse',
        iconBg: '#f5f1e8',
      },
      {
        title: 'Produtos em promocao',
        value: discountedCount,
        caption: totalProducts ? `${Math.round((discountedCount / totalProducts) * 100)}% do catalogo` : '0% do catalogo',
        icon: 'mdi-sale-outline',
        iconBg: '#fff4da',
      },
      {
        title: 'Categorias mapeadas',
        value: flattenCategoryTree(categories.value).length,
        caption: largestCategoryLabel(categoryBreakdown.value),
        icon: 'mdi-shape-plus',
        iconBg: '#f8eee2',
      },
    ]
  })

  onMounted(() => {
    loadDashboard()
  })

  async function loadDashboard() {
    errorMessage.value = null
    isRefreshing.value = true

    if (!products.value.length && !categories.value.length) {
      isLoading.value = true
    }

    try {
      const [rawProducts, categoryTree] = await Promise.all([
        api.getAll('products', { limit: 100 }),
        categoryService.getCategories(),
      ])

      products.value = rawProducts
      categories.value = categoryTree

      const recentCandidates = rawProducts.slice(0, RECENT_PRODUCTS_LIMIT)
      const lowStockCandidates = rawProducts
        .filter((product) => Number(product.quantity || 0) <= LOW_STOCK_THRESHOLD)
        .sort((left, right) => Number(left.quantity || 0) - Number(right.quantity || 0))
        .slice(0, LOW_STOCK_PRODUCTS_LIMIT)
      const discountCandidates = rawProducts
        .filter((product) => Number(product.discount || 0) > 0)
        .sort((left, right) => Number(right.discount || 0) - Number(left.discount || 0))
        .slice(0, DISCOUNT_PRODUCTS_LIMIT)

      recentProducts.value = await hydrateProducts(recentCandidates)
      lowStockProducts.value = await hydrateProducts(lowStockCandidates)
      discountedProducts.value = discountCandidates.map((product) => enrichSummaryProduct(product))
    } catch (error) {
      errorMessage.value =
        error?.message || 'Nao foi possivel carregar o dashboard administrativo agora.'
    } finally {
      isLoading.value = false
      isRefreshing.value = false
    }
  }

  async function hydrateProducts(list) {
    return Promise.all(list.map((product) => productService.hydrateProductSummary(product)))
  }

  function goToProduct(productId) {
    router.push({ path: `/user/products/${productId}` })
  }

  function formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(Number(value || 0))
  }

  function stockLabel(quantity) {
    const safeQuantity = Number(quantity || 0)

    if (safeQuantity <= LOW_STOCK_THRESHOLD) {
      return `${safeQuantity} restante(s)`
    }

    return `${safeQuantity} em estoque`
  }

  function formatCategoryNames(productCategories = []) {
    if (!productCategories.length) {
      return 'Sem categoria vinculada'
    }

    return productCategories.map((category) => category.name).join(', ')
  }

  function flattenCategoryTree(tree = []) {
    return tree.flatMap((category) => [
      category,
      ...flattenCategoryTree(category.childCategories || []),
    ])
  }

  function largestCategoryLabel(breakdown) {
    if (!breakdown.length) {
      return 'Sem distribuicao por categoria ainda'
    }

    return `${breakdown[0].name} lidera com ${breakdown[0].share}`
  }
</script>

<style scoped>
  .container {
    margin-top: 80px;
    margin-bottom: 146px;
  }

  .dashboard-row {
    margin-top: 32px;
  }

  .content-column {
    display: flex;
    flex-direction: column;
    gap: 24px;
  }

  .hero-card {
    display: flex;
    justify-content: space-between;
    gap: 24px;
    padding: 32px;
    border: 1px solid rgba(0, 0, 0, 0.08);
    border-radius: 24px;
    background:
      radial-gradient(circle at top left, rgba(219, 182, 113, 0.22), transparent 35%),
      linear-gradient(135deg, #fffdf9 0%, #fff7ec 100%);
  }

  .hero-copy {
    max-width: 640px;
  }

  .eyebrow,
  .section-label,
  .metric-label {
    margin-bottom: 8px;
    text-transform: uppercase;
    letter-spacing: 0.12em;
    font-size: 12px;
    color: rgba(0, 0, 0, 0.58);
  }

  .hero-copy h1,
  .panel-heading h2 {
    font-size: 28px;
    line-height: 1.1;
    font-weight: 600;
    color: #181818;
  }

  .panel-heading h2 {
    font-size: 22px;
  }

  .hero-description {
    margin-top: 16px;
    max-width: 560px;
    font-size: 15px;
    line-height: 1.7;
    color: rgba(0, 0, 0, 0.72);
  }

  .hero-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: flex-start;
    justify-content: flex-end;
    gap: 12px;
  }

  .metric-card,
  .panel-card {
    height: 100%;
    border: 1px solid rgba(0, 0, 0, 0.08);
    border-radius: 24px;
    background: white;
  }

  .metric-card {
    display: flex;
    gap: 16px;
    padding: 22px;
  }

  .metric-icon {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 52px;
    height: 52px;
    border-radius: 16px;
    color: #181818;
  }

  .metric-value {
    font-size: 30px;
    font-weight: 600;
    color: #181818;
  }

  .metric-caption,
  .category-copy p,
  .product-copy p,
  .spotlight-card p,
  .promotion-row p,
  .empty-state p {
    color: rgba(0, 0, 0, 0.62);
    font-size: 14px;
    line-height: 1.5;
  }

  .panel-card {
    padding: 24px;
  }

  .panel-heading {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    gap: 16px;
    margin-bottom: 20px;
  }

  .section-meta {
    color: rgba(0, 0, 0, 0.5);
    font-size: 13px;
    white-space: nowrap;
  }

  .product-list,
  .category-list,
  .promotion-list {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }

  .product-row,
  .promotion-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 16px;
    padding: 16px 0;
    border-top: 1px solid rgba(0, 0, 0, 0.06);
  }

  .product-row:first-child,
  .promotion-row:first-child {
    padding-top: 0;
    border-top: none;
  }

  .product-main {
    display: flex;
    gap: 16px;
    align-items: center;
    min-width: 0;
  }

  .product-thumb,
  .spotlight-image {
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    border-radius: 18px;
    background: #faf4e8;
  }

  .product-thumb {
    width: 72px;
    height: 72px;
    flex-shrink: 0;
  }

  .product-thumb img,
  .spotlight-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .product-copy,
  .category-copy {
    min-width: 0;
  }

  .product-title-line {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: 10px;
    margin-bottom: 4px;
  }

  .product-title-line h3,
  .spotlight-card h3,
  .promotion-row h3 {
    font-size: 16px;
    font-weight: 600;
    color: #181818;
  }

  .stock-badge {
    display: inline-flex;
    align-items: center;
    padding: 4px 10px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 600;
  }

  .stock-badge.critical {
    background: #fff1df;
    color: #8e5b00;
  }

  .category-row {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }

  .spotlight-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
    gap: 16px;
  }

  .spotlight-card {
    display: flex;
    flex-direction: column;
    gap: 14px;
    padding: 14px;
    border-radius: 20px;
    background: #fcfaf6;
    cursor: pointer;
    transition:
      transform 0.2s ease,
      box-shadow 0.2s ease;
  }

  .spotlight-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.06);
  }

  .spotlight-image {
    height: 180px;
  }

  .empty-state {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 16px 0 8px;
  }

  .metric-skeleton {
    border-radius: 24px;
  }

  @media (max-width: 1264px) {
    .hero-card {
      flex-direction: column;
    }

    .hero-actions {
      justify-content: flex-start;
    }
  }

  @media (max-width: 960px) {
    .container {
      margin-bottom: 96px;
    }

    .dashboard-row {
      margin-top: 24px;
    }

    .spotlight-grid {
      grid-template-columns: 1fr;
    }
  }

  @media (max-width: 600px) {
    .hero-card,
    .panel-card {
      padding: 20px;
      border-radius: 20px;
    }

    .metric-card {
      padding: 18px;
      border-radius: 20px;
    }

    .product-row,
    .promotion-row {
      flex-direction: column;
      align-items: stretch;
    }

    .product-main {
      align-items: flex-start;
    }

    .hero-copy h1 {
      font-size: 24px;
    }
  }
</style>
