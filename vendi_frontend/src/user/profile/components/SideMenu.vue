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
      <div v-for="subMenu in item.subMenus" :key="subMenu.title" class="d-flex flex-column ga-2">
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
    activeMyAccount: {
      type: Boolean,
      default: false,
    },
    activeMyProducts: {
      type: Boolean,
      default: false,
    },
    activeAddProduct: {
      type: Boolean,
      default: false,
    },
  })

  const menu = [
    {
      title: 'Manage my account',
      to: '/#',
      subMenus: [
        {
          title: 'My Account',
          to: '/profile',
          marked: props.activeMyAccount,
        },
        {
          title: 'My Payment Forms',
          to: '/#',
        },
      ],
    },
    {
      title: 'My Orders',
      to: '/#',
      subMenus: [
        {
          title: 'Returns',
          to: '/#',
        },
        {
          title: 'My Cancellations',
          to: '/#',
        },
      ],
    },
    {
      title: 'Wishlist',
      to: '/#',
    },
    {
      title: 'My Products',
      to: '/user/products',
      marked: props.activeMyProducts,
      subMenus: [
        {
          title: 'Add Product',
          to: '/user/products/create',
          marked: props.activeAddProduct,
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
