import java.io.IOException;
import java.util.Scanner;

public class QueryReader {

    /**
     * Index all text files under a directory.
     */
    public static void main(String[] args) throws IOException {

        /**
         * Setting Index and Data Dirs
         */

        String docsPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/PersianPoemsData/Poems";
        String indexPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/index";

        PoemIndexer poemIndexer = new PoemIndexer(docsPath,indexPath);
        poemIndexer.buildIndex();

        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();
        scanner.close();

        PoemFinder poemFinder = new PoemFinder(query,indexPath);

        System.out.println(poemFinder.find());

    }

}