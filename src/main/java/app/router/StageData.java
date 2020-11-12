package app.router;

import javafx.scene.Scene;
import lombok.Builder;
import lombok.Data;

import java.util.Stack;

@Data
@Builder
public class StageData {
    private String title;
    private Scene scene;
}
