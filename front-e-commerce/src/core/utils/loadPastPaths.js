import { useRoute } from 'vue-router';

export default function loadPastPaths() {
    const route = useRoute()
    const queryParam = route.query.origin;

    return queryParam
}