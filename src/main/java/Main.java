import view.TaskManagerView;
import controller.MainController;

/**
 * Created by Sviatoslav_H on 24.10.2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        TaskManagerView theView = new TaskManagerView();
        MainController theMainController = new MainController(theView);
        theView.setVisible(true);

    }
}
