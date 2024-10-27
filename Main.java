public class Main {
    public static void main(String[] args) {

        int regs;
        boolean regsFound = false;
        try {
            regs = Integer.parseInt(args[0]);
            regsFound = true;
        } catch (NumberFormatException e) {
            regs = 32;
        }
 
        Parser parser = null;
        String flag = "";
        try {
            flag = regsFound ? args[2] : args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            flag = "-p";
        }
        if (regsFound) {
            parser = Frontend.run(flag, args[1]);
        } else {   
            parser = Frontend.run(flag, args[0]);
        }

        Renamer renamer = new Renamer(parser.head, parser.tail, parser.operations, 16, parser.maxReg);

        renamer.Rename();

        // parser.printIR();

        Allocator allocator = new Allocator(parser.head, regs, renamer.vr, renamer.maxLive, 32768);

        allocator.allocate();

        // allocator.printRenamedIR();
    }
}
