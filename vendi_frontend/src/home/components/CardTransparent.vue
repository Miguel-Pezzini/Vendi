<template>
  <v-col cols="6" class="container d-flex justify-end align-center">
    <div class="image-wrapper">
      <img class="img" :src="image" />
    </div>
    <div class="content">
      <h3 v-if="$vuetify.display.smAndDown" class="ma-0">{{ title }}</h3>
      <h1 v-else>{{ title }}</h1>
    </div>
  </v-col>
</template>

<script setup>
  defineProps({
    image: String,
    title: String,
  })
</script>

<style scoped>
  .image-wrapper {
    position: relative;
    display: block;
    width: 100%;
    height: 350px;
    overflow: hidden;
  }

  .container {
    position: relative;
  }

  .container:hover {
    transition: 0.2s;
    scale: 1.01;
    cursor: pointer;
  }

  .img {
    width: 100%;
    height: 100%;
    object-fit: cover; /* preenche sem distorcer */
    border-radius: 10px;
    z-index: 1;
  }

  .image-wrapper::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    border-radius: 10px;
    background-color: rgba(0, 0, 0, 0.6);
    z-index: 0;
  }

  .content {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: white;
  }

  @media (max-width: 960px) {
    .image-wrapper {
      height: 170px; /* altura reduzida no mobile */
    }
  }
</style>
