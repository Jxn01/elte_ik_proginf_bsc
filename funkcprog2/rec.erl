-module(rec).
-export([inc/1, fooo/1, gcd/2, gen/2, inc_one/1, inc_lc/1, sum/1, sum_fold/1, sumt/1]).

%gcd(15, 6)
%gcd(15, 15)
gcd(A, A) ->
    A;
gcd(A, B) when A < B ->
    gcd(A, B-A);
gcd(A, B) ->
    gcd(A-B, B).

inc([]) ->
    [];
inc([NewL | Tail]) ->
    [NewL +1 | inc(Tail)].
%    Inc = Head + 1,
%    IncList = inc(Tail),
%    Res = [Inc | IncList],
%    Res.

inc_one(X) -> X + 1.

%%
gen(_F, []) ->
    [];
gen(F, [Head | Tail]) -> 
    [F(Head) | gen(F, Tail)].
%%gen(F, L) ->
    %% [F(Head) || Head <- L].
    %% lists:map(F, L).
fooo(NewL) ->
    inct(NewL, []).

inct([], Res) ->
    lists:reverse(Res);
inct([Head|Tail], Res) ->
    inct(Tail, [Head+1 | Res]). %% Res ++ [Head+1]
%    Inc = Head + 1,
%    NewRes = [Inc | Res],
%    inct(Tail, NewRes).
% 

inc_lc(L) ->
    [ E + 1 || E <- L ],
        %E nem jon ki
    [ E + 2 || E <- L, E < 12 ].

sum([]) ->
    0;
sum([H|T]) ->
    H + sum(T).

sumt(L) ->
    sum(L, 0).

sum([], Acc) ->
    Acc;
sum([H|T], Acc) ->
    sum(T, Acc+H).

sum_fold(L) ->
    lists:foldl(fun(H, Acc) -> Acc + H end, 0, L).
%     lists:foldl(fun(H, Acc) -> [H +1 | Acc ] end, 0, L).

