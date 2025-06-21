import api from '../plugins/api'

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

const productService = {
  loadProduct,
  loadProductDetails,
}

export default productService
