<template>
  <v-card>
    <v-form class="rounded">
      <v-row>
        <h1 class="title">
          Add a Product
        </h1>
      </v-row>
      <v-row>
        <Input
          variant="outlined"
          label="Nome"
        />
      </v-row>
      <v-row class="ga-4">
        <Input
          variant="outlined"
          label="E-mail"
        />
        <Input
          variant="outlined"
          label="Endereco"
        />
      </v-row>
      <v-row>
        <h1 class="title">
          Mudança de Senha
        </h1>
      </v-row>
      <v-row>
        <Input
          variant="outlined"
          label="Senha Atual"
        />
      </v-row>
      <v-row>
        <Input
          variant="outlined"
          label="Nova Senha"
        />
      </v-row>
      <v-row>
        <Input
          variant="outlined"
          label="Confirmar Nova Senha"
        />
      </v-row>
      <v-row>
        <v-spacer />
        <Button
          title="CANCELAR"
          variant="text"
        />
        <Button
          title="SALVAR MUDANçAS"
          bg-color="#DBB671"
        />
      </v-row>
    </v-form>
  </v-card>
</template>
      
    <script setup>
        import Input from '@/core/components/Input.vue';
        import Button from '@/core/components/Button.vue';
    
       
    </script>
      
      <style scoped>
      .v-card {
        padding: 40px 80px 40px 80px;
        box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
      }
      .title {
        padding-bottom: 16px;
        color: #DBB671;
        font-size: 20px;
        font-weight: 500;
      }
      </style>
      