-module(example).
-export([bar/0, bar/1, foo/1, foo2/1]).

foo2(X) when X == 1 ->
    ok;
foo2(1.0) ->
    float.

foo(1) ->
    integer;
foo(1.0) ->
    float;
foo('1') ->
    atom;
foo({1}) ->
    tuple;
foo([1]) ->
    list;
foo("1") ->
    string;
foo(<<1>>) ->
    binary;
foo(#{'1':=1}) ->
    mappp.

bar(x) ->
    x+1;
bar(X) ->
    X+1.

bar() ->
    foo().

foo() -> ok.