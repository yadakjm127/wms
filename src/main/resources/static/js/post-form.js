(function () {
  const title = document.getElementById('title');
  const content = document.getElementById('content');
  if (!title || !content) return;

  const form = title.closest('form');
  form.addEventListener('submit', (e) => {
    if (title.value.trim().length < 2) {
      alert('제목을 2자 이상 입력해 주세요.');
      e.preventDefault();
      title.focus();
      return;
    }
    if (content.value.trim().length < 10) {
      alert('상세 내용을 10자 이상 입력해 주세요.');
      e.preventDefault();
      content.focus();
    }
  });
})();
