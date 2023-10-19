package net.ironpulse.state;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Value<T> {
    private String name;

    private T value;
}
