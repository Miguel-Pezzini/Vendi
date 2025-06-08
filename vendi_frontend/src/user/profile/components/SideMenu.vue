<template>
  <ul class="d-flex flex-column ga-4">
    <div class="d-flex flex-column ga-3">
      <li><RouterLink><h1>Manage my account</h1></RouterLink></li>
      <div class="d-flex flex-column ga-2">
        <li>
          <RouterLink>
            <p :style="{color: activeMinhaConta ? '#DBB671' : black}">
              My Account
            </p>
          </RouterLink>
        </li>
        <li><RouterLink><p>My payment forms</p></RouterLink></li>
      </div>
    </div>
            
    <div class="d-flex flex-column ga-3">
      <li><RouterLink><h1>My Orders</h1></RouterLink></li>
      <div class="d-flex flex-column ga-2">
        <li><RouterLink><p>Returns</p></RouterLink></li>
        <li><RouterLink><p>My cancellations</p></RouterLink></li>
      </div>
    </div>
        
    <li>
      <RouterLink to="/wishlist">
        <h1>Wishlist</h1>
      </RouterLink>
    </li>
    <li>
      <RouterLink to="/user/products">
        <h1>My products</h1>
      </RouterLink>
    </li>
  </ul>
</template>
    
  <script setup>
  defineProps({
    activeMinhaConta: {
        type: Boolean,
        default: false
    }
  })
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
        opacity: 0.5;
    }
    
    </style>
    