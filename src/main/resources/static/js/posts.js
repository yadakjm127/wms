// posts.js
// 게시판 목록 / 좋아요 / 북마크 통합 스크립트

// 좋아요 토글
async function toggleLike(postId, el) {
  const token = document.querySelector('meta[name="_csrf"]')?.content;
  const header = document.querySelector('meta[name="_csrf_header"]')?.content;

  try {
    const res = await fetch(`/api/posts/${postId}/like`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        [header]: token
      }
    });

    if (!res.ok) {
      alert('좋아요 요청 실패: 서버 오류');
      return;
    }

    const data = await res.json();

    // 상태 반영
    el.classList.toggle('liked', data.liked);
    el.querySelector('.like-count').textContent = data.count;

  } catch (err) {
    console.error('좋아요 처리 중 오류:', err);
    alert('좋아요 처리 중 오류가 발생했습니다.');
  }
}

// 북마크 토글
async function toggleBookmark(postId, el) {
  const token = document.querySelector('meta[name="_csrf"]')?.content;
  const header = document.querySelector('meta[name="_csrf_header"]')?.content;

  try {
    const res = await fetch(`/api/posts/${postId}/bookmark`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        [header]: token
      }
    });

    if (!res.ok) {
      alert('북마크 요청 실패: 서버 오류');
      return;
    }

    const data = await res.json();
    el.classList.toggle('bookmarked', data.bookmarked);
    el.querySelector('.bookmark-count').textContent = data.count;

  } catch (err) {
    console.error('북마크 처리 중 오류:', err);
    alert('북마크 처리 중 오류가 발생했습니다.');
  }
}

// 삭제 확인
function confirmDelete(e) {
  if (!confirm("정말 삭제하시겠습니까?")) {
    e.preventDefault();
  }
}

// 초기화
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.like-btn').forEach(btn => {
    btn.addEventListener('click', e => {
      e.preventDefault();
      const postId = btn.dataset.postId;
      toggleLike(postId, btn);
    });
  });

  document.querySelectorAll('.bookmark-btn').forEach(btn => {
    btn.addEventListener('click', e => {
      e.preventDefault();
      const postId = btn.dataset.postId;
      toggleBookmark(postId, btn);
    });
  });

  document.querySelectorAll('.delete-btn').forEach(btn => {
    btn.addEventListener('click', confirmDelete);
  });
});
