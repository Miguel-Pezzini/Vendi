function fileToDataBase64(fileOrBlob) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(fileOrBlob)
    reader.onload = () => {
      const result = reader.result
      const base64 = result.split(',')[1]
      resolve(base64)
    }
    reader.onerror = (error) => reject(error)
  })
}

function base64ToFile(dataURI, filename = 'photo.jpg') {
  const [header, base64] = dataURI.split(',')
  const contentTypeMatch = header.match(/data:(.*);base64/)
  const contentType = contentTypeMatch ? contentTypeMatch[1] : 'application/octet-stream'

  const byteString = atob(base64)
  const byteArray = new Uint8Array(byteString.length)

  for (let i = 0; i < byteString.length; i++) {
    byteArray[i] = byteString.charCodeAt(i)
  }

  return new File([byteArray], filename, { type: contentType })
}

const imageService = {
  fileToDataBase64,
  base64ToFile,
}
export default imageService
