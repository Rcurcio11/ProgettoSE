
package seproject;

import java.util.ArrayDeque;
import java.util.Deque;


/**
 *
 * @author Group14
 */
public class OperationExecutor {
    
    private Deque<OperationCommand> commandStack;
    
    public OperationExecutor(){
        commandStack = new ArrayDeque<>();
    }
    
    public void execute(OperationCommand command){
        command.execute();
        commandStack.push(command);
    }
    
    public void undo(){
        if(!commandStack.isEmpty()){
            OperationCommand command = commandStack.pop();
            command.undo();
        }
    }
    
    public boolean commandIsInserted(){
        return !commandStack.isEmpty();
    }
}
