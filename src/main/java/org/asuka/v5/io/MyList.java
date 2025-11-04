package org.asuka.v5.io;

import java.util.List;

public record MyList<T>(List<T> data) {
    public MyList<T> add(T e) {
        data.add(e);
        return this;
    }

}
