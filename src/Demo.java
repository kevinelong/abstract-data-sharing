import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract class AppScreen {
    protected HashMap<String, String> data;

    AppScreen(HashMap<String, String> data) {
        this.data = data;
    }

    abstract void show();

    // Extracted method to display options list
    protected int displayOptions(String key, String screenName) {
        System.out.println(screenName);
        System.out.println("0. to exit");
        String[] options = data.getOrDefault(key, "").split(",");

        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s\n", i + 1, options[i].trim());
        }

        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();

        if (choice.equals("0")) {
            return -1; // Indicate exit
        }
        return Integer.parseInt(choice) - 1;
    }
}

class HomeScreen extends AppScreen {
    HomeScreen(HashMap<String, String> data) {
        super(data);
    }

    public void show() {
        int choiceIndex = displayOptions("main", "Home Screen");

        if (choiceIndex == -1) {
            return; // Exit the application
        }

        // Handle selected option
        String[] options = data.get("main").split(",");
        System.out.println(options[choiceIndex]);

        if ("fruit".equalsIgnoreCase(options[choiceIndex].trim())) {
            navigateToNextScreen();
        }
    }

    // Extracted navigation logic
    private void navigateToNextScreen() {
        new FruitScreen(data).show();
    }
}

class FruitScreen extends AppScreen {
    FruitScreen(HashMap<String, String> data) {
        super(data);
    }

    public void show() {
        int choiceIndex = displayOptions("fruit", "Fruit Screen");

        if (choiceIndex == -1) {
            return; // Exit this screen
        }

        String[] options = data.get("fruit").split(",");
        System.out.println(options[choiceIndex]);
    }
}

class MyApp {
    public void run() {
        HashMap<String, String> data = new HashMap<>();
        data.put("main", "meat,cheese,fruit");
        data.put("fruit", "apple,banana,cherry");

        AppScreen root = new HomeScreen(data);
        root.show();
    }
}

public class Demo {
    static MyApp app = new MyApp();

    public static void main(String[] args) {
        app.run();
    }
}