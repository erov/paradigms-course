init(MAX_N) :- precalc_primes(2, MAX_N).

mark_composite(I) :- composite(I), !.
mark_composite(I) :- assert(composite(I)).

update_next(I, Step, MAX_N) :- I =< MAX_N, mark_composite(I), Next is I + Step, update_next(Next, Step, MAX_N).

precalc_primes(I, MAX_N) :- \+ composite(I), Start is I * I, update_next(Start, I, MAX_N).
precalc_primes(I, MAX_N) :- I * I =< MAX_N, Next is I + 1, precalc_primes(Next, MAX_N).

prime(N) :- N > 1, \+ composite(N).

find_prime_divisors(N, _, [N]) :- prime(N), !.
find_prime_divisors(N, Start, [H | T]) :- number(N), !, minimal_divisor(N, Start, H), Next is div(N, H), find_prime_divisors(Next, H, T).
find_prime_divisors(N, Start, [H | T]) :- valid_divisors([H | T]), multiply([H | T], N).

prime_divisors(1, []) :- !.
prime_divisors(N, [H | T]) :- find_prime_divisors(N, 2, [H | T]).

minimal_divisor(N, I, I) :- prime(I), 0 is mod(N, I), !.
minimal_divisor(N, I, R) :- Next is I + 1, Next * Next =< N, minimal_divisor(N, Next, R).
minimal_divisor(N, N, N) :- prime(N).

valid_divisors([H]) :- prime(H), !.
valid_divisors([H1, H2 | T]) :- H1 =< H2, prime(H1), valid_divisors([H2 | T]).

multiply([], 1).
multiply([H | T], R) :- multiply(T, R1), R is H * R1.

% Index
prime_index(P, N) :- number(P), !, prime(P), recover_index(P, N).
prime_index(P, N) :- recover_prime(N, P).

prime_index_lookup(2, 1).

recover_index(P, R) :- prime_index_lookup(P, R), !.
recover_index(P, R) :- composite(P), !, Prev is P - 1, recover_index(Prev, R).
recover_index(P, R) :- Prev is P - 1, recover_index(Prev, R1), R is 1 + R1, assert(prime_index_lookup(P, R)).

recover_prime(N, P) :- prime_index_lookup(P, N), !.
recover_prime(N, P) :- PrevId is N - 1, recover_prime(PrevId, PrevPrime), From is PrevPrime + 1, find_prime(From, P), assert(prime_index_lookup(P, N)).

find_prime(R, R) :- prime(R), !.
find_prime(I, R) :- Step is I + 1, find_prime(Step, R).
