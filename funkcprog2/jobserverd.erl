-module(jobserverd).
%% client interface
-export([evaluate/3, result/1]).
%% server interface
-export([start/1, stop/0]).
%% server callback
-export([init/1, loop/1, terminate/1]).

-define(JS, {jobserver, 'js@Melindas-MBP'}).

evaluate(M, F, A) ->
    ?JS ! {job, {M, F, A}, Ref=make_ref()},
    Ref.

result(Ref) ->
    ?JS ! {result, Ref, self()},
    receive
        % {A, Ref} -> A
        {failed, Ref} -> failed;
        {{calculating, _}, Ref} -> not_ready; 
        {{ready, Value}, Ref} -> Value
    end.

start(Max) ->
    register(jobserver, spawn_link(?MODULE, init, [Max])).

stop() ->
    ?JS ! stop. 

init(Max)->
    process_flag(trap_exit, true),
    %InitState = initialize_state(Args),
    loop({Max, #{}}).

loop(State={Max, Jobs})->
    receive
        stop ->
            terminate(State);
        {job, {M, F, A}, Ref} -> %% synchronous, asynchronous
            Main = self(),
            Pid = spawn_link(fun() -> Main ! {worker_result, apply(M, F, A), Ref} end),
            loop({Max, Jobs#{Ref=>{calculating, Pid}}});
        {result, Ref, Pid} ->
            #{Ref:=Value} = Jobs,
            Pid ! {Value, Ref},
            loop(State);
        {worker_result, Value, Ref} ->
            loop({Max, Jobs#{Ref:={ready, Value}}});
        {'EXIT', Pid, Reason} when Reason /= normal ->
            Ref = maps:fold(fun(Ref, {calculating, WPid}, _) when WPid == Pid -> Ref;
                               (_, _, Acc) -> Acc
                            end, none, Jobs),
            loop({Max, Jobs#{Ref=>failed}});
        Msg ->
            io:format("Unhandled message: ~p~n", [Msg]),
            loop(State)
    end.

terminate(State)->
    io:format("Jobserver stopped. State: ~p~n", [State]).

initialize_state(_Args) ->
    do_init.

handle_req(_Msg, _State)->
    do_sth.

    % loop(State={Max, Jobs})->
    %     receive
    %         stop ->
    %             terminate(State);
    %         {job, {M, F, A}, Ref} -> %% synchronous, asynchronous
    %             Value = apply(M, F, A),
    %             loop({Max, Jobs#{Ref=>Value}});
    %         {result, Ref, Pid} ->
    %             #{Ref:=Value} = Jobs,
    %             Pid ! {finished, Value, Ref},
    %             loop(State);
    %         Msg ->
    %             io:format("Unhandled message: ~p~n", [Msg]),
    %             loop(State)
    %     end.
