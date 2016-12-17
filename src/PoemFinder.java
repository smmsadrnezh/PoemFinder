import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class PoemFinder {

    String indexPath = "/home/smmsadrnezh/IdeaProjects/PoemFinder/index";

    String[] fields;
    String[] queries;
    int index = 0;

    void initSearchArrays(int size) {
        fields = new String[size];
        queries = new String[size];
        for (String query : queries
                ) {
            query = "";
        }
    }

    public void setPoet(String poet) {
        this.queries[index] = poet;
        fields[index] = "poet";
        index++;
    }

    public void setBook(String book) {
        this.queries[index] = book;
        fields[index] = "book";
        index++;
    }

    public void setSeries(String series) {
        this.queries[index] = series;
        fields[index] = "series";
        index++;
    }

    public void setTitle(String title) {
        this.queries[index] = title;
        fields[index] = "title";
        index++;
    }

    public void setPoem(String poem) {
        this.queries[index] = poem;
        fields[index] = "poem";
        index++;
    }

    public PoemFinder() {

    }

    void search() throws IOException, ParseException {

        // instantiate necessary objects
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new PersianAnalyzer();

        Query query = MultiFieldQueryParser.parse(queries, fields, analyzer);
        TopDocs topDocs = searcher.search(query, 10);
        ScoreDoc[] hits = topDocs.scoreDocs;

        for (ScoreDoc hit : hits
                ) {
            Document doc = searcher.doc(hit.doc);
            System.out.println(doc.get("path"));
        }
    }

}
