import { createRouter, createWebHistory } from 'vue-router'
import Register from '@/pages/Register.vue'
import Login from '@/pages/Login.vue'
import Home from '@/pages/Home.vue'
import Profile from '@/pages/Profile.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    }
  ]
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