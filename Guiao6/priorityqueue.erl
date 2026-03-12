-module(priorityqueue).
-export([create/0, enqueue/3, dequeue/1]).

create() -> 
    {[],[]}.


enqueue(PriQueue, Item, Priority) -> 
    case lists:keyfind(Priority, 1, PriQueue) of
        false -> 
            Q = myqueue:enqueue(myqueue:create(), Item),
            [{Priority, Q} | PriQueue];
        {Priority, Q} ->
            NewQ = myqueue:enqueue(Q, Item),
            lists:keyreplace(Priority, 1, PriQueue, {Priority, NewQ})
        end.

dequeue([]) ->
    empty;

dequeue(PriQueue) -> 
    {Priority, Q} = list:max(PriQueue),
    case myqueue:dequeue(Q) of
        empty ->
            dequeue(lists:keydelete(Priority, 1, PriQueue));
        {NewQ, Item} -> 
            {lists:keyreplace(Priority, 1, PriQueue, {Priority, NewQ}), Item}
        end.
 