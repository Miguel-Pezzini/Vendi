import api from "../plugins/api";

export default async function loadProductPhoto(photoId) {
  const res = await api.get("photo", photoId);
  return res.data;
}