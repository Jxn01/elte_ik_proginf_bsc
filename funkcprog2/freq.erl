-module(freq).
-export([freq0/1, freq00/1, freq01/1, freq1/1, freq3/1]).

freq0([]) ->   
    [];
freq0([H|T]) ->
    [{H, count(H, T) +1} | freq0(T)].

count(H, L) ->
    length([ E || E<-L, E =:= H]).

freq00(L) ->
    lists:uniq([{E, count(E, L)} || E <- L]). %% lists:usort for older Erlang versions

freq01([]) ->   
    [];
freq01([H|T]) ->
%    Count = length([ E || E<-T, E =:= H]),
%    Rem = [ E || E<-T, E =/= H],
    {Count, Rem} = count_rem(H, T),
    [{H, Count +1} | freq01(Rem)].

count_rem(_E, []) ->
    {0, []};
count_rem(E, [E|T]) -> %when E =:= H ->
    {Count, Rem} = count_rem(E, T),
    {Count+1, Rem};
count_rem(E, [H|T]) ->
    {Count, Rem} = count_rem(E, T),
    {Count, [H|Rem]}.

count2(_E, []) -> 0;
count2(E, [E|T]) ->
    1+count2(E, T);
count2(E, [_|T]) ->
    count2(E, T).

freq1(L) ->
    freq2(L, []).

freq1([], Result) -> 
    Result;
freq1([H|T], Result) ->
    %A = 
    case lists:keymember(H, 1, Result) of %% case {S, G} of {ok, ok} -> ...
        true -> %A =1, 
            freq1(T, Result);
        false -> %A=2, 
            freq1(T, [{H, count2(H, T) +1} | Result])
    end. %,
    %A.
    
%% sajat keymember mintaillesztessel, 11.0 miatt


%% freq02([H|T], Visited) -> lists:member(H, Visited)
%% 

freq2([], Result) -> 
    Result;
freq2([H|T], Result) ->
    case lists:keyfind(H, 1, Result) of
        {H, Count}-> 
            freq2(T, lists:keyreplace(H, 1, Result, {H, Count+1}));
        false -> 
            freq2(T, [{H, 1} | Result])
    end.

freq3(L) ->
    freq3(L, #{}).

freq3([], Map) ->
    Map; %(maps:to_list(Map))
freq3([H|T], Map) ->
    case Map of
        #{H := Count} -> 
            freq3(T, Map#{H=>Count+1});
        _ -> 
            freq3(T, Map#{H=>1})
    end.