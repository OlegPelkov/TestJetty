package test.techprocess;

import java.util.concurrent.atomic.AtomicLong;

public class IdCounter {

    public static class SingletonHolder {
        public static final IdCounter INSTANCE = new IdCounter();
    }

    public static IdCounter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private AtomicLong id = new AtomicLong();

    public long getNextId() {
        long nextId;
        do {
            nextId = id.get();
        } while (!id.compareAndSet(nextId, nextId + 1));
        return nextId + 1;
    }
}
