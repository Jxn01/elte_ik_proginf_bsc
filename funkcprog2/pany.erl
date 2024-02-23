-module(pany).
-export([pany/2, start/2]).
%Az órán megírt párhuzamos map mintájára párhuzamosítsátok a lists:any/2 függvény viselkedését. Azaz kaptok egy listát és egy függvényt és megmondja, hogy van-e olyan listaelem, amire teljesül az adott tulajdonság és megmondja, hogy melyik az. Különben false-t ad vissza. 
%Az alábbi követelményeknek kellene megfelelni:
 %- annyi folyamatot indul el számolni, amennyi eleme van a listának
 %- a fő folyamat legyen lusta, azaz az első igaz értéknél térjen vissza és ne várja meg a többi adatot

%bead:pany(fun erlang:is_atom/1, [alma, 3, 4]) = {true, alma}
%bead:pany(fun erlang:is_atom/1, [3, 4]) = false
%bead:start(fun erlang:is_atom/1, [korte, 23, alma, 3, 4]) = {true, alma} (vagy korte, amelyik hamarabb megérkezik)

%Megjegyzés: figyeljetek arra, hogy ha a lustaság miatt hamarabb leáll a főfolyamat és az maga az Erlang Shell, akkor a maradék üzenet ott ragad a mailboxában, és a következő futtatáskor bezavarhat. Érdemes egy flush()-t mondani a futtatás után.

-spec pany(F::function(), L::list()) -> {true, Elem::any()} | false.
pany(F, L) -> pany(F, L, self(), []).
pany(F, [H|T], Pid, Acc) ->
  spawn(fun() -> pany(F, T, Pid, Acc) end),
  pany(F, T, Pid, [H|Acc]);
pany(F, [], Pid, Acc) ->
  Pid ! {self(), lists:any(F, lists:reverse(Acc))},
  receive
    {Pid, {true, Elem}} -> {true, Elem};
    {Pid, false} -> false
  end.

-spec start(F::function(), L::list()) -> {true, Elem::any()} | false.
start(F, L) ->
  spawn(fun() -> pany(F, L) end),
  receive
    {Pid, {true, Elem}} -> {true, Elem};
    {Pid, false} -> false
  end.
