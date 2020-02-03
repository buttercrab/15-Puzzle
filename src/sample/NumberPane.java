package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

class NumberPane extends Pane {
    private Timeline animation;
    private int sceneNumber; /// 0: start, 1: game, 2: game over
    private int size, moveCount = 0, minimumMove = 0;
    private int[][] array;
    private int[] dx = {1, -1, 0, 0}, dy = {0, 0, 1, -1};
    private ArrayList<CustomButton> buttons = new ArrayList<>();
    private Text timer = new Text(), heading = new Text(), moves = new Text();
    private CustomButton startButton = new StartButton(this);
    private CustomButton endButton = new EndButton(this);
    private boolean noClick = false;
    private long startTime = 0;
    private double lastTime = 0, score = 0;

    NumberPane(int size) {
        this.size = size;

        if (size <= 1) throw new IllegalStateException("size have to be 2 or more");

        sceneNumber = 0;

        heading.setText("15 Puzzle");

        heading.setFont(Font.font("Noto Sans", FontWeight.THIN, 20));
        heading.setFill(Color.BLACK);

        heading.xProperty().bind((NumberBinding) Util.bind("($ - $) / 2", this.widthProperty(), heading.getLayoutBounds().getWidth()));
        heading.yProperty().bind((NumberBinding) Util.bind("$ / 3.2", this.heightProperty()));

        NumberBinding scale1 = (NumberBinding) Util.bind("max(0.5, min($, $) / 100)", this.widthProperty(), this.heightProperty());
        heading.scaleXProperty().bind(scale1);
        heading.scaleYProperty().bind(scale1);

        timer.setText("");

        timer.setFont(Font.font("Noto Sans", FontWeight.THIN, 20));
        timer.setFill(Color.BLACK);

        timer.xProperty().bind((NumberBinding) Util.bind("$ / $ * 25", this.widthProperty(), this.size * 110 + 10));
        timer.yProperty().bind((NumberBinding) Util.bind("$ / $ * 25 + 7", this.heightProperty(), this.size * 110 + 60));

        NumberBinding scale2 = (NumberBinding) Util.bind("max(0.5, min($ / 7, $ / $ * 20) / 20)", this.widthProperty(), this.heightProperty(), this.size * 110 + 60);
        timer.scaleXProperty().bind(scale2);
        timer.scaleYProperty().bind(scale2);

        moves.setText("");

        moves.setFont(Font.font("Noto Sans", FontWeight.THIN, 20));
        moves.setFill(Color.BLACK);

        moves.xProperty().bind((NumberBinding) Util.bind("$ / $ * 25 + $ / 2", this.widthProperty(), this.size * 110 + 10, this.widthProperty()));
        moves.yProperty().bind((NumberBinding) Util.bind("$ / $ * 25 + 7", this.heightProperty(), this.size * 110 + 60));

        NumberBinding scale3 = (NumberBinding) Util.bind("max(0.5, min($ / 7, $ / $ * 20) / 20)", this.widthProperty(), this.heightProperty(), this.size * 110 + 60);
        moves.scaleXProperty().bind(scale3);
        moves.scaleYProperty().bind(scale3);

        this.getChildren().addAll(heading);

        animation = new Timeline(new KeyFrame(Duration.millis(20), e -> draw()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private boolean isSolvable(ArrayList<Integer> puzzle) {
        int parity = 0;
        int row = 0;
        int blankRow = 0;

        for (int i = 0; i < puzzle.size(); i++) {
            if (i % size == 0) row++;
            if (puzzle.get(i) == 0) {
                blankRow = row;
                continue;
            }
            for (int j = i + 1; j < puzzle.size(); j++) {
                if (puzzle.get(i) > puzzle.get(j) && puzzle.get(j) != 0) parity++;
            }
        }

        if (size % 2 == 0) {
            if (blankRow % 2 == 0) return parity % 2 == 0;
            return parity % 2 != 0;
        }
        return parity % 2 == 0;
    }

    private void initGame() {
        array = new int[size][size];
        ArrayList<Integer> tmp = new ArrayList<>();

        for (int i = 0; i < size * size; i++)
            tmp.add(i);
        MAIN:
        do {
            Collections.shuffle(tmp);
            if (!isSolvable(tmp)) {
                if (tmp.get(0) == 0)
                    Collections.swap(tmp, 1, 2);
                else if (tmp.get(1) == 0)
                    Collections.swap(tmp, 0, 2);
                else
                    Collections.swap(tmp, 0, 1);
            }

            for (int i = 0; i < size * size - 1; i++) {
                if (tmp.get(i) != i + 1)
                    break MAIN;
            }
        } while (true);

        Iterator<Integer> iterator = tmp.iterator();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int t = iterator.next();
                array[i][j] = t;
                if (t != 0) buttons.add(new GameButton(this, i, j, t));
            }
        }

        noClick = false;
        moveCount = 0;
    }

    private boolean isGameEnd() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (array[i][j] == 0) continue;
                if (array[i][j] != i * size + j + 1)
                    return false;
            }
        }
        return true;
    }

    private void draw() {
        for (CustomButton button : this.buttons) {
            button.update();
        }

        switch (sceneNumber) {
            case 0:
                startButton.update();
                break;
            case 1:
                lastTime = (double) ((System.currentTimeMillis() - startTime) / 100) / 10;
                timer.setText("time: " + lastTime);
                if (noClick) break;
                if (isGameEnd()) {
                    this.getChildren().clear();
                    this.getChildren().addAll(timer, moves, endButton, endButton.text, heading);
                    heading.setText("Game Clear!");
                    heading.setFont(Font.font("Noto Sans", 15));
                    buttons.clear();
                    sceneNumber = 2;
                }
                break;
            case 2:
                endButton.update();
                break;
            default:
                break;
        }
    }

    private int getDirection(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= size || ny < 0 || ny >= size) continue;
            if (array[nx][ny] == 0) return i + 1;
        }
        return 0;
    }

    void mouseMove(MouseEvent e) {
        if (noClick) return;
        if (sceneNumber == 0) {
            startButton.hover(e.getX(), e.getY());
            return;
        }
        if (sceneNumber == 2) {
            endButton.hover(e.getX(), e.getY());
            return;
        }
        for (CustomButton button : this.buttons) {
            button.hover(e.getX(), e.getY());
        }
    }

    void click(MouseEvent e) {
        if (noClick) return;
        if (sceneNumber == 0) {
            startButton.click(e.getX(), e.getY());
            return;
        }
        if (sceneNumber == 2) {
            endButton.click(e.getX(), e.getY());
            return;
        }
        for (CustomButton button : this.buttons) {
            if (button.click(e.getX(), e.getY()))
                break;
        }
    }

    private void move() {
        moveCount++;
        moves.setText("moves: " + moveCount);
    }

    abstract class CustomButton extends Rectangle {

        Text text = new Text();
        Pane parent;

        CustomButton(Pane pane) {
            super();
            this.parent = pane;
            this.parent.getChildren().add(this);
            this.parent.getChildren().add(text);
        }

        private boolean collide(double x, double y) {
            return this.getX() <= x && x <= this.getX() + this.getWidth() && this.getY() <= y && y <= this.getY() + this.getHeight();
        }

        abstract void hover(double x, double y);

        abstract boolean click(double x, double y);

        abstract void update();
    }

    class StartButton extends CustomButton {

        private DoubleProperty scaleNow = new SimpleDoubleProperty(1);
        private int scaleProgress = 0, dScale = 0;

        StartButton(Pane pane) {
            super(pane);

            this.xProperty().bind((NumberBinding) Util.bind("($ - $) / 2", this.parent.widthProperty(), this.widthProperty()));
            this.yProperty().bind((NumberBinding) Util.bind("$ / 4 * 3 - $ / 2 - 5", this.parent.heightProperty(), this.heightProperty()));

            this.widthProperty().bind((NumberBinding) Util.bind("$ / $ * $ * $", this.parent.widthProperty(), 500, 100, this.scaleNow));
            this.heightProperty().bind((NumberBinding) Util.bind("$ / $ * $ * $", this.parent.heightProperty(), 500, 60, this.scaleNow));

            this.arcWidthProperty().bind((NumberBinding) Util.bind("min($, $) / 50 * $", this.parent.widthProperty(), this.parent.heightProperty(), this.scaleNow));
            this.arcHeightProperty().bind((NumberBinding) Util.bind("min($, $) / 50 * $", this.parent.widthProperty(), this.parent.heightProperty(), this.scaleNow));

            this.setFill(Color.LIGHTCORAL);

            this.text.setText("Start");

            this.text.setFont(Font.font("Noto Sans", FontWeight.THIN, 16));
            this.text.setFill(Color.BLACK);

            this.text.xProperty().bind((NumberBinding) Util.bind("($ - $) / 2", this.parent.widthProperty(), this.text.getLayoutBounds().getWidth()));
            this.text.yProperty().bind((NumberBinding) Util.bind("$ / 4 * 3", this.parent.heightProperty()));

            NumberBinding scale = (NumberBinding) Util.bind("max(0.5, min($ / 5, $ / 2) / 10)", this.widthProperty(), this.heightProperty());
            this.text.scaleXProperty().bind(scale);
            this.text.scaleYProperty().bind(scale);
        }

        @Override
        void hover(double x, double y) {
            if (!super.collide(x, y)) {
                if (Math.abs(this.scaleNow.get() - 1) > 1e-6) {
                    dScale = -1;
                }
            } else {
                if (Math.abs(this.scaleNow.get() - 1.1) > 1e-6) {
                    dScale = 1;
                }
            }
        }

        @Override
        boolean click(double x, double y) {
            if (!super.collide(x, y)) return false;
            this.parent.getChildren().clear();
            this.parent.getChildren().addAll(timer, moves);
            moves.setText("moves: 0");
            heading.setText("");
            initGame();
            sceneNumber = 1;
            startTime = System.currentTimeMillis();
            return true;
        }

        @Override
        void update() {
            scaleProgress += dScale;
            if (scaleProgress < 0) scaleProgress = 0;
            if (scaleProgress > 10) scaleProgress = 10;

            scaleNow.set(Util.map(Util.smoothAnimation(10, scaleProgress, dScale == -1, dScale == 1), 0, 1, 1, 1.1));
        }
    }

    class EndButton extends CustomButton {

        private DoubleProperty scaleNow = new SimpleDoubleProperty(1);
        private int scaleProgress = 0, dScale = 0;

        EndButton(Pane pane) {
            super(pane);

            pane.getChildren().remove(2, 4);

            this.xProperty().bind((NumberBinding) Util.bind("($ - $) / 2", this.parent.widthProperty(), this.widthProperty()));
            this.yProperty().bind((NumberBinding) Util.bind("$ / 4 * 3 - $ / 2 - 5", this.parent.heightProperty(), this.heightProperty()));

            this.widthProperty().bind((NumberBinding) Util.bind("$ / $ * $ * $", this.parent.widthProperty(), 500, 100, this.scaleNow));
            this.heightProperty().bind((NumberBinding) Util.bind("$ / $ * $ * $", this.parent.heightProperty(), 500, 60, this.scaleNow));

            this.arcWidthProperty().bind((NumberBinding) Util.bind("min($, $) / 50 * $", this.parent.widthProperty(), this.parent.heightProperty(), this.scaleNow));
            this.arcHeightProperty().bind((NumberBinding) Util.bind("min($, $) / 50 * $", this.parent.widthProperty(), this.parent.heightProperty(), this.scaleNow));

            this.setFill(Color.LIGHTCORAL);

            this.text.setText("Home");

            this.text.setFont(Font.font("Noto Sans", FontWeight.THIN, 16));
            this.text.setFill(Color.BLACK);

            this.text.xProperty().bind((NumberBinding) Util.bind("($ - $) / 2", this.parent.widthProperty(), this.text.getLayoutBounds().getWidth()));
            this.text.yProperty().bind((NumberBinding) Util.bind("$ / 4 * 3", this.parent.heightProperty()));

            NumberBinding scale = (NumberBinding) Util.bind("max(0.5, min($ / 5, $ / 2) / 10)", this.widthProperty(), this.heightProperty());
            this.text.scaleXProperty().bind(scale);
            this.text.scaleYProperty().bind(scale);
        }

        @Override
        void hover(double x, double y) {
            if (!super.collide(x, y)) {
                if (Math.abs(this.scaleNow.get() - 1) > 1e-6) {
                    dScale = -1;
                }
            } else {
                if (Math.abs(this.scaleNow.get() - 1.1) > 1e-6) {
                    dScale = 1;
                }
            }
        }

        @Override
        boolean click(double x, double y) {
            if (!super.collide(x, y)) return false;
            this.parent.getChildren().clear();
            heading.setText("15 puzzle");
            heading.setFont(Font.font("Noto Sans", 20));
            this.parent.getChildren().addAll(heading, startButton, startButton.text);
            sceneNumber = 0;
            return true;
        }

        @Override
        void update() {
            scaleProgress += dScale;
            if (scaleProgress < 0) scaleProgress = 0;
            if (scaleProgress > 10) scaleProgress = 10;

            scaleNow.set(Util.map(Util.smoothAnimation(10, scaleProgress, dScale == -1, dScale == 1), 0, 1, 1, 1.1));
        }
    }


    class GameButton extends CustomButton {

        private DoubleProperty scaleNow = new SimpleDoubleProperty(1);
        private int scaleProgress = 0, dScale = 0;

        private DoubleProperty xPosition, yPosition;
        private int moveProgress = 0, moveDir, xpos, ypos;
        private double moveFrom = 0, moveTo = 0;

        GameButton(Pane pane, int y, int x, int number) {
            super(pane);

            this.setFill(Color.LIGHTCORAL);

            this.xpos = x;
            this.ypos = y;

            this.xPosition = new SimpleDoubleProperty(x);
            this.yPosition = new SimpleDoubleProperty(y);

            this.xProperty().bind((NumberBinding) Util.bind("$ / $ * ($ * 110 + 60) - $ / 2", this.parent.widthProperty(), 110 * size + 10, this.xPosition, this.widthProperty()));
            this.yProperty().bind((NumberBinding) Util.bind("$ / $ * ($ * 110 + 110) - $ / 2", this.parent.heightProperty(), 110 * size + 60, this.yPosition, this.heightProperty()));

            this.widthProperty().bind((NumberBinding) Util.bind("$ / $ * 100 * $", this.parent.widthProperty(), 110 * size + 10, this.scaleNow));
            this.heightProperty().bind((NumberBinding) Util.bind("$ / $ * 100 * $", this.parent.heightProperty(), 110 * size + 60, this.scaleNow));

            this.arcWidthProperty().bind((NumberBinding) Util.bind("min($, $) / 50 * $", this.parent.widthProperty(), this.parent.heightProperty(), this.scaleNow));
            this.arcHeightProperty().bind((NumberBinding) Util.bind("min($, $) / 50 * $", this.parent.widthProperty(), this.parent.heightProperty(), this.scaleNow));

            this.text.setText("" + number);

            this.text.setFont(Font.font("Noto Sans", FontWeight.THIN, 20));
            this.text.setFill(Color.BLACK);

            this.text.xProperty().bind((NumberBinding) Util.bind("$ / $ * ($ * 110 + 60) - $ / 2", this.parent.widthProperty(), 110 * size + 10, this.xPosition, this.text.getLayoutBounds().getWidth()));
            this.text.yProperty().bind((NumberBinding) Util.bind("$ / $ * ($ * 110 + 110) + 5", this.parent.heightProperty(), 110 * size + 60, this.yPosition));

            NumberBinding scale = (NumberBinding) Util.bind("max(0.5, min($, $) / 50)", this.widthProperty(), this.heightProperty());
            this.text.scaleXProperty().bind(scale);
            this.text.scaleYProperty().bind(scale);
        }

        @Override
        void hover(double x, double y) {
            if (getDirection(ypos, xpos) == 0) return;

            if (!super.collide(x, y)) {
                if (Math.abs(this.scaleNow.get() - 1) > 1e-6) {
                    dScale = -1;
                }
            } else {
                if (Math.abs(this.scaleNow.get() - 1.05) > 1e-6) {
                    dScale = 1;
                }
            }
        }

        @Override
        boolean click(double x, double y) {
            if (!super.collide(x, y)) return false;
            int dir = getDirection(ypos, xpos);
            if (dir == 0) return false;
            noClick = true;
            this.moveProgress = 10;
            this.moveDir = dir;
            this.moveFrom = dir <= 2 ? yPosition.get() : xPosition.get();
            this.moveTo = dir <= 2 ? yPosition.get() + dx[dir - 1] : xPosition.get() + dy[dir - 1];

            array[ypos + dx[dir - 1]][xpos + dy[dir - 1]] = array[ypos][xpos];
            array[ypos][xpos] = 0;

            ypos += dx[dir - 1];
            xpos += dy[dir - 1];

            move();

            return true;
        }

        @Override
        void update() {
            scaleProgress += dScale;
            if (scaleProgress < 0) scaleProgress = 0;
            if (scaleProgress > 10) scaleProgress = 10;

            scaleNow.set(Util.map(Util.smoothAnimation(10, scaleProgress, dScale == -1, dScale == 1), 0, 1, 1, 1.05));

            if (moveProgress > 0) {
                moveProgress--;

                if (moveDir <= 2)
                    yPosition.set(Util.map(Util.smoothAnimation(9, 9 - moveProgress, false, true), 0, 1, moveFrom, moveTo));
                else
                    xPosition.set(Util.map(Util.smoothAnimation(9, 9 - moveProgress, false, true), 0, 1, moveFrom, moveTo));
                if (moveProgress == 0) {
                    xPosition.set(xpos);
                    yPosition.set(ypos);
                    noClick = false;
                }
            }
        }
    }
}