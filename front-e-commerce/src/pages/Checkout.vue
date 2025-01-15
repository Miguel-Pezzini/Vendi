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
        <v-col cols="6">
            <h1 class="mb-12">Detalhes da Compra</h1>
            <div style="max-width: 470px;">
                <v-form>
                <Input variant="outlined" label="Primeiro Nome" required />
                <Input  variant="outlined" label="Nome da Empresa" />
                <Input  variant="outlined" label="Endereço" required />
                <Input variant="outlined"  label="Apartamento, casa, etc. (Opcional)" />
                <Input  variant="outlined" label="Cidade" required />
                <Input  variant="outlined" label="Telefone" required />
                <Input  variant="outlined" label="E-mail" required />
                <v-checkbox label="Salvar esses dados para Transações futuras"></v-checkbox>
            </v-form>
            </div>
           
        </v-col>
        <v-col  cols="6">
            a
        </v-col>
        
      </v-row>
    </v-container>
    
      
   </div>
  
   <Footer />
</template>
  
<script setup>
    import { ref, onMounted } from 'vue';
    import { useRoute } from 'vue-router';
  import Header from '@/core/Header.vue';
  import Path from '@/core/Path.vue';
  import Input from '@/core/Input.vue';
  import Footer from '@/core/Footer.vue';

  import loadPastPaths from '@/utils/loadPastPaths';

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
</style>
  