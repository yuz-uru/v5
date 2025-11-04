package org.asuka.v5.frame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.asuka.v5.io.IO;

import java.io.File;
import java.io.IOException;

public class Base extends Application {
    private IO io;

    public Base() {
    }

    @Override
    public void start(Stage stage) {
        Scene scene = scene(400, 323, stage);

        stage.setScene(scene);
        stage.setTitle("v5");
        stage.show();
    }

    private FileChooser chooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择文件夹");

        return chooser;
    }

    private String getPath(Stage stage, FileChooser chooser) {
        File selected = chooser.showOpenDialog(stage);

        if (selected != null) {
            return selected.getAbsolutePath();
        } else {
            return getPath(stage, chooser);
        }
    }

    private Scene scene(int x, int y, Stage stage) {
        Text t = text("", 40, 50);

        EventHandler<ActionEvent> writeEvent = event -> {
            try {
                io.write();
                t.setText(getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Button write = button("写入v5", 150, writeEvent);

        EventHandler<ActionEvent> initEvent = event -> {
            try {
                io.init();
                t.setText(getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Button init = button("还原Engine.ini", 275, initEvent);

        EventHandler<ActionEvent> directoryChooserEvent = event -> {
            FileChooser chooser = chooser();
            String path = getPath(stage, chooser);
            try {
                this.io = new IO(path);
                t.setText(getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Button directoryChooser = button("选择文件夹", 25, directoryChooserEvent);

        Pane root = new Pane();
        root.getChildren()
            .addAll(t, write, init, directoryChooser);

        return new Scene(root, x, y);
    }

    private String getText() throws IOException {
        return io == null ? "" :
                io.isOrigin() ? "当前已启用v5" : "当前未启用v5";
    }

    private Text text(String text, int x, int y) {
        Text t = new Text();

        t.setText(text);

        t.setLayoutX(x);
        t.setLayoutY(y);

        Font font = new Font(50);
        t.setFont(font);
        t.setFill(Color.RED);

        return t;
    }

    private Button button(String text, int x, EventHandler<ActionEvent> event) {
        Button b = new Button(text);

        b.setOnAction(event);
        b.setLayoutX(x);
        b.setLayoutY(150);

        b.setMinWidth(100);

        return b;
    }
}
