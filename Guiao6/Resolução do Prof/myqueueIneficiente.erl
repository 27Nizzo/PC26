-module(myqueueIneficiente).
-export([create/0, enqueue/2, dequeue/1, test/0]).

% Cria a queue, uma lista vazia
create() ->
    [].

% Vamos meter algum item na queue
% -> Caso seja uma lista vazia:
enqueue([], Item) ->
    [Item];
% -> Caso nao seja uma lista vazia:
enqueue(Queue, Item) ->
    Queue++[Item]. % Tambem da mas ele nao quer desta forma
    % Mais eficiente é ir buscar ao fim:
    % [Queue| Item].

dequeue([]) -> empty;

dequeue([H | T]) -> {T, H}. % Vai retornar a tail e removeu a head
 


test() ->
    Q = create(),
    Q1 = enqueue(Q, 1),
    Q2 = enqueue(Q1, 2),
    Q3 = enqueue(Q2, 3),
    Q4 = enqueue(Q3, 4),
    Q5 = enqueue(Q4, 2),
    empty = dequeue(Q),
    {Q6, 1} = dequeue(Q1),
    empty = dequeue(Q6),
    {Q7, 1} = dequeue(Q5),
    {Q8, 2} = dequeue(Q7),
    {_, 3} = dequeue(Q8),    
    ok.
