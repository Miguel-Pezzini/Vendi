import api from '../plugins/api'
import imageService from './imageService'

async function getPhotoData(photoId) {
  const res = await api.get('photo', photoId)
  return res.dataURI
}

function enrichSummaryProduct(product, dataURI = null) {
  const image = dataURI || product.image || null
  const fullPrice =
    product.discount && product.discount > 0
      ? Number((product.price / (1 - product.discount / 100)).toFixed(2))
      : null

  return {
    ...product,
    image,
    fullPrice,
    mainPhoto: product.mainPhoto ? { ...product.mainPhoto, dataURI: image } : product.mainPhoto,
  }
}

async function hydrateProductSummary(product) {
  if (!product?.mainPhoto?.id) {
    return enrichSummaryProduct(product)
  }

  const dataURI = await getPhotoData(product.mainPhoto.id)
  return enrichSummaryProduct(product, dataURI)
}

async function loadProduct(productId) {
  const product = await api.get('products', productId)
  return hydrateProductSummary(product)
}

async function loadProductDetails(productId) {
  const product = await api.get(`products/${productId}/details`)

  const mainPhoto = product.photos.find((photo) => photo.isMainPhoto)
  product.mainPhoto = {
    ...mainPhoto,
    dataURI: await getPhotoData(mainPhoto.id),
  }

  const photos = await Promise.all(
    product.photos
      .filter((photo) => !photo.isMainPhoto)
      .map(async (photo) => ({
        ...photo,
        dataURI: await getPhotoData(photo.id),
      }))
  )

  product.photos = photos

  return product
}

async function loadProductDetailsPhotosToFile(productId) {
  const product = await api.get(`products/${productId}/details`)

  const mainPhoto = product.photos.find((photo) => photo.isMainPhoto)
  product.mainPhoto = imageService.base64ToFile(
    await getPhotoData(mainPhoto.id),
    mainPhoto.filename
  )

  const photos = await Promise.all(
    product.photos
      .filter((photo) => !photo.isMainPhoto)
      .map(async (photo) => imageService.base64ToFile(await getPhotoData(photo.id), photo.filename))
  )

  product.photos = photos

  return product
}

async function loadProducts(resource = 'products', params = {}) {
  const products = await api.getAll(resource, params)
  return Promise.all(products.map(hydrateProductSummary))
}

const productService = {
  loadProduct,
  loadProductDetails,
  loadProducts,
  loadProductDetailsPhotosToFile,
  hydrateProductSummary,
}

export default productService
