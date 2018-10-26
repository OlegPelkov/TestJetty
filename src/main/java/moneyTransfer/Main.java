package moneyTransfer;

public class Main {

    public static void main(String[] args) throws Exception {
        boolean casMode = false;
        if (args != null && args.length > 0) {
            casMode = "casMode".equals(args[0]);
        }
        AccountServer.getInstance().setCas(casMode);
        AccountServer.getInstance().start();
        AccountServer.getInstance().getServer().join();
    }
}

