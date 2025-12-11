console.log("post-detail.js loaded");

// 삭제 버튼 confirm 처리
document.addEventListener("DOMContentLoaded", () => {
    const deleteForm = document.querySelector(".delete-form");
    if (!deleteForm) return;

    deleteForm.addEventListener("submit", (e) => {
        const ok = confirm("정말 삭제할까요?");
        if (!ok) {
            e.preventDefault();
        }
    });
});
