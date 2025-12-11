import {storageAdapter as api} from './storageAdapter.js';
import {fmtDate, qs} from './util.js';

const id = qs('id');
const $wrap = document.getElementById('wrap');
const $notFound = document.getElementById('notFound');

const $title = document.getElementById('title');
const $intro = document.getElementById('intro');
const $badge = document.getElementById('badge');
const $members = document.getElementById('members');
const $created = document.getElementById('created');

const $like = document.getElementById('likeBtn');
const $bm = document.getElementById('bmBtn');
const $del = document.getElementById('delBtn');
const $editBtn = document.getElementById('editBtn');
const $editLink = document.getElementById('editLink');

function load(){
  const s = api.get(id);
  if(!s){ $notFound.hidden=false; return; }
  $wrap.hidden = false;
  $title.textContent = s.title;
  $intro.textContent = s.intro||'';
  $badge.textContent = s.category||'신규';
  $members.textContent = `${s.memberCount}명 참여중`;
  $created.textContent = fmtDate(s.createdAt);
  $like.textContent = s.liked ? '좋아요 취소' : '좋아요';
  $bm.textContent = s.bookmarked ? '북마크 취소' : '북마크';
  const editHref = `/studies-edit.html?id=${encodeURIComponent(s.id)}`;
  $editBtn.href = editHref; $editLink.href = editHref;
}

$like.addEventListener('click', ()=>{ api.toggleLike(id); load(); });
$bm.addEventListener('click', ()=>{ api.toggleBookmark(id); load(); });
$del.addEventListener('click', ()=>{
  if(confirm('삭제하시겠습니까?')){ api.remove(id); location.href='/studies-browse.html'; }
});

load();
