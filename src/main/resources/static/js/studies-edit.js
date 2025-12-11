import {storageAdapter as api} from './storageAdapter.js';
import {qs} from './util.js';

const id = qs('id');
const $form = document.getElementById('form');
const $notFound = document.getElementById('notFound');
const $detailLink = document.getElementById('detailLink');
const $cancelBtn = document.getElementById('cancelBtn');

function load(){
  const s = api.get(id);
  if(!s){ $notFound.hidden=false; return; }
  $form.hidden=false;

  document.getElementById('title').value = s.title;
  document.getElementById('intro').value = s.intro||'';
  document.getElementById('category').value = s.category||'';
  document.getElementById('memberCount').value = s.memberCount||0;

  const detailHref = `/studies-detail.html?id=${encodeURIComponent(id)}`;
  $detailLink.href = detailHref;
  $cancelBtn.href = detailHref;
}

$form.addEventListener('submit', (e)=>{
  e.preventDefault();
  const patch = {
    title: document.getElementById('title').value.trim(),
    intro: document.getElementById('intro').value.trim(),
    category: document.getElementById('category').value,
    memberCount: Number(document.getElementById('memberCount').value||0),
  };
  if(!patch.title){ alert('제목을 입력하세요'); return; }
  api.update(id, patch);
  location.href = `/studies-detail.html?id=${encodeURIComponent(id)}`;
});

load();
