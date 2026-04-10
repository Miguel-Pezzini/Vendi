<template>
  <Header />
  <div class="d-flex container">
    <v-container>
      <v-row>
        <Path :old-paths="oldPaths" :active-path="'Checkout'" />
      </v-row>
      <v-row class="mt-16 pt-4">
        <v-col cols="6 mr-16">
          <h1 class="mb-12">Detalhes da Compra</h1>
          <div v-if="statusMessage" :class="['checkout-status', `checkout-status--${statusTone}`]">
            {{ statusMessage }}
          </div>
          <v-btn
            v-if="confirmedOrderId"
            class="mb-6"
            color="#dbb671"
            variant="flat"
            @click="goToOrderTracking">
            Ver acompanhamento do pedido
          </v-btn>
          <div style="max-width: 470px">
            <v-form ref="form">
              <Input
                v-model="dadosForm.firstName"
                variant="outlined"
                label="Primeiro Nome"
                required />
              <Input v-model="dadosForm.companyName" variant="outlined" label="Nome da Empresa" />
              <Input v-model="dadosForm.address" variant="outlined" label="Endereço" required />
              <Input
                v-model="dadosForm.additionalAddress"
                variant="outlined"
                label="Apartamento, casa, etc. (Opcional)" />
              <Input v-model="dadosForm.city" variant="outlined" label="Cidade" required />
              <Input v-model="dadosForm.phone" variant="outlined" label="Telefone" required />
              <Input
                v-model="dadosForm.email"
                type="email"
                variant="outlined"
                label="E-mail"
                required />
              <v-checkbox v-model="salvarForm" label="Salvar esses dados para Transações futuras" />
            </v-form>
          </div>
        </v-col>
        <v-col class="billing-container">
          <BillingContainer :cart="cart" :loading="submitting" @fazer-pedido="onFazerPedido" />
        </v-col>
      </v-row>
    </v-container>
  </div>

  <Footer />
</template>

