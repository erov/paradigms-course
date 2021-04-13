package search;

public class BinarySearch {
    /*
        Pred: args[] - int array, written as string array && int x == (int)args[0] &&
           && forall int i, j in [1, args.length) i < j (int)args[i] >= (int)args[j]
        Post: wrote minimal value k: (int)args[k] <= (int)args[0] if it exists, args.length-1 otherwise
    */
    public static void main(String[] args) {
        // Pred
        final int[] arr = new int[args.length-1];
        // Pred && int arr[] && arr.length == args.length-1
        int x = Integer.parseInt(args[0]);
        // Pred && int arr[] && arr.length == args.length && x == (int)args[0]
        int i = 0;
        // Converted: x == (int)args[0] && int arr[] && arr.length == args.length && forall int j in [0, i-1]: arr[j] == (int)args[j+1]
        // Pred && i == 0 && Converted
        // Inv: Pred && Converted
        while (i < args.length - 1) {
            // Pred && Converted && i < args.length-1
            arr[i] = Integer.parseInt(args[i + 1]);
            // Pred && Converted && i < args.length-1 && arr[i] == (int)args[i+1]
            i++;
            // Pred && Converted && i == i'+1
        }
        // Pred && Converted && i == args.length-1
        // Arr: int arr[] && arr.length == args.length && forall int i in [0, arr.length): arr[i] == (int)args[i+1] &&
        //   && forall int i, j in [0, arr.length) i < j arr[i] >= arr[j]
        // Args && Converted && i == args.length-1 =>  Arr

//        final int result = iterativeSearch(arr, x);
        final int result = recursiveSearch(arr,x, -1, arr.length);

        // Arr && result == minimal value pos: (int)arr[pos] <= (int)arr[0] if it exists, arr.length otherwise
        System.out.println(result);
        // Arr && wrote minimal value pos: (int)args[pos] <= (int)args[0] if it exists, args.length-1 otherwise
    }

    /*
        Input: forall int i, j in [0, arr.length), i < j arr[i] >= arr[j] &&
            && exists minimal integer value k in [0, arr.length]:
               forall integer i in [0, k) arr[i] > x && forall integer j in [pos, arr.length) arr[j] <= x

        Pred: Input
        Post: R == k
     */
    public static int iterativeSearch(int[] arr, int x) {
        // Input && 0 <= k <= arr.length
        int l = -1, r = arr.length;
        // Input && -1 <= l < k <= r <= arr.length
        // Inv: Input && -1 <= l < k <= r <= arr.length
        while (r - l > 1) {
            // Input && -1 <= l < k <= r <= arr.length && l + 1 < r
            int mid = (l + r) / 2;
            // Input && -1 <= l < k <= r <= arr.length && l + 1 < r && mid == (l + r) / 2
            // l + 1 < r && mid == (l + r) / 2  => l < mid < r
            if (arr[mid] > x) {
                // Input && -1 <= l < k <= r <= arr.length && l + 1 < r && mid == (l + r) / 2 && l < mid < r && arr[mid] > x
                // l < mid < r && l < k <= r && arr[mid] > x  =>  l < mid < k <= r
                l = mid;
                // Input && mid == l == (l' + r) / 2 && -1 <= l' < l < k <= r <= arr.length
                // len(l, r) == r - l == r - (l' + r) / 2 == 1/2 * len(l', r)
            } else {
                // Input && -1 <= l < k <= r <= arr.length && l + 1 < r && mid == (l + r) / 2 && l < mid < r && arr[mid] <= x
                // l < mid < r && l < k <= r && arr[mid] <= x  =>  l < k <= mid < r
                r = mid;
                // Input && mid == r == (l + r') / 2 && -1 <= l < k <= r < r' <= arr.length
                // len(l, r) == r - l == (l + r') / 2 - l == 1/2 * len(l, r')
            }
            // Input && -1 <= l < k <= r <= arr.length
        }
        // Input && -1 <= l < k <= r <= arr.length && r - l <= 1  =>  r - l == 1  =>  r == k  =>  R == k
        return r;
    }

    /*
        Pred: Input && -1 <= l < k <= r <= arr.length
        Post: R == k
     */
    public static int recursiveSearch(int[] arr, int x, int l, int r) {
        // Input && -1 <= l < k <= r <= arr.length
        if (r - l > 1) {
            // Input && l < k <= r && l + 1 < r
            int mid = (l + r) / 2;
            // Input && -1 <= l < k <= r <= arr.length && l + 1 < r && mid == (l + r) / 2
            // l + 1 < r && mid == (l + r) / 2  => l < mid < r
            if (arr[mid] > x) {
                // Input && -1 <= l < k <= r <= arr.length && l + 1 < r && mid == (l + r) / 2 && l < mid < r && arr[mid] > x
                // l < mid < r && l < k <= r && arr[mid] > x  =>  l < mid < k <= r
                l = mid;
                // Input && mid == l == (l' + r) / 2 && -1 <= l' < l < k <= r <= arr.length
                // len(l, r) == r - l == r - (l' + r) / 2 == 1/2 * len(l', r)
            } else {
                // Input && -1 <= l < k <= r <= arr.length && l + 1 < r && mid == (l + r) / 2 && l < mid < r && arr[mid] <= x
                // l < mid < r && l < k <= r && arr[mid] <= x  =>  l < k <= mid < r
                r = mid;
                // Input && mid == r == (l + r') / 2 && -1 <= l < k <= r < r' <= arr.length
                // len(l, r) == r - l == (l + r') / 2 - l == 1/2 * len(l, r')
            }
            // Input && -1 <= l < k <= r <= arr.length
            return recursiveSearch(arr, x, l, r);
        } else {
            // Input && -1 <= l < k <= r <= arr.length && r - l <= 1  =>  r - l == 1  =>  r == k  =>  R == k
            return r;
        }
    }
}
