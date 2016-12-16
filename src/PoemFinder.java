import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class PoemFinder {

    private PoemFinder() throws IOException, ParseException {

        String indexPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/index";

        // initialize search fields
        String[] fields = new String[4];
        fields[0] = "poet";
        fields[1] = "book";
        fields[2] = "series";
        fields[3] = "title";

        String[] queries = new String[4];
        queries[0] = "poet";
        queries[1] = "book";
        queries[2] = "series";
        queries[3] = "title";

        // crap
        boolean raw = false;
        String queryString = null;

        // instantiate necessary objects
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new PersianAnalyzer();

        while (true) {

            Query query = MultiFieldQueryParser.parse(queries, fields, analyzer);
            System.out.println(query);

        }

    }

}
