package test.techprocess;

import test.techprocess.commands.Command;
import test.techprocess.commands.CommandCreate;
import test.techprocess.commands.CommandDelete;
import test.techprocess.commands.CommandTransfer;

import java.util.HashMap;
import java.util.Map;

public class CommandsContainer {

    private Map<String, Class> commandMap = new HashMap<>();

    public void init(){
        commandMap.put("transfer", CommandTransfer.class);
        commandMap.put("delete", CommandDelete.class);
        commandMap.put("create", CommandCreate.class);
    }

    public Command getCommand(String name) {
        Class cmdClazz = commandMap.get(name);
        if (cmdClazz != null) {
            try {
                return (Command) cmdClazz.getConstructor(null).newInstance(null);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
