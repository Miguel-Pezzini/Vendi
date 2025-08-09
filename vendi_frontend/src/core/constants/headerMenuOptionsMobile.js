export default (context) => [
  {
    to: '/wishlist',
    title: 'Wish List',
    prependIcon: context.wishListActive ? 'mdi-heart' : 'mdi-heart-outline',
    color: context.wishListActive ? 'golden' : 'black',
  },
  {
    to: '/orders',
    title: 'My Orders',
    prependIcon: 'mdi-shopping-outline',
    color: context.shoppingActive ? 'golden' : 'black',
  },
  {
    title: 'Cart',
    to: '/cart',
    color: context.cartActive ? 'golden' : 'black',
    prependIcon: 'mdi-cart-outline',
  },
  {
    to: context.isAdmin ? '/admin' : '/profile',
    prependIcon: context.isAdmin ? context.adminIcon : context.accountIcon,
    title: 'My Account',
    color: context.accountActive ? 'golden' : 'black',
  },
]
