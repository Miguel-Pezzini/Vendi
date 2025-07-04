import { createRouter, createWebHistory } from 'vue-router'
import Register from '@/authenticate/views/Register.vue'
import Login from '@/authenticate/views/Login.vue'
import Home from '@/home/views/Home.vue'
import Profile from '@/user/profile/views/Profile.vue'
import Products from '@/store/views/Products.vue'
import Product from '@/product/views/Product.vue'
import Cart from '@/cart/views/Cart.vue'
import WishList from '@/wishlist/views/WishList.vue'
import Checkout from '@/checkout/views/Checkout.vue'
import Admin from '@/admin/view/Admin.vue'
import UserProducts from '@/user/products/view/UserProducts.vue'
import CreateProduct from '@/user/products/view/AddProduct.vue'
import EditProduct from '@/user/products/view/EditProduct.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/register',
      name: 'Register',
      component: Register,
    },
    {
      path: '/',
      name: 'Login',
      component: Login,
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
    },
    {
      path: '/user/products',
      name: 'User Products',
      component: UserProducts,
    },
    {
      path: '/user/products/:id',
      name: 'Edit Product',
      component: EditProduct,
      props: true,
    },
    {
      path: '/user/products/create',
      name: 'Create Product',
      component: CreateProduct,
    },
    {
      path: '/store',
      name: 'Store',
      component: Products,
    },
    {
      path: '/cart',
      name: 'Cart',
      component: Cart,
    },
    {
      path: '/product/:productId',
      name: 'Product',
      component: Product,
      props: true,
    },
    {
      path: '/wishlist',
      name: 'Wishlist',
      component: WishList,
    },
    {
      path: '/checkout',
      name: 'Checkout',
      component: Checkout,
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
    },
  ],
})

// router.beforeEach((to, from, next) => {
//   const token = localStorage.getItem('jwtToken');

//   if (to.meta.requiresAuth && !token) {
//     next({ path: '/login' });
//   } else {
//     next();
//   }
// });

export default router
