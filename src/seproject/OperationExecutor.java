
package seproject;

import java.util.ArrayDeque;
import java.util.Deque;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


/**
 *
 * @author Group14
 */
public class OperationExecutor {
    
    private Deque<OperationCommand> commandStack;
    private BooleanProperty commandIn;
    
    public OperationExecutor(){
        commandStack = new ArrayDeque<>();
        commandIn = new SimpleBooleanProperty(false);
        
    }
    
    public void execute(OperationCommand command){
        command.execute();
        commandStack.push(command);
        commandIn.setValue(true);
    }
    
    public void undo(){
        if(!commandStack.isEmpty()){
            OperationCommand command = commandStack.pop();
            command.undo();
            if(commandStack.size() == 0)
                commandIn.setValue(false);
        }
    }
    
    public boolean commandIsInserted(){
        return !commandStack.isEmpty();
    }
    
    public BooleanProperty commandIn(){
        return commandIn;
    }
}
