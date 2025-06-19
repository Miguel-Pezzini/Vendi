export default function mostrarPrice(price) {
  let priceFormatado = price.toFixed(2)

  return priceFormatado.replace('.', ',')
}
