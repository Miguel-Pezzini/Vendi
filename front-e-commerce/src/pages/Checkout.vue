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
      <v-row>
        a
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
  