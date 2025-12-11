// src/main/resources/static/js/admin-dashboard.js
(function () {
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]')?.content;

    const resultBox = document.getElementById('role-update-result');
    const roleButtons = document.querySelectorAll('.role-btn');

    roleButtons.forEach(btn => {
        btn.addEventListener('click', async () => {
            const userId = btn.getAttribute('data-id');
            const newRoleRaw = btn.getAttribute('data-role'); // "ADMIN" or "USER"
            if (!userId || !newRoleRaw) return;

            const newRoleFull = newRoleRaw === 'ADMIN' ? 'ADMIN' : 'USER';

            try {
                const resp = await fetch(`/admin/users/${userId}/role`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        ...(csrfHeaderName && csrfToken ? { [csrfHeaderName]: csrfToken } : {})
                    },
                    body: `role=${encodeURIComponent(newRoleFull)}`
                });

                const text = await resp.text();

                if (resp.ok && text === "ok") {
                    if (resultBox) {
                        resultBox.style.display = 'block';
                        resultBox.style.backgroundColor = '';
                        resultBox.style.borderColor = '';
                        resultBox.style.color = '';
                        resultBox.textContent =
                            `사용자 ${userId} 권한이 ${newRoleFull === 'ADMIN' ? '관리자' : '사용자'} 로 변경되었습니다.`;
                    }

                    const row = btn.closest('tr');
                    if (row) {
                        const roleCell = row.querySelector('.role-cell');
                        if (roleCell) {
                            // 화면 표시 한글화
                            roleCell.textContent = newRoleFull === 'ADMIN' ? '관리자' : '사용자';
                            // 내부 raw role 값(혹시 이후에 쓸 수도 있으니까 같이 갱신)
                            roleCell.setAttribute(
                                'data-raw-role',
                                newRoleFull === 'ADMIN' ? 'ROLE_ADMIN' : 'ROLE_USER'
                            );
                        }
                    }
                } else {
                    if (resultBox) {
                        resultBox.style.display = 'block';
                        resultBox.style.backgroundColor = '#fef2f2';
                        resultBox.style.borderColor = '#fecaca';
                        resultBox.style.color = '#b91c1c';
                        resultBox.textContent = `변경 실패: ${text}`;
                    }
                }
            } catch (err) {
                if (resultBox) {
                    resultBox.style.display = 'block';
                    resultBox.style.backgroundColor = '#fef2f2';
                    resultBox.style.borderColor = '#fecaca';
                    resultBox.style.color = '#b91c1c';
                    resultBox.textContent = `에러 발생: ${err}`;
                }
            }
        });
    });
})();
