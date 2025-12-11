document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('registerForm');
  if (!form) return;

  form.addEventListener('submit', (e) => {
    const id = form.username.value.trim();
    const pw = form.password.value.trim();
    if (!id) { e.preventDefault(); alert('아이디를 입력하세요.'); return; }
    if (!pw) { e.preventDefault(); alert('비밀번호를 입력하세요.'); return; }
  });
});
