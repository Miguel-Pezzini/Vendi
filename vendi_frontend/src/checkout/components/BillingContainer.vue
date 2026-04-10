<template>
  <div class="prices-container">
    <v-row v-for="item in cart.items" :key="item.id" align="center" class="mb-8">
      <div class="d-flex align-center ga-6">
        <v-img height="54" width="54" :src="item.product.image" />
        <p>{{ item.product.name }}</p>
      </div>
      <v-spacer />
      <p>R$ {{ item.subtotal }}</p>
    </v-row>
    <div class="d-flex flex-column ga-4">
      <v-row>
        <p>Subtotal:</p>
        <v-spacer />
        <p>R$ {{ cart.subtotal || 0 }}</p>
      </v-row>
      <v-row><v-divider opacity="0.6" /></v-row>
      <v-row>
        <p>Frete:</p>
        <v-spacer />
        <p>Free</p>
      </v-row>
      <v-row><v-divider opacity="0.6" /></v-row>
      <v-row>
        <p>Total:</p>
        <v-spacer />
        <p>R$ {{ cart.subtotal || 0 }}</p>
      </v-row>
    </div>
    <div class="stripe-box mt-8">
      <div class="stripe-box__header">
        <span class="stripe-box__badge">Stripe</span>
        <span class="stripe-box__title">Pagamento seguro hospedado</span>
      </div>
      <p class="stripe-box__text">
        Ao continuar, voce sera redirecionado para a pagina segura do Stripe para concluir o
        pagamento.
      </p>
    </div>
  </div>

  <v-row align="center" class="ga-4">
    <v-col class="pa-0 ma-0">
      <Input :hide-details="true" variant="outlined" label="Cupom de Desconto" />
    </v-col>
    <v-col cols="5" class="pa-0 ma-0">
      <button class="button-cupom">Aplicar Cupom</button>
    </v-col>
  </v-row>
  <v-row class="mt-8">
    <button :disabled="loading || !cart.items?.length" class="button-cupom" @click="fazerPedido">
      {{ loading ? 'Redirecionando...' : 'Pagar com Stripe' }}
    </button>
  </v-row>
</template>

<script setup>
  import Input from '@/core/components/Input.vue'

  defineProps({
    cart: {
      type: Object,
      default: () => ({ items: [], subtotal: 0 }),
    },
    loading: {
      type: Boolean,
      default: false,
    },
  })

  const emit = defineEmits(['fazerPedido'])

  function fazerPedido() {
    emit('fazerPedido')
  }
</script>

<style scoped>
  .billing-container {
    margin-top: 160px;
  }
  .prices-container {
    max-width: 425px;
  }
  .button-cupom {
    background-color: #dbb671;
    color: #fff;
    padding: 16px 48px;
    border-radius: 4px;
    width: 100%;
  }

  .button-cupom:disabled {
    cursor: not-allowed;
    opacity: 0.7;
  }

  .stripe-box {
    border: 1px solid rgba(15, 23, 42, 0.1);
    border-radius: 16px;
    padding: 20px;
    background: linear-gradient(135deg, rgba(15, 23, 42, 0.02), rgba(219, 182, 113, 0.12));
  }

  .stripe-box__header {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 12px;
  }

  .stripe-box__badge {
    background: #635bff;
    color: #fff;
    border-radius: 999px;
    padding: 4px 10px;
    font-size: 0.8rem;
    font-weight: 700;
  }

  .stripe-box__title {
    font-weight: 700;
  }

  .stripe-box__text {
    margin: 0;
    color: rgba(15, 23, 42, 0.72);
  }
</style>
