document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('loginForm');
  if (!form) return;

  form.addEventListener('submit', (e) => {
    const id = form.username.value.trim();
    const pw = form.password.value.trim();
    if (!id || !pw) {
      e.preventDefault();
      alert('아이디와 비밀번호를 입력하세요.');
    }
  });
});
