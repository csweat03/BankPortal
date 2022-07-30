package me.christian.bankportal.server.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Sweat
 * @since 9:28 PM 7/28/22
 */
public abstract class Handler<T> {

    protected List<T> implementationList = new ArrayList<>();

    public abstract void initialize();
    public abstract void update();

    public List<T> get() {
        return implementationList;
    }

    @SafeVarargs
    public final void add(T... implementation) {
        implementationList.addAll(List.of(implementation));
    }


}
