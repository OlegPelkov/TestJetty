package moneyTransfer.techprocess;

import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import moneyTransfer.techprocess.commands.CommandDelete;
import moneyTransfer.techprocess.commands.CommandTransfer;

import java.util.HashMap;
import java.util.Map;

public class CommandsContainer {

    private Map<String, Class> commandMap = new HashMap<>();

    public void init(){
        commandMap.put(CommandTransfer.NAME, CommandTransfer.class);
        commandMap.put(CommandDelete.NAME, CommandDelete.class);
        commandMap.put(CommandCreate.NAME, CommandCreate.class);
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
