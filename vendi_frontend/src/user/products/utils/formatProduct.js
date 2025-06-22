export default function formatDetailsProduct(product) {
  const mainPhoto = product.find((product) => product.mainPhoto)

  return {
    name: product.name,
    price: product.price,
    quantity: product.quantity,
    installment: product.installment,
    discount: product.discount,
    categories: product.categories,
  }
}
