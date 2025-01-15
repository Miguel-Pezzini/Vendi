<template>
  <div class="d-flex">
    <RouterLink
      v-for="oldP in oldPaths"
      :key="oldP"
      :to="`/${oldP}`"
      style="opacity: 0.5;"
    >
&nbsp;&nbsp;{{ oldP }}&nbsp;&nbsp; /
    </RouterLink>
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
  a {
    text-decoration: none;
    color: #000;
  }
  </style>
      