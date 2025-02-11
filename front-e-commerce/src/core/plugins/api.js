import axios from "axios";

const BASE_URL = 'http://localhost:8080/'

const server = axios.create({
    baseURL: BASE_URL,
    timeout: 1000,
    headers: {"Content-Type": "application/json" }
});



async function carregar(resource, data) {
    const response = await server.post(`${BASE_URL}${resource}`, data);

    return response.data
}

const api = {
    carregar,
};

export default api;