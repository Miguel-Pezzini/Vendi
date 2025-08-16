<template>
  <v-row class="bg-black text-white py-10 px-12 px-sm-4" align="start" justify="center">
    <v-col cols="12" md="3" class="d-flex flex-column gap-2 text-center text-md-start">
      <h1 class="text-h6 font-weight-bold">Categorias</h1>
      <p class="text-body-2">
        Casa inteligente • Interruptores • Tomadas • Relé • Hubs • Sensores • Lâmpadas • Controles
      </p>
    </v-col>

    <v-col cols="12" md="3" class="d-flex flex-column gap-2 text-center text-md-start">
      <h1 class="text-h6 font-weight-bold">Institucional</h1>
      <p class="text-body-2">
        Fale Conosco • Política de Privacidade • Quem Somos • Troca e Devolução
      </p>
    </v-col>

    <v-col cols="12" md="2" class="d-flex flex-column gap-2 text-center text-md-start">
      <h1 class="text-h6 font-weight-bold">Contatos</h1>
      <p class="text-body-2">
        Telefone: +55 XX XXXX-XXXX<br />
        Whatsapp: +55 XX XXXXX-XXXX<br />
        E-mail: xxxxxxxx@xxxxxxx.xxx
      </p>
    </v-col>

    <v-col cols="12" md="2" class="d-flex flex-column gap-2 text-center text-md-start">
      <h1 class="text-h6 font-weight-bold">Atendimento</h1>
      <p class="text-body-2">Seg à Sáb das 8h00 às 18h00</p>
    </v-col>
  </v-row>

  <v-row class="bg-black text-white pb-8" justify="center" no-gutters>
    <v-divider color="white" opacity="1" class="mb-4" />

    <v-row class="justify-space-evenly flex-wrap gap-2">
      <v-btn class="hover-scale" icon="mdi-facebook" color="black" />
      <v-btn class="hover-scale" icon="mdi-twitter" color="black" />
      <v-btn class="hover-scale" icon="mdi-instagram" color="black" />
      <v-btn class="hover-scale" icon="mdi-youtube" color="black" />
      <v-btn class="hover-scale" icon="mdi-pinterest" color="black" />
    </v-row>

    <v-divider color="white" opacity="1" class="mt-4" />
  </v-row>
</template>

<style scoped>
  .hover-scale:hover {
    transition: transform 0.2s ease;
    transform: scale(1.1);
  }
</style>
