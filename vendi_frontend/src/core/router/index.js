import { createRouter, createWebHistory } from 'vue-router'
import Register from '@/authenticate/views/Register.vue'
import Login from '@/authenticate/views/Login.vue'
import Home from '@/home/views/Home.vue'
import MyAccount from '@/user/profile/views/MyAccount.vue'
import MyAddresses from '@/user/profile/views/MyAddresses.vue'
import Products from '@/store/views/StoreView.vue'
import Product from '@/product/views/Product.vue'
import Cart from '@/cart/views/Cart.vue'
import WishList from '@/wishlist/views/WishList.vue'
import Checkout from '@/checkout/views/Checkout.vue'
import Admin from '@/admin/view/Admin.vue'
import UserProducts from '@/user/products/view/UserProducts.vue'
import CreateProduct from '@/user/products/view/AddProduct.vue'
import EditProduct from '@/user/products/view/EditProduct.vue'
import { createNavigationGuard } from './navigationGuard'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/register',
      name: 'Register',
      component: Register,
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
    },
    {
      path: '/account',
      name: 'Account',
      component: MyAccount,
      meta: { requiresAuth: true },
    },
    {
      path: '/account/addresses',
      name: 'Addresses',
      component: MyAddresses,
      meta: { requiresAuth: true },
    },
    {
      path: '/user/products',
      name: 'User Products',
      component: UserProducts,
      meta: { requiresAuth: true },
    },
    {
      path: '/user/products/create',
      name: 'Create Product',
      component: CreateProduct,
      meta: { requiresAuth: true, requiresAdmin: true },
    },
    {
      path: '/user/products/:id',
      name: 'Edit Product',
      component: EditProduct,
      props: true,
      meta: { requiresAuth: true, requiresAdmin: true },
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
      meta: { requiresAuth: true },
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
      meta: { requiresAuth: true },
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      meta: { requiresAuth: true, requiresAdmin: true },
    },
  ],
})

router.beforeEach(createNavigationGuard())

export default router
