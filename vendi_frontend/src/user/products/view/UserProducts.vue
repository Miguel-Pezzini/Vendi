<template>
  <Header account-active="true" />
  <v-divider />
  <v-container class="container">
    <v-row>
      <Path
        :old-paths="loadPastPaths(route)"
        active-path="My Products"
      />
      <v-spacer />
      <Button
        title="Create Product"
        variant="outlined"
        color="#DBB671"
      />
    </v-row>
  
    <v-row class="container margin">
      <v-col
        class="pa-0"
        cols="4"
      >
        <SideMenu active-minha-conta="true" />
      </v-col>
      <v-row>
        <v-row>
          <UserProduct
            :product="prod1"
          />
        </v-row>
      </v-row>
    </v-row>
  </v-container>
  <Footer />
</template>
    
  <script setup>
     import card1 from '@/assets/card1.webp'
      import Header from '@/core/components/Header.vue';
      import Path from '@/core/components/Path.vue';
      import SideMenu from '@/user/profile/components/SideMenu.vue';
      import Footer from '@/core/components/Footer.vue';
      import Button from '@/core/components/Button.vue';
      import { useRoute } from 'vue-router';
      import UserProduct from '../components/UserProduct.vue';
      import loadPastPaths from '@/core/utils/loadPastPaths';
      import { ref } from 'vue';
  
      const route = useRoute();

      const prod1 = ref({
        discount: 35,
        name: "Laptop",
        price: "960",
        fullPrice: "1160",
        isInWishList: false,
        quantity: 10,
        image: card1
        })
  
     
  </script>
    
    <style scoped>
    .container {
      margin-top: 80px;
    }
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
    .margin {
      margin-bottom: 146px;
    }
    </style>
    