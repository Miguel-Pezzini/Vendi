<template>
    <Header/>
    <Path :oldPaths="['Home']" activePath="Minha Conta" />
</template>
  
<script setup>
    import Header from '@/components/Header.vue';
    import Path from '@/components/Path.vue';
</script>
  
  <style scoped>
  body {
    font-family: "Poppins", serif;
  }
  </style>
  