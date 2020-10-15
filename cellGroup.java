import java.util.BitSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

abstract class cellGroup {
    cell[] cells = new cell[9];
    BitSet availableNumbers = new BitSet(9);
    int index;

    int numberOfCellsInGroup = 0;

    @Override
    public String toString()
    {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
    .append("index", index + 1)
    .append("Cell1", cells[0])
    .append("Cell2", cells[1])
    .append("Cell3", cells[2])
    .append("Cell4", cells[3])
    .append("Cell5", cells[4])
    .append("Cell6", cells[5])
    .append("Cell7", cells[6])
    .append("Cell8", cells[7])
    .append("Cell9", cells[8])
    .append("Available Num", availableNumbers)
    .toString();
    }


    public void addCellInGroup(cell cell) {
        if(numberOfCellsInGroup <9){
        cells[numberOfCellsInGroup] = cell;
        numberOfCellsInGroup++;
        }
    }

    public void clearAvailableNumber(int Number){
        this.availableNumbers.clear(Number - 1);
    }


    //Looks at a Cell Group and tries to find a cell that is the only one that has a specific number available to it
    public void lookForOnlyAvailableCellForNumber (){

 
        BitSet combined = new BitSet(9);
        BitSet temp = new BitSet(9);
        for (cell cell : cells) {
            if(cell.solved == false){
                

                combined.clear();
                temp.clear();
                for (cell cell2 : cells){
                    
                    if(cell2 != cell){
                    combined.or(cell2.availableNumbers);
                    }

                }
                combined.flip(0,9);
                combined.and(cell.availableNumbers);



                if( combined.cardinality() == 1){
                    System.out.println("NewGroup for Cell Index: " + (cell.index + 1));
                    System.out.println("Group Available Num: " + cell.row.availableNumbers + cell.column.availableNumbers + cell.box.availableNumbers);
                    System.out.println(this.toString());
                    cell.availableNumbers = combined;
                    cell.checkIfSolved();
                    System.out.println(this.toString());
                    System.out.println("Group Available Num: " + cell.row.availableNumbers + cell.column.availableNumbers + cell.box.availableNumbers);
                    System.out.println("");
                    
                }



            }

        }
    }

    //Looks for 2 cells that only have two of the same numbers available to them and removes those two numbers from all other available cells.
    public void simplifyWithPairs(){

        cell[] pairedCells = this.checkForPairs();
        for(cell element : pairedCells){
            for(cell comparedCell: pairedCells){
                if(element != null && comparedCell != null){
                    if(element.availableNumbers.equals(comparedCell.availableNumbers)){

                        simplifySharedCellGroup(element, comparedCell);
                        //System.out.println("Pair Simplification Triggered");

                    }
                }
            }

        }
    }

    private void simplifySharedCellGroup(cell element, cell comparedCell) {
        int firstIndex = element.availableNumbers.nextSetBit(0);
        int secondIndex = element.availableNumbers.nextSetBit(firstIndex+1);
        if(element.box == comparedCell.box) {
            element.box.removeTwoAvailableNumberFromAllCellsExcludingTwoGiven(element, comparedCell, firstIndex,
                    secondIndex);
        }
        if (element.row == comparedCell.row) {
            element.row.removeTwoAvailableNumberFromAllCellsExcludingTwoGiven(element, comparedCell, firstIndex,
                    secondIndex);
        }
        if (element.column == comparedCell.column) {
            element.column.removeTwoAvailableNumberFromAllCellsExcludingTwoGiven(element, comparedCell, firstIndex,
                    secondIndex);
        }

    }

    protected void removeTwoAvailableNumberFromAllCellsExcludingTwoGiven(cell element, cell comparedCell,
            int firstIndex, int secondIndex) {
        for(cell toBeSimplified: cells) {
            if(toBeSimplified != element && toBeSimplified != comparedCell && toBeSimplified.solved == false){

                if(firstIndex > 0 && secondIndex > 0){
                    toBeSimplified.removeAvailableNumber(firstIndex);
                    toBeSimplified.removeAvailableNumber(secondIndex);
                }
            }
            
        }
    }

    public cell[] checkForPairs(){

        cell[] returnArray = new cell[9];
        int count = 0;
        for(cell element: cells){
            if (element.availableNumbers.cardinality() == 2 && element.solved == false){
                returnArray[count] = element;
                count++;
            }
        }
        return returnArray;
    }

}
