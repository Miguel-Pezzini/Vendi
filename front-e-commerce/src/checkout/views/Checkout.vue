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
            <v-form>
              <Input
                variant="outlined"
                label="Primeiro Nome"
                required
              />
              <Input
                variant="outlined"
                label="Nome da Empresa"
              />
              <Input
                variant="outlined"
                label="Endereço"
                required
              />
              <Input
                variant="outlined"
                label="Apartamento, casa, etc. (Opcional)"
              />
              <Input
                variant="outlined"
                label="Cidade"
                required
              />
              <Input
                variant="outlined"
                label="Telefone"
                required
              />
              <Input
                variant="outlined"
                label="E-mail"
                required
              />
              <v-checkbox label="Salvar esses dados para Transações futuras" />
            </v-form>
          </div>
        </v-col>
        <v-col class="billing-container">
          <v-row
            v-for="n in 3"
            :key="n"
            align="center"
            class="mb-8"
          >
            <div class="d-flex align-center ga-6">
              <v-img
                height="54"
                width="54"
                :src="card1"
              />
              <p>LCD Monitor</p>
            </div>
            <v-spacer />
            <p>R$ 500</p>
          </v-row>
          <div class="d-flex flex-column ga-4 mb-8">
            <v-row>
              <p>Subtotal: </p>
              <v-spacer />
              <p>R$ 500</p>
            </v-row>
            <v-row><v-divider opacity="0.6" /></v-row>
            <v-row>
              <p>Frete: </p>
              <v-spacer />
              <p>R$ 500</p>
            </v-row>
            <v-row><v-divider opacity="0.6" /></v-row>
            <v-row>
              <p>Total: </p>
              <v-spacer />
              <p>R$ 500</p>
            </v-row>
          </div>
          <v-row class="mt-8">
            <v-radio-group>
              <div class="d-flex align-center">
                <v-radio
                label="Banco"
                value="BANCO"
              />
              <v-spacer /><v-icon>mdi-account</v-icon>
              </div>
              
              <v-radio
                label="Dinheiro na entrega"
                value="DINHEIRO"
              />
            </v-radio-group>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
  
  <Footer />
</template>
  
<script setup>
    import { ref, onMounted } from 'vue';
    import { useRoute } from 'vue-router';
  import Header from '@/core/components/Header.vue';
  import Path from '@/core/components/Path.vue';
  import Input from '@/core/components/Input.vue';
  import Footer from '@/core/components/Footer.vue';
  import card1 from '@/assets/card1.webp'

  import loadPastPaths from '@/core/utils/loadPastPaths';

  const route = useRoute()

  const oldPaths = ref([])

  onMounted(() => {
    oldPaths.value = loadPastPaths(route)
  })
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
  