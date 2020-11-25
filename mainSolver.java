public class mainSolver {
    static row[] rows = new row[9];
    static column[] columns = new column[9];
    static box[] boxes = new box[9];
    static cell[] cells = new cell[81];
    static String inputString = "000000043007530290200070000000040000043706010002890000700000500019000300000204000";
    static boolean sudokuSolved = false;
    static int loopCount = 0;
    static String outputString = "";
    static boolean changeMade = false;

    public static void main(final String[] args) {
        
        // changing inputString into an input Int  Array
        final String[] inputStringArray = inputString.split("");
        final int[] inputIntArray = new int[inputStringArray.length];
        for (int i = 0; i < inputStringArray.length; i++) {
            inputIntArray[i] = Integer.parseInt(inputStringArray[i]);
        }

        // creating 9 rows, column, and box objects
        for (int i = 0; i < 9; i++) {
            rows[i] = new row(i);
            columns[i] = new column(i);
            boxes[i] = new box(i);
        }
        // creating all cells for numbers and assigning them the correct row, column,
        // box, and number value
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i <= 2 && i >= 0) {
                    if (j <= 2 && j >= 0) {
                        cells[count] = new cell(rows[i], columns[j], boxes[0], inputIntArray[count], count);
                        count++;
                    } else if (j > 2 && j < 6) {
                        cells[count] = new cell(rows[i], columns[j], boxes[1], inputIntArray[count], count);
                        count++;
                    } else if (j >= 6 && j < 9) {
                        cells[count] = new cell(rows[i], columns[j], boxes[2], inputIntArray[count], count);
                        count++;
                    }
                } else if (i > 2 && i < 6) {
                    if (j <= 2 && j >= 0) {
                        cells[count] = new cell(rows[i], columns[j], boxes[3], inputIntArray[count], count);
                        count++;
                    } else if (j > 2 && j < 6) {
                        cells[count] = new cell(rows[i], columns[j], boxes[4], inputIntArray[count], count);
                        count++;
                    } else if (j >= 6 && j < 9) {
                        cells[count] = new cell(rows[i], columns[j], boxes[5], inputIntArray[count], count);
                        count++;
                    }

                } else if (i >= 6 && i < 9) {
                    if (j <= 2 && j >= 0) {
                        cells[count] = new cell(rows[i], columns[j], boxes[6], inputIntArray[count], count);
                        count++;
                    } else if (j > 2 && j < 6) {
                        cells[count] = new cell(rows[i], columns[j], boxes[7], inputIntArray[count], count);
                        count++;
                    } else if (j >= 6 && j < 9) {
                        cells[count] = new cell(rows[i], columns[j], boxes[8], inputIntArray[count], count);
                        count++;
                    }

                }

            }

        }

        //main loop
        while(sudokuSolved == false && loopCount < 100){
            for (cell element : cells){
                if(element.solved == false){
                    element.reduceAvailableNumberDueToCellGroup();
                    element.checkIfSolved();
                }
                
            }
            for (int i = 0; i < 9; i++){
                columns[i].lookForOnlyAvailableCellForNumber();
                rows[i].lookForOnlyAvailableCellForNumber();
                boxes[i].lookForOnlyAvailableCellForNumber();
            }
            for (int i = 0; i < 9; i++){
                columns[i].findPairs();
                rows[i].findPairs();
                boxes[i].findPairs();
            }

            checkIfPuzzleSolved();
            
            
            loopCount++;
            
        }

        for (cell element : cells){
            String temp = Integer.toString(element.number);
            outputString = outputString + temp;
        }

        System.out.println(outputString);
        System.out.println("Number of Loops required: " + loopCount);
    

    }

    static void checkIfPuzzleSolved(){
        sudokuSolved = true;
        for (cell element : cells) {
            if (element.solved == false) {
                sudokuSolved = false;
            }  
        }
    }
    
}
