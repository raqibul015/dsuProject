import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class UserManager {
    private User currentUser;
    private List<User> users;

    public UserManager() {
        users = FileHandler.loadUsers();
    }

    public void registerUser(String username, String password) {
        User user = new User(username, password);
        users.add(user);
        FileHandler.saveUser(user);
    }

    public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logoutUser() {
        currentUser = null;
    }
}

class FileHandler {
    private static final String USER_DATA_FILE = "userData.txt";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                users.add(new User(userData[0], userData[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(user.username + "," + user.password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Activity {
    String name;
    double duration;
    double distance;

    public Activity(String name, double duration, double distance) {
        this.name = name;
        this.duration = duration;
        this.distance = distance;
    }

    public double calculateCaloriesBurned() {
        double caloriesPerMinute = 5.0;
        return caloriesPerMinute * duration;
    }
}

class DailyActivityLogPanel extends JPanel {
    private UserManager userManager;
    private List<Activity> activities;

    public DailyActivityLogPanel(UserManager userManager, List<Activity> activities) {
        this.userManager = userManager;
        this.activities = activities;

        setLayout(new GridLayout(4, 2));

        JTextField nameField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField distanceField = new JTextField();

        add(new JLabel("Activity Name:"));
        add(nameField);
        add(new JLabel("Duration (minutes):"));
        add(durationField);
        add(new JLabel("Distance (kilometers):"));
        add(distanceField);

        JButton logButton = new JButton("Log Activity");

        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String activityName = nameField.getText();
                double duration = Double.parseDouble(durationField.getText());
                double distance = Double.parseDouble(distanceField.getText());

                Activity activity = new Activity(activityName, duration, distance);

                double caloriesBurned = activity.calculateCaloriesBurned();
                activities.add(activity);

                JOptionPane.showMessageDialog(null, "Activity recorded:\n" +
                        "Name: " + activity.name + "\n" +
                        "Duration: " + activity.duration + " minutes\n" +
                        "Distance: " + activity.distance + " kilometers\n" +
                        "Calories burned: " + caloriesBurned);
            }
        });

        add(logButton);
    }
}

class Workout {
    String exercise;
    int sets;
    int repetitions;

    public Workout(String exercise, int sets, int repetitions) {
        this.exercise = exercise;
        this.sets = sets;
        this.repetitions = repetitions;
    }

}

class WorkoutTrackingPanel extends JPanel {
    private List<Workout> workouts;

    public WorkoutTrackingPanel() {
        workouts = new ArrayList<>();

        setLayout(new GridLayout(4, 2));

        JTextField exerciseField = new JTextField();
        JTextField setsField = new JTextField();
        JTextField repsField = new JTextField();

        add(new JLabel("Exercise:"));
        add(exerciseField);
        add(new JLabel("Sets:"));
        add(setsField);
        add(new JLabel("Repetitions:"));
        add(repsField);

        JButton addWorkoutButton = new JButton("Add Workout");

        addWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String exercise = exerciseField.getText();
                int sets = Integer.parseInt(setsField.getText());
                int repetitions = Integer.parseInt(repsField.getText());

                Workout workout = new Workout(exercise, sets, repetitions);
                workouts.add(workout);

                JOptionPane.showMessageDialog(null, "Workout added!");
            }
        });

        add(addWorkoutButton);
    }
}

class Meal {
    String name;
    double calories;

    public Meal(String name, double calories) {
        this.name = name;
        this.calories = calories;
    }

}

class NutritionDiaryPanel extends JPanel {
    private List<Meal> meals;

    public NutritionDiaryPanel() {
        meals = new ArrayList<>();

        setLayout(new GridLayout(3, 2));

        JTextField mealNameField = new JTextField();
        JTextField caloriesField = new JTextField();

        add(new JLabel("Meal Name:"));
        add(mealNameField);
        add(new JLabel("Calories:"));
        add(caloriesField);

        JButton logMealButton = new JButton("Log Meal");

        logMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mealName = mealNameField.getText();
                double calories = Double.parseDouble(caloriesField.getText());

                Meal meal = new Meal(mealName, calories);
                meals.add(meal);

                JOptionPane.showMessageDialog(null, "Meal logged!");
            }
        });

        add(logMealButton);
    }
}

class Goal {
    String type;
    double target;

    public Goal(String type, double target) {
        this.type = type;
        this.target = target;
    }

}

class GoalSettingPanel extends JPanel {
    private List<Goal> goals;

    public GoalSettingPanel() {
        goals = new ArrayList<>();

        setLayout(new GridLayout(3, 2));

        JTextField goalTypeField = new JTextField();
        JTextField targetField = new JTextField();

        add(new JLabel("Goal Type:"));
        add(goalTypeField);
        add(new JLabel("Target Value:"));
        add(targetField);

        JButton setGoalButton = new JButton("Set Goal");

        setGoalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String goalType = goalTypeField.getText();
                double target = Double.parseDouble(targetField.getText());

                Goal goal = new Goal(goalType, target);
                goals.add(goal);

                JOptionPane.showMessageDialog(null, "Goal set!");
            }
        });

        add(setGoalButton);
    }
}

class DashboardPanel extends JPanel {
    private List<Activity> activities;
    private List<Workout> workouts;
    private List<Meal> meals;
    private List<Goal> goals;

