import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class PoemIndexer {

    private String docsPath;
    private String indexPath;

    protected PoemIndexer(String docsPath, String indexPath) {
        this.docsPath = docsPath;
        this.indexPath = indexPath;
    }

    /**
     * Index all text files under poems directory.
     */
    void buildIndex() throws IOException {

        final Path docDir = Paths.get(docsPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" + docDir.toAbsolutePath() + "' does not exist or is not readable, please check the path");
            System.exit(1);
        }

        Directory dir = FSDirectory.open(Paths.get(indexPath));
        Analyzer analyzer = new PersianAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(dir, indexWriterConfig);

        indexDocs(writer, docDir);

        writer.close();

    }

    /**
     * recurses over files and directories found under the given directory and build index.
     */
    void indexDocs(final IndexWriter writer, Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try {
                    indexDoc(writer, file);

                } catch (IOException ignore) {
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Indexes a single document
     */
    void indexDoc(IndexWriter writer, Path file) throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {
            Document doc = new Document();
            Field pathField = new StringField("path", file.toString(), Field.Store.YES);
            doc.add(pathField);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String singleLine;
            String poem = null;
            for (int i = 0; (singleLine = bufferedReader.readLine()) != null; i++) {
                switch (i) {
                    case 2:
                        doc.add(new StringField("poet", singleLine, Field.Store.YES));
                        break;
                    case 4:
                        doc.add(new StringField("book", singleLine, Field.Store.YES));
                        break;
                    case 6:
                        doc.add(new StringField("series", singleLine, Field.Store.YES));
                        break;
                    case 8:
                        doc.add(new StringField("title", singleLine, Field.Store.YES));
                        break;
                    default:
                        if (i > 9) {
                            poem += singleLine;
                        }
                }
            }
            doc.add(new TextField("poem", poem, Field.Store.YES));

            // New index, so we just add the document (no old document can be there):
            writer.addDocument(doc);
        }
    }
}