
public class MineField {
    boolean[][] field;
    int width;
    int height;

    public MineField(int width, int height, int mineCount) {
        this.width = width - 1;
        this.height = height - 1;
        field = new boolean[height][width];
        for (int i = 0; i < mineCount; i++) {
            int num1 = (int) (Math.random() * height);
            int num2 = (int) (Math.random() * width);
            if (field[num1][num2]) {
                i--;
            } else {
                field[num1][num2] = true;
            }

        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j]) {
                    System.out.print("1");
                }
                else{
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }


    public int numCloseMines(int i, int j){
        if(field[i][j]){
            return -1;
        }
        int count = 0;
        if(i>0 && j>0 && field[i-1][j-1]){
            count++;
            //System.out.println("1");
        }
        if(i>0 && field[i-1][j]){
            count++;
            //System.out.println("2");
        }
        if(i>0 && j<width && field[i-1][j+1]){
            count++;
            //System.out.println("3");
        }
        if(j>0 && field[i][j-1]){
            count++;
            //System.out.println("4");
        }
        if(j<width && field[i][j+1]){
            count++;
            //System.out.println("5");
        }
        if(i<height && j>0 && field[i+1][j-1]){
            count++;
            //System.out.println("6");
        }
        if(i<height && field[i+1][j]){
            count++;
            //System.out.println("7");
        }
        if(i<height && j<width && field[i+1][j+1]){
            count++;
            //System.out.println("8");
        }
        return count;
    }
}