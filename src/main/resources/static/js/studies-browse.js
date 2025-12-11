// 데모용: 버튼 클릭 로그
document.addEventListener('click', (e) => {
  const applyBtn = e.target.closest('.apply-btn');
  if (applyBtn) {
    const id = applyBtn.getAttribute('data-id');
    console.log('apply study id =', id);
    alert('신청 데모: id=' + id);
  }
});
