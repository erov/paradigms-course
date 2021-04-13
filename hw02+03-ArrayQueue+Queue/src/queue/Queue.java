package queue;

/*
    Model:
        [a_1, a_2, .., a_n]
        n - queue size

    Inv:
        n >= 0
        forall int i in [1, n]: a[i] != null

    Immutable:
        n == n' && forall int i in [1, n]: a[i] == a'[i]
 */
public interface Queue {
    /*
        Pred: e != null
        Post: n == n'+1 && a[n] == e && forall int i in [1, n']: a[i] == a'[i]
     */
    void enqueue(Object e);

    /*
        Pred: n > 0
        Post: R == a[1] && Immutable
     */
    Object element();

    /*
        Pred: n > 0
        Post: R == a[1] && n == n'-1 && forall int i in [1, n]: a[i] == a'[i+1]
     */
    Object dequeue();

    /*
        Pred: true
        Post: R == n && Immutable
     */
    int size();

    /*
        Pred: true
        Post: R == (n == 0) && Immutable
    */
    boolean isEmpty();

    /*
        Pred: true
        Post: n == 0
     */
    void clear();

    /*
        Pred: N > 0
        Post: R - new && R.n == n/N && R.a == [a[N], a[2*N], ..., a[(n/N)*N]] && Immutable
     */
    Queue getNth(int N);

    /*
        Pred: N > 0
        Post: R - new && R.n == n'/N && R.a == [a'[N], a'[2*N], ..., a'[(n'/N)*N]] &&
              n == n' - n'/N && forall int i in [1, n]: a[i] == a'[i + (i-1)/(N-1)]
     */
    Queue removeNth(int N);

    /*
        Pred: N > 0
        Post: n == n' - n'/N && forall int i in [1, n]: a[i] == a'[i + (i-1)/(N-1)]
     */
    void dropNth(int N);
}
