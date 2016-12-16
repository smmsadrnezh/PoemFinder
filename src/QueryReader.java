import org.apache.lucene.queryparser.classic.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueryReader {

    /**
     * Index all text files under a directory.
     */
    public static void main(String[] args) throws IOException, ParseException {

        // build the index from all text file under a specific directory
        String docsPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/PersianPoemsData/Poems";
        String indexPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/index";

        PoemIndexer poemIndexer = new PoemIndexer(docsPath, indexPath);
        poemIndexer.buildIndex();

        // config PoemFinder with a query
        PoemFinder poemFinder = new PoemFinder();
        String query = readQuery();
        configPoemFinder(poemFinder,query);

        // Search for poems
        poemFinder.search();
    }

    private static void configPoemFinder(PoemFinder poemFinder, String query) {
        String[] queryArray = query.split("###");
        poemFinder.initSearchArrays(queryArray.length);
        for (String queryClause : queryArray) {
            switch (queryClause.split(":")[0].replaceAll("#", "").replaceAll("null", "").trim()) {
                case "کتاب":
                    poemFinder.setBook(queryClause.split(":")[1]);
                    break;
                case "شاعر":
                    poemFinder.setPoet(queryClause.split(":")[1]);
                    break;
                case "سلسله":
                    poemFinder.setSeries(queryClause.split(":")[1]);
                    break;
                case "عنوان":
                    poemFinder.setTitle(queryClause.split(":")[1]);
                    break;
                case "متن":
                    poemFinder.setPoem(queryClause.split(":")[1]);
                    break;
            }
        }
    }

    private static String readQuery() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        bufferedReader.read();

        String query = null;
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            query += line;
        }

        return query;
    }

}