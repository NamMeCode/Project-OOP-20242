package hust.soict.ict.lab01;

public class AddMatrix {
    public static void main(String[] args) {
        int[][] matrix1={{1, 1, 1}, {1, 1, 1}};
        int[][] matrix2={{2, 2, 2}, {2, 2, 2}};
        int[][] matrix3=new int[2][3];
        for(int i=0; i<2; i++) for(int j=0; j<3; j++) matrix3[i][j]=matrix1[i][j]+matrix2[i][j];
        for(int i=0; i<2; i++) {
            for(int j=0; j<3; j++) {
                System.out.print(matrix3[i][j] + " ");
            }
            System.out.println();
        }
    }
}