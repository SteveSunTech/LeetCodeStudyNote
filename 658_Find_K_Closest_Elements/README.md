# Find K Closest Elements
Given a sorted array, two integers k and x, find the k closest elements to x in the array. The result should also be sorted in ascending order. If there is a tie, the smaller elements are always preferred.

```
Example 1:
Input: [1,2,3,4,5], k=4, x=3
Output: [1,2,3,4]

Example 2:
Input: [1,2,3,4,5], k=4, x=-1
Output: [1,2,3,4]
```

## Solution

先用 BinarySearch 找出，小於或等於X的第一個數字，另外要處理兩種狀況，當X大於最大的數字，或是小魚最小的數字，要直接把邊界值回傳。

接著，加入該值進入解中，並且使用左右兩個指針來去比較左右兩邊數字與X的差值，題目要我們將比較小的差值放入，並且當兩個差值相同時，直接加入左側的值。

考慮一開始就在邊界的狀況，我們只需要將右邊的值加入，反之亦然。

回傳List<Integer> result 之前，記得使用 Collections.sort() 做排序（Default 是使用 小到大)，視情況可以new 一個新的 Comparator 然後 Override compare method 來改寫排序方式。

```java
    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList<>();
        if(k == 0) return result;

        int len = arr.length;
        // 找到小於或等於x的第一個數，
        // 如果 k 比最大的數還大，回傳最小的數字
        int indexOfFirstX = findFirstX(arr, x);

        // 如果找到的index已經在邊界，代表之後的解只會往另外一個方向加入新數字
        boolean leftEnd = false;
        boolean rightEnd = false;

        result.add(arr[indexOfFirstX]);

        // 往左右兩邊找尋加入的數字
        int leftSide = indexOfFirstX - 1;
        int rightSide =  indexOfFirstX + 1;

        // 標記邊界
        if(indexOfFirstX == 0) leftEnd = true;
        if(indexOfFirstX == len - 1) rightEnd = true;

        // 進行一次while loop，都會加入新的數字
        while(result.size() < k) {

            // 已經抵達左邊界
            if(leftEnd || leftSide < 0) {
                result.add(arr[rightSide++]);
                continue;
            }

            // 已經抵達右邊界
            if(rightEnd || rightSide >= len) {
                result.add(arr[leftSide--]);
                continue;
            }

            if(Math.abs(arr[leftSide] - x) <= arr[rightSide] - x ) {
                result.add(arr[leftSide--]);
            } else if(rightSide < len){
                result.add(arr[rightSide++]);
            }
        }

        // 將結果做排序 Default ： 從小到大
        Collections.sort(result, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        return result;
    }

    // Binary Search 找尋第一個小於等於X的index
    public static int findFirstX(int[] arr, int x) {
        if(x > arr[arr.length - 1]) return arr.length - 1;
        if(x < arr[0]) return 0;

        int start = 0;
        int end = arr.length - 1;

        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(arr[mid] >= x) {
                end = mid;
            } else {
                start = mid;
            }
        }
        return arr[end] > x ? start : end;
    }
```