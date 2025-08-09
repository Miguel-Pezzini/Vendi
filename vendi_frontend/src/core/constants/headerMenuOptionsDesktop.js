export default (context) => [
  {
    showOn: ({ mdAndUp }) => mdAndUp,
    isRouterLink: true,
    to: '/wishlist',
    tooltip: 'Wish List',
    icon: () => (context.wishListActive ? 'mdi-heart' : 'mdi-heart-outline'),
    color: () => (context.wishListActive ? 'golden' : null),
  },
  {
    showOn: ({ mdAndUp }) => mdAndUp,
    isRouterLink: true,
    to: '/orders',
    tooltip: 'My Orders',
    icon: 'mdi-shopping-outline',
    color: context.shoppingActive ? 'golden' : null,
  },
  {
    showOn: () => true, // sempre visível
    isRouterLink: false,
    tooltip: 'Cart',
    icon: 'mdi-cart-outline',
    color: context.showCart.value ? 'golden' : null,
    onClick: () => (context.showCart.value = !context.showCart.value),
  },
  {
    showOn: ({ smAndDown }) => smAndDown,
    isRouterLink: false,
    icon: () => (context.showMenu.value ? 'mdi-close' : 'mdi-menu'),
    color: context.showMenu.value ? 'golden' : null,
    onClick: () => (context.showMenu.value = !context.showMenu.value),
  },
  {
    showOn: ({ mdAndUp }) => mdAndUp,
    isRouterLink: true,
    to: () => (context.isAdmin ? '/admin' : '/profile'),
    icon: () => (context.isAdmin ? context.adminIcon : context.accountIcon),
    tooltip: 'My Account',
    color: context.accountActive ? 'golden' : 'black',
  },
  {
    showOn: ({ mdAndUp }) => mdAndUp,
    isRouterLink: true,
    to: '/',
    tooltip: 'Logout',
    icon: 'mdi-logout',
    color: 'red',
  },
]
