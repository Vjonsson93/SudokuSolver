import java.util.BitSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class cell {

    int number;
    row row;
    column column;
    box box;
    int rowIndex;
    int columnIndex;
    int boxIndex;
    boolean solved = false;
    BitSet availableNumbers = new BitSet(9);
    int numOfAvailableNumbers = 9;
    int index;

    public cell(row Row, column Column, box Box, int Number, int Index) {
        row = Row;
        row.addCellInGroup(this);
        rowIndex = row.index;
        column = Column;
        column.addCellInGroup(this);
        columnIndex = column.index;
        box = Box;
        box.addCellInGroup(this);
        boxIndex = box.index;
        index = Index;


        if (Number <= 9 && Number >= 1){
            number = Number;
            solved = true;
            availableNumbers.set(number-1);
            cellClearSolvedNumberFromGroup();
        } else {
            availableNumbers.flip(0,9);
            number=0;
            solved = false;
        };
        
    }

    @Override
    public String toString()
    {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
    .append("index", index + 1)
    .append("Row", rowIndex + 1)
    .append("Column", columnIndex + 1)
    .append("Box", boxIndex + 1)
    .append("Number", number)
    .append("Available Num", availableNumbers)
    .append("Solved", solved)
    .toString();
    }


    //Looks to see if there is only 1 possible number
    //Assigns that number to cell and marks as solved
    //calls CellClearSolvedNumberFromGroup
    public void checkIfSolved() {
        this.updateNumOfAvailableNumbers();
        if(this.availableNumbers.cardinality() == 1 && solved == false){
            this.number = this.availableNumbers.nextSetBit(0) + 1;
            this.cellClearSolvedNumberFromGroup();
            solved = true;
        }
    }

    public void removeAvailableNumber(int index) {
        this.availableNumbers.clear(index);
    }

    //removes the number assigned to this cell from all cell groups as a possible number
    public void cellClearSolvedNumberFromGroup() {
        row.clearAvailableNumber(this.number);
        column.clearAvailableNumber(this.number);
        box.clearAvailableNumber(this.number);
        this.updateNumOfAvailableNumbers();
    }
    //Checks cell groups to make sure this cell doesn't have any numbers available that aren't available in the cell group
    public void reduceAvailableNumberDueToCellGroup(){
        if(this.row.availableNumbers.cardinality() != 0){
            this.availableNumbers.and(this.row.availableNumbers);
        }
        if(this.row.availableNumbers.cardinality() != 0){
        this.availableNumbers.and(this.column.availableNumbers);}
        if(this.row.availableNumbers.cardinality() != 0){
        this.availableNumbers.and(this.box.availableNumbers);}
    }
    public void updateNumOfAvailableNumbers() {
        numOfAvailableNumbers = this.availableNumbers.cardinality();
    }
    public void pairedWith(cell cell) {
        if(this.row == cell.row){
            for(cell cells : this.row.cells){
                if(cells != this && cells != cell){
                    cells.availableNumbers.andNot(this.availableNumbers);
                }
            }
        }
        if(this.column == cell.column){
            for(cell cells : this.column.cells){
                if(cells != this && cells != cell){
                    cells.availableNumbers.andNot(this.availableNumbers);
                }
            }
        }
        if(this.box == cell.box){
            for(cell cells : this.box.cells){
                if(cells != this && cells != cell){
                    cells.availableNumbers.andNot(this.availableNumbers);
                }
            }
        }
    }

}

