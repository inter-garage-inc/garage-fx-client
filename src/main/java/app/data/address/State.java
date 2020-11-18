package app.data.address;

import app.data.address.state.Brazil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Brazil.class)
public interface State {
    String getValue();
}
