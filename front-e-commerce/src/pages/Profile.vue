<template>
  <Header account-active="true" />
  <v-divider />
  <v-container class="container">
    <v-row>
      <Path
        :old-paths="['Home']"
        active-path="Minha Conta"
      />
      <v-spacer />
      <p>Bem-Vindo! <span style="color: #DBB671;">Your name</span></p>
    </v-row>

    <v-row class="container margin">
      <v-col
        class="pa-0"
        cols="4"
      >
        <SideMenu active-minha-conta="true" />
      </v-col>

      <v-col>
        <v-card>
          <v-form class="rounded">
            <v-row>
              <h1 class="title">
                Edite Seu Perfil
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
      </v-col>
    </v-row>
  </v-container>
  <Footer />
</template>
  
<script setup>
    import Header from '@/components/Header.vue';
    import Path from '@/components/Path.vue';
    import SideMenu from '@/components/SideMenu.vue';
    import Input from '@/components/Input.vue';
    import Button from '@/components/Button.vue';
    import Footer from '@/components/Footer.vue';
</script>
  
  <style scoped>
  body {
    font-family: "Poppins", serif;
  }
  .container {
    margin-top: 80px;
  }
  .v-card {
    font-family: "Poppins", serif;
    padding: 40px 80px 40px 80px;
    box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 0px 1px;
  }
  .title {
    padding-bottom: 16px;
    color: #DBB671;
    font-size: 20px;
    font-weight: 500;
  }
  .margin {
    margin-bottom: 146px;
  }
  </style>
  