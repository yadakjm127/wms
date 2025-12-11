// mypage.js
// 비밀번호 변경 폼 검증 + 페이지 로드 로그 출력

document.addEventListener("DOMContentLoaded", () => {
  console.log("MyPage loaded");

  const form = document.getElementById("password-form");
  if (!form) return;

  const newPw = document.getElementById("newPassword");
  const confirmPw = document.getElementById("confirmPassword");
  const lenErr = document.getElementById("pwd-len-error");
  const matchErr = document.getElementById("pwd-match-error");
  const submitBtn = document.getElementById("pwd-submit");

  function validate() {
    let ok = true;

    // 길이 체크 (최소 4자)
    if (newPw.value.length > 0 && newPw.value.length < 4) {
      lenErr.style.display = "block";
      ok = false;
    } else {
      lenErr.style.display = "none";
    }

    // 일치 체크
    if (confirmPw.value.length > 0 && newPw.value !== confirmPw.value) {
      matchErr.style.display = "block";
      ok = false;
    } else {
      matchErr.style.display = "none";
    }

    submitBtn.disabled = !ok;
    submitBtn.classList.toggle("disabled", !ok);
  }

  newPw.addEventListener("input", validate);
  confirmPw.addEventListener("input", validate);

  form.addEventListener("submit", (e) => {
    // 최종 방어
    if (newPw.value.length < 4) {
      lenErr.style.display = "block";
      e.preventDefault();
      return false;
    }
    if (newPw.value !== confirmPw.value) {
      matchErr.style.display = "block";
      e.preventDefault();
      return false;
    }
  });
});
