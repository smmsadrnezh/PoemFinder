import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueryReader {

    /**
     * Index all text files under a directory.
     */
    public static void main(String[] args) throws IOException {

        // Build the Index

        String docsPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/PersianPoemsData/Poems";
        String indexPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/index";

        PoemIndexer poemIndexer = new PoemIndexer(docsPath, indexPath);
        poemIndexer.buildIndex();

        // Parse and Build Query
        String query = readQuery();

        String[] queryArray = query.split("###");
        for (String queryClause : queryArray) {
            switch (queryClause.split(":")[0].replaceAll("#", "").replaceAll("null", "").trim()) {
                case "کتاب":
                    System.out.println(queryClause.split(":")[1]);
                    break;
                case "شاعر":
                    System.out.println(queryClause.split(":")[1]);
                    break;
                case "سلسله":
                    System.out.println(queryClause.split(":")[1]);
                    break;
                case "عنوان":
                    System.out.println(queryClause.split(":")[1]);
                    break;
                case "متن":
                    System.out.println(queryClause.split(":")[1]);
                    break;
            }
        }
    }

    private static String readQuery() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        bufferedReader.read();

        String query = null;
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            query += line;
        }

        return query;
    }

}