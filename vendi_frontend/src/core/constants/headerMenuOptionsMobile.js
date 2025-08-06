export default (context) => [
    {
      isRouterLink: true,
      to: '/wishlist',
      tooltip: 'Wish List',
      icon: () => (context.wishListActive ? 'mdi-heart' : 'mdi-heart-outline'),
      color: () => (context.wishListActive ? 'golden' : null),
    },
    {
      isRouterLink: true,
      to: '/orders',
      tooltip: 'My Orders',
      icon: 'mdi-shopping-outline',
      color: () => (context.shoppingActive ? 'golden' : null),
    },
    {
      isRouterLink: false,
      tooltip: 'Cart',
      icon: 'mdi-cart-outline',
      color: () => (context.showCart.value ? 'golden' : null),
      onClick: () => (context.showCart.value = !context.showCart.value),
    },
    {
      isRouterLink: true,
      to: () => (context.isAdmin ? '/admin' : '/profile'),
      icon: () => (context.isAdmin ? context.adminIcon : context.accountIcon),
      tooltip: 'My Account',
      color: () => (context.accountActive ? 'golden' : 'black'),
    },
    {
      isRouterLink: true,
      to: '/',
      tooltip: 'Logout',
      icon: 'mdi-logout',
      color: 'red',
    },
];