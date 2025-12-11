export function fmtDate(ts){
  const d = new Date(ts);
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth()+1).padStart(2,'0');
  const dd = String(d.getDate()).padStart(2,'0');
  return `${yyyy}.${mm}.${dd}`;
}
export function qs(name){
  const p = new URLSearchParams(location.search);
  return p.get(name);
}
export function byId(id){ return document.getElementById(id); }
