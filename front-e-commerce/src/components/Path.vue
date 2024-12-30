<template>
    <div class="d-flex">
        <p style="opacity: 0.5;" v-for="oldP in oldPaths" :key="oldP">&nbsp;&nbsp;{{oldP}}&nbsp;&nbsp; /</p>
        <span>&nbsp;&nbsp; {{activePath}}</span>
    </div>
</template>
      
  <script setup>
      defineProps({
          oldPaths: {
              type: Array,
              default: null
          },
          activePath:{
            type: String,
            default: null
          }
      })
  </script>
      
  <style scoped>
  body {
    font-family: "Poppins", serif;
    font-size: 14px;
  }
  </style>
      