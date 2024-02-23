-module(parmap).
-export([map/2, parmap/2, fib/1, ord_parmap/2, ord_parmap2/2]).
%% spawn - process creation
%% ! - message sending
%% receive - message receiving

map(F, L) ->
    [{Head, F(Head)} || Head <- L].

parmap(F, L) ->
    Main = self(),
    [spawn(fun() -> Main ! {Head, F(Head)} end)|| Head <- L],
    [receive
        A -> A
    end || _ <- L].

    % [begin
    %     spawn(fun() -> Main ! F(Head) end), receive A -> A end 
    %  end|| Head <- L].

fib(1) -> 1;
fib(2) -> 1;
fib(N) -> fib(N-1) + fib(N-2).

ord_parmap(F, L) ->
    Main = self(),
    [spawn(fun() -> Main ! {Head, F(Head)} end)|| Head <- L],
    [receive
        {Head, _} = A -> A
    end || Head <- L].

ord_parmap2(F, L) ->
    Main = self(),
    Pids = [spawn(fun() -> Main ! {self(), F(Head)} end)|| Head <- L],
    [receive
        {Pid, Val} -> Val
    end || Pid <- Pids].