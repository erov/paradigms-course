package search;

public class BinarySearchMax {
    /*
        Pred: args[] - int array, written as string array &&
           && exists int k in [0, args.length): forall int i, j in [0, k] i < j (int)args[i] < (int)args[j] &&
                                             && forall int i, j in [k+1, args.length) i < j (int)args[i] > (int)args[j] &&
                                             && k+1 < args.length -> (int)args[k] >= (int)args[k+1]
        Post: wrote maximum value in (int)args[] if it exists, an error message otherwise
    */
    public static void main(final String[] args) {
        // Pred
        final int[] arr = new int[args.length];
        // Pred && int arr[] && arr.length == args.length
        int i = 0;
        // Converted: int arr[] && arr.length == args.length && forall int j in [0, i-1]: arr[j] == (int)args[j]
        // Pred && i == 0 && Converted
        // Inv: Pred && Converted
        while (i < args.length) {
            // Pred && Converted && i < args.length
            arr[i] = Integer.parseInt(args[i]);
            // Pred && Converted && i < args.length && arr[i] == (int)args[i]
            i++;
            // Pred && Converted && i == i'+1
        }
        // Pred && Converted && i == args.length
        // Arr: int arr[] && arr.length == args.length && forall int i in [0, arr.length): arr[i] == (int)args[i] &&
        //   && exists int k in [0, arr.length): forall int i, j in [0, k] i < j arr[i] < arr[j] &&
        //                                    && forall int i, j in [k+1, arr.length) i < j arr[i] > arr[j] &&
        //                                    && k+1 < arr.length -> arr[k] >= arr[k+1]
        // Args && Converted && i == args.length  =>  Arr
        if (arr.length > 0) {
            // Arr && arr.length > 0
            // arr.length > 0  =>  maximum value in arr[] exists

//            final int result = iterativeSearch(arr);
            final int result = recursiveSearch(arr,0, arr.length);
            
            // Arr && result == maximum value in arr[]
            System.out.println(result);
            // Arr && wrote maximum value in arr[]  =>  wrote maximum value in (int)args[]
        } else {
            // Arr && arr.length == 0
            // arr.length == 0  =>  maximum value in arr[] doesn't exist
            System.out.println("Error: array is empty");
            // wrote an error message
        }
        // Arr && wrote maximum value in array (int)args if it exists, an error message otherwise
    }

    /*
        Input: int arr[], arr.length > 0 &&
            && exists int k in [0, arr.length): forall int i, j in [0, k] i < j arr[i] < arr[j] &&
                                             && forall int i, j in [k+1, arr.length) i < j arr[i] > arr[j] &&
                                             && k+1 < arr.length -> arr[k] >= arr[k+1]

        Pred: Input
        Post: R == arr[k] - maximum value in arr[]
     */
    public static int iterativeSearch(final int[] arr) {
        // Input
        int l = 0, r = arr.length;
        // Input && 0 <= l <= k < r <= arr.length
        // Inv: Input && 0 <= l <= k < r <= arr.length
        while (r - l > 1) {
            // Input && 0 <= l <= k < r <= arr.length && l + 1 < r
             final int mid = (l + r) / 2;
            // Input && 0 <= l <= k < r <= arr.length && l + 1 < r && mid == (l + r) / 2
            // l + 1 < r && mid == (l + r) / 2  =>  l < mid < r
            if (arr[mid - 1] <= arr[mid]) {
                // Input && 0 <= l <= k < r <= arr.length && l + 1 < r && mid == (l + r) / 2 && l < mid < r && arr[mid - 1] <= arr[mid]
                // l < mid < r && l <= k < r && arr[mid - 1] <= arr[mid]  =>  0 <= l < mid <= k < r <= arr.length
                l = mid;
                // Input && 0 <= l' < l <= k < r <= arr.length && mid == l == (l' + r) / 2
                // len(l, r) == r - l == r - (l' + r) / 2 == 1/2 * len(l', r)
            } else {
                // Input && 0 <= l <= k < r <= arr.length && l + 1 < r && mid == (l + r) / 2 && l < mid < r && arr[mid - 1] > arr[mid]
                // arr[mid - 1] > arr[mid] && l < mid < r && l <= k < r  =>  0 <= l <= k < mid < r <= arr.length
                r = mid;
                // Input && 0 <= l <= k < r < r' <= arr.length && mid == r == (l + r') / 2
                // len(l, r) == r - l == (l + r') / 2 - l == 1/2 * len(l, r')
            }
            // Input && 0 <= l' <= l <= k < r <= r' <= arr.length
            // len(l, r) == 1/2 * len(l', r')
        }
        // Input && 0 <= l <= k < r <= arr.length && r - l <= 1  =>  r - l == 1  =>  l == k  =>  R == arr[l] == maximum value in arr[]
        return arr[l];
    }

    /*
        Pred: Input && 0 <= l <= k < r <= arr.length
        Post: R == arr[k] - maximum value in arr[]
     */
    public static int recursiveSearch(final int[] arr, int l, int r) {
        // Input && 0 <= l <= k < r <= arr.length
        if (r - l > 1) {
            // Input && 0 <= l <= k < r <= arr.length && l + 1 < r
            final int mid = (l + r) / 2;
            // Input && 0 <= l <= k < r <= arr.length && l + 1 < r && mid == (l + r) / 2
            // l + 1 < r && mid == (l + r) / 2  =>  l < mid < r
            if (arr[mid - 1] <= arr[mid]) {
                // Input && 0 <= l <= k < r <= arr.length && l + 1 < r && mid == (l + r) / 2 && l < mid < r && arr[mid - 1] <= arr[mid]
                // l < mid < r && l <= k < r && arr[mid - 1] <= arr[mid]  =>  0 <= l < mid <= k < r <= arr.length
                l = mid;
                // Input && 0 <= l' < l <= k < r <= arr.length && mid == l == (l' + r) / 2
                // len(l, r) == r - l == r - (l' + r) / 2 == 1/2 * len(l', r)
            } else {
                // Input && 0 <= l <= k < r <= arr.length && l + 1 < r && mid == (l + r) / 2 && l < mid < r && arr[mid - 1] > arr[mid]
                // arr[mid - 1] > arr[mid] && l < mid < r && l <= k < r  =>  0 <= l <= k < mid < r <= arr.length
                r = mid;
                // Input && 0 <= l <= k < r < r' <= arr.length && mid == r == (l + r') / 2
                // len(l, r) == r - l == (l + r') / 2 - l == 1/2 * len(l, r')
            }
            // Input && 0 <= l' <= l <= k < r <= r' <= arr.length
            // len(l, r) == 1/2 * len(l', r')
            return recursiveSearch(arr, l, r);
        } else {
            // Input && 0 <= l <= k < r <= arr.length && r - l <= 1  =>  r - l == 1  =>  l == k  =>  R == arr[l] == maximum value in arr[]
            return arr[l];
        }
    }
}
