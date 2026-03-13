<template>
  <ul class="d-flex flex-column ga-4">
    <div v-for="item in menu" :key="item.title" class="d-flex flex-column ga-3">
      <li>
        <RouterLink :to="item.to">
          <h1 :class="item.marked ? 'text-golden' : 'text-black'">
            {{ item.title }}
          </h1>
        </RouterLink>
      </li>

      <div
        v-for="subMenu in item.subMenus"
        :key="subMenu.title"
        class="d-flex flex-column ga-2">
        <li>
          <RouterLink :to="subMenu.to">
            <p :class="subMenu.marked ? 'text-golden' : 'text-grey-darken-1'">
              {{ subMenu.title }}
            </p>
          </RouterLink>
        </li>
      </div>
    </div>
  </ul>
</template>

<script setup>
  const props = defineProps({
    activeDashboard: {
      type: Boolean,
      default: false,
    },
  })

  const menu = [
    {
      title: 'Admin Dashboard',
      to: '/admin',
      marked: props.activeDashboard,
      subMenus: [],
    },
    {
      title: 'Produtos',
      to: '/user/products',
      subMenus: [
        {
          title: 'Criar Produto',
          to: '/user/products/create',
        },
      ],
    },
  ]
</script>

<style scoped>
  a {
    text-decoration: none;
    color: black;
  }
  ul {
    list-style: none;
  }
  h1 {
    font-size: 16px;
    font-weight: 500;
  }
  p {
    margin-left: 35px;
    font-size: 16px;
  }
</style>
