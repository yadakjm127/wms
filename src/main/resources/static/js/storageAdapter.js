// localStorage 어댑터 (CRUD) + 시드 데이터
const KEY = 'mindhub.studies.v1';

function _load(){
  const raw = localStorage.getItem(KEY);
  if(!raw) return null;
  try{ return JSON.parse(raw); }catch(e){ return null; }
}
function _save(data){ localStorage.setItem(KEY, JSON.stringify(data)); }

function _seedIfEmpty(){
  const cur = _load();
  if(cur && Array.isArray(cur.items) && cur.items.length) return;
  const now = Date.now();
  const items = [
    {id:String(now-3), title:'토익 900+ 목표반', intro:'모의고사 중심 주3회 스터디', category:'어학', memberCount:12, createdAt:now-1000*60*60*24*6, liked:false, bookmarked:false},
    {id:String(now-2), title:'자바 개발자 취업 스터디', intro:'알고리즘/CS 면접, 주 2회', category:'개발', memberCount:8, createdAt:now-1000*60*60*24*4, liked:false, bookmarked:true},
    {id:String(now-1), title:'UX 포트폴리오 피드백', intro:'실무자 피드백 세션 진행', category:'디자인', memberCount:5, createdAt:now-1000*60*60*24*2, liked:true, bookmarked:false},
    {id:String(now-4), title:'SQL 중급 스터디', intro:'실전 쿼리 리뷰', category:'데이터', memberCount:7, createdAt:now-1000*60*60*24*9, liked:false, bookmarked:false},
  ];
  _save({items});
}

export const storageAdapter = {
  list(){ _seedIfEmpty(); const d=_load(); return d? d.items.slice(): []; },
  get(id){ _seedIfEmpty(); const d=_load(); return d.items.find(x=>x.id===id)||null; },
  create(payload){
    _seedIfEmpty();
    const d=_load();
    const id = String(Date.now()) + Math.random().toString(36).slice(2,8);
    const item = {
      id, title:payload.title||'', intro:payload.intro||'',
      category:payload.category||'', memberCount:Number(payload.memberCount||0),
      createdAt:Date.now(), liked:false, bookmarked:false
    };
    d.items.unshift(item); _save(d); return item;
  },
  update(id, patch){
    _seedIfEmpty();
    const d=_load();
    const idx = d.items.findIndex(x=>x.id===id);
    if(idx<0) return null;
    d.items[idx] = {...d.items[idx], ...patch};
    _save(d); return d.items[idx];
  },
  remove(id){
    _seedIfEmpty();
    const d=_load();
    const n = d.items.filter(x=>x.id!==id);
    _save({items:n});
  },
  toggleLike(id){
    const it = this.get(id); if(!it) return null;
    return this.update(id,{liked:!it.liked});
  },
  toggleBookmark(id){
    const it = this.get(id); if(!it) return null;
    return this.update(id,{bookmarked:!it.bookmarked});
  }
};
