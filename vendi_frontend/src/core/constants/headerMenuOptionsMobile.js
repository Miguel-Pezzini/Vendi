export default (context) => [
  {
    isRouterLink: true,
    to: '/wishlist',
    title: 'Wish List',
    value: 'WISHLIST',
    icon: () => (context.wishListActive ? 'mdi-heart' : 'mdi-heart-outline'),
    color: () => (context.wishListActive ? 'golden' : null),
  },
  {
    isRouterLink: true,
    to: '/orders',
    title: 'My Orders',
    value: 'ORDERS',
    icon: 'mdi-shopping-outline',
    color: () => (context.shoppingActive ? 'golden' : null),
  },
  {
    isRouterLink: false,
    title: 'Cart',
    to: '/cart',
    value: 'CART',
    icon: 'mdi-cart-outline',
  },
  {
    isRouterLink: true,
    value: 'ACCOUNT',
    to: () => (context.isAdmin ? '/admin' : '/profile'),
    icon: () => (context.isAdmin ? context.adminIcon : context.accountIcon),
    title: 'My Account',
    color: () => (context.accountActive ? 'golden' : 'black'),
  },
]
