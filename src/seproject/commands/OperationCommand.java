
package seproject.commands;

import seproject.exceptions.GenericDrawException;

/**
 *
 * @author Group14
 */
public interface OperationCommand {
    void execute() throws GenericDrawException;
    void undo();
}
