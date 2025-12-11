// CSRF 메타에서 헤더 키/값을 읽어 fetch에 바로 펼칠 수 있는 객체로 반환
// 사용: fetch(url, { headers: { 'Content-Type':'...', ...getCsrfHeaders() } })
function getCsrfHeaders() {
    const token = document.querySelector('meta[name="_csrf"]')?.content;
    const header = document.querySelector('meta[name="_csrf_header"]')?.content;
    if (token && header) {
        return { [header]: token };
    }
    return {};
}
