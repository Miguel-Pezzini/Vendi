<template>
  <v-col
    cols="auto"
    class="container d-flex justify-end align-center"
  >
    <!-- Div com fundo cinza e opacidade -->
    <div class="image-wrapper">
      <img
        class="img"
        :src="image"
      >
    </div>
    <div class="content">
      <h1>{{ title }}</h1>
    </div>
  </v-col>
</template>
  
  <script setup>
  defineProps({
    image: {
      type: String,
      default: null
    },
    title: {
      type: String,
      default: null,
    }
  })
  </script>
  
  <style scoped>
  body {
    font-family: "Poppins", serif;
  }
  .image-wrapper {
  position: relative;
  display: inline-block;
  }
  .container {
    position: relative;
  }
  .container:hover {
    transition: .2s;
    scale: 1.01;
    cursor: pointer;
  }
  .img {
    object-fit: cover;
    border-radius: 10px;
    height: 350px;
    width: 650px;
    z-index: 1;
  }
  .image-wrapper::before {
    height: 350px;
    width: 650px;
    object-fit: cover;
    border-radius: 10px;
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.6); /* Cinza com opacidade de 30% */
    z-index: 0;
}
  .content {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    text-align: center;
    color: white; /* A cor do texto pode ser alterada conforme necessário */
  }
  h1 {
    margin: 0;
  }
  </style>
  