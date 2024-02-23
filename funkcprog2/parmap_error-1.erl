-module(parmap_error).
-export([map/2, parmap/2, fib/1, error/3, parmap_link/2, parmap_monitor/2]).

map(F, L) ->
    [{Head, F(Head)} || Head <- L].

parmap(F, L) ->
    Main = self(),
    [spawn(fun() -> Main ! {Head, catch F(Head)} end)|| Head <- L],
    [receive
        {A, {'EXIT', {Error, _}}} -> {A, Error};
        A -> A
    end || _ <- L].

parmap_link(F, L) ->
    process_flag(trap_exit, true),
    Main = self(),
    [spawn_link(fun() -> Main ! {result, {Head, F(Head)}} end)|| Head <- L],
    [receive
        {'EXIT', _, Reason} when  Reason /= normal -> error;
        {result, A} -> A
    end || _ <- L].


parmap_monitor(F, L) ->
    register(master, self()),
    [spawn_monitor(fun() -> worker(Head, F) end)|| Head <- L],
    [receive
        {'DOWN', _, process, _, Reason} when  Reason /= normal -> error;
        {result, A} -> A
    end || _ <- L].

worker(E, F) ->
    master ! {result, {E, F(E)}}.

fib(1) -> 1;
fib(2) -> 1;
fib(N) -> fib(N-1) + fib(N-2).


error(M, F, Args) ->
    try 
%        M:F(),
%        M:F(A)
        throw(alma),
        Alma = 10,
        %Alma = 2,
        apply(M, F, Args)
    of
        Val -> {result, Val}
    catch
        _:undef -> "Undefined function";
        Class:Type:Stack -> {"Error", Class, Type, Stack}
    after 
        io:format("After~n")
    end.