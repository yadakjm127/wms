// 오늘 날짜 (클라이언트 포맷) — Locale 설정/프로퍼티 불필요
(function () {
  var el = document.getElementById("today-text");
  if (!el) return;
  var d = new Date();
  var day = ["일","월","화","수","목","금","토"][d.getDay()];
  var yyyy = d.getFullYear();
  var mm = String(d.getMonth() + 1).padStart(2,"0");
  var dd = String(d.getDate()).padStart(2,"0");
  el.textContent = yyyy + "." + mm + "." + dd + " (" + day + ")";
})();

// 버튼/라벨 줄바꿈 방지(안전망)
(function () {
  var targets = document.querySelectorAll('.btn, .qa-title, .nav-link');
  targets.forEach(function (el){ el.style.whiteSpace = 'nowrap'; });
})();
