package CommandLineInterpreter;
import java.util.*;

public class Directory {
    static final private HashMap<String, Directory> map = new HashMap<>();
    private String name;
    private Directory parent;
    private static Directory presentWorkingDirectory;
    private ArrayList<Directory> child = new ArrayList<>();

    static{
        Directory root = new Directory("root");
        map.put("root", root);
        presentWorkingDirectory = root;
    }

    protected Directory(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }


    @Override
    public String toString() {
        return name + " " ;
    }

    public static void makeDirectory(String name){


        Directory directory = map.get(name);

        if(directory == null){
            directory = new Directory(name);
            map.put(name, directory);
        }
        else {
            System.out.println("file or directory is already present");
        }

        if(!presentWorkingDirectory.child.contains((directory))){
            presentWorkingDirectory.child.add(directory);
        }


    }

    public static void listDirectory(){

        Directory directory = map.get(presentWorkingDirectory.getName());

        if(directory != null){
            for(Directory childDirectory : directory.child){
                System.out.print(childDirectory + " ");
            }
            System.out.println();
        }
    }

    public static void changeDirectory(String name){

        Directory directory = map.get(name);

        if(directory != null) {
            presentWorkingDirectory = directory;
        }
        else {
            System.out.println("no such file or directory");

        }
    }
    public static void removeDirectory(String name){

        Directory directory = map.get(name);
        if(directory != null){
            if(directory.child.size() == 0 && presentWorkingDirectory.getName() != name && presentWorkingDirectory.child.contains(directory)){
                map.remove(name);
                presentWorkingDirectory.child.remove(directory);
            }
            else{
                System.out.println("failed to remove " + name + " : Directory is not empty");
            }

        }
        else{
            System.out.println("no such file or directory");
        }
    }

    public static void main(String[] args) {
        boolean flag = true;

        while(flag){

            System.out.print("root:~ ");
            Scanner scanner = new Scanner(System.in);

            String command = scanner.nextLine();

            String[] commandArray = new String[2];

            commandArray = command.split(" ");

            switch (commandArray[0]){
                case "mkdir" :
                    if(commandArray.length > 1) {
                        Directory.makeDirectory(commandArray[1]);
                    }
                    else{
                        System.out.println("mkdir: missing operand");
                    }
                    break;
                case "rmdir" :

                    if(commandArray.length > 1) {
                        Directory.removeDirectory(commandArray[1]);
                    }
                    else{
                        System.out.println("rmdir: missing operand");
                    }
                    break;
                case "ls" :
                    Directory.listDirectory();
                    break;
                case "cd" :
                    if(commandArray.length > 1) {
                        Directory.changeDirectory(commandArray[1]);
                    }
                    else{
                        presentWorkingDirectory = map.get("root");
                    }
                    break;
                case "pwd" :
                    System.out.println(presentWorkingDirectory.toString());
                    break;

                case "clear" :
                    flag = false;
                    break;

                default :
                    System.out.println("command not found");
                    break;

            }

        }

    }

}
