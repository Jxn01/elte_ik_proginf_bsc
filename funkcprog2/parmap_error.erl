-module(parmap_error).
-export([map/2, parmap/2, fib/1]).

map(F, L) ->
    [{Head, F(Head)} || Head <- L].

parmap(F, L) ->
    Main = self(),
    [spawn(fun() -> Main ! {Head, catch F(Head)} end)|| Head <- L],
    [receive
        {A, {'EXIT', {Error, _}}} -> {A, Error};
        A -> A
    end || _ <- L].


fib(1) -> 1;
fib(2) -> 1;
fib(N) -> fib(N-1) + fib(N-2).

