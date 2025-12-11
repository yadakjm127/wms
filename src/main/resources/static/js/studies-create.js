import {storageAdapter as api} from './storageAdapter.js';

const $form = document.getElementById('form');

$form.addEventListener('submit', (e)=>{
  e.preventDefault();
  const title = document.getElementById('title').value.trim();
  const intro = document.getElementById('intro').value.trim();
  const category = document.getElementById('category').value;
  const memberCount = Number(document.getElementById('memberCount').value||0);
  if(!title){ alert('제목을 입력하세요'); return; }

  const item = api.create({title,intro,category,memberCount});
  location.href = `/studies-detail.html?id=${encodeURIComponent(item.id)}`;
});
