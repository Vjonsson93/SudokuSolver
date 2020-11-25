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
        for (cell cell : cells) {
            if(cell.solved == false){
                
                combined.clear();
                for (cell cell2 : cells){
                    
                    if(cell2 != cell){
                    combined.or(cell2.availableNumbers);
                    }

                }
                combined.flip(0,9);
                combined.and(cell.availableNumbers);

                if( combined.cardinality() == 1 && cell.solved == false){
                    cell.availableNumbers = (BitSet) combined.clone();
                    cell.checkIfSolved();                 
                }
            }

        }
    }
    
    public void findPairs () {
        BitSet pair = new BitSet (9);
        BitSet allOthers = new BitSet(9);

        
        for (cell cell : cells){
            if ( cell.solved == false){
                
                pair.clear();
                allOthers.clear();
                pair = (BitSet) cell.availableNumbers.clone();
                for (cell cell2 : cells){
                    if (cell2 != cell){
                        pair.and(cell2.availableNumbers);

                        if(pair.cardinality() == 2) {
                        
                            for(cell cell3 :cells){
                                if(cell3 != cell && cell3 != cell2){
                                    allOthers.and(cell3.availableNumbers);
                                }
                            }
                            allOthers.flip(0, 9);
                            allOthers.and(pair);

                            if(allOthers == pair){

                                cell.availableNumbers = (BitSet) pair.clone();
                                cell2.availableNumbers = (BitSet) pair.clone();
                                cell.pairedWith(cell2);
                            }
                        }
                    }
                }
            }
        }
    }
}


