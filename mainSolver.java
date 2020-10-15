public class mainSolver {
    static row[] rows = new row[9];
    static column[] columns = new column[9];
    static box[] boxes = new box[9];
    static cell[] cells = new cell[81];
    static String inputString = "000003060080050700015080040800300009349006000206090370000400506700008002090000430";
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
        while(sudokuSolved == false && loopCount < 5){
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
            //CELL INDEX 12 IS BEHAVING WEIRD!!!! AVAILABLE NUMBERS DISAPPEARS AFTER IT HAS BEEN SOLVED???

            
            // for (int i = 0; i < 9; i++){
            //     columns[i].simplifyWithPairs();
            //     rows[i].simplifyWithPairs();
            //     boxes[i].simplifyWithPairs();
            // }
            checkIfPuzzleSolved();
            
            
            loopCount++;
            
        }
        // for (cellGroup cellGroup : rows) {
        //     System.out.println(cellGroup.toString());                
        // }
        // for (cellGroup cellGroup : columns) {
        //     System.out.println(cellGroup.toString());                
        // }
        // for (cellGroup cellGroup : boxes) {
        //     System.out.println(cellGroup.toString());                
        // }


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