    public DashboardPanel(List<Activity> activities, List<Workout> workouts,
                          List<Meal> meals, List<Goal> goals) {
        this.activities = activities;
        this.workouts = workouts;
        this.meals = meals;
        this.goals = goals;

        setLayout(new GridLayout(4, 1));

        JTextArea activityArea = new JTextArea();
        activityArea.setEditable(false);
        JTextArea workoutArea = new JTextArea();
        workoutArea.setEditable(false);
        JTextArea mealArea = new JTextArea();
        mealArea.setEditable(false);
        JTextArea goalArea = new JTextArea();
        goalArea.setEditable(false);

        for (Activity activity : activities) {
            activityArea.append("Activity: " + activity.name + ", Duration: " + activity.duration +
                    " minutes, Distance: " + activity.distance + " km\n");
        }

        for (Workout workout : workouts) {
            workoutArea.append("Exercise: " + workout.exercise + ", Sets: " + workout.sets +
                    ", Repetitions: " + workout.repetitions + "\n");
        }

        for (Meal meal : meals) {
            mealArea.append("Meal: " + meal.name + ", Calories: " + meal.calories + "\n");
        }

        for (Goal goal : goals) {
            goalArea.append("Goal Type: " + goal.type + ", Target: " + goal.target + "\n");
        }

        JScrollPane activityScrollPane = new JScrollPane(activityArea);
        JScrollPane workoutScrollPane = new JScrollPane(workoutArea);
        JScrollPane mealScrollPane = new JScrollPane(mealArea);
        JScrollPane goalScrollPane = new JScrollPane(goalArea);

        add(activityScrollPane);
        add(workoutScrollPane);
        add(mealScrollPane);
        add(goalScrollPane);
    }
}

class LogoutPanel extends JPanel {
    private UserManager userManager;

    public LogoutPanel(UserManager userManager) {
        this.userManager = userManager;

        JButton logoutButton = new JButton("Logout");

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userManager.logoutUser();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LogoutPanel.this);
                frame.getContentPane().removeAll();
                frame.add(new LoginPanel(userManager));
                frame.revalidate();
                frame.repaint();
            }
        });

        add(logoutButton);
    }
}

class FunctionalityPanel extends JPanel {
    private UserManager userManager;
    private List<Activity> activities;
    private List<Workout> workouts;
    private List<Meal> meals;
    private List<Goal> goals;

    public FunctionalityPanel(UserManager userManager) {
        this.userManager = userManager;
        this.activities = new ArrayList<>();
        this.workouts = new ArrayList<>();
        this.meals = new ArrayList<>();
        this.goals = new ArrayList<>();

        setLayout(new GridLayout(4, 2));

        JButton dailyActivityLogButton = new JButton("Daily Activity Log");
        JButton workoutTrackingButton = new JButton("Workout Tracking");
        JButton nutritionDiaryButton = new JButton("Nutrition Diary");
        JButton goalSettingButton = new JButton("Goal Setting");
        JButton dashboardButton = new JButton("Dashboard");
        JButton logoutButton = new JButton("Logout");

        dailyActivityLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DailyActivityLogPanel dailyActivityLogPanel = new DailyActivityLogPanel(userManager, activities);
                JFrame frame = new JFrame("Daily Activity Log");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(dailyActivityLogPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });

        workoutTrackingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorkoutTrackingPanel workoutTrackingPanel = new WorkoutTrackingPanel();
                JFrame frame = new JFrame("Workout Tracking");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(workoutTrackingPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });

        nutritionDiaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NutritionDiaryPanel nutritionDiaryPanel = new NutritionDiaryPanel();
                JFrame frame = new JFrame("Nutrition Diary");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(nutritionDiaryPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });

        goalSettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GoalSettingPanel goalSettingPanel = new GoalSettingPanel();
                JFrame frame = new JFrame("Goal Setting");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(goalSettingPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DashboardPanel dashboardPanel = new DashboardPanel(activities, workouts, meals, goals);
                JFrame frame = new JFrame("Dashboard");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(dashboardPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userManager.logoutUser();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FunctionalityPanel.this);
                frame.getContentPane().removeAll();
                frame.add(new LoginPanel(userManager));
                frame.revalidate();
                frame.repaint();
            }
        });

        add(dailyActivityLogButton);
        add(workoutTrackingButton);
        add(nutritionDiaryButton);
        add(goalSettingButton);
        add(dashboardButton);
        add(logoutButton);
    }
}

class MainFrame extends JFrame {
    private UserManager userManager;

    public MainFrame() {
        userManager = new UserManager();

        setTitle("Health and Fitness Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Registration", new RegistrationPanel(userManager));
        tabbedPane.addTab("Login", new LoginPanel(userManager));
        tabbedPane.addTab("Functionality", new FunctionalityPanel(userManager));

        add(tabbedPane);

        setVisible(true);
    }
}

class RegistrationPanel extends JPanel {
    private UserManager userManager;

    public RegistrationPanel(UserManager userManager) {
        this.userManager = userManager;

        setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);

        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                userManager.registerUser(username, password);

                JOptionPane.showMessageDialog(null, "Registration successful!");
            }
        });

        add(registerButton);
    }
}

class LoginPanel extends JPanel {
    private UserManager userManager;

    public LoginPanel(UserManager userManager) {
        this.userManager = userManager;

        setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userManager.loginUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LoginPanel.this);
                    frame.getContentPane().removeAll();
                    frame.add(new FunctionalityPanel(userManager));
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.");
                }
            }
        });

        add(loginButton);
    }
}

public class HealthFitnessTrackerApp {
    public static void main(String[] args) {
        new MainFrame();
    }
}
