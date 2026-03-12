-module(test).
-export([factorial/1]).
factorial(0) -> 1;
factorial(N) -> N * factorial(N-1).

% Comandos para rodar o erl:
% 1) Escrever 'erl'
% 2) compilar o ficheiro erl: c(test).
% 3) executar uma função no ficheiro: X = test:factorial(10).
% Nota para compilar o ficheiro todo logo:
% - erlc .\test.erl


% Modulos importantes: lists e maps

