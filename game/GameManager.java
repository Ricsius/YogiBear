package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class GameManager {
    private GameGUI gui;
    private Level currentLevel;
    private ArrayList<Level> levels;
    private int levelIndex;
    private int delay;
    private Timer timer;
    private Timer playTimeTimer;
    private int yogiBearLives;
    private int playTime;
    private char inputtedKey;
    private MysqlConnectionPoolDataSource dataSource;

    public GameManager() {
        this.gui = new GameGUI();
        this.levels = new ArrayList<>();
        this.delay = 1000/60;
        this.timer = new Timer(this.delay, new Updater());
        this.playTimeTimer = new Timer(1000, new PlayTimeListener());
        this.inputtedKey = ' ';

        JFrame mainFrame = this.gui.getMainFrame();

        mainFrame.addKeyListener(new KeyInputter());
        this.gui.addStartButtonActionListener(new StartButtonListener());
        this.gui.addHighScoreButtonActionListener(new HighScoreButtonListener());
        this.gui.addBackToMainMenuButtonActionListener(new BackToMainMenuButtonListener());
        this.gui.addExitButtonActionListener(new ExitButtonListener());
        this.gui.addResumeButtonActionListener(new ResumeButtonListener());
        this.gui.addExitToMainMenuButtonActionListener(new ExitToMainMenuButtonListener());
        this.gui.addScoreSubmitButtonActionListener(new ScoreSubmitButtonListener());

        this.updateHighScore();
    }

    public char getInputtedKey() {
        return this.inputtedKey;
    }

    public int getDelay() {
        return this.delay;
    }

    public void decreaseLives() {
        if(this.yogiBearLives >= 1) {
            --this.yogiBearLives;
        }
        else {
            this.gameOver();
        }
    }

    public void pauseGame() {
        JPanel pauseMenuPanel = this.gui.getPauseMenuPanel();
        JLabel lifeText = this.gui.getLifeText();

        this.timer.stop();
        this.playTimeTimer.stop();
        lifeText.setText("You have " + this.yogiBearLives + " lives left");
        this.gui.displayPanel(pauseMenuPanel);
    }

    public void loadNextLevel() {
        this.timer.stop();
        this.playTimeTimer.stop();

        if(this.levelIndex + 1 < this.levels.size()) {
            LevelPanel levelPanel = this.gui.getLevelPanel();

            ++this.levelIndex;
            this.currentLevel = this.levels.get(levelIndex);
            levelPanel.setLevelToDraw(this.currentLevel);
            this.timer.start();
            this.playTimeTimer.start();
        }
        else {
            this.finishGame();
        }
    }

    private void initializeLevels() throws FileNotFoundException {
        this.levels.clear();

        try {
            Integer index = 0;
            String indexString;
            InputStream inputStream = getClass().getResourceAsStream("/levels/Level00.txt");
            InputStreamReader streamReader;
            BufferedReader bufferedReader;

            while(inputStream != null) {
                streamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(streamReader);
                this.levels.add(new Level(this, bufferedReader));
                ++index;

                if(index < 10) {
                    indexString = "0" + index;
                }
                else {
                    indexString = index.toString();
                }

                bufferedReader.close();
                streamReader.close();
                inputStream.close();

                inputStream = getClass().getResourceAsStream("/levels/Level" + indexString +".txt");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeGame() {
        this.levelIndex = -1;
        this.yogiBearLives = 3;
        this.playTime = -1;

        try{
            initializeLevels();
        }catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void finishGame() {
        JPanel scoreInputMenuPanel = this.gui.getScoreInputMenuPanel();

        this.gui.displayPanel(scoreInputMenuPanel);
    }

    private void gameOver() {
        JPanel gameOverMenuPanel = this.gui.getGameOverMenuPanel();

        this.timer.stop();
        this.playTimeTimer.stop();
        this.gui.displayPanel(gameOverMenuPanel);
    }

    private Connection createDatabaseConnection() throws ClassNotFoundException, SQLException{
        if (this.dataSource == null){
            Class.forName("com.mysql.jdbc.Driver");
            this.dataSource = new MysqlConnectionPoolDataSource();
            this.dataSource.setServerName("localhost");
            this.dataSource.setPort(3306);
            this.dataSource.setDatabaseName("YogiBearHighScores");
            this.dataSource.setUser("root");
            this.dataSource.setPassword("");
        }
        return this.dataSource.getPooledConnection().getConnection();
    }

    private void updateHighScore() {
        JTextArea highScoreTextArea = gui.getHighScoreTextArea();
        String query = "SELECT Id, Name, Time FROM Score ORDER BY Time ASC LIMIT 10;";

        highScoreTextArea.setText("");

        try (Connection dataBaseConnection = this.createDatabaseConnection();
             PreparedStatement statement = dataBaseConnection.prepareStatement(query)) {
            ResultSet results = statement.executeQuery();

            Integer placement = 1;

            while (results.next()) {
                String timeString;
                int time = results.getInt(3);
                Integer hours;
                Integer minutes = time / 60;
                Integer seconds = time % 60;

                if(minutes < 60) {
                    timeString = minutes.toString() + " Minutes " + seconds.toString() + " Seconds";
                }
                else {
                    hours = minutes / 60;
                    minutes -= (hours * 60);
                    timeString = hours.toString() + " Hours " + minutes.toString() + " Minutes " + seconds.toString() + " Seconds";
                }

                highScoreTextArea.append(placement.toString() + ".\n" + "Name: " + results.getString(2) + "\nTime: "  + timeString + "\n\n");
                ++placement;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertScore(String name, int time) {

        String SQL = "INSERT INTO Score(Name, Time) "
                + "VALUES(?,?)";

        try (Connection dataBaseConnection = this.createDatabaseConnection();
             PreparedStatement statement = dataBaseConnection.prepareStatement(SQL)) {

            Integer realTime = time;

            statement.setString(1, name);
            statement.setString(2, realTime.toString());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LevelPanel levelPanel = gui.getLevelPanel();
            JFrame mainFrame = gui.getMainFrame();

            initializeGame();
            loadNextLevel();
            levelPanel.setLevelToDraw(currentLevel);
            gui.displayPanel(levelPanel);
            mainFrame.requestFocus();
            timer.start();
        }
    }

    private class HighScoreButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel highScoreMenuPanel = gui.getHighScoreMenuPanel();

            gui.displayPanel(highScoreMenuPanel);
        }
    }

    private class BackToMainMenuButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel mainMenuPanel = gui.getMainMenuPanel();

            gui.displayPanel(mainMenuPanel);
        }
    }

    private class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class ResumeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame mainFrame = gui.getMainFrame();
            LevelPanel levelPanel = gui.getLevelPanel();

            gui.displayPanel(levelPanel);
            mainFrame.requestFocus();

            timer.start();
        }
    }

    private class ExitToMainMenuButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel mainMenuPanel = gui.getMainMenuPanel();

            timer.stop();
            gui.displayPanel(mainMenuPanel);
        }
    }

    private class ScoreSubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel errorMessageLabel = gui.getScoreInputErrorMessageLabel();
            JTextField nameTextField = gui.getScoreNameInputTextField();
            JPanel mainMenuPanel = gui.getMainMenuPanel();
            String name = nameTextField.getText();

            if(name.length() == 0) {
                errorMessageLabel.setText("You didn't write a name!");
                errorMessageLabel.setVisible(true);
            }else if(name.length() > 35) {
                errorMessageLabel.setText("The name cannot be longer than 35 characters!");
                errorMessageLabel.setVisible(true);
            }
            else {
                errorMessageLabel.setVisible(false);
                insertScore(name, playTime);
                updateHighScore();
                gui.displayPanel(mainMenuPanel);
            }
        }
    }

    private class KeyInputter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            inputtedKey = e.getKeyChar();
        }
    }

    private class Updater implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentLevel.update();
            gui.getLevelPanel().repaint();
            inputtedKey = ' ';
        }
    }

    private class PlayTimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playTime += 1;
        }
    }
}
