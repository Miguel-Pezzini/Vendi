import api from '../plugins/api'
import imageService from './imageService'

async function getPhotoData(photoId) {
  const res = await api.get('photo', photoId)
  return res.data
}

async function loadProduct(productId) {
  let product = await api.get('/product', productId)
  product.mainPhoto = { data: await getPhotoData(product.mainPhoto.id), ...product.mainPhoto }
  return product
}

async function loadProductDetails(productId) {
  const product = await api.get(`/product/${productId}/details`)

  const mainPhoto = product.photos.find((photo) => photo.isMainPhoto)
  product.mainPhoto = {
    ...mainPhoto,
    data: await getPhotoData(mainPhoto.id),
  }

  const photos = await Promise.all(
    product.photos
      .filter((photo) => !photo.isMainPhoto)
      .map(async (photo) => ({
        ...photo,
        data: await getPhotoData(photo.id),
      }))
  )

  product.photos = photos

  return product
}

async function loadProductDetailsPhotosToFile(productId) {
  const product = await api.get(`/product/${productId}/details`)

  let mainPhoto = product.photos.find((photo) => photo.isMainPhoto)
  mainPhoto = {
    ...mainPhoto,
    data: await getPhotoData(mainPhoto.id),
  }
  product.mainPhoto = imageService.base64ToFile(mainPhoto)

  const photos = await Promise.all(
    product.photos
      .filter((photo) => !photo.isMainPhoto)
      .map(async (photo) =>
        imageService.base64ToFile({
          ...photo,
          data: await getPhotoData(photo.id),
        })
      )
  )

  product.photos = photos

  return product
}

async function loadProducts(recurso) {
  const products = await api.getAll(recurso)

  await Promise.all(
    products.map(async (product) => {
      const photo = await getPhotoData(product.mainPhoto.id)
      product.mainPhoto = { ...product.mainPhoto, data: photo }
    })
  )

  return products
}

const productService = {
  loadProduct,
  loadProductDetails,
  loadProducts,
  loadProductDetailsPhotosToFile,
}

export default productService
