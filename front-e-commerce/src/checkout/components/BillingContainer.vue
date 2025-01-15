<template>
  <v-col class="billing-container">
    <div class="prices-container">
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
      <div class="d-flex flex-column ga-4">
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
            <v-spacer />
            <div class="d-flex ga-2">
              <v-img
                width="42"
                height="28"
                :src="logoBradesco"
              />
              <v-img
                width="42"
                height="28"
                :src="logoItau"
              />
              <v-img
                width="42"
                height="28"
                :src="logoSantander"
              />
              <v-img
                width="42"
                height="28"
                :src="logoNubank"
              />
              <v-img
                width="42"
                height="28"
                :src="logoPIX"
              />
            </div>
          </div>
                
          <v-radio
            label="Dinheiro na entrega"
            value="DINHEIRO"
          />
        </v-radio-group>
      </v-row>
    </div>
            
    <v-row
      align="center"
      class="ga-4"
    >
      <v-col class="pa-0 ma-0">
        <Input
          :hide-details="true"
          variant="outlined"
          label="Cupom de Desconto"
        />
      </v-col>
      <v-col
        cols="5"
        class="pa-0 ma-0"
      >
        <button class="button-cupom">
          Aplicar Cupom
        </button>
      </v-col>
    </v-row>
    <v-row class="mt-8">
      <button class="button-cupom">
        Fazer Pedido
      </button>
    </v-row>
  </v-col>
</template>
    
  <script setup>
      //import { ref } from 'vue';;
      import Input from '@/core/components/Input.vue';
  
      import card1 from '@/assets/card1.webp'
      import logoBradesco from '@/assets/logo-bancos/logo-bradesco.svg'
      import logoPIX from '@/assets/logo-bancos/logo-pix.png'
      import logoItau from '@/assets/logo-bancos/logo-itau.svg'
      import logoNubank from '@/assets/logo-bancos/logo-nubank.png'
      import logoSantander from '@/assets/logo-bancos/logo-santander.svg'
  </script>
    
  <style scoped>
    .billing-container {
      margin-top: 160px;
    }
    .prices-container {
      max-width: 425px;
    }
    .button-cupom {
      background-color: #DBB671;
      color: #FFF;
      padding: 16px 48px;
      border-radius: 4px;
    }
  </style>
    