-module(myqueue).
-export([create/0, enqueue/2, dequeue/1]).

create() ->
    {[],[]}.

enqueue({Front, Back}, Item) ->
    {Front, [Item | Back]}.

%% Ele insere um item na Head da lista Back que é composta por Head e Tail([H | T])
%% Exemplo [1 | [2,3]] -> [1,2,3]

dequeue({[], []}) -> 
    empty;

dequeue({[H | T], Back}) ->
    {{T, Back}, H};

dequeue({[], Back}) -> 
    dequeue({lists:reverse(Back), []}).
