export default (context) => [
  {
    isRouterLink: true,
    to: '/wishlist',
    tooltip: 'Wish List',
    value: 'WISHLIST',
    icon: () => (context.wishListActive ? 'mdi-heart' : 'mdi-heart-outline'),
    color: () => (context.wishListActive ? 'golden' : null),
  },
  {
    isRouterLink: true,
    to: '/orders',
    tooltip: 'My Orders',
    value: 'ORDERS',
    icon: 'mdi-shopping-outline',
    color: () => (context.shoppingActive ? 'golden' : null),
  },
  {
    isRouterLink: false,
    tooltip: 'Cart',
    value: 'CART',
    icon: 'mdi-cart-outline',
    color: () => (context.showCart.value ? 'golden' : null),
    onClick: () => (context.showCart.value = !context.showCart.value),
  },
  {
    isRouterLink: true,
    value: 'ACCOUNT',
    to: () => (context.isAdmin ? '/admin' : '/profile'),
    icon: () => (context.isAdmin ? context.adminIcon : context.accountIcon),
    tooltip: 'My Account',
    color: () => (context.accountActive ? 'golden' : 'black'),
  },
]
