<template>
  <Header />
  <div class="d-flex container">
    <v-container>
      <v-row>
        <Path
          :old-paths="oldPaths"
          :active-path="'Checkout'"
        />
      </v-row>
      <v-row class="mt-16 pt-4">
        <v-col cols="6 mr-16">
          <h1 class="mb-12">
            Detalhes da Compra
          </h1>
          <div style="max-width: 470px;">
            <v-form ref="form">
              <Input
                v-model="dadosForm.firstName"
                variant="outlined"
                label="Primeiro Nome"
                required
              />
              <Input
                v-model="dadosForm.companyName"
                variant="outlined"
                label="Nome da Empresa"
              />
              <Input
                v-model="dadosForm.address"
                variant="outlined"
                label="Endereço"
                required
              />
              <Input
                v-model="dadosForm.additionalAddress"
                variant="outlined"
                label="Apartamento, casa, etc. (Opcional)"
              />
              <Input
                v-model="dadosForm.city"
                variant="outlined"
                label="Cidade"
                required
              />
              <Input
                v-model="dadosForm.phone"
                variant="outlined"
                label="Telefone"
                required
              />
              <Input
                v-model="dadosForm.email"
                type="email"
                variant="outlined"
                label="E-mail"
                required
              />
              <v-checkbox
                v-model="salvarForm"
                label="Salvar esses dados para Transações futuras"
              />
            </v-form>
          </div>
        </v-col>
        <v-col class="billing-container">
          <BillingContainer @fazer-pedido="onFazerPedido" />
        </v-col>
      </v-row>
    </v-container>
  </div>
  
  <Footer />
</template>
  
<script setup>
    import { ref, onMounted } from 'vue';
    import Header from '@/core/components/Header.vue';
    import Path from '@/core/components/Path.vue';
    import Input from '@/core/components/Input.vue';
    import BillingContainer from '../components/BillingContainer.vue';
    import Footer from '@/core/components/Footer.vue';
    import loadPastPaths from '@/core/utils/loadPastPaths';

  const oldPaths = ref([])
  const form = ref(null)

  onMounted(() => {
    oldPaths.value = loadPastPaths()
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
  const paymentMethod = ref(null)
  const salvarForm = ref(false)


  async function onFazerPedido(data) {
    const isValid = await form.value.validate()
    if(!isValid) return
    paymentMethod.value = data;
  }
</script>
  
<style scoped>
   .container {
    font-family: "Poppins", serif;
    margin-top: 80px;
    margin-bottom: 140px;
    margin-right: 135px;
    margin-left: 135px;
  }
  .billing-container {
    margin-top: 160px;
  }
</style>
  