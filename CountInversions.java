
//Calculate Inversion for every Tile(Slice of puzle) and return the calc. for hand the puzzle is solvable or not.
public class CountInversions {
    public static int countInversions(int[][] arr) {
        int count = 0;
        int n = arr.length;

        for (int i = 0; i < n * n; i++) {
            int row1 = i / n;
            int col1 = i % n;

            if (arr[row1][col1] == 0) // Skip counting inversions for 0
                continue;

            for (int j = i + 1; j < n * n; j++) {
                int row2 = j / n;
                int col2 = j % n;

                if (arr[row2][col2] == 0) // Skip counting inversions for 0
                    continue;

                if (arr[row1][col1] > arr[row2][col2])
                    count++;
            }
        }
        System.out.println("The number of inversion: " +count);    //for developer to see it's work correctly
        return count;
    }
}

