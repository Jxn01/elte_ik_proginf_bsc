-module(ring).
-export([run/1, run/2]).

run(N, M) ->
        Last = lists:foldl(fun(_, Next) ->
                              %spawn(fun F() -> receive A -> Next ! A end, F() end)
                              spawn(fun() -> worker(Next) end)
                           end, self(), lists:seq(1, N)),
        [Last ! ok || _ <- lists:seq(1, M)],
        [receive
            ok -> finished
         end || _ <- lists:seq(1, M)].

worker(Next) ->
    receive
        A -> 
            Next ! A,
            worker(Next)
    end.

%% HF: rekurzioval az inditast
run(N) ->
%    [spawn(fun() -> receive A -> Next ! A end end) || _ <- lists:seq(1, N)].
    Last = lists:foldl(fun(_, Next) ->
                          %spawn(fun F() -> receive A -> Next ! A end, F() end)
                          spawn(fun() -> receive A -> Next ! A end end)
                       end, self(), lists:seq(1, N)),
    Last ! ok,
    receive
        ok -> finished
    end.

gcd_fun2(X, Z) ->
    F = fun Func(A,A) -> A;
            Func(A,B) when A < B -> Func(A, B-A);
            Func(A,B) -> Func(A-B, B)
        end,
    F(X,Z).


gcd_fun(X, Z) ->
    F = fun(A,A, Fun) -> A;
            (A,B, Fun) when A < B -> Fun(A, B-A, Fun);
            (A,B, Fun) -> Fun(A-B, B, Fun)
        end,
    F(X,Z,F).

gcd(A, A) ->
    A;
gcd(A, B) when A < B ->
    gcd(A, B-A);
gcd(A, B) ->
    gcd(A-B, B).