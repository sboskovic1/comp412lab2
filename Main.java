public class Main {
    public static void main(String[] args) {

        Parser parser = Frontend.run(args);

        Renamer renamer = new Renamer(parser.head, parser.tail, parser.operations, 16, parser.maxReg);

        renamer.Rename();

        renamer.printRenamedIR();
    }
}
