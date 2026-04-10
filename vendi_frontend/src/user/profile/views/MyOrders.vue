<template>
  <Header account-active />
  <v-divider />
  <v-container class="container">
    <v-row>
      <Path :old-paths="['Home']" active-path="My Orders" />
    </v-row>

    <v-row class="container margin">
      <v-col class="pa-0" cols="4">
        <SideMenu active-my-orders />
      </v-col>

      <v-col>
        <div class="content-header">
          <h1 class="title">Track your orders</h1>
          <p class="subtitle">Acompanhe status, itens comprados e historico de atualizacoes.</p>
        </div>

        <v-alert v-if="errorMessage" type="error" variant="tonal" class="mb-6">
          {{ errorMessage }}
        </v-alert>

        <v-progress-linear v-if="loading" color="#dbb671" indeterminate class="mb-6" />

        <v-card v-if="!loading && orders.length === 0" class="empty-state">
          <h2>Voce ainda nao tem pedidos.</h2>
          <p>Quando uma compra for criada no checkout, ela aparecera aqui para acompanhamento.</p>
        </v-card>

        <div v-else class="orders-list">
          <v-card
            v-for="order in orders"
            :key="order.id"
            :class="['order-card', { 'order-card--highlight': order.id === highlightedOrderId }]">
            <div class="order-card__header">
              <div>
                <p class="order-label">Pedido</p>
                <h2>#{{ shortId(order.id) }}</h2>
                <p class="order-meta">
                  {{ formatDate(order.createdAt) }} • {{ order.totalItems }} item(ns)
                </p>
              </div>

              <div class="order-card__summary">
                <v-chip :color="statusColor(order.status)" variant="flat" size="small">
                  {{ statusLabel(order.status) }}
                </v-chip>
                <strong>{{ formatCurrency(order.totalAmount) }}</strong>
              </div>
            </div>

            <div class="order-card__body">
              <div>
                <p class="order-label">Comprador</p>
                <p>{{ order.customerName }}</p>
                <p class="text-medium-emphasis">{{ order.email }}</p>
              </div>

              <v-btn
                variant="outlined"
                color="#dbb671"
                :loading="loadingOrderId === order.id"
                @click="toggleOrderDetails(order.id)">
                {{ expandedOrderId === order.id ? 'Ocultar detalhes' : 'Acompanhar pedido' }}
              </v-btn>
            </div>

            <div v-if="expandedOrderId === order.id && orderDetails" class="order-details">
              <div class="order-details__section">
                <h3>Entrega e pagamento</h3>
                <p>{{ orderDetails.customerName }} • {{ orderDetails.phone }}</p>
                <p>{{ orderDetails.addressLine1 }}</p>
                <p v-if="orderDetails.addressLine2">{{ orderDetails.addressLine2 }}</p>
                <p>{{ orderDetails.city }}</p>
                <p>Pagamento: {{ orderDetails.paymentProvider }}</p>
              </div>

              <div class="order-details__section">
                <h3>Itens</h3>
                <div v-for="item in orderDetails.items" :key="item.id" class="order-item">
                  <img
                    v-if="item.product?.image"
                    :src="item.product.image"
                    :alt="item.product.name"
                    class="order-item__image" />
                  <div v-else class="order-item__image order-item__image--placeholder" />

                  <div class="order-item__content">
                    <RouterLink :to="`/product/${item.product.id}`" class="order-item__link">
                      {{ item.product.name }}
                    </RouterLink>
                    <p>Quantidade: {{ item.quantity }}</p>
                    <p>Unitario: {{ formatCurrency(item.unitPrice) }}</p>
                  </div>

                  <strong>{{ formatCurrency(item.totalAmount) }}</strong>
                </div>
              </div>

              <div class="order-details__section">
                <h3>Historico</h3>
                <div
                  v-for="historyEntry in orderDetails.statusHistory"
                  :key="historyEntry.id"
                  class="history-entry">
                  <div>
                    <strong>{{ statusLabel(historyEntry.status) }}</strong>
                    <p>{{ formatDate(historyEntry.changedAt) }}</p>
                  </div>
                </div>
              </div>
            </div>
          </v-card>
        </div>
      </v-col>
    </v-row>
  </v-container>
  <Footer />
</template>

