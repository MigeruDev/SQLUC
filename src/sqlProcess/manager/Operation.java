/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlProcess.manager;

/**
 *
 * @author The Worst One
 */
public interface Operation {
    public void create(String command);
    public void delete(String command);
    public void update(String command);
    public void select(String command);
    public void join(String command);
}
