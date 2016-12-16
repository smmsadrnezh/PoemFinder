import java.io.IOException;
import java.nio.file.Paths;

import com.sun.istack.internal.Nullable;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;

public class PoemFinder {

    String indexPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/index";

    String poet;
    String book;
    String series;
    String title;
    String poem;

    public void setPoet(String poet) {
        this.poet = poet;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoem(String poem) {
        this.poem = poem;
    }

    public PoemFinder() throws IOException, ParseException {

        // initialize search fields
        String[] fields = new String[5];
        fields[0] = "poet";
        fields[1] = "book";
        fields[2] = "series";
        fields[3] = "title";
        fields[4] = "poem";

        // initialize queries with null
        String[] queries = new String[5];
        queries[0] = "";
        queries[1] = "";
        queries[2] = "";
        queries[3] = "";
        queries[4] = "";

        // instantiate necessary objects
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new PersianAnalyzer();

        Query query = MultiFieldQueryParser.parse(queries, fields, analyzer);
        searcher.search(query, 10);

    }

}
