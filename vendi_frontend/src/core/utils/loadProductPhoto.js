import api from "../plugins/api";

export default async function loadProductPhoto(mainPhotoId) {
    return await api.get("photo", mainPhotoId)
  }
  