<script setup>
  import { getCurrentInstance, onMounted, ref } from 'vue'
  import { useRoute } from 'vue-router'
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import Footer from '@/core/components/Footer.vue'
  import SideMenu from '../components/SideMenu.vue'
  import orderService from '@/core/services/orderService'

  const route = useRoute()
  const { proxy } = getCurrentInstance()

  const loading = ref(true)
  const loadingOrderId = ref(null)
  const errorMessage = ref('')
  const orders = ref([])
  const expandedOrderId = ref(null)
  const orderDetails = ref(null)
  const highlightedOrderId = ref(route.query.order || null)

  onMounted(async () => {
    await loadOrders()

    if (highlightedOrderId.value) {
      await toggleOrderDetails(highlightedOrderId.value)
    }
  })

  async function loadOrders() {
    loading.value = true
    errorMessage.value = ''

    try {
      orders.value = await orderService.getOrders()
    } catch (error) {
      errorMessage.value = normalizeError(error, 'Nao foi possivel carregar os pedidos.')
    } finally {
      loading.value = false
    }
  }

  async function toggleOrderDetails(orderId) {
    if (expandedOrderId.value === orderId) {
      expandedOrderId.value = null
      orderDetails.value = null
      return
    }

    loadingOrderId.value = orderId
    errorMessage.value = ''

    try {
      orderDetails.value = await orderService.getOrderById(orderId)
      expandedOrderId.value = orderId
      highlightedOrderId.value = orderId
    } catch (error) {
      const message = normalizeError(error, 'Nao foi possivel carregar os detalhes do pedido.')
      errorMessage.value = message
      proxy?.$showMessage?.('error', message)
    } finally {
      loadingOrderId.value = null
    }
  }

  function shortId(orderId) {
    return orderId?.slice?.(0, 8) || ''
  }

  function formatDate(value) {
    if (!value) return '-'

    return new Intl.DateTimeFormat('pt-BR', {
      dateStyle: 'medium',
      timeStyle: 'short',
    }).format(new Date(value))
  }

  function formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(Number(value || 0))
  }

  function statusLabel(status) {
    return (
      {
        PENDING_PAYMENT: 'Pagamento pendente',
        PAID: 'Pago',
        PAYMENT_FAILED: 'Pagamento falhou',
        CANCELED: 'Cancelado',
      }[status] || status
    )
  }

  function statusColor(status) {
    return (
      {
        PENDING_PAYMENT: 'warning',
        PAID: 'success',
        PAYMENT_FAILED: 'error',
        CANCELED: 'grey',
      }[status] || 'primary'
    )
  }

  function normalizeError(error, fallback) {
    if (typeof error === 'string') return error
    return error?.message || fallback
  }
</script>

<style scoped>
  .container {
    margin-top: 80px;
  }

  .margin {
    margin-bottom: 146px;
  }

  .content-header {
    margin-bottom: 24px;
  }

  .title {
    color: #dbb671;
    font-size: 28px;
    font-weight: 600;
  }

  .subtitle {
    color: rgba(0, 0, 0, 0.6);
    margin-top: 8px;
  }

  .orders-list {
    display: grid;
    gap: 20px;
  }

  .order-card {
    padding: 24px;
    box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
  }

  .order-card--highlight {
    box-shadow: rgba(219, 182, 113, 0.55) 0px 0px 0px 2px;
  }

  .order-card__header,
  .order-card__body {
    display: flex;
    justify-content: space-between;
    gap: 16px;
    align-items: center;
  }

  .order-card__body {
    margin-top: 20px;
  }

  .order-card__summary {
    display: flex;
    gap: 12px;
    align-items: center;
  }

  .order-label {
    color: rgba(0, 0, 0, 0.55);
    font-size: 13px;
    margin-bottom: 4px;
    text-transform: uppercase;
    letter-spacing: 0.08em;
  }

  .order-meta {
    color: rgba(0, 0, 0, 0.6);
    margin-top: 4px;
  }

  .order-details {
    display: grid;
    gap: 20px;
    margin-top: 24px;
    padding-top: 24px;
    border-top: 1px solid rgba(0, 0, 0, 0.08);
  }

  .order-details__section {
    display: grid;
    gap: 12px;
  }

  .order-details__section h3 {
    font-size: 18px;
    font-weight: 600;
  }

  .order-item {
    display: grid;
    grid-template-columns: 72px 1fr auto;
    gap: 16px;
    align-items: center;
  }

  .order-item__image {
    width: 72px;
    height: 72px;
    object-fit: cover;
    border-radius: 12px;
    background: #f5f5f5;
  }

  .order-item__image--placeholder {
    background: linear-gradient(135deg, #f1eadb, #f7f7f7);
  }

  .order-item__content {
    display: grid;
    gap: 4px;
  }

  .order-item__link {
    color: black;
    font-weight: 600;
    text-decoration: none;
  }

  .history-entry {
    padding: 14px 16px;
    border-radius: 14px;
    background: #faf7f0;
  }

  .empty-state {
    padding: 32px;
    display: grid;
    gap: 8px;
  }

  @media (max-width: 960px) {
    .order-card__header,
    .order-card__body,
    .order-item {
      grid-template-columns: 1fr;
      display: grid;
    }
  }
</style>
