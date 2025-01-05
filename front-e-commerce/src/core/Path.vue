<template>
  <div class="d-flex">
    <p
      v-for="oldP in oldPaths"
      :key="oldP"
      style="opacity: 0.5;"
    >
&nbsp;&nbsp;{{ oldP }}&nbsp;&nbsp; /
    </p>
    <span>&nbsp;&nbsp; {{ activePath }}</span>
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
      