<script setup>
  import { computed, getCurrentInstance, onMounted, ref } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import Header from '@/core/components/Header.vue'
  import Path from '@/core/components/Path.vue'
  import Input from '@/core/components/Input.vue'
  import BillingContainer from '../components/BillingContainer.vue'
  import Footer from '@/core/components/Footer.vue'
  import loadPastPaths from '@/core/utils/loadPastPaths'
  import cartService from '@/core/services/cartService'
  import checkoutService from '@/core/services/checkoutService'

  const route = useRoute()
  const router = useRouter()
  const { proxy } = getCurrentInstance()

  const oldPaths = ref([])
  const form = ref(null)
  const cart = ref({ items: [], subtotal: 0, totalItems: 0 })
  const submitting = ref(false)
  const statusMessage = ref('')
  const statusTone = ref('info')
  const confirmedOrderId = ref(null)

  onMounted(async () => {
    oldPaths.value = loadPastPaths(route)
    loadSavedCheckoutDetails()
    await refreshCart()
    await hydrateCheckoutReturn()
  })

  const dadosForm = ref({
    firstName: '',
    companyName: '',
    address: '',
    additionalAddress: '',
    city: '',
    phone: '',
    email: '',
  })
  const salvarForm = ref(false)
  const hasItemsInCart = computed(() => (cart.value.items || []).length > 0)

  async function onFazerPedido() {
    const isValid = await form.value.validate()
    if (!isValid.valid) return
    if (!hasItemsInCart.value) {
      statusTone.value = 'error'
      statusMessage.value = 'Seu carrinho esta vazio.'
      return
    }

    submitting.value = true

    try {
      const session = await checkoutService.createSession({
        firstName: dadosForm.value.firstName,
        companyName: dadosForm.value.companyName,
        address: dadosForm.value.address,
        additionalAddress: dadosForm.value.additionalAddress,
        city: dadosForm.value.city,
        phone: dadosForm.value.phone,
        email: dadosForm.value.email,
      })

      persistCheckoutDetails()
      window.location.assign(session.checkoutUrl)
    } catch (error) {
      const message = typeof error === 'string' ? error : error?.message || 'Nao foi possivel iniciar o checkout.'
      statusTone.value = 'error'
      statusMessage.value = message
      proxy?.$showMessage?.('error', message)
      submitting.value = false
    }
  }

  async function refreshCart() {
    try {
      cart.value = await cartService.getCart()
    } catch (error) {
      cart.value = { items: [], subtotal: 0, totalItems: 0 }
    }
  }

  async function hydrateCheckoutReturn() {
    const checkoutStatus = route.query.status
    const sessionId = route.query.session_id

    if (checkoutStatus === 'canceled') {
      confirmedOrderId.value = null
      statusTone.value = 'warning'
      statusMessage.value = 'Pagamento cancelado. Seu carrinho foi mantido para voce tentar novamente.'
      return
    }

    if (checkoutStatus !== 'success' || !sessionId) {
      return
    }

    for (let attempt = 0; attempt < 6; attempt += 1) {
      try {
        const response = await checkoutService.getSessionStatus(sessionId)

        if (response.status === 'PAID') {
          await refreshCart()
          confirmedOrderId.value = response.orderId
          statusTone.value = 'success'
          statusMessage.value = `Compra confirmada para ${response.email}. Pedido registrado com total de R$ ${response.totalAmount}.`
          proxy?.$showMessage?.('success', 'Compra confirmada com sucesso.')
          await router.replace({ path: route.path, query: { origin: route.query.origin } })
          return
        }

        if (response.status === 'PAYMENT_FAILED') {
          confirmedOrderId.value = null
          statusTone.value = 'error'
          statusMessage.value = 'O pagamento falhou no Stripe. Revise os dados e tente novamente.'
          return
        }

        if (response.status === 'CANCELED') {
          confirmedOrderId.value = null
          statusTone.value = 'warning'
          statusMessage.value = 'A sessao de pagamento expirou ou foi cancelada.'
          return
        }
      } catch (error) {
        break
      }

      await wait(1500)
    }

    statusTone.value = 'info'
    confirmedOrderId.value = null
    statusMessage.value =
      'Pagamento recebido e em confirmacao final. Se necessario, recarregue a pagina em alguns segundos.'
  }

  function goToOrderTracking() {
    if (!confirmedOrderId.value) return
    router.push({
      path: '/account/orders',
      query: { order: confirmedOrderId.value, origin: route.query.origin },
    })
  }

  function loadSavedCheckoutDetails() {
    const saved = globalThis.localStorage?.getItem('checkout_details')
    if (!saved) return

    try {
      dadosForm.value = {
        ...dadosForm.value,
        ...JSON.parse(saved),
      }
      salvarForm.value = true
    } catch (error) {
      globalThis.localStorage?.removeItem('checkout_details')
    }
  }

  function persistCheckoutDetails() {
    if (!salvarForm.value) {
      globalThis.localStorage?.removeItem('checkout_details')
      return
    }

    globalThis.localStorage?.setItem('checkout_details', JSON.stringify(dadosForm.value))
  }

  function wait(ms) {
    return new Promise((resolve) => {
      globalThis.setTimeout(resolve, ms)
    })
  }
</script>

<style scoped>
  .container {
    margin-top: 80px;
    margin-bottom: 140px;
    margin-right: 135px;
    margin-left: 135px;
  }
  .billing-container {
    margin-top: 160px;
  }

  .checkout-status {
    border-radius: 16px;
    padding: 16px 18px;
    margin-bottom: 20px;
    font-weight: 500;
  }

  .checkout-status--info {
    background: rgba(59, 130, 246, 0.08);
    color: #1d4ed8;
  }

  .checkout-status--success {
    background: rgba(34, 197, 94, 0.1);
    color: #166534;
  }

  .checkout-status--warning {
    background: rgba(245, 158, 11, 0.12);
    color: #92400e;
  }

  .checkout-status--error {
    background: rgba(239, 68, 68, 0.1);
    color: #b91c1c;
  }
</style>
