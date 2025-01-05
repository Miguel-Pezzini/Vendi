<template>
  <v-row class="footer">
    <v-col cols="3">
      <h1 class="title-footer">
        Categorias
      </h1>
      <p class="text-footer">
        Casa inteligente • Interruptores • Tomadas • Relé • Hubs • Sensores • Lâmpadas • Controles
      </p>
    </v-col>
    <v-col cols="3">
      <h1 class="title-footer">
        Institucional
      </h1>
      <p class="text-footer">
        Fale Conosco • Política de Privacidade • Quem Somos • Troca e Devolução
      </p>
    </v-col>
    <v-col cols="2">
      <h1 class="title-footer">
        Contatos
      </h1>
      <p class="text-footer">
        Telefone: +55 XX XXXX-XXXX<br>
        Whatsapp: +55 XX XXXXX-XXXX<br>
        E-mail: xxxxxxxx@xxxxxxx.xxx
      </p>
    </v-col>
    <v-col cols="2">
      <h1 class="title-footer">
        Atendimento
      </h1>
      <p class="text-footer">
        Seg à Sáb das 8h00 às 18h00
      </p>
    </v-col>
  </v-row>
  <v-row class="footer-icons justify-center align-center">
    <v-divider
      class="divider-footer"
      opacity="1"
      color="#FFFFFF"
    />
    <v-row class="row-icons justify-space-evenly">
      <v-btn
        icon="mdi-facebook"
        color="black"
      />
      <v-btn
        icon="mdi-twitter"
        color="black"
      />
      <v-btn
        icon="mdi-instagram"
        color="black"
      />
      <v-btn
        icon="mdi-youtube"
        color="black"
      />
      <v-btn
        icon="mdi-pinterest"
        color="black"
      />
    </v-row>
    <v-divider
      class="divider-footer"
      opacity="1"
      color="#FFFFFF"
    />
  </v-row>
</template>
        
      <script setup>
      
  
  defineProps({
    
  })
      </script>
  <style scoped>
  body {
    font-family: "Poppins", serif;
  }
  .footer {
    padding-top: 36px;
    padding-bottom: 69px;
    padding-left: 100px;
    gap: 20px;
    color: #FFF;
    background-color: black;
  }
  .title-footer {
    font-size: 18px;
    font-weight: 600;
  }
  .text-footer {
    font-size: 14px;
    font-weight: 400;
    line-height: 26px;
  }
  .footer-icons {
    color: #FFF;
    background-color: black;
    padding-bottom: 50px;
  }
  .divider-footer {
    flex: auto;
  }

  </style>