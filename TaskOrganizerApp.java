import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Task {
    private String description;
    private boolean completed;
    private Date dueDate;

    public Task(String description, Date dueDate) {
        this.description = description;
        this.completed = false;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "[" + (completed ? "X" : " ") + "] " + description + " (Fecha límite: " + dateFormat.format(dueDate) + ")";
    }
}

class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        return task1.getDueDate().compareTo(task2.getDueDate());
    }
}

class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String description, Date dueDate) {
        tasks.add(new Task(description, dueDate));
        System.out.println("Tarea agregada.");
    }

    public void listTasks() {
        Collections.sort(tasks, new TaskComparator());

        System.out.println("\nLista de tareas ordenadas por fecha límite:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
            System.out.println("Tarea marcada como completada.");
        } else {
            System.out.println("Número de tarea inválido.");
        }
    }
}

public class TaskOrganizerApp {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Organizador de Tareas ===");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Mostrar tareas");
            System.out.println("3. Marcar tarea como completada");
            System.out.println("4. Salir");
            System.out.print("Elije una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume el salto de línea después de la entrada numérica

            switch (option) {
                case 1:
                    System.out.print("Ingresa la descripción de la tarea: ");
                    String description = scanner.nextLine();

                    System.out.print("Ingresa la fecha límite (dd/MM/yyyy): ");
                    String dueDateString = scanner.nextLine();
                    Date dueDate = parseDate(dueDateString);

                    taskManager.addTask(description, dueDate);
                    break;

                case 2:
                    taskManager.listTasks();
                    break;

                case 3:
                    System.out.print("Ingresa el número de la tarea completada: ");
                    int taskIndex = scanner.nextInt();
                    taskManager.markTaskCompleted(taskIndex - 1);
                    break;

                case 4:
                    System.out.println("¡Gracias por usar el Organizador de Tareas!");
                    return;

                default:
                    System.out.println("Opción inválida. Por favor, elige una opción válida.");
            }
        }
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido. Se usará la fecha actual.");
            return new Date();
        }
    }
}