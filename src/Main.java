import java.io.*;
import java.util.Scanner;

interface Displayable {
    void displayInfo();
}
abstract class Visualization implements Displayable {
    private int width;
    protected int height;
    String backgroundColor;
    public boolean isInteractive;

    public Visualization(int width, int height, String backgroundColor, boolean isInteractive) {
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.isInteractive = isInteractive;
    }

    public int getWidth() {
        return width;
    }
}

class VisualizationFrame extends Visualization {
    private String frameType;
    protected boolean isResizable;
    public int zIndex;

    public VisualizationFrame(int width, int height, String backgroundColor, boolean isInteractive,
                              String frameType, boolean isResizable, int zIndex) {
        super(width, height, backgroundColor, isInteractive);
        this.frameType = frameType;
        this.isResizable = isResizable;
        this.zIndex = zIndex;
    }

    @Override
    public void displayInfo() {
        System.out.println("VisualizationFrame Info:");
        System.out.println("Width: " + getWidth());
        System.out.println("Height: " + height);
        System.out.println("Background Color: " + backgroundColor);
        System.out.println("Interactive?: " + isInteractive);
        System.out.println("Frame Type: " + frameType);
        System.out.println("Resizable?: " + isResizable);
        System.out.println("Z-Index: " + zIndex);
    }
}

class VisualizationLayer extends Visualization {
    private String layerName;
    protected int opacity;
    public boolean isVisible;

    public VisualizationLayer(int width, int height, String backgroundColor, boolean isInteractive,
                              String layerName, int opacity, boolean isVisible) {
        super(width, height, backgroundColor, isInteractive);
        this.layerName = layerName;
        this.opacity = opacity;
        this.isVisible = isVisible;
    }

    @Override
    public void displayInfo() {
        System.out.println("VisualizationLayer Info:");
        System.out.println("Width: " + getWidth());
        System.out.println("Height: " + height);
        System.out.println("Background Color: " + backgroundColor);
        System.out.println("Interactive?: " + isInteractive);
        System.out.println("Layer Name: " + layerName);
        System.out.println("Opacity: " + opacity);
        System.out.println("Visible?: " + isVisible);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean continueInput = true;

        while (continueInput) {
            Visualization[] visualizations = new Visualization[3];
            VisualizationFrame[] frames = new VisualizationFrame[3];
            VisualizationLayer[] layers = new VisualizationLayer[3];

            for (int i = 0; i < 3; i++) {
                System.out.println("Enter Visualization " + (i + 1) + " data:");
                System.out.print("Width: ");
                int width = scanner.nextInt();
                System.out.print("Height: ");
                int height = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Background Color: ");
                String backgroundColor = scanner.nextLine();
                System.out.print("Interactive visualization (true/false): ");
                boolean isInteractive = scanner.nextBoolean();

                visualizations[i] = new VisualizationFrame(width, height, backgroundColor, isInteractive,
                        "Type", true, 1); // Предполагается использование VisualizationFrame

                System.out.println("\nEnter VisualizationLayer " + (i + 1) + " data:");
                System.out.print("Layer Name: ");
                String layerName = scanner.next();
                System.out.print("Opacity: ");
                int opacity = scanner.nextInt();
                System.out.print("Visible (true/false): ");
                boolean isVisible = scanner.nextBoolean();

                layers[i] = new VisualizationLayer(width, height, backgroundColor, isInteractive,
                        layerName, opacity, isVisible);

                scanner.nextLine();
                System.out.println();

                System.out.println("Do you want to continue entering data? (yes/no)");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("no")) {
                    continueInput = false;
                    break;
                } else if (!choice.equalsIgnoreCase("yes")) {
                    System.out.println("Invalid input. Continuing data entry by default.");
                }
                scanner.nextLine();
            }

            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("visualizations.txt"))) {
                for (int i = 0; i < 3; i++) {
                    if (visualizations[i] == null || layers[i] == null) {
                        break;
                    }

                    String visualizationInfo = "\nVisualization " + (i + 1) + " Info:\n";
                    visualizationInfo += "------------------------\n";
                    visualizationInfo += "Width: " + visualizations[i].getWidth() + "\n";
                    visualizationInfo += "Height: " + visualizations[i].height + "\n";
                    visualizationInfo += "Background Color: " + visualizations[i].backgroundColor + "\n";
                    visualizationInfo += "Interactive?: " + visualizations[i].isInteractive + "\n";

                    String layerInfo = "\nVisualizationLayer " + (i + 1) + " Info:\n";
                    layerInfo += "------------------------\n";
                    layerInfo += "Width: " + layers[i].getWidth() + "\n";
                    layerInfo += "Height: " + layers[i].height + "\n";
                    layerInfo += "Background Color: " + layers[i].backgroundColor + "\n";
                    layerInfo += "Interactive?: " + layers[i].isInteractive + "\n";

                    byte[] visualizationBytes = visualizationInfo.getBytes();
                    byte[] layerBytes = layerInfo.getBytes();

                    bos.write(visualizationBytes);
                    bos.write(layerBytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("visualizations.txt"))) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = bis.read(buffer)) != -1) {
                    System.out.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
