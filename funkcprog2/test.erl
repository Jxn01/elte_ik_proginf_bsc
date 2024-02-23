-module(test).
-export([sum_freq/2, equals/2]).
-import(io , [fwrite/1, fwrite/2]).

-spec sum_freq(map(), [any()]) -> {integer(), map()}.
sum_freq(Map, []) -> {0, Map};
sum_freq(Map, [H | T]) -> {Freq, NewMap} = sum_freq(Map, T), {Freq + maps:get(H, Map, 0), maps:put(H, maps:get(H, Map, not_found), NewMap)}.

-spec equals([any()], [any()]) -> [integer()].
equals([], []) -> [];
equals([H1 | T1], [H2 | T2]) -> equals([H1 | T1], [H2 | T2], 1, []).
equals([], [], _, Acc) -> Acc;
equals([H1 | T1], [H2 | T2], Pos, Acc) -> equals(T1, T2, Pos + 1, if H1 == H2 -> Acc ++ [Pos]; true -> Acc end).
