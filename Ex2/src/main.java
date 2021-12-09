public class main   {

    public static void main (String[] args){
        if(args.length==1)
            Ex2.runGUI(args[0]);
        else{
            System.out.println("Wrong format ");
            System.out.println("Enter java -jar <name of jar file> <src of json file>");
        }

    }
}