<template>
  <ul class="d-flex flex-column ga-4">
    <div class="d-flex flex-column ga-3">
      <li>
        <RouterLink><h1>Gerenciar Minha Conta</h1></RouterLink>
      </li>
      <div class="d-flex flex-column ga-2">
        <li>
          <RouterLink>
            <p :style="{ color: activeMinhaConta ? '#DBB671' : black }">Minhas Conta</p>
          </RouterLink>
        </li>
        <li>
          <RouterLink><p>Minhas Formas de Pagamento</p></RouterLink>
        </li>
      </div>
    </div>

    <div class="d-flex flex-column ga-3">
      <li>
        <RouterLink><h1>Meus Pedidos</h1></RouterLink>
      </li>
      <div class="d-flex flex-column ga-2">
        <li>
          <RouterLink><p>Retornos</p></RouterLink>
        </li>
        <li>
          <RouterLink><p>Meus Cancelamentos</p></RouterLink>
        </li>
      </div>
    </div>

    <li>
      <RouterLink to="/wishlist">
        <h1>Lista De Desejos</h1>
      </RouterLink>
    </li>
  </ul>
</template>

<script setup>
  defineProps({
    activeMinhaConta: {
      type: Boolean,
      default: false,
    },
  })
</script>

<style scoped>
  a {
    text-decoration: none;
    color: black;
  }
  ul {
    list-style: none;
  }
  h1 {
    font-size: 16px;
    font-weight: 500;
  }
  p {
    margin-left: 35px;
    font-size: 16px;
    opacity: 0.5;
  }
</style>
