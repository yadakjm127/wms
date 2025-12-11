// 좋아요 토글(프론트 임시 효과)
function toggleLike(event) {
    const btn = event.target.closest('.like-btn, .like-button');
    if (!btn) return;
    btn.classList.toggle('liked');

    const countEl = btn.querySelector('.like-count');
    if (countEl) {
        const curr = parseInt(countEl.innerText || '0', 10);
        countEl.innerText = String(curr + (btn.classList.contains('liked') ? 1 : -1));
    }
}

// 북마크(추후 서버 연동)
function bookmarkPost(event) {
    alert("북마크 기능은 추후 구현 예정입니다.");
}

// 삭제 확인(폼 submit 버튼에 onClick으로 연결 가능)
function confirmDelete(e, message = "정말 삭제할까요?") {
    if (!confirm(message)) {
        e.preventDefault?.();
        return false;
    }
    return true;
}
