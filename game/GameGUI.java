package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameGUI {
    private JFrame mainFrame;
    private JPanel currentlyDisplayedPanel;

    private JPanel mainMenuPanel;
    private JButton startButton;
    private JButton highScoreButton;
    private JButton exitButton;

    private JPanel highScoreMenuPanel;
    private JTextArea highScoreTextArea;
    private JButton[] backToMainMenuButtons;

    private JPanel pauseMenuPanel;
    private JButton resumeButton;
    private JButton exitToMainMenuButton;
    private JLabel lifeText;

    private JPanel scoreInputMenuPanel;
    private JLabel scoreInputErrorMessageLabel;
    private JTextField scoreNameInputTextField;
    private JButton scoreSubmitButton;

    private LevelPanel levelPanel;

    private JPanel gameOverMenuPanel;

    private Dimension baseButtonSize = new Dimension(100, 50);

    public GameGUI() {
        this.mainFrame = new JFrame("Yogi Bear");
        this.mainMenuPanel = this.createMainMenu();
        this.backToMainMenuButtons = new JButton[2];
        this.highScoreMenuPanel = this.createHighScoreMenu();
        this.levelPanel = new LevelPanel();
        this.pauseMenuPanel = this.createPauseMenu();
        this.scoreInputMenuPanel = this.createScoreInputMenu();
        this.gameOverMenuPanel = this.createGameOverMenu();
        this.currentlyDisplayedPanel = this.mainMenuPanel;

        this.mainFrame.add(this.mainMenuPanel);
        this.mainFrame.setResizable(false);
        this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setUndecorated(true);
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }

    public JFrame getMainFrame() {
        return this.mainFrame;
    }

    public JPanel getMainMenuPanel() {
        return this.mainMenuPanel;
    }

    public JPanel getHighScoreMenuPanel() {
        return this.highScoreMenuPanel;
    }

    public JTextArea getHighScoreTextArea() {
        return this.highScoreTextArea;
    }

    public LevelPanel getLevelPanel() {
        return this.levelPanel;
    }

    public JPanel getPauseMenuPanel() {
        return this.pauseMenuPanel;
    }

    public JLabel getLifeText() {
        return this.lifeText;
    }

    public JPanel getGameOverMenuPanel() {
        return this.gameOverMenuPanel;
    };

    public JPanel getScoreInputMenuPanel() {
        return this.scoreInputMenuPanel;
    }

    public JLabel getScoreInputErrorMessageLabel() {
        return this.scoreInputErrorMessageLabel;
    }

    public  JTextField getScoreNameInputTextField() {
        return this.scoreNameInputTextField;
    }

    public void displayPanel(JPanel panel) {
        this.currentlyDisplayedPanel.setVisible(false);
        mainFrame.remove(this.currentlyDisplayedPanel);
        mainFrame.add(panel);
        panel.setVisible(true);
        this.currentlyDisplayedPanel = panel;
    }

    public void addStartButtonActionListener(ActionListener listener) {
        this.startButton.addActionListener(listener);
    }

    public void addHighScoreButtonActionListener(ActionListener listener) {
        this.highScoreButton.addActionListener(listener);
    }

    public void addBackToMainMenuButtonActionListener(ActionListener listener) {
        for(JButton button : this.backToMainMenuButtons) {
            button.addActionListener(listener);
        }
    }

    public void addExitButtonActionListener(ActionListener listener) {
        this.exitButton.addActionListener(listener);
    }

    public void addResumeButtonActionListener(ActionListener listener) {
        this.resumeButton.addActionListener(listener);
    }

    public void addExitToMainMenuButtonActionListener(ActionListener listener) {
        this.exitToMainMenuButton.addActionListener(listener);
    }

    public void addScoreSubmitButtonActionListener(ActionListener listener) {
        this.scoreSubmitButton.addActionListener(listener);
    }

    private JPanel createMainMenu() {
        JPanel ret = new JPanelWithBackgroundImage("/menu_images/YogiBearWallpaper.jpg");
        GridBagConstraints constraint = new GridBagConstraints();

        ret.setLayout(new GridBagLayout());
        this.startButton = new JButton("Start");
        this.startButton.setPreferredSize(this.baseButtonSize);
        this.highScoreButton = new JButton("High Score");
        this.highScoreButton.setPreferredSize(this.baseButtonSize);
        this.exitButton = new JButton("Exit");
        this.exitButton.setPreferredSize(this.baseButtonSize);

        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        ret.add(this.startButton, constraint);

        constraint.insets = new Insets(10,0,0,0);
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.ipadx = 40;
        ret.add(this.highScoreButton, constraint);

        constraint.gridx = 0;
        constraint.gridy = 2;
        ret.add(this.exitButton, constraint);

        return ret;
    }

    private JPanel createPauseMenu() {
        JPanel ret = new JPanelWithBackgroundImage("/menu_images/RestingYogiBear.jpg");
        GridBagConstraints constraint = new GridBagConstraints();

        ret.setLayout(new GridBagLayout());
        this.resumeButton = new JButton("Resume");
        this.resumeButton.setPreferredSize(this.baseButtonSize);
        this.exitToMainMenuButton = new JButton("Exit to main menu");
        this.exitToMainMenuButton.setPreferredSize(this.baseButtonSize);
        this.lifeText = new JLabel("You have <number here> lives", SwingConstants.CENTER);
        this.lifeText.setBackground(Color.WHITE);
        this.lifeText.setOpaque(true);

        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        ret.add(this.resumeButton, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.ipadx = 40;
        constraint.insets = new Insets(25,0,0,0);
        ret.add(this.exitToMainMenuButton, constraint);

        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.weightx = 0;
        constraint.insets = new Insets(10,0,0,0);
        ret.add(this.lifeText, constraint);

        return ret;
    }

    private JPanel createHighScoreMenu() {
        JPanel ret = new JPanel();
        JLabel label = new JLabel("High Score");
        GridBagConstraints constraint = new GridBagConstraints();

        ret.setLayout(new GridBagLayout());
        this.highScoreTextArea = new JTextArea();
        this.highScoreTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.highScoreTextArea.setEditable(false);
        this.backToMainMenuButtons[0] = new JButton("Back to Main Menu");
        this.backToMainMenuButtons[0].setPreferredSize(this.baseButtonSize);

        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        ret.add(label, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.insets = new Insets(10,0,0,0);
        ret.add(this.highScoreTextArea, constraint);

        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.ipadx = 150;
        constraint.insets = new Insets(25,0,0,0);
        ret.add(this.backToMainMenuButtons[0], constraint);

        return ret;
    }

    private JPanel createScoreInputMenu() {
        JPanel ret = new JPanelWithBackgroundImage("/menu_images/EscapedYogiBear.jpg");
        JLabel label = new JLabel("Congratulations! Please input a name for the score board.", SwingConstants.CENTER);
        GridBagConstraints constraint = new GridBagConstraints();

        ret.setLayout(new GridBagLayout());
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        this.scoreNameInputTextField = new JTextField();
        this.scoreInputErrorMessageLabel = new JLabel("<ERROR>");
        this.scoreInputErrorMessageLabel.setForeground(Color.RED);
        this.scoreInputErrorMessageLabel.setBackground(Color.BLACK);
        this.scoreInputErrorMessageLabel.setOpaque(true);
        this.scoreInputErrorMessageLabel.setVisible(false);
        this.scoreSubmitButton = new JButton("Submit");
        this.scoreSubmitButton.setPreferredSize(this.baseButtonSize);

        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        ret.add(label, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.insets = new Insets(25,0,0,0);
        ret.add(this.scoreInputErrorMessageLabel, constraint);

        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.insets = new Insets(10,0,0,0);
        ret.add(this.scoreNameInputTextField, constraint);

        constraint.gridx = 0;
        constraint.gridy = 3;
        constraint.insets = new Insets(25,0,0,0);
        ret.add(this.scoreSubmitButton, constraint);

        return ret;
    }

    private JPanel createGameOverMenu() {
        JPanel ret = new JPanelWithBackgroundImage("/menu_images/CaughtYogiBear.jpg");
        JLabel label = new JLabel("Game Over.", SwingConstants.CENTER);
        GridBagConstraints constraint = new GridBagConstraints();

        ret.setLayout(new GridBagLayout());
        label.setForeground(Color.RED);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        this.backToMainMenuButtons[1] = new JButton("Back to Main Menu");
        this.backToMainMenuButtons[1].setPreferredSize(this.baseButtonSize);

        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        ret.add(label, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.ipadx = 150;
        constraint.insets = new Insets(25,0,0,0);
        ret.add(this.backToMainMenuButtons[1], constraint);

        return ret;
    }
}
